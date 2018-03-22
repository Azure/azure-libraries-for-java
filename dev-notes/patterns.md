## Pattern: For entry points (accessor) to Azure resources those are not exposed by the Azure roll-up client

The rollup Azure client SHOULD expose only entry points (accessors) to most important Azure resources. Such accessors are known as `Top-level accessors`.

This raise a question that how do we allow user to access entry points those are not exposed by the rollup Azure client. In most of the cases the entry points we decide not to put under rollup clients are accessor to child resources (there some exceptions though).

Below shows the pattern that SHOULD be followed inorder to expose such child resources accessors.

```java
Children children = azure.parents().children();
```

example:

```java
azure.sqlServers()
      .sqlDatabases();
```

```java
azure.eventHubNamespaces()
        .eventHubs()
        .authorizationRules();
```

Such an accessor is known in general as `Nested-children accessors`. Reason for explicitly using the word `children` is to distingush it from any other type 
of nested accessor those are not necessarly children.

## Pattern: For defining resource through `Nested-children accessors`

The general pattern for the structure of child definition is:

```java
parents()
    .children()
        .define(<name>)
            .withExistingParent(Parent p) | withExistingParent(string, string..) | withExistingParentId(string id)
            .withChildSpecificProperty1(..)
            .withChildSpecificProperty2(..)
            .create();
```

The `define()` on the child MUST followed by a `ParentRef` stage.  `ParentRef` stage provide ways to refer the parent (explained later). Any withers for setting the properties of the child  MUST appear only after this stage.

The `ParentRef` MUST expose 3 ways of referencing the parent 

1. Using an instance of parent
2. Using a set of string argument that uniquely identifies the parent
3. Using id of the parent

All three method names MUST follow the naming convention - (1) It MUST start with `withExisting`. (2) The next part of the method name MUST be the singular form of the parent collection that is IMMEDIATELY above children accessor. (3) The method that takes id MUST have a last part of the name as `Id`.

For example:

```java
azure.sqlServers()
   .sqlDatabases()
      .define(<db-name>)
         .withExistingSqlServer(SqlServer s) | withExistingSqlServer(string rgname, string servName) | withExistingSqlServerId(string id)
         .withEdition(..)
         .create();
```

```java
azure.eventHubNamespaces()
    .eventHubs()
        .authorizationRules()
           .define(<rule-name>)
           .withExistingEventHub(EventHub s) | withExistingEventHub(string rgname, string nsName, string hubName) | withExistingEventHubId(string id)
           .withSendAccess()
           .create()
```

An exception to the above strict pattern is - there are cases where the children is more important than the parent. ONLY for such cases, along with `.withExisting**` methods, the `ParentRef` stage is allowed to expose `withNewParent` method that takes `Creatable<Parent>`. In-order to avoid complexities involved in specifying the various combination of region vs existing resource group vs new resource group, there SHOULD NOT be any other overload of `withNewParent`.




