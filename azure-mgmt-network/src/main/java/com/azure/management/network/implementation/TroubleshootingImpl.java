/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.NetworkWatcher;
import com.azure.management.network.Troubleshooting;
import com.azure.management.network.TroubleshootingDetails;
import com.azure.management.network.TroubleshootingParameters;
import com.azure.management.network.models.TroubleshootingResultInner;
import com.azure.management.resources.fluentcore.model.implementation.ExecutableImpl;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Implementation of Troubleshooting interface.
 */
class TroubleshootingImpl extends ExecutableImpl<Troubleshooting>
        implements Troubleshooting, Troubleshooting.Definition {

    private final NetworkWatcherImpl parent;
    private TroubleshootingParameters parameters = new TroubleshootingParameters();
    private TroubleshootingResultInner result;

    TroubleshootingImpl(NetworkWatcherImpl parent) {
        this.parent = parent;
    }

    @Override
    public TroubleshootingImpl withTargetResourceId(String targetResourceId) {
        parameters.setTargetResourceId(targetResourceId);
        return this;
    }

    @Override
    public TroubleshootingImpl withStorageAccount(String storageAccountId) {
        parameters.setStorageId(storageAccountId);
        return this;
    }

    @Override
    public TroubleshootingImpl withStoragePath(String storagePath) {
        parameters.setStoragePath(storagePath);
        return this;
    }

    @Override
    public NetworkWatcher parent() {
        return parent;
    }

    @Override
    public Mono<Troubleshooting> executeWorkAsync() {
        return this.parent().manager().inner().networkWatchers()
                .getTroubleshootingAsync(parent.resourceGroupName(), parent.name(), parameters)
                .map(troubleshootingResultInner -> {
                    TroubleshootingImpl.this.result = troubleshootingResultInner;
                    return TroubleshootingImpl.this;
                });
    }

    // Getters

    @Override
    public String targetResourceId() {
        return parameters.getTargetResourceId();
    }

    @Override
    public String storageId() {
        return parameters.getStorageId();
    }

    @Override
    public String storagePath() {
        return parameters.getStoragePath();
    }

    @Override
    public OffsetDateTime startTime() {
        return result.getStartTime();
    }

    @Override
    public OffsetDateTime endTime() {
        return result.getEndTime();
    }

    @Override
    public String code() {
        return result.getCode();
    }

    @Override
    public List<TroubleshootingDetails> results() {
        return result.getResults();
    }
}
