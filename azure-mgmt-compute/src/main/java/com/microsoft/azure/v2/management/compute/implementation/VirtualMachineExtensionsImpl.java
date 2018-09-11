/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.compute.implementation;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.compute.VirtualMachine;
import com.microsoft.azure.v2.management.compute.VirtualMachineExtension;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.collection.implementation.ExternalChildResourcesCachedImpl;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Represents a extension collection associated with a virtual machine.
 */
@LangDefinition
class VirtualMachineExtensionsImpl extends
        ExternalChildResourcesCachedImpl<VirtualMachineExtensionImpl,
                                VirtualMachineExtension,
                                VirtualMachineExtensionInner,
                                VirtualMachineImpl,
                                VirtualMachine> {
    private final VirtualMachineExtensionsInner client;

    /**
     * Creates new VirtualMachineExtensionsImpl.
     *
     * @param client the client to perform REST calls on extensions
     * @param parent the parent virtual machine of the extensions
     */
    VirtualMachineExtensionsImpl(VirtualMachineExtensionsInner client, VirtualMachineImpl parent) {
        super(parent,  parent.taskGroup(), "VirtualMachineExtension");
        this.client = client;
        this.cacheCollection();
    }

    /**
     * @return the extension as a map indexed by name.
     */
    public Map<String, VirtualMachineExtension> asMap() {
        return Collections.unmodifiableMap(this.asMapAsync().blockingLast(new HashMap<>()));
    }

    /**
     * @return an observable emits extensions in this collection as a map indexed by name.
     */
    public Observable<Map<String, VirtualMachineExtension>> asMapAsync() {
        return listAsync()
                .collect((Callable<Map<String, VirtualMachineExtension>>) () -> new HashMap<>(),
                        (map, extension) -> map.put(extension.name(), extension)).toObservable();
        }

    /**
     * @return an observable emits extensions in this collection
     */
    public Observable<VirtualMachineExtension> listAsync() {
        Observable<VirtualMachineExtensionImpl> extensions = Observable.fromIterable(this.collection().values());
        // Resolve reference getExtensions
        //
        Observable<VirtualMachineExtension> resolvedExtensionsStream = extensions
                .filter(extension -> extension.isReference())
                .flatMap(extension -> client.getAsync(parent().resourceGroupName(), parent().name(), extension.name())
                        .map(extensionInner -> new VirtualMachineExtensionImpl(extension.name(), parent(), extensionInner, client))
                        .toObservable());

        return resolvedExtensionsStream.concatWith(extensions.filter(extension -> !extension.isReference()));
    }

    /**
     * Starts an extension definition chain.
     *
     * @param name the reference name of the extension to be added
     * @return the extension
     */
    public VirtualMachineExtensionImpl define(String name) {
        VirtualMachineExtensionImpl newExtension = this.prepareInlineDefine(name);
        return newExtension;
    }

    /**
     * Starts an extension update chain.
     *
     * @param name the reference name of the extension to be updated
     * @return the extension
     */
    public VirtualMachineExtensionImpl update(String name) {
        return this.prepareInlineUpdate(name);
    }

    /**
     * Mark the extension with given name as to be removed.
     *
     * @param name the reference name of the extension to be removed
     */
    public void remove(String name) {
        this.prepareInlineRemove(name);
    }

    /**
     * Adds the extension to the collection.
     *
     * @param extension the extension
     */
    public void addExtension(VirtualMachineExtensionImpl extension) {
        this.addChildResource(extension);
    }

    @Override
    protected List<VirtualMachineExtensionImpl> listChildResources() {
        List<VirtualMachineExtensionImpl> childResources = new ArrayList<>();
        if (parent().inner().resources() != null) {
            for (VirtualMachineExtensionInner inner : parent().inner().resources()) {
                if (inner.name() == null) {
                    // This extension exists in the parent VM extension collection as a reference id.
                    //
                    inner.withLocation(parent().regionName());
                    childResources.add(new VirtualMachineExtensionImpl(ResourceUtils.nameFromResourceId(inner.id()),
                            this.parent(),
                            inner,
                            this.client));
                } else {
                    // This extension exists in the parent VM as a fully blown object
                    //
                    childResources.add(new VirtualMachineExtensionImpl(inner.name(),
                            this.parent(),
                            inner,
                            this.client));
                }
            }
        }
        return childResources;
    }

    @Override
    protected VirtualMachineExtensionImpl newChildResource(String name) {
        VirtualMachineExtensionImpl extension = VirtualMachineExtensionImpl
                .newVirtualMachineExtension(name, this.parent(), this.client);
        return extension;
    }
}
