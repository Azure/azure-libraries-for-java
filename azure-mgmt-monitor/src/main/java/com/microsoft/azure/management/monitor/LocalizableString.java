/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.apigeneration.LangMethodDefinition;

/**
 * The localizable string class.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Monitor.Fluent.Models")
public interface LocalizableString {
    /**
     * Get the value value.
     *
     * @return the value value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String value();

    /**
     * Get the localizedValue value.
     *
     * @return the localizedValue value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String localizedValue();
}
