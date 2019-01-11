/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import java.util.Collection;

/**
 * Defines values for .NET framework version.
 */
public final class NetFrameworkVersion extends RuntimeVersion<NetFrameworkVersion> {
    /**
     * Name of the component.
     */
    public static final String COMPONENT_NAME = "aspnet";

    /**
     * Netframework Off setting.
     */
    public static final NetFrameworkVersion OFF = NetFrameworkVersion.fromString("null");

    /** Static value v3.5 for NetFrameworkVersion. */
    public static final NetFrameworkVersion V3_0 = NetFrameworkVersion.fromString("v3.0");

    /** Static value v4.6 for NetFrameworkVersion. */
    public static final NetFrameworkVersion V4_6 = NetFrameworkVersion.fromString("v4.6");

    /**
     * Finds or creates a .NET Framework version based on the name.
     * @param name a name
     * @return an instance of NetFrameworkVersion
     */
    public static NetFrameworkVersion fromString(String name) {
        return fromString(name, NetFrameworkVersion.class);
    }

    /**
     * @return known .NET framework versions
     */
    public static Collection<NetFrameworkVersion> values() {
        return values(NetFrameworkVersion.class);
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
        for (NetFrameworkVersion ver : values()) {
            if (ver.toString().equalsIgnoreCase(version)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Create the enum fomr the passed in values if it does not already exist.
     *
     * @param name name of the framweork
     * @param displayVersion display version of the runtime
     * @param runtimeVersion runtime version of the runtime
     */
    @Override
    protected void createEnumFromVersionInformation(String name, String displayVersion, String runtimeVersion) {
        if(COMPONENT_NAME.equalsIgnoreCase(name)) {
            fromString(displayVersion);
        }
    }
}
