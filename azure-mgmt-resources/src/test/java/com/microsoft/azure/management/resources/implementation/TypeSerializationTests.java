/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.microsoft.azure.management.resources.DeploymentProperties;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Field;

public class TypeSerializationTests {

    @Test
    public void testDeploymentSerialization() throws Exception {
        final String templateJson = "{ \"/subscriptions/<redacted>/resourceGroups/<redacted>/providers/Microsoft.ManagedIdentity/userAssignedIdentities/<redacted>\": {} }";

        DeploymentImpl deployment = new DeploymentImpl(new DeploymentExtendedInner(), "", null);
        deployment.withTemplate(templateJson);

        AzureJacksonAdapter serializerAdapter = new AzureJacksonAdapter();
        String deploymentJson = serializerAdapter.serialize(createRequestFromInner(deployment));
        Assert.assertTrue(deploymentJson.contains("Microsoft.ManagedIdentity"));
    }

    private static DeploymentInner createRequestFromInner(DeploymentImpl deployment) throws NoSuchFieldException, IllegalAccessException {
        Field field = DeploymentImpl.class.getDeclaredField("deploymentCreateUpdateParameters");
        field.setAccessible(true);
        DeploymentInner implInner = (DeploymentInner) field.get(deployment);

        DeploymentInner inner = new DeploymentInner()
                .withProperties(new DeploymentProperties());
        inner.properties().withMode(deployment.mode());
        inner.properties().withTemplate(implInner.properties().template());
        inner.properties().withTemplateLink(deployment.templateLink());
        inner.properties().withParameters(deployment.parameters());
        inner.properties().withParametersLink(deployment.parametersLink());
        return inner;
    }
}
