/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.cosmosdb.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.cosmosdb.SqlDatabase;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

/**
 * An Azure Cosmos DB SQL database.
 */
@LangDefinition
public class SqlDatabaseImpl
        extends WrapperImpl<SqlDatabaseInner>
        implements SqlDatabase {

    SqlDatabaseImpl(SqlDatabaseInner innerObject) {
        super(innerObject);
    }

    @Override
    public String sqlDatabaseId() {
        return this.inner().sqlDatabaseId();
    }

    @Override
    public String _rid() {
        return this.inner()._rid();
    }

    @Override
    public Object _ts() {
        return this.inner()._ts();
    }

    @Override
    public String _etag() {
        return this.inner()._etag();
    }

    @Override
    public String _colls() {
        return this.inner()._colls();
    }

    @Override
    public String _users() {
        return this.inner()._users();
    }
}
