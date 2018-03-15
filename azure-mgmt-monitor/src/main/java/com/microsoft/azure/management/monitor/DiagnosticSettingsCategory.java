/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.apigeneration.LangMethodDefinition;
import com.microsoft.azure.management.monitor.implementation.DiagnosticSettingsCategoryResourceInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * The Azure event log entries are of type DiagnosticSettingsCategory.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.Monitor.Fluent.Models")
public interface DiagnosticSettingsCategory
        extends HasInner<DiagnosticSettingsCategoryResourceInner> {
    /**
     * Get the diagnostic settings category name value.
     *
     * @return the diagnostic settings category name
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    String name();

    /**
     * Get the categoryType value.
     *
     * @return the categoryType value
     */
    @LangMethodDefinition(AsType = LangMethodDefinition.LangMethodType.Property)
    CategoryType type();
}
