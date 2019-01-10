/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.appservice.implementation.ApplicationStackInner;
import com.microsoft.azure.management.resources.fluentcore.arm.ExpandableStringEnum;

/**
 * Defines values for Java versions.
 */
public abstract class RuntimeVersion<T extends RuntimeVersion<T>> extends ExpandableStringEnum<T>{

    /**
     * Rrturns the name of the runtime
     * @return
     */
    public abstract String getRuntimeName();

    /**

     * @param version runtime version for which we need to create the enum
     */
    /**
     * Creates an enum for the version of the runtime if one does not already exist
     * @param name name of the framweork
     * @param displayVersion display version of the runtime
     * @param runtimeVersion runtime versin of the runtime
     */
    protected abstract void createEnumFromVersionInformation(String name, String displayVersion, String runtimeVersion);

    /**
     * If a subclass is interested in creating enums for the minor versions, then override this
     * function and return true;
     * @return should we process the minor versions
     */
    protected boolean shouldProcessMinorVersions() {
        return false;
    }

    /**
     * If a subclass is interested in creating enums for the frameworks, then override this
     * function and return true;
     * @return should we process the minor versions
     */
    protected boolean shouldProcessFrameworks() {
        return false;
    }

    /**
     * Process the information in the framework information
     * @param frameworkInfo, the frameework information to process
     */
    protected void processFrameWorkInformation(ApplicationStackInner.Properties frameworkInfo) {
        for(StackMajorVersion majorVersion : frameworkInfo.majorVersions()) {
            this.processRuntimeMajorVersion(frameworkInfo.name(), majorVersion);
        }
    }

    /**
     * Check if we need to create enum(s) for this major version of the runtime
     * @param name, name of the runtime
     * @param versionInfo major bversion information
     */
    protected void processRuntimeMajorVersion(String name, StackMajorVersion versionInfo) {
        createEnumFromVersionInformation(name, versionInfo.displayVersion(), versionInfo.runtimeVersion());

        if (this.shouldProcessMinorVersions()) {
            for(StackMinorVersion minorVersionInfo : versionInfo.minorVersions()) {
                this.createEnumFromVersionInformation(name, minorVersionInfo.displayVersion(), minorVersionInfo.runtimeVersion());
            }
        }
    }

    public void parseApplicationStackInner(ApplicationStackInner appStack) {
        if (getRuntimeName().equalsIgnoreCase(appStack.name())) {
            //this is ours... process it

            //process the major versions
            if (appStack.properties().majorVersions() != null) {
                for (StackMajorVersion majorVersion : appStack.properties().majorVersions()) {
                    this.processRuntimeMajorVersion(appStack.name(), majorVersion);
                }
            }

            if (this.shouldProcessFrameworks() && appStack.properties().frameworks() != null) {
                //process the frameworks
                for (ApplicationStackInner.Properties frameworkInfo : appStack.properties().frameworks()) {
                    this.processFrameWorkInformation(frameworkInfo);
                }
            }

        }
    }
}
