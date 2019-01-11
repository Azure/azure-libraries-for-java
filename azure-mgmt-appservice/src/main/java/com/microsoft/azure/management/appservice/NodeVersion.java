/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import java.util.Collection;

/**
 * Defines values for PHP version.
 */
public final class NodeVersion extends RuntimeVersion<NodeVersion> {
    /**
     * Name of the component.
     */
    public static final String COMPONENT_NAME = "node";
    /** Static value 'Off' for NodeVersion. */
    public static final NodeVersion OFF = NodeVersion.fromString("null");

    /** Static value 0.6 for NodeVersion. */
    public static final NodeVersion NODE0_6 = NodeVersion.fromString("0.6");

    /** Static value 0.8 for NodeVersion. */
    public static final NodeVersion NODE0_8 = NodeVersion.fromString("0.8");

    /** Static value 0.10 for NodeVersion. */
    public static final NodeVersion NODE0_10 = NodeVersion.fromString("0.10");

    /** Static value 0.12 for NodeVersion. */
    public static final NodeVersion NODE0_12 = NodeVersion.fromString("0.12");

    /** Static value 4.8 for NodeVersion. */
    public static final NodeVersion NODE4_8 = NodeVersion.fromString("4.8");

    /** Static value 6.5 for NodeVersion. */
    public static final NodeVersion NODE6_5 = NodeVersion.fromString("6.5");

    /** Static value 6.9 for NodeVersion. */
    public static final NodeVersion NODE6_9 = NodeVersion.fromString("6.9");

    /** Static value 6.12 for NodeVersion. */
    public static final NodeVersion NODE6_12 = NodeVersion.fromString("6.12");

    /** Static value 7.10 for NodeVersion. */
    public static final NodeVersion NODE7_10 = NodeVersion.fromString("7.10");

    /** Static value 8.1 for NodeVersion. */
    public static final NodeVersion NODE8_1 = NodeVersion.fromString("8.1");

    /** Static value 8.4 for NodeVersion. */
    public static final NodeVersion NODE8_4 = NodeVersion.fromString("8.4");

    /** Static value 8.5 for NodeVersion. */
    public static final NodeVersion NODE8_5 = NodeVersion.fromString("8.5");

    /** Static value 8.9 for NodeVersion. */
    public static final NodeVersion NODE8_9 = NodeVersion.fromString("8.9");

    /** Static value 8.10 for NodeVersion. */
    public static final NodeVersion NODE8_10 = NodeVersion.fromString("8.10");

    /** Static value 8.11 for NodeVersion. */
    public static final NodeVersion NODE8_11 = NodeVersion.fromString("8.11");

    /** Static value 10.0 for NodeVersion. */
    public static final NodeVersion NODE10_0 = NodeVersion.fromString("10.0");

    /** Static value 10.6 for NodeVersion. */
    public static final NodeVersion NODE10_6 = NodeVersion.fromString("10.6");

    /**
     * Finds or creates a PHP version based on the specified name.
     * @param name a name
     * @return a PhpVersion instance
     */
    public static NodeVersion fromString(String name) {
        return fromString(name, NodeVersion.class);
    }

    /**
     * @return known PHP versions
     */
    public static Collection<NodeVersion> values() {
        return values(NodeVersion.class);
    }

    /**
     * @return the name of the component.
     */
    @Override
    public String getRuntimeName() {
        return COMPONENT_NAME;
    }

    /**
     * @param version the version to check
     * @return true if the version is present in the enum, false otherwise.
     */
    @Override
    public boolean containsVersion(String version) {
        for (NodeVersion ver : values()) {
            if (ver.toString().equalsIgnoreCase(version)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Vreate a new version enum form the passed in values if one does not exist already.
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
