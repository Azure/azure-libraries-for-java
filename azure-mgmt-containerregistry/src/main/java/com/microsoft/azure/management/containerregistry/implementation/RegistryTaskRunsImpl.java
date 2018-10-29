/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.containerregistry.RegistryTaskRun;
import com.microsoft.azure.management.containerregistry.RegistryTaskRuns;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

class RegistryTaskRunsImpl implements RegistryTaskRuns {

    private ContainerRegistryManager registryManager;

    RegistryTaskRunsImpl(ContainerRegistryManager registryManager) {
        this.registryManager = registryManager;
    }


    @Override
    public RegistryTaskRun.DefinitionStages.BlankFromRuns scheduleRun() {
        return new RegistryTaskRunImpl(registryManager, new RunInner());
    }

    @Override
    public Observable<RegistryTaskRun> listByRegistryAsync(String rgName, String acrName) {
        return this.registryManager.inner().runs().listAsync(rgName, acrName)
                .flatMapIterable(new Func1<Page<RunInner>, Iterable<RunInner>>() {
                    @Override
                    public Iterable<RunInner> call(Page<RunInner> page) {
                        return page.items();
                    }
                })
                .map(new Func1<RunInner, RegistryTaskRun>() {
                    @Override
                    public RegistryTaskRun call(RunInner inner) {
                        return wrapModel(inner);
                    }
                });
    }

    @Override
    public PagedList<RegistryTaskRun> listByRegistry(String rgName, String acrName) {
        return (new PagedListConverter<RunInner, RegistryTaskRun>() {
            @Override
            public Observable<RegistryTaskRun> typeConvertAsync(final RunInner inner) {
                return Observable.<RegistryTaskRun>just(wrapModel(inner));
            }
        }).convert(this.registryManager.inner().runs().list(rgName, acrName));
    }

    @Override
    public Observable<String> getLogSasUrlAsync(String rgName, String acrName, String runId) {
        return this.registryManager.inner().runs().getLogSasUrlAsync(rgName, acrName, runId).map(new Func1<RunGetLogResultInner, String>() {
            @Override
            public String call(RunGetLogResultInner runGetLogResultInner) {
                return runGetLogResultInner.logLink();
            }
        });
    }

    @Override
    public String getLogSasUrl(String rgName, String acrName, String runId) {
        return this.getLogSasUrlAsync(rgName, acrName, runId).toBlocking().last();
    }

    @Override
    public Completable cancelAsync(String rgName, String acrName, String runId) {
        return this.registryManager.inner().runs().cancelAsync(rgName, acrName, runId).toCompletable();
    }

    @Override
    public void cancel(String rgName, String acrName, String runId) {
        this.cancelAsync(rgName, acrName, runId).await();
    }

    private RegistryTaskRunImpl wrapModel(RunInner innerModel) {
        return new RegistryTaskRunImpl(registryManager, innerModel);
    }
}
