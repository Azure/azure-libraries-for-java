/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.compute.implementation;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.compute.VirtualMachineExtensionImage;
import com.microsoft.azure.v2.management.compute.VirtualMachineExtensionImageType;
import com.microsoft.azure.v2.management.compute.VirtualMachineExtensionImageVersion;
import com.microsoft.azure.v2.management.compute.VirtualMachineExtensionImages;
import com.microsoft.azure.v2.management.compute.VirtualMachinePublisher;
import com.microsoft.azure.v2.management.compute.VirtualMachinePublishers;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.PagedListConverter;
import io.reactivex.Observable;

/**
 * The implementation for {@link VirtualMachineExtensionImages}.
 */
@LangDefinition
class VirtualMachineExtensionImagesImpl
        implements VirtualMachineExtensionImages {
    private final VirtualMachinePublishers publishers;

    VirtualMachineExtensionImagesImpl(VirtualMachinePublishers publishers) {
        this.publishers = publishers;
    }

    @Override
    public PagedList<VirtualMachineExtensionImage> listByRegion(Region region) {
        return listByRegion(region.toString());
    }

    @Override
    public PagedList<VirtualMachineExtensionImage> listByRegion(String regionName) {
        PagedList<VirtualMachinePublisher> publishers = this.publishers().listByRegion(regionName);

        PagedList<VirtualMachineExtensionImageType> extensionTypes =
                new ChildListFlattener<>(publishers, new ChildListFlattener.ChildListLoader<VirtualMachinePublisher, VirtualMachineExtensionImageType>() {
                    @Override
                    public PagedList<VirtualMachineExtensionImageType> loadList(VirtualMachinePublisher publisher)  {
                        return publisher.extensionTypes().list();
                    }
                }).flatten();

        PagedList<VirtualMachineExtensionImageVersion> extensionTypeVersions =
                new ChildListFlattener<>(extensionTypes, new ChildListFlattener.ChildListLoader<VirtualMachineExtensionImageType, VirtualMachineExtensionImageVersion>() {
                    @Override
                    public PagedList<VirtualMachineExtensionImageVersion> loadList(VirtualMachineExtensionImageType type)  {
                        return type.versions().list();
                    }
                }).flatten();

        PagedListConverter<VirtualMachineExtensionImageVersion, VirtualMachineExtensionImage> converter =
                new PagedListConverter<VirtualMachineExtensionImageVersion, VirtualMachineExtensionImage>() {
                    @Override
                    public Observable<VirtualMachineExtensionImage> typeConvertAsync(VirtualMachineExtensionImageVersion virtualMachineExtensionImageVersion) {
                        return Observable.just((VirtualMachineExtensionImage) virtualMachineExtensionImageVersion.getImage());
                    }
                };

        return converter.convert(extensionTypeVersions);
    }

    @Override
    public Observable<VirtualMachineExtensionImage> listByRegionAsync(Region region) {
        return listByRegionAsync(region.name());
    }

    @Override
    public Observable<VirtualMachineExtensionImage> listByRegionAsync(String regionName) {
        return this.publishers().listByRegionAsync(regionName)
                .flatMap(virtualMachinePublisher -> virtualMachinePublisher.extensionTypes().listAsync())
                .flatMap(virtualMachineExtensionImageType -> virtualMachineExtensionImageType.versions().listAsync())
                .flatMap(virtualMachineExtensionImageVersion -> virtualMachineExtensionImageVersion.getImageAsync());
    }

    @Override
    public VirtualMachinePublishers publishers() {
        return this.publishers;
    }
}