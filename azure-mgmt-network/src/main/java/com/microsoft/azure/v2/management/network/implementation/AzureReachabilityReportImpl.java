/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.network.AzureReachabilityReport;
import com.microsoft.azure.v2.management.network.AzureReachabilityReportItem;
import com.microsoft.azure.v2.management.network.AzureReachabilityReportLocation;
import com.microsoft.azure.v2.management.network.AzureReachabilityReportParameters;
import com.microsoft.azure.v2.management.network.NetworkWatcher;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.ExecutableImpl;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The implementation of AzureReachabilityReport.
 */
@LangDefinition
class AzureReachabilityReportImpl extends ExecutableImpl<AzureReachabilityReport>
        implements AzureReachabilityReport,
        AzureReachabilityReport.Definition {
    private final NetworkWatcherImpl parent;
    private AzureReachabilityReportParameters parameters = new AzureReachabilityReportParameters();
    private AzureReachabilityReportInner inner;

    AzureReachabilityReportImpl(NetworkWatcherImpl parent) {
        this.parent = parent;
    }

    @Override
    public String aggregationLevel() {
        return inner.aggregationLevel();
    }

    @Override
    public AzureReachabilityReportLocation providerLocation() {
        return inner.providerLocation();
    }

    @Override
    public List<AzureReachabilityReportItem> reachabilityReport() {
        return Collections.unmodifiableList(inner.reachabilityReport());
    }

    @Override
    public AzureReachabilityReportParameters azureReachabilityReportParameters() {
        return parameters;
    }

    @Override
    public NetworkWatcher parent() {
        return parent;
    }

    @Override
    public AzureReachabilityReportInner inner() {
        return this.inner;
    }

    @Override
    public Observable<AzureReachabilityReport> executeWorkAsync() {
        return this.parent().manager().inner().networkWatchers()
                .getAzureReachabilityReportAsync(parent().resourceGroupName(), parent().name(), parameters)
                .map(azureReachabilityReportListInner -> {
                    AzureReachabilityReportImpl.this.inner = azureReachabilityReportListInner;
                    return (AzureReachabilityReport) AzureReachabilityReportImpl.this;
                })
                .switchIfEmpty(Single.just(((AzureReachabilityReport) AzureReachabilityReportImpl.this)))
                .toObservable();
    }

    @Override
    public AzureReachabilityReportImpl withProviderLocation(String country) {
        parameters.withProviderLocation(new AzureReachabilityReportLocation().withCountry(country));
        return this;
    }

    @Override
    public AzureReachabilityReportImpl withProviderLocation(String country, String state) {
        parameters.withProviderLocation(new AzureReachabilityReportLocation().withCountry(country).withState(state));
        return this;
    }

    @Override
    public AzureReachabilityReportImpl withProviderLocation(String country, String state, String city) {
        parameters.withProviderLocation(new AzureReachabilityReportLocation().withCountry(country).withState(state).withCity(city));
        return this;
    }

    @Override
    public AzureReachabilityReportImpl withStartTime(OffsetDateTime startTime) {
        parameters.withStartTime(startTime);
        return this;
    }

    @Override
    public AzureReachabilityReportImpl withEndTime(OffsetDateTime endTime) {
        parameters.withEndTime(endTime);
        return this;
    }

    @Override
    public DefinitionStages.WithExecute withAzureLocations(String... azureLocations) {
        parameters.withAzureLocations(Arrays.asList(azureLocations));
        return this;
    }

    @Override
    public DefinitionStages.WithExecute withProviders(String... providers) {
        parameters.withProviders(Arrays.asList(providers));
        return this;
    }
}