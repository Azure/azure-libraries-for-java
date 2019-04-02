/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.appservice.WindowsAppServiceStacks;
import com.microsoft.azure.management.appservice.JavaVersion;
import com.microsoft.azure.management.appservice.NetFrameworkVersion;
import com.microsoft.azure.management.appservice.NodeVersion;
import com.microsoft.azure.management.appservice.PhpVersion;
import com.microsoft.azure.management.appservice.PythonVersion;
import com.microsoft.azure.management.appservice.WebContainer;

import java.util.Collection;

/**
 * The runtimes data to be consumed by the user.
 */
class WindowsAppServiceStacksImpl implements WindowsAppServiceStacks {
    private Collection<NetFrameworkVersion> netframeworkVersions;
    private Collection<PythonVersion> pythonVersions;
    private Collection<PhpVersion> phpVersions;
    private Collection<JavaVersion> javaVersions;
    private Collection<NodeVersion> nodeVersions;
    private Collection<WebContainer> webContainers;

    /**
     * Basic constructor.
     */
    WindowsAppServiceStacksImpl() {
    }

    /**
     * @param versions the NetFramweork versions.
     * @return WindowsAppServiceStacksImpl object
     */
    public WindowsAppServiceStacksImpl withNetFrameworkVersions(Collection<NetFrameworkVersion> versions) {
        this.netframeworkVersions = versions;
        return this;
    }

    /**
     * @param versions the Python versions.
     * @return WindowsAppServiceStacksImpl object
     */
    public WindowsAppServiceStacksImpl withPythonVersions(Collection<PythonVersion> versions) {
        this.pythonVersions = versions;
        return this;
    }

    /**
     * @param versions the Php Versions.
     * @return WindowsAppServiceStacksImpl object.
     */
    public WindowsAppServiceStacksImpl withPhpVersions(Collection<PhpVersion> versions) {
        this.phpVersions = versions;
        return this;
    }

    /**
     * @param versions The Java Versions
     * @return The runtimes object.
     */
    public WindowsAppServiceStacksImpl withJavaVersions(Collection<JavaVersion> versions) {
        this.javaVersions = versions;
        return this;
    }

    /**
     * @param versions The WebContainer versions
     * @return The runtimes object.
     */
    public WindowsAppServiceStacksImpl withWebContainers(Collection<WebContainer> versions) {
        this.webContainers = versions;
        return this;
    }

    /**
     * @param versions the node versions.
     * @return The runtimes object.
     */
    public WindowsAppServiceStacksImpl withNodeVersions(Collection<NodeVersion> versions) {
        this.nodeVersions = versions;
        return this;
    }

    /**
     * @return the netframework versions.
     */
    public Collection<NetFrameworkVersion> netFrameworkVersions() {
        return this.netframeworkVersions;
    }

    /**
     * @return the python versions.
     */
    public Collection<PythonVersion> pythonVersions() {
        return this.pythonVersions;
    }

    /**
     * @return the php versions.
     */
    public Collection<PhpVersion> phpVersions() {
        return this.phpVersions;
    }

    /**
     * @return the java versions.
     */
    public Collection<JavaVersion> javaVersions() {
        return this.javaVersions;
    }

    /**
     * @return The web container versions.
     */
    public Collection<WebContainer> webContainers() {
        return this.webContainers;
    }

    /**
     * @return The node versions.
     */
    public Collection<NodeVersion> nodeVersions() {
        return  this.nodeVersions;
    }
}
