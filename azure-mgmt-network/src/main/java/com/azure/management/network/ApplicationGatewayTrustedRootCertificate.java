// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.azure.core.management.SubResource;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ApplicationGatewayTrustedRootCertificate model.
 */
@JsonFlatten
@Fluent
public class ApplicationGatewayTrustedRootCertificate extends SubResource {
    /*
     * Name of the trusted root certificate that is unique within an
     * Application Gateway.
     */
    @JsonProperty(value = "name")
    private String name;

    /*
     * A unique read-only string that changes whenever the resource is updated.
     */
    @JsonProperty(value = "etag")
    private String etag;

    /*
     * Type of the resource.
     */
    @JsonProperty(value = "type")
    private String type;

    /*
     * Certificate public data.
     */
    @JsonProperty(value = "properties.data")
    private String data;

    /*
     * Secret Id of (base-64 encoded unencrypted pfx) 'Secret' or 'Certificate'
     * object stored in KeyVault.
     */
    @JsonProperty(value = "properties.keyVaultSecretId")
    private String keyVaultSecretId;

    /*
     * Provisioning state of the trusted root certificate resource. Possible
     * values are: 'Updating', 'Deleting', and 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState")
    private String provisioningState;

    /**
     * Get the name property: Name of the trusted root certificate that is
     * unique within an Application Gateway.
     * 
     * @return the name value.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name property: Name of the trusted root certificate that is
     * unique within an Application Gateway.
     * 
     * @param name the name value to set.
     * @return the ApplicationGatewayTrustedRootCertificate object itself.
     */
    public ApplicationGatewayTrustedRootCertificate setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @return the etag value.
     */
    public String getEtag() {
        return this.etag;
    }

    /**
     * Set the etag property: A unique read-only string that changes whenever
     * the resource is updated.
     * 
     * @param etag the etag value to set.
     * @return the ApplicationGatewayTrustedRootCertificate object itself.
     */
    public ApplicationGatewayTrustedRootCertificate setEtag(String etag) {
        this.etag = etag;
        return this;
    }

    /**
     * Get the type property: Type of the resource.
     * 
     * @return the type value.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Set the type property: Type of the resource.
     * 
     * @param type the type value to set.
     * @return the ApplicationGatewayTrustedRootCertificate object itself.
     */
    public ApplicationGatewayTrustedRootCertificate setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the data property: Certificate public data.
     * 
     * @return the data value.
     */
    public String getData() {
        return this.data;
    }

    /**
     * Set the data property: Certificate public data.
     * 
     * @param data the data value to set.
     * @return the ApplicationGatewayTrustedRootCertificate object itself.
     */
    public ApplicationGatewayTrustedRootCertificate setData(String data) {
        this.data = data;
        return this;
    }

    /**
     * Get the keyVaultSecretId property: Secret Id of (base-64 encoded
     * unencrypted pfx) 'Secret' or 'Certificate' object stored in KeyVault.
     * 
     * @return the keyVaultSecretId value.
     */
    public String getKeyVaultSecretId() {
        return this.keyVaultSecretId;
    }

    /**
     * Set the keyVaultSecretId property: Secret Id of (base-64 encoded
     * unencrypted pfx) 'Secret' or 'Certificate' object stored in KeyVault.
     * 
     * @param keyVaultSecretId the keyVaultSecretId value to set.
     * @return the ApplicationGatewayTrustedRootCertificate object itself.
     */
    public ApplicationGatewayTrustedRootCertificate setKeyVaultSecretId(String keyVaultSecretId) {
        this.keyVaultSecretId = keyVaultSecretId;
        return this;
    }

    /**
     * Get the provisioningState property: Provisioning state of the trusted
     * root certificate resource. Possible values are: 'Updating', 'Deleting',
     * and 'Failed'.
     * 
     * @return the provisioningState value.
     */
    public String getProvisioningState() {
        return this.provisioningState;
    }

    /**
     * Set the provisioningState property: Provisioning state of the trusted
     * root certificate resource. Possible values are: 'Updating', 'Deleting',
     * and 'Failed'.
     * 
     * @param provisioningState the provisioningState value to set.
     * @return the ApplicationGatewayTrustedRootCertificate object itself.
     */
    public ApplicationGatewayTrustedRootCertificate setProvisioningState(String provisioningState) {
        this.provisioningState = provisioningState;
        return this;
    }
}
