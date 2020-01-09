/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.storage.implementation;

import com.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.azure.management.resources.fluentcore.utils.Utils;
import com.azure.management.storage.AccessTier;
import com.azure.management.storage.CustomDomain;
import com.azure.management.storage.Encryption;
import com.azure.management.storage.Identity;
import com.azure.management.storage.Kind;
import com.azure.management.storage.ProvisioningState;
import com.azure.management.storage.PublicEndpoints;
import com.azure.management.storage.Sku;
import com.azure.management.storage.SkuName;
import com.azure.management.storage.StorageAccount;
import com.azure.management.storage.StorageAccountCreateParameters;
import com.azure.management.storage.StorageAccountEncryptionKeySource;
import com.azure.management.storage.StorageAccountEncryptionStatus;
import com.azure.management.storage.StorageAccountKey;
import com.azure.management.storage.StorageAccountRegenerateKeyParameters;
import com.azure.management.storage.StorageAccountSkuType;
import com.azure.management.storage.StorageAccountUpdateParameters;
import com.azure.management.storage.StorageService;
import com.azure.management.storage.models.StorageAccountInner;
import com.azure.management.storage.models.StorageAccountsInner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

/**
 * Implementation for {@link StorageAccount}.
 */
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
            accountStatuses = new AccountStatuses(this.getInner().getStatusOfPrimary(), this.getInner().getStatusOfSecondary());
        }
        return accountStatuses;
    }

    @Override
    @Deprecated
    public Sku sku() {
        return new Sku().setName(this.getInner().getSku().getName());
    }

    @Override
    public StorageAccountSkuType skuType() {
        // We deprecated the sku() getter. When we remove it we wanted to rename this
        // 'beta' getter skuType() to sku().
        //
        return StorageAccountSkuType.fromSkuName(this.getInner().getSku().getName());
    }

    @Override
    public Kind kind() {
        return getInner().getKind();
    }

    @Override
    public OffsetDateTime creationTime() {
        return this.getInner().getCreationTime();
    }

    @Override
    public CustomDomain customDomain() {
        return this.getInner().getCustomDomain();
    }

    @Override
    public OffsetDateTime lastGeoFailoverTime() {
        return this.getInner().getLastGeoFailoverTime();
    }

    @Override
    public ProvisioningState provisioningState() {
        return this.getInner().getProvisioningState();
    }

    @Override
    public PublicEndpoints endPoints() {
        if (publicEndpoints == null) {
            publicEndpoints = new PublicEndpoints(this.getInner().getPrimaryEndpoints(), this.getInner().getSecondaryEndpoints());
        }
        return publicEndpoints;
    }

    @Override
    @Deprecated
    public Encryption encryption() {
        return getInner().getEncryption();
    }

    @Override
    public StorageAccountEncryptionKeySource encryptionKeySource() {
        return StorageEncryptionHelper.encryptionKeySource(this.getInner());
    }

    @Override
    public Map<StorageService, StorageAccountEncryptionStatus> encryptionStatuses() {
        return StorageEncryptionHelper.encryptionStatuses(this.getInner());
    }

    @Override
    public AccessTier accessTier() {
        return getInner().getAccessTier();
    }

    @Override
    public String systemAssignedManagedServiceIdentityTenantId() {
        if (this.getInner().getIdentity() == null) {
            return null;
        } else {
            return this.getInner().getIdentity().getTenantId();
        }
    }

    @Override
    public String systemAssignedManagedServiceIdentityPrincipalId() {
        if (this.getInner().getIdentity() == null) {
            return null;
        } else {
            return this.getInner().getIdentity().getPrincipalId();
        }
    }

    @Override
    public boolean isAccessAllowedFromAllNetworks() {
        return StorageNetworkRulesHelper.isAccessAllowedFromAllNetworks(this.getInner());
    }

    @Override
    public List<String> networkSubnetsWithAccess() {
        return StorageNetworkRulesHelper.networkSubnetsWithAccess(this.getInner());
    }

    @Override
    public List<String> ipAddressesWithAccess() {
        return StorageNetworkRulesHelper.ipAddressesWithAccess(this.getInner());
    }

    @Override
    public List<String> ipAddressRangesWithAccess() {
        return StorageNetworkRulesHelper.ipAddressRangesWithAccess(this.getInner());
    }

    @Override
    public boolean canReadLogEntriesFromAnyNetwork() {
        return StorageNetworkRulesHelper.canReadLogEntriesFromAnyNetwork(this.getInner());
    }

    @Override
    public boolean canReadMetricsFromAnyNetwork() {
        return StorageNetworkRulesHelper.canReadMetricsFromAnyNetwork(this.getInner());
    }

    @Override
    public boolean canAccessFromAzureServices() {
        return StorageNetworkRulesHelper.canAccessFromAzureServices(this.getInner());
    }

    @Override
    public boolean isAzureFilesAadIntegrationEnabled() {
        // FIXME: Update the storage API version
        return true;
//        return this.getInner().azureFilesIdentityBasedAuthentication() != null
//                && this.getInner().azureFilesIdentityBasedAuthentication().directoryServiceOptions() == DirectoryServiceOptions.AADDS;
    }

    @Override
    public boolean isHnsEnabled() {
        return Utils.toPrimitiveBoolean(this.getInner().isHnsEnabled());
    }

//    @Override
//    public boolean isLargeFileSharesEnabled() {
//        return this.getInner().largeFileSharesState() == LargeFileSharesState.ENABLED;
//    }

    @Override
    public List<StorageAccountKey> getKeys() {
        return this.getKeysAsync().block();
    }

    @Override
    public Mono<List<StorageAccountKey>> getKeysAsync() {
        return this.getManager().getInner().storageAccounts().listKeysAsync(this.getResourceGroupName(), this.getName())
                .map(storageAccountListKeysResultInner -> storageAccountListKeysResultInner.getKeys());
    }


    @Override
    public List<StorageAccountKey> regenerateKey(String keyName) {
        return this.regenerateKeyAsync(keyName).block();
    }

    @Override
    public Mono<List<StorageAccountKey>> regenerateKeyAsync(String keyName) {
        StorageAccountRegenerateKeyParameters parameters = new StorageAccountRegenerateKeyParameters().setKeyName(keyName);
        return this.getManager().getInner().storageAccounts().regenerateKeyAsync(this.getResourceGroupName(), this.getName(), parameters)
                .map(storageAccountListKeysResultInner -> storageAccountListKeysResultInner.getKeys());
    }

    @Override
    public Mono<StorageAccount> refreshAsync() {
        return super.refreshAsync().map(storageAccount -> {
            StorageAccountImpl impl = (StorageAccountImpl) storageAccount;
            impl.clearWrapperProperties();
            return impl;
        });
    }

    @Override
    protected Mono<StorageAccountInner> getInnerAsync() {
        // FIXME: Double check the API
        return this.getManager().getInner().storageAccounts().getPropertiesAsync(this.getResourceGroupName(), this.getName());
    }

    @Override
    @Deprecated
    public StorageAccountImpl withSku(SkuName skuName) {
        return withSku(StorageAccountSkuType.fromSkuName(skuName));
    }

    @Override
    public StorageAccountImpl withSku(StorageAccountSkuType sku) {
        if (isInCreateMode()) {
            createParameters.setSku(new Sku().setName(sku.name()));
        } else {
            updateParameters.setSku(new Sku().setName(sku.name()));
        }
        return this;
    }

    @Override
    public StorageAccountImpl withBlobStorageAccountKind() {
        createParameters.setKind(Kind.BLOB_STORAGE);
        return this;
    }

    @Override
    public StorageAccountImpl withGeneralPurposeAccountKind() {
        createParameters.setKind(Kind.STORAGE);
        return this;
    }

    @Override
    public StorageAccountImpl withGeneralPurposeAccountKindV2() {
        createParameters.setKind(Kind.STORAGE_V2);
        return this;
    }

    @Override
    public StorageAccountImpl withBlockBlobStorageAccountKind() {
        createParameters.setKind(Kind.BLOCK_BLOB_STORAGE);
        return this;
    }

    @Override
    public StorageAccountImpl withFileStorageAccountKind() {
        createParameters.setKind(Kind.FILE_STORAGE);
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
        this.networkRulesHelper = new StorageNetworkRulesHelper(this.updateParameters, this.getInner());
        this.encryptionHelper = new StorageEncryptionHelper(this.updateParameters, this.getInner());
        return super.update();
    }

    @Override
    public StorageAccountImpl withCustomDomain(CustomDomain customDomain) {
        if (isInCreateMode()) {
            createParameters.setCustomDomain(customDomain);
        } else {
            updateParameters.setCustomDomain(customDomain);
        }
        return this;
    }

    @Override
    public StorageAccountImpl withCustomDomain(String name) {
        return withCustomDomain(new CustomDomain().setName(name));
    }

    @Override
    public StorageAccountImpl withCustomDomain(String name, boolean useSubDomain) {
        return withCustomDomain(new CustomDomain().setName(name).setUseSubDomainName(useSubDomain));
    }

    @Override
    public StorageAccountImpl withAccessTier(AccessTier accessTier) {
        if (isInCreateMode()) {
            createParameters.setAccessTier(accessTier);
        } else {
            if (this.getInner().getKind() != Kind.BLOB_STORAGE) {
                throw new UnsupportedOperationException("Access tier can not be changed for general purpose storage accounts.");
            }
            updateParameters.setAccessTier(accessTier);
        }
        return this;
    }

    @Override
    public StorageAccountImpl withSystemAssignedManagedServiceIdentity() {
        if (this.getInner().getIdentity() == null) {
            if (isInCreateMode()) {
                createParameters.setIdentity(new Identity().setType("SystemAssigned"));
            } else {
                updateParameters.setIdentity(new Identity().setType("SystemAssigned"));
            }
        }
        return this;
    }

    @Override
    public StorageAccountImpl withOnlyHttpsTraffic() {
        if (isInCreateMode()) {
            createParameters.setEnableHttpsTrafficOnly(true);
        } else {
            updateParameters.setEnableHttpsTrafficOnly(true);
        }
        return this;
    }

    @Override
    public StorageAccountImpl withHttpAndHttpsTraffic() {
        updateParameters.setEnableHttpsTrafficOnly(false);
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
        updateParameters.setKind(Kind.STORAGE_V2);
        return this;
    }

    // CreateUpdateTaskGroup.ResourceCreator implementation
    @Override
    public Mono<StorageAccount> createResourceAsync() {
        this.networkRulesHelper.setDefaultActionIfRequired();
        createParameters.setLocation(this.getRegionName());
        createParameters.setTags(this.getInner().getTags());
        final StorageAccountsInner client = this.getManager().getInner().storageAccounts();
        return this.getManager().getInner().storageAccounts().createAsync(
                this.getResourceGroupName(), this.getName(), createParameters)
                // FIXME: Double check the method calling
                .flatMap(storageAccountInner -> client.getPropertiesAsync(getResourceGroupName(), getName())
                        .map(innerToFluentMap(this))
                        .doOnNext(storageAccount -> clearWrapperProperties()));
    }

    @Override
    public Mono<StorageAccount> updateResourceAsync() {
        this.networkRulesHelper.setDefaultActionIfRequired();
        updateParameters.setTags(this.getInner().getTags());
        return this.getManager().getInner().storageAccounts().updateAsync(
                getResourceGroupName(), getName(), updateParameters)
                .map(innerToFluentMap(this))
                .doOnNext(storageAccount -> clearWrapperProperties());
    }

    @Override
    public StorageAccountImpl withAzureFilesAadIntegrationEnabled(boolean enabled) {
        // FIXME: Update storage API version.
//        if (isInCreateMode()) {
//            if (enabled) {
//                this.createParameters.withAzureFilesIdentityBasedAuthentication(new AzureFilesIdentityBasedAuthentication().withDirectoryServiceOptions(DirectoryServiceOptions.AADDS));
//            }
//        } else {
//            if (this.createParameters.azureFilesIdentityBasedAuthentication() == null) {
//                this.createParameters.withAzureFilesIdentityBasedAuthentication(new AzureFilesIdentityBasedAuthentication());
//            }
//            if (enabled) {
//                this.updateParameters.azureFilesIdentityBasedAuthentication().withDirectoryServiceOptions(DirectoryServiceOptions.AADDS);
//            } else {
//                this.updateParameters.azureFilesIdentityBasedAuthentication().withDirectoryServiceOptions(DirectoryServiceOptions.NONE);
//            }
//        }
        return this;
    }

//    @Override
//    public StorageAccountImpl withLargeFileShares(boolean enabled) {
//        if (isInCreateMode()) {
//            if (enabled) {
//                this.createParameters.withLargeFileSharesState(LargeFileSharesState.ENABLED);
//            } else {
//                this.createParameters.withLargeFileSharesState(LargeFileSharesState.DISABLED);
//            }
//        }
//        return this;
//    }

    @Override
    public StorageAccountImpl withHnsEnabled(boolean enabled) {
        this.createParameters.setIsHnsEnabled(enabled);
        return this;
    }
}