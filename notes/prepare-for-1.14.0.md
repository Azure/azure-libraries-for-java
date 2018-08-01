# Prepare for Azure Management Libraries for Java 1.14.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.13 to 1.14 ...

> If this note missed any breaking changes, please open a pull request.


V1.14 is backwards compatible with V1.13 in the APIs intended for public use that reached the general availability (stable) stage in V1.x.

## Deprecated API's ##

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.13</th>
    <th align=left>In V1.14</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>

  <tr>
    <td><code>Azure or manager configuration</code></td>
    <td><code>Azure.configure().withMaxIdleConnections(int)</code></td>
    <td><i>Deprecated</i></td>
    <td>Use `withConnectionPool(ConnectionPool)` instead. `withMaxIdelConnections` will reset the keep alive timeout.</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/520">PR #520 </a></td>
  </tr>
</table>
