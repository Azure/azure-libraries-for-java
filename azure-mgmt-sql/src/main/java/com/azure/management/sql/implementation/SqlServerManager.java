/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.sql.implementation;

import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.serializer.AzureJacksonAdapter;
import com.azure.management.AzureTokenCredential;
import com.azure.management.RestClient;
import com.azure.management.RestClientBuilder;
import com.azure.management.resources.fluentcore.arm.AzureConfigurable;
import com.azure.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.azure.management.resources.fluentcore.arm.implementation.Manager;
import com.azure.management.sql.SqlServers;
import com.azure.management.sql.models.SqlManagementClientImpl;

/**
 * Entry point to Azure SQLServer resource management.
 */
public class SqlServerManager extends Manager<SqlServerManager, SqlManagementClientImpl> {
    private SqlServers sqlServers;

    private final String tenantId;

    protected SqlServerManager(RestClient restClient, String tenantId, String subscriptionId) {
        super(
                restClient,
                subscriptionId,
                new SqlManagementClientImpl(restClient.getHttpPipeline()));
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
     * @param credential the credentials to use
     * @param subscriptionId the subscription
     * @return the SqlServer
     */
    public static SqlServerManager authenticate(AzureTokenCredential credential, String subscriptionId) {
        return new SqlServerManager(new RestClientBuilder()
                .withBaseUrl(credential.getEnvironment(), AzureEnvironment.Endpoint.RESOURCE_MANAGER)
                .withCredential(credential)
                .withSerializerAdapter(new AzureJacksonAdapter())
//                .withResponseBuilderFactory(new AzureResponseBuilder.Factory())
//                .withInterceptor(new ProviderRegistrationInterceptor(credentials))
//                .withInterceptor(new ResourceManagerThrottlingInterceptor())
                .buildClient(), credential.getDomain(), subscriptionId);
    }

    /**
     * Creates an instance of SqlServer that exposes Compute resource management API entry points.
     *
     * @param restClient the RestClient to be used for API calls.
     * @param tenantId the tenant UUID
     * @param subscriptionId the subscription
     * @return the SqlServer
     */
    public static SqlServerManager authenticate(RestClient restClient, String tenantId, String subscriptionId) {
        return new SqlServerManager(restClient, tenantId, subscriptionId);
    }



    /**
     * The interface allowing configurations to be set.
     */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
         * Creates an instance of SqlServer that exposes Compute resource management API entry points.
         *
         * @param credential the credentials to use
         * @param subscriptionId the subscription
         * @return the SqlServer
         */
        SqlServerManager authenticate(AzureTokenCredential credential, String subscriptionId);
    }

    /**
     * The implementation for Configurable interface.
     */
    private static final class ConfigurableImpl extends AzureConfigurableImpl<Configurable> implements Configurable {
        @Override
        public SqlServerManager authenticate(AzureTokenCredential credential, String subscriptionId) {
            return SqlServerManager.authenticate(buildRestClient(credential), credential.getDomain(), subscriptionId);
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