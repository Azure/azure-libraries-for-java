/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.arm;

/**
 * The immutable context for ARM request.
 */
public final class Context {

    // nullable
    private final String correlationRequestId;

    /**
     * The empty context.
     */
    public static final Context NONE = new Context.Builder().build();

    private Context(String correlationRequestId) {
        this.correlationRequestId = correlationRequestId;
    }

    /**
     * @return the x-ms-correlation-request-id for ARM request.
     */
    public String correlationRequestId() {
        return correlationRequestId;
    }

    /**
     * The context builder.
     */
    public static class Builder {

        private String correlationRequestId = null;

        /**
         * The constructor of the builder.
         */
        public Builder() {
        }

        /**
         * Sets the x-ms-correlation-request-id for ARM request.
         *
         * @param correlationRequestId the x-ms-correlation-request-id for ARM request.
         * @return the Builder.
         */
        public Builder withCorrelationRequestId(String correlationRequestId) {
            this.correlationRequestId = correlationRequestId;
            return this;
        }

        /**
         * Creates the Context instance.
         *
         * @return the Context instance.
         */
        public Context build() {
            return new Context(correlationRequestId);
        }
    }
}
