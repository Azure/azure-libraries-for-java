# Prepare for Azure Management Libraries for Java 1.6.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.5 to 1.6 ...

> If this note missed any breaking changes, please open a pull request.


V1.6 is backwards compatible with V1.5 in the APIs intended for public use that reached the general availability (stable) stage in V1.x. 

Some breaking changes were introduced in APIs that were still in Beta in V1.5, as indicated by the `@Beta` annotation.


## Renames/Removals

The following methods and/or types have been either renamed or removed in V1.5 compared to the previous release (V1.4):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.5</th>
    <th align=left>In V1.6</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
  <tr>
    <td><code>Example - StorageAccount</code></td>
    <td><code>Example .withEncryption()</code></td>
    <td>Example - <i>Deprecated</i></td>
    <td>Example Use <code>withBlobEncryption()</code> instead</td>
    <td>Example - <a href="https://github.com/Azure/azure-libraries-for-java/pull/89">PR #89 </a></td>
  </tr>
</table>

## Changes in Input Parameters Order ##

<table>
  <tr>
    <th align=left>Area</th>
    <th align=left>Method</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
  <tr>
    <td>ContainerGroups</td>
    <td><code>getLogContent()</code></td>
    <td><code>Second argument <i>containerName</i> and third argument <i>containerGroupName</i> were swapped</code></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/85">#85</a></td>
  </tr>
  <tr>
    <td>ContainerGroups</td>
    <td><code>getLogContentAsync()</code></td>
    <td><code>Second argument <i>containerName</i> and third argument <i>containerGroupName</i> were swapped</code></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/85">#85</a></td>
  </tr>
</table>
