/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.compute.implementation;

import com.microsoft.azure.v2.management.compute.VirtualMachineIdentity;
import com.microsoft.azure.v2.management.compute.VirtualMachineIdentityUserAssignedIdentitiesValue;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.azure.v2.management.compute.implementation.VirtualMachineInner;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SerializationTests {
    @Test
    public void test1() throws IOException {
        AzureJacksonAdapter jacksonAdapter = new AzureJacksonAdapter();

        Map<String, VirtualMachineIdentityUserAssignedIdentitiesValue> userAssignedIdentities = new HashMap<>();
        userAssignedIdentities.put("af.B/C", new VirtualMachineIdentityUserAssignedIdentitiesValue());
        userAssignedIdentities.put("af.B/D", new VirtualMachineIdentityUserAssignedIdentitiesValue());

        VirtualMachineIdentity identity = new VirtualMachineIdentity();
        identity.withUserAssignedIdentities(userAssignedIdentities);

        VirtualMachineInner virtualMachine = new VirtualMachineInner();
        virtualMachine.withIdentity(identity);

        virtualMachine.withLicenseType("abs");

        String serialized = jacksonAdapter.serialize(virtualMachine);

        System.out.println(serialized);
    }
}
