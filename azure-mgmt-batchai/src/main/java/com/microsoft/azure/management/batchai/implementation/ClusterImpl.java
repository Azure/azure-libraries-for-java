/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.AutoScaleSettings;
import com.microsoft.azure.management.batchai.Cluster;
import com.microsoft.azure.management.batchai.DeallocationOption;
import com.microsoft.azure.management.batchai.ManualScaleSettings;
import com.microsoft.azure.management.batchai.ScaleSettings;
import com.microsoft.azure.management.batchai.UserAccountSettings;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import rx.Observable;

/**
 * Implementation for Cluster and its create and update interfaces.
 */
@LangDefinition
public class ClusterImpl extends GroupableResourceImpl<
        Cluster,
        ClusterInner,
        ClusterImpl,
        BatchAIManager>
        implements
        Cluster,
        Cluster.Definition,
        Cluster.Update {
    private ClusterCreateParametersInner createParameters = new ClusterCreateParametersInner();
    private ClusterUpdateParametersInner updateParameters;

    protected ClusterImpl(String name, ClusterInner innerObject, BatchAIManager manager) {
        super(name, innerObject, manager);
    }

    @Override
    public Observable<Cluster> createResourceAsync() {
        createParameters.withLocation(this.regionName());
//        createParameters.withTags(this.inner().getTags());
        return this.manager().inner().clusters().createAsync(resourceGroupName(), name(), createParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<ClusterInner> getInnerAsync() {
        return this.manager().inner().clusters().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public ClusterImpl withVMSize(String vmSize) {
        createParameters.withVmSize(vmSize);
        return this;
    }

    @Override
    public ClusterImpl withUserName(String userName, String password) {
        ensureUserAccountSettings().withAdminUserName(userName).withAdminUserPassword(password);
        return this;
    }

    private UserAccountSettings ensureUserAccountSettings() {
        if (createParameters.userAccountSettings() == null) {
            createParameters.withUserAccountSettings(new UserAccountSettings());
        }
        return createParameters.userAccountSettings();
    }

    @Override
    public Cluster.DefinitionStages.WithCreate withAutoScale(int minimumNodeCount, int maximumNodeCount) {
        AutoScaleSettings autoScaleSettings = new AutoScaleSettings().withMinimumNodeCount(minimumNodeCount).withMaximumNodeCount(maximumNodeCount);
        ensureScaleSettings().withAutoScale(autoScaleSettings);
        return this;
    }

    @Override
    public Cluster.DefinitionStages.WithCreate withAutoScale(int minimumNodeCount, int maximumNodeCount, int initialNodeCount) {
        AutoScaleSettings autoScaleSettings = new AutoScaleSettings()
                .withMinimumNodeCount(minimumNodeCount)
                .withMaximumNodeCount(maximumNodeCount)
                .withInitialNodeCount(initialNodeCount);
        ensureScaleSettings().withAutoScale(autoScaleSettings);
        return this;
    }

    @Override
    public Cluster.DefinitionStages.WithCreate withManualScale(int targetNodeCount) {
        ensureScaleSettings().withManual(new ManualScaleSettings().withTargetNodeCount(targetNodeCount));
        return this;
    }

    @Override
    public Cluster.DefinitionStages.WithCreate withManualScale(int targetNodeCount, DeallocationOption deallocationOption) {
        ensureScaleSettings().withManual(new ManualScaleSettings().withTargetNodeCount(targetNodeCount).withNodeDeallocationOption(deallocationOption));
        return this;
    }

    private ScaleSettings ensureScaleSettings() {
        if (createParameters.scaleSettings() == null) {
            createParameters.withScaleSettings(new ScaleSettings());
        }
        return createParameters.scaleSettings();
    }
}
