/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import java.util.Collection;

/**
 * Defines values for Python version.
 */
public final class PythonVersion extends RuntimeVersion<PythonVersion> {
    /**
     * Name of the component.
     */
    public static final String COMPONENT_NAME = "python";
    /** Static value 'Off' for PythonVersion. */
    public static final PythonVersion OFF = PythonVersion.fromString("null");

    /** Static value 2.7 for PythonVersion. */
    public static final PythonVersion PYTHON_27 = PythonVersion.fromString("2.7");

    /** Static value 3.4 for PythonVersion. */
    public static final PythonVersion PYTHON_34 = PythonVersion.fromString("3.4");

    /**
     * Finds or creates a Python version based on the specified name.
     * @param name a name
     * @return a PythonVersion instance
     */
    public static PythonVersion fromString(String name) {
        return fromString(name, PythonVersion.class);
    }

    /**
     * @return known Python versions
     */
    public static Collection<PythonVersion> values() {
        return values(PythonVersion.class);
    }

    /**
     * @return the runtime name.
     */
    @Override
    public String getRuntimeName() {
        return COMPONENT_NAME;
    }

    /**
     * @param version the version to check.
     * @return true if the enum contains the version, false otherwise.
     */
    @Override
    public boolean containsVersion(String version) {
        for (PythonVersion ver : values()) {
            if (ver.toString().equalsIgnoreCase(version)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Create a version enum form the values passed in if one does not exist already.
     * @param name name of the framweork
     * @param displayVersion display version of the runtime
     * @param runtimeVersion runtime versin of the runtime
     */
    @Override
    protected void createEnumFromVersionInformation(String name, String displayVersion, String runtimeVersion) {
        if (COMPONENT_NAME.equalsIgnoreCase(name)) {
            fromString(runtimeVersion);
        }
    }
}
