/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.network.NetworkWatcher;
import com.microsoft.azure.management.network.Troubleshooting;
import com.microsoft.azure.management.network.TroubleshootingDetails;
import com.microsoft.azure.management.network.TroubleshootingParameters;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.ExecutableImpl;
import org.joda.time.DateTime;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;

/**
 * Implementation of Troubleshooting interface.
 */
@LangDefinition
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
        parameters.withTargetResourceId(targetResourceId);
        return this;
    }

    @Override
    public TroubleshootingImpl withStorageAccount(String storageAccountId) {
        parameters.withStorageId(storageAccountId);
        return this;
    }

    @Override
    public TroubleshootingImpl withStoragePath(String storagePath) {
        parameters.withStoragePath(storagePath);
        return this;
    }

    @Override
    public NetworkWatcher parent() {
        return parent;
    }

    @Override
    public Observable<Troubleshooting> executeWorkAsync() {
        return this.parent().manager().inner().networkWatchers()
                .getTroubleshootingAsync(parent.resourceGroupName(), parent.name(), parameters)
                .map(new Func1<TroubleshootingResultInner, Troubleshooting>() {
                    @Override
                    public Troubleshooting call(TroubleshootingResultInner troubleshootingResultInner) {
                        TroubleshootingImpl.this.result = troubleshootingResultInner;
                        return TroubleshootingImpl.this;
                    }
                });
    }

    // Getters

    @Override
    public String targetResourceId() {
        return parameters.targetResourceId();
    }

    @Override
    public String storageId() {
        return parameters.storageId();
    }

    @Override
    public String storagePath() {
        return parameters.storagePath();
    }

    @Override
    public DateTime startTime() {
        return result.startTime();
    }

    @Override
    public DateTime endTime() {
        return result.endTime();
    }

    @Override
    public String code() {
        return result.code();
    }

    @Override
    public List<TroubleshootingDetails> results() {
        return result.results();
    }
}
