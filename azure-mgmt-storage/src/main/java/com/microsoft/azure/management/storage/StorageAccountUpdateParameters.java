/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.storage.implementation.SkuInner;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The parameters that can be provided when updating the storage account
 * properties.
 */
@JsonFlatten
public class StorageAccountUpdateParameters {
    /**
     * Gets or sets the SKU name. Note that the SKU name cannot be updated to
     * Standard_ZRS, Premium_LRS or Premium_ZRS, nor can accounts of those SKU
     * names be updated to any other value.
     */
    @JsonProperty(value = "sku")
    private SkuInner sku;

    /**
     * Gets or sets a list of key value pairs that describe the resource. These
     * tags can be used in viewing and grouping this resource (across resource
     * groups). A maximum of 15 tags can be provided for a resource. Each tag
     * must have a key no greater in length than 128 characters and a value no
     * greater in length than 256 characters.
     */
    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    /**
     * The identity of the resource.
     */
    @JsonProperty(value = "identity")
    private Identity identity;

    /**
     * Custom domain assigned to the storage account by the user. Name is the
     * CNAME source. Only one custom domain is supported per storage account at
     * this time. To clear the existing custom domain, use an empty string for
     * the custom domain name property.
     */
    @JsonProperty(value = "properties.customDomain")
    private CustomDomain customDomain;

    /**
     * Provides the encryption settings on the account. The default setting is
     * unencrypted.
     */
    @JsonProperty(value = "properties.encryption")
    private Encryption encryption;

    /**
     * Required for storage accounts where kind = BlobStorage. The access tier
     * used for billing. Possible values include: 'Hot', 'Cool'.
     */
    @JsonProperty(value = "properties.accessTier")
    private AccessTier accessTier;

    /**
     * Provides the identity based authentication settings for Azure Files.
     */
    @JsonProperty(value = "properties.azureFilesIdentityBasedAuthentication")
    private AzureFilesIdentityBasedAuthentication azureFilesIdentityBasedAuthentication;

    /**
     * Allows https traffic only to storage service if sets to true.
     */
    @JsonProperty(value = "properties.supportsHttpsTrafficOnly")
    private Boolean enableHttpsTrafficOnly;

    /**
     * Network rule set.
     */
    @JsonProperty(value = "properties.networkAcls")
    private NetworkRuleSet networkRuleSet;

    /**
     * Allow large file shares if sets to Enabled. It cannot be disabled once
     * it is enabled. Possible values include: 'Disabled', 'Enabled'.
     */
    @JsonProperty(value = "properties.largeFileSharesState")
    private LargeFileSharesState largeFileSharesState;

    /**
     * Maintains information about the network routing choice opted by the user
     * for data transfer.
     */
    @JsonProperty(value = "properties.routingPreference")
    private RoutingPreference routingPreference;

    /**
     * Allow or disallow public access to all blobs or containers in the
     * storage account. The default interpretation is true for this property.
     */
    @JsonProperty(value = "properties.allowBlobPublicAccess")
    private Boolean allowBlobPublicAccess;

    /**
     * Set the minimum TLS version to be permitted on requests to storage. The
     * default interpretation is TLS 1.0 for this property. Possible values
     * include: 'TLS1_0', 'TLS1_1', 'TLS1_2'.
     */
    @JsonProperty(value = "properties.minimumTlsVersion")
    private MinimumTlsVersion minimumTlsVersion;

    /**
     * Optional. Indicates the type of storage account. Currently only
     * StorageV2 value supported by server. Possible values include: 'Storage',
     * 'StorageV2', 'BlobStorage', 'FileStorage', 'BlockBlobStorage'.
     */
    @JsonProperty(value = "kind")
    private Kind kind;

    /**
     * Get gets or sets the SKU name. Note that the SKU name cannot be updated to Standard_ZRS, Premium_LRS or Premium_ZRS, nor can accounts of those SKU names be updated to any other value.
     *
     * @return the sku value
     */
    public SkuInner sku() {
        return this.sku;
    }

    /**
     * Set gets or sets the SKU name. Note that the SKU name cannot be updated to Standard_ZRS, Premium_LRS or Premium_ZRS, nor can accounts of those SKU names be updated to any other value.
     *
     * @param sku the sku value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withSku(SkuInner sku) {
        this.sku = sku;
        return this;
    }

    /**
     * Get gets or sets a list of key value pairs that describe the resource. These tags can be used in viewing and grouping this resource (across resource groups). A maximum of 15 tags can be provided for a resource. Each tag must have a key no greater in length than 128 characters and a value no greater in length than 256 characters.
     *
     * @return the tags value
     */
    public Map<String, String> tags() {
        return this.tags;
    }

    /**
     * Set gets or sets a list of key value pairs that describe the resource. These tags can be used in viewing and grouping this resource (across resource groups). A maximum of 15 tags can be provided for a resource. Each tag must have a key no greater in length than 128 characters and a value no greater in length than 256 characters.
     *
     * @param tags the tags value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withTags(Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Get the identity of the resource.
     *
     * @return the identity value
     */
    public Identity identity() {
        return this.identity;
    }

    /**
     * Set the identity of the resource.
     *
     * @param identity the identity value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withIdentity(Identity identity) {
        this.identity = identity;
        return this;
    }

    /**
     * Get custom domain assigned to the storage account by the user. Name is the CNAME source. Only one custom domain is supported per storage account at this time. To clear the existing custom domain, use an empty string for the custom domain name property.
     *
     * @return the customDomain value
     */
    public CustomDomain customDomain() {
        return this.customDomain;
    }

    /**
     * Set custom domain assigned to the storage account by the user. Name is the CNAME source. Only one custom domain is supported per storage account at this time. To clear the existing custom domain, use an empty string for the custom domain name property.
     *
     * @param customDomain the customDomain value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withCustomDomain(CustomDomain customDomain) {
        this.customDomain = customDomain;
        return this;
    }

    /**
     * Get provides the encryption settings on the account. The default setting is unencrypted.
     *
     * @return the encryption value
     */
    public Encryption encryption() {
        return this.encryption;
    }

    /**
     * Set provides the encryption settings on the account. The default setting is unencrypted.
     *
     * @param encryption the encryption value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withEncryption(Encryption encryption) {
        this.encryption = encryption;
        return this;
    }

    /**
     * Get required for storage accounts where kind = BlobStorage. The access tier used for billing. Possible values include: 'Hot', 'Cool'.
     *
     * @return the accessTier value
     */
    public AccessTier accessTier() {
        return this.accessTier;
    }

    /**
     * Set required for storage accounts where kind = BlobStorage. The access tier used for billing. Possible values include: 'Hot', 'Cool'.
     *
     * @param accessTier the accessTier value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withAccessTier(AccessTier accessTier) {
        this.accessTier = accessTier;
        return this;
    }

    /**
     * Get provides the identity based authentication settings for Azure Files.
     *
     * @return the azureFilesIdentityBasedAuthentication value
     */
    public AzureFilesIdentityBasedAuthentication azureFilesIdentityBasedAuthentication() {
        return this.azureFilesIdentityBasedAuthentication;
    }

    /**
     * Set provides the identity based authentication settings for Azure Files.
     *
     * @param azureFilesIdentityBasedAuthentication the azureFilesIdentityBasedAuthentication value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withAzureFilesIdentityBasedAuthentication(AzureFilesIdentityBasedAuthentication azureFilesIdentityBasedAuthentication) {
        this.azureFilesIdentityBasedAuthentication = azureFilesIdentityBasedAuthentication;
        return this;
    }

    /**
     * Get allows https traffic only to storage service if sets to true.
     *
     * @return the enableHttpsTrafficOnly value
     */
    public Boolean enableHttpsTrafficOnly() {
        return this.enableHttpsTrafficOnly;
    }

    /**
     * Set allows https traffic only to storage service if sets to true.
     *
     * @param enableHttpsTrafficOnly the enableHttpsTrafficOnly value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withEnableHttpsTrafficOnly(Boolean enableHttpsTrafficOnly) {
        this.enableHttpsTrafficOnly = enableHttpsTrafficOnly;
        return this;
    }

    /**
     * Get network rule set.
     *
     * @return the networkRuleSet value
     */
    public NetworkRuleSet networkRuleSet() {
        return this.networkRuleSet;
    }

    /**
     * Set network rule set.
     *
     * @param networkRuleSet the networkRuleSet value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withNetworkRuleSet(NetworkRuleSet networkRuleSet) {
        this.networkRuleSet = networkRuleSet;
        return this;
    }

    /**
     * Get allow large file shares if sets to Enabled. It cannot be disabled once it is enabled. Possible values include: 'Disabled', 'Enabled'.
     *
     * @return the largeFileSharesState value
     */
    public LargeFileSharesState largeFileSharesState() {
        return this.largeFileSharesState;
    }

    /**
     * Set allow large file shares if sets to Enabled. It cannot be disabled once it is enabled. Possible values include: 'Disabled', 'Enabled'.
     *
     * @param largeFileSharesState the largeFileSharesState value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withLargeFileSharesState(LargeFileSharesState largeFileSharesState) {
        this.largeFileSharesState = largeFileSharesState;
        return this;
    }

    /**
     * Get maintains information about the network routing choice opted by the user for data transfer.
     *
     * @return the routingPreference value
     */
    public RoutingPreference routingPreference() {
        return this.routingPreference;
    }

    /**
     * Set maintains information about the network routing choice opted by the user for data transfer.
     *
     * @param routingPreference the routingPreference value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withRoutingPreference(RoutingPreference routingPreference) {
        this.routingPreference = routingPreference;
        return this;
    }

    /**
     * Get allow or disallow public access to all blobs or containers in the storage account. The default interpretation is true for this property.
     *
     * @return the allowBlobPublicAccess value
     */
    public Boolean allowBlobPublicAccess() {
        return this.allowBlobPublicAccess;
    }

    /**
     * Set allow or disallow public access to all blobs or containers in the storage account. The default interpretation is true for this property.
     *
     * @param allowBlobPublicAccess the allowBlobPublicAccess value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withAllowBlobPublicAccess(Boolean allowBlobPublicAccess) {
        this.allowBlobPublicAccess = allowBlobPublicAccess;
        return this;
    }

    /**
     * Get set the minimum TLS version to be permitted on requests to storage. The default interpretation is TLS 1.0 for this property. Possible values include: 'TLS1_0', 'TLS1_1', 'TLS1_2'.
     *
     * @return the minimumTlsVersion value
     */
    public MinimumTlsVersion minimumTlsVersion() {
        return this.minimumTlsVersion;
    }

    /**
     * Set set the minimum TLS version to be permitted on requests to storage. The default interpretation is TLS 1.0 for this property. Possible values include: 'TLS1_0', 'TLS1_1', 'TLS1_2'.
     *
     * @param minimumTlsVersion the minimumTlsVersion value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withMinimumTlsVersion(MinimumTlsVersion minimumTlsVersion) {
        this.minimumTlsVersion = minimumTlsVersion;
        return this;
    }

    /**
     * Get optional. Indicates the type of storage account. Currently only StorageV2 value supported by server. Possible values include: 'Storage', 'StorageV2', 'BlobStorage', 'FileStorage', 'BlockBlobStorage'.
     *
     * @return the kind value
     */
    public Kind kind() {
        return this.kind;
    }

    /**
     * Set optional. Indicates the type of storage account. Currently only StorageV2 value supported by server. Possible values include: 'Storage', 'StorageV2', 'BlobStorage', 'FileStorage', 'BlockBlobStorage'.
     *
     * @param kind the kind value to set
     * @return the StorageAccountUpdateParameters object itself.
     */
    public StorageAccountUpdateParameters withKind(Kind kind) {
        this.kind = kind;
        return this;
    }

}
