/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.childresource;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class ChickenImpl {
    private PulletsImpl pullets;

    ChickenImpl() {
        this.pullets = new PulletsImpl(this);
        this.pullets.enableCommitMode();
    }

    PulletsImpl pullets() {
        return this.pullets;
    }

    ChickenImpl withPullet(PulletImpl pullet) {
        this.pullets.addPullet(pullet);
        return this;
    }

    PulletImpl defineNewPullet(String name) {
        return this.pullets.define(name);
    }

    PulletImpl updatePullet(String name) {
        return this.pullets.update(name);
    }

    ChickenImpl withoutPullet(String name) {
        this.pullets.remove(name);
        return this;
    }

    Flux<ChickenImpl> applyAsync() {
        final ChickenImpl self = this;
        return this.pullets.commitAsync()
                .flatMap(p -> Flux.just(self));
//                .map(new Func1<PulletImpl, ChickenImpl>() {
//                    public ChickenImpl call(PulletImpl p) {
//                        return self;
//                    }
//                });
    }
}
