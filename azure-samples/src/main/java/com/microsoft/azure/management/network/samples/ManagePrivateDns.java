/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.network.samples;

import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.azure.management.network.Network;
import com.microsoft.azure.management.privatedns.v2018_09_01.implementation.privatednsManager;
import com.microsoft.azure.management.privatedns.v2018_09_01.implementation.RecordSetInner;
import com.microsoft.azure.management.privatedns.v2018_09_01.ARecord;
import com.microsoft.azure.management.privatedns.v2018_09_01.RecordType;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.SubResource;
import com.microsoft.rest.LogLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ManagePrivateDns {
    /**
     * Main function which runs the actual sample.
     * @param azure instance of the azure client
     * @param prDnsManager instance of PrivateDns client
     * @return true if sample runs successfully
     */
    public static boolean runSample(Azure azure, privatednsManager prDnsManager) {
        final Region region = Region.US_EAST;
        final String rgName = SdkContext.randomResourceName("rg", 24);
        final String nwName = SdkContext.randomResourceName("nw", 24);
        final String regionDNS = "global";                      // location always 'global' for privateDNS
        final String privateDNSName = "private.contoso.com";    // private DNS name 

        try {
            Network network = azure.networks().define(nwName)
                    .withRegion(Region.US_EAST)
                    .withNewResourceGroup(rgName)
                    .withAddressSpace("10.0.0.0/27")
                    .withSubnet("subnet1", "10.0.0.0/27")
                    .create();
            //
            // Private DNS hybrid sample
            //
            prDnsManager.privateZones().define(privateDNSName)
                .withRegion(regionDNS)
                .withExistingResourceGroup(rgName)
                .withIfMatch(null)
                .withIfNoneMatch(null)
                .create();
            prDnsManager.virtualNetworkLinks().define("linkToVnet")
                    .withExistingPrivateDnsZone(rgName, privateDNSName)
                    .withIfMatch(null)
                    .withIfNoneMatch(null)
                    .withLocation(regionDNS)
                    .withRegistrationEnabled(true)
                    .withVirtualNetwork(new SubResource().withId(network.id()))
                    .create();
            RecordSetInner recordSetInner = new RecordSetInner();
            ARecord arecord = new ARecord();
            arecord.withIpv4Address("10.0.0.10"); // IP Address for record 
            List<ARecord> list = new ArrayList<ARecord>();
            list.add(arecord);
            recordSetInner.withARecords(list);
            recordSetInner.withTtl(1L);
            String alias = "db"; //alias for IP 
            prDnsManager.recordSets().inner().createOrUpdate(
                rgName, 
                privateDNSName, 
                RecordType.A, 
                alias, 
                recordSetInner);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Deleting Resource Group: " + rgName);
                azure.resourceGroups().beginDeleteByName(rgName);
            } catch (NullPointerException npe) {
                System.out.println("Did not create any resources in Azure. No clean up is necessary");
            } catch (Exception g) {
                g.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Main entry point.
     * @param args the parameters
     */
    public static void main(String[] args) {
        try {
            //=============================================================
            // Authenticate

            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));
            final ApplicationTokenCredentials cred = ApplicationTokenCredentials.fromFile(credFile);
            //
            Azure azure = Azure.configure()
                    .withLogLevel(LogLevel.BODY)
                    .authenticate(credFile)
                    .withDefaultSubscription();
            privatednsManager prDnsManager = privatednsManager.authenticate(cred, cred.defaultSubscriptionId());
            // Print selected subscription
            //
            System.out.println("Selected subscription: " + azure.subscriptionId());
            //
            runSample(azure, prDnsManager);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private ManagePrivateDns() {
    }
}
