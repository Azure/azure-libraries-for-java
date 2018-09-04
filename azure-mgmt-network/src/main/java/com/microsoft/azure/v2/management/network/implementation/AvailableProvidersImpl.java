/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.network.AvailableProviders;
import com.microsoft.azure.v2.management.network.AvailableProvidersListCountry;
import com.microsoft.azure.v2.management.network.AvailableProvidersListParameters;
import com.microsoft.azure.v2.management.network.NetworkWatcher;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.ExecutableImpl;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The implementation of AvailableProviders.
 */
@LangDefinition
class AvailableProvidersImpl extends ExecutableImpl<AvailableProviders>
        implements AvailableProviders,
        AvailableProviders.Definition {
    private Map<String, AvailableProvidersListCountry> providersByCountry;
    private final NetworkWatcherImpl parent;
    private AvailableProvidersListParameters parameters = new AvailableProvidersListParameters();
    private AvailableProvidersListInner inner;

    AvailableProvidersImpl(NetworkWatcherImpl parent) {
        this.parent = parent;
    }

    @Override
    public AvailableProvidersListParameters availableProvidersParameters() {
        return parameters;
    }

    @Override
    public Map<String, AvailableProvidersListCountry> providersByCountry() {
        return Collections.unmodifiableMap(this.providersByCountry);
    }

    private void initializeResourcesFromInner() {
        this.providersByCountry = new TreeMap<>();
        List<AvailableProvidersListCountry> availableProvidersList = this.inner().countries();
        if (availableProvidersList != null) {
            for (AvailableProvidersListCountry resource : availableProvidersList) {
                this.providersByCountry.put(resource.countryName(), resource);
            }
        }
    }

    @Override
    public NetworkWatcher parent() {
        return parent;
    }

    @Override
    public AvailableProvidersListInner inner() {
        return this.inner;
    }

    @Override
    public Observable<AvailableProviders> executeWorkAsync() {
        return this.parent().manager().inner().networkWatchers()
                .listAvailableProvidersAsync(parent().resourceGroupName(), parent().name(), parameters)
                .map(availableProvidersListInner -> {
                    AvailableProvidersImpl.this.inner = availableProvidersListInner;
                    AvailableProvidersImpl.this.initializeResourcesFromInner();
                    return (AvailableProviders) AvailableProvidersImpl.this;
                })
                .switchIfEmpty(Single.just(((AvailableProviders) AvailableProvidersImpl.this)))
                .toObservable();
    }

    @Override
    public AvailableProvidersImpl withAzureLocations(String... azureLocations) {
        parameters.withAzureLocations(Arrays.asList(azureLocations));
        return this;
    }

    @Override
    public AvailableProvidersImpl withAzureLocation(String azureLocation) {
        if (parameters.azureLocations() == null) {
            parameters.withAzureLocations(new ArrayList<String>());
        }
        parameters.azureLocations().add(azureLocation);
        return this;
    }

    @Override
    public AvailableProvidersImpl withCountry(String country) {
        parameters.withCountry(country);
        return this;
    }

    @Override
    public AvailableProvidersImpl withState(String state) {
        parameters.withState(state);
        return this;
    }

    @Override
    public AvailableProvidersImpl withCity(String city) {
        parameters.withCity(city);
        return this;
    }
}