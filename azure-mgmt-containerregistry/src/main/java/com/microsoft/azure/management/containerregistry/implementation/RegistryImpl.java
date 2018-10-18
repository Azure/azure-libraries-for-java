/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.AccessKeyType;
//import com.microsoft.azure.management.containerregistry.BuildTaskOperations;
//import com.microsoft.azure.management.containerregistry.QueuedBuildOperations;
import com.microsoft.azure.management.containerregistry.Registry;
import com.microsoft.azure.management.containerregistry.RegistryCredentials;
import com.microsoft.azure.management.containerregistry.RegistryUpdateParameters;
import com.microsoft.azure.management.containerregistry.RegistryUsage;
import com.microsoft.azure.management.containerregistry.Sku;
import com.microsoft.azure.management.containerregistry.SkuName;
import com.microsoft.azure.management.containerregistry.SourceUploadDefinition;
import com.microsoft.azure.management.containerregistry.StorageAccountProperties;
import com.microsoft.azure.management.containerregistry.WebhookOperations;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.storage.StorageAccount;
import com.microsoft.azure.management.storage.implementation.StorageManager;
import org.joda.time.DateTime;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.Collection;

/**
 * Implementation for Registry and its create and update interfaces.
 */
@LangDefinition
public class RegistryImpl
        extends GroupableResourceImpl<
                                    Registry,
                                    RegistryInner,
                                    RegistryImpl,
                                    ContainerRegistryManager>
        implements Registry,
        Registry.Definition,
        Registry.Update {

    private RegistryUpdateParameters updateParameters;
    private final StorageManager storageManager;
    private String storageAccountId;
    private String creatableStorageAccountKey;
    private WebhooksImpl webhooks;
    //private QueuedBuildOperations queuedBuilds;
    //private BuildTaskOperations buildTasks;

    protected RegistryImpl(String name, RegistryInner innerObject, ContainerRegistryManager manager,
                           final StorageManager storageManager) {
        super(name, innerObject, manager);
        this.storageManager = storageManager;

        this.storageAccountId = null;
        this.webhooks = new WebhooksImpl(this, "Webhook");
    }

    @Override
    protected Observable<RegistryInner> getInnerAsync() {
        return this.manager().inner().registries().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public RegistryImpl update() {
        updateParameters = new RegistryUpdateParameters();
        return super.update();
    }

    @Override
    public Observable<Registry> createResourceAsync() {
        final RegistryImpl self = this;
        if (isInCreateMode()) {
            if (self.creatableStorageAccountKey != null) {
                StorageAccount storageAccount = self.<StorageAccount>taskResult(this.creatableStorageAccountKey);
                self.inner().storageAccount().withId(storageAccount.id());
            } else if (storageAccountId != null) {
                self.inner().storageAccount().withId(storageAccountId);
            }

            return manager().inner().registries().createAsync(self.resourceGroupName(), self.name(), self.inner())
                .map(innerToFluentMap(this));
        } else {
            updateParameters.withTags(inner().getTags());
            return manager().inner().registries().updateAsync(self.resourceGroupName(), self.name(), self.updateParameters)
                .map(innerToFluentMap(this));
        }
    }

    @Override
    public Completable afterPostRunAsync(boolean isGroupFaulted) {
        this.webhooks.clear();
        return Completable.complete();
    }


    @Override
    public Sku sku() {
        return this.inner().sku();
    }

    @Override
    public String loginServerUrl() {
        return this.inner().loginServer();
    }

    @Override
    public DateTime creationDate() {
        return this.inner().creationDate();
    }

    @Override
    public boolean adminUserEnabled() {
        return this.inner().adminUserEnabled();
    }

    @Override
    public String storageAccountName() {
        if (this.inner().storageAccount() == null) {
            return null;
        }

        return ResourceUtils.nameFromResourceId(this.inner().storageAccount().id());
    }

    @Override
    public String storageAccountId() {
        if (this.inner().storageAccount() == null) {
            return null;
        }

        return this.inner().storageAccount().id();
    }

    @Override
    public RegistryImpl withClassicSku() {
        if (this.isInCreateMode()) {
            this.inner().withSku(new Sku().withName(SkuName.CLASSIC));
            this.inner().withStorageAccount(new StorageAccountProperties());
        }

        return this;
    }

    @Override
    public RegistryImpl withBasicSku() {
        return setManagedSku(new Sku().withName(SkuName.BASIC));
    }

    @Override
    public RegistryImpl withStandardSku() {
        return setManagedSku(new Sku().withName(SkuName.STANDARD));
    }

    @Override
    public RegistryImpl withPremiumSku() {
        return setManagedSku(new Sku().withName(SkuName.PREMIUM));
    }

    private RegistryImpl setManagedSku(Sku sku) {
        if (this.isInCreateMode()) {
            this.inner().withSku(sku);
            this.inner().withStorageAccount(null);
        } else {
            this.updateParameters.withSku(sku);
            this.updateParameters.withStorageAccount(null);
        }

        return this;
    }

    @Override
    public RegistryImpl withExistingStorageAccount(StorageAccount storageAccount) {
        this.storageAccountId = storageAccount.id();

        return this;
    }

    @Override
    public RegistryImpl withExistingStorageAccount(String id) {
        this.storageAccountId = id;

        return this;
    }

    @Override
    public RegistryImpl withNewStorageAccount(String storageAccountName) {
        this.storageAccountId = null;

        StorageAccount.DefinitionStages.WithGroup definitionWithGroup = this.storageManager
                .storageAccounts()
                .define(storageAccountName)
                .withRegion(this.regionName());
        Creatable<StorageAccount> definitionAfterGroup;
        if (this.creatableGroup != null) {
            definitionAfterGroup = definitionWithGroup.withNewResourceGroup(this.creatableGroup);
        } else {
            definitionAfterGroup = definitionWithGroup.withExistingResourceGroup(this.resourceGroupName());
        }

        return withNewStorageAccount(definitionAfterGroup);
    }

    @Override
    public RegistryImpl withNewStorageAccount(Creatable<StorageAccount> creatable) {
        this.storageAccountId = null;

        if (this.creatableStorageAccountKey == null) {
            this.creatableStorageAccountKey = this.addDependency(creatable);
        }
        return this;
    }

    @Override
    public RegistryImpl withRegistryNameAsAdminUser() {
        if (this.isInCreateMode()) {
            this.inner().withAdminUserEnabled(true);
        } else {
            this.updateParameters.withAdminUserEnabled(true);
        }

        return this;
    }

    @Override
    public RegistryImpl withoutRegistryNameAsAdminUser() {
        if (this.isInCreateMode()) {
            this.inner().withAdminUserEnabled(false);
        } else {
            this.updateParameters.withAdminUserEnabled(false);
        }

        return this;
    }

    @Override
    public RegistryCredentials getCredentials() {
        return this.manager().containerRegistries()
            .getCredentials(this.resourceGroupName(), this.name());
    }

    @Override
    public Observable<RegistryCredentials> getCredentialsAsync() {
        return this.manager().containerRegistries()
            .getCredentialsAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public RegistryCredentials regenerateCredential(AccessKeyType accessKeyType) {
        return this.manager().containerRegistries()
            .regenerateCredential(this.resourceGroupName(), this.name(), accessKeyType);
    }

    @Override
    public Observable<RegistryCredentials> regenerateCredentialAsync(AccessKeyType accessKeyType) {
        return this.manager().containerRegistries()
            .regenerateCredentialAsync(this.resourceGroupName(), this.name(), accessKeyType);
    }

    @Override
    public Collection<RegistryUsage> listQuotaUsages() {
        return this.manager().containerRegistries()
            .listQuotaUsages(this.resourceGroupName(), this.name());
    }

    @Override
    public Observable<RegistryUsage> listQuotaUsagesAsync() {
        return this.manager().containerRegistries()
            .listQuotaUsagesAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public WebhookOperations webhooks() {
        return new WebhookOperationsImpl(this);
    }

//    @Override
//    public QueuedBuildOperations queuedBuilds() {
//        if (this.queuedBuilds == null) {
//            this.queuedBuilds = new QueuedBuildOperationsImpl(this);
//        }
//        return this.queuedBuilds;
//    }

//    @Override
//    public BuildTaskOperations buildTasks() {
//        if (this.buildTasks == null) {
//            this.buildTasks = new BuildTaskOperationsImpl(this);
//        }
//        return this.buildTasks;
//    }

    @Override
    public SourceUploadDefinition getBuildSourceUploadUrl() {
        return this.getBuildSourceUploadUrlAsync().toBlocking().single();
    }

    @Override
    public Observable<SourceUploadDefinition> getBuildSourceUploadUrlAsync() {
        return this.manager().inner().registries()
            .getBuildSourceUploadUrlAsync(this.resourceGroupName(), this.name())
            .map(new Func1<SourceUploadDefinitionInner, SourceUploadDefinition>() {
                @Override
                public SourceUploadDefinition call(SourceUploadDefinitionInner sourceUploadDefinitionInner) {
                    return new SourceUploadDefinitionImpl(sourceUploadDefinitionInner);
                }
            });
    }

    @Override
    public RegistryImpl withoutWebhook(String name) {
        webhooks.withoutWebhook(name);
        return this;
    }

    @Override
    public WebhookImpl updateWebhook(String name) {
        return webhooks.updateWebhook(name);
    }

    @Override
    public WebhookImpl defineWebhook(String name) {
        return webhooks.defineWebhook(name);
    }
}
