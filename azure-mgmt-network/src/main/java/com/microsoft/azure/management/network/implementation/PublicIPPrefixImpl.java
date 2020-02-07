/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.network.implementation;


import com.microsoft.azure.SubResource;
import com.microsoft.azure.management.network.IPVersion;
import com.microsoft.azure.management.network.IpTag;
import com.microsoft.azure.management.network.ProvisioningState;
import com.microsoft.azure.management.network.PublicIPPrefix;
import com.microsoft.azure.management.network.PublicIPPrefixSku;
import com.microsoft.azure.management.network.ReferencedPublicIpAddress;
import com.microsoft.azure.management.network.model.AppliableWithTags;
import com.microsoft.azure.management.resources.fluentcore.arm.AvailabilityZoneId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class PublicIPPrefixImpl extends GroupableResourceImpl<PublicIPPrefix, PublicIPPrefixInner, PublicIPPrefixImpl, NetworkManager>
        implements PublicIPPrefix,
        PublicIPPrefix.Definition,
        PublicIPPrefix.Update,
        AppliableWithTags<PublicIPPrefix> {

    PublicIPPrefixImpl(String name, PublicIPPrefixInner inner, NetworkManager manager) {
        super(name, inner, manager);
    }

    @Override
    public Observable<PublicIPPrefix> createResourceAsync() {
        PublicIPPrefixesInner client = this.manager().inner().publicIPPrefixes();
        return client.createOrUpdateAsync(this.resourceGroupName(), this.name(), this.inner())
                .map(innerToFluentMap(this));
    }

    @Override
    public Observable<PublicIPPrefix> updateResourceAsync() {
        PublicIPPrefixesInner client = this.manager().inner().publicIPPrefixes();
        return client.createOrUpdateAsync(this.resourceGroupName(), this.name(), this.inner())
                .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<PublicIPPrefixInner> getInnerAsync() {
        PublicIPPrefixesInner client = this.manager().inner().publicIPPrefixes();
        return client.getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public PublicIPPrefixImpl updateTags() {
        return this;
    }

    @Override
    public PublicIPPrefix applyTags() {
        return applyTagsAsync().toBlocking().last();
    }

    @Override
    public Observable<PublicIPPrefix> applyTagsAsync() {
        return this.manager().inner().publicIPPrefixes().updateTagsAsync(resourceGroupName(), name(), inner().getTags())
                .flatMap(new Func1<PublicIPPrefixInner, Observable<PublicIPPrefix>>() {
                    @Override
                    public Observable<PublicIPPrefix> call(PublicIPPrefixInner inner) {
                        setInner(inner);
                        return Observable.just((PublicIPPrefix) PublicIPPrefixImpl.this);
                    }
                });
    }

    @Override
    public ServiceFuture<PublicIPPrefix> applyTagsAsync(ServiceCallback<PublicIPPrefix> callback) {
        return ServiceFuture.fromBody(applyTagsAsync(), callback);
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }

    @Override
    public String ipPrefix() {
        return this.inner().ipPrefix();
    }

    @Override
    public List<IpTag> ipTags() {
        return Collections.unmodifiableList(inner().ipTags() == null ? new ArrayList<IpTag>() : inner().ipTags());
    }

    @Override
    public SubResource loadBalancerFrontendIpConfiguration() {
        return this.inner().loadBalancerFrontendIpConfiguration();
    }

    @Override
    public Integer prefixLength() {
        return this.inner().prefixLength();
    }

    @Override
    public ProvisioningState provisioningState() {
        return this.inner().provisioningState();
    }

    @Override
    public List<ReferencedPublicIpAddress> publicIPAddresses() {
        return Collections.unmodifiableList(inner().publicIPAddresses() == null ? new ArrayList<ReferencedPublicIpAddress>() : this.inner().publicIPAddresses());
    }

    @Override
    public IPVersion publicIPAddressVersion() {
        return this.inner().publicIPAddressVersion();
    }

    @Override
    public String resourceGuid() {
        return this.inner().resourceGuid();
    }

    @Override
    public PublicIPPrefixSku sku() {
        return this.inner().sku();
    }

    @Override
    public Set<AvailabilityZoneId> availabilityZones() {
        Set<AvailabilityZoneId> zones = new HashSet<>();
        if (this.inner().zones() != null) {
            for (String zone : this.inner().zones()) {
                zones.add(AvailabilityZoneId.fromString(zone));
            }
        }
        return Collections.unmodifiableSet(zones);
    }

    @Override
    public PublicIPPrefixImpl withIpTags(List<IpTag> ipTags) {
        this.inner().withIpTags(ipTags);
        return this;
    }

    @Override
    public PublicIPPrefixImpl withPrefixLength(Integer prefixLength) {
        this.inner().withPrefixLength(prefixLength);
        return this;
    }

    @Override
    public PublicIPPrefixImpl withPublicIPAddressVersion(IPVersion publicIPAddressVersion) {
        this.inner().withPublicIPAddressVersion(publicIPAddressVersion);
        return this;
    }

    @Override
    public PublicIPPrefixImpl withSku(PublicIPPrefixSku sku) {
        this.inner().withSku(sku);
        return this;
    }

    @Override
    public PublicIPPrefixImpl withAvailabilityZone(AvailabilityZoneId zoneId) {
        // Note: Zone is not updatable as of now, so this is available only during definition time.
        // Service return `ResourceAvailabilityZonesCannotBeModified` upon attempt to append a new
        // zone or remove one. Trying to remove the last one means attempt to change resource from
        // zonal to regional, which is not supported.
        //
        if (this.inner().zones() == null) {
            this.inner().withZones(new ArrayList<String>());
        }
        this.inner().zones().add(zoneId.toString());
        return this;
    }
}
