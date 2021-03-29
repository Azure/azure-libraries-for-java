# Prepare for Azure Management Libraries for Java 1.41.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.40 to 1.41 ...

> If this note missed any breaking changes, please open a pull request.

V1.41 is backwards compatible with V1.40 in the APIs intended for public use that reached the general availability (stable) stage in V1.x.

Some breaking changes were introduced in APIs because update of the API service spec.


## Breaking changes

The following methods and/or types have been changed in V1.41 compared to the previous release (V1.40):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.40</th>
    <th align=left>In V1.41</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
    <tr>
      <td><code>Container Instance</code></td>
      <td><code>com.microsoft.azure.management.containerinstance.ContainerGroups</code></td>
      <td>Return type of method <code>listOperations</code> changed to <code>PagedList&lt;Operation&gt;</code>. Return type of method <code>listCachedImages</code> changed to <code>PagedList&lt;CachedImages&gt;</code>. Return type of method <code>listCapabilities</code> changed to <code>PagedList&lt;Capabilities&gt;</code>.</td>
      <td></td>
      <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/1381">#1381</a></td>
    </tr>
</table>
