/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.logic.implementation;

import com.microsoft.azure.management.logic.WorkflowProvisioningState;
import org.joda.time.DateTime;
import com.microsoft.azure.management.logic.WorkflowState;
import com.microsoft.azure.management.logic.Sku;
import com.microsoft.azure.management.logic.ResourceReference;
import java.util.Map;
import com.microsoft.azure.management.logic.WorkflowParameter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.Resource;

/**
 * The workflow type.
 */
@JsonFlatten
public class WorkflowInner extends Resource {
    /**
     * Gets the provisioning state. Possible values include: 'NotSpecified',
     * 'Accepted', 'Running', 'Ready', 'Creating', 'Created', 'Deleting',
     * 'Deleted', 'Canceled', 'Failed', 'Succeeded', 'Moving', 'Updating',
     * 'Registering', 'Registered', 'Unregistering', 'Unregistered',
     * 'Completed'.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private WorkflowProvisioningState provisioningState;

    /**
     * Gets the created time.
     */
    @JsonProperty(value = "properties.createdTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime createdTime;

    /**
     * Gets the changed time.
     */
    @JsonProperty(value = "properties.changedTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime changedTime;

    /**
     * The state. Possible values include: 'NotSpecified', 'Completed',
     * 'Enabled', 'Disabled', 'Deleted', 'Suspended'.
     */
    @JsonProperty(value = "properties.state")
    private WorkflowState state;

    /**
     * Gets the version.
     */
    @JsonProperty(value = "properties.version", access = JsonProperty.Access.WRITE_ONLY)
    private String version;

    /**
     * Gets the access endpoint.
     */
    @JsonProperty(value = "properties.accessEndpoint", access = JsonProperty.Access.WRITE_ONLY)
    private String accessEndpoint;

    /**
     * The sku.
     */
    @JsonProperty(value = "properties.sku")
    private Sku sku;

    /**
     * The integration account.
     */
    @JsonProperty(value = "properties.integrationAccount")
    private ResourceReference integrationAccount;

    /**
     * The definition.
     */
    @JsonProperty(value = "properties.definition")
    private Object definition;

    /**
     * The parameters.
     */
    @JsonProperty(value = "properties.parameters")
    private Map<String, WorkflowParameter> parameters;

    /**
     * Get the provisioningState value.
     *
     * @return the provisioningState value
     */
    public WorkflowProvisioningState provisioningState() {
        return this.provisioningState;
    }

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
     * Get the state value.
     *
     * @return the state value
     */
    public WorkflowState state() {
        return this.state;
    }

    /**
     * Set the state value.
     *
     * @param state the state value to set
     * @return the WorkflowInner object itself.
     */
    public WorkflowInner withState(WorkflowState state) {
        this.state = state;
        return this;
    }

    /**
     * Get the version value.
     *
     * @return the version value
     */
    public String version() {
        return this.version;
    }

    /**
     * Get the accessEndpoint value.
     *
     * @return the accessEndpoint value
     */
    public String accessEndpoint() {
        return this.accessEndpoint;
    }

    /**
     * Get the sku value.
     *
     * @return the sku value
     */
    public Sku sku() {
        return this.sku;
    }

    /**
     * Set the sku value.
     *
     * @param sku the sku value to set
     * @return the WorkflowInner object itself.
     */
    public WorkflowInner withSku(Sku sku) {
        this.sku = sku;
        return this;
    }

    /**
     * Get the integrationAccount value.
     *
     * @return the integrationAccount value
     */
    public ResourceReference integrationAccount() {
        return this.integrationAccount;
    }

    /**
     * Set the integrationAccount value.
     *
     * @param integrationAccount the integrationAccount value to set
     * @return the WorkflowInner object itself.
     */
    public WorkflowInner withIntegrationAccount(ResourceReference integrationAccount) {
        this.integrationAccount = integrationAccount;
        return this;
    }

    /**
     * Get the definition value.
     *
     * @return the definition value
     */
    public Object definition() {
        return this.definition;
    }

    /**
     * Set the definition value.
     *
     * @param definition the definition value to set
     * @return the WorkflowInner object itself.
     */
    public WorkflowInner withDefinition(Object definition) {
        this.definition = definition;
        return this;
    }

    /**
     * Get the parameters value.
     *
     * @return the parameters value
     */
    public Map<String, WorkflowParameter> parameters() {
        return this.parameters;
    }

    /**
     * Set the parameters value.
     *
     * @param parameters the parameters value to set
     * @return the WorkflowInner object itself.
     */
    public WorkflowInner withParameters(Map<String, WorkflowParameter> parameters) {
        this.parameters = parameters;
        return this;
    }

}
