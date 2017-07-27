/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.logic.implementation;

import org.joda.time.DateTime;
import com.microsoft.azure.management.logic.AgreementType;
import com.microsoft.azure.management.logic.BusinessIdentity;
import com.microsoft.azure.management.logic.AgreementContent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * The integration account agreement.
 */
@JsonFlatten
public class IntegrationAccountAgreementInner extends Resource {
    /**
     * The created time.
     */
    @JsonProperty(value = "properties.createdTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime createdTime;

    /**
     * The changed time.
     */
    @JsonProperty(value = "properties.changedTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime changedTime;

    /**
     * The metadata.
     */
    @JsonProperty(value = "properties.metadata")
    private Object metadata;

    /**
     * The agreement type. Possible values include: 'NotSpecified', 'AS2',
     * 'X12', 'Edifact'.
     */
    @JsonProperty(value = "properties.agreementType", required = true)
    private AgreementType agreementType;

    /**
     * The integration account partner that is set as host partner for this
     * agreement.
     */
    @JsonProperty(value = "properties.hostPartner", required = true)
    private String hostPartner;

    /**
     * The integration account partner that is set as guest partner for this
     * agreement.
     */
    @JsonProperty(value = "properties.guestPartner", required = true)
    private String guestPartner;

    /**
     * The business identity of the host partner.
     */
    @JsonProperty(value = "properties.hostIdentity", required = true)
    private BusinessIdentity hostIdentity;

    /**
     * The business identity of the guest partner.
     */
    @JsonProperty(value = "properties.guestIdentity", required = true)
    private BusinessIdentity guestIdentity;

    /**
     * The agreement content.
     */
    @JsonProperty(value = "properties.content", required = true)
    private AgreementContent content;

    /**
     * Get the createdTime value.
     *
     * @return the createdTime value
     */
    public DateTime createdTime() {
        return this.createdTime;
    }

    /**
     * Get the changedTime value.
     *
     * @return the changedTime value
     */
    public DateTime changedTime() {
        return this.changedTime;
    }

    /**
     * Get the metadata value.
     *
     * @return the metadata value
     */
    public Object metadata() {
        return this.metadata;
    }

    /**
     * Set the metadata value.
     *
     * @param metadata the metadata value to set
     * @return the IntegrationAccountAgreementInner object itself.
     */
    public IntegrationAccountAgreementInner withMetadata(Object metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * Get the agreementType value.
     *
     * @return the agreementType value
     */
    public AgreementType agreementType() {
        return this.agreementType;
    }

    /**
     * Set the agreementType value.
     *
     * @param agreementType the agreementType value to set
     * @return the IntegrationAccountAgreementInner object itself.
     */
    public IntegrationAccountAgreementInner withAgreementType(AgreementType agreementType) {
        this.agreementType = agreementType;
        return this;
    }

    /**
     * Get the hostPartner value.
     *
     * @return the hostPartner value
     */
    public String hostPartner() {
        return this.hostPartner;
    }

    /**
     * Set the hostPartner value.
     *
     * @param hostPartner the hostPartner value to set
     * @return the IntegrationAccountAgreementInner object itself.
     */
    public IntegrationAccountAgreementInner withHostPartner(String hostPartner) {
        this.hostPartner = hostPartner;
        return this;
    }

    /**
     * Get the guestPartner value.
     *
     * @return the guestPartner value
     */
    public String guestPartner() {
        return this.guestPartner;
    }

    /**
     * Set the guestPartner value.
     *
     * @param guestPartner the guestPartner value to set
     * @return the IntegrationAccountAgreementInner object itself.
     */
    public IntegrationAccountAgreementInner withGuestPartner(String guestPartner) {
        this.guestPartner = guestPartner;
        return this;
    }

    /**
     * Get the hostIdentity value.
     *
     * @return the hostIdentity value
     */
    public BusinessIdentity hostIdentity() {
        return this.hostIdentity;
    }

    /**
     * Set the hostIdentity value.
     *
     * @param hostIdentity the hostIdentity value to set
     * @return the IntegrationAccountAgreementInner object itself.
     */
    public IntegrationAccountAgreementInner withHostIdentity(BusinessIdentity hostIdentity) {
        this.hostIdentity = hostIdentity;
        return this;
    }

    /**
     * Get the guestIdentity value.
     *
     * @return the guestIdentity value
     */
    public BusinessIdentity guestIdentity() {
        return this.guestIdentity;
    }

    /**
     * Set the guestIdentity value.
     *
     * @param guestIdentity the guestIdentity value to set
     * @return the IntegrationAccountAgreementInner object itself.
     */
    public IntegrationAccountAgreementInner withGuestIdentity(BusinessIdentity guestIdentity) {
        this.guestIdentity = guestIdentity;
        return this;
    }

    /**
     * Get the content value.
     *
     * @return the content value
     */
    public AgreementContent content() {
        return this.content;
    }

    /**
     * Set the content value.
     *
     * @param content the content value to set
     * @return the IntegrationAccountAgreementInner object itself.
     */
    public IntegrationAccountAgreementInner withContent(AgreementContent content) {
        this.content = content;
        return this;
    }

}
