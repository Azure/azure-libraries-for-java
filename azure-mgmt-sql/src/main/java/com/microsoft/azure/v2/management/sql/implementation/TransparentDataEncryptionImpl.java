/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.RefreshableWrapperImpl;
import com.microsoft.azure.v2.management.sql.TransparentDataEncryption;
import com.microsoft.azure.v2.management.sql.TransparentDataEncryptionActivity;
import com.microsoft.azure.v2.management.sql.TransparentDataEncryptionStatus;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation for TransparentDataEncryption.
 */
@LangDefinition
class TransparentDataEncryptionImpl
        extends RefreshableWrapperImpl<TransparentDataEncryptionInner, TransparentDataEncryption>
        implements TransparentDataEncryption {
    private final String sqlServerName;
    private final String resourceGroupName;
    private final SqlServerManager sqlServerManager;
    private final ResourceId resourceId;

    protected TransparentDataEncryptionImpl(String resourceGroupName, String sqlServerName, TransparentDataEncryptionInner innerObject, SqlServerManager sqlServerManager) {
        super(innerObject);
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        this.sqlServerManager = sqlServerManager;
        this.resourceId = ResourceId.fromString(this.inner().id());
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String resourceGroupName() {
        return this.resourceGroupName;
    }

    @Override
    public String sqlServerName() {
        return this.sqlServerName;
    }

    @Override
    public String databaseName() {
        return resourceId.parent().name();
    }

    @Override
    public TransparentDataEncryptionStatus status() {
        return this.inner().status();
    }

    @Override
    public TransparentDataEncryption updateStatus(TransparentDataEncryptionStatus transparentDataEncryptionState) {
        this.inner().withStatus(transparentDataEncryptionState);
        TransparentDataEncryptionInner transparentDataEncryptionInner = this.sqlServerManager.inner().transparentDataEncryptions()
            .createOrUpdate(this.resourceGroupName, this.sqlServerName, this.databaseName(), transparentDataEncryptionState);
        this.setInner(transparentDataEncryptionInner);

        return this;
    }

    @Override
    public Observable<TransparentDataEncryption> updateStatusAsync(TransparentDataEncryptionStatus transparentDataEncryptionState) {
        final TransparentDataEncryptionImpl self = this;
        return this.sqlServerManager.inner().transparentDataEncryptions()
                .createOrUpdateAsync(self.resourceGroupName, self.sqlServerName, self.databaseName(), transparentDataEncryptionState)
                .map(transparentDataEncryptionInner ->  {
                    self.setInner(transparentDataEncryptionInner);
                    return (TransparentDataEncryption) self;
                })
                .toObservable();
    }

    @Override
    public List<TransparentDataEncryptionActivity> listActivities() {
        List<TransparentDataEncryptionActivity> transparentDataEncryptionActivities = new ArrayList<>();
        List<TransparentDataEncryptionActivityInner> transparentDataEncryptionActivityInners = this.sqlServerManager.inner().transparentDataEncryptionActivities()
            .listByConfiguration(this.resourceGroupName, this.sqlServerName, this.databaseName());
        if (transparentDataEncryptionActivityInners != null) {
            for (TransparentDataEncryptionActivityInner transparentDataEncryptionActivityInner : transparentDataEncryptionActivityInners) {
                transparentDataEncryptionActivities.add(new TransparentDataEncryptionActivityImpl(transparentDataEncryptionActivityInner));
            }
        }
        return Collections.unmodifiableList(transparentDataEncryptionActivities);
    }

    @Override
    public Observable<TransparentDataEncryptionActivity> listActivitiesAsync() {
        return this.sqlServerManager.inner().transparentDataEncryptionActivities()
            .listByConfigurationAsync(this.resourceGroupName, this.sqlServerName, this.databaseName())
            .flatMapObservable(transparentDataEncryptionActivityInners ->  Observable.fromIterable(transparentDataEncryptionActivityInners))
            .map(transparentDataEncryptionActivityInner -> new TransparentDataEncryptionActivityImpl(transparentDataEncryptionActivityInner));
    }

    @Override
    protected Maybe<TransparentDataEncryptionInner> getInnerAsync() {
        return this.sqlServerManager.inner().transparentDataEncryptions().getAsync(this.resourceGroupName, this.sqlServerName, this.databaseName());
    }
}
