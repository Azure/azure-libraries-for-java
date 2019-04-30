# Prepare for Azure Management Libraries for Java 1.7.0 #

Steps to migrate code that uses Azure Management Libraries for Java from 1.6 to 1.7 ...

> If this note missed any breaking changes, please open a pull request.


V1.7 is backwards compatible with V1.6 in the APIs intended for public use that reached the general availability (stable) stage in V1.x with a few exceptions in the SQL management library (though these changes will have minimal impact on the developer). 

Some breaking changes were introduced in APIs that were still in Beta in V1.6, as indicated by the `@Beta` annotation.


## Breaking changes

The following methods and/or types have been changed in V1.7 compared to the previous release (V1.6):

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.6</th>
    <th align=left>In V1.7</th>
    <th align=left>Remarks</th>
    <th align=left>Ref</th>
  </tr>
  <tr>
    <td><code>SqlServer</code></td>
    <td><code>.version()</code></td>
    <td>returns String</td>
    <td>Previously it returned ServerVersion which contained fixed set of values</td>
    <td><a href="https://github.com/Azure/azure-libraries-for-java/issues/219">Issue #219 </a></td>
  </tr>
</table>


## Deprecated API's ##

<table>
  <tr>
    <th align=left>Area/Model</th>
    <th align=left>In V1.6</th>
    <th align=left>In V1.7</th>
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

