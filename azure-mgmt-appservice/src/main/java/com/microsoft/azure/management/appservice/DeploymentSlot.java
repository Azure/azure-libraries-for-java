/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.appservice.implementation.AppServiceManager;
import com.microsoft.azure.management.appservice.implementation.SiteInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.IndependentChildResource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import rx.Completable;

import java.io.File;
import java.io.InputStream;

/**
 * An immutable client-side representation of an Azure Web App deployment slot.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
@Beta
public interface DeploymentSlot extends
        IndependentChildResource<AppServiceManager, SiteInner>,
        WebAppBase,
        Refreshable<DeploymentSlot>,
        Updatable<DeploymentSlot.Update>,
        HasParent<WebApp> {

    /**
     * Deploys a WAR file onto the Azure specialized Tomcat on this web app.
     * @param warFile the WAR file to upload
     */
    @Beta(SinceVersion.V1_7_0)
    void warDeploy(File warFile);

    /**
     * Deploys a WAR file onto the Azure specialized Tomcat on this web app.
     * @param warFile the WAR file to upload
     * @return a completable of the operation
     */
    @Beta(SinceVersion.V1_7_0)
    Completable warDeployAsync(File warFile);

    /**
     * Deploys a WAR file onto the Azure specialized Tomcat on this web app.
     * @param warFile the WAR file to upload
     */
    @Beta(SinceVersion.V1_7_0)
    void warDeploy(InputStream warFile);

    /**
     * Deploys a WAR file onto the Azure specialized Tomcat on this web app.
     * @param warFile the WAR file to upload
     * @return a completable of the operation
     */
    @Beta(SinceVersion.V1_7_0)
    Completable warDeployAsync(InputStream warFile);

    /**
     * Deploys a WAR file onto the Azure specialized Tomcat on this web app.
     * @param warFile the WAR file to upload
     * @param appName the name of the app, default to "ROOT" when not provided
     */
    @Beta(SinceVersion.V1_7_0)
    void warDeploy(File warFile, String appName);

    /**
     * Deploys a WAR file onto the Azure specialized Tomcat on this web app.
     * @param warFile the WAR file to upload
     * @param appName the name of the app, default to "ROOT" when not provided
     * @return a completable of the operation
     */
    @Beta(SinceVersion.V1_7_0)
    Completable warDeployAsync(File warFile, String appName);

    /**
     * Deploys a WAR file onto the Azure specialized Tomcat on this web app.
     * @param warFile the WAR file to upload
     * @param appName the name of the app, default to "ROOT" when not provided
     */
    @Beta(SinceVersion.V1_7_0)
    void warDeploy(InputStream warFile, String appName);

    /**
     * Deploys a WAR file onto the Azure specialized Tomcat on this web app.
     * @param warFile the WAR file to upload
     * @param appName the name of the app, default to "ROOT" when not provided
     * @return a completable of the operation
     */
    @Beta(SinceVersion.V1_7_0)
    Completable warDeployAsync(InputStream warFile, String appName);

    /**************************************************************
     * Fluent interfaces to provision a deployment slot
     **************************************************************/

    /**
     * Container interface for all the definitions that need to be implemented.
     */
    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithConfiguration,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of all the deployment slot definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of the deployment slot definition.
         */
        interface Blank extends WithConfiguration {
        }

        /**
         * A deployment slot definition allowing the configuration to clone from to be specified.
         */
        interface WithConfiguration {
            /**
             * Creates the deployment slot with brand new site configurations.
             * @return the next stage of the definition
             */
            WithCreate withBrandNewConfiguration();

            /**
             * Copies the site configurations from the web app the deployment slot belongs to.
             * @return the next stage of the definition
             */
            WithCreate withConfigurationFromParent();

            /**
             * Copies the site configurations from a given web app.
             * @param webApp the web app to copy the configurations from
             * @return the next stage of the definition
             */
            WithCreate withConfigurationFromWebApp(WebApp webApp);

            /**
             * Copies the site configurations from a given deployment slot.
             * @param deploymentSlot the deployment slot to copy the configurations from
             * @return the next stage of the definition
             */
            WithCreate withConfigurationFromDeploymentSlot(DeploymentSlot deploymentSlot);
        }

        /**
         * A site definition with sufficient inputs to create a new web app /
         * deployments slot in the cloud, but exposing additional optional
         * inputs to specify.
         */
        interface WithCreate extends
            Creatable<DeploymentSlot>,
            WebAppBase.DefinitionStages.WithCreate<DeploymentSlot> {
        }
    }

    /**
     * The template for a web app update operation, containing all the settings that can be modified.
     */
    interface Update extends
        Appliable<DeploymentSlot>,
        WebAppBase.Update<DeploymentSlot> {
    }
}