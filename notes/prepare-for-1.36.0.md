# Prepare for Azure Management Libraries for Java 1.36.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.35.1 to 1.36 ...

> If this note missed any breaking changes, please open a pull request.

V1.36 is backwards compatible with V1.35.1 in the APIs intended for public use that reached the general availability (stable) stage in V1.x.

Some breaking changes were introduced in APIs because update of the API service spec.


## Breaking changes

The following methods and/or types have been changed in V1.36 compared to the previous release (V1.35.1):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.35.1</th>
    <th align=left>In V1.36</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
    <tr>
      <td><code>Resource</code></td>
      <td><code>Object Deployment#template()</code></td>
      <td><code>String Deployment#templateHash()</code></td>
      <td></td>
      <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/1237">#1237</a></td>
    </tr>
</table>

