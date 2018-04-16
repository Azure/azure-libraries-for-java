# Prepare for Azure Management Libraries for Java 1.9.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.8 to 1.9 ...

> If this note missed any breaking changes, please open a pull request.


V1.9 is backwards compatible with V1.8 in the APIs intended for public use that reached the general availability (stable) stage in V1.x with a few exceptions in the ==XXXX== management library (though these changes will have minimal impact on the developer). 

Some breaking changes were introduced in APIs that were still in Beta in V1.8, as indicated by the `@Beta` annotation.


## Breaking changes

The following methods and/or types have been changed in V1.9 compared to the previous release (V1.8):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.8</th>
    <th align=left>In V1.9</th>
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
</table>


## Deprecated API's ##

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.8</th>
    <th align=left>In V1.9</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
  <tr>
    <td><code>SqlServer</code></td>
    <td><code>.listUsages()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.listUsageMetrics()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlServer</code></td>
    <td><code>.withNewElasticPool()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.defineElasticPool()</code> or <code>.elasticPools().define()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlServer</code></td>
    <td><code>.withNewDatabase()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.defineDatabase()</code> or <code>.databases().define()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlServer</code></td>
    <td><code>.withNewFirewallRule()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.defineFirewallRule()</code> or <code>.firewallRules().define()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlServer</code></td>
    <td><code>.withoutElasticPool()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>SqlElasticPool.delete()</code> or <code>.elasticPools().delete()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlServer</code></td>
    <td><code>.withoutDatabase()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>SqlDatabase.delete()</code> or <code>.databases().delete()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlServer</code></td>
    <td><code>.withoutFirewallRule()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>SqlFirewallRule.delete()</code> or <code>.firewallRules().delete()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>

  <tr>
    <td><code>SqlDatabase</code></td>
    <td><code>.getUpgradeHint()</code></td>
    <td><i>Deprecated</i></td>
    <td>The service has discontinued this API (it returns null)</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlDatabase</code></td>
    <td><code>.listUsages()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.listMetrics()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlDatabase</code></td>
    <td><code>.withEdition()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.withBasicEdition()</code> or <code>.withStandardEdition()</code> or <code>.withPremiumEdition()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlDatabase</code></td>
    <td><code>.withMaxSizeBytes()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.withBasicEdition()</code> or <code>.withStandardEdition()</code> or <code>.withPremiumEdition()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlDatabase</code></td>
    <td><code>.withServiceObjective()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.withBasicEdition()</code> or <code>.withStandardEdition()</code> or <code>.withPremiumEdition()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlElasticPool</code></td>
    <td><code>.storageMB()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.storageCapacityInMB()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlElasticPool</code></td>
    <td><code>.withEdition()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.withBasicEdition()</code> or <code>.withStandardEdition()</code> or <code>.withPremiumEdition()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlElasticPool</code></td>
    <td><code>.withDtu()</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.withReservedDtu()</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlElasticPool</code></td>
    <td><code>.withDatabaseDtuMin(int)</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.withDatabaseDtuMin(SqlElasticPoolBasicMinEDTUs | SqlElasticPoolStandardMinEDTUs | SqlElasticPoolPremiumMinEDTUs)</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlElasticPool</code></td>
    <td><code>.withDatabaseDtuMax(int)</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.withDatabaseDtuMax(SqlElasticPoolBasicMaxEDTUs | SqlElasticPoolStandardMaxEDTUs | SqlElasticPoolPremiumMaxEDTUs)</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  <tr>
    <td><code>SqlElasticPool</code></td>
    <td><code>.withStorageCapacity(int)</code></td>
    <td><i>Deprecated</i></td>
    <td>Use <code>.withStorageCapacity(SqlElasticPoolStandardStorage | SqlElasticPoolPremiumSorage)</code> instead</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/pull/196">PR #196 </a></td>
  </tr>
  
</table>

