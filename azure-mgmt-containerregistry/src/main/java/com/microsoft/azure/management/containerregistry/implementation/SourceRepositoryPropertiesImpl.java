/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.SourceControlType;
import com.microsoft.azure.management.containerregistry.SourceRepositoryProperties;
import com.microsoft.azure.management.containerregistry.TokenType;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

/**
 * Implementation for SourceRepositoryProperties.
 */
@LangDefinition
public class SourceRepositoryPropertiesImpl
    extends WrapperImpl<SourceRepositoryPropertiesInner>
    implements SourceRepositoryProperties {

    /**
     * Creates an instance of the SourceRepositoryProperties object.
     *
     * @param innerObject the inner object
     */
    SourceRepositoryPropertiesImpl(SourceRepositoryPropertiesInner innerObject) {
        super(innerObject);
    }

    @Override
    public SourceControlType sourceControlType() {
        return this.inner().sourceControlType();
    }

    @Override
    public String repositoryUrl() {
        return this.inner().repositoryUrl();
    }

    @Override
    public boolean isCommitTriggerEnabled() {
        return this.inner().isCommitTriggerEnabled();
    }

    @Override
    public TokenType authenticationTokenType() {
        if (this.inner().sourceControlAuthProperties() != null) {
            return this.inner().sourceControlAuthProperties().tokenType();
        } else {
            return null;
        }
    }

    @Override
    public String authenticationToken() {
        if (this.inner().sourceControlAuthProperties() != null) {
            return this.inner().sourceControlAuthProperties().token();
        } else {
            return null;
        }
    }

    @Override
    public String refreshToken() {
        if (this.inner().sourceControlAuthProperties() != null) {
            return this.inner().sourceControlAuthProperties().refreshToken();
        } else {
            return null;
        }
    }

    @Override
    public String scope() {
        if (this.inner().sourceControlAuthProperties() != null) {
            return this.inner().sourceControlAuthProperties().scope();
        } else {
            return null;
        }
    }

    @Override
    public int tokenExpirationTimeInSeconds() {
        if (this.inner().sourceControlAuthProperties() != null) {
            return this.inner().sourceControlAuthProperties().expiresIn();
        } else {
            return 0;
        }
    }
}
