/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.keyvault;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.management.CloudException;
import com.azure.management.graphrbac.ActiveDirectoryUser;
import com.azure.management.graphrbac.ServicePrincipal;
import com.azure.management.resources.fluentcore.arm.Region;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import org.junit.Test;

public class VaultTests extends KeyVaultManagementTest {
    @Test
    public void canCRUDVault() throws Exception {
        // Create user service principal
        String sp = SdkContext.randomResourceName("sp", 20);
        String us = SdkContext.randomResourceName("us", 20);
        ServicePrincipal servicePrincipal = graphRbacManager.servicePrincipals()
                .define(sp)
                .withNewApplication("http://" + sp)
                .create();

        ActiveDirectoryUser user = graphRbacManager.users()
                .define(us)
                .withEmailAlias(us)
                .withPassword("P@$$w0rd")
                .create();

        try {
            // CREATE
            Vault vault = keyVaultManager.vaults().define(VAULT_NAME)
                    .withRegion(Region.US_WEST)
                    .withNewResourceGroup(RG_NAME)
                    .defineAccessPolicy()
                    .forServicePrincipal("http://" + sp)
                        .allowKeyPermissions(KeyPermissions.LIST)
                        .allowSecretAllPermissions()
                        .allowCertificatePermissions(CertificatePermissions.GET)
                        .attach()
                    .defineAccessPolicy()
                    .forUser(us)
                        .allowKeyAllPermissions()
                        .allowSecretAllPermissions()
                        .allowCertificatePermissions(CertificatePermissions.GET, CertificatePermissions.LIST, CertificatePermissions.CREATE)
                        .attach()
                    //.withBypass(NetworkRuleBypassOptions.AZURE_SERVICES)
                    .withAccessFromAzureServices()
                    .withAccessFromIpAddress("0.0.0.0/0")
                    .create();
            Assert.assertNotNull(vault);
            Assert.assertFalse(vault.softDeleteEnabled());
            Assert.assertEquals(vault.networkRuleSet().getBypass(), NetworkRuleBypassOptions.AZURE_SERVICES);
            
            // GET
            vault = keyVaultManager.vaults().getByResourceGroup(RG_NAME, VAULT_NAME);
            Assert.assertNotNull(vault);
            for (AccessPolicy policy : vault.accessPolicies()) {
                if (policy.objectId().equals(servicePrincipal.id())) {
                    Assert.assertArrayEquals(new KeyPermissions[] { KeyPermissions.LIST }, policy.permissions().getKeys().toArray());
                    Assert.assertEquals(SecretPermissions.values().size(), policy.permissions().getSecrets().size());
                    Assert.assertArrayEquals(new CertificatePermissions[] { CertificatePermissions.GET }, policy.permissions().getCertificates().toArray());
                }
                if (policy.objectId().equals(user.id())) {
                    Assert.assertEquals(KeyPermissions.values().size(), policy.permissions().getKeys().size());
                    Assert.assertEquals(SecretPermissions.values().size(), policy.permissions().getSecrets().size());
                    Assert.assertEquals(3, policy.permissions().getCertificates().size());
                }
            }
            // LIST
            PagedIterable<Vault> vaults = keyVaultManager.vaults().listByResourceGroup(RG_NAME);
            for (Vault v : vaults) {
                if (VAULT_NAME.equals(v.name())) {
                    vault = v;
                    break;
                }
            }
            Assert.assertNotNull(vault);
            // UPDATE
            vault.update()
                    .updateAccessPolicy(servicePrincipal.id())
                        .allowKeyAllPermissions()
                        .disallowSecretAllPermissions()
                        .allowCertificateAllPermissions()
                        .parent()
                    .withTag("foo", "bar")
                    .apply();
            for (AccessPolicy policy : vault.accessPolicies()) {
                if (policy.objectId().equals(servicePrincipal.id())) {
                    Assert.assertEquals(KeyPermissions.values().size(), policy.permissions().getKeys().size());
                    Assert.assertEquals(0, policy.permissions().getSecrets().size());
                    Assert.assertEquals(CertificatePermissions.values().size(), policy.permissions().getCertificates().size());
                }
            }
            
            // DELETE
            keyVaultManager.vaults().deleteById(vault.id());
            SdkContext.sleep(20000);
            boolean deleted = false;
            try {
                keyVaultManager.vaults().getDeleted(VAULT_NAME, Region.US_WEST.toString());
            } catch (CloudException exception) {
                if (exception.getResponse().getStatusCode() == 404) {
                    deleted = true;
                }
            }
            Assert.assertTrue(deleted);
        } finally {
            graphRbacManager.servicePrincipals().deleteById(servicePrincipal.id());
//            graphRbacManager.users().deleteById(user.id());
        }
    }
    
    @Test
    public void canCRUDVaultAsync() throws Exception {
        // Create user service principal
        String sp = SdkContext.randomResourceName("sp", 20);
        String us = SdkContext.randomResourceName("us", 20);
        ServicePrincipal servicePrincipal = graphRbacManager.servicePrincipals()
                .define(sp)
                .withNewApplication("http://" + sp)
                .create();

        ActiveDirectoryUser user = graphRbacManager.users()
                .define(us)
                .withEmailAlias(us)
                .withPassword("P@$$w0rd")
                .create();

        try {
            // CREATE
            Vault vault = keyVaultManager.vaults().define(VAULT_NAME)
                    .withRegion(Region.US_WEST)
                    .withNewResourceGroup(RG_NAME)
                    .defineAccessPolicy()
                    .forServicePrincipal("http://" + sp)
                        .allowKeyPermissions(KeyPermissions.LIST)
                        .allowSecretAllPermissions()
                        .allowCertificatePermissions(CertificatePermissions.GET)
                        .attach()
                    .defineAccessPolicy()
                    .forUser(us)
                        .allowKeyAllPermissions()
                        .allowSecretAllPermissions()
                        .allowCertificatePermissions(CertificatePermissions.GET, CertificatePermissions.LIST, CertificatePermissions.CREATE)
                        .attach()
                    .create();
            Assert.assertNotNull(vault);
            Assert.assertFalse(vault.softDeleteEnabled());
            // GET
            vault = keyVaultManager.vaults().getByResourceGroupAsync(RG_NAME, VAULT_NAME).block();
            Assert.assertNotNull(vault);
            for (AccessPolicy policy : vault.accessPolicies()) {
                if (policy.objectId().equals(servicePrincipal.id())) {
                    Assert.assertArrayEquals(new KeyPermissions[] { KeyPermissions.LIST }, policy.permissions().getKeys().toArray());
                    Assert.assertEquals(SecretPermissions.values().size(), policy.permissions().getSecrets().size());
                    Assert.assertArrayEquals(new CertificatePermissions[] { CertificatePermissions.GET }, policy.permissions().getCertificates().toArray());
                }
                if (policy.objectId().equals(user.id())) {
                    Assert.assertEquals(KeyPermissions.values().size(), policy.permissions().getKeys().size());
                    Assert.assertEquals(SecretPermissions.values().size(), policy.permissions().getSecrets().size());
                    Assert.assertEquals(3, policy.permissions().getCertificates().size());
                }
            }
            // LIST
            PagedIterable<Vault> vaults = new PagedIterable<>(keyVaultManager.vaults().listByResourceGroupAsync(RG_NAME));
            for (Vault v : vaults) {
                if (VAULT_NAME.equals(v.name())) {
                    vault = v;
                    break;
                }
            }
            Assert.assertNotNull(vault);
            // UPDATE
            vault.update()
                    .updateAccessPolicy(servicePrincipal.id())
                        .allowKeyAllPermissions()
                        .disallowSecretAllPermissions()
                        .allowCertificateAllPermissions()
                        .parent()
                    .withTag("foo", "bar")
                    .apply();
            for (AccessPolicy policy : vault.accessPolicies()) {
                if (policy.objectId().equals(servicePrincipal.id())) {
                    Assert.assertEquals(KeyPermissions.values().size(), policy.permissions().getKeys().size());
                    Assert.assertEquals(0, policy.permissions().getSecrets().size());
                    Assert.assertEquals(CertificatePermissions.values().size(), policy.permissions().getCertificates().size());
                }
            }
            
            // DELETE
            keyVaultManager.vaults().deleteByIdAsync(vault.id()).block();
            SdkContext.sleep(20000);
            boolean deleted = false;
            try {
                keyVaultManager.vaults().getDeleted(VAULT_NAME, Region.US_WEST.toString());
            } catch (CloudException exception) {
                if (exception.getResponse().getStatusCode() == 404) {
                    deleted = true;
                }
            }
            Assert.assertTrue(deleted);
        } finally {
            graphRbacManager.servicePrincipals().deleteById(servicePrincipal.id());
//            graphRbacManager.users().deleteById(user.id());
        }
    }
    
    @Test
    public void canEnableSoftDeleteAndPurge() throws InterruptedException {
    	String otherVaultName = VAULT_NAME + "other";
        String sp = SdkContext.randomResourceName("sp", 20);
        String us = SdkContext.randomResourceName("us", 20);
        
        ServicePrincipal servicePrincipal = graphRbacManager.servicePrincipals()
                .define(sp)
                .withNewApplication("http://" + sp)
                .create();

        ActiveDirectoryUser user = graphRbacManager.users()
                .define(us)
                .withEmailAlias(us)
                .withPassword("P@$$w0rd")
                .create();

        try {
            Vault vault = keyVaultManager.vaults().define(otherVaultName)
                    .withRegion(Region.US_WEST)
                    .withNewResourceGroup(RG_NAME)
                    .defineAccessPolicy()
                    .forServicePrincipal("http://" + sp)
                        .allowKeyPermissions(KeyPermissions.LIST)
                        .allowSecretAllPermissions()
                        .allowCertificatePermissions(CertificatePermissions.GET)
                        .attach()
                    .defineAccessPolicy()
                    .forUser(us)
                        .allowKeyAllPermissions()
                        .allowSecretAllPermissions()
                        .allowCertificatePermissions(CertificatePermissions.GET, CertificatePermissions.LIST, CertificatePermissions.CREATE)
                        .attach()
                    .withSoftDeleteEnabled()
                    .create();
            Assert.assertTrue(vault.softDeleteEnabled());

            keyVaultManager.vaults().deleteByResourceGroup(RG_NAME, otherVaultName);;
            SdkContext.sleep(20000);
            //Can still see deleted vault.
            Assert.assertNotNull(keyVaultManager.vaults().getDeleted(otherVaultName, Region.US_WEST.toString()));

            keyVaultManager.vaults().purgeDeleted(otherVaultName,  Region.US_WEST.toString());
            SdkContext.sleep(20000);
            //Vault is purged
            boolean deleted = false;
            try {
                keyVaultManager.vaults().getDeleted(otherVaultName, Region.US_WEST.toString());
            } catch (CloudException exception) {
                if (exception.getResponse().getStatusCode() == 404) {
                    deleted = true;
                }
            }
            Assert.assertTrue(deleted);
        } finally {
            graphRbacManager.servicePrincipals().deleteById(servicePrincipal.id());
           // graphRbacManager.users().deleteById(user.id());
        }
    }
    
}
