
### Using Java SDK with Canary enabled subscriptions


There are two canary regions:

```
Central US EUAP
East US 2 EUAP
```

You will need a subscription whitelisted to be able to work with Canary regions.

Create credentials for initializing an azure client instance. You can use the following sample for guidance :

```java
final String clientId = “<clientId>”;
final String tenantId = “<tenanId>”;
final String clientSecret = “<clientSecret>”;
final String subscriptionId = “<subscriptionId>”; // The azure subscription whitelisted for Canary

ApplicationTokenCredentials credentials = new ApplicationTokenCredentials(clientId, tenantId, clientSecret, AzureEnvironment.AZURE);

Azure azure = Azure.configure()
        .withLogLevel(LogLevel.BODY_AND_HEADERS)
        .authenticate(credentials)
        .withSubscription(subscriptionId);

```

Use a canary region when creating resources.

```java
String rgName = "<new-resource-group-name>";
        
azure.resourceGroups().define(rgName)
            .withRegion("Central US EUAP") // Canary region as string
            .create();

```

You can also pass a strongly typed region:

```java
String rgName = "<new-resource-group-name>";
Region canaryRegion = Region.create("Central US EUAP", "Central US EUAP");

azure.resourceGroups().define(rgName)
            .withRegion(canaryRegion) // Canary region
            .create();
```
