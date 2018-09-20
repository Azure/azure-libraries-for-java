/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute;

import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.compute.implementation.ComputeManager;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.rest.v2.http.HttpPipeline;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SharedGalleryImageTests extends ComputeManagementTest {
    private static String RG_NAME = "";
    private static final Region REGION = Region.US_WEST_CENTRAL;
    private static final String VMNAME = "javavm";

    @Override
    protected void initializeClients(HttpPipeline httpPipeline, String defaultSubscription, String domain) {
        RG_NAME = generateRandomResourceName("javacsmrg", 15);
        super.initializeClients(httpPipeline, defaultSubscription, domain);
    }

    @Override
    protected void cleanUpResources() {
       resourceManager.resourceGroups().beginDeleteByName(RG_NAME);
    }

    @Test
    public void canCreateUpdateListGetDeleteGallery() {
        // Create a gallery
        //
        Gallery javaGallery = this.computeManager.galleries().define("JavaImageGallery")
                .withRegion(REGION)
                .withNewResourceGroup(RG_NAME)
                // Optionals - Start
                .withDescription("java's image gallery")
                // Optionals - End
                .create();

        Assert.assertNotNull(javaGallery.uniqueName());
        Assert.assertEquals("JavaImageGallery", javaGallery.name());
        Assert.assertEquals("java's image gallery", javaGallery.description());
        Assert.assertNotNull(javaGallery.provisioningState());
        //
        // Update the gallery
        //
        javaGallery.update()
                .withDescription("updated java's image gallery")
                .withTag("jdk", "openjdk")
                .apply();

        Assert.assertEquals("updated java's image gallery", javaGallery.description());
        Assert.assertNotNull(javaGallery.tags());
        Assert.assertEquals(1, javaGallery.tags().size());
        //
        // List galleries
        //
        PagedList<Gallery> galleries = this.computeManager.galleries().listByResourceGroup(RG_NAME);
        Assert.assertEquals(1, galleries.size());
        galleries = this.computeManager.galleries().list();
        Assert.assertTrue(galleries.size() > 0);
        //
        this.computeManager.galleries().deleteByResourceGroup(javaGallery.resourceGroupName(), javaGallery.name());
    }

    @Test
    public void canCreateUpdateGetDeleteGalleryImage() {
        final String galleryName = generateRandomResourceName("jsim", 15);
        final String galleryImageName = "JavaImages";

        // Create a gallery
        //
        Gallery javaGallery = this.computeManager.galleries().define(galleryName)
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(RG_NAME)
                .withDescription("java's image gallery")
                .create();
        //
        // Create an image in the gallery
        //
        GalleryImage galleryImage = this.computeManager.galleryImages().define(galleryImageName)
                .withExistingGallery(javaGallery)
                .withLocation(REGION)
                .withIdentifier("JavaSDKTeam", "JDK", "Jdk-9")
                .withGeneralizedWindows()
                // Optionals - Start
                .withUnsupportedDiskType(DiskSkuTypes.STANDARD_LRS)
                .withUnsupportedDiskType(DiskSkuTypes.PREMIUM_LRS)
                .withRecommendedMaximumCPUsCountForVirtualMachine(25)
                .withRecommendedMaximumMemoryForVirtualMachine(3200)
                // Options - End
                .create();

        Assert.assertNotNull(galleryImage);
        Assert.assertNotNull(galleryImage.inner());
        Assert.assertTrue(galleryImage.location().equalsIgnoreCase(REGION.toString()));
        Assert.assertTrue(galleryImage.osType().equals(OperatingSystemTypes.WINDOWS));
        Assert.assertTrue(galleryImage.osState().equals(OperatingSystemStateTypes.GENERALIZED));
        Assert.assertEquals(2, galleryImage.unsupportedDiskTypes().size());
        Assert.assertNotNull(galleryImage.identifier());
        Assert.assertEquals("JavaSDKTeam", galleryImage.identifier().publisher());
        Assert.assertEquals("JDK", galleryImage.identifier().offer());
        Assert.assertEquals("Jdk-9", galleryImage.identifier().sku());
        Assert.assertNotNull(galleryImage.recommendedVirtualMachineConfiguration());
        Assert.assertNotNull(galleryImage.recommendedVirtualMachineConfiguration().vCPUs());
        Assert.assertNotNull(galleryImage.recommendedVirtualMachineConfiguration().vCPUs().max());
        Assert.assertEquals(25, galleryImage.recommendedVirtualMachineConfiguration().vCPUs().max().intValue());
        Assert.assertNotNull(galleryImage.recommendedVirtualMachineConfiguration().memory());
        Assert.assertNotNull(galleryImage.recommendedVirtualMachineConfiguration().memory().max());
        Assert.assertEquals(3200, galleryImage.recommendedVirtualMachineConfiguration().memory().max().intValue());
        //
        // Update an image in the gallery
        //
        galleryImage.update()
                .withoutUnsupportedDiskType(DiskSkuTypes.PREMIUM_LRS)
                .withRecommendedMinimumCPUsCountForVirtualMachine(15)
                .withRecommendedMemoryForVirtualMachine(2200, 3200)
                .apply();

        Assert.assertEquals(1, galleryImage.unsupportedDiskTypes().size());
        Assert.assertNotNull(galleryImage.recommendedVirtualMachineConfiguration());
        Assert.assertNotNull(galleryImage.recommendedVirtualMachineConfiguration().vCPUs());
        Assert.assertNotNull(galleryImage.recommendedVirtualMachineConfiguration().vCPUs().max());
        Assert.assertEquals(25, galleryImage.recommendedVirtualMachineConfiguration().vCPUs().max().intValue());
        Assert.assertNotNull(galleryImage.recommendedVirtualMachineConfiguration().vCPUs().min());
        Assert.assertEquals(15, galleryImage.recommendedVirtualMachineConfiguration().vCPUs().min().intValue());
        Assert.assertNotNull(galleryImage.recommendedVirtualMachineConfiguration().memory());
        Assert.assertNotNull(galleryImage.recommendedVirtualMachineConfiguration().memory().max());
        Assert.assertEquals(3200, galleryImage.recommendedVirtualMachineConfiguration().memory().max().intValue());
        Assert.assertNotNull(galleryImage.recommendedVirtualMachineConfiguration().memory().min());
        Assert.assertEquals(2200, galleryImage.recommendedVirtualMachineConfiguration().memory().min().intValue());
        //
        // List images in the gallery
        //
        PagedList<GalleryImage> images = this.computeManager.galleryImages().listByGallery(RG_NAME, galleryName);

        Assert.assertEquals(1, images.size());
        //
        // Get image from gallery
        //
        galleryImage = this.computeManager.galleryImages().getByGallery(RG_NAME, galleryName, galleryImageName);

        Assert.assertNotNull(galleryImage);
        Assert.assertNotNull(galleryImage.inner());
        //
        // Delete an image from gallery
        //
        this.computeManager.galleryImages().deleteByGallery(RG_NAME, galleryName, galleryImageName);
    }

    @Test
    @Ignore("CI fails this test with no useful trace, work fine locally")
    public void canCreateUpdateGetDeleteGalleryImageVersion() {
        //
        // Create a gallery
        //
        final String galleryName = generateRandomResourceName("jsim", 15); // "jsim94f154754";

        Gallery gallery = this.computeManager.galleries().define(galleryName)
                .withRegion(Region.US_WEST_CENTRAL)
                .withNewResourceGroup(RG_NAME)
                .withDescription("java's image gallery")
                .create();
        //
        // Create an image in the gallery (a container to hold custom linux image)
        //
        final String galleryImageName = "SampleImages";

        GalleryImage galleryImage = this.computeManager.galleryImages().define(galleryImageName)
                .withExistingGallery(gallery)
                .withLocation(REGION)
                .withIdentifier("JavaSDKTeam", "JDK", "Jdk-9")
                .withGeneralizedLinux()
                .create();
        //
        // Create a custom image to base the version on
        //
        VirtualMachineCustomImage customImage = prepareCustomImage(RG_NAME, REGION, computeManager);
        // String customImageId = "/subscriptions/0b1f6471-1bf0-4dda-aec3-cb9272f09590/resourceGroups/javacsmrg91482/providers/Microsoft.Compute/images/img96429090dee3";
        //
        // Create a image version based on the custom image
        //

        final String versionName = "0.0.4";

        GalleryImageVersion imageVersion = this.computeManager.galleryImageVersions().define(versionName)
                .withExistingImage(RG_NAME, gallery.name(), galleryImage.name())
                .withLocation(REGION.toString())
                .withSourceCustomImage(customImage)
                // Options - Start
                .withRegionAvailability(Region.US_EAST2, 1)
                // Options - End
                .create();

        Assert.assertNotNull(imageVersion);
        Assert.assertNotNull(imageVersion.inner());
        Assert.assertNotNull(imageVersion.availableRegions());
        Assert.assertEquals(2, imageVersion.availableRegions().size());
        Assert.assertFalse(imageVersion.isExcludedFromLatest());

        //
        // Update image version
        //
        imageVersion.update()
                .withoutRegionAvailability(Region.US_EAST2)
                .apply();

        Assert.assertNotNull(imageVersion.availableRegions());
        Assert.assertEquals(1, imageVersion.availableRegions().size());
        Assert.assertFalse(imageVersion.isExcludedFromLatest());

        //
        // List image versions
        //
        PagedList<GalleryImageVersion> versions = galleryImage.listVersions();

        Assert.assertNotNull(versions);
        Assert.assertTrue(versions.size() > 0);

        //
        // Delete the image version
        //
        this.computeManager.galleryImageVersions().deleteByGalleryImage(RG_NAME, galleryName, galleryImageName, versionName);
    }

    private VirtualMachineCustomImage prepareCustomImage(String rgName, Region region, ComputeManager computeManager) {
        VirtualMachine linuxVM = prepareGeneralizedVmWith2EmptyDataDisks(rgName,
                generateRandomResourceName("muldvm", 15),
                region,
                computeManager);

        final String vhdBasedImageName = generateRandomResourceName("img", 20);
        //
        VirtualMachineCustomImage.DefinitionStages.WithCreateAndDataDiskImageOSDiskSettings
                creatableDisk = computeManager
                .virtualMachineCustomImages()
                .define(vhdBasedImageName)
                .withRegion(region)
                .withNewResourceGroup(rgName)
                .withLinuxFromVhd(linuxVM.osUnmanagedDiskVhdUri(), OperatingSystemStateTypes.GENERALIZED)
                .withOSDiskCaching(linuxVM.osDiskCachingType());
        for (VirtualMachineUnmanagedDataDisk disk : linuxVM.unmanagedDataDisks().values()) {
            creatableDisk.defineDataDiskImage()
                    .withLun(disk.lun())
                    .fromVhd(disk.vhdUri())
                    .withDiskCaching(disk.cachingType())
                    .withDiskSizeInGB(disk.size() + 10) // Resize each data disk image by +10GB
                    .attach();
        }
        //
        VirtualMachineCustomImage customImage = creatableDisk.create();
        return customImage;
    }

    private VirtualMachine prepareGeneralizedVmWith2EmptyDataDisks(String rgName,
                                                                   String vmName,
                                                                   Region region,
                                                                   ComputeManager computeManager) {
        final String uname = "javauser";
        final String password = "12NewPA$$w0rd!";
        final KnownLinuxVirtualMachineImage linuxImage = KnownLinuxVirtualMachineImage.UBUNTU_SERVER_16_04_LTS;
        final String publicIpDnsLabel = generateRandomResourceName("pip", 20);

        VirtualMachine virtualMachine = computeManager.virtualMachines()
                .define(vmName)
                .withRegion(region)
                .withNewResourceGroup(rgName)
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withNewPrimaryPublicIPAddress(publicIpDnsLabel)
                .withPopularLinuxImage(linuxImage)
                .withRootUsername(uname)
                .withRootPassword(password)
                .withUnmanagedDisks()
                .defineUnmanagedDataDisk("disk-1")
                    .withNewVhd(30)
                    .withCaching(CachingTypes.READ_WRITE)
                    .attach()
                .defineUnmanagedDataDisk("disk-2")
                    .withNewVhd(60)
                    .withCaching(CachingTypes.READ_ONLY)
                    .attach()
                .withSize(VirtualMachineSizeTypes.STANDARD_D5_V2)
                .withNewStorageAccount(generateRandomResourceName("stg", 17))
                .withOSDiskCaching(CachingTypes.READ_WRITE)
                .create();
        //
        deprovisionAgentInLinuxVM(virtualMachine.getPrimaryPublicIPAddress().fqdn(), 22, uname, password);
        virtualMachine.deallocate();
        virtualMachine.generalize();
        return virtualMachine;
    }

}
