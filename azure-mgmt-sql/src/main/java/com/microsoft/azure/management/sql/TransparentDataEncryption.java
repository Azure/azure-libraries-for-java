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
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.sql.implementation.TransparentDataEncryptionInner;
import rx.Observable;

import java.util.List;


/**
 * An immutable client-side representation of an Azure SQL database's TransparentDataEncryption.
 */
@Beta(Beta.SinceVersion.V1_7_0)
@Fluent
public interface TransparentDataEncryption extends
        Refreshable<TransparentDataEncryption>,
        HasInner<TransparentDataEncryptionInner>,
        HasResourceGroup,
        HasName,
        HasId {
    /**
     * @return name of the SQL Server to which this replication belongs
     */
    String sqlServerName();

    /**
     * @return name of the SQL Database to which this replication belongs
     */
    String databaseName();

    /**
     * @return the status of the Azure SQL Database Transparent Data Encryption
     */
    TransparentDataEncryptionStates status();

    /**
     * Updates the state of the transparent data encryption status.
     *
     * @param transparentDataEncryptionState state of the data encryption to set
     * @return the new encryption settings after the update operation
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    TransparentDataEncryption updateStatus(TransparentDataEncryptionStates transparentDataEncryptionState);

    /**
     * Updates the state of the transparent data encryption status.
     *
     * @param transparentDataEncryptionState state of the data encryption to set
     * @return a representation of the deferred computation of the new encryption settings after the update operation
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    Observable<TransparentDataEncryption> updateStatusAsync(TransparentDataEncryptionStates transparentDataEncryptionState);

    /**
     * @return an Azure SQL Database Transparent Data Encryption Activities
     */
    @Method
    List<TransparentDataEncryptionActivity> listActivities();

    /**
     * @return an Azure SQL Database Transparent Data Encryption Activities
     */
    @Beta(Beta.SinceVersion.V1_7_0)
    @Method
    Observable<TransparentDataEncryptionActivity> listActivitiesAsync();
}

