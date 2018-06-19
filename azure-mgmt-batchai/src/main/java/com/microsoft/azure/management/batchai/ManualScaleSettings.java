/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.batchai;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Manual scale settings for the cluster.
 */
public class ManualScaleSettings {
    /**
     * Target node count.
     * The desired number of compute nodes in the Cluster. Default is 0.
     */
    @JsonProperty(value = "targetNodeCount", required = true)
    private int targetNodeCount;

    /**
     * Node deallocation options.
     * An action to be performed when the cluster size is decreasing. The
     * default value is requeue. Possible values include: 'requeue',
     * 'terminate', 'waitforjobcompletion'.
     */
    @JsonProperty(value = "nodeDeallocationOption")
    private DeallocationOption nodeDeallocationOption;

    /**
     * Get the desired number of compute nodes in the Cluster. Default is 0.
     *
     * @return the targetNodeCount value
     */
    public int targetNodeCount() {
        return this.targetNodeCount;
    }

    /**
     * Set the desired number of compute nodes in the Cluster. Default is 0.
     *
     * @param targetNodeCount the targetNodeCount value to set
     * @return the ManualScaleSettings object itself.
     */
    public ManualScaleSettings withTargetNodeCount(int targetNodeCount) {
        this.targetNodeCount = targetNodeCount;
        return this;
    }

    /**
     * Get an action to be performed when the cluster size is decreasing. The default value is requeue. Possible values include: 'requeue', 'terminate', 'waitforjobcompletion'.
     *
     * @return the nodeDeallocationOption value
     */
    public DeallocationOption nodeDeallocationOption() {
        return this.nodeDeallocationOption;
    }

    /**
     * Set an action to be performed when the cluster size is decreasing. The default value is requeue. Possible values include: 'requeue', 'terminate', 'waitforjobcompletion'.
     *
     * @param nodeDeallocationOption the nodeDeallocationOption value to set
     * @return the ManualScaleSettings object itself.
     */
    public ManualScaleSettings withNodeDeallocationOption(DeallocationOption nodeDeallocationOption) {
        this.nodeDeallocationOption = nodeDeallocationOption;
        return this;
    }

}
