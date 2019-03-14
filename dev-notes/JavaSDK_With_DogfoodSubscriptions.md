
### Using Java SDK with Dogfood subscriptions

If you have a dogfood subscription for ARM endpoint https://api-dogfood.resources.windows-int.net/ then first you will need to build an AzureEnvironment instance for dogfood:

```java
AzureEnvironment azure_canary = new AzureEnvironment(new HashMap<String, String>() {
    {
        this.put("managementEndpointUrl", "https://management.core.windows.net/");
        this.put("resourceManagerEndpointUrl", "https://api-dogfood.resources.windows-int.net/");
        this.put("activeDirectoryEndpointUrl", "https://login.windows-ppe.net/");
        this.put("activeDirectoryResourceId", "https://management.core.windows.net/");
        this.put("activeDirectoryGraphResourceId", "https://graph.ppe.windows.net/");
    }
});

```

Create a credential instance with above environment object and use it to initialize an instance of Azure client.

```
final String clientId = “<clientId>”;
final String tenantId = “<tenanId>”;
final String clientSecret = “<clientSecret>”;
final String subscriptionId = “<subscriptionId>”; // Canary specific subscription

ApplicationTokenCredentials credentials = new ApplicationTokenCredentials(clientId, tenantId, clientSecret, azure_canary);

```

Use a dogfood region when creating resources.

```java
String rgName = "<new-resource-group-name>";
String region = "<dog-food-region>";
azure.resourceGroups().define(rgName)
            .withRegion(region)
            .create();

```