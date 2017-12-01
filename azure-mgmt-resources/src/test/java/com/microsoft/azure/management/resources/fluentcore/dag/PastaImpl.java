/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.dag;

import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreateUpdateTask;
import org.junit.Assert;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link IPasta}
 */
class PastaImpl
        extends CreatableUpdatableImpl<IPasta, PastaInner, PastaImpl>
        implements IPasta {
    final List<Creatable<IPasta>> delayedPastas;
    final long eventDelayInMilliseconds;
    final Throwable errorToThrow;
    boolean prepareCalled = false;

    public PastaImpl(String name, long eventDelayInMilliseconds) {
        this(name, eventDelayInMilliseconds, false);
    }

    public PastaImpl(String name, long eventDelayInMilliseconds, boolean fault) {
        super(name, name, new PastaInner());
        this.eventDelayInMilliseconds = eventDelayInMilliseconds;
        if (fault) {
            this.errorToThrow = new RuntimeException(name);
        } else {
            this.errorToThrow = null;
        }
        delayedPastas = new ArrayList<>();
    }

    /**
     * a pasta specified via this wither will be added immediately as dependency.
     *
     * @param pasta the pasta
     * @return the next stage of pasta
     */
    @Override
    public PastaImpl withInstantPasta(Creatable<IPasta> pasta) {
        this.addDependency(pasta);
        return this;
    }

    /**
     * a pancake specified via this wither will not be added immediately as a dependency, will be added only
     * inside beforeGroupCreateOrUpdate {@link CreateUpdateTask.ResourceCreatorUpdater#beforeGroupCreateOrUpdate()}
     *
     * @param pasta the pasta
     * @return the next stage of pasta
     */
    @Override
    public PastaImpl withDelayedPasta(Creatable<IPasta> pasta) {
        this.delayedPastas.add(pasta);
        return this;
    }


    @Override
    public void beforeGroupCreateOrUpdate() {
        Assert.assertFalse("PastaImpl::beforeGroupCreateOrUpdate() should not be called multiple times", this.prepareCalled);
        prepareCalled = true;
        int oldCount = this.taskGroup().getNode(this.key()).dependencyKeys().size();
        for(Creatable<IPasta> pancake : this.delayedPastas) {
            this.addDependency(pancake);
        }
        int newCount = this.taskGroup().getNode(this.key()).dependencyKeys().size();
        System.out.println("Pasta(" + this.name() + ")::beforeGroupCreateOrUpdate() 'delayedSize':" + this.delayedPastas.size()
                + " 'dependency count [old, new]': [" + oldCount + "," + newCount + "]");
    }

    @Override
    public Observable<IPasta> createResourceAsync() {
        if (this.errorToThrow == null) {
            System.out.println("Pasta(" + this.name() + ")::createResourceAsync() 'onNext()'");
            return Observable.just(this)
                    .delay(this.eventDelayInMilliseconds, TimeUnit.MILLISECONDS)
                    .map(new Func1<PastaImpl, IPasta>() {
                        @Override
                        public IPasta call(PastaImpl pasta) {
                            return pasta;
                        }
                    });
        } else {
            System.out.println("Pasta(" + this.name() + ")::createResourceAsync() 'onError()'");
            return Observable.just(this)
                    .delay(this.eventDelayInMilliseconds, TimeUnit.MILLISECONDS)
                    .flatMap(new Func1<PastaImpl, Observable<IPasta>>() {
                        @Override
                        public Observable<IPasta> call(PastaImpl pasta) {
                            return toErrorObservable(errorToThrow);
                        }
                    });
        }
    }

    @Override
    public boolean isInCreateMode() {
        return true;
    }

    @Override
    protected Observable<PastaInner> getInnerAsync() {
        return Observable.just(this.inner());
    }

    private Observable<IPasta> toErrorObservable(Throwable throwable) {
        return Observable.error(throwable);
    }
}
