# Prepare for Azure Management Libraries for Java 1.22.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.21 to 1.18 ...

> If this note missed any breaking changes, please open a pull request.


V1.18 is backwards compatible with V1.21 in the APIs intended for public use that reached the general availability (stable) stage in V1.x with a few exceptions in the ==XXXX== management library (though these changes will have minimal impact on the developer).

Some breaking changes were introduced in APIs that were still in Beta in V1.18, as indicated by the `@Beta` annotation.


## Breaking changes

The following methods and/or types have been changed in V1.18 compared to the previous release (V1.21):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.21</th>
    <th align=left>In V1.22</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
    <tr>
    <td align=left>Storage</td>
    <td align=left>List and ListAsync for StorageUsage</td>
    <td align=left>Removed</td>
    <td align=left></td>
    <td align=left></th>
  </tr>
</table>
