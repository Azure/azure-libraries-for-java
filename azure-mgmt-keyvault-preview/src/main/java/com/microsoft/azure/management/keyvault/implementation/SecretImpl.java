/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.management.keyvault.Secret;
import com.microsoft.azure.management.keyvault.SecretPatchProperties;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import java.util.Arrays;
import java.util.Iterator;
import rx.Observable;
import com.microsoft.azure.management.keyvault.SecretProperties;
import java.util.Map;

class SecretImpl extends CreatableUpdatableImpl<Secret, SecretInner, SecretImpl> implements Secret, Secret.Definition, Secret.Update {
    private final KeyVaultManager manager;
    private String resourceGroupName;
    private String vaultName;
    private String secretName;
    private SecretCreateOrUpdateParametersInner createParameter;
    private SecretPatchParametersInner updateParameter;
    SecretImpl(String name, KeyVaultManager manager) {
        super(name, new SecretInner());
        this.manager = manager;
        this.secretName = name;
        this.createParameter = new SecretCreateOrUpdateParametersInner();
        this.updateParameter = new SecretPatchParametersInner();
    }
    SecretImpl(String name, SecretInner inner,  KeyVaultManager manager) {
        super(name, inner);
        this.manager = manager;
        this.resourceGroupName = GetValueFromIdByName(inner.id(), "resourceGroups");
        this.vaultName = GetValueFromIdByName(inner.id(), "vaults");
        this.secretName = GetValueFromIdByName(inner.id(), "secrets");
    }

    @Override
    public KeyVaultManager manager() {
        return this.manager;
    }

    @Override
    public Observable<Secret> createResourceAsync() {
        SecretsInner client = this.manager.inner().secrets();
        return client.createOrUpdateAsync(this.resourceGroupName, this.vaultName, this.secretName, this.createParameter)
            .map(innerToFluentMap(this));
    }
    @Override
    public Observable<Secret> updateResourceAsync() {
        SecretsInner client = this.manager.inner().secrets();
        return client.updateAsync(this.resourceGroupName, this.vaultName, this.secretName, this.updateParameter)
            .map(innerToFluentMap(this));
    }
    @Override
    protected Observable<SecretInner> getInnerAsync() {
        SecretsInner client = this.manager.inner().secrets();
        return client.getAsync(this.resourceGroupName, this.vaultName, this.secretName);
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }


    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public String location() {
        return this.inner().location();
    }

    @Override
    public String name() {
        return this.inner().name();
    }

    @Override
    public SecretProperties properties() {
        return this.inner().properties();
    }

    @Override
    public Map<String, String> tags() {
        return this.inner().getTags();
    }

    @Override
    public String type() {
        return this.inner().type();
    }


    @Override
    public SecretImpl withExistingVault(String resourceGroupName, String vaultName) {
        this.resourceGroupName = resourceGroupName;
        this.vaultName = vaultName;
        return this;
    }

    @Override
    public SecretImpl withProperties(SecretProperties properties) {
        this.createParameter.withProperties(properties);
        return this;
    }

    @Override
    public SecretImpl withProperties(SecretPatchProperties properties) {
        this.updateParameter.withProperties(properties);
        return this;
    }

    @Override
    public SecretImpl withTags(Map<String, String> tags) {
        if (isInCreateMode()) {
            this.createParameter.withTags(tags);
        } else {
            this.updateParameter.withTags(tags);
        }
        return this;
    }

    private static String GetValueFromIdByName(String id, String name) {
        if (id == null) {
            return null;
        }
        Iterable<String> iterable = Arrays.asList(id.split("/"));
        Iterator <String> itr = iterable.iterator();
        while (itr.hasNext()) {
            String part = itr.next();
            if (part != null && part.trim() != "") {
                if (part.equalsIgnoreCase(name)) {
                    if (itr.hasNext()) {
                        return itr.next();
                    } else {
                        return null;
                    }
                }
            }
        }
        return null;
    }
    private static String GetValueFromIdByPosition(String id, int pos) {
        if (id == null) {
            return null;
        }
        Iterable<String> iterable = Arrays.asList(id.split("/"));
        Iterator <String> itr = iterable.iterator();
        int index = 0;
        while (itr.hasNext()) {
            String part = itr.next();
            if (part != null && part.trim() != "") {
                if (index == pos) {
                    if (itr.hasNext()) {
                        return itr.next();
                    } else {
                        return null;
                    }
                }
            }
            index++;
        }
        return null;
    }
}
