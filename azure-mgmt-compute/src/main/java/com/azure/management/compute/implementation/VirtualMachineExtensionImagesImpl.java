/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.compute.implementation;

import com.microsoft.azure.PagedList;
import com.azure.management.apigeneration.LangDefinition;
import com.azure.management.compute.VirtualMachineExtensionImage;
import com.azure.management.compute.VirtualMachineExtensionImageType;
import com.azure.management.compute.VirtualMachineExtensionImageVersion;
import com.azure.management.compute.VirtualMachineExtensionImages;
import com.azure.management.compute.VirtualMachinePublisher;
import com.azure.management.compute.VirtualMachinePublishers;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.utils.PagedListConverter;
import rx.Observable;
import rx.functions.Func1;

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
                .flatMap(new Func1<VirtualMachinePublisher, Observable<VirtualMachineExtensionImageType>>() {
                    @Override
                    public Observable<VirtualMachineExtensionImageType> call(VirtualMachinePublisher virtualMachinePublisher) {
                        return virtualMachinePublisher.extensionTypes().listAsync();
                    }
                }).flatMap(new Func1<VirtualMachineExtensionImageType, Observable<VirtualMachineExtensionImageVersion>>() {
                    @Override
                    public Observable<VirtualMachineExtensionImageVersion> call(VirtualMachineExtensionImageType virtualMachineExtensionImageType) {
                        return virtualMachineExtensionImageType.versions().listAsync();
                    }
                }).flatMap(new Func1<VirtualMachineExtensionImageVersion, Observable<VirtualMachineExtensionImage>>() {
                    @Override
                    public Observable<VirtualMachineExtensionImage> call(VirtualMachineExtensionImageVersion virtualMachineExtensionImageVersion) {
                        return virtualMachineExtensionImageVersion.getImageAsync();
                    }
                });
    }

    @Override
    public VirtualMachinePublishers publishers() {
        return this.publishers;
    }
}