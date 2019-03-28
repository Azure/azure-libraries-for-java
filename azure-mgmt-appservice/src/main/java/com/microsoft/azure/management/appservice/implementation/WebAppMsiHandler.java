/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.appservice.ManagedServiceIdentity;
import com.microsoft.azure.management.appservice.ManagedServiceIdentityType;
import com.microsoft.azure.management.appservice.ManagedServiceIdentityUserAssignedIdentitiesValue;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.graphrbac.implementation.RoleAssignmentHelper;
import com.microsoft.azure.management.msi.Identity;
import com.microsoft.azure.management.resources.ResourceIdentityType;
import com.microsoft.azure.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class to set Managed Service Identity (MSI) property on a web app,
 * install or update MSI extension and create role assignments for the service principal
 * associated with the web app.
 */
public class WebAppMsiHandler  extends RoleAssignmentHelper {
    private WebAppBaseImpl webAppBase;

    private List<String> creatableIdentityKeys;
    private Map<String, ManagedServiceIdentityUserAssignedIdentitiesValue> userAssignedIdentities;

    /**
     * Creates VirtualMachineMsiHandler.
     *
     * @param rbacManager the graph rbac manager
     * @param webAppBase the web app to which MSI extension needs to be installed and
     *                       for which role assignments needs to be created
     */
    WebAppMsiHandler(final GraphRbacManager rbacManager,
                             WebAppBaseImpl webAppBase) {
        super(rbacManager, webAppBase.taskGroup(), webAppBase.idProvider());
        this.webAppBase = webAppBase;
        this.creatableIdentityKeys = new ArrayList<>();
        this.userAssignedIdentities = new HashMap<>();
    }

    /**
     * Specifies that Local Managed Service Identity needs to be enabled in the web app.
     * If MSI extension is not already installed then it will be installed with access token
     * port as 50342.
     *
     * @return WebAppMsiHandler
     */
    WebAppMsiHandler withLocalManagedServiceIdentity() {
        this.initSiteIdentity(ManagedServiceIdentityType.SYSTEM_ASSIGNED);
        return this;
    }

    /**
     * Specifies that Local Managed Service Identity needs to be disabled in the web app.
     *
     * @return WebAppMsiHandler
     */
    WebAppMsiHandler withoutLocalManagedServiceIdentity() {
        SiteInner siteInner = (SiteInner) this.webAppBase.inner();
        if (siteInner.identity() == null
                || siteInner.type() == null
                || siteInner.identity().type().equals(ManagedServiceIdentityType.USER_ASSIGNED)) {
            return this;
        } else if (siteInner.identity().type().equals(ResourceIdentityType.SYSTEM_ASSIGNED)) {
            siteInner.withIdentity(null);
        }
        return this;
    }

    /**
     * Specifies that given identity should be set as one of the External Managed Service Identity
     * of the web app.
     *
     * @param creatableIdentity yet-to-be-created identity to be associated with the virtual machine
     * @return WebAppMsiHandler
     */
    WebAppMsiHandler withNewExternalManagedServiceIdentity(Creatable<Identity> creatableIdentity) {
        this.initSiteIdentity(ManagedServiceIdentityType.USER_ASSIGNED);

        TaskGroup.HasTaskGroup dependency = (TaskGroup.HasTaskGroup) creatableIdentity;
        Objects.requireNonNull(dependency);

        this.webAppBase.taskGroup().addDependency(dependency);
        this.creatableIdentityKeys.add(creatableIdentity.key());

        return this;
    }

    /**
     * Specifies that given identity should be set as one of the External Managed Service Identity
     * of the web app.
     *
     * @param identity an identity to associate
     * @return WebAppMsiHandler
     */
    WebAppMsiHandler withExistingExternalManagedServiceIdentity(Identity identity) {
        this.initSiteIdentity(ManagedServiceIdentityType.USER_ASSIGNED);
        this.userAssignedIdentities.put(identity.id(), new ManagedServiceIdentityUserAssignedIdentitiesValue());
        return this;
    }

    /**
     * Specifies that given identity should be removed from the list of External Managed Service Identity
     * associated with the web app.
     *
     * @param identityId resource id of the identity
     * @return WebAppMsiHandler
     */
    WebAppMsiHandler withoutExternalManagedServiceIdentity(String identityId) {
        this.userAssignedIdentities.put(identityId, null);
        return this;
    }

    void processCreatedExternalIdentities() {
        for (String key : this.creatableIdentityKeys) {
            Identity identity = (Identity) this.webAppBase.taskGroup().taskResult(key);
            Objects.requireNonNull(identity);
            this.userAssignedIdentities.put(identity.id(), new ManagedServiceIdentityUserAssignedIdentitiesValue());
        }
        this.creatableIdentityKeys.clear();
    }

    void handleExternalIdentities() {
        SiteInner siteInner = (SiteInner) this.webAppBase.inner();
        if (!this.userAssignedIdentities.isEmpty()) {
            siteInner.identity().withUserAssignedIdentities(this.userAssignedIdentities);
        }
    }


    /**
     * Clear VirtualMachineMsiHandler post-run specific internal state.
     */
    void clear() {
        this.userAssignedIdentities = new HashMap<>();
    }


    /**
     * Initialize VM's identity property.
     *
     * @param identityType the identity type to set
     */
    private void initSiteIdentity(ManagedServiceIdentityType identityType) {
        if (!identityType.equals(ManagedServiceIdentityType.USER_ASSIGNED)
                && !identityType.equals(ManagedServiceIdentityType.SYSTEM_ASSIGNED)) {
            throw new IllegalArgumentException("Invalid argument: " + identityType);
        }

        SiteInner siteInner = (SiteInner) this.webAppBase.inner();
        if (siteInner.identity() == null) {
            siteInner.withIdentity(new ManagedServiceIdentity());
        }
        if (siteInner.identity().type() == null
                || siteInner.identity().type().equals(identityType)) {
            siteInner.identity().withType(identityType);
        } else {
            siteInner.identity().withType(ManagedServiceIdentityType.SYSTEM_ASSIGNED);
        }
    }
}
