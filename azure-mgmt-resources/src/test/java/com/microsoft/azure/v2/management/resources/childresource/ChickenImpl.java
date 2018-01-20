/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.childresource;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

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

    Observable<ChickenImpl> applyAsync() {
        final ChickenImpl self = this;
        return this.pullets.commitAsync()
                .map(new Function<PulletImpl, ChickenImpl>() {
                    public ChickenImpl apply(PulletImpl p) {
                        return self;
                    }
                });
    }
}
