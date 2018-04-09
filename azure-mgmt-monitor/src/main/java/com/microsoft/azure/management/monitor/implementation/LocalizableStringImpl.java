/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.LocalizableString;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

/**
 * The {@link LocalizableString} wrapper class implementation.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Monitor.Fluent.Models")
class LocalizableStringImpl
        extends WrapperImpl<LocalizableStringInner> implements LocalizableString {

    LocalizableStringImpl(LocalizableStringInner innerObject) {
        super(innerObject);
    }

    @Override
    public String value() {
        return this.inner().value();
    }

    @Override
    public String localizedValue() {
        return this.inner().localizedValue();
    }
}
