/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.sql;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;

/**
 * The reserved eDTUs value range for a "Basic" edition of an Azure SQL Elastic Pool.
 */
@Fluent
@Beta(since = "V1_7_0")
public enum SqlElasticPoolBasicEDTUs {
    /** 50 eDTUs available to the pool. */
    eDTU_50(50),

    /** 100 eDTUs available to the pool. */
    eDTU_100(100),

    /** 200 eDTUs available to the pool. */
    eDTU_200(200),

    /** 400 eDTUs available to the pool. */
    eDTU_400(400),

    /** 800 eDTUs available to the pool. */
    eDTU_800(800),

    /** 1200 eDTUs available to the pool. */
    eDTU_1200(1200),

    /** 1600 eDTUs available to the pool. */
    eDTU_1600(1600);

    /** The reserved eDTU for the SQL Elastic Pool. */
    private int value;

    SqlElasticPoolBasicEDTUs(int eDTU) {
        this.value = eDTU;
    }

    /**
     * @return the reserved eDTU for the SQL Elastic Pool
     */
    public int value() {
        return this.value;
    }
}
