/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.mediaservices.implementation;

import com.microsoft.azure.management.mediaservices.StreamingPolicy;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import rx.Observable;
import org.joda.time.DateTime;
import com.microsoft.azure.management.mediaservices.EnvelopeEncryption;
import com.microsoft.azure.management.mediaservices.CommonEncryptionCenc;
import com.microsoft.azure.management.mediaservices.CommonEncryptionCbcs;
import com.microsoft.azure.management.mediaservices.NoEncryption;

class StreamingPolicyImpl extends CreatableUpdatableImpl<StreamingPolicy, StreamingPolicyInner, StreamingPolicyImpl> implements StreamingPolicy, StreamingPolicy.Definition, StreamingPolicy.Update {
    private final MediaManager manager;
    private String resourceGroupName;
    private String accountName;
    private String streamingPolicyName;

    StreamingPolicyImpl(String name, MediaManager manager) {
        super(name, new StreamingPolicyInner());
        this.manager = manager;
        // Set resource name
        this.streamingPolicyName = name;
        //
    }

    StreamingPolicyImpl(StreamingPolicyInner inner, MediaManager manager) {
        super(inner.name(), inner);
        this.manager = manager;
        // Set resource name
        this.streamingPolicyName = inner.name();
        // resource ancestor names
        this.resourceGroupName = IdParsingUtils.getValueFromIdByName(inner.id(), "resourceGroups");
        this.accountName = IdParsingUtils.getValueFromIdByName(inner.id(), "mediaServices");
        this.streamingPolicyName = IdParsingUtils.getValueFromIdByName(inner.id(), "streamingPolicies");
        //
    }

    @Override
    public MediaManager manager() {
        return this.manager;
    }

    @Override
    public Observable<StreamingPolicy> createResourceAsync() {
        StreamingPoliciesInner client = this.manager().inner().streamingPolicies();
        return client.createAsync(this.resourceGroupName, this.accountName, this.streamingPolicyName, this.inner())
            .map(innerToFluentMap(this));
    }

    @Override
    public Observable<StreamingPolicy> updateResourceAsync() {
        StreamingPoliciesInner client = this.manager().inner().streamingPolicies();
        return client.createAsync(this.resourceGroupName, this.accountName, this.streamingPolicyName, this.inner())
            .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<StreamingPolicyInner> getInnerAsync() {
        StreamingPoliciesInner client = this.manager().inner().streamingPolicies();
        return client.getAsync(this.resourceGroupName, this.accountName, this.streamingPolicyName);
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }


    @Override
    public CommonEncryptionCbcs commonEncryptionCbcs() {
        return this.inner().commonEncryptionCbcs();
    }

    @Override
    public CommonEncryptionCenc commonEncryptionCenc() {
        return this.inner().commonEncryptionCenc();
    }

    @Override
    public DateTime created() {
        return this.inner().created();
    }

    @Override
    public String defaultContentKeyPolicyName() {
        return this.inner().defaultContentKeyPolicyName();
    }

    @Override
    public EnvelopeEncryption envelopeEncryption() {
        return this.inner().envelopeEncryption();
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public NoEncryption noEncryption() {
        return this.inner().noEncryption();
    }

    @Override
    public String type() {
        return this.inner().type();
    }

    @Override
    public StreamingPolicyImpl withExistingMediaservice(String resourceGroupName, String accountName) {
        this.resourceGroupName = resourceGroupName;
        this.accountName = accountName;
        return this;
    }

    @Override
    public StreamingPolicyImpl withCommonEncryptionCbcs(CommonEncryptionCbcs commonEncryptionCbcs) {
        this.inner().withCommonEncryptionCbcs(commonEncryptionCbcs);
        return this;
    }

    @Override
    public StreamingPolicyImpl withCommonEncryptionCenc(CommonEncryptionCenc commonEncryptionCenc) {
        this.inner().withCommonEncryptionCenc(commonEncryptionCenc);
        return this;
    }

    @Override
    public StreamingPolicyImpl withDefaultContentKeyPolicyName(String defaultContentKeyPolicyName) {
        this.inner().withDefaultContentKeyPolicyName(defaultContentKeyPolicyName);
        return this;
    }

    @Override
    public StreamingPolicyImpl withEnvelopeEncryption(EnvelopeEncryption envelopeEncryption) {
        this.inner().withEnvelopeEncryption(envelopeEncryption);
        return this;
    }

    @Override
    public StreamingPolicyImpl withNoEncryption(NoEncryption noEncryption) {
        this.inner().withNoEncryption(noEncryption);
        return this;
    }

}
