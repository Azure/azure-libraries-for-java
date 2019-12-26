package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.storage.implementation.ListContainerItemInner;
import org.joda.time.DateTime;

import java.util.Map;

public interface ListContainerItem extends HasInner<ListContainerItemInner> {

    /**
     * Get specifies whether data in the container may be accessed publicly and the level of access. Possible values include: 'Container', 'Blob', 'None'.
     *
     * @return the publicAccess value
     */
    public PublicAccess publicAccess();

    /**
     * Get returns the date and time the container was last modified.
     *
     * @return the lastModifiedTime value
     */
    public DateTime lastModifiedTime();

    /**
     * Get the lease status of the container. Possible values include: 'Locked', 'Unlocked'.
     *
     * @return the leaseStatus value
     */
    public LeaseStatus leaseStatus();

    /**
     * Get lease state of the container. Possible values include: 'Available', 'Leased', 'Expired', 'Breaking', 'Broken'.
     *
     * @return the leaseState value
     */
    public LeaseState leaseState();

    /**
     * Get specifies whether the lease on a container is of infinite or fixed duration, only when the container is leased. Possible values include: 'Infinite', 'Fixed'.
     *
     * @return the leaseDuration value
     */
    public LeaseDuration leaseDuration();

    /**
     * Get a name-value pair to associate with the container as metadata.
     *
     * @return the metadata value
     */
    public Map<String, String> metadata();

    /**
     * Get the ImmutabilityPolicy property of the container.
     *
     * @return the immutabilityPolicy value
     */
    public ImmutabilityPolicyProperties immutabilityPolicy();

    /**
     * Get the LegalHold property of the container.
     *
     * @return the legalHold value
     */
    public LegalHoldProperties legalHold();

    /**
     * Get the hasLegalHold public property is set to true by SRP if there are at least one existing tag. The hasLegalHold public property is set to false by SRP if all existing legal hold tags are cleared out. There can be a maximum of 1000 blob containers with hasLegalHold=true for a given account.
     *
     * @return the hasLegalHold value
     */
    public Boolean hasLegalHold();

    /**
     * Get the hasImmutabilityPolicy public property is set to true by SRP if ImmutabilityPolicy has been created for this container. The hasImmutabilityPolicy public property is set to false by SRP if ImmutabilityPolicy has not been created for this container.
     *
     * @return the hasImmutabilityPolicy value
     */
    public Boolean hasImmutabilityPolicy();

}
