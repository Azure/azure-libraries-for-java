# Prepare for Azure Management Libraries for Java 1.31.1 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.31 to 1.31.1 ...

> If this note missed any breaking changes, please open a pull request.


V1.31.1 is backwards compatible with V1.31 in the APIs intended for public use that reached the general availability (stable) stage in V1.x.

Some breaking changes were introduced in APIs because update of the API service spec.


## Breaking changes

The following methods and/or types have been changed in V1.31.1 compared to the previous release (V1.31):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.31</th>
    <th align=left>In V1.31.1</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
    <tr>
      <td><code>Appservice</code></td>
      <td><code>com.microsoft.azure.management.appservice.RuntimeStack.WILDFLY_14_JRE8</code></td>
      <td>Removed. Refer to the blog in Remark for wildfly docker image.</td>
      <td><a href="https://azure.github.io/AppService/2020/01/31/Wildfly-on-App-Service.html">Run Wildfly on App Service
</a></td>
      <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/1050">#1050</a></td>
    </tr>
</table>

