/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 */

package com.azure.management.keyvault.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.azure.management.graphrbac.implementation.GraphRbacManager;
import com.azure.management.keyvault.CheckNameAvailabilityResult;
import com.azure.management.keyvault.CreateMode;
import com.azure.management.keyvault.DeletedVault;
import com.azure.management.keyvault.Sku;
import com.azure.management.keyvault.SkuName;
import com.azure.management.keyvault.Vault;
import com.azure.management.keyvault.VaultCreateOrUpdateParameters;
import com.azure.management.keyvault.VaultProperties;
import com.azure.management.keyvault.Vaults;
import com.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;

/**
 * The implementation of Vaults and its parent interfaces.
 */
class VaultsImpl extends GroupableResourcesImpl<Vault, VaultImpl, VaultInner, VaultsInner, KeyVaultManager>
        implements Vaults {
    private final GraphRbacManager graphRbacManager;
    private final String tenantId;
    

    VaultsImpl(final KeyVaultManager keyVaultManager, final GraphRbacManager graphRbacManager, final String tenantId) {
        super(keyVaultManager.inner().vaults(), keyVaultManager);
        this.graphRbacManager = graphRbacManager;
        this.tenantId = tenantId;
    }

    @Override
    public PagedList<Vault> listByResourceGroup(String groupName) {
        return wrapList(this.inner().listByResourceGroup(groupName));
    }

    @Override
    public Observable<Vault> listByResourceGroupAsync(String resourceGroupName) {
        return wrapPageAsync(this.inner().listByResourceGroupAsync(resourceGroupName));
    }

    @Override
    protected Observable<VaultInner> getInnerAsync(String resourceGroupName, String name) {
        return this.inner().getByResourceGroupAsync(resourceGroupName, name);
    }

    @Override
    protected Completable deleteInnerAsync(String resourceGroupName, String name) {
        return this.inner().deleteAsync(resourceGroupName, name).toCompletable();
    }

    @Override
    public Completable deleteByResourceGroupAsync(String groupName, String name) {
        return this.inner().deleteAsync(groupName, name).toCompletable();
    }

    @Override
    public VaultImpl define(String name) {
        return wrapModel(name).withSku(SkuName.STANDARD).withEmptyAccessPolicy();
    }

    @Override
    protected VaultImpl wrapModel(String name) {
        VaultInner inner = new VaultInner().withProperties(new VaultProperties());
        inner.properties().withTenantId(UUID.fromString(tenantId));
        return new VaultImpl(name, inner, this.manager(), graphRbacManager);
    }

    @Override
    protected VaultImpl wrapModel(VaultInner vaultInner) {
        if (vaultInner == null) {
            return null;
        }
        return new VaultImpl(vaultInner.name(), vaultInner, super.manager(), graphRbacManager);
    }

    @Override
    public PagedList<DeletedVault> listDeleted() {
        PagedList<DeletedVaultInner> listDeleted = this.inner().listDeleted();
        PagedListConverter<DeletedVaultInner, DeletedVault> converter = new PagedListConverter<DeletedVaultInner, DeletedVault>() {
            @Override
            public Observable<DeletedVault> typeConvertAsync(DeletedVaultInner inner) {
                DeletedVault deletedVault = new DeletedVaultImpl(inner);
                return Observable.just(deletedVault);
            }
        };
        return converter.convert(listDeleted);
    }

    @Override
    public DeletedVault getDeleted(String vaultName, String location) {
        Object deletedVault = inner().getDeleted(vaultName, location);
        if (deletedVault == null) {
            return null;
        }
        return new DeletedVaultImpl((DeletedVaultInner) deletedVault);
    }

    @Override
    public void purgeDeleted(String vaultName, String location) {
        inner().purgeDeleted(vaultName, location);
    }

    @Override
    public Observable<DeletedVault> getDeletedAsync(String vaultName, String location) {
        VaultsInner client = this.inner();
        return client.getDeletedAsync(vaultName, location).map(new Func1<DeletedVaultInner, DeletedVault>() {
            @Override
            public DeletedVault call(DeletedVaultInner inner) {
                return new DeletedVaultImpl(inner);
            }
        });
    }

    @Override
    public Completable purgeDeletedAsync(String vaultName, String location) {
        return this.inner().purgeDeletedAsync(vaultName, location).toCompletable();
    }

    private Observable<DeletedVault> convertPageDeletedVaultToDeletedVaultAsync(Observable<Page<DeletedVault>> page) {
        return page.flatMap(new Func1<Page<DeletedVault>, Observable<DeletedVault>>() {
            @Override
            public Observable<DeletedVault> call(Page<DeletedVault> inner) {
                return Observable.from(inner.items());
            }
        });
    }

    @Override
    public Observable<DeletedVault> listDeletedAsync() {
        VaultsInner client = this.inner();
        Observable<Page<DeletedVault>> page = client.listDeletedAsync()
                .map(new Func1<Page<DeletedVaultInner>, Page<DeletedVault>>() {
                    @Override
                    public Page<DeletedVault> call(Page<DeletedVaultInner> inner) {
                        return convertPageDeletedVaultInner(inner);
                    }
                });
        return convertPageDeletedVaultToDeletedVaultAsync(page);
    }

    private Page<DeletedVault> convertPageDeletedVaultInner(Page<DeletedVaultInner> inner) {
        List<DeletedVault> items = new ArrayList<>();
        for (DeletedVaultInner item : inner.items()) {
            items.add(new DeletedVaultImpl(item));
        }
        PageImpl<DeletedVault> deletedVaultPage = new PageImpl<DeletedVault>();
        deletedVaultPage.setItems(items);
        return deletedVaultPage;
    }

    @Override
    public CheckNameAvailabilityResult checkNameAvailability(String name) {
        return new CheckNameAvailabilityResultImpl(inner().checkNameAvailability(name));
    }

    @Override
    public Observable<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name) {
        VaultsInner client = this.inner();
        return client.checkNameAvailabilityAsync(name)
                .map(new Func1<CheckNameAvailabilityResultInner, CheckNameAvailabilityResult>() {
                    @Override
                    public CheckNameAvailabilityResult call(CheckNameAvailabilityResultInner inner) {
                        return new CheckNameAvailabilityResultImpl(inner);
                    }
                });
    }

    @Override
    public Vault recoverSoftDeletedVault(String resourceGroupName, String vaultName, String location) {
        return recoverSoftDeletedVaultAsync(resourceGroupName, vaultName, location).toBlocking().last();
    }
    
    @Override
    public Observable<Vault> recoverSoftDeletedVaultAsync(final String resourceGroupName, final String vaultName, String location) {
        final KeyVaultManager manager = this.manager();
        return getDeletedAsync(vaultName, location).flatMap(new Func1<DeletedVault, Observable<Vault>>() {
            @Override
            public Observable<Vault> call(DeletedVault deletedVault) {
                VaultCreateOrUpdateParameters parameters = new VaultCreateOrUpdateParameters();
                parameters.withLocation(deletedVault.location());
                parameters.withTags(deletedVault.inner().properties().tags());
                parameters.withProperties(new VaultProperties()
                        .withCreateMode(CreateMode.RECOVER)
                        .withSku(new Sku().withName(SkuName.STANDARD))
                        .withTenantId(UUID.fromString(tenantId))
                        );
                return inner().createOrUpdateAsync(resourceGroupName, vaultName, parameters).map(new Func1<VaultInner, Vault>() {
                    @Override
                    public Vault call(VaultInner inner) {
                        return new VaultImpl(inner.id(), inner, manager, graphRbacManager);
                    }
                });
            }
            
        });
    }

}
