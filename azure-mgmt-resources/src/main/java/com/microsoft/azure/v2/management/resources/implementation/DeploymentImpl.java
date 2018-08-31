/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.v2.management.resources.Dependency;
import com.microsoft.azure.v2.management.resources.Deployment;
import com.microsoft.azure.v2.management.resources.DeploymentExportResult;
import com.microsoft.azure.v2.management.resources.DeploymentMode;
import com.microsoft.azure.v2.management.resources.DeploymentOperations;
import com.microsoft.azure.v2.management.resources.DeploymentProperties;
import com.microsoft.azure.v2.management.resources.DeploymentPropertiesExtended;
import com.microsoft.azure.v2.management.resources.ParametersLink;
import com.microsoft.azure.v2.management.resources.Provider;
import com.microsoft.azure.v2.management.resources.ResourceGroup;
import com.microsoft.azure.v2.management.resources.TemplateLink;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of {@link Deployment} and its nested interfaces.
 */
public final class DeploymentImpl extends
        CreatableUpdatableImpl<Deployment, DeploymentExtendedInner, DeploymentImpl>
        implements
        Deployment,
        Deployment.Definition,
        Deployment.Update {

    private final ResourceManager resourceManager;
    private String resourceGroupName;
    private Creatable<ResourceGroup> creatableResourceGroup;
    private ObjectMapper objectMapper;

    DeploymentImpl(DeploymentExtendedInner innerModel, final ResourceManager resourceManager) {
        super(innerModel.name(), innerModel);
        this.resourceGroupName = ResourceUtils.groupFromResourceId(innerModel.id());
        this.resourceManager = resourceManager;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String resourceGroupName() {
        return this.resourceGroupName;
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String provisioningState() {
        if (this.inner().properties() == null) {
            return null;
        }
        return this.inner().properties().provisioningState();
    }

    @Override
    public String correlationId() {
        if (this.inner().properties() == null) {
            return null;
        }
        return this.inner().properties().correlationId();
    }

    @Override
    public OffsetDateTime timestamp() {
        if (this.inner().properties() == null) {
            return null;
        }
        return this.inner().properties().timestamp();
    }

    @Override
    public Object outputs() {
        if (this.inner().properties() == null) {
            return null;
        }
        return this.inner().properties().outputs();
    }

    @Override
    public List<Provider> providers() {
        if (this.inner().properties() == null) {
            return null;
        }
        List<Provider> providers = new ArrayList<>();
        for (ProviderInner inner : this.inner().properties().providers()) {
            providers.add(new ProviderImpl(inner));
        }
        return providers;
    }

    @Override
    public List<Dependency> dependencies() {
        if (this.inner().properties() == null) {
            return null;
        }
        return this.inner().properties().dependencies();
    }

    @Override
    public Object template() {
        if (this.inner().properties() == null) {
            return null;
        }
        return this.inner().properties().template();
    }

    @Override
    public TemplateLink templateLink() {
        if (this.inner().properties() == null) {
            return null;
        }
        return this.inner().properties().templateLink();
    }

    @Override
    public Object parameters() {
        if (this.inner().properties() == null) {
            return null;
        }
        return this.inner().properties().parameters();
    }

    @Override
    public ParametersLink parametersLink() {
        if (this.inner().properties() == null) {
            return null;
        }
        return this.inner().properties().parametersLink();
    }

    @Override
    public DeploymentMode mode() {
        if (this.inner().properties() == null) {
            return null;
        }
        return inner().properties().mode();
    }

    @Override
    public DeploymentOperations deploymentOperations() {
        return new DeploymentOperationsImpl(this.manager().inner().deploymentOperations(), this);
    }

    @Override
    public void cancel() {
        this.cancelAsync().blockingAwait();
    }

    @Override
    public Completable cancelAsync() {
        return this.manager().inner().deployments().cancelAsync(resourceGroupName, name());
    }

    @Override
    public ServiceFuture<Void> cancelAsync(ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(this.cancelAsync(), callback);
    }

    @Override
    public DeploymentExportResult exportTemplate() {
        return this.exportTemplateAsync().blockingGet();
    }

    @Override
    public Maybe<DeploymentExportResult> exportTemplateAsync() {
        return this.manager().inner().deployments().exportTemplateAsync(resourceGroupName(), name()).map(new Function<DeploymentExportResultInner, DeploymentExportResult>() {
            @Override
            public DeploymentExportResult apply(DeploymentExportResultInner deploymentExportResultInner) {
                return new DeploymentExportResultImpl(deploymentExportResultInner);
            }
        });
    }

    @Override
    public ServiceFuture<DeploymentExportResult> exportTemplateAsync(ServiceCallback<DeploymentExportResult> callback) {
        return ServiceFuture.fromBody(this.exportTemplateAsync(), callback);
    }

    // Withers

    @Override
    public DeploymentImpl withNewResourceGroup(String resourceGroupName, Region region) {
        this.creatableResourceGroup = this.resourceManager.resourceGroups()
                .define(resourceGroupName)
                .withRegion(region);
        this.addDependency(this.creatableResourceGroup);
        this.resourceGroupName = resourceGroupName;
        return this;
    }

    @Override
    public DeploymentImpl withNewResourceGroup(Creatable<ResourceGroup> resourceGroupDefinition) {
        this.resourceGroupName = resourceGroupDefinition.name();
        this.addDependency(resourceGroupDefinition);
        this.creatableResourceGroup = resourceGroupDefinition;
        return this;
    }

    @Override
    public DeploymentImpl withExistingResourceGroup(String resourceGroupName) {
        this.resourceGroupName = resourceGroupName;
        return this;
    }

    @Override
    public DeploymentImpl withExistingResourceGroup(ResourceGroup resourceGroup) {
        this.resourceGroupName = resourceGroup.name();
        return this;
    }

    @Override
    public DeploymentImpl withTemplate(Object template) {
        if (this.inner().properties() == null) {
            this.inner().withProperties(new DeploymentPropertiesExtended());
        }
        this.inner().properties().withTemplate(template);
        this.inner().properties().withTemplateLink(null);
        return this;
    }

    @Override
    public DeploymentImpl withTemplate(String templateJson) throws IOException {
        return withTemplate(objectMapper.readTree(templateJson));
    }

    @Override
    public DeploymentImpl withTemplateLink(String uri, String contentVersion) {
        if (this.inner().properties() == null) {
            this.inner().withProperties(new DeploymentPropertiesExtended());
        }
        this.inner().properties().withTemplateLink(new TemplateLink().withUri(uri).withContentVersion(contentVersion));
        this.inner().properties().withTemplate(null);
        return this;
    }

    @Override
    public DeploymentImpl withMode(DeploymentMode mode) {
        if (this.inner().properties() == null) {
            this.inner().withProperties(new DeploymentPropertiesExtended());
        }
        this.inner().properties().withMode(mode);
        return this;
    }

    @Override
    public DeploymentImpl withParameters(Object parameters) {
        if (this.inner().properties() == null) {
            this.inner().withProperties(new DeploymentPropertiesExtended());
        }
        this.inner().properties().withParameters(parameters);
        this.inner().properties().withParametersLink(null);
        return this;
    }

    @Override
    public DeploymentImpl withParameters(String parametersJson) throws IOException {
        return withParameters(objectMapper.readTree(parametersJson));
    }

    @Override
    public DeploymentImpl withParametersLink(String uri, String contentVersion) {
        if (this.inner().properties() == null) {
            this.inner().withProperties(new DeploymentPropertiesExtended());
        }
        this.inner().properties().withParametersLink(new ParametersLink().withUri(uri).withContentVersion(contentVersion));
        this.inner().properties().withParameters(null);
        return this;
    }

    @Override
    public DeploymentImpl beginCreate() {
        if (this.creatableResourceGroup != null) {
            this.creatableResourceGroup.create();
        }
        setInner(this.manager().inner().deployments().beginCreateOrUpdate(resourceGroupName(), name(), createRequestFromInner()));
        return this;
    }

    private DeploymentProperties createRequestFromInner() {
        DeploymentProperties props = new DeploymentProperties();
        props.withMode(mode());
        props.withTemplate(template());
        props.withTemplateLink(templateLink());
        props.withParameters(parameters());
        props.withParametersLink(parametersLink());
        return props;
    }

    @Override
    public Observable<Deployment> beginCreateAsync() {
        return Observable.just(creatableResourceGroup)
                .flatMap(resourceGroupCreatable -> {
                    if (resourceGroupCreatable != null) {
                        return creatableResourceGroup.createAsync();
                    } else {
                        return Observable.just((Indexable) DeploymentImpl.this);
                    }
                })
                .flatMap(indexable ->manager().inner().deployments().beginCreateOrUpdateAsync(resourceGroupName(), name(), createRequestFromInner()))
                .map(status -> status.result())
                .map(innerToFluentMap(this));
    }

    @Override
    public Observable<Deployment> createResourceAsync() {
        return this.manager().inner().deployments().createOrUpdateAsync(resourceGroupName(), name(), createRequestFromInner())
                .map(innerToFluentMap(this))
                .toObservable();
    }

    @Override
    public Observable<Deployment> applyAsync() {
        return updateResourceAsync();
    }

    @Override
    public Observable<Deployment> updateResourceAsync() {
        try {
            if (this.templateLink() != null && this.template() != null) {
                this.withTemplate(null);
            }
            if (this.parametersLink() != null && this.parameters() != null) {
                this.withParameters(null);
            }
        } catch (IOException e) {
            return Observable.error(e);
        }
        return createResourceAsync();
    }

    @Override
    protected Maybe<DeploymentExtendedInner> getInnerAsync() {
        return this.manager().inner().deployments().getByResourceGroupAsync(resourceGroupName(), name());
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }

    @Override
    public ResourceManager manager() {
        return this.resourceManager;
    }
}
