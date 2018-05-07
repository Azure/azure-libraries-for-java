# Prepare for Azure Management Libraries for Java 1.10.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.9 to 1.10 ...

> If this note missed any breaking changes, please open a pull request.


V1.10 is backwards compatible with V1.9 in the APIs intended for public use that reached the general availability (stable) stage in V1.x with a few exceptions in the ==XXXX== management library (though these changes will have minimal impact on the developer). 

Some breaking changes were introduced in APIs that were still in Beta in V1.9, as indicated by the `@Beta` annotation.


## Breaking changes

The following methods and/or types have been changed in V1.10 compared to the previous release (V1.9):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.9</th>
    <th align=left>In V1.10</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
  <tr>
    <td><code>BatchAICluster</code></td>
    <td><code>.jobs()</code></td>
    <td><code>Azure.batchAIJobs()</code></td>
    <td>Changed entry point for Batch AI jobs management</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/434">PR #434</a></td>
  </tr>             
</table>

