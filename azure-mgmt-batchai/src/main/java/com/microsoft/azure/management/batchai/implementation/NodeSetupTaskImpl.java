/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.BatchAICluster;
import com.microsoft.azure.management.batchai.EnvironmentVariable;
import com.microsoft.azure.management.batchai.EnvironmentVariableWithSecretValue;
import com.microsoft.azure.management.batchai.KeyVaultSecretReference;
import com.microsoft.azure.management.batchai.NodeSetupTask;
import com.microsoft.azure.management.batchai.ResourceId;
import com.microsoft.azure.management.batchai.SetupTask;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for NodeSetupTask and its create interface.
 */
@LangDefinition
class NodeSetupTaskImpl
        extends IndexableWrapperImpl<SetupTask>
        implements
        NodeSetupTask,
        NodeSetupTask.Definition<BatchAICluster.DefinitionStages.WithCreate> {
    private BatchAIClusterImpl parent;

    NodeSetupTaskImpl(SetupTask inner, BatchAIClusterImpl parent) {
        super(inner);
        this.parent = parent;
    }

    @Override
    public BatchAIClusterImpl attach() {
        return this.parent.withSetupTask(this);
    }

    @Override
    public NodeSetupTaskImpl withCommandLine(String commandLine) {
        inner().withCommandLine(commandLine);
        return this;
    }

    @Override
    public NodeSetupTaskImpl withRunElevated() {
        inner().withRunElevated(true);
        return this;
    }

    @Override
    public NodeSetupTaskImpl withStdOutErrPath(String stdOutErrPathPrefix) {
        inner().withStdOutErrPathPrefix(stdOutErrPathPrefix);
        return this;
    }

    @Override
    public NodeSetupTaskImpl withEnvironmentVariable(String name, String value) {
        ensureEnvironmentVariables().add(new EnvironmentVariable().withName(name).withValue(value));
        return this;
    }

    private List<EnvironmentVariable> ensureEnvironmentVariables() {
        if (inner().environmentVariables() == null) {
            inner().withEnvironmentVariables(new ArrayList<EnvironmentVariable>());
        }
        return inner().environmentVariables();
    }

    @Override
    public NodeSetupTaskImpl withEnvironmentVariableSecretValue(String name, String value) {
        ensureEnvironmentVariablesWithSecrets().add(new EnvironmentVariableWithSecretValue().withName(name).withValue(value));
        return this;
    }

    @Override
    public NodeSetupTaskImpl withEnvironmentVariableSecretValue(String name, String keyVaultId, String secretUrl) {
        KeyVaultSecretReference secretReference = new KeyVaultSecretReference()
                .withSourceVault(new ResourceId().withId(keyVaultId)).withSecretUrl(secretUrl);
        ensureEnvironmentVariablesWithSecrets().add(new EnvironmentVariableWithSecretValue().withName(name).withValueSecretReference(secretReference));
        return this;
    }

    private List<EnvironmentVariableWithSecretValue> ensureEnvironmentVariablesWithSecrets() {
        if (inner().secrets() == null) {
            inner().withSecrets(new ArrayList<EnvironmentVariableWithSecretValue>());
        }
        return inner().secrets();
    }

    @Override
    public BatchAICluster parent() {
        return parent;
    }
}
