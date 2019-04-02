/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import org.apache.commons.lang3.NotImplementedException;

import java.util.Collection;

/**
 * Defines values for .NET framework version.
 */
public final class LinuxStackVersion extends RuntimeVersion<LinuxStackVersion> {
    /**
     * Name of the component.
     */
    public static final String COMPONENT_NAME = "linux";

    /**
     * Netframework Off setting.
     */
    public static final LinuxStackVersion OFF = LinuxStackVersion.fromString("null");

    /**
     * Finds or creates a .NET Framework version based on the name.
     * @param name a name
     * @return an instance of NetFrameworkVersion
     */
    public static LinuxStackVersion fromString(String name) {
        return fromString(name, LinuxStackVersion.class);
    }

    /**
     * @return known .NET framework versions
     */
    public static Collection<LinuxStackVersion> values() {
        return values(LinuxStackVersion.class);
    }

    /**
     * @return The runtime name.
     */
    @Override
    public String getRuntimeName() {
        return COMPONENT_NAME;
    }

    /**
     * @param version the version to check.
     * @return true if the version present in the enum, false otherwise.
     */
    @Override
    public boolean containsVersion(String version) {
        for (LinuxStackVersion ver : values()) {
            if (ver.toString().equalsIgnoreCase(version)) {
                return true;
            }
        }

        return false;
    }

    /**
     * This entry point should never be used.
     *
     * @param name name of the framweork
     * @param displayVersion display version of the runtime
     * @param runtimeVersion runtime version of the runtime
     */
    @Override
    protected void createEnumFromVersionInformation(String name, String displayVersion, String runtimeVersion) {
        throw new NotImplementedException("Do not use this function to add new enums of type LinuxStackVersion, use RuntimeStack.fromStackNameAndVersionString");
    }
}
