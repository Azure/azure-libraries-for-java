# Prepare for Azure Management Libraries for Java 1.12.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.11 to 1.12 ...

> If this note missed any breaking changes, please open a pull request.


V1.12 is backwards compatible with V1.11 in the APIs intended for public use that reached the general availability (stable) stage in V1.x with a few exceptions in the ==XXXX== management library (though these changes will have minimal impact on the developer). 

Some breaking changes were introduced in APIs that were still in Beta in V1.11, as indicated by the `@Beta` annotation.


## Breaking changes

The following methods and/or types have been changed in V1.12 compared to the previous release (V1.11):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.11</th>
    <th align=left>In V1.12</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
  <tr>
    <td><code>BatchAI</code></td>
    <td><code>N/A</code></td>
    <td><code>BatchAIWorkspace.clusters().getByName(String name)</code><br/>
    <code>BatchAIWorkspace.fileServers().getByName(String name)</code><br/>
    <code>BatchAIWorkspace.experiments().getByName(String name)</code><br/>
    <code>BatchAIExperiment.jobs().define(String name)</code><br/></td>
    <td>BatchAIWorkspace was introduced as a top-level resource. BatchAIFileServer, BatchAICluster and BatchAIExperiment now are associated with a workspace. All BatchAIJobs are grouped by BatchAIExperiment.</code></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/452">PR #452</a></td>
  </tr>
    <tr>
      <td><code>BatchAI</code></td>
      <td><code>String unit();</code></td>
      <td><code>UsageUnit unit();</code></td>
      <td>Return type changed from <code>String</code> to <code>UsageUnit</code></td>
      <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/452">PR #452</a></td>
    </tr>
</table>

