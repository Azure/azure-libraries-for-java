# Update Log

## Base framework 

 - Attachable.InDefinitionAlt, Attachable.InUpdateAlt: What're these two interface methods here.
 
 - AzureConfigurable interface methods are not clearly defined.
 
 - ReadableWrappersImpl: Do we really still need this class?
 
 - GroupPagedIterable: Need refine this class in the Fluent V2 usage scenarios.
 
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
  
  
  