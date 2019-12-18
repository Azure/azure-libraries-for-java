# Prepare for Azure Management Libraries for Java 1.26.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.25 to 1.26 ...

> If this note missed any breaking changes, please open a pull request.

V1.26 is backwards compatible with V1.25 in the APIs intended for public use that reached the general availability (stable) stage in V1.x with a few exceptions in the ==XXXX== management library (though these changes will have minimal impact on the developer).

Some breaking changes were introduced in APIs that were still in Beta in V1.26, as indicated by the <code>@Beta</code> annotation.

## Drop Method Usage or Use Alternate

<table>
  <tr>
    <th>Drop Method</th>
    <th>Use Alternate</th>
    <th>Ref</th>
  </tr>
  <tr>
    <td><code>ManagedClusterAgentPoolProfile.storageProfile()</code></td>
    <td><code>NA</code></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/824">#824</a></td>
  </tr>
</table>
