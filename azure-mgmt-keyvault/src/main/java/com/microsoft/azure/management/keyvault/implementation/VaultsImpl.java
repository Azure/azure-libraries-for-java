/**
  * Copyright (c) Microsot Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.microsoft.azure.AzureServiceFuture;
import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.keyvault.CheckNameAvailabilityResult;
import com.microsoft.azure.management.keyvault.DeletedVault;
import com.microsoft.azure.management.keyvault.SkuName;
import com.microsoft.azure.management.keyvault.Vault;
import com.microsoft.azure.management.keyvault.VaultProperties;
import com.microsoft.azure.management.keyvault.Vaults;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.GroupableResourcesImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.PagedListConverter;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceResponse;

import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

/**
 * The implementation of Vaults and its parent interfaces.
 */
@LangDefinition
class VaultsImpl
        extends GroupableResourcesImpl<
            Vault,
            VaultImpl,
            VaultInner,
            VaultsInner,
            KeyVaultManager>
        implements Vaults {
    private final GraphRbacManager graphRbacManager;
    private final String tenantId;

    VaultsImpl(
            final KeyVaultManager keyVaultManager,
            final GraphRbacManager graphRbacManager,
            final String tenantId) {
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
        return wrapModel(name)
                .withSku(SkuName.STANDARD)
                .withEmptyAccessPolicy();
    }

    @Override
    protected VaultImpl wrapModel(String name) {
        VaultInner inner = new VaultInner().withProperties(new VaultProperties());
        inner.properties().withTenantId(UUID.fromString(tenantId));
        return new VaultImpl(
                name,
                inner,
                this.manager(),
                graphRbacManager);
    }

    @Override
    protected VaultImpl wrapModel(VaultInner vaultInner) {
        if (vaultInner == null) {
            return null;
        }
        return new VaultImpl(
                vaultInner.name(),
                vaultInner,
                super.manager(),
                graphRbacManager);
    }    
	
	@Override
	public PagedList<DeletedVault> listDeleted() {
	    PagedList<DeletedVaultInner> listDeleted = this.inner().listDeleted();
	    PagedListConverter<DeletedVaultInner, DeletedVault> converter =  
	            new PagedListConverter<DeletedVaultInner, DeletedVault> () {
	                @Override
                    public Observable<DeletedVault> typeConvertAsync(DeletedVaultInner inner) {
                        return Observable.just((DeletedVault) new DeletedVaultImpl(inner));
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
    public Observable<ServiceResponse<DeletedVault>> getDeletedWithServiceResponseAsync(String vaultName, String location) {
        VaultsInner client = this.inner();
        return client.getDeletedWithServiceResponseAsync(vaultName, location).map(new Func1<ServiceResponse<DeletedVaultInner>, ServiceResponse<DeletedVault>>() {
            @Override
            public ServiceResponse<DeletedVault> call(ServiceResponse<DeletedVaultInner> inner) {
                return new ServiceResponse<DeletedVault>(new DeletedVaultImpl(inner.body()), inner.response());
            }
        });
    }

    @Override
    public ServiceFuture<DeletedVault> getDeletedAsync(String vaultName, String location, final ServiceCallback<DeletedVault> serviceCallback) {
        return ServiceFuture.fromResponse(getDeletedWithServiceResponseAsync(vaultName, location), serviceCallback);
    }


    @Override
    public ServiceFuture<Void> purgeDeletedAsync(String vaultName, String location,
            ServiceCallback<Void> serviceCallback) {
        return this.inner().purgeDeletedAsync(vaultName, location, serviceCallback);
    }


    @Override
    public Observable<Void> purgeDeletedAsync(String vaultName, String location) {
        return this.inner().purgeDeletedAsync(vaultName, location);
    }


    @Override
    public Observable<ServiceResponse<Void>> purgeDeletedWithServiceResponseAsync(String vaultName, String location) {
        return this.inner().purgeDeletedWithServiceResponseAsync(vaultName, location);
    }


    @Override
    public ServiceFuture<List<DeletedVault>> listDeletedAsync(
            ListOperationCallback<DeletedVault> serviceCallback) {
        //Reimplemented this due to different callback types
        return AzureServiceFuture.fromPageResponse(listDeletedSinglePageAsync(),
                new Func1<String, Observable<ServiceResponse<Page<DeletedVault>>>> () {
            @Override
            public Observable<ServiceResponse<Page<DeletedVault>>> call(String nextPageLink) {
                return listDeletedNextSinglePageAsync(nextPageLink);
            }
        }, serviceCallback);
    }
    
    @Override
    public Observable<ServiceResponse<Page<DeletedVault>>> listDeletedSinglePageAsync() {
        VaultsInner client = this.inner();
        return client.listDeletedSinglePageAsync().map(new Func1<ServiceResponse<Page<DeletedVaultInner>>, ServiceResponse<Page<DeletedVault>>>() {
            @Override
            public ServiceResponse<Page<DeletedVault>> call(ServiceResponse<Page<DeletedVaultInner>> inner) {
                return new ServiceResponse<Page<DeletedVault>>(convertPageDeletedVaultInner(inner.body()), inner.response());
            }
        });
    }
    
    @Override
    public Observable<ServiceResponse<Page<DeletedVault>>> listDeletedNextSinglePageAsync(final String nextPageLink) {
        VaultsInner client = this.inner();
        return client.listDeletedNextSinglePageAsync(nextPageLink).map(new Func1<ServiceResponse<Page<DeletedVaultInner>>, ServiceResponse<Page<DeletedVault>>>() {
            @Override
            public ServiceResponse<Page<DeletedVault>> call(ServiceResponse<Page<DeletedVaultInner>> inner) {
                return new ServiceResponse<Page<DeletedVault>>(convertPageDeletedVaultInner(inner.body()), inner.response());
            }
        });
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
        Observable<Page<DeletedVault>> page =  client.listDeletedAsync().map(new Func1<Page<DeletedVaultInner>, Page<DeletedVault>>() {
            @Override
            public Page<DeletedVault> call(Page<DeletedVaultInner> inner) {
                return convertPageDeletedVaultInner(inner);
            }
        });
        return convertPageDeletedVaultToDeletedVaultAsync(page);
    }

    @Override
    public Observable<ServiceResponse<Page<DeletedVault>>> listDeletedWithServiceResponseAsync() {
        VaultsInner client = this.inner();
        return client.listDeletedWithServiceResponseAsync().map(new Func1<ServiceResponse<Page<DeletedVaultInner>>, ServiceResponse<Page<DeletedVault>>>() {
            @Override
            public ServiceResponse<Page<DeletedVault>> call (ServiceResponse<Page<DeletedVaultInner>> inner) {
                return new ServiceResponse<Page<DeletedVault>>(convertPageDeletedVaultInner(inner.body()), inner.response());
            }
        });
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
    public ServiceFuture<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name,
            ServiceCallback<CheckNameAvailabilityResult> serviceCallback) {
        return ServiceFuture.fromResponse(checkNameAvailabilityWithServiceResponseAsync(name), serviceCallback);
    }


    @Override
    public Observable<CheckNameAvailabilityResult> checkNameAvailabilityAsync(String name) {
        VaultsInner client = this.inner();
        return client.checkNameAvailabilityAsync(name).map(new Func1<CheckNameAvailabilityResultInner, CheckNameAvailabilityResult> () {
            @Override
            public CheckNameAvailabilityResult call(CheckNameAvailabilityResultInner inner) {
                return new CheckNameAvailabilityResultImpl(inner);
            }
        });
    }


    @Override
    public Observable<ServiceResponse<CheckNameAvailabilityResult>> checkNameAvailabilityWithServiceResponseAsync(
            String name) {
        VaultsInner client = this.inner();        
        return client.checkNameAvailabilityWithServiceResponseAsync(name).map(new Func1<ServiceResponse<CheckNameAvailabilityResultInner>, ServiceResponse<CheckNameAvailabilityResult>>() {
            @Override
            public ServiceResponse<CheckNameAvailabilityResult> call(ServiceResponse<CheckNameAvailabilityResultInner> inner) {
                return new ServiceResponse<CheckNameAvailabilityResult>(new CheckNameAvailabilityResultImpl(inner.body()), inner.response());
            }
        });
    }


    @Override
    public PagedList<DeletedVault> listDeletedNext(String nextPageLink) {
        PagedList<DeletedVaultInner> listDeleted = this.inner().listDeletedNext(nextPageLink);
        PagedListConverter<DeletedVaultInner, DeletedVault> converter =  
                new PagedListConverter<DeletedVaultInner, DeletedVault> () {
                    @Override
                    public Observable<DeletedVault> typeConvertAsync(DeletedVaultInner inner) {
                        return Observable.just((DeletedVault) new DeletedVaultImpl(inner));
                    }
        };
        return converter.convert(listDeleted);
    }


    @Override
    public ServiceFuture<List<DeletedVault>> listDeletedNextAsync(String nextPageLink,
            ServiceFuture<List<DeletedVault>> serviceFuture, ListOperationCallback<DeletedVault> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(listDeletedNextSinglePageAsync(nextPageLink), 
                new Func1<String, Observable<ServiceResponse<Page<DeletedVault>>>>() {
            @Override
            public Observable<ServiceResponse<Page<DeletedVault>>> call(String nextPageLink) {
                return listDeletedNextSinglePageAsync(nextPageLink);
            }
        }, serviceCallback);
    }


    @Override
    public Observable<DeletedVault> listDeletedNextAsync(String nextPageLink) {
        VaultsInner client = this.inner();
        Observable<Page<DeletedVault>> page = client.listDeletedNextAsync(nextPageLink).map(new Func1<Page<DeletedVaultInner>, Page<DeletedVault>>() {
            @Override
            public Page<DeletedVault> call(Page<DeletedVaultInner> inner) {
                return convertPageDeletedVaultInner(inner);
            }
        });
        return convertPageDeletedVaultToDeletedVaultAsync(page);
    }


    @Override
    public Observable<ServiceResponse<Page<DeletedVault>>> listDeletedNextWithServiceResponseAsync(
            String nextPageLink) {
        VaultsInner client = this.inner();
        return client.listDeletedNextWithServiceResponseAsync(nextPageLink).map(new Func1<ServiceResponse<Page<DeletedVaultInner>>, ServiceResponse<Page<DeletedVault>>>() {
            @Override
            public ServiceResponse<Page<DeletedVault>> call (ServiceResponse<Page<DeletedVaultInner>> inner) {
                return new ServiceResponse<Page<DeletedVault>>(convertPageDeletedVaultInner(inner.body()), inner.response());
            }
        });
    }


}
