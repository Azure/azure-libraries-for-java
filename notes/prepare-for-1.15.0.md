# Prepare for Azure Management Libraries for Java 1.15.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.14 to 1.15 ...

> If this note missed any breaking changes, please open a pull request.


V1.15 is backwards compatible with V1.14 in the APIs intended for public use that reached the general availability (stable) stage in V1.x with a few exceptions in the ==XXXX== management library (though these changes will have minimal impact on the developer). 

Some breaking changes were introduced in APIs that were still in Beta in V1.14, as indicated by the `@Beta` annotation.


## Breaking changes

The following methods and/or types have been changed in V1.15 compared to the previous release (V1.14):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.14</th>
    <th align=left>In V1.15</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
  <tr>
    <td><code>Azure Container Services</code></td>
    <td><code>withVirtualMachineCount()</code></td>
    <td><code>withAgentPoolVirtualMachineCount()</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/542"></a></td>
  </tr>
  <tr>
    <td><code>Azure Container Services</code></td>
    <td><code>withKeyVaultSecret()</code></td>
    <td><code>N/A</code></td>
    <td>Removed because the service does not expose it</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/542"></a></td>
  </tr>
  <tr>
    <td><code>Azure Container Services</code></td>
    <td><code>defineAgentPool()</code></td>
    <td></td>
    <td>Re-ordered the definition flow to start with <code>withVirtualMachineSize()</code></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/542"></a></td>
  </tr>
</table>

