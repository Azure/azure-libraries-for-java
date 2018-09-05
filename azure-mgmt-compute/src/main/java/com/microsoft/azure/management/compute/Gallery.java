/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.compute.implementation.ComputeManager;
import com.microsoft.azure.management.compute.implementation.GalleryInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import rx.Observable;

/**
 * An immutable client-side representation of an Azure gallery.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_15_0)
public interface Gallery extends HasInner<GalleryInner>,
        Resource,
        GroupableResource<ComputeManager, GalleryInner>,
        HasResourceGroup,
        Refreshable<Gallery>,
        Updatable<Gallery.Update>,
        HasManager<ComputeManager> {
    /**
     * @return description for the gallery resource.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String description();

    /**
     * @return the unique name of the gallery resource.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String uniqueName();

    /**
     * @return the provisioning state of the gallery resource.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String provisioningState();

    /**
     * Retrieves information about an image in the gallery.
     *
     * @param imageName The name of the image.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    Observable<GalleryImage> getImageAsync(String imageName);

    /**
     * Retrieves information about an image in the gallery.
     *
     * @param imageName The name of the image.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the gallery image
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    GalleryImage getImage(String imageName);

    /**
     * List images in the gallery.
     *
     * @return the observable for the request
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    Observable<GalleryImage> listImagesAsync();

    /**
     * List images in the gallery.
     *
     * @return the list of images in the gallery
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    PagedList<GalleryImage> listImages();

    /**
     * The entirety of the gallery definition.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    interface Definition extends DefinitionStages.Blank, DefinitionStages.WithGroup, DefinitionStages.WithCreate {
    }

    /**
     * Grouping of gallery definition stages.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    interface DefinitionStages {
        /**
         * The first stage of a gallery definition.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface Blank extends GroupableResource.DefinitionWithRegion<WithGroup> {
        }

        /**
         * The stage of the gallery definition allowing to specify the resource group.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithGroup extends GroupableResource.DefinitionStages.WithGroup<WithCreate> {
        }

        /**
         * The stage of the gallery definition allowing to specify description.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithDescription {
            /**
             * Specifies description for the gallery.
             *
             * @param description The description
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withDescription(String description);
        }

        /**
         * The stage of the definition which contains all the minimum required inputs for
         * the resource to be created (via {@link WithCreate#create()}), but also allows
         * for any other optional settings to be specified.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithCreate extends Creatable<Gallery>,
                Resource.DefinitionWithTags<WithCreate>,
                DefinitionStages.WithDescription {
        }
    }
    /**
     * The template for a Gallery update operation, containing all the settings that can be modified.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    interface Update extends Appliable<Gallery>,
            Resource.UpdateWithTags<Update>,
            UpdateStages.WithDescription {
    }

    /**
     * Grouping of gallery update stages.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    interface UpdateStages {
        /**
         * The stage of the gallery update allowing to specify description.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithDescription {
            /**
             * Specifies description for the gallery.
             * @param description The description
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withDescription(String description);
        }
    }
}
