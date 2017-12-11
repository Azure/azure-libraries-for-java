# Prepare for Azure Management Libraries for Java 1.5.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.4 to 1.5 ...

> If this note missed any breaking changes, please open a pull request.


V1.5 is backwards compatible with V1.4 in the APIs intended for public use that reached the general availability (stable) stage in V1.x. 

Some breaking changes were introduced in APIs that were still in Beta in V1.4, as indicated by the `@Beta` annotation.


## Renames/Removals

The following methods and/or types have been either renamed or removed in V1.5 compared to the previous release (V1.4):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.4</th>
    <th align=left>In V1.5</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
  <tr>
    <td><code>StorageAccount</code></td>
    <td><code>.withEncryption()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>withBlobEncryption()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/89">PR #89 </a></td>
  </tr>
  <tr>
    <td><code>VirtualMachine</code></td>
    <td><code>.managedServiceIdentityTenantId()</code></td>
    <td><code>.systemAssignedManagedServiceIdentityTenantId()</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachine</code></td>
    <td><code>.managedServiceIdentityPrincipalId()</code></td>
    <td><code>.systemAssignedManagedServiceIdentityPrincipalId()</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachine</code></td>
    <td><code>.withManagedServiceIdentity()</code></td>
    <td><code>.withSystemAssignedManagedServiceIdentity()</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachine</code></td>
    <td><code>.withRoleBasedAccessTo(scope, role)</code></td>
    <td><code>.withSystemAssignedIdentityBasedAccessTo(resourceId, role)</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachine</code></td>
    <td><code>.withRoleBasedAccessToCurrentResourceGroup(role)</code></td>
    <td><code>.withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(role)</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachine</code></td>
    <td><code>.withRoleDefinitionBasedAccessTo(scope, roleDefinitionId)</code></td>
    <td><code>.withSystemAssignedIdentityBasedAccessTo(resourceId, roleDefinitionId)</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachine</code></td>
    <td><code>.withRoleDefinitionBasedAccessToCurrentResourceGroup(roleDefinitionId)</code></td>
    <td><code>.withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(roleDefinitionId)</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachineScaleSet</code></td>
    <td><code>.managedServiceIdentityTenantId()</code></td>
    <td><code>.systemAssignedManagedServiceIdentityTenantId()</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachineScaleSet</code></td>
    <td><code>.managedServiceIdentityPrincipalId()</code></td>
    <td><code>.systemAssignedManagedServiceIdentityPrincipalId()</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachineScaleSet</code></td>
    <td><code>.withManagedServiceIdentity()</code></td>
    <td><code>.withSystemAssignedManagedServiceIdentity()</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachineScaleSet</code></td>
    <td><code>.withRoleBasedAccessTo(scope, role)</code></td>
    <td><code>.withSystemAssignedIdentityBasedAccessTo(resourceId, role)</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachineScaleSet</code></td>
    <td><code>.withRoleBasedAccessToCurrentResourceGroup(role)</code></td>
    <td><code>.withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(role)</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachineScaleSet</code></td>
    <td><code>.withRoleDefinitionBasedAccessTo(scope, roleDefinitionId)</code></td>
    <td><code>.withSystemAssignedIdentityBasedAccessTo(resourceId, roleDefinitionId)</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>VirtualMachineScaleSet</code></td>
    <td><code>.withRoleDefinitionBasedAccessToCurrentResourceGroup(roleDefinitionId)</code></td>
    <td><code>.withSystemAssignedIdentityBasedAccessToCurrentResourceGroup(roleDefinitionId)</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/83">PR #83 </a><br/><a href="https://github.com/Azure/azure-libraries-for-java/pull/88">PR #88</a></td>
  </tr>
  <tr>
    <td><code>WebAppBase</code></td>
    <td><code>.isPremiumApp()</code></td>
    <td><code>Removed</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/92">PR #92 </a></td>
  </tr>
  <tr>
    <td><code>WebAppBase</code></td>
    <td><code>.microService()</code></td>
    <td><code>Removed</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/92">PR #92 </a></td>
  </tr>
  <tr>
    <td><code>WebAppBase</code></td>
    <td><code>.gatewaySiteName()</code></td>
    <td><code>Removed</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/92">PR #92 </a></td>
  </tr>
</table>