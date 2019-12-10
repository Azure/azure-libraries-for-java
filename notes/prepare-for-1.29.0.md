# Prepare for Azure Management Libraries for Java 1.29.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.28 to 1.29 ...

> If this note missed any breaking changes, please open a pull request.

V1.29 is backwards compatible with V1.28 in the APIs intended for public use that reached the general availability (stable) stage in V1.x with a few exceptions in the ==XXXX== management library (though these changes will have minimal impact on the developer).

Some breaking changes were introduced in APIs that were still in Beta in V1.29, as indicated by the `@Beta` annotation.

## Breaking changes

The following methods and/or types have been changed in V1.18 compared to the previous release (V1.17):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.28</th>
    <th align=left>In V1.29</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
    <tr>
    <td align=left>Disk</td>
    <td align=left>fromVhd</td>
    <td align=left>fromVhd.withStorageAccount</td>
    <td align=left></td>
    <td align=left><a href="https://github.com/Azure/azure-libraries-for-java/pull/908/commits/4561e32b364b1cc77fbf9598e4a10dc27caea323">PR #908</th>
  </tr>
</table>