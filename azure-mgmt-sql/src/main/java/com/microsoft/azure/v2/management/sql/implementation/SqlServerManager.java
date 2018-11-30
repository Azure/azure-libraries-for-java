/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.sql.implementation;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.credentials.AzureTokenCredentials;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.implementation.Manager;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ProviderRegistrationPolicyFactory;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ResourceManagerThrottlingPolicyFactory;
import com.microsoft.azure.v2.management.sql.SqlServers;
import com.microsoft.azure.v2.policy.AsyncCredentialsPolicyFactory;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.http.HttpPipelineBuilder;

/**
 * Entry point to Azure SQLServer resource management.
 */
public class SqlServerManager extends Manager<SqlServerManager, SqlManagementClientImpl> {
    private SqlServers sqlServers;

    private final String tenantId;

    protected SqlServerManager(HttpPipeline httpPipeline, String subscriptionId, String domain, AzureEnvironment environment) {
        super(
                httpPipeline,
                subscriptionId,
                environment,
                new SqlManagementClientImpl(httpPipeline, environment).withSubscriptionId(subscriptionId));
        this.tenantId = domain;
    }

    /**
     * Get a Configurable instance that can be used to create SqlServer with optional configuration.
     *
     * @return Configurable
     */
    public static Configurable configure() {
        return new SqlServerManager.ConfigurableImpl();
    }

    /**
     * Creates an instance of SqlServer that exposes Compute resource management API entry points.
     *
     * @param credentials the credentials to use
     * @param subscriptionId the subscription
     * @return the SqlServer
     */
    public static SqlServerManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
        return new SqlServerManager(new HttpPipelineBuilder()
                .withRequestPolicy(new AsyncCredentialsPolicyFactory(credentials))
                .withRequestPolicy(new ProviderRegistrationPolicyFactory(credentials))
                .withRequestPolicy(new ResourceManagerThrottlingPolicyFactory())
                .build(), subscriptionId, credentials.domain(), credentials.environment());
    }

    /**
     * Creates an instance of SqlServer that exposes Compute resource management API entry points.
     *
     * @param httpPipeline the httpPipeline to be used for API calls.
     * @param subscriptionId the subscription
     * @param domain the domain
     * @param environment the azure environment hosting the APIs
     * @return the SqlServer
     */
    public static SqlServerManager authenticate(HttpPipeline httpPipeline, String subscriptionId, String domain, AzureEnvironment environment) {
        return new SqlServerManager(httpPipeline, subscriptionId, domain, environment);
    }



    /**
     * The interface allowing configurations to be set.
     */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
         * Creates an instance of SqlServer that exposes Compute resource management API entry points.
         *
         * @param credentials the credentials to use
         * @param subscriptionId the subscription
         * @return the SqlServer
         */
        SqlServerManager authenticate(AzureTokenCredentials credentials, String subscriptionId);
    }

    /**
     * The implementation for Configurable interface.
     */
    private static final class ConfigurableImpl extends AzureConfigurableImpl<Configurable> implements Configurable {
        @Override
        public SqlServerManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
            return SqlServerManager.authenticate(buildPipeline(credentials), subscriptionId, credentials.domain(), credentials.environment());
        }
    }

    /**
     * @return the SQL Server management API entry point
     */
    public SqlServers sqlServers() {
        if (sqlServers == null) {
            sqlServers = new SqlServersImpl(this);
        }

        return sqlServers;
    }

    /**
     * Get the tenant ID value.
     *
     * @return the tenant ID value
     */
    public String tenantId() {
        return this.tenantId;
    }


}
