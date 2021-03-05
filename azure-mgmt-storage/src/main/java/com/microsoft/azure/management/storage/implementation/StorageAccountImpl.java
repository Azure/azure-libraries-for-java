/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.azure.management.storage.AccessTier;
import com.microsoft.azure.management.storage.AzureFilesIdentityBasedAuthentication;
import com.microsoft.azure.management.storage.CustomDomain;
import com.microsoft.azure.management.storage.DirectoryServiceOptions;
import com.microsoft.azure.management.storage.Encryption;
import com.microsoft.azure.management.storage.Identity;
import com.microsoft.azure.management.storage.Kind;
import com.microsoft.azure.management.storage.LargeFileSharesState;
import com.microsoft.azure.management.storage.MinimumTlsVersion;
import com.microsoft.azure.management.storage.ProvisioningState;
import com.microsoft.azure.management.storage.PublicEndpoints;
import com.microsoft.azure.management.storage.Sku;
import com.microsoft.azure.management.storage.SkuName;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.StorageAccountCreateParameters;
import com.microsoft.azure.management.storage.StorageAccountEncryptionKeySource;
import com.microsoft.azure.management.storage.StorageAccountEncryptionStatus;
import com.microsoft.azure.management.storage.StorageAccountKey;
import com.microsoft.azure.management.storage.StorageAccountSkuType;
import com.microsoft.azure.management.storage.StorageAccountUpdateParameters;
import com.microsoft.azure.management.storage.StorageService;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import org.joda.time.DateTime;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.util.List;
import java.util.Map;

/**
 * Implementation for {@link StorageAccount}.
 */
@LangDefinition
class StorageAccountImpl
        extends GroupableResourceImpl<
        StorageAccount,
        StorageAccountInner,
        StorageAccountImpl,
        StorageManager>
        implements
        StorageAccount,
        StorageAccount.Definition,
        StorageAccount.Update {

    private PublicEndpoints publicEndpoints;
    private AccountStatuses accountStatuses;
    private StorageAccountCreateParameters createParameters;
    private StorageAccountUpdateParameters updateParameters;
    private StorageNetworkRulesHelper networkRulesHelper;
    private StorageEncryptionHelper encryptionHelper;

    StorageAccountImpl(String name,
                       StorageAccountInner innerModel,
                       final StorageManager storageManager) {
        super(name, innerModel, storageManager);
        this.createParameters = new StorageAccountCreateParameters();
        this.networkRulesHelper = new StorageNetworkRulesHelper(this.createParameters);
        this.encryptionHelper = new StorageEncryptionHelper(this.createParameters);
    }

    @Override
    public AccountStatuses accountStatuses() {
        if (accountStatuses == null) {
            accountStatuses = new AccountStatuses(this.inner().statusOfPrimary(), this.inner().statusOfSecondary());
        }
        return accountStatuses;
    }

    @Override
    @Deprecated
    public Sku sku() {
        return new Sku().withName(this.inner().sku().name());
    }

    @Override
    public StorageAccountSkuType skuType() {
        // We deprecated the sku() getter. When we remove it we wanted to rename this
        // 'beta' getter skuType() to sku().
        //
        return StorageAccountSkuType.fromSkuName(this.inner().sku().name());
    }

    @Override
    public Kind kind() {
        return inner().kind();
    }

    @Override
    public DateTime creationTime() {
        return this.inner().creationTime();
    }

    @Override
    public CustomDomain customDomain() {
        return this.inner().customDomain();
    }

    @Override
    public DateTime lastGeoFailoverTime() {
        return this.inner().lastGeoFailoverTime();
    }

    @Override
    public ProvisioningState provisioningState() {
        return this.inner().provisioningState();
    }

    @Override
    public PublicEndpoints endPoints() {
        if (publicEndpoints == null) {
            publicEndpoints = new PublicEndpoints(this.inner().primaryEndpoints(), this.inner().secondaryEndpoints());
        }
        return publicEndpoints;
    }

    @Override
    @Deprecated
    public Encryption encryption() {
        return inner().encryption();
    }

    @Override
    public StorageAccountEncryptionKeySource encryptionKeySource() {
        return StorageEncryptionHelper.encryptionKeySource(this.inner());
    }

    @Override
    public Map<StorageService, StorageAccountEncryptionStatus> encryptionStatuses() {
        return StorageEncryptionHelper.encryptionStatuses(this.inner());
    }

    @Override
    public AccessTier accessTier() {
        return inner().accessTier();
    }

    @Override
    public String systemAssignedManagedServiceIdentityTenantId() {
        if (this.inner().identity() == null) {
            return null;
        } else {
            return this.inner().identity().tenantId();
        }
    }

    @Override
    public String systemAssignedManagedServiceIdentityPrincipalId() {
        if (this.inner().identity() == null) {
            return null;
        } else {
            return this.inner().identity().principalId();
        }
    }

    @Override
    public boolean isAccessAllowedFromAllNetworks() {
        return StorageNetworkRulesHelper.isAccessAllowedFromAllNetworks(this.inner());
    }

    @Override
    public List<String> networkSubnetsWithAccess() {
        return StorageNetworkRulesHelper.networkSubnetsWithAccess(this.inner());
    }

    @Override
    public List<String> ipAddressesWithAccess() {
        return StorageNetworkRulesHelper.ipAddressesWithAccess(this.inner());
    }

    @Override
    public List<String> ipAddressRangesWithAccess() {
        return StorageNetworkRulesHelper.ipAddressRangesWithAccess(this.inner());
    }

    @Override
    public boolean canReadLogEntriesFromAnyNetwork() {
        return StorageNetworkRulesHelper.canReadLogEntriesFromAnyNetwork(this.inner());
    }

    @Override
    public boolean canReadMetricsFromAnyNetwork() {
        return StorageNetworkRulesHelper.canReadMetricsFromAnyNetwork(this.inner());
    }

    @Override
    public boolean canAccessFromAzureServices() {
        return StorageNetworkRulesHelper.canAccessFromAzureServices(this.inner());
    }

    @Override
    public boolean isAzureFilesAadIntegrationEnabled() {
        return this.inner().azureFilesIdentityBasedAuthentication() != null
                && this.inner().azureFilesIdentityBasedAuthentication().directoryServiceOptions() == DirectoryServiceOptions.AADDS;
    }

    @Override
    public boolean isHnsEnabled() {
        return Utils.toPrimitiveBoolean(this.inner().isHnsEnabled());
    }

    @Override
    public boolean isLargeFileSharesEnabled() {
        return this.inner().largeFileSharesState() == LargeFileSharesState.ENABLED;
    }

    @Override
    public MinimumTlsVersion minimalTlsVersion() {
        return this.inner().minimumTlsVersion();
    }

    @Override
    public boolean isHttpsTrafficOnly() {
        return Utils.toPrimitiveBoolean(this.inner().enableHttpsTrafficOnly());
    }

    @Override
    public List<StorageAccountKey> getKeys() {
        return this.getKeysAsync().toBlocking().last();
    }

    @Override
    public Observable<List<StorageAccountKey>> getKeysAsync() {
        return this.manager().inner().storageAccounts().listKeysAsync(
                this.resourceGroupName(), this.name()).map(new Func1<StorageAccountListKeysResultInner, List<StorageAccountKey>>() {
            @Override
            public List<StorageAccountKey> call(StorageAccountListKeysResultInner storageAccountListKeysResultInner) {
                return storageAccountListKeysResultInner.keys();
            }
        });
    }

    @Override
    public ServiceFuture<List<StorageAccountKey>> getKeysAsync(ServiceCallback<List<StorageAccountKey>> callback) {
        return ServiceFuture.fromBody(this.getKeysAsync(), callback);
    }

    @Override
    public List<StorageAccountKey> regenerateKey(String keyName) {
        return this.regenerateKeyAsync(keyName).toBlocking().last();
    }

    @Override
    public Observable<List<StorageAccountKey>> regenerateKeyAsync(String keyName) {
        return this.manager().inner().storageAccounts().regenerateKeyAsync(
                this.resourceGroupName(), this.name(), keyName).map(new Func1<StorageAccountListKeysResultInner, List<StorageAccountKey>>() {
            @Override
            public List<StorageAccountKey> call(StorageAccountListKeysResultInner storageAccountListKeysResultInner) {
                return storageAccountListKeysResultInner.keys();
            }
        });
    }

    @Override
    public ServiceFuture<List<StorageAccountKey>> regenerateKeyAsync(String keyName, ServiceCallback<List<StorageAccountKey>> callback) {
        return ServiceFuture.fromBody(this.regenerateKeyAsync(keyName), callback);
    }

    @Override
    public Observable<StorageAccount> refreshAsync() {
        return super.refreshAsync().map(new Func1<StorageAccount, StorageAccount>() {
            @Override
            public StorageAccount call(StorageAccount storageAccount) {
                StorageAccountImpl impl = (StorageAccountImpl) storageAccount;
                impl.clearWrapperProperties();
                return impl;
            }
        });
    }

    @Override
    protected Observable<StorageAccountInner> getInnerAsync() {
        return this.manager().inner().storageAccounts().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    @Deprecated
    public StorageAccountImpl withSku(SkuName skuName) {
        return withSku(StorageAccountSkuType.fromSkuName(skuName));
    }

    @Override
    public StorageAccountImpl withSku(StorageAccountSkuType sku) {
        if (isInCreateMode()) {
            createParameters.withSku(new SkuInner().withName(sku.name()));
        } else {
            updateParameters.withSku(new SkuInner().withName(sku.name()));
        }
        return this;
    }

    @Override
    public StorageAccountImpl withBlobStorageAccountKind() {
        createParameters.withKind(Kind.BLOB_STORAGE);
        return this;
    }

    @Override
    public StorageAccountImpl withGeneralPurposeAccountKind() {
        createParameters.withKind(Kind.STORAGE);
        return this;
    }

    @Override
    public StorageAccountImpl withGeneralPurposeAccountKindV2() {
        createParameters.withKind(Kind.STORAGE_V2);
        return this;
    }

    @Override
    public StorageAccountImpl withBlockBlobStorageAccountKind() {
        createParameters.withKind(Kind.BLOCK_BLOB_STORAGE);
        return this;
    }

    @Override
    public StorageAccountImpl withFileStorageAccountKind() {
        createParameters.withKind(Kind.FILE_STORAGE);
        return this;
    }

    @Override
    @Deprecated
    public StorageAccountImpl withEncryption() {
        return withBlobEncryption();
    }

    @Override
    public StorageAccountImpl withBlobEncryption() {
        this.encryptionHelper.withBlobEncryption();
        return this;
    }

    @Override
    public StorageAccountImpl withFileEncryption() {
        this.encryptionHelper.withFileEncryption();
        return this;
    }

    @Override
    public StorageAccountImpl withEncryptionKeyFromKeyVault(String keyVaultUri, String keyName, String keyVersion) {
        this.encryptionHelper.withEncryptionKeyFromKeyVault(keyVaultUri, keyName, keyVersion);
        return this;
    }

    @Override
    @Deprecated
    public StorageAccountImpl withoutEncryption() {
        return withoutBlobEncryption();
    }

    @Override
    public StorageAccountImpl withoutBlobEncryption() {
        this.encryptionHelper.withoutBlobEncryption();
        return this;
    }

    @Override
    public StorageAccountImpl withoutFileEncryption() {
        this.encryptionHelper.withoutFileEncryption();
        return this;
    }

    private void clearWrapperProperties() {
        accountStatuses = null;
        publicEndpoints = null;
    }

    @Override
    public StorageAccountImpl update() {
        createParameters = null;
        updateParameters = new StorageAccountUpdateParameters();
        this.networkRulesHelper = new StorageNetworkRulesHelper(this.updateParameters, this.inner());
        this.encryptionHelper = new StorageEncryptionHelper(this.updateParameters, this.inner());
        return super.update();
    }

    @Override
    public StorageAccountImpl withCustomDomain(CustomDomain customDomain) {
        if (isInCreateMode()) {
            createParameters.withCustomDomain(customDomain);
        } else {
            updateParameters.withCustomDomain(customDomain);
        }
        return this;
    }

    @Override
    public StorageAccountImpl withCustomDomain(String name) {
        return withCustomDomain(new CustomDomain().withName(name));
    }

    @Override
    public StorageAccountImpl withCustomDomain(String name, boolean useSubDomain) {
        return withCustomDomain(new CustomDomain().withName(name).withUseSubDomainName(useSubDomain));
    }

    @Override
    public StorageAccountImpl withAccessTier(AccessTier accessTier) {
        if (isInCreateMode()) {
            createParameters.withAccessTier(accessTier);
        } else {
            if (this.inner().kind() != Kind.BLOB_STORAGE) {
                throw new UnsupportedOperationException("Access tier can not be changed for general purpose storage accounts.");
            }
            updateParameters.withAccessTier(accessTier);
        }
        return this;
    }

    @Override
    public StorageAccountImpl withSystemAssignedManagedServiceIdentity() {
        if (this.inner().identity() == null) {
            if (isInCreateMode()) {
                createParameters.withIdentity(new Identity().withType("SystemAssigned"));
            } else {
                updateParameters.withIdentity(new Identity().withType("SystemAssigned"));
            }
        }
        return this;
    }

    @Override
    public StorageAccountImpl withOnlyHttpsTraffic() {
        if (isInCreateMode()) {
            createParameters.withEnableHttpsTrafficOnly(true);
        } else {
            updateParameters.withEnableHttpsTrafficOnly(true);
        }
        return this;
    }

    @Override
    public StorageAccountImpl withHttpAndHttpsTraffic() {
        if (isInCreateMode()) {
            createParameters.withEnableHttpsTrafficOnly(false);
        } else {
            updateParameters.withEnableHttpsTrafficOnly(false);
        }
        return this;
    }

    @Override
    public StorageAccountImpl withMinimalTlsVersion(MinimumTlsVersion minimalTlsVersion) {
        if (isInCreateMode()) {
            createParameters.withMinimumTlsVersion(minimalTlsVersion);
        } else {
            updateParameters.withMinimumTlsVersion(minimalTlsVersion);
        }
        return this;
    }

    @Override
    public StorageAccountImpl withAccessFromAllNetworks() {
        this.networkRulesHelper.withAccessFromAllNetworks();
        return this;
    }

    @Override
    public StorageAccountImpl withAccessFromSelectedNetworks() {
        this.networkRulesHelper.withAccessFromSelectedNetworks();
        return this;
    }

    @Override
    public StorageAccountImpl withAccessFromNetworkSubnet(String subnetId) {
        this.networkRulesHelper.withAccessFromNetworkSubnet(subnetId);
        return this;
    }

    @Override
    public StorageAccountImpl withAccessFromIpAddress(String ipAddress) {
        this.networkRulesHelper.withAccessFromIpAddress(ipAddress);
        return this;
    }

    @Override
    public StorageAccountImpl withAccessFromIpAddressRange(String ipAddressCidr) {
        this.networkRulesHelper.withAccessFromIpAddressRange(ipAddressCidr);
        return this;
    }

    @Override
    public StorageAccountImpl withReadAccessToLogEntriesFromAnyNetwork() {
        this.networkRulesHelper.withReadAccessToLoggingFromAnyNetwork();
        return this;
    }

    @Override
    public StorageAccountImpl withReadAccessToMetricsFromAnyNetwork() {
        this.networkRulesHelper.withReadAccessToMetricsFromAnyNetwork();
        return this;
    }

    @Override
    public StorageAccountImpl withAccessFromAzureServices() {
        this.networkRulesHelper.withAccessAllowedFromAzureServices();
        return this;
    }

    @Override
    public StorageAccountImpl withoutNetworkSubnetAccess(String subnetId) {
        this.networkRulesHelper.withoutNetworkSubnetAccess(subnetId);
        return this;
    }

    @Override
    public StorageAccountImpl withoutIpAddressAccess(String ipAddress) {
        this.networkRulesHelper.withoutIpAddressAccess(ipAddress);
        return this;
    }

    @Override
    public StorageAccountImpl withoutIpAddressRangeAccess(String ipAddressCidr) {
        this.networkRulesHelper.withoutIpAddressRangeAccess(ipAddressCidr);
        return this;
    }

    @Override
    public Update withoutReadAccessToLoggingFromAnyNetwork() {
        this.networkRulesHelper.withoutReadAccessToLoggingFromAnyNetwork();
        return this;
    }

    @Override
    public Update withoutReadAccessToMetricsFromAnyNetwork() {
        this.networkRulesHelper.withoutReadAccessToMetricsFromAnyNetwork();
        return this;
    }

    @Override
    public Update withoutAccessFromAzureServices() {
        this.networkRulesHelper.withoutAccessFromAzureServices();
        return this;
    }

    @Override
    public Update upgradeToGeneralPurposeAccountKindV2() {
        updateParameters.withKind(Kind.STORAGE_V2);
        return this;
    }

    // CreateUpdateTaskGroup.ResourceCreator implementation
    @Override
    public Observable<StorageAccount> createResourceAsync() {
        this.networkRulesHelper.setDefaultActionIfRequired();
        createParameters.withLocation(this.regionName());
        createParameters.withTags(this.inner().getTags());
        final StorageAccountsInner client = this.manager().inner().storageAccounts();
        return this.manager().inner().storageAccounts().createAsync(
                this.resourceGroupName(), this.name(), createParameters)
                .flatMap(new Func1<StorageAccountInner, Observable<StorageAccountInner>>() {
                    @Override
                    public Observable<StorageAccountInner> call(StorageAccountInner storageAccountInner) {
                        return client.getByResourceGroupAsync(resourceGroupName(), name());
                    }
                })
                .map(innerToFluentMap(this))
                .doOnNext(new Action1<StorageAccount>() {
                    @Override
                    public void call(StorageAccount storageAccount) {
                        clearWrapperProperties();
                    }
                });
    }

    @Override
    public Observable<StorageAccount> updateResourceAsync() {
        this.networkRulesHelper.setDefaultActionIfRequired();
        updateParameters.withTags(this.inner().getTags());
        return this.manager().inner().storageAccounts().updateAsync(
                resourceGroupName(), name(), updateParameters)
                .map(innerToFluentMap(this))
                .doOnNext(new Action1<StorageAccount>() {
                    @Override
                    public void call(StorageAccount storageAccount) {
                        clearWrapperProperties();
                    }
                });
    }

    @Override
    public StorageAccountImpl withAzureFilesAadIntegrationEnabled(boolean enabled) {
        if (isInCreateMode()) {
            if (enabled) {
                this.createParameters.withAzureFilesIdentityBasedAuthentication(new AzureFilesIdentityBasedAuthentication().withDirectoryServiceOptions(DirectoryServiceOptions.AADDS));
            }
        } else {
            if (this.createParameters.azureFilesIdentityBasedAuthentication() == null) {
                this.createParameters.withAzureFilesIdentityBasedAuthentication(new AzureFilesIdentityBasedAuthentication());
            }
            if (enabled) {
                this.updateParameters.azureFilesIdentityBasedAuthentication().withDirectoryServiceOptions(DirectoryServiceOptions.AADDS);
            } else {
                this.updateParameters.azureFilesIdentityBasedAuthentication().withDirectoryServiceOptions(DirectoryServiceOptions.NONE);
            }
        }
        return this;
    }

    @Override
    public StorageAccountImpl withLargeFileShares(boolean enabled) {
        if (isInCreateMode()) {
            if (enabled) {
                this.createParameters.withLargeFileSharesState(LargeFileSharesState.ENABLED);
            } else {
                this.createParameters.withLargeFileSharesState(LargeFileSharesState.DISABLED);
            }
        }
        return this;
    }

    @Override
    public StorageAccountImpl withHnsEnabled(boolean enabled) {
        this.createParameters.withIsHnsEnabled(enabled);
        return this;
    }
}