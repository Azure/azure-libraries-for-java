/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.NetworkWatcher;
import com.azure.management.network.PCFilter;
import com.azure.management.network.PacketCapture;
import com.azure.management.network.PacketCaptureFilter;
import com.azure.management.network.PacketCaptureStatus;
import com.azure.management.network.PacketCaptureStorageLocation;
import com.azure.management.network.ProvisioningState;
import com.azure.management.network.models.PacketCaptureInner;
import com.azure.management.network.models.PacketCaptureResultInner;
import com.azure.management.network.models.PacketCapturesInner;
import com.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.azure.management.resources.fluentcore.utils.Utils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation for Packet Capture and its create and update interfaces.
 */
public class PacketCaptureImpl extends
        CreatableUpdatableImpl<PacketCapture, PacketCaptureResultInner, PacketCaptureImpl>
        implements
        PacketCapture,
        PacketCapture.Definition {
    private final PacketCapturesInner client;
    private final PacketCaptureInner createParameters;
    private final NetworkWatcher parent;

    PacketCaptureImpl(String name, NetworkWatcherImpl parent, PacketCaptureResultInner innerObject,
                      PacketCapturesInner client) {
        super(name, innerObject);
        this.client = client;
        this.parent = parent;
        this.createParameters = new PacketCaptureInner();
    }

    @Override
    protected Mono<PacketCaptureResultInner> getInnerAsync() {
        return this.client.getAsync(parent.resourceGroupName(), parent.name(), name());
    }

    @Override
    public void stop() {
        stopAsync().block();
    }

    @Override
    public Mono<Void> stopAsync() {
        return this.client.stopAsync(parent.resourceGroupName(), parent.name(), name());
    }

    @Override
    public PacketCaptureStatus getStatus() {
        return getStatusAsync().block();
    }

    @Override
    public Mono<PacketCaptureStatus> getStatusAsync() {
        return this.client.getStatusAsync(parent.resourceGroupName(), parent.name(), name())
                .map(inner -> new PacketCaptureStatusImpl(inner));
    }

    @Override
    public PacketCaptureImpl withTarget(String target) {
        createParameters.setTarget(target);
        return this;
    }

    @Override
    public PacketCaptureImpl withStorageAccountId(String storageId) {
        PacketCaptureStorageLocation storageLocation = createParameters.getStorageLocation();
        if (storageLocation == null) {
            storageLocation = new PacketCaptureStorageLocation();
        }
        createParameters.setStorageLocation(storageLocation.setStorageId(storageId));
        return this;
    }

    @Override
    public DefinitionStages.WithCreate withStoragePath(String storagePath) {
        createParameters.getStorageLocation().setStoragePath(storagePath);
        return this;
    }

    @Override
    public PacketCaptureImpl withFilePath(String filePath) {
        PacketCaptureStorageLocation storageLocation = createParameters.getStorageLocation();
        if (storageLocation == null) {
            storageLocation = new PacketCaptureStorageLocation();
        }
        createParameters.setStorageLocation(storageLocation.setFilePath(filePath));
        return this;
    }

    @Override
    public PacketCaptureImpl withBytesToCapturePerPacket(int bytesToCapturePerPacket) {
        createParameters.setBytesToCapturePerPacket(bytesToCapturePerPacket);
        return this;
    }

    @Override
    public PacketCaptureImpl withTotalBytesPerSession(int totalBytesPerSession) {
        createParameters.setTotalBytesPerSession(totalBytesPerSession);
        return this;
    }

    @Override
    public PacketCaptureImpl withTimeLimitInSeconds(int timeLimitInSeconds) {
        createParameters.setTimeLimitInSeconds(timeLimitInSeconds);
        return this;
    }

    @Override
    public PCFilter.Definition<DefinitionStages.WithCreate> definePacketCaptureFilter() {
        return new PCFilterImpl(new PacketCaptureFilter(), this);
    }

    void attachPCFilter(PCFilterImpl pcFilter) {
        if (createParameters.getFilters() == null) {
            createParameters.setFilters(new ArrayList<PacketCaptureFilter>());
        }
        createParameters.getFilters().add(pcFilter.inner());
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().getId() == null;
    }

    @Override
    public Mono<PacketCapture> createResourceAsync() {
        return this.client.createAsync(parent.resourceGroupName(), parent.name(), this.name(), createParameters)
                .map(innerToFluentMap(this));
    }

    @Override
    public String id() {
        return inner().getId();
    }

    @Override
    public String targetId() {
        return inner().getTarget();
    }

    @Override
    public int bytesToCapturePerPacket() {
        return Utils.toPrimitiveInt(inner().getBytesToCapturePerPacket());
    }

    @Override
    public int totalBytesPerSession() {
        return Utils.toPrimitiveInt(inner().getTotalBytesPerSession());
    }

    @Override
    public int timeLimitInSeconds() {
        return Utils.toPrimitiveInt(inner().getTimeLimitInSeconds());
    }

    @Override
    public PacketCaptureStorageLocation storageLocation() {
        return inner().getStorageLocation();
    }

    @Override
    public List<PacketCaptureFilter> filters() {
        return inner().getFilters();
    }

    @Override
    public ProvisioningState provisioningState() {
        return inner().getProvisioningState();
    }
}
