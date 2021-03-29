/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.implementation;

import com.microsoft.azure.management.resources.DeploymentProperties;
import com.microsoft.azure.management.resources.Tags;
import com.microsoft.azure.management.resources.TagsPatchOperation;
import com.microsoft.azure.management.resources.TagsPatchResource;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class TypeSerializationTests {

    public static final class Map1<K,V> extends AbstractMap<K,V> {
        private final K k0;
        private final V v0;

        public Map1(K k0, V v0) {
            this.k0 = Objects.requireNonNull(k0);
            this.v0 = Objects.requireNonNull(v0);
        }

        @Override
        public Set<Entry<K,V>> entrySet() {
            Entry<K,V> entry = new AbstractMap.SimpleEntry<>(k0, v0);
            return new HashSet<>(Collections.singletonList(entry));
        }

        @Override
        public V get(Object o) {
            return o.equals(k0) ? v0 : null;
        }

        @Override
        public boolean containsKey(Object o) {
            return o.equals(k0);
        }

        @Override
        public boolean containsValue(Object o) {
            return o.equals(v0); // implicit nullcheck of o
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int hashCode() {
            return k0.hashCode() ^ v0.hashCode();
        }
    }

    @Test
    @Ignore("fails if Map is final")
    public void testTagsPatchResourceSerialization() throws Exception {
        Map<String, String> tags = new Map1<>("tag.1", "value.1");

        TagsPatchResource tagsPatchResource = new TagsPatchResource()
                .withOperation(TagsPatchOperation.REPLACE)
                .withProperties(new Tags().withTags(tags));

        AzureJacksonAdapter serializerAdapter = new AzureJacksonAdapter();
        String tagsPatchResourceJson = serializerAdapter.serialize(tagsPatchResource);
        Assert.assertTrue(tagsPatchResourceJson.contains("tag.1"));
    }

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
