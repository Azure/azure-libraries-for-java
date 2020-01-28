/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.AzureReachabilityReport;
import com.azure.management.network.AzureReachabilityReportItem;
import com.azure.management.network.AzureReachabilityReportLocation;
import com.azure.management.network.AzureReachabilityReportParameters;
import com.azure.management.network.NetworkWatcher;
import com.azure.management.network.models.AzureReachabilityReportInner;
import com.azure.management.resources.fluentcore.model.implementation.ExecutableImpl;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The implementation of AzureReachabilityReport.
 */
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
        return inner.getAggregationLevel();
    }

    @Override
    public AzureReachabilityReportLocation providerLocation() {
        return inner.getProviderLocation();
    }

    @Override
    public List<AzureReachabilityReportItem> reachabilityReport() {
        return Collections.unmodifiableList(inner.getReachabilityReport());
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
    public Mono<AzureReachabilityReport> executeWorkAsync() {
        return this.parent().manager().inner().networkWatchers()
                .getAzureReachabilityReportAsync(parent().resourceGroupName(), parent().name(), parameters)
                .map(azureReachabilityReportListInner -> {
                    AzureReachabilityReportImpl.this.inner = azureReachabilityReportListInner;
                    return AzureReachabilityReportImpl.this;
                });
    }

    @Override
    public AzureReachabilityReportImpl withProviderLocation(String country) {
        parameters.setProviderLocation(new AzureReachabilityReportLocation().setCountry(country));
        return this;
    }

    @Override
    public AzureReachabilityReportImpl withProviderLocation(String country, String state) {
        parameters.setProviderLocation(new AzureReachabilityReportLocation().setCountry(country).setState(state));
        return this;
    }

    @Override
    public AzureReachabilityReportImpl withProviderLocation(String country, String state, String city) {
        parameters.setProviderLocation(new AzureReachabilityReportLocation().setCountry(country).setState(state).setCity(city));
        return this;
    }

    @Override
    public AzureReachabilityReportImpl withStartTime(OffsetDateTime startTime) {
        parameters.setStartTime(startTime);
        return this;
    }

    @Override
    public AzureReachabilityReportImpl withEndTime(OffsetDateTime endTime) {
        parameters.setEndTime(endTime);
        return this;
    }

    @Override
    public DefinitionStages.WithExecute withAzureLocations(String... azureLocations) {
        parameters.setAzureLocations(Arrays.asList(azureLocations));
        return this;
    }

    @Override
    public DefinitionStages.WithExecute withProviders(String... providers) {
        parameters.setProviders(Arrays.asList(providers));
        return this;
    }
}