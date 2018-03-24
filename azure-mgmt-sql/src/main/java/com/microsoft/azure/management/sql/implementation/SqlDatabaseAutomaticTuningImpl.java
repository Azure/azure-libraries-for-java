/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.RefreshableWrapperImpl;
import com.microsoft.azure.management.sql.AutomaticTuningMode;
import com.microsoft.azure.management.sql.AutomaticTuningOptionModeDesired;
import com.microsoft.azure.management.sql.AutomaticTuningOptions;
import com.microsoft.azure.management.sql.SqlDatabaseAutomaticTuning;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import rx.Observable;
import rx.functions.Func1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementation for Azure SQL Database automatic tuning.
 */
@LangDefinition
public class SqlDatabaseAutomaticTuningImpl
    extends RefreshableWrapperImpl<DatabaseAutomaticTuningInner, SqlDatabaseAutomaticTuning>
    implements
        SqlDatabaseAutomaticTuning,
        SqlDatabaseAutomaticTuning.Update {

    protected String key;
    private SqlServerManager sqlServerManager;
    private String resourceGroupName;
    private String sqlServerName;
    private String sqlDatabaseName;

    private Map<String, AutomaticTuningOptions> automaticTuningOptionsMap;

    SqlDatabaseAutomaticTuningImpl(SqlDatabaseImpl database, DatabaseAutomaticTuningInner innerObject) {
        this(database.resourceGroupName, database.sqlServerName, database.name(), innerObject, database.sqlServerManager);
    }

    SqlDatabaseAutomaticTuningImpl(String resourceGroupName, String sqlServerName, String sqlDatabaseName, DatabaseAutomaticTuningInner innerObject, SqlServerManager sqlServerManager) {
        super(innerObject);
        Objects.requireNonNull(innerObject);
        Objects.requireNonNull(sqlServerManager);
        this.sqlServerManager = sqlServerManager;
        this.resourceGroupName = resourceGroupName;
        this.sqlServerName = sqlServerName;
        this.sqlDatabaseName = sqlDatabaseName;

        this.key = UUID.randomUUID().toString();
        this.automaticTuningOptionsMap = new HashMap<String, AutomaticTuningOptions>();
    }

    @Override
    public AutomaticTuningMode desiredState() {
        return this.inner().desiredState();
    }

    @Override
    public AutomaticTuningMode actualState() {
        return this.inner().actualState();
    }

    @Override
    public Map<String, AutomaticTuningOptions> tuningOptions() {
        return Collections.unmodifiableMap(this.inner().options() != null ? this.inner().options() : new HashMap<String, AutomaticTuningOptions>());
    }

    @Override
    public SqlDatabaseAutomaticTuningImpl withAutomaticTuningMode(AutomaticTuningMode desiredState) {
        this.inner().withDesiredState(desiredState);
        return this;
    }

    @Override
    public SqlDatabaseAutomaticTuningImpl withAutomaticTuningOption(String tuningOptionName, AutomaticTuningOptionModeDesired desiredState) {
        if (this.automaticTuningOptionsMap == null) {
            this.automaticTuningOptionsMap = new HashMap<String, AutomaticTuningOptions>();
        }
        this.automaticTuningOptionsMap
            .put(tuningOptionName, new AutomaticTuningOptions().withDesiredState(desiredState));
        return this;
    }

    @Override
    public SqlDatabaseAutomaticTuningImpl withAutomaticTuningOptions(Map<String, AutomaticTuningOptionModeDesired> tuningOptions) {
        if (tuningOptions != null) {
            for (Map.Entry<String, AutomaticTuningOptionModeDesired> option : tuningOptions.entrySet()) {
                this.withAutomaticTuningOption(option.getKey(), option.getValue());
            }
        }
        return this;
    }

    @Override
    public SqlDatabaseAutomaticTuningImpl update() {
        return this;
    }

    @Override
    protected Observable<DatabaseAutomaticTuningInner> getInnerAsync() {
        return this.sqlServerManager.inner().databaseAutomaticTunings()
            .getAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName);
    }

    @Override
    public SqlDatabaseAutomaticTuning apply() {
        return this.applyAsync().toBlocking().last();
    }

    @Override
    public Observable<SqlDatabaseAutomaticTuning> applyAsync() {
        final SqlDatabaseAutomaticTuningImpl self = this;
        this.inner().withOptions(this.automaticTuningOptionsMap);
        return this.sqlServerManager.inner().databaseAutomaticTunings()
            .updateAsync(this.resourceGroupName, this.sqlServerName, this.sqlDatabaseName, this.inner())
            .map(new Func1<DatabaseAutomaticTuningInner, SqlDatabaseAutomaticTuning>() {
                @Override
                public SqlDatabaseAutomaticTuning call(DatabaseAutomaticTuningInner databaseAutomaticTuningInner) {
                    self.setInner(databaseAutomaticTuningInner);
                    self.automaticTuningOptionsMap.clear();
                    return self;
                }
            });
    }

    @Override
    public ServiceFuture<SqlDatabaseAutomaticTuning> applyAsync(ServiceCallback<SqlDatabaseAutomaticTuning> callback) {
        return ServiceFuture.fromBody(applyAsync(), callback);
    }

    @Override
    public String key() {
        return this.key;
    }
}
