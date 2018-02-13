/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.eventhub.implementation;

import com.microsoft.azure.management.eventhub.ProvisioningStateDR;
import com.microsoft.azure.management.eventhub.RoleDisasterRecovery;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * Single item in List or Get Alias(Disaster Recovery configuration) operation.
 */
@JsonFlatten
public class ArmDisasterRecoveryInner extends Resource {
    /**
     * Provisioning state of the Alias(Disaster Recovery configuration) -
     * possible values 'Accepted' or 'Succeeded' or 'Failed'. Possible values
     * include: 'Accepted', 'Succeeded', 'Failed'.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private ProvisioningStateDR provisioningState;

    /**
     * ARM Id of the Primary/Secondary eventhub namespace name, which is part
     * of GEO DR pairning.
     */
    @JsonProperty(value = "properties.partnerNamespace")
    private String partnerNamespace;

    /**
     * Alternate name specified when alias and namespace names are same.
     */
    @JsonProperty(value = "properties.alternateName")
    private String alternateName;

    /**
     * role of namespace in GEO DR - possible values 'Primary' or
     * 'PrimaryNotReplicating' or 'Secondary'. Possible values include:
     * 'Primary', 'PrimaryNotReplicating', 'Secondary'.
     */
    @JsonProperty(value = "properties.role", access = JsonProperty.Access.WRITE_ONLY)
    private RoleDisasterRecovery role;

    /**
     * Get the provisioningState value.
     *
     * @return the provisioningState value
     */
    public ProvisioningStateDR provisioningState() {
        return this.provisioningState;
    }

    /**
     * Get the partnerNamespace value.
     *
     * @return the partnerNamespace value
     */
    public String partnerNamespace() {
        return this.partnerNamespace;
    }

    /**
     * Set the partnerNamespace value.
     *
     * @param partnerNamespace the partnerNamespace value to set
     * @return the ArmDisasterRecoveryInner object itself.
     */
    public ArmDisasterRecoveryInner withPartnerNamespace(String partnerNamespace) {
        this.partnerNamespace = partnerNamespace;
        return this;
    }

    /**
     * Get the alternateName value.
     *
     * @return the alternateName value
     */
    public String alternateName() {
        return this.alternateName;
    }

    /**
     * Set the alternateName value.
     *
     * @param alternateName the alternateName value to set
     * @return the ArmDisasterRecoveryInner object itself.
     */
    public ArmDisasterRecoveryInner withAlternateName(String alternateName) {
        this.alternateName = alternateName;
        return this;
    }

    /**
     * Get the role value.
     *
     * @return the role value
     */
    public RoleDisasterRecovery role() {
        return this.role;
    }

}
