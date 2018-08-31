/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.implementation;

import com.microsoft.azure.v2.management.resources.ExportTemplateRequest;
import com.microsoft.azure.v2.management.resources.ResourceGroup;
import com.microsoft.azure.v2.management.resources.ResourceGroupExportResult;
import com.microsoft.azure.v2.management.resources.ResourceGroupExportTemplateOptions;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The implementation for {@link ResourceGroup} and its create and update interfaces.
 */
class ResourceGroupImpl extends
        CreatableUpdatableImpl<ResourceGroup, ResourceGroupInner, ResourceGroupImpl>
        implements
        ResourceGroup,
        ResourceGroup.Definition,
        ResourceGroup.Update  {

    private final ResourceGroupsInner client;

    protected ResourceGroupImpl(final ResourceGroupInner innerModel, final ResourceManagementClientImpl serviceClient) {
        super(innerModel.name(), innerModel);
        this.client = serviceClient.resourceGroups();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String provisioningState() {
        return this.inner().properties().provisioningState();
    }

    @Override
    public String regionName() {
        return this.inner().location();
    }

    @Override
    public Region region() {
        return Region.fromName(this.regionName());
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String type() {
        return null;
    }

    @Override
    public Map<String, String> tags() {
        Map<String, String> tags = this.inner().tags();
        if (tags == null) {
            tags = new HashMap<>();
        }
        return Collections.unmodifiableMap(tags);
    }

    @Override
    public ResourceGroupExportResult exportTemplate(ResourceGroupExportTemplateOptions options) {
        return this.exportTemplateAsync(options).blockingGet();
    }

    @Override
    public Maybe<ResourceGroupExportResult> exportTemplateAsync(ResourceGroupExportTemplateOptions options) {
        ExportTemplateRequest inner = new ExportTemplateRequest()
                .withResources(Collections.singletonList("*"))
                .withOptions(options.toString());
        return client.exportTemplateAsync(name(), inner).map(new Function<ResourceGroupExportResultInner, ResourceGroupExportResult>() {
            @Override
            public ResourceGroupExportResult apply(ResourceGroupExportResultInner resourceGroupExportResultInner) {
                return new ResourceGroupExportResultImpl(resourceGroupExportResultInner);
            }
        });
    }

    @Override
    public ServiceFuture<ResourceGroupExportResult> exportTemplateAsync(ResourceGroupExportTemplateOptions options, ServiceCallback<ResourceGroupExportResult> callback) {
        return ServiceFuture.fromBody(this.exportTemplateAsync(options), callback);
    }

    @Override
    public ResourceGroupImpl withRegion(String regionName) {
        this.inner().withLocation(regionName);
        return this;
    }

    @Override
    public ResourceGroupImpl withRegion(Region region) {
        return this.withRegion(region.toString());
    }

    @Override
    public ResourceGroupImpl withTags(Map<String, String> tags) {
        this.inner().withTags(new HashMap<>(tags));
        return this;
    }

    @Override
    public ResourceGroupImpl withTag(String key, String value) {
        if (this.inner().tags() == null) {
            this.inner().withTags(new HashMap<String, String>());
        }
        this.inner().tags().put(key, value);
        return this;
    }

    @Override
    public ResourceGroupImpl withoutTag(String key) {
        this.inner().tags().remove(key);
        return this;
    }

    @Override
    public Observable<ResourceGroup> createResourceAsync() {
        ResourceGroupInner params = new ResourceGroupInner();
        params.withLocation(this.inner().location());
        params.withTags(this.inner().tags());
        return client.createOrUpdateAsync(this.name(), params)
                .map(innerToFluentMap(this))
                .toObservable();
    }

    @Override
    public Observable<ResourceGroup> updateResourceAsync() {
        return createResourceAsync();
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }

    @Override
    protected Maybe<ResourceGroupInner> getInnerAsync() {
        return client.getAsync(this.key);
    }
}
