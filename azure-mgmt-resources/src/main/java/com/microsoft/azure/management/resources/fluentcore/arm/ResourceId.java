/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.arm;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import java.security.InvalidParameterException;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;

/**
 * Instantiate itself from a resource id, and give easy access to resource information like subscription, resourceGroup,
 * resource name.
 */
@LangDefinition
public final class ResourceId {

    private String subscriptionId = null;
    private String resourceGroupName = null;
    private String name = null;
    private String providerNamespace = null;
    private String resourceType = null;
    private String id = null;
    private String parentId = null;

    private static String badIdErrorText(String id) {
        return String.format("The specified ID `%s` is not a valid Azure resource ID.", id);
    }

    private ResourceId(final String id) {
        if (id == null) {
            // Protect against NPEs from null IDs, preserving legacy behavior for null IDs
            return;
        } else {
            // Skip the first '/' if any, and then split using '/'
            String[] splits = (id.startsWith("/")) ? id.substring(1).split("/") : id.split("/");

            // Save the ID itself
            this.id = id;

            // Format of id:
            // /subscriptions/<subscriptionId>/resourceGroups/<resourceGroupName>/providers/<providerNamespace>(/<parentResourceType>/<parentName>)*/<resourceType>/<name>
            //  0             1                2              3                   4         5                                                        N-2            N-1

            // Extract resource type and name
            if (splits.length < 2) {
                throw new InvalidParameterException(badIdErrorText(id));
            }

            for (int i = 0; i < splits.length && i < 6; i++) {
                switch (i) {
                case 0:
                    // Ensure "subscriptions"
                    if (!splits[i].equalsIgnoreCase("subscriptions")) {
                        throw new InvalidParameterException(badIdErrorText(id));
                    }
                    break;
                case 1:
                    // Extract subscription ID
                    this.subscriptionId = splits[i];
                    break;
                case 2:
                    // Ensure "resourceGroups"
                    if (!splits[i].equalsIgnoreCase("resourceGroups")) {
                        throw new InvalidParameterException(badIdErrorText(id));
                    }
                    break;
                case 3:
                    // Extract resource group name
                    this.resourceGroupName = splits[i];
                    break;
                case 4:
                    // Ensure "providers"
                    if (!splits[i].equalsIgnoreCase("providers")) {
                        throw new InvalidParameterException(badIdErrorText(id));
                    }
                    break;
                case 5:
                    // Extract provider namespace
                    this.providerNamespace = splits[i];
                    break;
                default:
                    break;
                }
            }

            parseProviderSpecificResourceId(providerNamespace, splits);
        }
    }

    /**
     * This is a workaround to allow resource names embedded in the resourceId to contain forward slashes.
     * Forward slashes should be updateResourceFields not permitted in ResourceId 'names' but it seems someone managed to do it
     * in ServiceBus Queues and it breaks the ResourceId parsing code.
     * The code looks for the "queues" keyword (resourceType) and assumes everything before that is the
     * parentId and everything after is the resource "name".
     * It only works for Microsoft.ServiceBus and for Queues since there is only one level of hierarchy
     * and we haven't run into problems with Topics.
     * Example:
     *   ResourceId: /subscriptions/aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee/resourceGroups/Default-ServiceBus-EastUS/providers/Microsoft.ServiceBus/namespaces/updateResourceFields/queues/some/queue/name
     *   -> parentId : /subscriptions/aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee/resourceGroups/Default-ServiceBus-EastUS/providers/Microsoft.ServiceBus/namespaces/updateResourceFields
     *   -> resource name : some/queue/name
     * Example:
     *   ResourceId: /subscriptions/aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee/resourceGroups/Default-ServiceBus-EastUS/providers/Microsoft.ServiceBus/namespaces/updateResourceFields/topics/some/topic
     *   -> parentId : /subscriptions/aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee/resourceGroups/Default-ServiceBus-EastUS/providers/Microsoft.ServiceBus/namespaces/updateResourceFields
     *   -> resource name : some/topic
     * Example:
     *   ResourceId: /subscriptions/aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee/resourceGroups/Default-ServiceBus-EastUS/providers/Microsoft.ServiceBus/namespaces/updateResourceFields/topics/someTopic/subscriptions/some/subscription
     *   -> parentId : /subscriptions/aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee/resourceGroups/Default-ServiceBus-EastUS/providers/Microsoft.ServiceBus/namespaces/updateResourceFields/topics/someTopic
     *   -> resource name : some/subscription
     */
    private void parseProviderSpecificResourceId(String providerNamespace, String[] splits) {
        if (providerNamespace.equalsIgnoreCase("microsoft.servicebus")) {
            // find at which index we find queues -> after, it's all name, before is parent
            int queuesIndex = Arrays.asList(splits).lastIndexOf("queues");
            if (queuesIndex > 0) {
                updateResourceFields(splits, queuesIndex);
                return;
            }
            // find at which index we find topics -> then check if there are subscriptions
            int topicsIndex = Arrays.asList(splits).lastIndexOf("topics");
            if (topicsIndex > 0) {
                int subscriptionsIndex = Arrays.asList(splits).lastIndexOf("subscriptions");
                //we didn't find subscriptions, or this is the root, assume everything after topics is the name
                if (subscriptionsIndex <= 0) {
                    updateResourceFields(splits, topicsIndex);
                } else {
                    updateResourceFields(splits, subscriptionsIndex);
                }
            }
        }

        this.name = splits[splits.length - 1];
        this.resourceType = splits[splits.length - 2];

        // Extract parent ID
        if (splits.length < 10) {
            this.parentId = null;
        } else {
            String[] parentSplits = new String[splits.length - 2];
            System.arraycopy(splits, 0, parentSplits, 0, splits.length - 2);
            this.parentId = "/" + StringUtils.join(parentSplits, "/");
        }
    }

    private void updateResourceFields(String[] splits, int queuesIndex) {
        this.name = StringUtils.join(Arrays.copyOfRange(splits, queuesIndex + 1, splits.length), "/");
        this.parentId = "/" + StringUtils.join(Arrays.copyOfRange(splits, 0, queuesIndex), "/");
        this.resourceType = splits[queuesIndex];
    }

    /**
     * Returns parsed ResourceId object for a given resource id.
     * @param id of the resource
     * @return ResourceId object
     */
    public static ResourceId fromString(String id) {
        return new ResourceId(id);
    }

    /**
     * @return subscriptionId of the resource.
     */
    public String subscriptionId() {
        return this.subscriptionId;
    }

    /**
     * @return resourceGroupName of the resource.
     */
    public String resourceGroupName() {
        return this.resourceGroupName;
    }

    /**
     * @return name of the resource.
     */
    public String name() {
        return this.name;
    }

    /**
     * @return parent resource id of the resource if any, otherwise null.
     */
    public ResourceId parent() {
        if (this.id == null || this.parentId == null) {
            return null;
        } else {
            return fromString(this.parentId);
        }
    }

    /**
     * @return name of the provider.
     */
    public String providerNamespace() {
        return this.providerNamespace;
    }

    /**
     * @return type of the resource.
     */
    public String resourceType() {
        return this.resourceType;
    }

    /**
     * @return full type of the resource.
     */
    public String fullResourceType() {
        if (this.parentId == null) {
            return this.providerNamespace + "/" + this.resourceType;
        } else {
            return this.parent().fullResourceType() + "/" + this.resourceType;
        }
    }

    /**
     * @return the id of the resource.
     */
    public String id() {
        return id;
    }
}
