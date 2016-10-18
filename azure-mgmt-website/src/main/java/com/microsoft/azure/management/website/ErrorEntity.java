/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.website;

import java.util.List;

/**
 * Body of the error response returned from the API.
 */
public class ErrorEntity {
    /**
     * Basic error code.
     */
    private String code;

    /**
     * Any details of the error.
     */
    private String message;

    /**
     * Type of error.
     */
    private String extendedCode;

    /**
     * Message template.
     */
    private String messageTemplate;

    /**
     * Parameters for the template.
     */
    private List<String> parameters;

    /**
     * Inner errors.
     */
    private List<ErrorEntity> innerErrors;

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
     * @return the ErrorEntity object itself.
     */
    public ErrorEntity withCode(String code) {
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
     * @return the ErrorEntity object itself.
     */
    public ErrorEntity withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Get the extendedCode value.
     *
     * @return the extendedCode value
     */
    public String extendedCode() {
        return this.extendedCode;
    }

    /**
     * Set the extendedCode value.
     *
     * @param extendedCode the extendedCode value to set
     * @return the ErrorEntity object itself.
     */
    public ErrorEntity withExtendedCode(String extendedCode) {
        this.extendedCode = extendedCode;
        return this;
    }

    /**
     * Get the messageTemplate value.
     *
     * @return the messageTemplate value
     */
    public String messageTemplate() {
        return this.messageTemplate;
    }

    /**
     * Set the messageTemplate value.
     *
     * @param messageTemplate the messageTemplate value to set
     * @return the ErrorEntity object itself.
     */
    public ErrorEntity withMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
        return this;
    }

    /**
     * Get the parameters value.
     *
     * @return the parameters value
     */
    public List<String> parameters() {
        return this.parameters;
    }

    /**
     * Set the parameters value.
     *
     * @param parameters the parameters value to set
     * @return the ErrorEntity object itself.
     */
    public ErrorEntity withParameters(List<String> parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * Get the innerErrors value.
     *
     * @return the innerErrors value
     */
    public List<ErrorEntity> innerErrors() {
        return this.innerErrors;
    }

    /**
     * Set the innerErrors value.
     *
     * @param innerErrors the innerErrors value to set
     * @return the ErrorEntity object itself.
     */
    public ErrorEntity withInnerErrors(List<ErrorEntity> innerErrors) {
        this.innerErrors = innerErrors;
        return this;
    }

}
