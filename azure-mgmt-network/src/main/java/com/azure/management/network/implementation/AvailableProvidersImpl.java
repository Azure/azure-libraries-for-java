/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.AvailableProviders;
import com.azure.management.network.AvailableProvidersListCountry;
import com.azure.management.network.AvailableProvidersListParameters;
import com.azure.management.network.NetworkWatcher;
import com.azure.management.network.models.AvailableProvidersListInner;
import com.azure.management.resources.fluentcore.model.implementation.ExecutableImpl;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The implementation of AvailableProviders.
 */
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
        List<AvailableProvidersListCountry> availableProvidersList = this.inner().getCountries();
        if (availableProvidersList != null) {
            for (AvailableProvidersListCountry resource : availableProvidersList) {
                this.providersByCountry.put(resource.getCountryName(), resource);
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
    public Mono<AvailableProviders> executeWorkAsync() {
        return this.parent().manager().inner().networkWatchers()
                .listAvailableProvidersAsync(parent().resourceGroupName(), parent().name(), parameters)
                .map(availableProvidersListInner -> {
                    AvailableProvidersImpl.this.inner = availableProvidersListInner;
                    AvailableProvidersImpl.this.initializeResourcesFromInner();
                    return AvailableProvidersImpl.this;
                });
    }

    @Override
    public AvailableProvidersImpl withAzureLocations(String... azureLocations) {
        parameters.setAzureLocations(Arrays.asList(azureLocations));
        return this;
    }

    @Override
    public AvailableProvidersImpl withAzureLocation(String azureLocation) {
        if (parameters.getAzureLocations() == null) {
            parameters.setAzureLocations(new ArrayList<String>());
        }
        parameters.getAzureLocations().add(azureLocation);
        return this;
    }

    @Override
    public AvailableProvidersImpl withCountry(String country) {
        parameters.setCountry(country);
        return this;
    }

    @Override
    public AvailableProvidersImpl withState(String state) {
        parameters.setState(state);
        return this;
    }

    @Override
    public AvailableProvidersImpl withCity(String city) {
        parameters.setCity(city);
        return this;
    }
}