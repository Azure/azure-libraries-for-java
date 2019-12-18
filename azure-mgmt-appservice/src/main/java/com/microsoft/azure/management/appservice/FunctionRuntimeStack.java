/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;

import java.util.Objects;

/**
 * Defines function app runtime for Linux.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
@Beta(Beta.SinceVersion.V1_28_0)
public class FunctionRuntimeStack {

    /** JAVA 8. */
    public static final FunctionRuntimeStack JAVA_8 = new FunctionRuntimeStack("java", "~2",
            "java|8", "DOCKER|mcr.microsoft.com/azure-functions/java:2.0-java8-appservice");

    private final String runtime;
    private final String version;

    private final String linuxFxVersionForConsumptionPlan;
    private final String linuxFxVersionForDedicatedPlan;

    /**
     * Creates a custom function app runtime stack.
     * @param runtime the language runtime
     * @param version the language runtime version
     * @param linuxFxVersionForConsumptionPlan the LinuxFxVersion property value, for Consumption plan
     * @param linuxFxVersionForDedicatedPlan the LinuxFxVersion property value, for dedicated plan (app service plan or premium)
     */
    public FunctionRuntimeStack(String runtime, String version, String linuxFxVersionForConsumptionPlan, String linuxFxVersionForDedicatedPlan) {
        this.runtime = runtime;
        this.version = version;

        this.linuxFxVersionForConsumptionPlan = linuxFxVersionForConsumptionPlan;
        this.linuxFxVersionForDedicatedPlan = linuxFxVersionForDedicatedPlan;
    }

    /**
     * @return the name of the language runtime
     */
    public String runtime() {
        return runtime;
    }

    /**
     * @return the version of the Language runtime
     */
    public String version() {
        return version;
    }

    /**
     * Gets LinuxFxVersion property value, for Consumption plan.
     * @return the LinuxFxVersion property value for siteConfig
     */
    public String getLinuxFxVersionForConsumptionPlan() {
        return linuxFxVersionForConsumptionPlan;
    }

    /**
     * Gets LinuxFxVersion property value, for dedicated plan (app service plan or premium).
     * @return the LinuxFxVersion property value for siteConfig
     */
    public String getLinuxFxVersionForDedicatedPlan() {
        return linuxFxVersionForDedicatedPlan;
    }

    @Override
    public String toString() {
        return runtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FunctionRuntimeStack that = (FunctionRuntimeStack) o;
        return runtime.equals(that.runtime)
                && version.equals(that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(runtime, version);
    }
}
