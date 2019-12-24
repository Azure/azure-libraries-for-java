/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.dag;

import com.azure.management.resources.fluentcore.model.Creatable;
import com.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.azure.management.resources.fluentcore.model.implementation.CreateUpdateTask;
import org.junit.Assert;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link IPizza}
 */
class PizzaImpl
        extends CreatableUpdatableImpl<IPizza, PizzaInner, PizzaImpl>
        implements IPizza {
    final List<Creatable<IPizza>> delayedPizzas;
    boolean prepareCalled = false;

    public PizzaImpl(String name) {
        super(name, name, new PizzaInner());
        delayedPizzas = new ArrayList<>();
    }

    /**
     * a pizza specified via this wither will be added immediately as dependency.
     *
     * @param pizza the pizza
     * @return the next stage of pizza
     */
    @Override
    public PizzaImpl withInstantPizza(Creatable<IPizza> pizza) {
        this.addDependency(pizza);
        return this;
    }

    /**
     * a pizza specified via this wither will not be added immediately as a dependency, will be added only
     * inside beforeGroupCreateOrUpdate {@link CreateUpdateTask.ResourceCreatorUpdater#beforeGroupCreateOrUpdate()}
     *
     * @param pizza the pizza
     * @return the next stage of pizza
     */
    @Override
    public PizzaImpl withDelayedPizza(Creatable<IPizza> pizza) {
        this.delayedPizzas.add(pizza);
        return this;
    }

    @Override
    public void beforeGroupCreateOrUpdate() {
        Assert.assertFalse("PizzaImpl::beforeGroupCreateOrUpdate() should not be called multiple times", this.prepareCalled);
        prepareCalled = true;
        int oldCount = this.getTaskGroup().getNode(this.getKey()).dependencyKeys().size();
        for (Creatable<IPizza> pizza : this.delayedPizzas) {
            this.addDependency(pizza);
        }
        int newCount = this.getTaskGroup().getNode(this.getKey()).dependencyKeys().size();
        System.out.println("Pizza(" + this.getName() + ")::beforeGroupCreateOrUpdate() 'delayedSize':" + this.delayedPizzas.size()
                + " 'dependency count [old, new]': [" + oldCount + "," + newCount + "]");
    }

    @Override
    public Mono<IPizza> createResourceAsync() {
        System.out.println("Pizza(" + this.getName() + ")::createResourceAsync()");
        return Mono.just(this)
                .delayElement(Duration.ofMillis(250))
                .map(pizza -> pizza);
    }

    @Override
    public boolean isInCreateMode() {
        return true;
    }

    @Override
    protected Mono<PizzaInner> getInnerAsync() {
        return Mono.just(this.getInner());
    }
}
