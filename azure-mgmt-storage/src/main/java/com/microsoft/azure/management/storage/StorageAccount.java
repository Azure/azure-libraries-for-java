/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import com.microsoft.azure.management.storage.implementation.AccountStatuses;
import com.microsoft.azure.management.storage.implementation.StorageAccountInner;
import com.microsoft.azure.management.storage.implementation.StorageManager;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import org.joda.time.DateTime;
import rx.Observable;

import java.util.List;
import java.util.Map;

/**
 * An immutable client-side representation of an Azure storage account.
 */
@Fluent
public interface StorageAccount extends
        GroupableResource<StorageManager, StorageAccountInner>,
        Refreshable<StorageAccount>,
        Updatable<StorageAccount.Update> {

    /**
     * @return the status indicating whether the primary and secondary location of
     * the storage account is available or unavailable. Possible values include:
     * 'Available', 'Unavailable'
     */
    AccountStatuses accountStatuses();

    /**
     * @return the sku of this storage account.
     * @deprecated use {@link StorageAccount#skuType()} instead.
     */
    @Deprecated
    Sku sku();

    /**
     * @return the sku of this storage account.
     */
    @Beta(Beta.SinceVersion.V1_5_0)
    StorageAccountSkuType skuType();

    /**
     * @return the kind of the storage account. Possible values are 'Storage',
     * 'BlobStorage'.
     */
    Kind kind();

    /**
     * @return the creation date and time of the storage account in UTC
     */
    DateTime creationTime();

    /**
     * @return the user assigned custom domain assigned to this storage account
     */
    CustomDomain customDomain();

    /**
     * @return the timestamp of the most recent instance of a failover to the
     * secondary location. Only the most recent timestamp is retained. This
     * element is not returned if there has never been a failover instance.
     * Only available if the accountType is StandardGRS or StandardRAGRS
     */
    DateTime lastGeoFailoverTime();

    /**
     * @return the status of the storage account at the time the operation was
     * called. Possible values include: 'Creating', 'ResolvingDNS',
     * 'Succeeded'
     */
    ProvisioningState provisioningState();

    /**
     * @return the URLs that are used to perform a retrieval of a public blob,
     * queue or table object. Note that StandardZRS and PremiumLRS accounts
     * only return the blob endpoint
     */
    PublicEndpoints endPoints();

    /**
     * @return the encryption settings on the account.
     * @deprecated use {@link StorageAccount#encryptionKeySource()}, {@link StorageAccount#encryptionStatuses()} instead.
     */
    @Deprecated
    Encryption encryption();

    /**
     * @return the source of the key used for encryption.
     */
    StorageAccountEncryptionKeySource encryptionKeySource();

    /**
     * @return the encryption statuses indexed by storage service type.
     */
    Map<StorageService, StorageAccountEncryptionStatus> encryptionStatuses();

    /**
     * @return access tier used for billing. Access tier cannot be changed more
     * than once every 7 days (168 hours). Access tier cannot be set for
     * StandardLRS, StandardGRS, StandardRAGRS, or PremiumLRS account types.
     * Possible values include: 'Hot', 'Cool'.
     */
    AccessTier accessTier();

    /**
     * @return the Managed Service Identity specific Active Directory tenant ID assigned to the
     * storage account.
     */
    @Beta(Beta.SinceVersion.V1_5_0)
    String systemAssignedManagedServiceIdentityTenantId();

    /**
     * @return the Managed Service Identity specific Active Directory service principal ID assigned
     * to the storage account.
     */
    @Beta(Beta.SinceVersion.V1_5_0)
    String systemAssignedManagedServiceIdentityPrincipalId();

    /**
     * @return true if authenticated application from any network is allowed to access the
     * storage account, false if only application from whitelisted network (subnet, ip address,
     * ip address range) can access the storage account.
     */
    @Beta(Beta.SinceVersion.V1_5_0)
    boolean isAccessAllowedFromAllNetworks();

    /**
     * @return the list of resource id of virtual network subnet having access to the storage account.
     */
    @Beta(Beta.SinceVersion.V1_5_0)
    List<String> networkSubnetsWithAccess();

    /**
     * @return the list of ip addresses having access to the storage account.
     */
    @Beta(Beta.SinceVersion.V1_5_0)
    List<String> ipAddressesWithAccess();

    /**
     * @return the list of ip address ranges having access to the storage account.
     */
    @Beta(Beta.SinceVersion.V1_5_0)
    List<String> ipAddressRangesWithAccess();

    /**
     * Checks storage log entries can be read from any network.
     *
     * @return true if storage log entries can be read from any network, false otherwise
     */
    @Beta(Beta.SinceVersion.V1_5_0)
    boolean canReadLogEntriesFromAnyNetwork();

    /**
     * Checks storage metrics can be read from any network.
     *
     * @return true if storage metrics can be read from any network, false otherwise
     */
    @Beta(Beta.SinceVersion.V1_5_0)
    boolean canReadMetricsFromAnyNetwork();

    /**
     * Checks storage account can be accessed from applications running on azure.
     *
     * @return true if storage can be accessed from application running on azure, false otherwise
     */
    @Beta(Beta.SinceVersion.V1_5_0)
    boolean canAccessFromAzureServices();

    /**
     * Fetch the up-to-date access keys from Azure for this storage account.
     *
     * @return the access keys for this storage account
     */
    List<StorageAccountKey> getKeys();

    /**
     * Fetch the up-to-date access keys from Azure for this storage account asynchronously.
     *
     * @return a representation of the deferred computation of this call, returning the access keys
     */
    Observable<List<StorageAccountKey>> getKeysAsync();

    /**
     * Fetch the up-to-date access keys from Azure for this storage account asynchronously.
     *
     * @param callback the callback to call on success or failure, with access keys as parameter.
     * @return a handle to cancel the request
     */
    ServiceFuture<List<StorageAccountKey>> getKeysAsync(ServiceCallback<List<StorageAccountKey>> callback);

    /**
     * Regenerates the access keys for this storage account.
     *
     * @param keyName if the key name
     * @return the generated access keys for this storage account
     */
    List<StorageAccountKey> regenerateKey(String keyName);

    /**
     * Regenerates the access keys for this storage account asynchronously.
     *
     * @param keyName if the key name
     * @return a representation of the deferred computation of this call, returning the regenerated access key
     */
    Observable<List<StorageAccountKey>> regenerateKeyAsync(String keyName);

    /**
     * Regenerates the access keys for this storage account asynchronously.
     *
     * @param keyName if the key name
     * @param callback the callback to call on success or failure, with access keys as parameter.
     * @return a handle to cancel the request
     */
    ServiceFuture<List<StorageAccountKey>> regenerateKeyAsync(String keyName, ServiceCallback<List<StorageAccountKey>> callback);

    /**
     * Container interface for all the definitions that need to be implemented.
     */
    interface Definition extends
        DefinitionStages.Blank,
        DefinitionStages.WithGroup,
        DefinitionStages.WithCreate,
        DefinitionStages.WithCreateAndAccessTier {
    }

    /**
     * Grouping of all the storage account definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of the storage account definition.
         */
        interface Blank extends GroupableResource.DefinitionWithRegion<WithGroup> {
        }

        /**
         * The stage of a storage account definition allowing to specify the resource group.
         */
        interface WithGroup extends GroupableResource.DefinitionStages.WithGroup<WithCreate> {
        }

        /**
         * The stage of a storage account definition allowing to specify sku.
         */
        interface WithSku {
            /**
             * Specifies the sku of the storage account.
             * @deprecated use {@link WithSku#withSku(StorageAccountSkuType)} instead
             *
             * @param skuName the sku
             * @return the next stage of storage account definition
             */
            @Deprecated
            WithCreate withSku(SkuName skuName);

            /**
             * Specifies the sku of the storage account.
             *
             * @param sku the sku
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withSku(StorageAccountSkuType sku);
        }

        /**
         * The stage of a storage account definition allowing to specify account kind as blob storage.
         */
        interface WithBlobStorageAccountKind {
            /**
             * Specifies the storage account kind to be "BlobStorage". The access
             * tier is defaulted to be "Hot".
             *
             * @return the next stage of storage account definition
             */
            WithCreateAndAccessTier withBlobStorageAccountKind();
        }

        /**
         * The stage of a storage account definition allowing to specify account kind as general purpose.
         */
        interface WithGeneralPurposeAccountKind {
            /**
             * Specifies the storage account kind to be "Storage", the kind for
             * general purposes.
             *
             * @return the next stage of storage account definition
             */
            WithCreate withGeneralPurposeAccountKind();

            /**
             * Specifies the storage account kind to be "StorageV2", the kind for
             * general purposes.
             *
             * @return the next stage of storage account definition
             */
            WithCreate withGeneralPurposeAccountKindV2();
        }

        /**
         * The stage of a storage account definition allowing to specify encryption settings.
         */
        interface WithEncryption {
            /**
             * Specifies that encryption needs be enabled for blob service.
             * @deprecated use {@link WithEncryption#withBlobEncryption()} instead.
             *
             * @return the next stage of storage account definition
             */
            @Deprecated
            WithCreate withEncryption();

            /**
             * Specifies that encryption needs be enabled for blob service.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withBlobEncryption();

            /**
             * Disables encryption for blob service.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withoutBlobEncryption();

            /**
             * Specifies that encryption needs be enabled for file service.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withFileEncryption();

            /**
             * Disables encryption for file service.
             *
             * @return he next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withoutFileEncryption();

            /**
             * Specifies the KeyVault key to be used as encryption key.
             *
             * @param keyVaultUri the uri to KeyVault
             * @param keyName the KeyVault key name
             * @param keyVersion the KeyVault key version
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withEncryptionKeyFromKeyVault(String keyVaultUri, String keyName, String keyVersion);
        }

        /**
         * The stage of a storage account definition allowing to associate custom domain with the account.
         */
        interface WithCustomDomain {
            /**
             * Specifies the user domain assigned to the storage account.
             *
             * @param customDomain the user domain assigned to the storage account
             * @return the next stage of storage account definition
             */
            WithCreate withCustomDomain(CustomDomain customDomain);

            /**
             * Specifies the user domain assigned to the storage account.
             *
             * @param name the custom domain name, which is the CNAME source
             * @return the next stage of storage account definition
             */
            WithCreate withCustomDomain(String name);

            /**
             * Specifies the user domain assigned to the storage account.
             *
             * @param name the custom domain name, which is the CNAME source
             * @param useSubDomain whether indirect CName validation is enabled
             * @return the next stage of storage account definition
             */
            WithCreate withCustomDomain(String name, boolean useSubDomain);
        }

        /**
         * The stage of a storage account definition allowing to enable implicit managed service identity (MSI).
         */
        interface WithManagedServiceIdentity {
            /**
             * Specifies that implicit managed service identity (MSI) needs to be enabled.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withSystemAssignedManagedServiceIdentity();
        }

        /**
         * The stage of storage account definition allowing to restrict access protocol.
         */
        @Beta(Beta.SinceVersion.V1_5_0)
        interface WithAccessTraffic {
            /**
             * Specifies that only https traffic should be allowed to storage account.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withOnlyHttpsTraffic();
        }

        /**
         * The stage of storage account definition allowing to configure network access settings.
         */
        @Beta(Beta.SinceVersion.V1_5_0)
        interface WithNetworkAccess {
            /**
             * Specifies that by default access to storage account should be allowed from all networks.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withAccessFromAllNetworks();

            /**
             * Specifies that by default access to storage account should be denied from
             * all networks except from those networks specified via
             * {@link WithNetworkAccess#withAccessFromNetworkSubnet(String)}
             * {@link WithNetworkAccess#withAccessFromIpAddress(String)} and
             * {@link WithNetworkAccess#withAccessFromIpAddressRange(String)}.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withAccessFromSelectedNetworks();

            /**
             * Specifies that access to the storage account from the specific virtual network subnet should be allowed.
             *
             * @param subnetId the virtual network subnet id
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withAccessFromNetworkSubnet(String subnetId);

            /**
             * Specifies that access to the storage account from the specific ip address should be allowed.
             *
             * @param ipAddress the ip address
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withAccessFromIpAddress(String ipAddress);

            /**
             * Specifies that access to the storage account from the specific ip range should be allowed.
             *
             * @param ipAddressCidr the ip address range expressed in cidr format
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withAccessFromIpAddressRange(String ipAddressCidr);

            /**
             * Specifies that read access to the storage logging should be allowed from any network.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withReadAccessToLogEntriesFromAnyNetwork();

            /**
             * Specifies that read access to the storage metrics should be allowed from any network.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withReadAccessToMetricsFromAnyNetwork();

            /**
             * Specifies that access to the storage account should be allowed from applications running
             * on Microsoft Azure services.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            WithCreate withAccessFromAzureServices();
        }

        /**
         * A storage account definition with sufficient inputs to create a new
         * storage account in the cloud, but exposing additional optional inputs to
         * specify.
         */
        interface WithCreate extends
                Creatable<StorageAccount>,
                DefinitionStages.WithSku,
                DefinitionStages.WithBlobStorageAccountKind,
                DefinitionStages.WithGeneralPurposeAccountKind,
                DefinitionStages.WithEncryption,
                DefinitionStages.WithCustomDomain,
                DefinitionStages.WithManagedServiceIdentity,
                DefinitionStages.WithAccessTraffic,
                DefinitionStages.WithNetworkAccess,
                Resource.DefinitionWithTags<WithCreate> {
        }

        /**
         * The stage of storage account definition allowing to set access tier.
         */
        interface WithCreateAndAccessTier extends DefinitionStages.WithCreate {
            /**
             * Specifies the access tier used for billing.
             * <p>
             * Access tier cannot be changed more than once every 7 days (168 hours).
             * Access tier cannot be set for StandardLRS, StandardGRS, StandardRAGRS,
             * or PremiumLRS account types.
             *
             * @param accessTier the access tier value
             * @return the next stage of storage account definition
             */
            DefinitionStages.WithCreate withAccessTier(AccessTier accessTier);
        }
    }

    /**
     * Grouping of all the storage account update stages.
     */
    interface UpdateStages {
        /**
         * The stage of the storage account update allowing to change the sku.
         */
        interface WithSku {
            /**
             * Specifies the sku of the storage account.
             * @deprecated use {@link WithSku#withSku(StorageAccountSkuType)} instead.
             *
             * @param skuName the sku
             * @return the next stage of storage account update
             */
            @Deprecated
            Update withSku(SkuName skuName);


            /**
             * Specifies the sku of the storage account.
             *
             * @param sku the sku
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withSku(StorageAccountSkuType sku);
        }

        /**
         * The stage of the storage account update allowing to associate custom domain.
         */
        interface WithCustomDomain {
            /**
             * Specifies the user domain assigned to the storage account.
             *
             * @param customDomain the user domain assigned to the storage account
             * @return the next stage of storage account update
             */
            Update withCustomDomain(CustomDomain customDomain);

            /**
             * Specifies the user domain assigned to the storage account.
             *
             * @param name the custom domain name, which is the CNAME source
             * @return the next stage of storage account update
             */
            Update withCustomDomain(String name);

            /**
             * Specifies the user domain assigned to the storage account.
             *
             * @param name the custom domain name, which is the CNAME source
             * @param useSubDomain whether indirect CName validation is enabled
             * @return the next stage of storage account update
             */
            Update withCustomDomain(String name, boolean useSubDomain);
        }

        /**
         * The stage of the storage account update allowing to configure encryption settings.
         */
        interface WithEncryption {
            /**
             * Enables encryption for blob service.
             * @deprecated use {@link WithEncryption#withBlobEncryption()} instead.
             *
             * @return the next stage of storage account update
             */
            @Deprecated
            Update withEncryption();

            /**
             * Enables encryption for blob service.
             *
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withBlobEncryption();

            /**
             * Enables encryption for file service.
             *
             * @return he next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withFileEncryption();

            /**
             * Disables encryption for blob service.
             * @deprecated use {@link WithEncryption#withoutBlobEncryption()} instead.
             *
             * @return the next stage of storage account update
             */
            @Deprecated
            Update withoutEncryption();

            /**
             * Disables encryption for blob service.
             *
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withoutBlobEncryption();

            /**
             * Disables encryption for file service.
             *
             * @return he next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withoutFileEncryption();

            /**
             * Specifies the KeyVault key to be used as key for encryption.
             *
             * @param keyVaultUri the uri to KeyVault
             * @param keyName the KeyVault key name
             * @param keyVersion the KeyVault key version
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withEncryptionKeyFromKeyVault(String keyVaultUri, String keyName, String keyVersion);
        }

        /**
         * A blob storage account update stage allowing access tier to be specified.
         */
        interface WithAccessTier {
            /**
             * Specifies the access tier used for billing.
             * <p>
             * Access tier cannot be changed more than once every 7 days (168 hours).
             * Access tier cannot be set for StandardLRS, StandardGRS, StandardRAGRS,
             * or PremiumLRS account types.
             *
             * @param accessTier the access tier value
             * @return the next stage of storage account update
             */
            Update withAccessTier(AccessTier accessTier);
        }

        /**
         * The stage of the storage account update allowing to enable managed service identity (MSI).
         */
        interface WithManagedServiceIdentity {
            /**
             * Specifies that implicit managed service identity (MSI) needs to be enabled.
             *
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withSystemAssignedManagedServiceIdentity();
        }

        /**
         * The stage of the storage account update allowing to specify the protocol to be used to access account.
         */
        @Beta(Beta.SinceVersion.V1_5_0)
        interface WithAccessTraffic {
            /**
             * Specifies that only https traffic should be allowed to storage account.
             *
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withOnlyHttpsTraffic();

            /**
             * Specifies that both http and https traffic should be allowed to storage account.
             *
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withHttpAndHttpsTraffic();
        }


        /**
         * The stage of storage account update allowing to configure network access.
         */
        @Beta(Beta.SinceVersion.V1_5_0)
        interface WithNetworkAccess {
            /**
             * Specifies that by default access to storage account should be allowed from
             * all networks.
             *
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withAccessFromAllNetworks();

            /**
             * Specifies that by default access to storage account should be denied from
             * all networks except from those networks specified via
             * {@link WithNetworkAccess#withAccessFromNetworkSubnet(String)},
             * {@link WithNetworkAccess#withAccessFromIpAddress(String)} and
             * {@link WithNetworkAccess#withAccessFromIpAddressRange(String)}.
             *
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withAccessFromSelectedNetworks();

            /**
             * Specifies that access to the storage account from a specific virtual network
             * subnet should be allowed.
             *
             * @param subnetId the virtual network subnet id
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withAccessFromNetworkSubnet(String subnetId);

            /**
             * Specifies that access to the storage account from a specific ip address should be allowed.
             *
             * @param ipAddress the ip address
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withAccessFromIpAddress(String ipAddress);

            /**
             * Specifies that access to the storage account from a specific ip range should be allowed.
             *
             * @param ipAddressCidr the ip address range expressed in cidr format
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withAccessFromIpAddressRange(String ipAddressCidr);

            /**
             * Specifies that read access to the storage logging should be allowed from any network.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withReadAccessToLogEntriesFromAnyNetwork();

            /**
             * Specifies that read access to the storage metrics should be allowed from any network.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withReadAccessToMetricsFromAnyNetwork();

            /**
             * Specifies that access to the storage account should be allowed from applications running on
             * Microsoft Azure services.
             *
             * @return the next stage of storage account definition
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withAccessFromAzureServices();

            /**
             * Specifies that previously allowed access from specific virtual network subnet should be removed.
             *
             * @param subnetId the virtual network subnet id
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withoutNetworkSubnetAccess(String subnetId);

            /**
             * Specifies that previously allowed access from specific ip address should be removed.
             *
             * @param ipAddress the ip address
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withoutIpAddressAccess(String ipAddress);

            /**
             * Specifies that previously allowed access from specific ip range should be removed.
             *
             * @param ipAddressCidr the ip address range expressed in cidr format
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withoutIpAddressRangeAccess(String ipAddressCidr);

            /**
             * Specifies that previously added read access exception to the storage logging from any network
             * should be removed.
             *
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withoutReadAccessToLoggingFromAnyNetwork();

            /**
             * Specifies that previously added read access exception to the storage metrics from any network
             * should be removed.
             *
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withoutReadAccessToMetricsFromAnyNetwork();

            /**
             * Specifies that previously added access exception to the storage account from application
             * running on azure should be removed.
             *
             * @return the next stage of storage account update
             */
            @Beta(Beta.SinceVersion.V1_5_0)
            Update withoutAccessFromAzureServices();
        }
    }

    /**
     * The template for a storage account update operation, containing all the settings that can be modified.
     */
    interface Update extends
            Appliable<StorageAccount>,
            UpdateStages.WithSku,
            UpdateStages.WithCustomDomain,
            UpdateStages.WithEncryption,
            UpdateStages.WithAccessTier,
            UpdateStages.WithManagedServiceIdentity,
            UpdateStages.WithAccessTraffic,
            UpdateStages.WithNetworkAccess,
            Resource.UpdateWithTags<Update> {
    }
}
