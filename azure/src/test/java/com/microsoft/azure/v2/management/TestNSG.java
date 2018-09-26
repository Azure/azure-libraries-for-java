/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management;

import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.azure.v2.management.network.ApplicationSecurityGroup;
import com.microsoft.azure.v2.management.network.NetworkInterface;
import com.microsoft.azure.v2.management.network.NetworkSecurityGroup;
import com.microsoft.azure.v2.management.network.NetworkSecurityGroups;
import com.microsoft.azure.v2.management.network.NetworkSecurityRule;
import com.microsoft.azure.v2.management.network.SecurityRuleProtocol;
import com.microsoft.azure.v2.management.network.Subnet;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.Utils;

import java.util.List;

import io.reactivex.Observable;
import org.junit.Assert;

/**
 * Test for network security group CRUD.
 */
public class TestNSG extends TestTemplate<NetworkSecurityGroup, NetworkSecurityGroups> {
    @Override
    public NetworkSecurityGroup createResource(NetworkSecurityGroups nsgs) throws Exception {
        final String newName = "nsg" + this.testId;
        final String resourceGroupName = "rg" + this.testId;
        final String nicName = "nic" + this.testId;
        final String asgName = SdkContext.randomResourceName("asg", 8);
        final Region region = Region.US_WEST;
        final SettableFuture<NetworkSecurityGroup> nsgFuture = SettableFuture.create();

        ApplicationSecurityGroup asg = nsgs.manager().applicationSecurityGroups().define(asgName)
                .withRegion(region)
                .withNewResourceGroup(resourceGroupName)
                .create();
        // Create
        Observable<Indexable> resourceStream = nsgs.define(newName)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroupName)
                .defineRule("rule1")
                    .allowOutbound()
                    .fromAnyAddress()
                    .fromPort(80)
                    .toAnyAddress()
                    .toPort(80)
                    .withProtocol(SecurityRuleProtocol.TCP)
                    .attach()
                .defineRule("rule2")
                    .allowInbound()
                    .withSourceApplicationSecurityGroup(asg.id())
                    .fromAnyPort()
                    .toAnyAddress()
                    .toPortRange(22, 25)
                    .withAnyProtocol()
                    .withPriority(200)
                    .withDescription("foo!!")
                    .attach()
                .createAsync();

        Utils.<NetworkSecurityGroup>rootResource(resourceStream)
                .subscribe(networkSecurityGroup -> nsgFuture.set(networkSecurityGroup), throwable -> nsgFuture.setException(throwable));

        NetworkSecurityGroup nsg = nsgFuture.get();

        NetworkInterface nic = nsgs.manager().networkInterfaces().define(nicName)
                .withRegion(region)
                .withExistingResourceGroup(resourceGroupName)
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withExistingNetworkSecurityGroup(nsg)
                .create();

        nsg.refresh();

        // Verify
        Assert.assertTrue(nsg.region().equals(region));
        Assert.assertTrue(nsg.securityRules().size() == 2);

        // Confirm NIC association
        Assert.assertEquals(1, nsg.networkInterfaceIds().size());
        Assert.assertTrue(nsg.networkInterfaceIds().contains(nic.id()));

        Assert.assertEquals(1, nsg.securityRules().get("rule2").sourceApplicationSecurityGroupIds().size());
        Assert.assertEquals(asg.id(), nsg.securityRules().get("rule2").sourceApplicationSecurityGroupIds().iterator().next());

        return nsg;
    }

    @Override
    public NetworkSecurityGroup updateResource(NetworkSecurityGroup resource) throws Exception {
        resource = resource.update()
                .withoutRule("rule1")
                .withTag("tag1", "value1")
                .withTag("tag2", "value2")
                .defineRule("rule3")
                    .allowInbound()
                    .fromAnyAddress()
                    .fromAnyPort()
                    .toAnyAddress()
                    .toAnyPort()
                    .withProtocol(SecurityRuleProtocol.UDP)
                    .attach()
                .withoutRule("rule1")
                .updateRule("rule2")
                    .denyInbound()
                    .fromAddresses("100.0.0.0/29", "100.1.0.0/29")
                    .fromPortRanges("88-90")
                    .withPriority(300)
                    .withDescription("bar!!!")
                    .parent()
                .apply();
        Assert.assertTrue(resource.tags().containsKey("tag1"));
        Assert.assertTrue(resource.securityRules().get("rule2").sourceApplicationSecurityGroupIds().isEmpty());
        Assert.assertNull(resource.securityRules().get("rule2").sourceAddressPrefix());
        Assert.assertEquals(2, resource.securityRules().get("rule2").sourceAddressPrefixes().size());
        Assert.assertTrue(resource.securityRules().get("rule2").sourceAddressPrefixes().contains("100.1.0.0/29"));
        Assert.assertEquals(1, resource.securityRules().get("rule2").sourcePortRanges().size());
        Assert.assertEquals("88-90", resource.securityRules().get("rule2").sourcePortRanges().get(0));

        resource.updateTags()
                .withTag("tag3", "value3")
                .withoutTag("tag1")
                .applyTags();
        Assert.assertEquals("value3", resource.tags().get("tag3"));
        Assert.assertFalse(resource.tags().containsKey("tag1"));
        return resource;
    }

    private static StringBuilder printRule(NetworkSecurityRule rule, StringBuilder info) {
        info.append("\n\t\tRule: ").append(rule.name())
            .append("\n\t\t\tAccess: ").append(rule.access())
            .append("\n\t\t\tDirection: ").append(rule.direction())
            .append("\n\t\t\tFrom address: ").append(rule.sourceAddressPrefix())
            .append("\n\t\t\tFrom port range: ").append(rule.sourcePortRange())
            .append("\n\t\t\tTo address: ").append(rule.destinationAddressPrefix())
            .append("\n\t\t\tTo port: ").append(rule.destinationPortRange())
            .append("\n\t\t\tProtocol: ").append(rule.protocol())
            .append("\n\t\t\tPriority: ").append(rule.priority())
            .append("\n\t\t\tDescription: ").append(rule.description());
        return info;
    }

    public static void printNSG(NetworkSecurityGroup resource) {
        StringBuilder info = new StringBuilder();
        info.append("NSG: ").append(resource.id())
                .append("Name: ").append(resource.name())
                .append("\n\tResource group: ").append(resource.resourceGroupName())
                .append("\n\tRegion: ").append(resource.region())
                .append("\n\tTags: ").append(resource.tags());

        // Output security rules
        info.append("\n\tCustom security rules:");
        for (NetworkSecurityRule rule : resource.securityRules().values()) {
            info = printRule(rule, info);
        }

        // Output default security rules
        info.append("\n\tDefault security rules:");
        for (NetworkSecurityRule rule : resource.defaultSecurityRules().values()) {
            info = printRule(rule, info);
        }

        // Output associated NIC IDs
        info.append("\n\tNICs: ").append(resource.networkInterfaceIds());

        // Output associated subnets
        info.append("\n\tAssociated subnets: ");
        List<Subnet> subnets = resource.listAssociatedSubnets();
        if (subnets == null || subnets.size() == 0) {
            info.append("(None)");
        } else {
            for (Subnet subnet : subnets) {
                info.append("\n\t\tNetwork ID: ").append(subnet.parent().id())
                    .append("\n\t\tSubnet name: ").append(subnet.name());
            }
        }

        System.out.println(info.toString());
    }

    @Override
    public void print(NetworkSecurityGroup resource) {
        printNSG(resource);
    }
}
