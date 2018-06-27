/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.containerregistry.implementation.BuildInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;

import org.joda.time.DateTime;

import java.util.List;
import rx.Observable;

/**
 * An object that represents a build for a container registry.
 */
@Fluent
@Beta(Beta.SinceVersion.V2_0_0)
public interface Build extends
    HasInner<BuildInner>,
    Refreshable<Build> {

    /**
     * @return the unique identifier for the build
     */
    @Method
    String buildId();

    /**
     * @return the status of the build
     */
    @Method
    BuildStatus status();

    /**
     * @return the last updated time for the build
     */
    @Method
    DateTime lastUpdatedTime();

    /**
     * @return type of build. Possible values include: 'AutoBuild', 'QuickBuild'
     */
    @Method
    BuildType buildType();

    /**
     * @return the time the build was created
     */
    @Method
    DateTime createTime();

    /**
     * @return the time the build started
     */
    @Method
    DateTime startTime();

    /**
     * @return the time the build finished
     */
    @Method
    DateTime finishTime();

    /**
     * @return the list of all images that were generated from the build
     */
    @Method
    List<ImageDescriptor> outputImages();

    /**
     * @return the build task with which the build was started
     */
    @Method
    String buildTask();

    /**
     * @return the image update trigger that caused the build
     */
    @Method
    ImageUpdateTrigger imageUpdateTrigger();    

    /**
     * @return the git commit trigger that caused the build
     */
    @Method
    GitCommitTrigger gitCommitTrigger();

    /**
     * @return the value that indicates whether archiving is enabled or not
     */
    @Method
    Boolean isArchiveEnabled();

    /**
     * @return the platform properties against which the build will happen
     */
    @Method
    PlatformProperties platform();

    /**
     * Gets a link to download the build logs.
     *
     * @return the BuildGetLogResult object if successful.
     */
    @Method
    BuildGetLogResult getLogLink();

    /**
     * Gets a link to download the build logs.
     *
     * @return a representation of the future computation of this call, returning the BuildGetLogResult object if successful
     */
    @Method
    Observable<BuildGetLogResult> getLogLinkAsync();
}
