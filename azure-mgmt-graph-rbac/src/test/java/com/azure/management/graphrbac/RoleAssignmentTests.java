/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac;

import com.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.AfterClass;
import org.junit.jupiter.api.Assertions;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RoleAssignmentTests extends GraphRbacManagementTest {
    @Test
    public void canCRUDRoleAssignment() throws Exception {
        String roleAssignmentName = SdkContext.randomUuid();
        String spName = SdkContext.randomResourceName("sp", 20);

        ServicePrincipal sp = graphRbacManager.servicePrincipals().define(spName)
                .withNewApplication("http://" + spName)
                .create();

        SdkContext.sleep(15000);

        RoleAssignment roleAssignment = graphRbacManager.roleAssignments()
                .define(roleAssignmentName)
                .forServicePrincipal(sp)
                .withBuiltInRole(BuiltInRole.CONTRIBUTOR)
                .withSubscriptionScope(resourceManager.getSubscriptionId())
                .create();

        Assertions.assertNotNull(roleAssignment);
    }

}
