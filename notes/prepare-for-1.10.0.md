# Prepare for Azure Management Libraries for Java 1.10.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.9 to 1.10 ...

> If this note missed any breaking changes, please open a pull request.


V1.9 is backwards compatible with V1.8 in the APIs intended for public use that reached the general availability (stable) stage in V1.x with a few exceptions in the ==XXXX== management library (though these changes will have minimal impact on the developer). 

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
    <td><code>NetworkWatcher</code></td>
    <td><code>.getTopology(String targetResourceGroup)</code></td>
    <td><code>.topology().withTargetResourceGroup(String resourceGroupName).withTargetNetwork(String networkId).withTargetSubnet(String subnetName).execute()</code> where <code>withTargetNetwork()</code> and <code>withTargetSubnet()</code> are optional</td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/394">PR #394 </a></td>
  </tr>
  <tr>
    <td><code>NetworkWatcher</code></td>
    <td><code>.getTopologyAsync(String targetResourceGroup)</code></td>
    <td><code>.topology().withTargetResourceGroup(String resourceGroupName).withTargetNetwork(String networkId).withTargetSubnet(String subnetName).executeAsync()</code> where <code>withTargetNetwork()</code> and <code>withTargetSubnet()</code> are optional</td>
    <td></td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/394">PR #394 </a></td>
  </tr>
  <tr>
    <td><code>VerificationIPFlow</code></td>
    <td><code>.withProtocol(Protocol protocol)</code></td>
    <td><code>.withProtocol(IpFlowProtocol)</code></td>
    <td>Updated to the latest swagger specs</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/394">PR #394 </a></td>
  </tr>
  <tr>
    <td><code>ExpressRouteCircuit</code></td>
    <td><code>ExpressRouteCircuitPeeringType peeringType();</code></td>
    <td><code>ExpressRoutePeeringType peeringType();</code></td>
    <td>Return type changed.</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/394">PR #394 </a></td>
  </tr>
  <tr>
    <td><code>ExpressRouteCircuit</code></td>
    <td><code>ExpressRouteCircuitPeeringState state();</code></td>
    <td><code>ExpressRoutePeeringState state();</code></td>
    <td>Return type changed.</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/394">PR #394 </a></td>
  </tr>
  <tr>
    <td><code>ExpressRouteCircuit</code></td>
    <td><code>int peerAsn();</code></td>
    <td><code>long peerAsn();</code></td>
    <td>Return type changed.</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/394">PR #394 </a></td>
  </tr>                
</table>


## Deprecated API's ##

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.9</th>
    <th align=left>In V1.10</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>

  <tr>
    <td><code>Redis Cache</code></td>
    <td><code>Update.withSubnet(HasId networkResource, String subnetName)</code></td>
    <td><i>Deprecated</i></td>
    <td>Subnet configuration cannot be changed on existing Redis Cache instance.</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/391">PR #391 </a></td>
  </tr>
  <tr>
    <td><code>Redis Cache</code></td>
    <td><code>Update.withStaticIP(String staticIP)</code></td>
    <td><i>Deprecated</i></td>
    <td>Static IP configuration cannot be changed on existing Redis Cache instance.</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/391">PR #391 </a></td>
  </tr>
</table>

