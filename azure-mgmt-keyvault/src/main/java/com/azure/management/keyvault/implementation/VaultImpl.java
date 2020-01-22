/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.keyvault.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import com.azure.core.management.CloudException;
import com.azure.management.RestClient;
import com.azure.management.graphrbac.implementation.GraphRbacManager;
import com.azure.management.keyvault.AccessPolicy;
import com.azure.management.keyvault.AccessPolicyEntry;
import com.azure.management.keyvault.CreateMode;
import com.azure.management.keyvault.IPRule;
import com.azure.management.keyvault.Keys;
import com.azure.management.keyvault.NetworkRuleAction;
import com.azure.management.keyvault.NetworkRuleBypassOptions;
import com.azure.management.keyvault.NetworkRuleSet;
import com.azure.management.keyvault.Secrets;
import com.azure.management.keyvault.Sku;
import com.azure.management.keyvault.SkuName;
import com.azure.management.keyvault.Vault;
import com.azure.management.keyvault.VaultCreateOrUpdateParameters;
import com.azure.management.keyvault.VaultProperties;
import com.azure.management.keyvault.VirtualNetworkRule;
import com.azure.management.keyvault.models.VaultInner;
import com.azure.management.keyvault.models.VaultsInner;
import com.azure.management.resources.fluentcore.arm.models.implementation.GroupableResourceImpl;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import com.azure.management.resources.fluentcore.utils.Utils;
import com.azure.security.keyvault.keys.KeyAsyncClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
import com.azure.security.keyvault.secrets.SecretAsyncClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import reactor.core.publisher.Mono;

/**
 * Implementation for Vault and its parent interfaces.
 */
class VaultImpl extends GroupableResourceImpl<Vault, VaultInner, VaultImpl, KeyVaultManager>
        implements Vault, Vault.Definition, Vault.Update {
    private GraphRbacManager graphRbacManager;
    private List<AccessPolicyImpl> accessPolicies;

    private SecretAsyncClient secretClient;
    private KeyAsyncClient keyClient;
    private RestClient vaultRestClient;

    private Keys keys;
    private Secrets secrets;

    VaultImpl(String key, VaultInner innerObject, KeyVaultManager manager, GraphRbacManager graphRbacManager) {
        super(key, innerObject, manager);
        this.graphRbacManager = graphRbacManager;
        this.accessPolicies = new ArrayList<>();
        if (innerObject != null && innerObject.getProperties() != null
                && innerObject.getProperties().getAccessPolicies() != null) {
            for (AccessPolicyEntry entry : innerObject.getProperties().getAccessPolicies()) {
                this.accessPolicies.add(new AccessPolicyImpl(entry, this));
            }
        }

        vaultRestClient = getManager().newRestClientBuilder().withScope("https://vault.azure.net" + "/.default").buildClient();
        init();
    }

    private void init() {
        if (getInner().getProperties().getVaultUri() != null) {
            final String vaultUrl = vaultUri();
            this.secretClient = new SecretClientBuilder()
                    .vaultUrl(vaultUrl)
                    .pipeline(vaultRestClient.getHttpPipeline())
                    .buildAsyncClient();
            this.keyClient = new KeyClientBuilder()
                    .vaultUrl(vaultUrl)
                    .pipeline(vaultRestClient.getHttpPipeline())
                    .buildAsyncClient();
        }
    }

    @Override
    public RestClient vaultRestClient() {
        return vaultRestClient;
    }

    @Override
    public SecretAsyncClient secretClient() {
        return secretClient;
    }

    @Override
    public KeyAsyncClient keyClient() {
        return keyClient;
    }

    @Override
    public Keys keys() {
        if (keys == null) {
            keys = new KeysImpl(keyClient, this);
        }
        return keys;
    }

    @Override
    public Secrets secrets() {
        if (secrets == null) {
            secrets = new SecretsImpl(secretClient, this);
        }
        return secrets;
    }

    @Override
    public String vaultUri() {
        if (getInner().getProperties() == null) {
            return null;
        }
        return getInner().getProperties().getVaultUri();
    }

    @Override
    public String tenantId() {
        if (getInner().getProperties() == null) {
            return null;
        }
        if (getInner().getProperties().getTenantId() == null) {
            return null;
        }
        return getInner().getProperties().getTenantId().toString();
    }

    @Override
    public Sku sku() {
        if (getInner().getProperties() == null) {
            return null;
        }
        return getInner().getProperties().getSku();
    }

    @Override
    public List<AccessPolicy> accessPolicies() {
        AccessPolicy[] array = new AccessPolicy[accessPolicies.size()];
        return Arrays.asList(accessPolicies.toArray(array));
    }

    @Override
    public boolean enabledForDeployment() {
        if (getInner().getProperties() == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(getInner().getProperties().isEnabledForDeployment());
    }

    @Override
    public boolean enabledForDiskEncryption() {
        if (getInner().getProperties() == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(getInner().getProperties().isEnabledForDiskEncryption());
    }

    @Override
    public boolean enabledForTemplateDeployment() {
        if (getInner().getProperties() == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(getInner().getProperties().isEnabledForTemplateDeployment());
    }

    @Override
    public boolean softDeleteEnabled() {
        if (getInner().getProperties() == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(getInner().getProperties().isEnableSoftDelete());
    }

    @Override
    public boolean purgeProtectionEnabled() {
        if (getInner().getProperties() == null) {
            return false;
        }
        return Utils.toPrimitiveBoolean(getInner().getProperties().isEnablePurgeProtection());
    }

    @Override
    public VaultImpl withEmptyAccessPolicy() {
        this.accessPolicies = new ArrayList<>();
        return this;
    }

    @Override
    public VaultImpl withoutAccessPolicy(String objectId) {
        for (AccessPolicyImpl entry : this.accessPolicies) {
            if (entry.objectId().equals(objectId)) {
                accessPolicies.remove(entry);
                break;
            }
        }
        return this;
    }

    @Override
    public VaultImpl withAccessPolicy(AccessPolicy accessPolicy) {
        accessPolicies.add((AccessPolicyImpl) accessPolicy);
        return this;
    }

    @Override
    public AccessPolicyImpl defineAccessPolicy() {
        return new AccessPolicyImpl(new AccessPolicyEntry(), this);
    }

    @Override
    public AccessPolicyImpl updateAccessPolicy(String objectId) {
        for (AccessPolicyImpl entry : this.accessPolicies) {
            if (entry.objectId().equals(objectId)) {
                return entry;
            }
        }
        throw new NoSuchElementException(String.format("Identity %s not found in the access policies.", objectId));
    }

    @Override
    public VaultImpl withDeploymentEnabled() {
        getInner().getProperties().setEnabledForDeployment(true);
        return this;
    }

    @Override
    public VaultImpl withDiskEncryptionEnabled() {
        getInner().getProperties().setEnabledForDiskEncryption(true);
        return this;
    }

    @Override
    public VaultImpl withTemplateDeploymentEnabled() {
        getInner().getProperties().setEnabledForTemplateDeployment(true);
        return this;
    }

    @Override
    public VaultImpl withSoftDeleteEnabled() {
        getInner().getProperties().setEnableSoftDelete(true);
        return this;
    }

    @Override
    public VaultImpl withPurgeProtectionEnabled() {
        getInner().getProperties().setEnablePurgeProtection(true);
        return this;
    }

    @Override
    public VaultImpl withDeploymentDisabled() {
        getInner().getProperties().setEnabledForDeployment(false);
        return this;
    }

    @Override
    public VaultImpl withDiskEncryptionDisabled() {
        getInner().getProperties().setEnabledForDiskEncryption(false);
        return this;
    }

    @Override
    public VaultImpl withTemplateDeploymentDisabled() {
        getInner().getProperties().setEnabledForTemplateDeployment(false);
        return this;
    }

    @Override
    public VaultImpl withSku(SkuName skuName) {
        if (getInner().getProperties() == null) {
            getInner().setProperties(new VaultProperties());
        }
        getInner().getProperties().setSku(new Sku().setName(skuName));
        return this;
    }

    private Mono<List<AccessPolicy>> populateAccessPolicies() {
        List<Mono<?>> observables = new ArrayList<>();
        for (final AccessPolicyImpl accessPolicy : accessPolicies) {
            if (accessPolicy.objectId() == null) {
                if (accessPolicy.userPrincipalName() != null) {
                    observables.add(graphRbacManager.users().getByNameAsync(accessPolicy.userPrincipalName())
                            .subscribeOn(SdkContext.getReactorScheduler()).doOnNext(user -> {
                                if (user == null) {
                                    throw new CloudException(
                                            String.format("User principal name %s is not found in tenant %s",
                                                    accessPolicy.userPrincipalName(), graphRbacManager.tenantId()),
                                            null);
                                }
                                accessPolicy.forObjectId(user.getId());
                            }));
                } else if (accessPolicy.servicePrincipalName() != null) {
                    observables.add(
                            graphRbacManager.servicePrincipals().getByNameAsync(accessPolicy.servicePrincipalName())
                                    .subscribeOn(SdkContext.getReactorScheduler()).doOnNext(sp -> {
                                        if (sp == null) {
                                            throw new CloudException(String.format(
                                                    "Service principal name %s is not found in tenant %s",
                                                    accessPolicy.servicePrincipalName(), graphRbacManager.tenantId()),
                                                    null);
                                        }
                                        accessPolicy.forObjectId(sp.getId());
                                    }));
                } else {
                    throw new IllegalArgumentException("Access policy must specify object ID.");
                }
            }
        }
        if (observables.isEmpty()) {
            return Mono.just(accessPolicies());
        } else {
            return Mono.zip(observables, args -> accessPolicies());
        }
    }

    @Override
    public Mono<Vault> createResourceAsync() {
        final VaultsInner client = this.getManager().getInner().vaults();
        return populateAccessPolicies().flatMap(o -> {
            VaultCreateOrUpdateParameters parameters = new VaultCreateOrUpdateParameters();
            parameters.setLocation(getRegionName());
            parameters.setProperties(getInner().getProperties());
            parameters.setTags(getInner().getTags());
            parameters.getProperties().setAccessPolicies(new ArrayList<>());
            for (AccessPolicy accessPolicy : accessPolicies) {
                parameters.getProperties().getAccessPolicies().add(accessPolicy.getInner());
            }
            return client.createOrUpdateAsync(getResourceGroupName(), getName(), parameters);
        }).map(innerToFluentMap(this)).map(ignore -> {
            init();
            return this;
        });
    }

    @Override
    protected Mono<VaultInner> getInnerAsync() {
        return this.getManager().getInner().vaults().getByResourceGroupAsync(getResourceGroupName(), getName());
    }

    @Override
    public CreateMode createMode() {
        return getInner().getProperties().getCreateMode();
    }


    @Override
    public NetworkRuleSet networkRuleSet() {
        return getInner().getProperties().getNetworkAcls();
    }

    @Override
    public VaultImpl withAccessFromAllNetworks() {
        if (getInner().getProperties().getNetworkAcls() == null) {
            getInner().getProperties().setNetworkAcls(new NetworkRuleSet());
        }
        getInner().getProperties().getNetworkAcls().setDefaultAction(NetworkRuleAction.ALLOW);
        return this;
    }

    @Override
    public VaultImpl withAccessFromSelectedNetworks() {
        if (getInner().getProperties().getNetworkAcls() == null) {
            getInner().getProperties().setNetworkAcls(new NetworkRuleSet());
        }
        getInner().getProperties().getNetworkAcls().setDefaultAction(NetworkRuleAction.DENY);
        return this;
    }

    /**
     * Specifies that access to the storage account should be allowed from the given ip address or ip address range.
     *
     * @param ipAddressOrRange the ip address or ip address range in cidr format
     * @return VaultImpl
     */
    private VaultImpl withAccessAllowedFromIpAddressOrRange(String ipAddressOrRange) {
        NetworkRuleSet networkRuleSet = getInner().getProperties().getNetworkAcls();
        if (networkRuleSet.getIpRules() == null) {
            networkRuleSet.setIpRules(new ArrayList<>());
        }
        boolean found = false;
        for (IPRule rule: networkRuleSet.getIpRules()) {
            if (rule.getValue().equalsIgnoreCase(ipAddressOrRange)) {
                found = true;
                break;
            }
        }
        if (!found) {
            networkRuleSet.getIpRules().add(new IPRule()
                    .setValue(ipAddressOrRange));
        }
        return this;
    }
    
    @Override
    public VaultImpl withAccessFromIpAddress(String ipAddress) {
        return withAccessAllowedFromIpAddressOrRange(ipAddress);
    }

    @Override
    public VaultImpl withAccessFromIpAddressRange(String ipAddressCidr) {
        return withAccessAllowedFromIpAddressOrRange(ipAddressCidr);
    }

    @Override
    public VaultImpl withAccessFromAzureServices() {
        if (getInner().getProperties().getNetworkAcls() == null) {
            getInner().getProperties().setNetworkAcls(new NetworkRuleSet());
        }
        getInner().getProperties().getNetworkAcls().setBypass(NetworkRuleBypassOptions.AZURE_SERVICES);
        return this;
    }

    @Override
    public VaultImpl withBypass(NetworkRuleBypassOptions bypass) {
        if (getInner().getProperties().getNetworkAcls() == null) {
            getInner().getProperties().setNetworkAcls(new NetworkRuleSet());
        }
        getInner().getProperties().getNetworkAcls().setBypass(bypass);
        return this;
    }

    @Override
    public VaultImpl withDefaultAction(NetworkRuleAction defaultAction) {
        if (getInner().getProperties().getNetworkAcls() == null) {
            getInner().getProperties().setNetworkAcls(new NetworkRuleSet());
        }
        getInner().getProperties().getNetworkAcls().setDefaultAction(defaultAction);
        return this;
    }

    @Override
    public VaultImpl withVirtualNetworkRules(List<VirtualNetworkRule> virtualNetworkRules) {
        if (getInner().getProperties().getNetworkAcls() == null) {
            getInner().getProperties().setNetworkAcls(new NetworkRuleSet());
        }
        getInner().getProperties().getNetworkAcls().setVirtualNetworkRules(virtualNetworkRules);
        return this;
    }

}
