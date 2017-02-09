/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ChildResource;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Describes definition and update stages of unmanaged data disk of a scale set.
 */
@Fluent
public interface VirtualMachineScaleSetUnmanagedDataDisk extends
        HasInner<VirtualMachineScaleSetDataDisk>,
        ChildResource<VirtualMachineScaleSet> {

    /**
     * Grouping of unmanaged data disk definition stages applicable as part of a virtual machine scale set creation.
     */
    interface DefinitionStages {
        /**
         * The first stage of a unmanaged data disk definition.
         *
         * @param <ParentT> the return type of the final {@link WithAttach#attach()}
         */
        interface Blank<ParentT>
                extends WithDiskSource<ParentT> {
        }

        /**
         * The stage of the unmanaged data disk definition allowing to choose the source.
         *
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithDiskSource<ParentT> {
            /**
             * specifies that unmanaged disk needs to be created with a new vhd of given size.
             *
             * @param sizeInGB the initial disk size in GB
             * @return the next stage of unmanaged data disk definition
             */
            WithNewVhdDiskSettings<ParentT> withNewVhd(int sizeInGB);

            /**
             * Specifies the image lun identifier of the source disk image.
             *
             * @param imageLun the lun
             * @return the next stage of unmanaged data disk definition
             */
            WithFromImageDiskSettings<ParentT> fromImage(int imageLun);
        }

        /**
         * The stage that allows configure the unmanaged disk based on new vhd.
         *
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithNewVhdDiskSettings<ParentT> extends WithAttach<ParentT> {
            /**
             * Specifies the logical unit number for the unmanaged data disk.
             *
             * @param lun the logical unit number
             * @return the next stage of unmanaged data disk definition
             */
            WithNewVhdDiskSettings<ParentT> withLun(Integer lun);

            /**
             * Specifies the caching type for the unmanaged data disk.
             *
             * @param cachingType the disk caching type. Possible values include: 'None', 'ReadOnly', 'ReadWrite'
             * @return the next stage of unmanaged data disk definition
             */
            WithNewVhdDiskSettings<ParentT> withCaching(CachingTypes cachingType);
        }

        /**
         * The stage that allows configure the unmanaged disk based on an image.
         *
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithFromImageDiskSettings<ParentT> extends WithAttach<ParentT> {
            /**
             * Specifies the size in GB the unmanaged disk needs to be resized.
             *
             * @param sizeInGB the disk size in GB
             * @return the next stage of unmanaged data disk definition
             */
            WithFromImageDiskSettings<ParentT> withSizeInGB(Integer sizeInGB);

            /**
             * Specifies the caching type for the unmanaged data disk.
             *
             * @param cachingType the disk caching type. Possible values include: 'None', 'ReadOnly', 'ReadWrite'
             * @return the next stage of unmanaged data disk definition
             */
            WithFromImageDiskSettings<ParentT> withCaching(CachingTypes cachingType);
        }

        /**
         * The final stage of the unmanaged data disk definition.
         *
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithAttach<ParentT> extends Attachable.InDefinition<ParentT> {
        }
    }

    /** The entirety of a unmanaged data disk of a virtual machine scale set definition.
     * @param <ParentT> the return type of the final {@link DefinitionStages.WithAttach#attach()}
     */
    interface DefinitionWithNewVhd<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithDiskSource<ParentT>,
            DefinitionStages.WithNewVhdDiskSettings<ParentT>,
            DefinitionStages.WithAttach<ParentT> {
    }


    /** The entirety of a unmanaged data disk of a virtual machine scale set definition.
     * @param <ParentT> the return type of the final {@link DefinitionStages.WithAttach#attach()}
     */
    interface DefinitionWithImage<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithDiskSource<ParentT>,
            DefinitionStages.WithFromImageDiskSettings<ParentT>,
            DefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Grouping of unamanged data disk definition stages applicable as part of a virtual machine scale set update.
     */
    interface UpdateDefinitionStages {
        /**
         * The first stage of a unmanaged data disk definition.
         *
         * @param <ParentT> the return type of the final {@link WithAttach#attach()}
         */
        interface Blank<ParentT>
                extends WithDiskSource<ParentT> {
        }

        /**
         * The stage of the unmanaged data disk definition allowing to choose the source.
         *
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithDiskSource<ParentT> {
            /**
             * specifies that unmanaged disk needs to be created with a new vhd of given size.
             *
             * @param sizeInGB the initial disk size in GB
             * @return the next stage of unmanaged data disk definition
             */
            WithNewVhdDiskSettings<ParentT> withNewVhd(int sizeInGB);
        }

        /**
         * The stage that allows configure the unmanaged disk based on new vhd.
         *
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithNewVhdDiskSettings<ParentT> extends WithAttach<ParentT> {
            /**
             * Specifies the logical unit number for the unmanaged data disk.
             *
             * @param lun the logical unit number
             * @return the next stage of unmanaged data disk definition
             */
            WithNewVhdDiskSettings<ParentT> withLun(Integer lun);

            /**
             * Specifies the caching type for the unmanaged data disk.
             *
             * @param cachingType the disk caching type. Possible values include: 'None', 'ReadOnly', 'ReadWrite'
             * @return the next stage of unmanaged data disk definition
             */
            WithNewVhdDiskSettings<ParentT> withCaching(CachingTypes cachingType);
        }

        /**
         * The final stage of the unmanaged data disk definition.
         *
         * @param <ParentT> the return type of {@link WithAttach#attach()}
         */
        interface WithAttach<ParentT> extends Attachable.InUpdate<ParentT> {
        }
    }

    /** The entirety of a unmanaged data disk of a virtual machine scale set definition.
     * @param <ParentT> the return type of the final {@link DefinitionStages.WithAttach#attach()}
     */
    interface UpdateDefinition<ParentT> extends
            UpdateDefinitionStages.Blank<ParentT>,
            UpdateDefinitionStages.WithDiskSource<ParentT>,
            UpdateDefinitionStages.WithNewVhdDiskSettings<ParentT>,
            UpdateDefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Grouping of unmanaged data disk update stages.
     */
    interface UpdateStages {
        /**
         * The stage of the unmanaged data disk update allowing to set the disk size.
         */
        interface WithDiskSize {
            /**
             * Specifies the new size in GB for data disk.
             *
             * @param sizeInGB the disk size in GB
             * @return the next stage of unmanaged data disk update
             */
            Update withSizeInGB(Integer sizeInGB);
        }

        /**
         * The stage of the unmanaged data disk update allowing to set the disk lun.
         */
        interface WithDiskLun {
            /**
             * Specifies the new logical unit number for the unmanaged data disk.
             *
             * @param lun the logical unit number
             * @return the next stage of unmanaged data disk update
             */
            Update withLun(Integer lun);
        }

        /**
         * The stage of the unmanaged data disk update allowing to set the disk caching type.
         */
        interface WithDiskCaching {
            /**
             * Specifies the new caching type for the unmanaged data disk.
             *
             * @param cachingType the disk caching type. Possible values include: 'None', 'ReadOnly', 'ReadWrite'
             * @return the next stage of unmanaged data disk update
             */
            Update withCaching(CachingTypes cachingType);
        }
    }

    /**
     * The entirety of a unmanaged data disk update as part of a virtual machine scale set update.
     */
    interface Update extends
            UpdateStages.WithDiskSize,
            UpdateStages.WithDiskLun,
            UpdateStages.WithDiskCaching,
            Settable<VirtualMachineScaleSet.Update> {
    }
}
