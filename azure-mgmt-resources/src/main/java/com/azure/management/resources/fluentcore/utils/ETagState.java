/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.utils;

public class ETagState {
    private boolean doImplicitETagCheckOnCreate;
    private boolean doImplicitETagCheckOnUpdate;
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
        this.eTagOnUpdate = null;
        this.eTagOnDelete = null;
        return this;
    }

    public String ifMatchValueOnUpdate(String currentETagValue) {
        String eTagValue = null;
        if (this.doImplicitETagCheckOnUpdate) {
            eTagValue = currentETagValue;
        }
        if (this.eTagOnUpdate != null) {
            eTagValue = this.eTagOnUpdate;
        }
        return eTagValue;
    }

    public String ifMatchValueOnDelete() {
        return this.eTagOnDelete;
    }

    public String ifNonMatchValueOnCreate() {
        if (this.doImplicitETagCheckOnCreate) {
            return "*";
        }
        return null;
    }
}
