/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.msi;

import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.graphrbac.BuiltInRole;
import com.microsoft.azure.management.msi.implementation.MSIManager;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.rest.LogLevel;
import org.junit.Test;

import java.io.File;

public class MSIIdentityManagementTests {
    @Test
    public void firstTests() throws Exception {
        final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));
        ApplicationTokenCredentials credentials = ApplicationTokenCredentials.fromFile(credFile);

        MSIManager msiManager = MSIManager
                .configure()
                .withLogLevel(LogLevel.BODY_AND_HEADERS)
                .authenticate(credentials, credentials.defaultSubscriptionId());

        msiManager.identities()
                .define("name")
                .withRegion(Region.US_EAST)
                .withNewResourceGroup("new-rg")
                .create();

        Identity identity = msiManager.identities()
                .define("name")
                .withRegion(Region.US_EAST)
                .withNewResourceGroup("new-rg")
                .withRoleBasedAccessTo("", BuiltInRole.READER)
                .create();
    }
}
