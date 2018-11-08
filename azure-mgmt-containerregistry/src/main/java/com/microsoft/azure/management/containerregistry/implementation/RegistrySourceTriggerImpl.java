/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.AuthInfo;
import com.microsoft.azure.management.containerregistry.AuthInfoUpdateParameters;
import com.microsoft.azure.management.containerregistry.RegistrySourceTrigger;
import com.microsoft.azure.management.containerregistry.RegistryTask;
import com.microsoft.azure.management.containerregistry.SourceControlType;
import com.microsoft.azure.management.containerregistry.SourceProperties;
import com.microsoft.azure.management.containerregistry.SourceTrigger;
import com.microsoft.azure.management.containerregistry.SourceTriggerEvent;
import com.microsoft.azure.management.containerregistry.SourceTriggerUpdateParameters;
import com.microsoft.azure.management.containerregistry.TokenType;
import com.microsoft.azure.management.containerregistry.TriggerStatus;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.ArrayList;
import java.util.List;

@LangDefinition
class RegistrySourceTriggerImpl implements
        RegistrySourceTrigger,
        RegistrySourceTrigger.Definition,
        RegistrySourceTrigger.Update,
        HasInner<SourceTrigger> {
    private SourceTrigger inner;
    private RegistryTaskImpl registryTaskImpl;
    private SourceTriggerUpdateParameters sourceTriggerUpdateParameters;

    RegistrySourceTriggerImpl(RegistryTaskImpl registryTaskImpl) {
        this.registryTaskImpl = registryTaskImpl;
        this.inner = new SourceTrigger();
        this.inner.withSourceRepository(new SourceProperties());
    }

    RegistrySourceTriggerImpl(String sourceTriggerName, RegistryTaskImpl registryTaskImpl) {
        this.registryTaskImpl = registryTaskImpl;
        this.inner = new SourceTrigger();
        this.inner.withSourceRepository(new SourceProperties());

        boolean foundSourceTrigger = false;
        for (SourceTriggerUpdateParameters stup : registryTaskImpl.taskUpdateParameters.trigger().sourceTriggers()) {
            if (stup.name().equals(sourceTriggerName)) {
                this.sourceTriggerUpdateParameters = stup;
                foundSourceTrigger = true;
            }
        }

        if (!foundSourceTrigger) {
            throw new IllegalArgumentException("This task does not have a trigger corresponding to the inputted name");
        }
    }

    @Override
    public RegistrySourceTriggerImpl withName(String name) {
        this.inner.withName(name);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withGithubAsSourceControl() {
        if (isInCreateMode()) {
            this.inner.sourceRepository().withSourceControlType(SourceControlType.GITHUB);
        } else {
            this.sourceTriggerUpdateParameters.sourceRepository().withSourceControlType(SourceControlType.GITHUB);
        }
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withAzureDevOpsAsSourceControl() {
        if (isInCreateMode()) {
            this.inner.sourceRepository().withSourceControlType(SourceControlType.VISUAL_STUDIO_TEAM_SERVICE);
        } else {
            this.sourceTriggerUpdateParameters.sourceRepository().withSourceControlType(SourceControlType.VISUAL_STUDIO_TEAM_SERVICE);
        }
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withSourceControl(SourceControlType sourceControl) {
        if (isInCreateMode()) {
            this.inner.sourceRepository().withSourceControlType(SourceControlType.fromString(sourceControl.toString()));
        } else {
            this.sourceTriggerUpdateParameters.sourceRepository().withSourceControlType(SourceControlType.fromString(sourceControl.toString()));
        }
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withSourceControlRepositoryUrl(String sourceControlRepositoryUrl) {
        if (isInCreateMode()) {
            this.inner.sourceRepository().withRepositoryUrl(sourceControlRepositoryUrl);
        } else {
            this.sourceTriggerUpdateParameters.sourceRepository().withRepositoryUrl(sourceControlRepositoryUrl);
        }
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withRepositoryBranch(String branch) {
        if (isInCreateMode()) {
            this.inner.sourceRepository().withBranch(branch);
        } else {
            this.sourceTriggerUpdateParameters.sourceRepository().withBranch(branch);
        }
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withRepositoryAuthentication(TokenType tokenType, String token) {
        if (isInCreateMode()) {
            AuthInfo authInfo = new AuthInfo()
                    .withTokenType(tokenType)
                    .withToken(token);
            this.inner.sourceRepository().withSourceControlAuthProperties(authInfo);
        } else {
            AuthInfoUpdateParameters authInfoUpdateParameters = new AuthInfoUpdateParameters()
                    .withTokenType(tokenType)
                    .withToken(token);
            this.sourceTriggerUpdateParameters.sourceRepository().withSourceControlAuthProperties(authInfoUpdateParameters);
        }
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withRepositoryAuthentication(TokenType tokenType, String token, String refreshToken, String scope, int expiresIn) {
        if (isInCreateMode()) {
            AuthInfo authInfo = new AuthInfo()
                    .withTokenType(tokenType)
                    .withToken(token)
                    .withRefreshToken(refreshToken)
                    .withScope(scope)
                    .withExpiresIn(expiresIn);
            this.inner.sourceRepository().withSourceControlAuthProperties(authInfo);
        } else {
            AuthInfoUpdateParameters authInfoUpdateParameters = new AuthInfoUpdateParameters()
                    .withTokenType(tokenType)
                    .withToken(token)
                    .withRefreshToken(refreshToken)
                    .withScope(scope)
                    .withExpiresIn(expiresIn);
            this.sourceTriggerUpdateParameters.sourceRepository().withSourceControlAuthProperties(authInfoUpdateParameters);
        }
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withCommitTriggerEvent() {
        if (this.inner.sourceTriggerEvents() == null) {
            this.inner.withSourceTriggerEvents(new ArrayList<SourceTriggerEvent>());
        }
        List<SourceTriggerEvent> sourceTriggerEvents = this.inner.sourceTriggerEvents();
        sourceTriggerEvents.add(SourceTriggerEvent.COMMIT);
        if (isInCreateMode()) {
            this.inner.withSourceTriggerEvents(sourceTriggerEvents);
        } else {
            this.sourceTriggerUpdateParameters.withSourceTriggerEvents(sourceTriggerEvents);
        }
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withPullTriggerEvent() {
        if (this.inner.sourceTriggerEvents() == null) {
            this.inner.withSourceTriggerEvents(new ArrayList<SourceTriggerEvent>());
        }
        List<SourceTriggerEvent> sourceTriggerEvents = this.inner.sourceTriggerEvents();
        sourceTriggerEvents.add(SourceTriggerEvent.PULLREQUEST);
        if (isInCreateMode()) {
            this.inner.withSourceTriggerEvents(sourceTriggerEvents);
        } else {
            this.sourceTriggerUpdateParameters.withSourceTriggerEvents(sourceTriggerEvents);
        }
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withTriggerEvent(SourceTriggerEvent sourceTriggerEvent) {
        if (this.inner.sourceTriggerEvents() == null) {
            this.inner.withSourceTriggerEvents(new ArrayList<SourceTriggerEvent>());
        }
        List<SourceTriggerEvent> sourceTriggerEvents = this.inner.sourceTriggerEvents();
        sourceTriggerEvents.add(SourceTriggerEvent.fromString(sourceTriggerEvent.toString()));
        if (isInCreateMode()) {
            this.inner.withSourceTriggerEvents(sourceTriggerEvents);
        } else {
            this.sourceTriggerUpdateParameters.withSourceTriggerEvents(sourceTriggerEvents);
        }
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withTriggerStatusEnabled() {
        if (isInCreateMode()) {
            this.inner.withStatus(TriggerStatus.ENABLED);
        } else {
            this.sourceTriggerUpdateParameters.withStatus(TriggerStatus.ENABLED);
        }
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withTriggerStatusDisabled() {
        if (isInCreateMode()) {
            this.inner.withStatus(TriggerStatus.DISABLED);
        } else {
            this.sourceTriggerUpdateParameters.withStatus(TriggerStatus.DISABLED);
        }
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withTriggerStatus(TriggerStatus triggerStatus) {
        if (isInCreateMode()) {
            this.inner.withStatus(TriggerStatus.fromString(triggerStatus.toString()));
        } else {
            this.sourceTriggerUpdateParameters.withStatus(TriggerStatus.fromString(triggerStatus.toString()));
        }
        return this;
    }

    @Override
    public RegistryTask.DefinitionStages.TaskCreatable attach() {
        this.registryTaskImpl.withSourceTriggerCreateParameters(this.inner);
        return this.registryTaskImpl;
    }

    @Override
    public SourceTrigger inner() {
        return this.inner;
    }

    private boolean isInCreateMode() {
        if (this.registryTaskImpl.inner().id() == null) {
            return true;
        }
        return false;
    }

    @Override
    public RegistryTask.Update parent() {
        this.registryTaskImpl.withSourceTriggerUpdateParameters(this.sourceTriggerUpdateParameters);
        return this.registryTaskImpl;
    }
}
