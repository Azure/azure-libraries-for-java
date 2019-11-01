/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources;

/**
 * Deployment What-if operation parameters.
 */
public class DeploymentWhatIfParameters {

    private DeploymentWhatIf deploymentWhatIf;

    DeploymentWhatIfParameters() {
        deploymentWhatIf = new DeploymentWhatIf()
                .withProperties(new DeploymentWhatIfProperties());
    }

    /**
     * Set the location to store the deployment data.
     *
     * @param location the location value to set
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withLocation(String location) {
        deploymentWhatIf.withLocation(location);
        return this;
    }

    /**
     * Set mode with value of 'INCREMENTAL' in deployment properties.
     *
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withIncrementalMode() {
        deploymentWhatIf.properties().withMode(DeploymentMode.INCREMENTAL);
        return this;
    }

    /**
     * Set mode with value of 'COMPLETE' in deployment properties.
     *
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withCompleteMode() {
        deploymentWhatIf.properties().withMode(DeploymentMode.COMPLETE);
        return this;
    }

    /**
     * Set result format with value of 'FULL_RESOURCE_PAYLOADS' in What-if settings of deployment properties.
     *
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withFullResourcePayloadsResultFormat() {
        deploymentWhatIf.properties()
                .withWhatIfSettings(new DeploymentWhatIfSettings()
                        .withResultFormat(WhatIfResultFormat.FULL_RESOURCE_PAYLOADS));
        return this;
    }

    /**
     * Set result format with value of 'RESOURCE_ID_ONLY' in What-if settings of deployment properties.
     *
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withResourceIdOnlyResultFormat() {
        deploymentWhatIf.properties()
                .withWhatIfSettings(new DeploymentWhatIfSettings()
                        .withResultFormat(WhatIfResultFormat.RESOURCE_ID_ONLY));
        return this;
    }

    /**
     * Set the uri and content version of parameters file.
     *
     * @param uri the uri value to set
     * @param contentVersion the content version value to set
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withParametersLink(String uri, String contentVersion) {
        deploymentWhatIf.properties()
                .withParametersLink(new ParametersLink().withUri(uri).withContentVersion(contentVersion));
        return this;
    }

    /**
     * Set the uri and content version of template.
     *
     * @param uri the location uri to set
     * @param contentVersion the content version value to set
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withTemplateLink(String uri, String contentVersion) {
        deploymentWhatIf.properties()
                .withTemplateLink(new TemplateLink().withUri(uri).withContentVersion(contentVersion));
        return this;
    }

    /**
     * Set the template content.
     *
     * @param template the template value to set
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withTemplate(Object template) {
        deploymentWhatIf.properties()
                .withTemplate(template);
        return this;
    }

    /**
     * Set the name and value pairs that define the deployment parameters for the template.
     *
     * @param parameters the parameters value to set
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withParameters(Object parameters) {
        deploymentWhatIf.properties()
                .withParameters(parameters);
        return this;
    }

    /**
     * Set the type of information to log for debugging.
     *
     * @param detailedLevel the detail level value to set
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withDetailedLevel(String detailedLevel) {
        deploymentWhatIf.properties()
                .withDebugSetting(new DebugSetting().withDetailLevel(detailedLevel));
        return this;
    }

    /**
     * Set the What-if deployment on error behavior type with value of 'LAST_SUCCESSFUL'.
     *
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withLastSuccessfulOnErrorDeployment() {
        addOnErrorDeploymentIfAbsent();
        deploymentWhatIf.properties()
                .onErrorDeployment()
                .withType(OnErrorDeploymentType.LAST_SUCCESSFUL);
        return this;
    }

    /**
     * Set the What-if deployment on error behavior type with value of 'SPECIFIC_DEPLOYMENT'.
     *
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withSpecialDeploymentOnErrorDeployment() {
        addOnErrorDeploymentIfAbsent();
        deploymentWhatIf.properties()
                .onErrorDeployment()
                .withType(OnErrorDeploymentType.SPECIFIC_DEPLOYMENT);
        return this;
    }

    /**
     * Set the deployment name to be used on error case.
     *
     * @param deploymentName the deployment name value to set
     * @return the DeploymentWhatIfParameters object itself.
     */
    public DeploymentWhatIfParameters withDeploymentName(String deploymentName) {
        addOnErrorDeploymentIfAbsent();
        deploymentWhatIf.properties()
                .onErrorDeployment()
                .withDeploymentName(deploymentName);
        return this;
    }

    /**
     * Set new OnErrorDeployment object if it is not initialized.
     */
    private void addOnErrorDeploymentIfAbsent() {
        if (deploymentWhatIf.properties().onErrorDeployment() == null) {
            deploymentWhatIf.properties().withOnErrorDeployment(new OnErrorDeployment());
        }
    }

    /**
     * Get the DeploymentWhatIf object.
     *
     * @return the DeploymentWhatIf value.
     */
    public DeploymentWhatIf deploymentWhatIf() {
        return this.deploymentWhatIf;
    }
}
