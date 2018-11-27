/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;

/**
 * The maximum limit of the reserved eDTUs value range for a "Basic" edition of an Azure SQL Elastic Pool.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_7_0)
public enum SqlElasticPoolBasicMaxEDTUs {
    /** Maximum 5 eDTUs available per each database. */
    eDTU_5(5);

    /** The maximum eDTUs available per each database for the SQL Elastic Pool. */
    private int value;

    SqlElasticPoolBasicMaxEDTUs(int eDTU) {
        this.value = eDTU;
    }

    /**
     * @return the maximum eDTUs available per each database for the SQL Elastic Pool
     */
    public int value() {
        return this.value;
    }
}
