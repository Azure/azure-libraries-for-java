/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.io.BaseEncoding;
import com.microsoft.azure.CloudException;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.appservice.AppServicePlan;
import com.microsoft.azure.management.appservice.FunctionApp;
import com.microsoft.azure.management.appservice.FunctionDeploymentSlots;
import com.microsoft.azure.management.appservice.FunctionRuntimeStack;
import com.microsoft.azure.management.appservice.NameValuePair;
import com.microsoft.azure.management.appservice.OperatingSystem;
import com.microsoft.azure.management.appservice.PricingTier;
import com.microsoft.azure.management.appservice.SkuDescription;
import com.microsoft.azure.management.appservice.SkuName;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.StorageAccountKey;
import com.microsoft.azure.management.storage.StorageAccountSkuType;
import com.microsoft.rest.credentials.TokenCredentials;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.joda.time.DateTime;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Completable;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.functions.Func2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The implementation for FunctionApp.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
class FunctionAppImpl
    extends AppServiceBaseImpl<FunctionApp, FunctionAppImpl, FunctionApp.DefinitionStages.WithCreate, FunctionApp.Update>
    implements
        FunctionApp,
        FunctionApp.Definition,
        FunctionApp.DefinitionStages.NewAppServicePlanWithGroup,
        FunctionApp.DefinitionStages.ExistingLinuxPlanWithGroup,
        FunctionApp.Update {

    private static final String SETTING_FUNCTIONS_WORKER_RUNTIME = "FUNCTIONS_WORKER_RUNTIME";
    private static final String SETTING_FUNCTIONS_EXTENSION_VERSION = "FUNCTIONS_EXTENSION_VERSION";
    private static final String SETTING_WEBSITE_CONTENTAZUREFILECONNECTIONSTRING = "WEBSITE_CONTENTAZUREFILECONNECTIONSTRING";
    private static final String SETTING_WEBSITE_CONTENTSHARE = "WEBSITE_CONTENTSHARE";
    private static final String SETTING_WEB_JOBS_STORAGE = "AzureWebJobsStorage";
    private static final String SETTING_WEB_JOBS_DASHBOARD = "AzureWebJobsDashboard";

    private Creatable<StorageAccount> storageAccountCreatable;
    private StorageAccount storageAccountToSet;
    private StorageAccount currentStorageAccount;
    private final FunctionAppKeyService functionAppKeyService;
    private FunctionService functionService;
    private FunctionServiceViaKey functionServiceViaKey;
    private String cachedFunctionAppMasterKey;
    private FunctionDeploymentSlots deploymentSlots;

    private Func1<AppServicePlan, Void> linuxFxVersionSetter = null;
    private Observable<AppServicePlan> cachedAppServicePlanObservable = null; // potentially shared between submitSiteConfig and submitAppSettings

    FunctionAppImpl(final String name, SiteInner innerObject, SiteConfigResourceInner siteConfig, SiteLogsConfigInner logConfig, AppServiceManager manager) {
        super(name, innerObject, siteConfig, logConfig, manager);
        functionAppKeyService = manager.restClient().retrofit().create(FunctionAppKeyService.class);
        if (!isInCreateMode()) {
            initializeFunctionService();
        }
    }

    private void initializeFunctionService() {
        if (functionService == null) {
            HttpUrl defaultHostName = HttpUrl.parse(defaultHostName());
            if (defaultHostName == null) {
                defaultHostName = new HttpUrl.Builder().host(defaultHostName()).scheme("http").build();
            }
            functionService = manager().restClient().newBuilder()
                    .withBaseUrl(defaultHostName.toString())
                    .withCredentials(new FunctionCredentials(this))
                    .build()
                    .retrofit().create(FunctionService.class);
            functionServiceViaKey = manager().restClient().newBuilder()
                    .withBaseUrl(defaultHostName.toString())
                    .build()
                    .retrofit().create(FunctionServiceViaKey.class);
        }
    }

    @Override
    public void setInner(SiteInner innerObject) {
        super.setInner(innerObject);
    }

    @Override
    public FunctionDeploymentSlots deploymentSlots() {
        if (deploymentSlots == null) {
            deploymentSlots = new FunctionDeploymentSlotsImpl(this);
        }
        return deploymentSlots;
    }

    @Override
    public FunctionAppImpl withNewConsumptionPlan() {
        return withNewAppServicePlan(OperatingSystem.WINDOWS, new PricingTier(SkuName.DYNAMIC.toString(), "Y1"));
    }

    @Override
    public FunctionAppImpl withNewConsumptionPlan(String appServicePlanName) {
        return withNewAppServicePlan(appServicePlanName, OperatingSystem.WINDOWS, new PricingTier(SkuName.DYNAMIC.toString(), "Y1"));
    }

    @Override
    public FunctionAppImpl withRuntime(String runtime) {
        return withAppSetting(SETTING_FUNCTIONS_WORKER_RUNTIME, runtime);
    }

    @Override
    public FunctionAppImpl withRuntimeVersion(String version) {
        return withAppSetting(SETTING_FUNCTIONS_EXTENSION_VERSION, version.startsWith("~") ? version : "~" + version);
    }

    @Override
    public FunctionAppImpl withLatestRuntimeVersion() {
        return withRuntimeVersion("latest");
    }

    @Override
    Observable<Indexable> submitSiteConfig() {
        if (linuxFxVersionSetter != null) {
            cachedAppServicePlanObservable = this.cachedAppServicePlanObservable(); // first usage, so get a new one
            return cachedAppServicePlanObservable.map(linuxFxVersionSetter)
                    .flatMap(new Func1<Void, Observable<Indexable>>() {
                        @Override
                        public Observable<Indexable> call(Void aVoid) {
                            return FunctionAppImpl.super.submitSiteConfig();
                        }
                    });
        } else {
            return super.submitSiteConfig();
        }
    }

    @Override
    Observable<Indexable> submitAppSettings() {
        if (storageAccountCreatable != null && this.taskResult(storageAccountCreatable.key()) != null) {
            storageAccountToSet = this.<StorageAccount>taskResult(storageAccountCreatable.key());
        }
        if (storageAccountToSet == null) {
            return super.submitAppSettings();
        } else {
            if (cachedAppServicePlanObservable == null) {
                cachedAppServicePlanObservable = this.cachedAppServicePlanObservable();
            }
            return Observable.merge(storageAccountToSet.getKeysAsync()
                .flatMapIterable(new Func1<List<StorageAccountKey>, Iterable<StorageAccountKey>>() {
                    @Override
                    public Iterable<StorageAccountKey> call(List<StorageAccountKey> storageAccountKeys) {
                        return storageAccountKeys;
                    }
                })
                .first().zipWith(cachedAppServicePlanObservable, new Func2<StorageAccountKey, AppServicePlan, Observable<Indexable>>() {
                    @Override
                    public Observable<Indexable> call(StorageAccountKey storageAccountKey, AppServicePlan appServicePlan) {
                        String connectionString = com.microsoft.azure.management.resources.fluentcore.utils.Utils.getStorageConnectionString(
                                storageAccountToSet.name(), storageAccountKey.value(), manager().restClient());
                        addAppSettingIfNotModified(SETTING_WEB_JOBS_STORAGE, connectionString);
                        addAppSettingIfNotModified(SETTING_WEB_JOBS_DASHBOARD, connectionString);
                        if (OperatingSystem.WINDOWS.equals(operatingSystem()) && // as Portal logic, only Windows plan would have following appSettings
                                (appServicePlan == null || isConsumptionOrPremiumAppServicePlan(appServicePlan.pricingTier()))) {
                            addAppSettingIfNotModified(SETTING_WEBSITE_CONTENTAZUREFILECONNECTIONSTRING, connectionString);
                            addAppSettingIfNotModified(SETTING_WEBSITE_CONTENTSHARE, SdkContext.randomResourceName(name(), 32));
                        }
                        return FunctionAppImpl.super.submitAppSettings();
                    }
                })).doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        currentStorageAccount = storageAccountToSet;
                        storageAccountToSet = null;
                        storageAccountCreatable = null;
                        cachedAppServicePlanObservable = null;
                    }
                });
        }
    }

    @Override
    public OperatingSystem operatingSystem() {
        return (inner().reserved() == null || !inner().reserved())
                ? OperatingSystem.WINDOWS
                : OperatingSystem.LINUX;
    }

    private void addAppSettingIfNotModified(String key, String value) {
        if (!appSettingModified(key)) {
            withAppSetting(key, value);
        }
    }

    private boolean appSettingModified(String key) {
        return (appSettingsToAdd != null && appSettingsToAdd.containsKey(key))
                || (appSettingsToRemove != null && appSettingsToRemove.contains(key));
    }

    private static boolean isConsumptionOrPremiumAppServicePlan(PricingTier pricingTier) {
        if (pricingTier == null || pricingTier.toSkuDescription() == null) {
            return true;
        }
        SkuDescription description = pricingTier.toSkuDescription();
        return SkuName.DYNAMIC.toString().equalsIgnoreCase(description.tier()) || SkuName.ELASTIC_PREMIUM.toString().equalsIgnoreCase(description.tier());
    }

    private static boolean isConsumptionPlan(PricingTier pricingTier) {
        if (pricingTier == null || pricingTier.toSkuDescription() == null) {
            return true;
        }
        SkuDescription description = pricingTier.toSkuDescription();
        return SkuName.DYNAMIC.toString().equalsIgnoreCase(description.tier());
    }

    @Override
    FunctionAppImpl withNewAppServicePlan(OperatingSystem operatingSystem, PricingTier pricingTier) {
        return super.withNewAppServicePlan(operatingSystem, pricingTier).autoSetAlwaysOn(pricingTier);
    }

    @Override
    FunctionAppImpl withNewAppServicePlan(String appServicePlan, OperatingSystem operatingSystem, PricingTier pricingTier) {
        return super.withNewAppServicePlan(appServicePlan, operatingSystem, pricingTier).autoSetAlwaysOn(pricingTier);
    }

    @Override
    public FunctionAppImpl withExistingAppServicePlan(AppServicePlan appServicePlan) {
        super.withExistingAppServicePlan(appServicePlan);
        return autoSetAlwaysOn(appServicePlan.pricingTier());
    }

    private FunctionAppImpl autoSetAlwaysOn(PricingTier pricingTier) {
        SkuDescription description = pricingTier.toSkuDescription();
        if (description.tier().equalsIgnoreCase(SkuName.BASIC.toString())
                || description.tier().equalsIgnoreCase(SkuName.STANDARD.toString())
                || description.tier().equalsIgnoreCase(SkuName.PREMIUM.toString())
                || description.tier().equalsIgnoreCase(SkuName.PREMIUM_V2.toString())) {
            return withWebAppAlwaysOn(true);
        } else {
            return withWebAppAlwaysOn(false);
        }
    }

    @Override
    public FunctionAppImpl withNewStorageAccount(String name, com.microsoft.azure.management.storage.SkuName sku) {
        StorageAccount.DefinitionStages.WithGroup storageDefine = manager().storageManager().storageAccounts()
            .define(name)
            .withRegion(regionName());
        if (super.creatableGroup != null && isInCreateMode()) {
            storageAccountCreatable = storageDefine.withNewResourceGroup(super.creatableGroup)
                .withGeneralPurposeAccountKindV2()
                .withSku(sku);
        } else {
            storageAccountCreatable = storageDefine.withExistingResourceGroup(resourceGroupName())
                .withGeneralPurposeAccountKindV2()
                .withSku(sku);
        }
        this.addDependency(storageAccountCreatable);
        return this;
    }

    @Override
    public FunctionAppImpl withNewStorageAccount(String name, StorageAccountSkuType sku) {
        StorageAccount.DefinitionStages.WithGroup storageDefine = manager().storageManager().storageAccounts()
                .define(name)
                .withRegion(regionName());
        if (super.creatableGroup != null && isInCreateMode()) {
            storageAccountCreatable = storageDefine.withNewResourceGroup(super.creatableGroup)
                    .withGeneralPurposeAccountKindV2()
                    .withSku(sku);
        } else {
            storageAccountCreatable = storageDefine.withExistingResourceGroup(resourceGroupName())
                    .withGeneralPurposeAccountKindV2()
                    .withSku(sku);
        }
        this.addDependency(storageAccountCreatable);
        return this;
    }

    @Override
    public FunctionAppImpl withExistingStorageAccount(StorageAccount storageAccount) {
        this.storageAccountToSet = storageAccount;
        return this;
    }

    @Override
    public FunctionAppImpl withDailyUsageQuota(int quota) {
        inner().withDailyMemoryTimeQuota(quota);
        return this;
    }

    @Override
    public FunctionAppImpl withoutDailyUsageQuota() {
        return withDailyUsageQuota(0);
    }

    @Override
    public FunctionAppImpl withNewLinuxConsumptionPlan() {
        return withNewAppServicePlan(OperatingSystem.LINUX, new PricingTier(SkuName.DYNAMIC.toString(), "Y1"));
    }

    @Override
    public FunctionAppImpl withNewLinuxConsumptionPlan(String appServicePlanName) {
        return withNewAppServicePlan(appServicePlanName, OperatingSystem.LINUX, new PricingTier(SkuName.DYNAMIC.toString(), "Y1"));
    }

    @Override
    public FunctionAppImpl withNewLinuxAppServicePlan(PricingTier pricingTier) {
        return super.withNewAppServicePlan(OperatingSystem.LINUX, pricingTier);
    }

    @Override
    public FunctionAppImpl withNewLinuxAppServicePlan(String appServicePlanName, PricingTier pricingTier) {
        return super.withNewAppServicePlan(appServicePlanName, OperatingSystem.LINUX, pricingTier);
    }

    @Override
    public FunctionAppImpl withNewLinuxAppServicePlan(Creatable<AppServicePlan> appServicePlanCreatable) {
        super.withNewAppServicePlan(appServicePlanCreatable);
        if (appServicePlanCreatable instanceof AppServicePlan) {
            this.autoSetAlwaysOn(((AppServicePlan) appServicePlanCreatable).pricingTier());
        }
        return this;
    }

    @Override
    public FunctionAppImpl withExistingLinuxAppServicePlan(AppServicePlan appServicePlan) {
        return super.withExistingAppServicePlan(appServicePlan).autoSetAlwaysOn(appServicePlan.pricingTier());
    }

    @Override
    public FunctionAppImpl withBuiltInImage(final FunctionRuntimeStack runtimeStack) {
        ensureLinuxPlan();
        cleanUpContainerSettings();
        if (siteConfig == null) {
            siteConfig = new SiteConfigResourceInner();
        }
        withRuntime(runtimeStack.runtime());
        withRuntimeVersion(runtimeStack.version());
        linuxFxVersionSetter = new Func1<AppServicePlan, Void>() {
            @Override
            public Void call(AppServicePlan appServicePlan) {
                if (appServicePlan == null || isConsumptionPlan(appServicePlan.pricingTier())) {
                    siteConfig.withLinuxFxVersion(runtimeStack.getLinuxFxVersionForConsumptionPlan());
                } else {
                    siteConfig.withLinuxFxVersion(runtimeStack.getLinuxFxVersionForDedicatedPlan());
                }
                return null;
            }
        };
        return this;
    }

    @Override
    public FunctionAppImpl withPublicDockerHubImage(String imageAndTag) {
        ensureLinuxPlan();
        return super.withPublicDockerHubImage(imageAndTag);
    }

    @Override
    public FunctionAppImpl withPrivateDockerHubImage(String imageAndTag) {
        ensureLinuxPlan();
        return super.withPublicDockerHubImage(imageAndTag);
    }

    @Override
    public FunctionAppImpl withPrivateRegistryImage(String imageAndTag, String serverUrl) {
        ensureLinuxPlan();
        return super.withPrivateRegistryImage(imageAndTag, serverUrl);
    }

    @Override
    protected void cleanUpContainerSettings() {
        linuxFxVersionSetter = null;
        if (siteConfig != null && siteConfig.linuxFxVersion() != null) {
            siteConfig.withLinuxFxVersion(null);
        }
        // Docker Hub
        withoutAppSetting(SETTING_DOCKER_IMAGE);
        withoutAppSetting(SETTING_REGISTRY_SERVER);
        withoutAppSetting(SETTING_REGISTRY_USERNAME);
        withoutAppSetting(SETTING_REGISTRY_PASSWORD);
    }

    @Override
    protected OperatingSystem appServicePlanOperatingSystem(AppServicePlan appServicePlan) {
        // Consumption plan or premium (elastic) plan would have "functionapp" or "elastic" in "kind" property, no "linux" in it.
        return (appServicePlan.inner().reserved() == null || !appServicePlan.inner().reserved())
                ? OperatingSystem.WINDOWS
                : OperatingSystem.LINUX;
    }

    private Observable<AppServicePlan> cachedAppServicePlanObservable() {
        // it could get more than one subscriber, so hot observable + caching
        return this.manager().appServicePlans().getByIdAsync(this.appServicePlanId()).cacheWithInitialCapacity(1);
    }

    @Override
    public StorageAccount storageAccount() {
        return currentStorageAccount;
    }

    @Override
    public String getMasterKey() {
        return getMasterKeyAsync().toBlocking().single();
    }

    @Override
    public Observable<String> getMasterKeyAsync() {
        return functionAppKeyService.getMasterKey(resourceGroupName(), name(), manager().subscriptionId(), "2019-08-01", manager().inner().userAgent())
                .map(new Func1<ListKeysResult, String>() {
                    @Override
                    public String call(ListKeysResult keys) {
                        return keys.getMasterKey();
                    }
                });
    }

    @Override
    public Map<String, String> listFunctionKeys(String functionName) {
        return listFunctionKeysAsync(functionName).toBlocking().single();
    }

    @Override
    public Observable<Map<String, String>> listFunctionKeysAsync(final String functionName) {
        return functionService.listFunctionKeys(functionName)
                .map(new Func1<FunctionKeyListResult, Map<String, String>>() {
                    @Override
                    public Map<String, String> call(FunctionKeyListResult result) {
                        Map<String, String> keys = new HashMap<String, String>();
                        if (result.keys != null) {
                            for (NameValuePair pair : result.keys) {
                                keys.put(pair.name(), pair.value());
                            }
                        }
                        return keys;
                    }
                });
    }

    @Override
    public NameValuePair addFunctionKey(String functionName, String keyName, String keyValue) {
        return addFunctionKeyAsync(functionName, keyName, keyValue).toBlocking().single();
    }

    @Override
    public Observable<NameValuePair> addFunctionKeyAsync(String functionName, String keyName, String keyValue) {
        if (keyValue != null) {
            return functionService.addFunctionKey(functionName, keyName, new NameValuePair().withName(keyName).withValue(keyValue));
        } else {
            return functionService.generateFunctionKey(functionName, keyName);
        }
    }

    @Override
    public void removeFunctionKey(String functionName, String keyName) {
        removeFunctionKeyAsync(functionName, keyName).toObservable().toBlocking().subscribe();
    }

    @Override
    public Completable removeFunctionKeyAsync(String functionName, String keyName) {
        return functionService.deleteFunctionKey(functionName, keyName).toCompletable();
    }

    @Override
    public void triggerFunction(String functionName, Object payload) {
        triggerFunctionAsync(functionName, payload).toObservable().toBlocking().subscribe();
    }

    @Override
    public Completable triggerFunctionAsync(final String functionName, final Object payload) {
        return getCachedMasterKey().flatMap(new Func1<String, Observable<Void>>() {
            @Override
            public Observable<Void> call(String s) {
                return functionServiceViaKey.triggerFunction(s, functionName, payload);
            }
        }).toCompletable();
    }

    private Observable<String> getCachedMasterKey() {
        if (cachedFunctionAppMasterKey != null) {
            return Observable.just(cachedFunctionAppMasterKey);
        } else {
            return this.getMasterKeyAsync().map(new Func1<String, String>() {
                @Override
                public String call(String s) {
                    cachedFunctionAppMasterKey = s;
                    return s;
                }
            });
        }
    }

    @Override
    public void syncTriggers() {
        syncTriggersAsync().toObservable().toBlocking().subscribe();
    }

    @Override
    public Completable syncTriggersAsync() {
        return manager().inner().webApps().syncFunctionTriggersAsync(resourceGroupName(), name())
                .toCompletable()
                .onErrorResumeNext(new Func1<Throwable, Completable>() {
                    @Override
                    public Completable call(Throwable throwable) {
                        if (throwable instanceof CloudException && ((CloudException) throwable).response().code() == 200) {
                            return Completable.complete();
                        } else {
                            return Completable.error(throwable);
                        }
                    }
                });
    }

    @Override
    public Observable<String> streamApplicationLogsAsync() {
        return functionService.ping()
                .mergeWith(functionService.getHostStatus())
                .last()
                .flatMap(new Func1<Void, Observable<String>>() {
                    @Override
                    public Observable<String> call(Void aVoid) {
                        return FunctionAppImpl.super.streamApplicationLogsAsync();
                    }
                });
    }

    @Override
    public Observable<String> streamHttpLogsAsync() {
        return functionService.ping()
                .mergeWith(functionService.getHostStatus())
                .last()
                .flatMap(new Func1<Void, Observable<String>>() {
                    @Override
                    public Observable<String> call(Void aVoid) {
                        return FunctionAppImpl.super.streamHttpLogsAsync();
                    }
                });
    }

    @Override
    public Observable<String> streamTraceLogsAsync() {
        return functionService.ping()
                .mergeWith(functionService.getHostStatus())
                .last()
                .flatMap(new Func1<Void, Observable<String>>() {
                    @Override
                    public Observable<String> call(Void aVoid) {
                        return FunctionAppImpl.super.streamTraceLogsAsync();
                    }
                });
    }

    @Override
    public Observable<String> streamDeploymentLogsAsync() {
        return functionService.ping()
                .mergeWith(functionService.getHostStatus())
                .last()
                .flatMap(new Func1<Void, Observable<String>>() {
                    @Override
                    public Observable<String> call(Void aVoid) {
                        return FunctionAppImpl.super.streamDeploymentLogsAsync();
                    }
                });
    }

    @Override
    public Observable<String> streamAllLogsAsync() {
        return functionService.ping()
                .mergeWith(functionService.getHostStatus())
                .last()
                .flatMap(new Func1<Void, Observable<String>>() {
                    @Override
                    public Observable<String> call(Void aVoid) {
                        return FunctionAppImpl.super.streamAllLogsAsync();
                    }
                });
    }

    @Override
    public Completable zipDeployAsync(File zipFile) {
        try {
            final InputStream is = new FileInputStream(zipFile);
            return zipDeployAsync(new FileInputStream(zipFile)).doAfterTerminate(new Action0() {
                @Override
                public void call() {
                    try {
                        is.close();
                    } catch (IOException e) {
                        Exceptions.propagate(e);
                    }
                }
            });
        } catch (IOException e) {
            return Completable.error(e);
        }
    }

    @Override
    public void zipDeploy(File zipFile) {
        zipDeployAsync(zipFile).await();
    }

    @Override
    public Completable zipDeployAsync(InputStream zipFile) {
        return kuduClient.zipDeployAsync(zipFile);
    }

    @Override
    public void zipDeploy(InputStream zipFile) {
        zipDeployAsync(zipFile).await();
    }

    @Override
    public Observable<Indexable> createAsync() {
        if (this.isInCreateMode()) {
            if (inner().serverFarmId() == null) {
                withNewConsumptionPlan();
            }
            if (currentStorageAccount == null && storageAccountToSet == null && storageAccountCreatable == null) {
                withNewStorageAccount(SdkContext.randomResourceName(getStorageAccountName(), 20), StorageAccountSkuType.STANDARD_LRS);
            }
        }
        return super.createAsync();
    }

    @Override
    public Completable afterPostRunAsync(final boolean isGroupFaulted) {
        if (!isGroupFaulted) {
            initializeFunctionService();
        }
        return super.afterPostRunAsync(isGroupFaulted);
    }

    private String getStorageAccountName() {
        return name().replaceAll("[^a-zA-Z0-9]", "");
    }

    private static class ListKeysResult {
        @JsonProperty("masterKey")
        private String masterKey;

        @JsonProperty("functionKeys")
        private Map<String, String> functionKeys;

        @JsonProperty("systemKeys")
        private Map<String, String> systemKeys;

        public String getMasterKey() {
            return masterKey;
        }
    }

    private interface FunctionAppKeyService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps getMasterKey" })
        @POST("subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Web/sites/{name}/host/default/listkeys")
        Observable<ListKeysResult> getMasterKey(@Path("resourceGroupName") String resourceGroupName, @Path("name") String name, @Path("subscriptionId") String subscriptionId, @Query("api-version") String apiVersion, @Header("User-Agent") String userAgent);
    }

    private interface FunctionService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps listFunctionKeys" })
        @GET("admin/functions/{name}/keys")
        Observable<FunctionKeyListResult> listFunctionKeys(@Path("name") String functionName);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps addFunctionKey" })
        @PUT("admin/functions/{name}/keys/{keyName}")
        Observable<NameValuePair> addFunctionKey(@Path("name") String functionName, @Path("keyName") String keyName, @Body NameValuePair key);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps generateFunctionKey" })
        @POST("admin/functions/{name}/keys/{keyName}")
        Observable<NameValuePair> generateFunctionKey(@Path("name") String functionName, @Path("keyName") String keyName);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps deleteFunctionKey" })
        @DELETE("admin/functions/{name}/keys/{keyName}")
        Observable<Void> deleteFunctionKey(@Path("name") String functionName, @Path("keyName") String keyName);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps ping" })
        @POST("admin/host/ping")
        Observable<Void> ping();

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps getHostStatus" })
        @GET("admin/host/status")
        Observable<Void> getHostStatus();
    }

    private interface FunctionServiceViaKey {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.appservice.WebApps triggerFunction" })
        @POST("admin/functions/{name}")
        Observable<Void> triggerFunction(@Header("x-functions-key") String key, @Path("name") String functionName, @Body Object payload);
    }

    private static class FunctionKeyListResult {
        @JsonProperty("keys")
        private List<NameValuePair> keys;
    }

    private static final class FunctionCredentials extends TokenCredentials {
        private String token;
        private long expire;
        private final FunctionAppImpl functionApp;

        private FunctionCredentials(FunctionAppImpl functionApp) {
            super("Bearer", null);
            this.functionApp = functionApp;
        }

        @Override
        public String getToken(Request request) {
            if (token == null || expire < DateTime.now().getMillis()) {
                token = functionApp.manager().inner().webApps()
                        .getFunctionsAdminToken(functionApp.resourceGroupName(), functionApp.name());
                String jwt = new String(BaseEncoding.base64Url().decode(token.split("\\.")[1]));
                Pattern pattern = Pattern.compile("\"exp\": *([0-9]+),");
                Matcher matcher = pattern.matcher(jwt);
                matcher.find();
                expire = Long.parseLong(matcher.group(1));
            }
            return token;
        }
    }
}
