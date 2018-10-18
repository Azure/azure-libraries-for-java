///**
// * Copyright (c) Microsoft Corporation. All rights reserved.
// * Licensed under the MIT License. See License.txt in the project root for
// * license information.
// */
//package com.microsoft.azure.management.containerregistry.implementation;
//
//import com.microsoft.azure.Page;
//import com.microsoft.azure.PagedList;
//import com.microsoft.azure.management.apigeneration.LangDefinition;
//import com.microsoft.azure.management.containerregistry.Build;
//import com.microsoft.azure.management.containerregistry.BuildTaskBuildRequest;
//import com.microsoft.azure.management.containerregistry.QueuedBuildOperations;
//import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
//import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
//import rx.Completable;
//import rx.Observable;
//import rx.functions.Func1;
//
///**
// * Represents a queued build collection of operations associated with a container registry.
// */
//@LangDefinition
//public class QueuedBuildOperationsImpl implements QueuedBuildOperations {
//    private final RegistryImpl containerRegistry;
//
//    QueuedBuildOperationsImpl(RegistryImpl containerRegistry) {
//        this.containerRegistry = containerRegistry;
//    }
//
//    @Override
//    public Build queueBuildTask(String buildTaskName) {
//        return this.queueBuildTaskAsync(buildTaskName).toBlocking().single();
//    }
//
//    @Override
//    public Observable<Build> queueBuildTaskAsync(String buildTaskName) {
//        BuildTaskBuildRequest buildTaskBuildRequest = new BuildTaskBuildRequest().withBuildTaskName(buildTaskName);
//        return this.containerRegistry.manager().inner().registries()
//            .queueBuildAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), buildTaskBuildRequest)
//            .map(new Func1<BuildInner, Build>() {
//                @Override
//                public Build call(BuildInner buildInner) {
//                    return null;
//                }
//            });
//    }
//
//    @Override
//    public Build.QueuedQuickBuildDefinitionStages.Blank queueQuickBuild() {
//        // the name of the queued build is the build ID and it will be assigned after create
//        BuildImpl build = new BuildImpl(null, this.containerRegistry, new BuildInner());
//        build.setPendingOperation(ExternalChildResourceImpl.PendingOperation.ToBeCreated);
//        return build;
//    }
//
//    @Override
//    public Build get(String buildId) {
//        return this.getAsync(buildId).toBlocking().single();
//    }
//
//    @Override
//    public Observable<Build> getAsync(String buildId) {
//        final QueuedBuildOperationsImpl self = this;
//        return this.containerRegistry.manager().inner().builds()
//            .getAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), buildId)
//            .map(new Func1<BuildInner, Build>() {
//                @Override
//                public Build call(BuildInner buildInner) {
//                    return new BuildImpl(buildInner.name(), self.containerRegistry, buildInner);
//                }
//            });
//    }
//
//    @Override
//    public void cancel(String buildId) {
//        this.containerRegistry.manager().inner().builds()
//            .cancel(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), buildId);
//    }
//
//    @Override
//    public Completable cancelAsync(String buildId) {
//        return this.containerRegistry.manager().inner().builds()
//            .cancelAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), buildId).toCompletable();
//    }
//
//    @Override
//    public PagedList<Build> list() {
//        final QueuedBuildOperationsImpl self = this;
//        final PagedListConverter<BuildInner, Build> converter = new PagedListConverter<BuildInner, Build>() {
//            @Override
//            public Observable<Build> typeConvertAsync(BuildInner inner) {
//                return Observable.just((Build) new BuildImpl(inner.name(), self.containerRegistry, inner));
//            }
//        };
//
//        return converter.convert(this.containerRegistry.manager().inner().builds()
//            .list(this.containerRegistry.resourceGroupName(), this.containerRegistry.name()));
//    }
//
//    @Override
//    public Observable<Build> listAsync() {
//        final QueuedBuildOperationsImpl self = this;
//
//        return this.containerRegistry.manager().inner().builds()
//            .listAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name())
//            .flatMap(new Func1<Page<BuildInner>, Observable<BuildInner>>() {
//                @Override
//                public Observable<BuildInner> call(Page<BuildInner> buildInnerPage) {
//                    return Observable.from(buildInnerPage.items());
//                }
//            }).map(new Func1<BuildInner, Build>() {
//                @Override
//                public Build call(BuildInner inner) {
//                    return new BuildImpl(inner.name(), self.containerRegistry, inner);
//                }
//            });
//    }
//
//    @Override
//    public PagedList<Build> listWithFilterAndTop(String filter, int top) {
//        final QueuedBuildOperationsImpl self = this;
//        final PagedListConverter<BuildInner, Build> converter = new PagedListConverter<BuildInner, Build>() {
//            @Override
//            public Observable<Build> typeConvertAsync(BuildInner inner) {
//                return Observable.just((Build) new BuildImpl(inner.name(), self.containerRegistry, inner));
//            }
//        };
//
//        return converter.convert(this.containerRegistry.manager().inner().builds()
//            .list(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), filter, top, null));
//    }
//
//    @Override
//    public Observable<Build> listWithFilterAndTopAsync(String filter, int top) {
//        final QueuedBuildOperationsImpl self = this;
//
//        return this.containerRegistry.manager().inner().builds()
//            .listAsync(this.containerRegistry.resourceGroupName(), this.containerRegistry.name(), filter, top, null)
//            .flatMap(new Func1<Page<BuildInner>, Observable<BuildInner>>() {
//                @Override
//                public Observable<BuildInner> call(Page<BuildInner> buildInnerPage) {
//                    return Observable.from(buildInnerPage.items());
//                }
//            }).map(new Func1<BuildInner, Build>() {
//                @Override
//                public Build call(BuildInner inner) {
//                    return new BuildImpl(inner.name(), self.containerRegistry, inner);
//                }
//            });
//
//    }
//}
