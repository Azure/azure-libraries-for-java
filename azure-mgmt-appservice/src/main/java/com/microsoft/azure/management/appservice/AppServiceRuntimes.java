/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;

import java.util.Collection;

/**
 * Endpoints and credentials for publishing to a web app.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
@Beta
public interface AppServiceRuntimes {
    /**
     * @return the netframework versions.
     */
    Collection<NetFrameworkVersion> NetFrameworkVersions();

    /**
     * @return the python versions.
     */
    Collection<PythonVersion> PythonVersions();

    /**
     * @return the php versions.
     */
    Collection<PhpVersion> PhpVersions();

    /**
     * @return the java versions.
     */
    Collection<JavaVersion> JavaVersions();

    /**
     * @return The web container versions.
     */
    Collection<WebContainer> Webcontainers();

    /**
     * @return The node versions.
     */
    Collection<NodeVersion> NodeVersions();
}
