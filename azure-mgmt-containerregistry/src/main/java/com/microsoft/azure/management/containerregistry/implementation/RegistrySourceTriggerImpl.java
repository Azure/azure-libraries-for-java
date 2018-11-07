/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.AuthInfo;
import com.microsoft.azure.management.containerregistry.RegistrySourceTrigger;
import com.microsoft.azure.management.containerregistry.RegistryTask;
import com.microsoft.azure.management.containerregistry.SourceControlType;
import com.microsoft.azure.management.containerregistry.SourceProperties;
import com.microsoft.azure.management.containerregistry.SourceTrigger;
import com.microsoft.azure.management.containerregistry.SourceTriggerEvent;
import com.microsoft.azure.management.containerregistry.TokenType;
import com.microsoft.azure.management.containerregistry.TriggerStatus;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.ArrayList;
import java.util.List;

@LangDefinition
class RegistrySourceTriggerImpl implements
        RegistrySourceTrigger,
        RegistrySourceTrigger.Definition,
        HasInner<SourceTrigger> {
    private SourceTrigger inner;
    private RegistryTaskImpl registryTaskImpl;

    RegistrySourceTriggerImpl(RegistryTaskImpl registryTaskImpl) {
        this.registryTaskImpl = registryTaskImpl;
        this.inner = new SourceTrigger();
        this.inner.withSourceRepository(new SourceProperties());
    }

    @Override
    public RegistrySourceTriggerImpl withName(String name) {
        this.inner.withName(name);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withGithubAsSourceControl() {
        this.inner.sourceRepository().withSourceControlType(SourceControlType.GITHUB);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withAzureDevOpsAsSourceControl() {
        this.inner.sourceRepository().withSourceControlType(SourceControlType.VISUAL_STUDIO_TEAM_SERVICE);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withSourceControl(DefinitionStages.SourceControlType sourceControl) {
        this.inner.sourceRepository().withSourceControlType(SourceControlType.fromString(sourceControl.toString()));
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withSourceControlRepositoryUrl(String sourceControlRepositoryUrl) {
        this.inner.sourceRepository().withRepositoryUrl(sourceControlRepositoryUrl);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withRepositoryBranch(String branch) {
        this.inner.sourceRepository().withBranch(branch);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withRepositoryAuthentication(TokenType tokenType, String token) {
        AuthInfo authInfo = new AuthInfo()
                .withTokenType(tokenType)
                .withToken(token);
        this.inner.sourceRepository().withSourceControlAuthProperties(authInfo);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withRepositoryAuthentication(TokenType tokenType, String token, String refreshToken, String scope, int expiresIn) {
        AuthInfo authInfo = new AuthInfo()
                .withTokenType(tokenType)
                .withToken(token)
                .withRefreshToken(refreshToken)
                .withScope(scope)
                .withExpiresIn(expiresIn);
        this.inner.sourceRepository().withSourceControlAuthProperties(authInfo);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withCommitTriggerEvent() {
        if (this.inner.sourceTriggerEvents() == null) {
            this.inner.withSourceTriggerEvents(new ArrayList<SourceTriggerEvent>());
        }
        List<SourceTriggerEvent> sourceTriggerEvents = this.inner.sourceTriggerEvents();
        sourceTriggerEvents.add(SourceTriggerEvent.COMMIT);
        this.inner.withSourceTriggerEvents(sourceTriggerEvents);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withPullTriggerEvent() {
        if (this.inner.sourceTriggerEvents() == null) {
            this.inner.withSourceTriggerEvents(new ArrayList<SourceTriggerEvent>());
        }
        List<SourceTriggerEvent> sourceTriggerEvents = this.inner.sourceTriggerEvents();
        sourceTriggerEvents.add(SourceTriggerEvent.PULLREQUEST);
        this.inner.withSourceTriggerEvents(sourceTriggerEvents);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withTriggerEvent(SourceTriggerEvent sourceTriggerEvent) {
        if (this.inner.sourceTriggerEvents() == null) {
            this.inner.withSourceTriggerEvents(new ArrayList<SourceTriggerEvent>());
        }
        List<SourceTriggerEvent> sourceTriggerEvents = this.inner.sourceTriggerEvents();
        sourceTriggerEvents.add(SourceTriggerEvent.fromString(sourceTriggerEvent.toString()));
        this.inner.withSourceTriggerEvents(sourceTriggerEvents);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withTriggerStatusEnabled() {
        this.inner.withStatus(TriggerStatus.ENABLED);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withTriggerStatusDisabled() {
        this.inner.withStatus(TriggerStatus.DISABLED);
        return this;
    }

    @Override
    public RegistrySourceTriggerImpl withTriggerStatus(TriggerStatus triggerStatus) {
        this.inner.withStatus(TriggerStatus.fromString(triggerStatus.toString()));
        return this;
    }

    @Override
    public RegistryTask.DefinitionStages.TaskCreatable attach() {
        this.registryTaskImpl.withSourceTrigger(this.inner);
        return this.registryTaskImpl;
    }

    @Override
    public SourceTrigger inner() {
        return this.inner;
    }
}
