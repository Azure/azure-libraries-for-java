/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.storage.implementation;

import com.microsoft.azure.management.storage.Kind;
import com.microsoft.azure.management.storage.Identity;
import com.microsoft.azure.management.storage.ProvisioningState;
import com.microsoft.azure.management.storage.Endpoints;
import com.microsoft.azure.management.storage.AccountStatus;
import org.joda.time.DateTime;
import com.microsoft.azure.management.storage.CustomDomain;
import com.microsoft.azure.management.storage.Encryption;
import com.microsoft.azure.management.storage.AccessTier;
import com.microsoft.azure.management.storage.NetworkRuleSet;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * The storage account.
 */
@JsonFlatten
public class StorageAccountInner extends Resource {
    /**
     * Gets the SKU.
     */
    @JsonProperty(value = "sku", access = JsonProperty.Access.WRITE_ONLY)
    private SkuInner sku;

    /**
     * Gets the Kind. Possible values include: 'Storage', 'BlobStorage'.
     */
    @JsonProperty(value = "kind", access = JsonProperty.Access.WRITE_ONLY)
    private Kind kind;

    /**
     * The identity of the resource.
     */
    @JsonProperty(value = "identity")
    private Identity identity;

    /**
     * Gets the status of the storage account at the time the operation was
     * called. Possible values include: 'Creating', 'ResolvingDNS',
     * 'Succeeded'.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private ProvisioningState provisioningState;

    /**
     * Gets the URLs that are used to perform a retrieval of a public blob,
     * queue, or table object. Note that Standard_ZRS and Premium_LRS accounts
     * only return the blob endpoint.
     */
    @JsonProperty(value = "properties.primaryEndpoints", access = JsonProperty.Access.WRITE_ONLY)
    private Endpoints primaryEndpoints;

    /**
     * Gets the location of the primary data center for the storage account.
     */
    @JsonProperty(value = "properties.primaryLocation", access = JsonProperty.Access.WRITE_ONLY)
    private String primaryLocation;

    /**
     * Gets the status indicating whether the primary location of the storage
     * account is available or unavailable. Possible values include:
     * 'available', 'unavailable'.
     */
    @JsonProperty(value = "properties.statusOfPrimary", access = JsonProperty.Access.WRITE_ONLY)
    private AccountStatus statusOfPrimary;

    /**
     * Gets the timestamp of the most recent instance of a failover to the
     * secondary location. Only the most recent timestamp is retained. This
     * element is not returned if there has never been a failover instance.
     * Only available if the accountType is Standard_GRS or Standard_RAGRS.
     */
    @JsonProperty(value = "properties.lastGeoFailoverTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime lastGeoFailoverTime;

    /**
     * Gets the location of the geo-replicated secondary for the storage
     * account. Only available if the accountType is Standard_GRS or
     * Standard_RAGRS.
     */
    @JsonProperty(value = "properties.secondaryLocation", access = JsonProperty.Access.WRITE_ONLY)
    private String secondaryLocation;

    /**
     * Gets the status indicating whether the secondary location of the storage
     * account is available or unavailable. Only available if the SKU name is
     * Standard_GRS or Standard_RAGRS. Possible values include: 'available',
     * 'unavailable'.
     */
    @JsonProperty(value = "properties.statusOfSecondary", access = JsonProperty.Access.WRITE_ONLY)
    private AccountStatus statusOfSecondary;

    /**
     * Gets the creation date and time of the storage account in UTC.
     */
    @JsonProperty(value = "properties.creationTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime creationTime;

    /**
     * Gets the custom domain the user assigned to this storage account.
     */
    @JsonProperty(value = "properties.customDomain", access = JsonProperty.Access.WRITE_ONLY)
    private CustomDomain customDomain;

    /**
     * Gets the URLs that are used to perform a retrieval of a public blob,
     * queue, or table object from the secondary location of the storage
     * account. Only available if the SKU name is Standard_RAGRS.
     */
    @JsonProperty(value = "properties.secondaryEndpoints", access = JsonProperty.Access.WRITE_ONLY)
    private Endpoints secondaryEndpoints;

    /**
     * Gets the encryption settings on the account. If unspecified, the account
     * is unencrypted.
     */
    @JsonProperty(value = "properties.encryption", access = JsonProperty.Access.WRITE_ONLY)
    private Encryption encryption;

    /**
     * Required for storage accounts where kind = BlobStorage. The access tier
     * used for billing. Possible values include: 'Hot', 'Cool'.
     */
    @JsonProperty(value = "properties.accessTier", access = JsonProperty.Access.WRITE_ONLY)
    private AccessTier accessTier;

    /**
     * Allows https traffic only to storage service if sets to true.
     */
    @JsonProperty(value = "properties.supportsHttpsTrafficOnly")
    private Boolean enableHttpsTrafficOnly;

    /**
     * Network rule set.
     */
    @JsonProperty(value = "properties.networkAcls", access = JsonProperty.Access.WRITE_ONLY)
    private NetworkRuleSet networkRuleSet;

    /**
     * Get the sku value.
     *
     * @return the sku value
     */
    public SkuInner sku() {
        return this.sku;
    }

    /**
     * Get the kind value.
     *
     * @return the kind value
     */
    public Kind kind() {
        return this.kind;
    }

    /**
     * Get the identity value.
     *
     * @return the identity value
     */
    public Identity identity() {
        return this.identity;
    }

    /**
     * Set the identity value.
     *
     * @param identity the identity value to set
     * @return the StorageAccountInner object itself.
     */
    public StorageAccountInner withIdentity(Identity identity) {
        this.identity = identity;
        return this;
    }

    /**
     * Get the provisioningState value.
     *
     * @return the provisioningState value
     */
    public ProvisioningState provisioningState() {
        return this.provisioningState;
    }

    /**
     * Get the primaryEndpoints value.
     *
     * @return the primaryEndpoints value
     */
    public Endpoints primaryEndpoints() {
        return this.primaryEndpoints;
    }

    /**
     * Get the primaryLocation value.
     *
     * @return the primaryLocation value
     */
    public String primaryLocation() {
        return this.primaryLocation;
    }

    /**
     * Get the statusOfPrimary value.
     *
     * @return the statusOfPrimary value
     */
    public AccountStatus statusOfPrimary() {
        return this.statusOfPrimary;
    }

    /**
     * Get the lastGeoFailoverTime value.
     *
     * @return the lastGeoFailoverTime value
     */
    public DateTime lastGeoFailoverTime() {
        return this.lastGeoFailoverTime;
    }

    /**
     * Get the secondaryLocation value.
     *
     * @return the secondaryLocation value
     */
    public String secondaryLocation() {
        return this.secondaryLocation;
    }

    /**
     * Get the statusOfSecondary value.
     *
     * @return the statusOfSecondary value
     */
    public AccountStatus statusOfSecondary() {
        return this.statusOfSecondary;
    }

    /**
     * Get the creationTime value.
     *
     * @return the creationTime value
     */
    public DateTime creationTime() {
        return this.creationTime;
    }

    /**
     * Get the customDomain value.
     *
     * @return the customDomain value
     */
    public CustomDomain customDomain() {
        return this.customDomain;
    }

    /**
     * Get the secondaryEndpoints value.
     *
     * @return the secondaryEndpoints value
     */
    public Endpoints secondaryEndpoints() {
        return this.secondaryEndpoints;
    }

    /**
     * Get the encryption value.
     *
     * @return the encryption value
     */
    public Encryption encryption() {
        return this.encryption;
    }

    /**
     * Get the accessTier value.
     *
     * @return the accessTier value
     */
    public AccessTier accessTier() {
        return this.accessTier;
    }

    /**
     * Get the enableHttpsTrafficOnly value.
     *
     * @return the enableHttpsTrafficOnly value
     */
    public Boolean enableHttpsTrafficOnly() {
        return this.enableHttpsTrafficOnly;
    }

    /**
     * Set the enableHttpsTrafficOnly value.
     *
     * @param enableHttpsTrafficOnly the enableHttpsTrafficOnly value to set
     * @return the StorageAccountInner object itself.
     */
    public StorageAccountInner withEnableHttpsTrafficOnly(Boolean enableHttpsTrafficOnly) {
        this.enableHttpsTrafficOnly = enableHttpsTrafficOnly;
        return this;
    }

    /**
     * Get the networkRuleSet value.
     *
     * @return the networkRuleSet value
     */
    public NetworkRuleSet networkRuleSet() {
        return this.networkRuleSet;
    }

}
