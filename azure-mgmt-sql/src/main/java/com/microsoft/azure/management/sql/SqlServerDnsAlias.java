/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasResourceGroup;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.sql.implementation.ServerDnsAliasInner;
import rx.Completable;

/**
 * An immutable client-side representation of an Azure SQL Server DNS alias.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_8_0)
public interface SqlServerDnsAlias
    extends
        HasId,
        HasInner<ServerDnsAliasInner>,
        HasName,
        HasResourceGroup,
        Indexable,
        Refreshable<SqlServerDnsAlias> {
    /**
     * @return name of the SQL Server to which this DNS alias belongs
     */
    String sqlServerName();

    /**
     * @return the fully qualified DNS record for alias
     */
    String azureDnsRecord();

    /**
     * @return the parent SQL server ID
     */
    String parentId();

    /**
     * Deletes the DNS alias.
     */
    @Method
    void delete();

    /**
     * Deletes the DNS alias asynchronously.
     *
     * @return a representation of the deferred computation of this call
     */
    @Method
    Completable deleteAsync();

}
