/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.management.sql.SqlActiveDirectoryAdministrator;

/**
 * Response containing the SQL Active Directory administrator.
 */
 @LangDefinition
public class SqlActiveDirectoryAdministratorImpl extends WrapperImpl<ServerAzureADAdministratorInner>
    implements SqlActiveDirectoryAdministrator {

    protected SqlActiveDirectoryAdministratorImpl(ServerAzureADAdministratorInner innerObject) {
        super(innerObject);
    }

    @Override
    public String administratorType() {
        return this.inner().administratorType();
    }

    @Override
    public String signInName() {
        return this.inner().login();
    }

    @Override
    public String id() {
        return this.inner().sid().toString();
    }

    @Override
    public String tenantId() {
        return this.inner().tenantId().toString();
    }
}
