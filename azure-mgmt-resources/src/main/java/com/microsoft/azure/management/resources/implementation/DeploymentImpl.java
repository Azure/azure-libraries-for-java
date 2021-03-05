/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.management.resources.DebugSetting;
import com.microsoft.azure.management.resources.Dependency;
import com.microsoft.azure.management.resources.Deployment;
import com.microsoft.azure.management.resources.DeploymentExportResult;
import com.microsoft.azure.management.resources.DeploymentMode;
import com.microsoft.azure.management.resources.DeploymentOperations;
import com.microsoft.azure.management.resources.DeploymentProperties;
import com.microsoft.azure.management.resources.DeploymentWhatIf;
import com.microsoft.azure.management.resources.DeploymentWhatIfProperties;
import com.microsoft.azure.management.resources.DeploymentWhatIfSettings;
import com.microsoft.azure.management.resources.OnErrorDeployment;
import com.microsoft.azure.management.resources.OnErrorDeploymentType;
import com.microsoft.azure.management.resources.ParametersLink;
import com.microsoft.azure.management.resources.Provider;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.ResourceReference;
import com.microsoft.azure.management.resources.TemplateLink;
import com.microsoft.azure.management.resources.WhatIfOperationResult;
import com.microsoft.azure.management.resources.WhatIfResultFormat;
import com.microsoft.azure.management.resources.fluentcore.arm.Context;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import org.joda.time.DateTime;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.io.IOException;
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
        Deployment.Update,
        Deployment.Execution {

    private final ResourceManager resourceManager;
    private String resourceGroupName;
    private Creatable<ResourceGroup> creatableResourceGroup;
    private ObjectMapper objectMapper;
    private DeploymentWhatIf deploymentWhatIf;
    private DeploymentInner deploymentCreateUpdateParameters;

    DeploymentImpl(DeploymentExtendedInner innerModel, String name, final ResourceManager resourceManager) {
        super(name, innerModel);
        this.resourceGroupName = ResourceUtils.groupFromResourceId(innerModel.id());
        this.resourceManager = resourceManager;
        this.objectMapper = new ObjectMapper();
        this.deploymentWhatIf = new DeploymentWhatIf();
        this.deploymentCreateUpdateParameters = new DeploymentInner();
    }

    @Override
    public String resourceGroupName() {
        return this.resourceGroupName;
    }

    @Override
    public String provisioningState() {
        if (this.inner().properties() == null) {
            return null;
        }
        return this.inner().properties().provisioningState().toString();
    }

    @Override
    public String correlationId() {
        if (this.inner().properties() == null) {
            return null;
        }
        return this.inner().properties().correlationId();
    }

    @Override
    public DateTime timestamp() {
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
    public String templateHash() {
        if (this.inner().properties() == null) {
            return null;
        }
        return this.inner().properties().templateHash();
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
    public List<ResourceReference> outputResources() {
        if (this.inner().properties() == null) {
            return null;
        }
        return inner().properties().outputResources();
    }

    @Override
    public DeploymentOperations deploymentOperations() {
        return new DeploymentOperationsImpl(this.manager().inner().deploymentOperations(), this);
    }

    @Override
    public void cancel() {
        this.cancelAsync().await();
    }

    @Override
    public Completable cancelAsync() {
        return this.manager().inner().deployments().cancelAsync(resourceGroupName, name()).toCompletable();
    }

    @Override
    public ServiceFuture<Void> cancelAsync(ServiceCallback<Void> callback) {
        return ServiceFuture.fromBody(this.cancelAsync(), callback);
    }

    @Override
    public DeploymentExportResult exportTemplate() {
        return this.exportTemplateAsync().toBlocking().last();
    }

    @Override
    public Observable<DeploymentExportResult> exportTemplateAsync() {
        return this.manager().inner().deployments().exportTemplateAsync(resourceGroupName(), name()).map(new Func1<DeploymentExportResultInner, DeploymentExportResult>() {
            @Override
            public DeploymentExportResult call(DeploymentExportResultInner deploymentExportResultInner) {
                return new DeploymentExportResultImpl(deploymentExportResultInner);
            }
        });
    }

    @Override
    public ServiceFuture<DeploymentExportResult> exportTemplateAsync(ServiceCallback<DeploymentExportResult> callback) {
        return ServiceFuture.fromBody(this.exportTemplateAsync(), callback);
    }

    @Override
    public DeploymentImpl prepareWhatIf() {
        return this;
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
        if (this.deploymentCreateUpdateParameters.properties() == null) {
            this.deploymentCreateUpdateParameters.withProperties(new DeploymentProperties());
        }
        this.deploymentCreateUpdateParameters.properties().withTemplate(template);
        this.deploymentCreateUpdateParameters.properties().withTemplateLink(null);
        return this;
    }

    @Override
    public DeploymentImpl withTemplate(String templateJson) throws IOException {
        return withTemplate(objectMapper.readValue(templateJson, Object.class));
    }

    @Override
    public DeploymentImpl withTemplateLink(String uri, String contentVersion) {
        if (this.deploymentCreateUpdateParameters.properties() == null) {
            this.deploymentCreateUpdateParameters.withProperties(new DeploymentProperties());
        }
        this.deploymentCreateUpdateParameters.properties().withTemplateLink(new TemplateLink().withUri(uri).withContentVersion(contentVersion));
        this.deploymentCreateUpdateParameters.properties().withTemplate(null);
        return this;
    }

    @Override
    public DeploymentImpl withMode(DeploymentMode mode) {
        if (this.deploymentCreateUpdateParameters.properties() == null) {
            this.deploymentCreateUpdateParameters.withProperties(new DeploymentProperties());
        }
        this.deploymentCreateUpdateParameters.properties().withMode(mode);
        return this;
    }

    @Override
    public DeploymentImpl withParameters(Object parameters) {
        if (this.deploymentCreateUpdateParameters.properties() == null) {
            this.deploymentCreateUpdateParameters.withProperties(new DeploymentProperties());
        }
        this.deploymentCreateUpdateParameters.properties().withParameters(parameters);
        this.deploymentCreateUpdateParameters.properties().withParametersLink(null);
        return this;
    }

    @Override
    public DeploymentImpl withParameters(String parametersJson) throws IOException {
        return withParameters(objectMapper.readValue(parametersJson, Object.class));
    }

    @Override
    public DeploymentImpl withParametersLink(String uri, String contentVersion) {
        if (this.deploymentCreateUpdateParameters.properties() == null) {
            this.deploymentCreateUpdateParameters.withProperties(new DeploymentProperties());
        }
        this.deploymentCreateUpdateParameters.properties().withParametersLink(new ParametersLink().withUri(uri).withContentVersion(contentVersion));
        this.deploymentCreateUpdateParameters.properties().withParameters(null);
        return this;
    }

    @Override
    public DeploymentImpl beginCreate(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("'context' cannot be null.");
        }
        if (this.creatableResourceGroup != null) {
            this.creatableResourceGroup.create();
        }
        setInner(this.manager().inner().deployments().beginCreateOrUpdate(resourceGroupName(), name(), deploymentCreateUpdateParameters, context));
        prepareForUpdate(this.inner());
        return this;
    }

    @Override
    public Observable<Deployment> beginCreateAsync(final Context context) {
        if (context == null) {
            return Observable.error(new IllegalArgumentException("'context' cannot be null."));
        }
        return Observable.just(creatableResourceGroup)
                .flatMap(new Func1<Creatable<ResourceGroup>, Observable<Indexable>>() {
                    @Override
                    public Observable<Indexable> call(Creatable<ResourceGroup> resourceGroupCreatable) {
                        if (resourceGroupCreatable != null) {
                            return creatableResourceGroup.createAsync();
                        } else {
                            return Observable.just((Indexable) DeploymentImpl.this);
                        }
                    }
                })
                .flatMap(new Func1<Indexable, Observable<DeploymentExtendedInner>>() {
                    @Override
                    public Observable<DeploymentExtendedInner> call(Indexable indexable) {
                        return manager().inner().deployments().beginCreateOrUpdateAsync(resourceGroupName(), name(), deploymentCreateUpdateParameters, context);
                    }
                })
                .map(new Func1<DeploymentExtendedInner, DeploymentExtendedInner>() {
                    @Override
                    public DeploymentExtendedInner call(DeploymentExtendedInner deploymentExtendedInner) {
                        prepareForUpdate(deploymentExtendedInner);
                        return deploymentExtendedInner;
                    }
                })
                .map(innerToFluentMap(this));
    }

    @Override
    public Deployment beginCreate() {
        return beginCreate(Context.NONE);
    }

    @Override
    public Observable<Deployment> beginCreateAsync() {
        return beginCreateAsync(Context.NONE);
    }

    @Override
    public Deployment create(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("'context' cannot be null.");
        }
        return Utils.<Deployment>rootResource(createAsync(context)).toBlocking().single();
    }

    @Override
    public Observable<Indexable> createAsync(final Context context) {
        if (context == null) {
            return Observable.error(new IllegalArgumentException("'context' cannot be null."));
        }
        TaskGroup.InvocationContext invocationContext = this.taskGroup().newInvocationContext();
        invocationContext.put(TaskGroup.InvocationContext.KEY_CONTEXT, context);
        return taskGroup().invokeAsync(invocationContext);
    }

    @Override
    public Observable<Deployment> createResourceAsync(Context context) {
        return this.manager().inner().deployments().createOrUpdateAsync(resourceGroupName(), name(), deploymentCreateUpdateParameters, context)
                .map(new Func1<DeploymentExtendedInner, DeploymentExtendedInner>() {
                    @Override
                    public DeploymentExtendedInner call(DeploymentExtendedInner deploymentExtendedInner) {
                        prepareForUpdate(deploymentExtendedInner);
                        return deploymentExtendedInner;
                    }
                })
                .map(innerToFluentMap(this));
    }

    @Override
    public Observable<Deployment> createResourceAsync() {
        return this.createResourceAsync(Context.NONE);
    }

    private void prepareForUpdate(DeploymentExtendedInner inner) {
        deploymentCreateUpdateParameters = new DeploymentInner();
        deploymentCreateUpdateParameters.withLocation(inner.location());
        deploymentCreateUpdateParameters.withTags(inner.getTags());
        if (inner.properties() != null) {
            deploymentCreateUpdateParameters.withProperties(new DeploymentProperties());
            deploymentCreateUpdateParameters.properties().withDebugSetting(inner.properties().debugSetting());
            deploymentCreateUpdateParameters.properties().withMode(inner.properties().mode());
            deploymentCreateUpdateParameters.properties().withParameters(inner.properties().parameters());
            deploymentCreateUpdateParameters.properties().withParametersLink(inner.properties().parametersLink());
            deploymentCreateUpdateParameters.properties().withTemplateLink(inner.properties().templateLink());
            if (inner.properties().onErrorDeployment() != null) {
                deploymentCreateUpdateParameters.properties().withOnErrorDeployment(new OnErrorDeployment());
                deploymentCreateUpdateParameters.properties().onErrorDeployment().withDeploymentName(inner.properties().onErrorDeployment().deploymentName());
                deploymentCreateUpdateParameters.properties().onErrorDeployment().withType(inner.properties().onErrorDeployment().type());
            }
        }
    }

    @Override
    public Observable<Deployment> applyAsync() {
        return updateResourceAsync();
    }

    @Override
    protected Observable<DeploymentExtendedInner> getInnerAsync() {
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

    @Override
    public String id() {
        return inner().id();
    }

    @Override
    public DeploymentImpl withDetailedLevel(String detailedLevel) {
        if (deploymentWhatIf.properties() == null) {
            deploymentWhatIf.withProperties(new DeploymentWhatIfProperties());
        }
        deploymentWhatIf.properties().withDebugSetting(new DebugSetting().withDetailLevel(detailedLevel));
        return this;
    }

    @Override
    public DeploymentImpl withDeploymentName(String deploymentName) {
        if (deploymentWhatIf.properties() == null) {
            deploymentWhatIf.withProperties(new DeploymentWhatIfProperties());
        }
        if (deploymentWhatIf.properties().onErrorDeployment() == null) {
            deploymentWhatIf.properties().withOnErrorDeployment(new OnErrorDeployment());
        }
        deploymentWhatIf.properties().onErrorDeployment().withDeploymentName(deploymentName);
        return this;
    }

    @Override
    public DeploymentImpl withLocation(String location) {
        this.deploymentWhatIf.withLocation(location);
        return this;
    }

    @Override
    public DeploymentImpl withIncrementalMode() {
        if (deploymentWhatIf.properties() == null) {
            deploymentWhatIf.withProperties(new DeploymentWhatIfProperties());
        }
        deploymentWhatIf.properties().withMode(DeploymentMode.INCREMENTAL);
        return this;
    }

    @Override
    public DeploymentImpl withCompleteMode() {
        if (deploymentWhatIf.properties() == null) {
            deploymentWhatIf.withProperties(new DeploymentWhatIfProperties());
        }
        deploymentWhatIf.properties().withMode(DeploymentMode.COMPLETE);
        return this;
    }

    @Override
    public DeploymentImpl withFullResourcePayloadsResultFormat() {
        if (deploymentWhatIf.properties() == null) {
            deploymentWhatIf.withProperties(new DeploymentWhatIfProperties());
        }
        if (deploymentWhatIf.properties().whatIfSettings() == null) {
            deploymentWhatIf.properties().withWhatIfSettings(new DeploymentWhatIfSettings());
        }
        deploymentWhatIf.properties().whatIfSettings().withResultFormat(WhatIfResultFormat.FULL_RESOURCE_PAYLOADS);
        return this;
    }

    @Override
    public DeploymentImpl withResourceIdOnlyResultFormat() {
        if (deploymentWhatIf.properties() == null) {
            deploymentWhatIf.withProperties(new DeploymentWhatIfProperties());
        }
        if (deploymentWhatIf.properties().whatIfSettings() == null) {
            deploymentWhatIf.properties().withWhatIfSettings(new DeploymentWhatIfSettings());
        }
        deploymentWhatIf.properties().whatIfSettings().withResultFormat(WhatIfResultFormat.RESOURCE_ID_ONLY);
        return this;
    }

    @Override
    public DeploymentImpl withLastSuccessfulOnErrorDeployment() {
        if (deploymentWhatIf.properties() == null) {
            deploymentWhatIf.withProperties(new DeploymentWhatIfProperties());
        }
        if (deploymentWhatIf.properties().onErrorDeployment() == null) {
            deploymentWhatIf.properties().withOnErrorDeployment(new OnErrorDeployment());
        }
        deploymentWhatIf.properties().onErrorDeployment().withType(OnErrorDeploymentType.LAST_SUCCESSFUL);
        return this;
    }

    @Override
    public DeploymentImpl withSpecialDeploymentOnErrorDeployment() {
        if (deploymentWhatIf.properties() == null) {
            deploymentWhatIf.withProperties(new DeploymentWhatIfProperties());
        }
        if (deploymentWhatIf.properties().onErrorDeployment() == null) {
            deploymentWhatIf.properties().withOnErrorDeployment(new OnErrorDeployment());
        }
        deploymentWhatIf.properties().onErrorDeployment().withType(OnErrorDeploymentType.SPECIFIC_DEPLOYMENT);
        return this;
    }

    @Override
    public DeploymentImpl withWhatIfTemplate(Object template) {
        if (deploymentWhatIf.properties() == null) {
            deploymentWhatIf.withProperties(new DeploymentWhatIfProperties());
        }
        deploymentWhatIf.properties().withTemplate(template);
        return this;
    }

    @Override
    public DeploymentImpl withWhatIfTemplateLink(String uri, String contentVersion) {
        if (deploymentWhatIf.properties() == null) {
            deploymentWhatIf.withProperties(new DeploymentWhatIfProperties());
        }
        deploymentWhatIf.properties().withTemplateLink(new TemplateLink().withUri(uri).withContentVersion(contentVersion));
        return this;
    }

    @Override
    public DeploymentImpl withWhatIfParameters(Object parameters) {
        if (deploymentWhatIf.properties() == null) {
            deploymentWhatIf.withProperties(new DeploymentWhatIfProperties());
        }
        deploymentWhatIf.properties().withParameters(parameters);
        return this;
    }

    @Override
    public DeploymentImpl withWhatIfParametersLink(String uri, String contentVersion) {
        if (deploymentWhatIf.properties() == null) {
            deploymentWhatIf.withProperties(new DeploymentWhatIfProperties());
        }
        deploymentWhatIf.properties().withParametersLink(new ParametersLink().withUri(uri).withContentVersion(contentVersion));
        return this;
    }

    @Override
    public WhatIfOperationResult whatIf() {
        return this.whatIfAsync().toBlocking().last();
    }

    @Override
    public Observable<WhatIfOperationResult> whatIfAsync() {
        return this.manager().inner().deployments().whatIfAsync(resourceGroupName(), name(), deploymentWhatIf).map(new Func1<WhatIfOperationResultInner, WhatIfOperationResult>() {
            @Override
            public WhatIfOperationResult call(WhatIfOperationResultInner whatIfOperationResultInner) {
                return new WhatIfOperationResultImpl(whatIfOperationResultInner);
            }
        });
    }

    @Override
    public ServiceFuture<WhatIfOperationResult> whatIfAsync(ServiceCallback<WhatIfOperationResult> callback) {
        return ServiceFuture.fromBody(this.whatIfAsync(), callback);
    }

    @Override
    public WhatIfOperationResult whatIfAtSubscriptionScope() {
        return this.whatIfAtSubscriptionScopeAsync().toBlocking().last();
    }

    @Override
    public Observable<WhatIfOperationResult> whatIfAtSubscriptionScopeAsync() {
        return this.manager().inner().deployments().whatIfAtSubscriptionScopeAsync(name(), deploymentWhatIf).map(new Func1<WhatIfOperationResultInner, WhatIfOperationResult>() {
            @Override
            public WhatIfOperationResult call(WhatIfOperationResultInner whatIfOperationResultInner) {
                return new WhatIfOperationResultImpl(whatIfOperationResultInner);
            }
        });
    }

    @Override
    public ServiceFuture<WhatIfOperationResult> whatIfAtSubscriptionScopeAsync(ServiceCallback<WhatIfOperationResult> callback) {
        return ServiceFuture.fromBody(this.whatIfAtSubscriptionScopeAsync(), callback);
    }
}
