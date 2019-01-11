/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import java.util.Collection;

/**
 * Defines values for Java web container.
 */
public final class WebContainer extends RuntimeVersion<WebContainer> {
    /**
     * Name of the component.
     */
    public static final String COMPONENT_NAME = "javaContainers";
    public static final String SEPERATOR = " ";
    public static final WebContainer OFF = WebContainer.fromString("null");
    /** Static value tomcat 7.0 newest for WebContainer. */
    public static final WebContainer TOMCAT_7_0_NEWEST = WebContainer.fromString("tomcat 7.0");

    /** Static value tomcat 7.0.50 for WebContainer. */
    public static final WebContainer TOMCAT_7_0_50 = WebContainer.fromString("tomcat 7.0.50");

    /** Static value tomcat 7.0.62 for WebContainer. */
    public static final WebContainer TOMCAT_7_0_62 = WebContainer.fromString("tomcat 7.0.62");

    /** Static value tomcat 8.0 newest for WebContainer. */
    public static final WebContainer TOMCAT_8_0_NEWEST = WebContainer.fromString("tomcat 8.0");

    /** Static value tomcat 8.0.23 for WebContainer. */
    public static final WebContainer TOMCAT_8_0_23 = WebContainer.fromString("tomcat 8.0.23");

    /** Static value tomcat 8.5 newest for WebContainer. */
    public static final WebContainer TOMCAT_8_5_NEWEST = WebContainer.fromString("tomcat 8.5");

    /** Static value tomcat 8.5.6 for WebContainer. */
    public static final WebContainer TOMCAT_8_5_6 = WebContainer.fromString("tomcat 8.5.6");

    /** Static value tomcat 8.5.20 for WebContainer. */
    public static final WebContainer TOMCAT_8_5_20 = WebContainer.fromString("tomcat 8.5.20");

    /** Static value tomcat 9.0 newest for WebContainer. */
    public static final WebContainer TOMCAT_9_0_NEWEST = WebContainer.fromString("tomcat 9.0");

    /** Static value tomcat 9_0_0 for WebContainer. */
    public static final WebContainer TOMCAT_9_0_0 = WebContainer.fromString("tomcat 9.0.0");

    /** Static value jetty 9.1 for WebContainer. */
    public static final WebContainer JETTY_9_1_NEWEST = WebContainer.fromString("jetty 9.1");

    /** Static value jetty 9.1.0 v20131115 for WebContainer. */
    public static final WebContainer JETTY_9_1_V20131115 = WebContainer.fromString("jetty 9.1.0.20131115");

    /** Static value jetty 9.3 for WebContainer. */
    public static final WebContainer JETTY_9_3_NEWEST = WebContainer.fromString("jetty 9.3");

    /** Static value jetty 9.3.13 v20161014 for WebContainer. */
    public static final WebContainer JETTY_9_3_V20161014 = WebContainer.fromString("jetty 9.3.13.20161014");

    /**
     * Finds or creates a Web container based on the specified name.
     * @param name a name
     * @return a WebContainer instance
     */
    public static WebContainer fromString(String name) {
        return fromString(name, WebContainer.class);
    }

    /**
     * Finds or creates a Java container version based on the specified name and version.
     * @param name framework name
     * @return framework instance version
     */
    public static WebContainer fromString(String name, String version) {
        return fromString(name + SEPERATOR + version, WebContainer.class);
    }

    /**
     * @return known Web container types
     */
    public static Collection<WebContainer> values() {
        return values(WebContainer.class);
    }

    /**
     * @return the component name.
     */
    @Override
    public String getRuntimeName() {
        return this.COMPONENT_NAME;
    }

    /**
     * @param version the version to check
     * @return True if the enum contains the version, false otherwise.
     */
    @Override
    public boolean containsVersion(String version) {
        for (WebContainer ver : values()) {
            if (ver.toString().equalsIgnoreCase(version)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return true. This runtime processes frameworks.
     */
    @Override
    protected boolean shouldProcessFrameworks() {
        return true;
    }

    /**
     * @return true, This runtime processes minor versions.
     */
    @Override
    protected boolean shouldProcessMinorVersions() {
        return true;
    }

    /**
     * Create a new version enum from the passed in values if one does not already exist.
     * @param name name of the framweork
     * @param displayVersion display version of the runtime
     * @param runtimeVersion runtime versin of the runtime
     */
    @Override
    protected void createEnumFromVersionInformation(String name, String displayVersion, String runtimeVersion) {
        fromString(name, runtimeVersion);
    }
}
