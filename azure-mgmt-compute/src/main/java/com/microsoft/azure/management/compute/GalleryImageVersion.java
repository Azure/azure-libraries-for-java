/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.compute.implementation.ComputeManager;
import com.microsoft.azure.management.compute.implementation.GalleryImageVersionInner;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

/**
 * An immutable client-side representation of an Azure gallery image version.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_15_0)
public interface GalleryImageVersion extends HasInner<GalleryImageVersionInner>,
        Indexable,
        Refreshable<GalleryImageVersion>,
        Updatable<GalleryImageVersion.Update>,
        HasManager<ComputeManager> {
    /**
     * @return the ARM id of the image version.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String id();

    /**
     * @return the default location of the image version.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String location();

    /**
     * @return the image version name.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String name();

    /**
     * @return the provisioningState of image version resource.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String provisioningState();

    /**
     * @return the publishingProfile configuration of the image version.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    GalleryImageVersionPublishingProfile publishingProfile();

    /**
     * @return the regions in which the image version is available.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    List<TargetRegion> availableRegions();

    StorageAccountType storageAccountType();

    /**
     * @return the date indicating image version's end of life.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    DateTime endOfLifeDate();

    /**
     * @return true if the image version is excluded from considering as a
     * candidate when VM is created with 'latest' image version, false otherwise.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    Boolean isExcludedFromLatest();

    /**
     * @return the replicationStatus of image version in published regions.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    ReplicationStatus replicationStatus();

    /**
     * @return the image version storageProfile describing OS and data disks.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    GalleryImageVersionStorageProfile storageProfile();

    /**
     * @return the tags associated with the image version.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    Map<String, String> tags();

    /**
     * @return the type.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String type();

    /**
     * The entirety of the gallery image version definition.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    interface Definition extends DefinitionStages.Blank,
            DefinitionStages.WithImage,
            DefinitionStages.WithLocation,
            DefinitionStages.WithSource,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of gallery image version definition stages.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    interface DefinitionStages {
        /**
         * The first stage of a gallery image version definition.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface Blank extends WithImage {
        }

        /**
         * The stage of the gallery image version definition allowing to specify parent image.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithImage {
           /**
            * Specifies the image container to hold this image version.
            *
            * @param resourceGroupName the name of the resource group
            * @param galleryName the name of the gallery
            * @param galleryImageName the name of the gallery image
            * @return the next definition stage
            */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithLocation withExistingImage(String resourceGroupName, String galleryName, String galleryImageName);
        }

        /**
         * The stage of the gallery image version definition allowing to specify location.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithLocation {
           /**
            * Specifies the default location for the image version.
            *
            * @param location resource location
            * @return the next definition stage
            */
           @Beta(Beta.SinceVersion.V1_15_0)
           WithSource withLocation(String location);

            /**
             * Specifies location.
             *
             * @param location resource location
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithSource withLocation(Region location);
        }

        /**
         * The stage of the image version definition allowing to specify the source.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithSource {
            /**
             * Specifies that the provided custom image needs to be used as source of the image version.
             *
             * @param customImageId the ARM id of the custom image
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withSourceCustomImage(String customImageId);

            /**
             * Specifies that the provided custom image needs to be used as source of the image version.
             *
             * @param customImage the custom image
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withSourceCustomImage(VirtualMachineCustomImage customImage);
        }

        /**
         * The stage of image version definition allowing to specify the regions in which the image version
         * has to be available.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithAvailableRegion {
            /**
             * Specifies a region in which image version needs to be available.
             *
             * @param region the region
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withRegionAvailability(Region region, int replicaCount);

            /**
             * Specifies list of regions in which image version needs to be available.
             *
             * @param regions the region list
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withRegionAvailability(List<TargetRegion> regions);
        }

        /**
         * The stage of image version definition allowing to specify the storage account type.
         */
        @Beta(Beta.SinceVersion.V1_22_0)
        interface WithStorageAccountType {
            /**
             * Specifies the storage account type for this gallery image version.
             *
             * @param storageAccountType the storage account type for this gallery image version.
             * @return the next definition stage
             */
            WithCreate withStorageAccountType(StorageAccountType storageAccountType);
        }

        /**
         * The stage of the gallery image version definition allowing to specify end of life of the version.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithEndOfLifeDate {
            /**
             * Specifies end of life date of the image version.
             *
             * @param endOfLifeDate The end of life date
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withEndOfLifeDate(DateTime endOfLifeDate);
        }

        /**
         * The stage of the gallery image version definition allowing to specify that the version
         * should not be considered as a candidate version when VM is deployed with 'latest' as version
         * of the image.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithExcludeFromLatest {
            /**
             * Specifies that this version is not a candidate to consider as latest.
             *
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withExcludedFromLatest();
        }

        /**
         * The stage of the gallery image version definition allowing to specify Tags.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithTags {
            /**
             * Specifies tags.
             *
             * @param tags the resource tags
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withTags(Map<String, String> tags);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for
         * the resource to be created (via {@link WithCreate#create()}), but also allows
         * for any other optional settings to be specified.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithCreate extends Creatable<GalleryImageVersion>,
                DefinitionStages.WithAvailableRegion,
                DefinitionStages.WithStorageAccountType,
                DefinitionStages.WithEndOfLifeDate,
                DefinitionStages.WithExcludeFromLatest,
                DefinitionStages.WithTags {
        }
    }
    /**
     * The template for a gallery image version update operation, containing all the settings that can be modified.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    interface Update extends Appliable<GalleryImageVersion>,
            UpdateStages.WithAvailableRegion,
            UpdateStages.WithEndOfLifeDate,
            UpdateStages.WithExcludeFromLatest,
            UpdateStages.WithTags {
    }

    /**
     * Grouping of gallery image version update stages.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    interface UpdateStages {
        /**
         * The stage of image version update allowing to specify the regions in which the image version
         * has to be available.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithAvailableRegion {
            /**
             * Specifies a region in which image version needs to be available.
             *
             * @param region the region
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withRegionAvailability(Region region, int replicaCount);

            /**
             * Specifies list of regions in which image version needs to be available.
             *
             * @param regions the region list
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withRegionAvailability(List<TargetRegion> regions);

            /**
             * Specifies that an image version should be removed from an existing region serving it.
             *
             * @param region the region
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withoutRegionAvailability(Region region);
        }

        /**
         * The stage of the gallery image version update allowing to specify end of life of the version.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithEndOfLifeDate {
            /**
             * Specifies end of life date of the image version.
             *
             * @param endOfLifeDate The end of life of this gallery image
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withEndOfLifeDate(DateTime endOfLifeDate);
        }

        /**
         * The stage of the gallery image version definition allowing to specify whether this
         * version should be a candidate version to be considered when VM is deployed with 'latest'
         * as version of the image.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithExcludeFromLatest {
            /**
             * Specifies that this version is not a candidate to consider as latest.
             *
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withExcludedFromLatest();

            /**
             * Specifies that this version is a candidate to consider as latest.
             *
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withoutExcludedFromLatest();
        }

        /**
         * The stage of the gallery image version update allowing to specify Tags.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithTags {
            /**
             * Specifies tags.
             *
             * @param tags resource tags
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withTags(Map<String, String> tags);
        }
    }
}
