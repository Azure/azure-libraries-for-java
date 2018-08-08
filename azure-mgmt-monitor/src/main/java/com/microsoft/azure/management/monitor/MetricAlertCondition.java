/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;

/**
 * An immutable client-side representation of an Azure metric alert criteria.
 */
@Fluent
public interface MetricAlertCondition /*extends
        ExternalChildResource<CdnEndpoint, CdnProfile>,
        HasInner<EndpointInner>*/ {

    interface DefinitionStages {
        interface Blank {
            interface MetricName<ParentT> {
                //DefinitionStages.WithStandardAttach<ParentT> withMetricName(String originName, String originHostName);
                WithCriteriaOperator<ParentT> withSignalName(String metricName);
            }

            interface WithCriteriaOperator<ParentT> {
                WithConditionAttach<ParentT> withCondition(MetricAlertRuleCondition condition, MetricAlertRuleTimeAggregation timeAggregation, double threshold);
            }

            interface WithConditionAttach<ParentT>
                    extends AttachableCondition<ParentT> {

                WithConditionAttach<ParentT> withMetricNamespace(String metricNamespace);

                WithConditionAttach<ParentT> withDimensionFilter(String dimensionName, String... values);
            }

            interface AttachableCondition<ParentT> {
                @Method
                ParentT attach();
            }
        }
    }

    interface UpdateDefinitionStages {
        interface Blank {
            interface MetricName<ParentT> {
                //DefinitionStages.WithStandardAttach<ParentT> withMetricName(String originName, String originHostName);
                WithCriteriaOperator<ParentT> withSignalName(String metricName);
            }

            interface WithCriteriaOperator<ParentT> {
                WithConditionAttach<ParentT> withCondition(MetricAlertRuleCondition operator, MetricAlertRuleTimeAggregation timeAggregation, double threshold);
            }

            interface WithConditionAttach<ParentT>
                    extends AttachableCondition<ParentT> {

                WithConditionAttach<ParentT> withMetricNamespace(String metricNamespace);

                WithConditionAttach<ParentT> withDimensionFilter(String dimensionName, String... values);
            }

            interface AttachableCondition<ParentT> {
                @Method
                ParentT attach();
            }
        }
    }

    interface UpdateStages extends Update {

        UpdateStages withCondition(MetricAlertRuleCondition operator, MetricAlertRuleTimeAggregation timeAggregation, double threshold);

        UpdateStages withMetricNamespace(String metricNamespace);

        UpdateStages withoutMetricNamespace();

        UpdateStages withDimensionFilter(String dimensionName, String... values);

        UpdateStages withoutDimensionFilter(String name);
    }

    interface Update extends
            Settable<MetricAlert.Update> {
    }
}