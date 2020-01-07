// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.storage.models;

import com.azure.core.annotation.Fluent;
import com.azure.management.storage.ImmutabilityPolicyState;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ImmutabilityPolicy model.
 */
@Fluent
public final class ImmutabilityPolicyInner extends AzureEntityResource {
    /*
     * The immutability period for the blobs in the container since the policy
     * creation, in days.
     */
    @JsonProperty(value = "properties.immutabilityPeriodSinceCreationInDays", required = true)
    private int immutabilityPeriodSinceCreationInDays;

    /*
     * The ImmutabilityPolicy state of a blob container, possible values
     * include: Locked and Unlocked.
     */
    @JsonProperty(value = "properties.state", access = JsonProperty.Access.WRITE_ONLY)
    private ImmutabilityPolicyState state;

    /**
     * Get the immutabilityPeriodSinceCreationInDays property: The immutability
     * period for the blobs in the container since the policy creation, in
     * days.
     * 
     * @return the immutabilityPeriodSinceCreationInDays value.
     */
    public int getImmutabilityPeriodSinceCreationInDays() {
        return this.immutabilityPeriodSinceCreationInDays;
    }

    /**
     * Set the immutabilityPeriodSinceCreationInDays property: The immutability
     * period for the blobs in the container since the policy creation, in
     * days.
     * 
     * @param immutabilityPeriodSinceCreationInDays the
     * immutabilityPeriodSinceCreationInDays value to set.
     * @return the ImmutabilityPolicyInner object itself.
     */
    public ImmutabilityPolicyInner setImmutabilityPeriodSinceCreationInDays(int immutabilityPeriodSinceCreationInDays) {
        this.immutabilityPeriodSinceCreationInDays = immutabilityPeriodSinceCreationInDays;
        return this;
    }

    /**
     * Get the state property: The ImmutabilityPolicy state of a blob
     * container, possible values include: Locked and Unlocked.
     * 
     * @return the state value.
     */
    public ImmutabilityPolicyState getState() {
        return this.state;
    }
}
