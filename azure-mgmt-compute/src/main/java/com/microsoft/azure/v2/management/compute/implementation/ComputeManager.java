/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.compute.implementation;

import com.microsoft.azure.v2.credentials.AzureTokenCredentials;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ProviderRegistrationPolicyFactory;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ResourceManagerThrottlingPolicyFactory;
import com.microsoft.rest.v2.annotations.Beta;
import com.microsoft.azure.v2.management.compute.AvailabilitySets;
import com.microsoft.azure.v2.management.compute.ComputeSkus;
import com.microsoft.azure.v2.management.compute.ComputeUsages;
import com.microsoft.azure.v2.management.compute.Disks;
import com.microsoft.azure.v2.management.compute.Galleries;
import com.microsoft.azure.v2.management.compute.GalleryImageVersions;
import com.microsoft.azure.v2.management.compute.GalleryImages;
import com.microsoft.azure.v2.management.compute.Snapshots;
import com.microsoft.azure.v2.management.compute.VirtualMachineCustomImages;
import com.microsoft.azure.v2.management.compute.VirtualMachineExtensionImages;
import com.microsoft.azure.v2.management.compute.VirtualMachineImages;
import com.microsoft.azure.v2.management.compute.VirtualMachineScaleSets;
import com.microsoft.azure.v2.management.compute.VirtualMachines;
import com.microsoft.azure.v2.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.v2.management.network.implementation.NetworkManager;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.Manager;
import com.microsoft.azure.v2.management.storage.implementation.StorageManager;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.http.HttpPipelineBuilder;
import com.microsoft.rest.v2.policy.CredentialsPolicyFactory;

/**
 * Entry point to Azure compute resource management.
 */
public final class ComputeManager extends Manager<ComputeManager, ComputeManagementClientImpl> {
    // The service managers
    private StorageManager storageManager;
    private NetworkManager networkManager;
    private GraphRbacManager rbacManager;

    // The collections
    private AvailabilitySets availabilitySets;
    private VirtualMachines virtualMachines;
    private VirtualMachineImages virtualMachineImages;
    private VirtualMachineExtensionImages virtualMachineExtensionImages;
    private VirtualMachineScaleSets virtualMachineScaleSets;
    private ComputeUsages computeUsages;
    private VirtualMachineCustomImages virtualMachineCustomImages;
    private Disks disks;
    private Snapshots snapshots;
    private ComputeSkus computeSkus;
    private Galleries galleries;
    private GalleryImages galleryImages;
    private GalleryImageVersions galleryImageVersions;

    /**
     * Get a Configurable instance that can be used to create ComputeManager with optional configuration.
     *
     * @return Configurable
     */
    public static Configurable configure() {
        return new ComputeManager.ConfigurableImpl();
    }

    /**
     * Creates an instance of ComputeManager that exposes Compute resource management API entry points.
     *
     * @param credentials the credentials to use
     * @param subscriptionId the subscription
     * @param domain the domain
     * @return the ComputeManager
     */
    public static ComputeManager authenticate(AzureTokenCredentials credentials, String subscriptionId, String domain) {
        return new ComputeManager(new HttpPipelineBuilder()
                .withRequestPolicy(new CredentialsPolicyFactory(credentials))
                .withRequestPolicy(new ProviderRegistrationPolicyFactory(credentials))
                .withRequestPolicy(new ResourceManagerThrottlingPolicyFactory())
                .build(), subscriptionId, domain);
    }

    /**
     * Creates an instance of ComputeManager that exposes Compute resource management API entry points.
     *
     * @param httpPipeline the httpPipeline to be used for API calls.
     * @param subscriptionId the subscription
     * @param domain the domain
     * @return the ComputeManager
     */
    public static ComputeManager authenticate(HttpPipeline httpPipeline, String subscriptionId, String domain) {
        return new ComputeManager(httpPipeline, subscriptionId, domain);
    }
    /**
     * The interface allowing configurations to be set.
     */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
         * Creates an instance of ComputeManager that exposes Compute resource management API entry points.
         *
         * @param credentials the credentials to use
         * @param subscriptionId the subscription
         * @return the ComputeManager
         */
        ComputeManager authenticate(AzureTokenCredentials credentials, String subscriptionId);
    }

    /**
     * The implementation for Configurable interface.
     */
    private static final class ConfigurableImpl extends AzureConfigurableImpl<Configurable> implements  Configurable {
        @Override
        public ComputeManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
            return ComputeManager.authenticate(buildPipeline(credentials), subscriptionId, credentials.domain());
        }
    }

    private ComputeManager(HttpPipeline httpPipeline, String subscriptionId, String domain) {
        super(
                httpPipeline,
                subscriptionId,
                new ComputeManagementClientImpl(httpPipeline).withSubscriptionId(subscriptionId));
        storageManager = StorageManager.authenticate(httpPipeline, subscriptionId);
        networkManager = NetworkManager.authenticate(httpPipeline, subscriptionId);
        rbacManager = GraphRbacManager.authenticate(httpPipeline, domain);
    }

    /**
     * @return the availability set resource management API entry point
     */
    public AvailabilitySets availabilitySets() {
        if (availabilitySets == null) {
            availabilitySets = new AvailabilitySetsImpl(this);
        }
        return availabilitySets;
    }

    /**
     * @return the virtual machine resource management API entry point
     */
    public VirtualMachines virtualMachines() {
        if (virtualMachines == null) {
            virtualMachines = new VirtualMachinesImpl(
                    this,
                    storageManager,
                    networkManager,
                    rbacManager);
        }
        return virtualMachines;
    }

    /**
     * @return the virtual machine image resource management API entry point
     */
    public VirtualMachineImages virtualMachineImages() {
        if (virtualMachineImages == null) {
            virtualMachineImages = new VirtualMachineImagesImpl(new VirtualMachinePublishersImpl(super.innerManagementClient.virtualMachineImages(),
                    super.innerManagementClient.virtualMachineExtensionImages()),
                    super.innerManagementClient.virtualMachineImages());
        }
        return virtualMachineImages;
    }

    /**
     * @return the virtual machine extension image resource management API entry point
     */
    public VirtualMachineExtensionImages virtualMachineExtensionImages() {
        if (virtualMachineExtensionImages == null) {
            virtualMachineExtensionImages = new VirtualMachineExtensionImagesImpl(new VirtualMachinePublishersImpl(super.innerManagementClient.virtualMachineImages(),
                    super.innerManagementClient.virtualMachineExtensionImages()));
        }
        return virtualMachineExtensionImages;
    }

    /**
     * @return the virtual machine scale set resource management API entry point
     */
    public VirtualMachineScaleSets virtualMachineScaleSets() {
        if (virtualMachineScaleSets == null) {
            virtualMachineScaleSets = new VirtualMachineScaleSetsImpl(
                    this,
                    storageManager,
                    networkManager,
                    this.rbacManager);
        }
        return virtualMachineScaleSets;
    }

    /**
     * @return the compute resource usage management API entry point
     */
    public ComputeUsages usages() {
        if (computeUsages == null) {
            computeUsages = new ComputeUsagesImpl(super.innerManagementClient);
        }
        return computeUsages;
    }

    /**
     * @return the virtual machine custom image management API entry point
     */
    public VirtualMachineCustomImages virtualMachineCustomImages() {
        if (virtualMachineCustomImages == null) {
            virtualMachineCustomImages = new VirtualMachineCustomImagesImpl(this);
        }
        return virtualMachineCustomImages;
    }

    /**
     * @return the managed disk management API entry point
     */
    public Disks disks() {
        if (disks == null) {
            disks = new DisksImpl(this);
        }
        return disks;
    }

    /**
     * @return the managed snapshot management API entry point
     */
    public Snapshots snapshots() {
        if (snapshots == null) {
            snapshots = new SnapshotsImpl(this);
        }
        return snapshots;
    }

    /**
     * @return the compute service SKU management API entry point
     */
    public ComputeSkus computeSkus() {
        if (computeSkus == null) {
            computeSkus = new ComputeSkusImpl(this);
        }
        return computeSkus;
    }

    /**
     * @return the compute service gallery management entry point
     */
    @Beta(since = "V1_15_0")
    public Galleries galleries() {
        if (galleries == null) {
            galleries = new GalleriesImpl(this);
        }
        return galleries;
    }

    /**
     * @return the compute service gallery image management entry point
     */
    @Beta(since = "V1_15_0")
    public GalleryImages galleryImages() {
        if (galleryImages == null) {
            galleryImages = new GalleryImagesImpl(this);
        }
        return galleryImages;
    }

    /**
     * @return the compute service gallery image version management entry point
     */
    @Beta(since = "V1_15_0")
    public GalleryImageVersions galleryImageVersions() {
        if (galleryImageVersions == null) {
            galleryImageVersions = new GalleryImageVersionsImpl(this);
        }
        return galleryImageVersions;
    }
}