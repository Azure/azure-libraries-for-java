/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.keyvault.models.Attributes;
import com.microsoft.azure.keyvault.models.SecretAttributes;
import com.microsoft.azure.keyvault.models.SecretBundle;
import com.microsoft.azure.keyvault.requests.SetSecretRequest;
import com.microsoft.azure.keyvault.requests.UpdateSecretRequest;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyOperation;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyType;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.keyvault.Secret;
import com.microsoft.azure.management.keyvault.Vault;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.CreatableUpdatableImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;

import java.util.List;
import java.util.Map;

/**
 * Implementation for Vault and its parent interfaces.
 */
@LangDefinition
class SecretImpl
        extends CreatableUpdatableImpl<
                Secret,
                SecretBundle,
                SecretImpl>
        implements
        Secret,
        Secret.Definition,
        Secret.Update {

    private final Vault vault;
    private SetSecretRequest.Builder setSecretRequest;
    private UpdateSecretRequest.Builder updateSecretRequest;

    SecretImpl(String name, SecretBundle innerObject, Vault vault) {
        super(name, innerObject);
        this.vault = vault;
        this.updateSecretRequest = new UpdateSecretRequest.Builder(vault.vaultUri(), name);
    }

    @Override
    public String id() {
        return inner().id();
    }

    @Override
    public String value() {
        return inner().value();
    }

    @Override
    public SecretAttributes attributes() {
        return inner().attributes();
    }

    @Override
    public Map<String, String> tags() {
        return inner().tags();
    }

    @Override
    public String contentType() {
        return inner().contentType();
    }

    @Override
    public String kid() {
        return inner().kid();
    }

    @Override
    public boolean managed() {
        return Utils.toPrimitiveBoolean(inner().managed());
    }

    @Override
    protected Observable<SecretBundle> getInnerAsync() {
        return Observable.from(vault.client().getSecretAsync(id(), null));
    }

    @Override
    public SecretImpl withTags(Map<String, String> tags) {
        setSecretRequest.withTags(tags);
        updateSecretRequest.withTags(tags);
        return this;
    }

    @Override
    public boolean isInCreateMode() {
        return id() == null;
    }

    @Override
    public Observable<Secret> createResourceAsync() {
        return Observable.from(vault.client().setSecretAsync(setSecretRequest.build(), null))
                .map(innerToFluentMap(this))
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        setSecretRequest = null;
                        updateSecretRequest = new UpdateSecretRequest.Builder(vault.vaultUri(), name());
                    }
                });
    }

    @Override
    public Observable<Secret> updateResourceAsync() {
        Observable<Secret> set = Observable.just((Secret) this);
        if (setSecretRequest != null) {
            set = createResourceAsync();
        }
        return set.flatMap(new Func1<Secret, Observable<SecretBundle>>() {
            @Override
            public Observable<SecretBundle> call(Secret secret) {
                return Observable.from(vault.client().updateSecretAsync(updateSecretRequest.build(), null));
            }
        }).flatMap(new Func1<SecretBundle, Observable<Secret>>() {
            @Override
            public Observable<Secret> call(SecretBundle secretBundle) {
                return refreshAsync();
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                setSecretRequest = null;
                updateSecretRequest = new UpdateSecretRequest.Builder(vault.vaultUri(), name());
            }
        });
    }

    @Override
    public SecretImpl withAttributes(Attributes attributes) {
        setSecretRequest.withAttributes(attributes);
        updateSecretRequest.withAttributes(attributes);
        return this;
    }

    @Override
    public SecretImpl withVersion(String version) {
        updateSecretRequest.withVersion(version);
        return this;
    }

    @Override
    public SecretImpl withValue(String value) {
        setSecretRequest = new SetSecretRequest.Builder(vault.vaultUri(), name(), value);
        return this;
    }

    @Override
    public SecretImpl withContentType(String contentType) {
        setSecretRequest.withContentType(contentType);
        updateSecretRequest.withContentType(contentType);
        return this;
    }
}
