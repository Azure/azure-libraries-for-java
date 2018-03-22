/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.management.sql.CheckNameAvailabilityResult;

/**
 * Implementation for CheckNameAvailabilityResult.
 */
@LangDefinition
public class CheckNameAvailabilityResultImpl
    extends WrapperImpl<CheckNameAvailabilityResponseInner>
    implements CheckNameAvailabilityResult {
        /**
         * Creates an instance of the check name availability result object.
         *
         * @param inner the inner object
         */
        CheckNameAvailabilityResultImpl(CheckNameAvailabilityResponseInner inner) {
            super(inner);
        }

        @Override
        public boolean isAvailable() {
            return this.inner().available();
        }

        @Override
        public String unavailabilityReason() {
            return this.inner().reason() != null ? this.inner().reason().toString() : null;
        }

        @Override
        public String unavailabilityMessage() {
            return this.inner().message();
        }
}
