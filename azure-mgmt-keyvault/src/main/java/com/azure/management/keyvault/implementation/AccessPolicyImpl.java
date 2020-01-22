/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.keyvault.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;
import com.azure.management.graphrbac.ActiveDirectoryGroup;
import com.azure.management.graphrbac.ActiveDirectoryUser;
import com.azure.management.graphrbac.ServicePrincipal;
import com.azure.management.keyvault.AccessPolicy;
import com.azure.management.keyvault.AccessPolicyEntry;
import com.azure.management.keyvault.CertificatePermissions;
import com.azure.management.keyvault.KeyPermissions;
import com.azure.management.keyvault.Permissions;
import com.azure.management.keyvault.SecretPermissions;
import com.azure.management.keyvault.StoragePermissions;
import com.azure.management.keyvault.Vault;

/**
 * Implementation for AccessPolicy and its parent interfaces.
 */
class AccessPolicyImpl extends ChildResourceImpl<AccessPolicyEntry, VaultImpl, Vault>
        implements AccessPolicy, AccessPolicy.Definition<Vault.DefinitionStages.WithCreate>,
        AccessPolicy.UpdateDefinition<Vault.Update>, AccessPolicy.Update {
    private String userPrincipalName;
    private String servicePrincipalName;

    AccessPolicyImpl(AccessPolicyEntry innerObject, VaultImpl parent) {
        super(innerObject, parent);
        getInner().setTenantId(UUID.fromString(parent.tenantId()));
    }

    String userPrincipalName() {
        return userPrincipalName;
    }

    String servicePrincipalName() {
        return servicePrincipalName;
    }

    @Override
    public String tenantId() {
        if (getInner().getTenantId() == null) {
            return null;
        }
        return getInner().getTenantId().toString();
    }

    @Override
    public String objectId() {
        if (getInner().getObjectId() == null) {
            return null;
        }
        return getInner().getObjectId();
    }

    @Override
    public String applicationId() {
        if (getInner().getApplicationId() == null) {
            return null;
        }
        return getInner().getApplicationId().toString();
    }

    @Override
    public Permissions permissions() {
        return getInner().getPermissions();
    }

    @Override
    public String getName() {
        return getInner().getObjectId();
    }

    private void initializeKeyPermissions() {
        if (getInner().getPermissions() == null) {
            getInner().setPermissions(new Permissions());
        }
        if (getInner().getPermissions().getKeys() == null) {
            getInner().getPermissions().setKeys(new ArrayList<KeyPermissions>());
        }
    }

    private void initializeSecretPermissions() {
        if (getInner().getPermissions() == null) {
            getInner().setPermissions(new Permissions());
        }
        if (getInner().getPermissions().getSecrets() == null) {
            getInner().getPermissions().setSecrets(new ArrayList<SecretPermissions>());
        }
    }

    private void initializeCertificatePermissions() {
        if (getInner().getPermissions() == null) {
            getInner().setPermissions(new Permissions());
        }
        if (getInner().getPermissions().getCertificates() == null) {
            getInner().getPermissions().setCertificates(new ArrayList<CertificatePermissions>());
        }
    }

    private void initializeStoragePermissions() {
        if (getInner().getPermissions() == null) {
            getInner().setPermissions(new Permissions());
        }
        if (getInner().getPermissions().getStorage() == null) {
            getInner().getPermissions().setStorage(new ArrayList<StoragePermissions>());
        }
    }

    @Override
    public AccessPolicyImpl allowKeyPermissions(KeyPermissions... permissions) {
        initializeKeyPermissions();
        for (KeyPermissions permission : permissions) {
            if (!getInner().getPermissions().getKeys().contains(permission)) {
                getInner().getPermissions().getKeys().add(permission);
            }
        }
        return this;
    }

    @Override
    public AccessPolicyImpl allowKeyPermissions(List<KeyPermissions> permissions) {
        initializeKeyPermissions();
        for (KeyPermissions permission : permissions) {
            if (!getInner().getPermissions().getKeys().contains(permission)) {
                getInner().getPermissions().getKeys().add(permission);
            }
        }
        return this;
    }

    @Override
    public AccessPolicyImpl allowSecretPermissions(SecretPermissions... permissions) {
        initializeSecretPermissions();
        for (SecretPermissions permission : permissions) {
            if (!getInner().getPermissions().getSecrets().contains(permission)) {
                getInner().getPermissions().getSecrets().add(permission);
            }
        }
        return this;
    }

    @Override
    public AccessPolicyImpl allowSecretPermissions(List<SecretPermissions> permissions) {
        initializeSecretPermissions();
        for (SecretPermissions permission : permissions) {
            if (!getInner().getPermissions().getSecrets().contains(permission)) {
                getInner().getPermissions().getSecrets().add(permission);
            }
        }
        return this;
    }

    @Override
    public AccessPolicyImpl allowCertificateAllPermissions() {
        for (CertificatePermissions permission : CertificatePermissions.values()) {
            allowCertificatePermissions(permission);
        }
        return this;
    }

    @Override
    public AccessPolicyImpl allowCertificatePermissions(CertificatePermissions... permissions) {
        initializeCertificatePermissions();
        for (CertificatePermissions permission : permissions) {
            if (!getInner().getPermissions().getCertificates().contains(permission)) {
                getInner().getPermissions().getCertificates().add(permission);
            }
        }
        return this;
    }

    @Override
    public AccessPolicyImpl allowCertificatePermissions(List<CertificatePermissions> permissions) {
        initializeCertificatePermissions();
        for (CertificatePermissions permission : permissions) {
            if (!getInner().getPermissions().getCertificates().contains(permission)) {
                getInner().getPermissions().getCertificates().add(permission);
            }
        }
        return this;
    }

    @Override
    public AccessPolicyImpl allowStorageAllPermissions() {
        for (StoragePermissions permission : StoragePermissions.values()) {
            allowStoragePermissions(permission);
        }
        return this;
    }

    @Override
    public AccessPolicyImpl allowStoragePermissions(StoragePermissions... permissions) {
        initializeStoragePermissions();
        for (StoragePermissions permission : permissions) {
            if (!getInner().getPermissions().getStorage().contains(permission)) {
                getInner().getPermissions().getStorage().add(permission);
            }
        }
        return this;
    }

    @Override
    public AccessPolicyImpl allowStoragePermissions(List<StoragePermissions> permissions) {
        initializeStoragePermissions();
        for (StoragePermissions permission : permissions) {
            if (!getInner().getPermissions().getStorage().contains(permission)) {
                getInner().getPermissions().getStorage().add(permission);
            }
        }
        return this;
    }

    @Override
    public AccessPolicyImpl disallowCertificateAllPermissions() {
        initializeCertificatePermissions();
        getInner().getPermissions().getSecrets().clear();
        return this;
    }

    @Override
    public AccessPolicyImpl disallowCertificatePermissions(CertificatePermissions... permissions) {
        initializeCertificatePermissions();
        getInner().getPermissions().getCertificates().removeAll(Arrays.asList(permissions));
        return this;
    }

    @Override
    public AccessPolicyImpl disallowCertificatePermissions(List<CertificatePermissions> permissions) {
        initializeCertificatePermissions();
        getInner().getPermissions().getCertificates().removeAll(permissions);
        return this;
    }

    @Override
    public VaultImpl attach() {
        getParent().withAccessPolicy(this);
        return getParent();
    }

    @Override
    public AccessPolicyImpl forObjectId(String objectId) {
        getInner().setObjectId(objectId);
        return this;
    }

    @Override
    public AccessPolicyImpl forUser(ActiveDirectoryUser user) {
        getInner().setObjectId(user.getId());
        return this;
    }

    @Override
    public AccessPolicyImpl forUser(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
        return this;
    }

    @Override
    public AccessPolicyImpl forApplicationId(String applicationId) {
        getInner().setApplicationId(UUID.fromString(applicationId));
        return this;
    }

    @Override
    public AccessPolicyImpl forTenantId(String tenantId) {
        getInner().setTenantId(UUID.fromString(tenantId));
        return this;
    }

    @Override
    public AccessPolicyImpl forGroup(ActiveDirectoryGroup activeDirectoryGroup) {
        getInner().setObjectId(activeDirectoryGroup.getId());
        return this;
    }

    @Override
    public AccessPolicyImpl forServicePrincipal(ServicePrincipal servicePrincipal) {
        getInner().setObjectId(servicePrincipal.getId());
        return this;
    }

    @Override
    public AccessPolicyImpl forServicePrincipal(String servicePrincipalName) {
        this.servicePrincipalName = servicePrincipalName;
        return this;
    }

    @Override
    public AccessPolicyImpl allowKeyAllPermissions() {
        for (KeyPermissions permission : KeyPermissions.values()) {
            allowKeyPermissions(permission);
        }
        return this;
    }

    @Override
    public AccessPolicyImpl disallowKeyAllPermissions() {
        initializeKeyPermissions();
        getInner().getPermissions().getKeys().clear();
        return this;
    }

    @Override
    public AccessPolicyImpl disallowKeyPermissions(KeyPermissions... permissions) {
        initializeSecretPermissions();
        getInner().getPermissions().getKeys().removeAll(Arrays.asList(permissions));
        return this;
    }

    @Override
    public AccessPolicyImpl disallowKeyPermissions(List<KeyPermissions> permissions) {
        initializeSecretPermissions();
        getInner().getPermissions().getKeys().removeAll(permissions);
        return this;
    }

    @Override
    public AccessPolicyImpl allowSecretAllPermissions() {
        for (SecretPermissions permission : SecretPermissions.values()) {
            allowSecretPermissions(permission);
        }
        return this;
    }

    @Override
    public AccessPolicyImpl disallowSecretAllPermissions() {
        initializeSecretPermissions();
        getInner().getPermissions().getSecrets().clear();
        return this;
    }

    @Override
    public AccessPolicyImpl disallowSecretPermissions(SecretPermissions... permissions) {
        initializeSecretPermissions();
        getInner().getPermissions().getSecrets().removeAll(Arrays.asList(permissions));
        return this;
    }

    @Override
    public AccessPolicyImpl disallowSecretPermissions(List<SecretPermissions> permissions) {
        initializeSecretPermissions();
        getInner().getPermissions().getSecrets().removeAll(permissions);
        return this;
    }

    @Override
    public AccessPolicyImpl disallowStorageAllPermissions() {
        initializeStoragePermissions();
        getInner().getPermissions().getStorage().clear();
        return this;
    }

    @Override
    public AccessPolicyImpl disallowStoragePermissions(StoragePermissions... permissions) {
        initializeStoragePermissions();
        getInner().getPermissions().getStorage().removeAll(Arrays.asList(permissions));
        return this;
    }

    @Override
    public AccessPolicyImpl disallowStoragePermissions(List<StoragePermissions> permissions) {
        initializeStoragePermissions();
        getInner().getPermissions().getStorage().removeAll(permissions);
        return this;
    }

}
