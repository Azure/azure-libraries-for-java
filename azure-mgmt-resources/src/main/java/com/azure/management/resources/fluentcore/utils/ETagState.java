/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.utils;

public class ETagState {
    private boolean doImplicitETagCheckOnCreate;
    private boolean doImplicitETagCheckOnUpdate;
    private String eTagOnCreate;
    private String eTagOnUpdate;
    private String eTagOnDelete;

    public ETagState withImplicitETagCheckOnCreate() {
        this.doImplicitETagCheckOnCreate = true;
        return this;
    }

    public ETagState withImplicitETagCheckOnUpdate() {
        this.doImplicitETagCheckOnUpdate = true;
        return this;
    }

    public ETagState withExplicitETagCheckOnCreate(String eTagValue) {
        this.eTagOnCreate = eTagValue;
        return this;
    }

    public ETagState withExplicitETagCheckOnUpdate(String eTagValue) {
        this.eTagOnUpdate = eTagValue;
        return this;
    }

    public ETagState withExplicitETagCheckOnDelete(String eTagValue) {
        this.eTagOnDelete = eTagValue;
        return this;
    }


    public ETagState clear() {
        this.doImplicitETagCheckOnCreate = false;
        this.doImplicitETagCheckOnUpdate = false;
        this.eTagOnCreate = null;
        this.eTagOnUpdate = null;
        this.eTagOnDelete = null;
        return this;
    }

    public String eTagOnUpdate(String currentETagValue) {
        if (this.doImplicitETagCheckOnUpdate) {
            return currentETagValue;
        }
        return this.eTagOnUpdate;
    }

    public String eTagOnDelete() {
        return this.eTagOnDelete;
    }

    public String eTagOnCreate() {
        if (this.doImplicitETagCheckOnCreate) {
            return "*";
        }
        return this.eTagOnCreate;
    }
}
