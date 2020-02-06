# Prepare for Azure Management Libraries for Java 1.31.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.30 to 1.31 ...

> If this note missed any breaking changes, please open a pull request.


V1.31 is backwards compatible with V1.30 in the APIs intended for public use that reached the general availability (stable) stage in V1.x.

Some breaking changes were introduced in APIs because update of the API service spec.


## Breaking changes

The following methods and/or types have been changed in V1.31 compared to the previous release (V1.30):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.30</th>
    <th align=left>In V1.31</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
    <tr>
      <td><code>Network</code></td>
      <td><code>String ApplicationSecurityGroup#provisioningState();</code></td>
      <td><code>ProvisioningState ApplicationSecurityGroup#provisioningState();;</code></td>
      <td>Return type changed from <code>String</code> to <code>ProvisioningState</code></td>
      <td></td>
    </tr>
    <tr>
      <td><code>Network</code></td>
      <td><code>String DdosProtectionPlan#provisioningState();</code></td>
      <td><code>ProvisioningState DdosProtectionPlan#provisioningState();;</code></td>
      <td>Return type changed from <code>String</code> to <code>ProvisioningState</code></td>
      <td></td>
    </tr>
    <tr>
      <td><code>Network</code></td>
      <td><code>String ExpressRouteCircuit#provisioningState();</code></td>
      <td><code>ProvisioningState ExpressRouteCircuit#provisioningState();;</code></td>
      <td>Return type changed from <code>String</code> to <code>ProvisioningState</code></td>
      <td></td>
    </tr>
    <tr>
      <td><code>Network</code></td>
      <td><code>String ExpressRouteCircuitPeering#provisioningState();</code></td>
      <td><code>ProvisioningState ExpressRouteCircuitPeering#provisioningState();;</code></td>
      <td>Return type changed from <code>String</code> to <code>ProvisioningState</code></td>
      <td></td>
    </tr>
    <tr>
      <td><code>Network</code></td>
      <td><code>String ExpressRouteCrossConnection#provisioningState();</code></td>
      <td><code>ProvisioningState ExpressRouteCrossConnection#provisioningState();;</code></td>
      <td>Return type changed from <code>String</code> to <code>ProvisioningState</code></td>
      <td></td>
    </tr>
    <tr>
      <td><code>Network</code></td>
      <td><code>String ExpressRouteCrossConnectionPeering#provisioningState();</code></td>
      <td><code>ProvisioningState ExpressRouteCrossConnectionPeering#provisioningState();;</code></td>
      <td>Return type changed from <code>String</code> to <code>ProvisioningState</code></td>
      <td></td>
    </tr>
    <tr>
      <td><code>Network</code></td>
      <td><code>String LocalNetworkGateway#provisioningState();</code></td>
      <td><code>ProvisioningState LocalNetworkGateway#provisioningState();;</code></td>
      <td>Return type changed from <code>String</code> to <code>ProvisioningState</code></td>
      <td></td>
    </tr>
    <tr>
      <td><code>Network</code></td>
      <td><code>String RouteFilter#provisioningState();</code></td>
      <td><code>ProvisioningState RouteFilter#provisioningState();;</code></td>
      <td>Return type changed from <code>String</code> to <code>ProvisioningState</code></td>
      <td></td>
    </tr>
    <tr>
      <td><code>Network</code></td>
      <td><code>String RouteFilterRule#provisioningState();</code></td>
      <td><code>ProvisioningState RouteFilterRule#provisioningState();;</code></td>
      <td>Return type changed from <code>String</code> to <code>ProvisioningState</code></td>
      <td></td>
    </tr>
    <tr>
      <td><code>Network</code></td>
      <td><code>String VirtualNetworkGatewayConnection#provisioningState();</code></td>
      <td><code>ProvisioningState VirtualNetworkGatewayConnection#provisioningState();;</code></td>
      <td>Return type changed from <code>String</code> to <code>ProvisioningState</code></td>
      <td></td>
    </tr>
</table>

