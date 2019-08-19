package com.microsoft.azure.management.cosmosdb;

import com.microsoft.azure.management.cosmosdb.implementation.SqlDatabaseInner;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

public class SqlDatabaseImpl
        extends WrapperImpl<SqlDatabaseInner>
        implements SqlDatabase {

    public SqlDatabaseImpl(SqlDatabaseInner innerObject) {
        super(innerObject);
    }

    public String sqlDatabaseId() {
        return this.inner().sqlDatabaseId();
    }

    public String _rid() {
        return this.inner()._rid();
    }

    public Object _ts() {
        return this.inner()._ts();
    }

    public String _etag() {
        return this.inner()._etag();
    }

    public String _colls() {
        return this.inner()._colls();
    }

    public String _users() {
        return this.inner()._users();
    }
}
