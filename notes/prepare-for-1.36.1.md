# Prepare for Azure Management Libraries for Java 1.36.1 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.36 to 1.36.1 ...

> If this note missed any breaking changes, please open a pull request.

V1.36.1 is backwards compatible with V1.36 in the APIs intended for public use that reached the general availability (stable) stage in V1.x.

Some breaking changes were introduced in APIs because update of the API service spec.


## Breaking changes

The following methods and/or types have been changed in V1.36.1 compared to the previous release (V1.36.0):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.36</th>
    <th align=left>In V1.36.1</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
  <tr>
    <td><code>KnownLinuxVirtualMachineImage</code></td>
    <td><code>.withPopularLunixImage(KnownLinuxVirtualMachineImage.DEBIAN_8)</code></td>
    <td><code>.withLatestLinuxImage("credativ", "Debian", "8")</code></td>
    <td>Similar to other removed linux images</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/1249">#1249</a></td>
  </tr>
  <tr>
    <td><code>KnownWindowsVirtualMachineImage</code></td>
    <td><code>.withPopularWindowsImage(KnownWindowsVirtualMachineImage.WINDOWS_SERVER_2008_R2_SP1)</code></td>
    <td><code>.withLatestWindowsImage("MicrosoftWindowsServer", "WindowsServer", "2008-R2-SP1")</code></td>
    <td>Similar to other removed windows images</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/1249">#1249</a></td>
  </tr>  
</table>

