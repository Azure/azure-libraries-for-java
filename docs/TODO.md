# Update Log

## Base framework - Design Consideration 

 - Attachable.InDefinitionAlt, Attachable.InUpdateAlt: What're these two interface methods here.
 
 - AzureConfigurable interface methods are not clearly defined.
 
 - ReadableWrappersImpl: Do we really still need this class?
 
 - GroupPagedIterable: Need refine this class in the Fluent V2 usage scenarios.
 
## Service Client Design consideration 
   
   - AzureEnvironment: How to play with this object
 
## ARM generator
 
  - The Deployment.java/ResourceGroup.java/ResourceGroups.java is overwritten by the fluent generator.
    * Fix by generate inner class into the models folder
    * `--add-inner Deployment,OtherClassName` to generate inner instead
  
  - ErrorResponseException: How to generate exception?
  
  - Componentsschemasidentitypropertiesuserassignedidentitiesadditionalproperties what's this? 
  
### FIXME
 
  - ProvidersImpl: What's the value for expand and top
  
  - ResourceGroupsImpl: checkExistence should return boolean value instead of void
  
  - QUESTION: beginDeleteByNameAsync - Does the method begin with beginXXX is deprecated? 
  
  - ResourceManagerClientImpl & SubscriptionClientImpl both have OperationsInner
  
 
 ## Storage 
  - StorageAccountsInner: Should support listing
  
  
 ## Network 
 - ErrorResponseException & ErrorException are generated under the models folder even I changed the folder name to `model` instead.
 - ComponentsSchemasManagedserviceidentityPropertiesUserassignedidentitiesAdditionalproperties???
 - ConnectionMonitor is not generated with Inner and override the default connection monitor interface
  - NatGatewaySku: The NatGateWaySkuName is not generated 
  
    ```JSON
        "NatGatewaySku": {
          "properties": {
            "name": {
              "type": "string",
              "description": "Name of Nat Gateway SKU.",
              "enum": [
                "Standard"
              ],
              "x-ms-enum": {
                "name": "NatGatewaySkuName",
                "modelAsString": true
              }
            }
          },
          "description": "SKU of nat gateway."
        }
    ```
  - TagsObject for AapplyTagsToInnerAsync???
