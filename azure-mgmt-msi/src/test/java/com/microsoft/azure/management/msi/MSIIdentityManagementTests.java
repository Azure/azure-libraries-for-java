/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.msi;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.graphrbac.BuiltInRole;
import com.microsoft.azure.management.graphrbac.RoleAssignment;
import com.microsoft.azure.v2.management.msi.implementation.MSIManager;
import com.microsoft.azure.management.resources.ResourceGroup;
import com.microsoft.azure.management.resources.core.TestBase;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.management.resources.implementation.ResourceManager;
import com.microsoft.azure.v2.management.msi.Identity;
import com.microsoft.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;
import rx.functions.Action1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MSIIdentityManagementTests extends TestBase {
    private static String RG_NAME = "";
    private static Region region = Region.fromName("West Central US");

    private MSIManager msiManager;
    private ResourceManager resourceManager;

    @Override
    protected void initializeClients(RestClient restClient, String defaultSubscription, String domain) throws IOException {
        this.msiManager = MSIManager.authenticate(restClient, defaultSubscription);
        this.resourceManager = msiManager.resourceManager();
    }

    @Override
    protected void cleanUpResources() {
        this.resourceManager.resourceGroups().deleteByName(RG_NAME);
    }

    @Test
    public void canCreateGetListDeleteIdentity() throws Exception {
        RG_NAME = generateRandomResourceName("javaismrg", 15);
        String identityName = generateRandomResourceName("msi-id", 15);

        Creatable<ResourceGroup> creatableRG = resourceManager.resourceGroups()
                .define(RG_NAME)
                .withRegion(region);

        Identity identity = msiManager.identities()
                .define(identityName)
                .withRegion(region)
                .withNewResourceGroup(creatableRG)
                .create();

        Assert.assertNotNull(identity);
        Assert.assertNotNull(identity.inner());
        Assert.assertTrue(String.format("%s == %s", identityName, identity.name()), identityName.equalsIgnoreCase(identity.name()));
        Assert.assertTrue(String.format("%s == %s", RG_NAME, identity.resourceGroupName()), RG_NAME.equalsIgnoreCase(identity.resourceGroupName()));

        Assert.assertNotNull(identity.clientId());
        Assert.assertNotNull(identity.principalId());
        Assert.assertNotNull(identity.tenantId());
        Assert.assertNotNull(identity.clientSecretUrl());

        identity = msiManager.identities().getById(identity.id());

        Assert.assertNotNull(identity);
        Assert.assertNotNull(identity.inner());

        PagedList<Identity> identities = msiManager.identities()
                .listByResourceGroup(RG_NAME);

        Assert.assertNotNull(identities);

        boolean found = false;
        for (Identity id : identities) {
            Assert.assertNotNull(id);
            Assert.assertNotNull(id.inner());
            if (id.name().equalsIgnoreCase(identityName)) {
                found = true;
            }
            Assert.assertNotNull(identity.clientId());
            Assert.assertNotNull(identity.principalId());
            Assert.assertNotNull(identity.tenantId());
            Assert.assertNotNull(identity.clientSecretUrl());
        }

        Assert.assertTrue(found);

        msiManager.identities()
                .deleteById(identity.id());
    }

    @Test
    public void canAssignCurrentResourceGroupAccessRoleToIdentity() throws Exception {
        RG_NAME = generateRandomResourceName("javaismrg", 15);
        String identityName = generateRandomResourceName("msi-id", 15);

        Creatable<ResourceGroup> creatableRG = resourceManager.resourceGroups()
                .define(RG_NAME)
                .withRegion(region);

        Identity identity = msiManager.identities()
                .define(identityName)
                .withRegion(region)
                .withNewResourceGroup(creatableRG)
                .withAccessToCurrentResourceGroup(BuiltInRole.READER)
                .create();

        // Ensure role assigned
        //
        ResourceGroup resourceGroup = this.resourceManager.resourceGroups().getByName(identity.resourceGroupName());
        PagedList<RoleAssignment> roleAssignments = this.msiManager.graphRbacManager().roleAssignments().listByScope(resourceGroup.id());
        boolean found = false;
        for (RoleAssignment roleAssignment : roleAssignments) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(identity.principalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the resource group that identity belongs to", found);

        identity.update()
                .withoutAccessTo(resourceGroup.id(), BuiltInRole.READER)
                .apply();

        SdkContext.sleep(30 * 1000);

        // Ensure role assignment removed
        //
        roleAssignments = this.msiManager.graphRbacManager().roleAssignments().listByScope(resourceGroup.id());
        boolean notFound = true;
        for (RoleAssignment roleAssignment : roleAssignments) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(identity.principalId())) {
                notFound = false;
                break;
            }
        }
        Assert.assertTrue("Role assignment to access resource group is not removed", notFound);

        msiManager.identities()
                .deleteById(identity.id());

    }

    @Test
    public void canAssignRolesToIdentity() throws Exception {
        RG_NAME = generateRandomResourceName("javaismrg", 15);
        String identityName = generateRandomResourceName("msi-id", 15);

        String anotherRgName = generateRandomResourceName("rg", 15);

        ResourceGroup anotherResourceGroup = resourceManager.resourceGroups()
                .define(anotherRgName)
                .withRegion(region)
                .create();

        Creatable<ResourceGroup> creatableRG = resourceManager.resourceGroups()
                .define(RG_NAME)
                .withRegion(region);

        final List<Indexable> createdResosurces = new ArrayList<Indexable>();

        msiManager.identities()
                .define(identityName)
                .withRegion(region)
                .withNewResourceGroup(creatableRG)
                .withAccessToCurrentResourceGroup(BuiltInRole.READER)
                .withAccessTo(anotherResourceGroup, BuiltInRole.CONTRIBUTOR)
                .createAsync()
                .doOnNext(new Action1<Indexable>() {
                    @Override
                    public void call(Indexable indexable) {
                        createdResosurces.add(indexable);
                    }
                }).toBlocking().last();

        int roleAssignmentResourcesCount = 0;
        int identityResourcesCount = 0;
        int resourceGroupResourcesCount = 0;
        Identity identity = null;

        for (Indexable resource : createdResosurces) {
            if (resource instanceof ResourceGroup) {
                resourceGroupResourcesCount++;
            } else if (resource instanceof RoleAssignment) {
                roleAssignmentResourcesCount++;
            } else if (resource instanceof Identity) {
                identityResourcesCount++;
                identity = (Identity) resource;
            }
        }

        Assert.assertEquals(1, resourceGroupResourcesCount);
        Assert.assertEquals(2, roleAssignmentResourcesCount);
        Assert.assertEquals(2, identityResourcesCount); // Identity resource will be emitted twice - before & after post-run, will be fixed in graph
        Assert.assertNotNull(identity);

        // Ensure roles are assigned
        //
        ResourceGroup resourceGroup = this.resourceManager.resourceGroups().getByName(identity.resourceGroupName());
        PagedList<RoleAssignment> roleAssignments = this.msiManager.graphRbacManager().roleAssignments().listByScope(resourceGroup.id());
        boolean found = false;
        for (RoleAssignment roleAssignment : roleAssignments) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(identity.principalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the resource group that identity belongs to", found);

        roleAssignments = this.msiManager.graphRbacManager().roleAssignments().listByScope(anotherResourceGroup.id());
        found = false;
        for (RoleAssignment roleAssignment : roleAssignments) {
            if (roleAssignment.principalId() != null && roleAssignment.principalId().equalsIgnoreCase(identity.principalId())) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected role assignment not found for the resource group resource", found);

        identity = identity
                .update()
                .withTag("a", "bb")
                .apply();

        Assert.assertNotNull(identity.tags());
        Assert.assertTrue(identity.tags().containsKey("a"));

        resourceManager.resourceGroups().deleteByName(anotherRgName);
    }
}
