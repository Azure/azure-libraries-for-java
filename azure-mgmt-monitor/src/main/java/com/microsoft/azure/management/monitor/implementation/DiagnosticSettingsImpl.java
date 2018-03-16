/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.monitor.implementation;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.monitor.DiagnosticSetting;
import com.microsoft.azure.management.monitor.DiagnosticSettings;
import com.microsoft.azure.management.monitor.DiagnosticSettingsCategory;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.CreatableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.RXMapper;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *  Implementation for DiagnosticSettings.
 */
@LangDefinition
class DiagnosticSettingsImpl
    extends CreatableResourcesImpl<DiagnosticSetting, DiagnosticSettingImpl, DiagnosticSettingsResourceInner>
    implements DiagnosticSettings {

    private final MonitorManager manager;

    DiagnosticSettingsImpl(final MonitorManager manager) {
        this.manager = manager;
    }

    @Override
    public DiagnosticSettingImpl define(String name) {
        return wrapModel(name);
    }

    // Fluent model create helpers

    @Override
    protected DiagnosticSettingImpl wrapModel(String name) {
        DiagnosticSettingsResourceInner inner = new DiagnosticSettingsResourceInner();

        return new DiagnosticSettingImpl(name, inner, this.manager());
    }

    @Override
    protected DiagnosticSettingImpl wrapModel(DiagnosticSettingsResourceInner inner) {
        if (inner == null) {
            return null;
        }
        return new DiagnosticSettingImpl(inner.name(), inner, this.manager());
    }

    @Override
    public MonitorManager manager() {
        return this.manager;
    }

    @Override
    public DiagnosticSettingsInner inner() {
        return this.manager().inner().diagnosticSettings();
    }

    @Override
    public List<DiagnosticSettingsCategory> listCategoriesByResource(String resourceId) {
        List<DiagnosticSettingsCategory> categories = new ArrayList<>();
        DiagnosticSettingsCategoryResourceCollectionInner collection = this.manager().inner().diagnosticSettingsCategorys().list(resourceId);
        if (collection != null) {
            for (DiagnosticSettingsCategoryResourceInner category : collection.value()) {
                categories.add(new DiagnosticSettingsCategoryImpl(category));
            }
        }
        return categories;
    }

    @Override
    public Observable<DiagnosticSettingsCategory> listCategoriesByResourceAsync(String resourceId) {
        return this.manager().inner().diagnosticSettingsCategorys().listAsync(resourceId)
                .flatMap(new Func1<DiagnosticSettingsCategoryResourceCollectionInner, Observable<DiagnosticSettingsCategoryResourceInner>>() {
                    @Override
                    public Observable<DiagnosticSettingsCategoryResourceInner> call(DiagnosticSettingsCategoryResourceCollectionInner diagnosticSettingsCategoryResourceCollectionInner) {
                        return Observable.from(diagnosticSettingsCategoryResourceCollectionInner.value());
                    }
                }).map(new Func1<DiagnosticSettingsCategoryResourceInner, DiagnosticSettingsCategory>() {
                    @Override
                    public DiagnosticSettingsCategory call(DiagnosticSettingsCategoryResourceInner diagnosticSettingsCategoryInner) {
                        return new DiagnosticSettingsCategoryImpl(diagnosticSettingsCategoryInner);
                    }
                });
    }

    @Override
    public DiagnosticSettingsCategory getCategory(String resourceId, String name) {
        return new DiagnosticSettingsCategoryImpl(this.manager().inner().diagnosticSettingsCategorys().get(resourceId, name));
    }

    @Override
    public Observable<DiagnosticSettingsCategory> getCategoryAsync(String resourceId, String name) {
        return this.manager().inner().diagnosticSettingsCategorys().getAsync(resourceId, name)
                .map(new Func1<DiagnosticSettingsCategoryResourceInner, DiagnosticSettingsCategory>() {
                    @Override
                    public DiagnosticSettingsCategory call(DiagnosticSettingsCategoryResourceInner diagnosticSettingsCategoryResourceInner) {
                        return new DiagnosticSettingsCategoryImpl(diagnosticSettingsCategoryResourceInner);
                    }
                });
    }

    @Override
    public PagedList<DiagnosticSetting> listByResource(String resourceId) {
        DiagnosticSettingsResourceCollectionInner result = this.manager().inner().diagnosticSettings().list(resourceId);
        if (result == null) {
            return null;
        }
        return wrapList(result.value());
    }

    @Override
    public Observable<DiagnosticSetting> listByResourceAsync(String resourceId) {
        final DiagnosticSettingsImpl self = this;
        return this.manager().inner().diagnosticSettings().listAsync(resourceId)
                .flatMap(new Func1<DiagnosticSettingsResourceCollectionInner, Observable<DiagnosticSettingsResourceInner>>() {
                    @Override
                    public Observable<DiagnosticSettingsResourceInner> call(DiagnosticSettingsResourceCollectionInner diagnosticSettingsResourceCollectionInner) {
                        return Observable.from(diagnosticSettingsResourceCollectionInner.value());
                    }
                }).map(new Func1<DiagnosticSettingsResourceInner, DiagnosticSetting>() {
                    @Override
                    public DiagnosticSetting call(DiagnosticSettingsResourceInner diagnosticSettingInner) {
                        return new DiagnosticSettingImpl(diagnosticSettingInner.name(), diagnosticSettingInner, self.manager());
                    }
                });
    }

    @Override
    public void delete(String resourceId, String name) {
        this.manager().inner().diagnosticSettings().delete(resourceId, name);
    }

    @Override
    public ServiceFuture<Void> deleteAsync(String resourceId, String name, ServiceCallback<Void> callback) {
        return this.manager().inner().diagnosticSettings().deleteAsync(resourceId, name, callback);
    }

    @Override
    public Completable deleteAsync(String resourceId, String name) {
        return this.manager().inner().diagnosticSettings().deleteAsync(resourceId, name).toCompletable();
    }

    @Override
    public DiagnosticSetting get(String resourceId, String name) {
        return wrapModel(this.manager().inner().diagnosticSettings().get(resourceId, name));
    }

    @Override
    public Observable<DiagnosticSetting> getAsync(String resourceId, String name) {
        return this.manager().inner().diagnosticSettings().getAsync(resourceId, name)
                .map(new Func1<DiagnosticSettingsResourceInner, DiagnosticSetting>() {
                    @Override
                    public DiagnosticSetting call(DiagnosticSettingsResourceInner diagnosticSettingsResourceInner) {
                        return wrapModel(diagnosticSettingsResourceInner);
                    }
                });
    }

    @Override
    public Completable deleteByIdAsync(String id) {
        return this.manager().inner().diagnosticSettings().deleteAsync(getResourceIdFromSettingsId(id), getNameFromSettingsId(id)).toCompletable();
    }

    @Override
    public Observable<String> deleteByIdsAsync(Collection<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Observable.empty();
        }

        Collection<Observable<String>> observables = new ArrayList<>();
        for (String id : ids) {
            final String resourceGroupName = getResourceIdFromSettingsId(id);
            final String name = getNameFromSettingsId(id);
            Observable<String> o = RXMapper.map(this.inner().deleteAsync(resourceGroupName, name), id);
            observables.add(o);
        }

        return Observable.mergeDelayError(observables);
    }

    @Override
    public Observable<String> deleteByIdsAsync(String... ids) {
        return this.deleteByIdsAsync(new ArrayList<String>(Arrays.asList(ids)));
    }

    @Override
    public void deleteByIds(Collection<String> ids) {
        if (ids != null && !ids.isEmpty()) {
            this.deleteByIdsAsync(ids).toBlocking().last();
        }
    }

    @Override
    public void deleteByIds(String... ids) {
        this.deleteByIds(new ArrayList<String>(Arrays.asList(ids)));
    }

    @Override
    public DiagnosticSetting getById(String id) {
        return wrapModel(this.inner().get(getResourceIdFromSettingsId(id), getNameFromSettingsId(id)));
    }

    @Override
    public Observable<DiagnosticSetting> getByIdAsync(String id) {
        return this.inner().getAsync(getResourceIdFromSettingsId(id), getNameFromSettingsId(id))
                .map(new Func1<DiagnosticSettingsResourceInner, DiagnosticSetting>() {
                    @Override
                    public DiagnosticSetting call(DiagnosticSettingsResourceInner diagnosticSettingsResourceInner) {
                        return wrapModel(diagnosticSettingsResourceInner);
                    }
                });
    }

    @Override
    public ServiceFuture<DiagnosticSetting> getByIdAsync(String id, ServiceCallback<DiagnosticSetting> callback) {
        return ServiceFuture.fromBody(getByIdAsync(id), callback);
    }

    private String getResourceIdFromSettingsId(String diagnosticSettingId) {
        if (diagnosticSettingId == null) {
            throw new IllegalArgumentException("Parameter 'resourceId' is required and cannot be null.");
        }
        int dsIdx = diagnosticSettingId.lastIndexOf(DiagnosticSettingImpl.DIAGNOSTIC_SETTINGS_URI);
        if (dsIdx == -1) {
            throw new IllegalArgumentException("Parameter 'resourceId' does not represent a valid Diagnostic Settings resource Id [" + diagnosticSettingId + "].");
        }

        return diagnosticSettingId.substring(0, dsIdx);
    }

    private String getNameFromSettingsId(String diagnosticSettingId) {
        String resourceId = getResourceIdFromSettingsId(diagnosticSettingId);
        return diagnosticSettingId.substring(resourceId.length() + DiagnosticSettingImpl.DIAGNOSTIC_SETTINGS_URI.length());
    }
}