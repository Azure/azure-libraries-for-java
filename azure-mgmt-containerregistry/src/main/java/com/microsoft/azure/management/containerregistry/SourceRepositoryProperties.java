/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.containerregistry.implementation.SourceRepositoryPropertiesInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * The source repository properties for a build task.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_14_0)
public interface SourceRepositoryProperties extends HasInner<SourceRepositoryPropertiesInner> {
    /**
     * @return the source control type
     */
    SourceControlType sourceControlType();

    /**
     * @return the URL to the source code repository
     */
    String repositoryUrl();

    /**
     * @return the whether the source control commit trigger is enabled or not
     */
    boolean isCommitTriggerEnabled();

    /**
     * @return the type of the authentication token
     */
    TokenType authenticationTokenType();

    /**
     * @return the access token used to access the source control provider
     */
    String authenticationToken();

    /**
     * @return the refresh token used to refresh the access token
     */
    String refreshToken();

    /**
     * @return the scope of the access token
     */
    String scope();

    /**
     * @return time in seconds that the token remains valid
     */
    int tokenExpirationTimeInSeconds();
}
