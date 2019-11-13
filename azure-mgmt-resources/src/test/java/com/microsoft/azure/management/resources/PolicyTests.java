/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.CloudException;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.fail;

public class PolicyTests extends TestBase {
    protected static ResourceManager resourceManager;
    private String policyRule = "{\"if\":{\"not\":{\"field\":\"location\",\"in\":[\"northeurope\",\"westeurope\"]}},\"then\":{\"effect\":\"deny\"}}";

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) {
        resourceManager = ResourceManager
                .authenticate(restClient)
                .withSubscription(defaultSubscription);
    }

    @Override
    protected void cleanUpResources() {

    }

    @Test
    public void canCRUDPolicyDefinition() throws Exception {
        // Create
        PolicyDefinition definition = resourceManager.policyDefinitions().define("policy1")
                .withPolicyRuleJson(policyRule)
                .withPolicyType(PolicyType.CUSTOM)
                .withDisplayName("My Policy")
                .withDescription("This is my policy")
                .create();
        Assert.assertEquals("policy1", definition.name());
        Assert.assertEquals(PolicyType.CUSTOM, definition.policyType());
        Assert.assertEquals("My Policy", definition.displayName());
        Assert.assertEquals("This is my policy", definition.description());
        // List
        List<PolicyDefinition> definitions = resourceManager.policyDefinitions().list();
        boolean found = false;
        for (PolicyDefinition def : definitions) {
            if (definition.id().equalsIgnoreCase(def.id())) {
                found = true;
            }
        }
        Assert.assertTrue(found);
        // Get
        definition = resourceManager.policyDefinitions().getByName("policy1");
        Assert.assertNotNull(definition);
        Assert.assertEquals("My Policy", definition.displayName());
        // Delete
        resourceManager.policyDefinitions().deleteById(definition.id());
    }

    @Test
    public void canCRUDPolicyAssignment() throws Exception {
        // Create definition
        PolicyDefinition definition = resourceManager.policyDefinitions().define("policy1")
                .withPolicyRuleJson(policyRule)
                .withPolicyType(PolicyType.CUSTOM)
                .withDisplayName("My Policy")
                .withDescription("This is my policy")
                .create();
        // Create assignment
        ResourceGroup group = resourceManager.resourceGroups().define("rgassignment115095")
                .withRegion(Region.UK_WEST)
                .create();
        PolicyAssignment assignment = resourceManager.policyAssignments().define("assignment1")
                .forResourceGroup(group)
                .withPolicyDefinition(definition)
                .withDisplayName("My Assignment")
                .create();
        // Verify
        Assert.assertNotNull(assignment);
        Assert.assertEquals("My Assignment", assignment.displayName());
        // Delete
        resourceManager.resourceGroups().define(group.name());
        resourceManager.policyAssignments().deleteById(assignment.id());
        resourceManager.policyDefinitions().deleteById(definition.id());
    }
}
