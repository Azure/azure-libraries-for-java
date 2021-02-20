/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Describes a data disk.
 */
public class DataDisk {
    /**
     * Specifies the logical unit number of the data disk. This value is used
     * to identify data disks within the VM and therefore must be unique for
     * each data disk attached to a VM.
     */
    @JsonProperty(value = "lun", required = true)
    private int lun;

    /**
     * The disk name.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * The virtual hard disk.
     */
    @JsonProperty(value = "vhd")
    private VirtualHardDisk vhd;

    /**
     * The source user image virtual hard disk. The virtual hard disk will be
     * copied before being attached to the virtual machine. If SourceImage is
     * provided, the destination virtual hard drive must not exist.
     */
    @JsonProperty(value = "image")
    private VirtualHardDisk image;

    /**
     * Specifies the caching requirements. &lt;br&gt;&lt;br&gt; Possible values
     * are: &lt;br&gt;&lt;br&gt; **None** &lt;br&gt;&lt;br&gt; **ReadOnly**
     * &lt;br&gt;&lt;br&gt; **ReadWrite** &lt;br&gt;&lt;br&gt; Default: **None
     * for Standard storage. ReadOnly for Premium storage**. Possible values
     * include: 'None', 'ReadOnly', 'ReadWrite'.
     */
    @JsonProperty(value = "caching")
    private CachingTypes caching;

    /**
     * Specifies whether writeAccelerator should be enabled or disabled on the
     * disk.
     */
    @JsonProperty(value = "writeAcceleratorEnabled")
    private Boolean writeAcceleratorEnabled;

    /**
     * Specifies how the virtual machine should be created.&lt;br&gt;&lt;br&gt;
     * Possible values are:&lt;br&gt;&lt;br&gt; **Attach** \u2013 This value is
     * used when you are using a specialized disk to create the virtual
     * machine.&lt;br&gt;&lt;br&gt; **FromImage** \u2013 This value is used
     * when you are using an image to create the virtual machine. If you are
     * using a platform image, you also use the imageReference element
     * described above. If you are using a marketplace image, you  also use the
     * plan element previously described. Possible values include: 'FromImage',
     * 'Empty', 'Attach'.
     */
    @JsonProperty(value = "createOption", required = true)
    private DiskCreateOptionTypes createOption;

    /**
     * Specifies the size of an empty data disk in gigabytes. This element can
     * be used to overwrite the size of the disk in a virtual machine image.
     * &lt;br&gt;&lt;br&gt; This value cannot be larger than 1023 GB.
     */
    @JsonProperty(value = "diskSizeGB")
    private Integer diskSizeGB;

    /**
     * The managed disk parameters.
     */
    @JsonProperty(value = "managedDisk")
    private ManagedDiskParameters managedDisk;

    /**
     * Specifies whether the data disk is in process of detachment from the
     * VirtualMachine/VirtualMachineScaleset.
     */
    @JsonProperty(value = "toBeDetached")
    private Boolean toBeDetached;

    /**
     * Specifies the Read-Write IOPS for the managed disk when
     * StorageAccountType is UltraSSD_LRS. Returned only for VirtualMachine
     * ScaleSet VM disks. Can be updated only via updates to the VirtualMachine
     * Scale Set.
     */
    @JsonProperty(value = "diskIOPSReadWrite", access = JsonProperty.Access.WRITE_ONLY)
    private Long diskIOPSReadWrite;

    /**
     * Specifies the bandwidth in MB per second for the managed disk when
     * StorageAccountType is UltraSSD_LRS. Returned only for VirtualMachine
     * ScaleSet VM disks. Can be updated only via updates to the VirtualMachine
     * Scale Set.
     */
    @JsonProperty(value = "diskMBpsReadWrite", access = JsonProperty.Access.WRITE_ONLY)
    private Long diskMBpsReadWrite;

    /**
     * Specifies the detach behavior to be used while detaching a disk or which
     * is already in the process of detachment from the virtual machine.
     * Supported values: **ForceDetach**. &lt;br&gt;&lt;br&gt; detachOption:
     * **ForceDetach** is applicable only for managed data disks. If a previous
     * detachment attempt of the data disk did not complete due to an
     * unexpected failure from the virtual machine and the disk is still not
     * released then use force-detach as a last resort option to detach the
     * disk forcibly from the VM. All writes might not have been flushed when
     * using this detach behavior. &lt;br&gt;&lt;br&gt; This feature is still
     * in preview mode and is not supported for VirtualMachineScaleSet. To
     * force-detach a data disk update toBeDetached to 'true' along with
     * setting detachOption: 'ForceDetach'. Possible values include:
     * 'ForceDetach'.
     */
    @JsonProperty(value = "detachOption")
    private DiskDetachOptionTypes detachOption;

    /**
     * Get specifies the logical unit number of the data disk. This value is used to identify data disks within the VM and therefore must be unique for each data disk attached to a VM.
     *
     * @return the lun value
     */
    public int lun() {
        return this.lun;
    }

    /**
     * Set specifies the logical unit number of the data disk. This value is used to identify data disks within the VM and therefore must be unique for each data disk attached to a VM.
     *
     * @param lun the lun value to set
     * @return the DataDisk object itself.
     */
    public DataDisk withLun(int lun) {
        this.lun = lun;
        return this;
    }

    /**
     * Get the disk name.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the disk name.
     *
     * @param name the name value to set
     * @return the DataDisk object itself.
     */
    public DataDisk withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the virtual hard disk.
     *
     * @return the vhd value
     */
    public VirtualHardDisk vhd() {
        return this.vhd;
    }

    /**
     * Set the virtual hard disk.
     *
     * @param vhd the vhd value to set
     * @return the DataDisk object itself.
     */
    public DataDisk withVhd(VirtualHardDisk vhd) {
        this.vhd = vhd;
        return this;
    }

    /**
     * Get the source user image virtual hard disk. The virtual hard disk will be copied before being attached to the virtual machine. If SourceImage is provided, the destination virtual hard drive must not exist.
     *
     * @return the image value
     */
    public VirtualHardDisk image() {
        return this.image;
    }

    /**
     * Set the source user image virtual hard disk. The virtual hard disk will be copied before being attached to the virtual machine. If SourceImage is provided, the destination virtual hard drive must not exist.
     *
     * @param image the image value to set
     * @return the DataDisk object itself.
     */
    public DataDisk withImage(VirtualHardDisk image) {
        this.image = image;
        return this;
    }

    /**
     * Get specifies the caching requirements. &lt;br&gt;&lt;br&gt; Possible values are: &lt;br&gt;&lt;br&gt; **None** &lt;br&gt;&lt;br&gt; **ReadOnly** &lt;br&gt;&lt;br&gt; **ReadWrite** &lt;br&gt;&lt;br&gt; Default: **None for Standard storage. ReadOnly for Premium storage**. Possible values include: 'None', 'ReadOnly', 'ReadWrite'.
     *
     * @return the caching value
     */
    public CachingTypes caching() {
        return this.caching;
    }

    /**
     * Set specifies the caching requirements. &lt;br&gt;&lt;br&gt; Possible values are: &lt;br&gt;&lt;br&gt; **None** &lt;br&gt;&lt;br&gt; **ReadOnly** &lt;br&gt;&lt;br&gt; **ReadWrite** &lt;br&gt;&lt;br&gt; Default: **None for Standard storage. ReadOnly for Premium storage**. Possible values include: 'None', 'ReadOnly', 'ReadWrite'.
     *
     * @param caching the caching value to set
     * @return the DataDisk object itself.
     */
    public DataDisk withCaching(CachingTypes caching) {
        this.caching = caching;
        return this;
    }

    /**
     * Get specifies whether writeAccelerator should be enabled or disabled on the disk.
     *
     * @return the writeAcceleratorEnabled value
     */
    public Boolean writeAcceleratorEnabled() {
        return this.writeAcceleratorEnabled;
    }

    /**
     * Set specifies whether writeAccelerator should be enabled or disabled on the disk.
     *
     * @param writeAcceleratorEnabled the writeAcceleratorEnabled value to set
     * @return the DataDisk object itself.
     */
    public DataDisk withWriteAcceleratorEnabled(Boolean writeAcceleratorEnabled) {
        this.writeAcceleratorEnabled = writeAcceleratorEnabled;
        return this;
    }

    /**
     * Get specifies how the virtual machine should be created.&lt;br&gt;&lt;br&gt; Possible values are:&lt;br&gt;&lt;br&gt; **Attach** \u2013 This value is used when you are using a specialized disk to create the virtual machine.&lt;br&gt;&lt;br&gt; **FromImage** \u2013 This value is used when you are using an image to create the virtual machine. If you are using a platform image, you also use the imageReference element described above. If you are using a marketplace image, you  also use the plan element previously described. Possible values include: 'FromImage', 'Empty', 'Attach'.
     *
     * @return the createOption value
     */
    public DiskCreateOptionTypes createOption() {
        return this.createOption;
    }

    /**
     * Set specifies how the virtual machine should be created.&lt;br&gt;&lt;br&gt; Possible values are:&lt;br&gt;&lt;br&gt; **Attach** \u2013 This value is used when you are using a specialized disk to create the virtual machine.&lt;br&gt;&lt;br&gt; **FromImage** \u2013 This value is used when you are using an image to create the virtual machine. If you are using a platform image, you also use the imageReference element described above. If you are using a marketplace image, you  also use the plan element previously described. Possible values include: 'FromImage', 'Empty', 'Attach'.
     *
     * @param createOption the createOption value to set
     * @return the DataDisk object itself.
     */
    public DataDisk withCreateOption(DiskCreateOptionTypes createOption) {
        this.createOption = createOption;
        return this;
    }

    /**
     * Get specifies the size of an empty data disk in gigabytes. This element can be used to overwrite the size of the disk in a virtual machine image. &lt;br&gt;&lt;br&gt; This value cannot be larger than 1023 GB.
     *
     * @return the diskSizeGB value
     */
    public Integer diskSizeGB() {
        return this.diskSizeGB;
    }

    /**
     * Set specifies the size of an empty data disk in gigabytes. This element can be used to overwrite the size of the disk in a virtual machine image. &lt;br&gt;&lt;br&gt; This value cannot be larger than 1023 GB.
     *
     * @param diskSizeGB the diskSizeGB value to set
     * @return the DataDisk object itself.
     */
    public DataDisk withDiskSizeGB(Integer diskSizeGB) {
        this.diskSizeGB = diskSizeGB;
        return this;
    }

    /**
     * Get the managed disk parameters.
     *
     * @return the managedDisk value
     */
    public ManagedDiskParameters managedDisk() {
        return this.managedDisk;
    }

    /**
     * Set the managed disk parameters.
     *
     * @param managedDisk the managedDisk value to set
     * @return the DataDisk object itself.
     */
    public DataDisk withManagedDisk(ManagedDiskParameters managedDisk) {
        this.managedDisk = managedDisk;
        return this;
    }

    /**
     * Get specifies whether the data disk is in process of detachment from the VirtualMachine/VirtualMachineScaleset.
     *
     * @return the toBeDetached value
     */
    public Boolean toBeDetached() {
        return this.toBeDetached;
    }

    /**
     * Set specifies whether the data disk is in process of detachment from the VirtualMachine/VirtualMachineScaleset.
     *
     * @param toBeDetached the toBeDetached value to set
     * @return the DataDisk object itself.
     */
    public DataDisk withToBeDetached(Boolean toBeDetached) {
        this.toBeDetached = toBeDetached;
        return this;
    }

    /**
     * Get specifies the Read-Write IOPS for the managed disk when StorageAccountType is UltraSSD_LRS. Returned only for VirtualMachine ScaleSet VM disks. Can be updated only via updates to the VirtualMachine Scale Set.
     *
     * @return the diskIOPSReadWrite value
     */
    public Long diskIOPSReadWrite() {
        return this.diskIOPSReadWrite;
    }

    /**
     * Get specifies the bandwidth in MB per second for the managed disk when StorageAccountType is UltraSSD_LRS. Returned only for VirtualMachine ScaleSet VM disks. Can be updated only via updates to the VirtualMachine Scale Set.
     *
     * @return the diskMBpsReadWrite value
     */
    public Long diskMBpsReadWrite() {
        return this.diskMBpsReadWrite;
    }

    /**
     * Get specifies the detach behavior to be used while detaching a disk or which is already in the process of detachment from the virtual machine. Supported values: **ForceDetach**. &lt;br&gt;&lt;br&gt; detachOption: **ForceDetach** is applicable only for managed data disks. If a previous detachment attempt of the data disk did not complete due to an unexpected failure from the virtual machine and the disk is still not released then use force-detach as a last resort option to detach the disk forcibly from the VM. All writes might not have been flushed when using this detach behavior. &lt;br&gt;&lt;br&gt; This feature is still in preview mode and is not supported for VirtualMachineScaleSet. To force-detach a data disk update toBeDetached to 'true' along with setting detachOption: 'ForceDetach'. Possible values include: 'ForceDetach'.
     *
     * @return the detachOption value
     */
    public DiskDetachOptionTypes detachOption() {
        return this.detachOption;
    }

    /**
     * Set specifies the detach behavior to be used while detaching a disk or which is already in the process of detachment from the virtual machine. Supported values: **ForceDetach**. &lt;br&gt;&lt;br&gt; detachOption: **ForceDetach** is applicable only for managed data disks. If a previous detachment attempt of the data disk did not complete due to an unexpected failure from the virtual machine and the disk is still not released then use force-detach as a last resort option to detach the disk forcibly from the VM. All writes might not have been flushed when using this detach behavior. &lt;br&gt;&lt;br&gt; This feature is still in preview mode and is not supported for VirtualMachineScaleSet. To force-detach a data disk update toBeDetached to 'true' along with setting detachOption: 'ForceDetach'. Possible values include: 'ForceDetach'.
     *
     * @param detachOption the detachOption value to set
     * @return the DataDisk object itself.
     */
    public DataDisk withDetachOption(DiskDetachOptionTypes detachOption) {
        this.detachOption = detachOption;
        return this;
    }

}
