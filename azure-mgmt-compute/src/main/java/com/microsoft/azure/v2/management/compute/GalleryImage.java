/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.v2.management.compute.implementation.ComputeManager;
import com.microsoft.azure.v2.management.compute.implementation.GalleryImageInner;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasManager;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import org.joda.time.DateTime;
import rx.Observable;

import java.util.List;
import java.util.Map;

/**
 * An immutable client-side representation of an Azure gallery image.
 * A gallery image resource is a container for multiple versions of the same image.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_15_0)
public interface GalleryImage extends HasInner<GalleryImageInner>,
        Indexable,
        Refreshable<GalleryImage>,
        Updatable<GalleryImage.Update>,
        HasManager<ComputeManager> {
    /**
     * @return the description of the image.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String description();

    /**
     * @return the disk types not supported by the image.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    List<DiskSkuTypes> unsupportedDiskTypes();

    /**
     * @return a description of features not supported by the image.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    Disallowed disallowed();

    /**
     * @return the date indicating image's end of life.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    DateTime endOfLifeDate();

    /**
     * @return the image eula.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String eula();

    /**
     * @return the ARM id of the image.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String id();

    /**
     * @return an identifier describing publisher, offer and sku of the image.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    GalleryImageIdentifier identifier();

    /**
     * @return the location of the image.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String location();

    /**
     * @return the image name.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String name();

    /**
     * @return the OS state of the image.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    OperatingSystemStateTypes osState();

    /**
     * @return the image OS type.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    OperatingSystemTypes osType();

    /**
     * @return the uri to image privacy statement.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String privacyStatementUri();

    /**
     * @return the provisioningState of image resource.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String provisioningState();

    /**
     * @return the purchasePlan of the image.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    ImagePurchasePlan purchasePlan();

    /**
     * @return the value describing recommended configuration for a virtual machine
     * based on this image.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    RecommendedMachineConfiguration recommendedVirtualMachineConfiguration();

    /**
     * @return the uri to the image release note.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String releaseNoteUri();

    /**
     * @return the tags associated with the image.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    Map<String, String> tags();

    /**
     * @return the type value.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    String type();

    /**
     * Retrieves information about an image version.
     *
     * @param versionName The name of the image.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable for the request
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    Observable<GalleryImageVersion> getVersionAsync(String versionName);

    /**
     * Retrieves information about an image version.
     *
     * @param versionName The name of the image version.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the image version
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    GalleryImageVersion getVersion(String versionName);

    /**
     * List image versions.
     *
     * @return the observable for the request
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    Observable<GalleryImageVersion> listVersionsAsync();

    /**
     * List image versions.
     *
     * @return the list of image versions
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    PagedList<GalleryImageVersion> listVersions();

    /**
     * The entirety of the gallery image definition.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    interface Definition extends DefinitionStages.Blank,
            DefinitionStages.WithGallery,
            DefinitionStages.WithLocation,
            DefinitionStages.WithIdentifier,
            DefinitionStages.WithOsTypeAndState,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of gallery image definition stages.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    interface DefinitionStages {
        /**
         * The first stage of a gallery image definition.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface Blank extends WithGallery {
        }

        /**
         * The stage of the gallery image definition allowing to specify parent gallery it belongs to.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithGallery {
           /**
            * Specifies the gallery in which this image resides.
            *
            * @param resourceGroupName The name of the resource group
            * @param galleryName The name of the gallery
            * @return the next definition stage
            */
           @Beta(Beta.SinceVersion.V1_15_0)
            WithLocation withExistingGallery(String resourceGroupName, String galleryName);

            /**
             * Specifies the gallery in which this image resides.
             *
             * @param gallery the gallery
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithLocation withExistingGallery(Gallery gallery);
        }

        /**
         * The stage of the gallery image definition allowing to specify location of the image.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithLocation {
           /**
            * Specifies location.
            *
            * @param location resource location
            * @return the next definition stage
            */
           @Beta(Beta.SinceVersion.V1_15_0)
           WithIdentifier withLocation(String location);

            /**
             * Specifies location.
             *
             * @param location resource location
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithIdentifier withLocation(Region location);
        }

        /**
         * The stage of the gallery image definition allowing to specify identifier that
         * identifies publisher, offer and sku of the image.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithIdentifier {
            /**
             * Specifies identifier (publisher, offer and sku) for the image.
             *
             * @param identifier the identifier parameter value
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithOsTypeAndState withIdentifier(GalleryImageIdentifier identifier);

            /**
             * Specifies an identifier (publisher, offer and sku) for the image.
             *
             * @param publisher image publisher name
             * @param offer image offer name
             * @param sku image sku name
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithOsTypeAndState withIdentifier(String publisher, String offer, String sku);
        }

        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithOsTypeAndState {
            /**
             * Specifies that image is a Windows image with OS state as generalized.
             *
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withGeneralizedWindows();

            /**
             * Specifies that image is a Linux image with OS state as generalized.
             *
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withGeneralizedLinux();

            /**
             * Specifies that image is a Windows image.
             *
             * @param osState operating system state
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withWindows(OperatingSystemStateTypes osState);

            /**
             * Specifies that image is a Linux image.
             *
             * @param osState operating system state
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withLinux(OperatingSystemStateTypes osState);
        }

        /**
         * The stage of the gallery image definition allowing to specify description.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithDescription {
            /**
             * Specifies description.
             *
             * @param description the description of the gallery image
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withDescription(String description);
        }

        /**
         * The stage of the gallery image definition allowing to specify settings disallowed
         * for a virtual machine based on the image.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithDisallowed {
            /**
             * Specifies the disk type not supported by the image.
             *
             * @param diskType the disk type
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withUnsupportedDiskType(DiskSkuTypes diskType);

            /**
             * Specifies the disk types not supported by the image.
             *
             * @param diskTypes the disk types
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withUnsupportedDiskTypes(List<DiskSkuTypes> diskTypes);

            /**
             * Specifies disallowed settings.
             *
             * @param disallowed the disallowed settings
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withDisallowed(Disallowed disallowed);
        }

        /**
         * The stage of the gallery image definition allowing to specify end of life of the version.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithEndOfLifeDate {
            /**
             * Specifies end of life date of the image.
             *
             * @param endOfLifeDate the end of life of the gallery image
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withEndOfLifeDate(DateTime endOfLifeDate);
        }

        /**
         * The stage of the gallery image definition allowing to specify eula.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithEula {
            /**
             * Specifies eula.
             *
             * @param eula the Eula agreement for the gallery image
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withEula(String eula);
        }

        /**
         * The stage of the gallery image definition allowing to specify privacy statement uri.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithPrivacyStatementUri {
            /**
             * Specifies image privacy statement uri.
             *
             * @param privacyStatementUri The privacy statement uri
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withPrivacyStatementUri(String privacyStatementUri);
        }

        /**
         * The stage of the gallery image definition allowing to specify purchase plan.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithPurchasePlan {
            /**
             * Specifies purchase plan for this image.
             *
             * @param name plan name
             * @param publisher publisher name
             * @param product product name
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withPurchasePlan(String name, String publisher, String product);

            /**
             * Specifies purchase plan for this image.
             *
             * @param purchasePlan the purchase plan
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withPurchasePlan(ImagePurchasePlan purchasePlan);
        }

        /**
         * The stage of the gallery image definition allowing to specify recommended
         * configuration for the virtual machine.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithRecommendedVMConfiguration {
            /**
             * Specifies the recommended minimum number of virtual CUPs for the virtual machine bases on the image.
             *
             * @param minCount the minimum number of virtual CPUs
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withRecommendedMinimumCPUsCountForVirtualMachine(int minCount);

            /**
             * Specifies the recommended maximum number of virtual CUPs for the virtual machine bases on this image.
             *
             * @param maxCount the maximum number of virtual CPUs
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withRecommendedMaximumCPUsCountForVirtualMachine(int maxCount);

            /**
             * Specifies the recommended virtual CUPs for the virtual machine bases on the image.
             *
             * @param minCount the minimum number of virtual CPUs
             * @param maxCount the maximum number of virtual CPUs
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withRecommendedCPUsCountForVirtualMachine(int minCount, int maxCount);

            /**
             * Specifies the recommended minimum memory for the virtual machine bases on the image.
             *
             * @param minMB the minimum memory in MB
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withRecommendedMinimumMemoryForVirtualMachine(int minMB);

            /**
             * Specifies the recommended maximum memory for the virtual machine bases on the image.
             *
             * @param maxMB the maximum memory in MB
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withRecommendedMaximumMemoryForVirtualMachine(int maxMB);

            /**
             * Specifies the recommended memory for the virtual machine bases on the image.
             *
             * @param minMB the minimum memory in MB
             * @param maxMB the maximum memory in MB
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withRecommendedMemoryForVirtualMachine(int minMB, int maxMB);

            /**
             * Specifies recommended configuration for the virtual machine based on the image.
             *
             * @param recommendedConfig the recommended configuration
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withRecommendedConfigurationForVirtualMachine(RecommendedMachineConfiguration recommendedConfig);
        }

        /**
         * The stage of the gallery image definition allowing to specify uri to release note.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithReleaseNoteUri {
            /**
             * Specifies uri to release note.
             *
             * @param releaseNoteUri the release note uri
             * @return the next definition stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            WithCreate withReleaseNoteUri(String releaseNoteUri);
        }

        /**
         * The stage of the gallery image definition allowing to specify tags.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithTags {
            /**
             * Specifies tags.
             *
             * @param tags resource tags
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
        interface WithCreate extends Creatable<GalleryImage>,
                DefinitionStages.WithDescription,
                DefinitionStages.WithDisallowed,
                DefinitionStages.WithEndOfLifeDate,
                DefinitionStages.WithEula,
                DefinitionStages.WithPrivacyStatementUri,
                DefinitionStages.WithPurchasePlan,
                DefinitionStages.WithRecommendedVMConfiguration,
                DefinitionStages.WithReleaseNoteUri,
                DefinitionStages.WithTags {
        }
    }
    /**
     * The template for a gallery image update operation, containing all the settings that can be modified.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    interface Update extends Appliable<GalleryImage>,
            UpdateStages.WithDescription,
            UpdateStages.WithDisallowed,
            UpdateStages.WithEndOfLifeDate,
            UpdateStages.WithEula,
            UpdateStages.WithOsState,
            UpdateStages.WithPrivacyStatementUri,
            UpdateStages.WithRecommendedVMConfiguration,
            UpdateStages.WithReleaseNoteUri,
            UpdateStages.WithTags {
    }

    /**
     * Grouping of gallery image update stages.
     */
    @Beta(Beta.SinceVersion.V1_15_0)
    interface UpdateStages {
        /**
         * The stage of the gallery image update allowing to specify description.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithDescription {
            /**
             * Specifies description of the gallery image resource.
             *
             * @param description The description
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withDescription(String description);
        }

        /**
         * The stage of the gallery image update allowing to specify settings disallowed
         * for a virtual machine based on the image.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithDisallowed {
            /**
             * Specifies the disk type not supported by the image.
             *
             * @param diskType the disk type
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withUnsupportedDiskType(DiskSkuTypes diskType);

            /**
             * Specifies the disk types not supported by the image.
             *
             * @param diskTypes the disk types
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withUnsupportedDiskTypes(List<DiskSkuTypes> diskTypes);

            /**
             * Specifies the disk type should be removed from the unsupported disk type.
             *
             * @param diskType the disk type
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withoutUnsupportedDiskType(DiskSkuTypes diskType);

            /**
             * Specifies disallowed settings.
             *
             * @param disallowed the disallowed settings
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withDisallowed(Disallowed disallowed);
        }

        /**
         * The stage of the gallery image update allowing to specify EndOfLifeDate.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithEndOfLifeDate {
            /**
             * Specifies end of life date of the image.
             *
             * @param endOfLifeDate the end of life of the gallery image
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withEndOfLifeDate(DateTime endOfLifeDate);
        }

        /**
         * The stage of the gallery image update allowing to specify Eula.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithEula {
            /**
             * Specifies eula.
             *
             * @param eula the Eula agreement for the gallery image
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withEula(String eula);
        }

        /**
         * The stage of the gallery image update allowing to specify OsState.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithOsState {
            /**
             * Specifies osState.
             *
             * @param osState the OS State.
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withOsState(OperatingSystemStateTypes osState);
        }

        /**
         * The stage of the gallery image update allowing to specify privacy statement uri.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithPrivacyStatementUri {
            /**
             * Specifies image privacy statement uri.
             *
             * @param privacyStatementUri the privacy statement uri
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withPrivacyStatementUri(String privacyStatementUri);
        }

        /**
         * The stage of the gallery image definition allowing to specify recommended
         * configuration for the virtual machine.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithRecommendedVMConfiguration {
            /**
             * Specifies the recommended minimum number of virtual CUPs for the virtual machine bases on the image.
             *
             * @param minCount the minimum number of virtual CPUs
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withRecommendedMinimumCPUsCountForVirtualMachine(int minCount);

            /**
             * Specifies the recommended maximum number of virtual CUPs for the virtual machine bases on the image.
             *
             * @param maxCount the maximum number of virtual CPUs
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withRecommendedMaximumCPUsCountForVirtualMachine(int maxCount);

            /**
             * Specifies the recommended virtual CUPs for the virtual machine bases on the image.
             *
             * @param minCount the minimum number of virtual CPUs
             * @param maxCount the maximum number of virtual CPUs
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withRecommendedCPUsCountForVirtualMachine(int minCount, int maxCount);

            /**
             * Specifies the recommended minimum memory for the virtual machine bases on the image.
             *
             * @param minMB the minimum memory in MB
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withRecommendedMinimumMemoryForVirtualMachine(int minMB);

            /**
             * Specifies the recommended maximum memory for the virtual machine bases on the image.
             *
             * @param maxMB the maximum memory in MB
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withRecommendedMaximumMemoryForVirtualMachine(int maxMB);

            /**
             * Specifies the recommended virtual CUPs for the virtual machine bases on the image.
             *
             * @param minMB the minimum memory in MB
             * @param maxMB the maximum memory in MB
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withRecommendedMemoryForVirtualMachine(int minMB, int maxMB);

            /**
             * Specifies recommended configuration for the virtual machine based on the image.
             *
             * @param recommendedConfig the recommended configuration
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withRecommendedConfigurationForVirtualMachine(RecommendedMachineConfiguration recommendedConfig);
        }

        /**
         * The stage of the gallery image update allowing to specify uri to release note.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithReleaseNoteUri {
            /**
             * Specifies release note uri.
             *
             * @param releaseNoteUri the release note uri
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withReleaseNoteUri(String releaseNoteUri);
        }

        /**
         * The stage of the gallery image update allowing to specify Tags.
         */
        @Beta(Beta.SinceVersion.V1_15_0)
        interface WithTags {
            /**
             * Specifies tags.
             * @param tags resource tags
             * @return the next update stage
             */
            @Beta(Beta.SinceVersion.V1_15_0)
            Update withTags(Map<String, String> tags);
        }
    }
}
