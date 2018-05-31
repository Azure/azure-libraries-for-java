/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.Experiment;
import com.microsoft.azure.management.batchai.Workspace;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import rx.Observable;

@LangDefinition
class ExperimentImpl extends ExternalChildResourceImpl<Experiment, ExperimentInner, WorkspaceImpl, Workspace>
        implements Experiment {
    protected ExperimentImpl(String name, WorkspaceImpl parent, ExperimentInner innerObject) {
        super(name, parent, innerObject);
    }

    @Override
    public Observable<Experiment> createResourceAsync() {
        return null;
    }

    @Override
    public Observable<Experiment> updateResourceAsync() {
        return null;
    }

    @Override
    public Observable<Void> deleteResourceAsync() {
        return null;
    }

    @Override
    protected Observable<ExperimentInner> getInnerAsync() {
        return null;
    }

    @Override
    public String id() {
        return null;
    }
//        BatchAIJob.Definition,
}