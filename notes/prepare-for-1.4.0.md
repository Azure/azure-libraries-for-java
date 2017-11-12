# Prepare for Azure Management Libraries for Java 1.4.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.3 to 1.4 ...

> If this note missed any breaking changes, please open a pull request.


V1.4 is backwards compatible with V1.3 in the APIs intended for public use that reached the general availability (stable) stage in V1.x. 

Some breaking changes were introduced in APIs that were still in Beta in V1.3, as indicated by the `@Beta` annotation.


## Renames/Removals

The following methods and/or types have been either renamed or removed in V1.4 compared to the previous release (V1.3):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.3</th>
    <th align=left>In V1.4</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
  <tr>
    <td><code>StorageAccount</code></td>
    <td><code>.withEncryption(Encryption)</code></td>
    <td><i>Removed</i></td>
    <td>Use <code>withEncryption()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-sdk-for-java/pull/1948">PR #1948</a></td>
  </tr>
  <tr>
    <td><code>??</code></td>
    <td><code>??</code></td>
    <td><code>??</code></td>
    <td>??</td>
    <td><a href="??">??</a></td>
  </tr>
</table>
