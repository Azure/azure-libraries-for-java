/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.v2.management.sql.SqlSyncFullSchemaProperty;
import com.microsoft.azure.v2.management.sql.SyncFullSchemaTable;
import java.time.OffsetDateTime;

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
    public OffsetDateTime lastUpdateTime() {
        return this.inner().lastUpdateTime();
    }
}
