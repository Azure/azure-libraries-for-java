# Prepare for Azure Management Libraries for Java 1.10.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.10 to 1.11 ...

> If this note missed any breaking changes, please open a pull request.


V1.11 is backwards compatible with V1.10 in the APIs intended for public use that reached the general availability (stable) stage in V1.x with a few exceptions in the ==XXXX== management library (though these changes will have minimal impact on the developer). 

Some breaking changes were introduced in APIs that were still in Beta in V1.9, as indicated by the `@Beta` annotation.


## Breaking changes

The following methods and/or types have been changed in V1.11 compared to the previous release (V1.9):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.10</th>
    <th align=left>In V1.11</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
  <tr>
    <td><code>ContainerServiceVMSizeTypes</code></td>
    <td><code>STANDARD_A0, STANDARD_B1MS, STANDARD_B1S</code></td>
    <td><code>N/A</code></td>
    <td>These container service VM size types are removed but since it's an expandable enum these values can be manually instantiated.</code></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/460">PR #460</a></td>
  </tr>
  <tr>
    <td><code>CosmosDBAccount</code></td>
    <td><code>Observable<Void> regenerateKeyAsync(KeyKind keyKind);</code></td>
    <td><code>Completable regenerateKeyAsync(KeyKind keyKind);</code></td>
    <td>Return type is changed to Completable</code></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/457">PR #457</a></td>
  </tr>
</table>

