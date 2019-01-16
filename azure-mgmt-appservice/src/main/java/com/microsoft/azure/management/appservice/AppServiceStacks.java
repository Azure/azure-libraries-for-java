/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;

import java.util.Set;

/**
 * Endpoints and credentials for publishing to a web app.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
@Beta
public interface AppServiceStacks {
    /**
     * @return the latest Windows application service stacks.
     */
    WindowsAppServiceStacks getLatestWindowsStacks();

    /**
     * @return the latest Linux application service stacks.
     */
    Set<RuntimeStack> listLatestLinuxStacks();
}
