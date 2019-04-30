/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.cosmosdb;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.cosmosdb.implementation.DatabaseAccountListKeysResultInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * An immutable client-side representation of an Azure Cosmos DB DatabaseAccountListKeysResult.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_8_0)
public interface DatabaseAccountListKeysResult extends HasInner<DatabaseAccountListKeysResultInner> {
    /**
     * @return Base 64 encoded value of the primary read-write key.
     */
    String primaryMasterKey();

    /**
     * @return Base 64 encoded value of the secondary read-write key.
     */
    String secondaryMasterKey();

    /**
     * @return Base 64 encoded value of the primary read-only key.
     */
    String primaryReadonlyMasterKey();

    /**
     * @return Base 64 encoded value of the secondary read-only key.
     */
    String secondaryReadonlyMasterKey();
}
