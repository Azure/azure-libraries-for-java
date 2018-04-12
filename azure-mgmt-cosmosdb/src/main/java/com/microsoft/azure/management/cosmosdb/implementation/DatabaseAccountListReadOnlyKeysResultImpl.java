/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.cosmosdb.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.cosmosdb.DatabaseAccountListReadOnlyKeysResult;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

/**
 * The implementation for DatabaseAccountListReadOnlyKeysResult.
 */
@LangDefinition
public class DatabaseAccountListReadOnlyKeysResultImpl extends WrapperImpl<DatabaseAccountListReadOnlyKeysResultInner>
    implements DatabaseAccountListReadOnlyKeysResult {
    DatabaseAccountListReadOnlyKeysResultImpl(DatabaseAccountListReadOnlyKeysResultInner innerObject) {
        super(innerObject);
    }

    @Override
    public String primaryReadonlyMasterKey() {
        return this.inner().primaryReadonlyMasterKey();
    }

    @Override
    public String secondaryReadonlyMasterKey() {
        return this.inner().secondaryReadonlyMasterKey();
    }
}
