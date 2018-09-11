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
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.http.HttpPipelineBuilder;
import com.microsoft.rest.v2.policy.CredentialsPolicyFactory;

/**
 * Entry point to Azure SQLServer resource management.
 */
public class SqlServerManager extends Manager<SqlServerManager, SqlManagementClientImpl> {
    private SqlServers sqlServers;

    private final String tenantId;

    protected SqlServerManager(HttpPipeline httpPipeline, String tenantId, String subscriptionId) {
        super(
                httpPipeline,
                subscriptionId,
                new SqlManagementClientImpl(httpPipeline).withSubscriptionId(subscriptionId));
        this.tenantId = tenantId;
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
                .withRequestPolicy(new CredentialsPolicyFactory(credentials))
                .withRequestPolicy(new ProviderRegistrationPolicyFactory(credentials))
                .withRequestPolicy(new ResourceManagerThrottlingPolicyFactory())
                .build(), credentials.domain(), subscriptionId);
    }

    /**
     * Creates an instance of SqlServer that exposes Compute resource management API entry points.
     *
     * @param httpPipeline the HttpPipeline to be used for API calls.
     * @param tenantId the tenant UUID
     * @param subscriptionId the subscription
     * @return the SqlServer
     */
    public static SqlServerManager authenticate(HttpPipeline httpPipeline, String tenantId, String subscriptionId) {
        return new SqlServerManager(httpPipeline, tenantId, subscriptionId);
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
            return SqlServerManager.authenticate(buildPipeline(credentials), credentials.domain(), subscriptionId);
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
