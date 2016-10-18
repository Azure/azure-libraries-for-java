/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.trafficmanager.implementation;

import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.azure.management.trafficmanager.TrafficManagerAzureEndpoint;
import com.microsoft.azure.management.trafficmanager.TrafficManagerExternalEndpoint;
import com.microsoft.azure.management.trafficmanager.TrafficManagerNestedProfileEndpoint;
import com.microsoft.azure.management.trafficmanager.TrafficManagerProfile;
import com.microsoft.azure.management.trafficmanager.ProfileMonitorStatus;
import com.microsoft.azure.management.trafficmanager.TrafficRoutingMethod;
import rx.Observable;
import rx.functions.Func1;

import java.util.List;
import java.util.Map;

/**
 * Implementation for {@link TrafficManagerProfile}.
 */
class TrafficManagerProfileImpl
        extends GroupableResourceImpl<
        TrafficManagerProfile,
        ProfileInner,
        TrafficManagerProfileImpl,
        TrafficManager>
        implements
        TrafficManagerProfile,
        TrafficManagerProfile.Definition,
        TrafficManagerProfile.Update {
    private final ProfilesInner innerCollection;
    private final EndpointsInner endpointsClient;
    private final String profileStatusDisabled = "Disabled";
    private final String profileStatusEnabled = "Enabled";
    private TrafficManagerEndpointsImpl endpoints;

    TrafficManagerProfileImpl(String name,
                              final ProfileInner innerModel,
                              final ProfilesInner innerCollection,
                              final EndpointsInner endpointsClient,
                              final TrafficManager trafficManager) {
        super(name, innerModel, trafficManager);
        this.innerCollection = innerCollection;
        this.endpointsClient = endpointsClient;
        this.endpoints = new TrafficManagerEndpointsImpl(endpointsClient, this);
    }

    @Override
    public String dnsLabel() {
        return this.inner().dnsConfig().relativeName();
    }

    @Override
    public String fqdn() {
        return this.inner().dnsConfig().fqdn();
    }

    @Override
    public int timeToLive() {
        return this.inner().dnsConfig().ttl().intValue();
    }

    @Override
    public boolean isEnabled() {
        return this.inner().profileStatus().equalsIgnoreCase(this.profileStatusEnabled);
    }

    @Override
    public TrafficRoutingMethod trafficRoutingMethod() {
        return TrafficRoutingMethod.fromValue(this.inner().trafficRoutingMethod());
    }

    @Override
    public ProfileMonitorStatus monitorStatus() {
        return new ProfileMonitorStatus(this.inner().monitorConfig().profileMonitorStatus());
    }

    @Override
    public int monitoringPort() {
        return this.inner().monitorConfig().port().intValue();
    }

    @Override
    public String monitoringPath() {
        return this.inner().monitorConfig().path();
    }

    // TODO Expose monitoring protocol

    @Override
    public Map<String, TrafficManagerExternalEndpoint> externalEndpoints() {
        return this.endpoints.externalEndpointsAsMap();
    }

    @Override
    public Map<String, TrafficManagerAzureEndpoint> azureEndpoints() {
        return this.endpoints.azureEndpointsAsMap();
    }

    @Override
    public Map<String, TrafficManagerNestedProfileEndpoint> nestedProfileEndpoints() {
        return this.endpoints.nestedProfileEndpointsAsMap();
    }

    @Override
    public TrafficManagerProfile refresh() {
        ProfileInner inner = this.innerCollection.get(this.resourceGroupName(), this.name());
        this.setInner(inner);
        this.endpoints.refresh();
        return this;
    }

    @Override
    public TrafficManagerProfileImpl withLeafDomainLabel(String dnsLabel) {
        this.inner().dnsConfig().withRelativeName(dnsLabel);
        return this;
    }

    @Override
    public TrafficManagerProfileImpl withPriorityBasedRouting() {
        this.withTrafficRoutingMethod(TrafficRoutingMethod.PRIORITY);
        return this;
    }

    @Override
    public TrafficManagerProfileImpl withWeightBasedRouting() {
        this.withTrafficRoutingMethod(TrafficRoutingMethod.WEIGHTED);
        return this;
    }

    @Override
    public TrafficManagerProfileImpl withPerformanceBasedRouting() {
        this.withTrafficRoutingMethod(TrafficRoutingMethod.PERFORMANCE);
        return this;
    }

    @Override
    public TrafficManagerProfileImpl withTrafficRoutingMethod(TrafficRoutingMethod routingMethod) {
        this.inner().withTrafficRoutingMethod(routingMethod.toString());
        return this;
    }

    @Override
    public TrafficManagerEndpointImpl defineAzureTargetEndpoint(String name) {
        return this.endpoints.defineAzureTargetEndpoint(name);
    }

    @Override
    public TrafficManagerEndpointImpl defineExternalTargetEndpoint(String name) {
        return this.endpoints.defineExteralTargetEndpoint(name);
    }

    @Override
    public TrafficManagerEndpointImpl defineNestedTargetEndpoint(String name) {
        return this.endpoints.defineNestedProfileTargetEndpoint(name);
    }

    @Override
    public TrafficManagerEndpointImpl updateAzureTargetEndpoint(String name) {
        return this.endpoints.updateAzureEndpoint(name);
    }

    @Override
    public TrafficManagerEndpointImpl updateExternalTargetEndpoint(String name) {
        return this.endpoints.updateExternalEndpoint(name);
    }

    @Override
    public TrafficManagerEndpointImpl updateNestedProfileTargetEndpoint(String name) {
        return this.endpoints.updateNestedProfileEndpoint(name);
    }

    @Override
    public TrafficManagerProfileImpl withoutEndpoint(String name) {
        this.endpoints.remove(name);
        return this;
    }

    @Override
    public TrafficManagerProfileImpl withHttpMonitoring() {
        return this.withHttpMonitoring(80, "/");
    }

    @Override
    public TrafficManagerProfileImpl withHttpsMonitoring() {
        return this.withHttpsMonitoring(443, "/");
    }

    @Override
    public TrafficManagerProfileImpl withHttpMonitoring(int port, String path) {
        this.inner().monitorConfig()
            .withPort(new Long(port))
            .withPath(path)
            .withProtocol("http");
        return this;
    }

    @Override
    public TrafficManagerProfileImpl withHttpsMonitoring(int port, String path) {
        this.inner().monitorConfig()
            .withPort(new Long(port))
            .withPath(path)
            .withProtocol("https");
        return this;
    }

    @Override
    public TrafficManagerProfileImpl withProfileStatusDisabled() {
        this.inner().withProfileStatus(this.profileStatusDisabled);
        return this;
    }

    @Override
    public TrafficManagerProfileImpl withProfileStatusEnabled() {
        this.inner().withProfileStatus(this.profileStatusEnabled);
        return this;
    }

    @Override
    public TrafficManagerProfileImpl withTimeToLive(int ttlInSeconds) {
        this.inner().dnsConfig().withTtl(new Long(ttlInSeconds));
        return this;
    }

    @Override
    public Observable<TrafficManagerProfile> createResourceAsync() {
        final TrafficManagerProfileImpl self = this;
        return innerCollection.createOrUpdateAsync(resourceGroupName(), name(), inner())
                .map(new Func1<ProfileInner, TrafficManagerProfile>() {
                    @Override
                    public TrafficManagerProfile call(ProfileInner profileInner) {
                        self.setInner(profileInner);
                        return self;
                    }
                }).flatMap(new Func1<TrafficManagerProfile, Observable<? extends TrafficManagerProfile>>() {
                    @Override
                    public Observable<? extends TrafficManagerProfile> call(TrafficManagerProfile profile) {
                        return self.endpoints.commitAndGetAllAsync()
                                .map(new Func1<List<TrafficManagerEndpointImpl>, TrafficManagerProfile>() {
                                    @Override
                                    public TrafficManagerProfile call(List<TrafficManagerEndpointImpl> endpoints) {
                                        return self;
                                    }
                                });
                    }
                });
    }

    @Override
    public Observable<TrafficManagerProfile> updateResourceAsync() {
        final TrafficManagerProfileImpl self = this;
        // In update we first commit the endpoints then update profile, the reason is through portal and direct API
        // call one can create endpoints without properties those are not applicable for the profile's current routing
        // method. We cannot update the routing method of the profile until existing endpoints contains the properties
        // required for the new routing method.
        return self.endpoints.commitAndGetAllAsync()
                .flatMap(new Func1<List<TrafficManagerEndpointImpl>, Observable<? extends TrafficManagerProfile>>() {
                    public Observable<? extends TrafficManagerProfile> call(List<TrafficManagerEndpointImpl> endpoints) {
                        return innerCollection.createOrUpdateAsync(resourceGroupName(), name(), inner())
                            .map(new Func1<ProfileInner, TrafficManagerProfile>() {
                                    @Override
                                    public TrafficManagerProfile call(ProfileInner profileInner) {
                                self.setInner(profileInner);
                                return self;
                                }
                                });
                    }
                });
    }

    TrafficManagerProfileImpl withEndpoint(TrafficManagerEndpointImpl endpoint) {
        this.endpoints.addEndpoint(endpoint);
        return this;
    }
}
