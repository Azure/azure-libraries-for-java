/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.management.sql.SqlSyncFullSchemaProperty;
import com.microsoft.azure.management.sql.SyncFullSchemaTable;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation for SqlSyncGroup.
 */
@LangDefinition
public class SqlSyncFullSchemaPropertyImpl
    extends
        WrapperImpl<SyncFullSchemaPropertiesInner>
    implements
        SqlSyncFullSchemaProperty {

    protected SqlSyncFullSchemaPropertyImpl(SyncFullSchemaPropertiesInner innerObject) {
        super(innerObject);
    }

    @Override
    public List<SyncFullSchemaTable> tables() {
        return Collections.unmodifiableList(this.inner().tables() != null ? this.inner().tables() : new ArrayList<SyncFullSchemaTable>());
    }

    @Override
    public DateTime lastUpdateTime() {
        return this.inner().lastUpdateTime();
    }
}
