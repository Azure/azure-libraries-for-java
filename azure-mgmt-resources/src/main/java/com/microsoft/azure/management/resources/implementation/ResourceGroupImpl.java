/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.microsoft.azure.management.resources.ExportTemplateRequest;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.ResourceGroupExportResult;
import com.microsoft.azure.management.resources.ResourceGroupExportTemplateOptions;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Observable;
import rx.functions.Func1;

import java.util.Arrays;
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

    protected ResourceGroupImpl(final ResourceGroupInner innerModel, String name, final ResourceManagementClientImpl serviceClient) {
        super(name, innerModel);
        this.client = serviceClient.resourceGroups();
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
        Map<String, String> tags = this.inner().getTags();
        if (tags == null) {
            tags = new HashMap<>();
        }
        return Collections.unmodifiableMap(tags);
    }

    @Override
    public ResourceGroupExportResult exportTemplate(ResourceGroupExportTemplateOptions options) {
        return this.exportTemplateAsync(options).toBlocking().last();
    }

    @Override
    public Observable<ResourceGroupExportResult> exportTemplateAsync(ResourceGroupExportTemplateOptions options) {
        ExportTemplateRequest inner = new ExportTemplateRequest()
                .withResources(Arrays.asList("*"))
                .withOptions(options.toString());
        return client.exportTemplateAsync(name(), inner).map(new Func1<ResourceGroupExportResultInner, ResourceGroupExportResult>() {
            @Override
            public ResourceGroupExportResult call(ResourceGroupExportResultInner resourceGroupExportResultInner) {
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
        if (this.inner().getTags() == null) {
            this.inner().withTags(new HashMap<String, String>());
        }
        this.inner().getTags().put(key, value);
        return this;
    }

    @Override
    public ResourceGroupImpl withoutTag(String key) {
        this.inner().getTags().remove(key);
        return this;
    }

    @Override
    public Observable<ResourceGroup> createResourceAsync() {
        ResourceGroupInner params = new ResourceGroupInner();
        params.withLocation(this.inner().location());
        params.withTags(this.inner().getTags());
        return client.createOrUpdateAsync(this.name(), params)
                .map(innerToFluentMap(this));
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
    protected Observable<ResourceGroupInner> getInnerAsync() {
        return client.getAsync(this.key);
    }
}
