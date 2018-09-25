# Prepare for Azure Management Libraries for Java 1.16.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.15 to 1.16 ...

> If this note missed any breaking changes, please open a pull request.


V1.16 is backwards compatible with V1.15 in the APIs intended for public use that reached the general availability (stable) stage in V1.x with a few exceptions in the ==XXXX== management library (though these changes will have minimal impact on the developer). 

Some breaking changes were introduced in APIs that were still in Beta in V1.16, as indicated by the `@Beta` annotation.


## Breaking changes

The following methods and/or types have been changed in V1.16 compared to the previous release (V1.15):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.15</th>
    <th align=left>In V1.16</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
  <tr>
    <td><code>GalleryImageVersion</code></td>
    <td><code>withRegionAvailability(Region region)</code></td>
    <td><code>withRegionAvailability(Region region, int replicaCount)</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-sdk-for-java/pull/587">PR #587</a></td>
  </tr>
  <tr>
    <td><code>GalleryImageVersion</code></td>
    <td><code>withRegionAvailability(List&lt;Region&gt; regions)</code></td>
    <td><code>withRegionAvailability(List&lt;TargetRegion&gt; regions)</code></td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-sdk-for-java/pull/587">PR #587</a></td>
  </tr>
</table>

# Drop Method Usage or Use Alternate #

<table>
  <tr>
    <th>Drop Method</th>
    <th>Use Alternate</th>
    <th>Ref</th>
  </tr>
  <tr>
    <td><code>GalleryImageVersion.scalerTier()</code></td>
    <td><code>NA (The concept of scaler tier is dropped)</code></td>
    <td><a href="https://github.com/Azure/azure-sdk-for-java/pull/587">#587</a></td>
  </tr>
    <tr>
      <td><code>GalleryImageVersion.withScaleTier(ScaleTier scaleTier)</code></td>
      <td><code>NA (The concept scaler tier is dropped)</code></td>
      <td><a href="https://github.com/Azure/azure-sdk-for-java/pull/587">#587</a></td>
    </tr>
</table>
