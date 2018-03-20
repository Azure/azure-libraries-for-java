/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.recoveryservicessiterecovery;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Service based exception details.
 */
public class ARMExceptionDetails {
    /**
     * Gets service error code.
     */
    @JsonProperty(value = "code")
    private String code;

    /**
     * Gets error message.
     */
    @JsonProperty(value = "message")
    private String message;

    /**
     * Gets possible cause for error.
     */
    @JsonProperty(value = "possibleCauses")
    private String possibleCauses;

    /**
     * Gets recommended action for the error.
     */
    @JsonProperty(value = "recommendedAction")
    private String recommendedAction;

    /**
     * Gets the client request Id for the session.
     */
    @JsonProperty(value = "clientRequestId")
    private String clientRequestId;

    /**
     * Gets the activity Id for the session.
     */
    @JsonProperty(value = "activityId")
    private String activityId;

    /**
     * Gets exception target.
     */
    @JsonProperty(value = "target")
    private String target;

    /**
     * Get the code value.
     *
     * @return the code value
     */
    public String code() {
        return this.code;
    }

    /**
     * Set the code value.
     *
     * @param code the code value to set
     * @return the ARMExceptionDetails object itself.
     */
    public ARMExceptionDetails withCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * Get the message value.
     *
     * @return the message value
     */
    public String message() {
        return this.message;
    }

    /**
     * Set the message value.
     *
     * @param message the message value to set
     * @return the ARMExceptionDetails object itself.
     */
    public ARMExceptionDetails withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Get the possibleCauses value.
     *
     * @return the possibleCauses value
     */
    public String possibleCauses() {
        return this.possibleCauses;
    }

    /**
     * Set the possibleCauses value.
     *
     * @param possibleCauses the possibleCauses value to set
     * @return the ARMExceptionDetails object itself.
     */
    public ARMExceptionDetails withPossibleCauses(String possibleCauses) {
        this.possibleCauses = possibleCauses;
        return this;
    }

    /**
     * Get the recommendedAction value.
     *
     * @return the recommendedAction value
     */
    public String recommendedAction() {
        return this.recommendedAction;
    }

    /**
     * Set the recommendedAction value.
     *
     * @param recommendedAction the recommendedAction value to set
     * @return the ARMExceptionDetails object itself.
     */
    public ARMExceptionDetails withRecommendedAction(String recommendedAction) {
        this.recommendedAction = recommendedAction;
        return this;
    }

    /**
     * Get the clientRequestId value.
     *
     * @return the clientRequestId value
     */
    public String clientRequestId() {
        return this.clientRequestId;
    }

    /**
     * Set the clientRequestId value.
     *
     * @param clientRequestId the clientRequestId value to set
     * @return the ARMExceptionDetails object itself.
     */
    public ARMExceptionDetails withClientRequestId(String clientRequestId) {
        this.clientRequestId = clientRequestId;
        return this;
    }

    /**
     * Get the activityId value.
     *
     * @return the activityId value
     */
    public String activityId() {
        return this.activityId;
    }

    /**
     * Set the activityId value.
     *
     * @param activityId the activityId value to set
     * @return the ARMExceptionDetails object itself.
     */
    public ARMExceptionDetails withActivityId(String activityId) {
        this.activityId = activityId;
        return this;
    }

    /**
     * Get the target value.
     *
     * @return the target value
     */
    public String target() {
        return this.target;
    }

    /**
     * Set the target value.
     *
     * @param target the target value to set
     * @return the ARMExceptionDetails object itself.
     */
    public ARMExceptionDetails withTarget(String target) {
        this.target = target;
        return this;
    }

}
