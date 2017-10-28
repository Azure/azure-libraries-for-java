/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.containerservice.implementation;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.AzureResponseBuilder;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.containerservice.ContainerServices;
import com.microsoft.azure.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.implementation.Manager;
import com.microsoft.azure.management.resources.fluentcore.utils.ProviderRegistrationInterceptor;
import com.microsoft.azure.management.resources.fluentcore.utils.ResourceManagerThrottlingInterceptor;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.RestClient;

/**
 * Entry point to Azure Container Service management.
 */
@Beta(SinceVersion.V1_4_0)
public final class ContainerServiceManager extends Manager<ContainerServiceManager, ContainerServiceManagementClientImpl> {
    // The service managers
    private ContainerServicesImpl containerServices;
//    private KubernetesServicesImpl kubernetesServices;

    /**
     * Get a Configurable instance that can be used to create ContainerServiceManager with optional configuration.
     *
     * @return Configurable
     */
    public static Configurable configure() {
        return new ContainerServiceManager.ConfigurableImpl();
    }

    /**
     * Creates an instance of ContainerServiceManager that exposes Azure Container Service resource management API entry points.
     *
     * @param credentials the credentials to use
     * @param subscriptionId the subscription
     * @return the ContainerServiceManager
     */
    public static ContainerServiceManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
        return new ContainerServiceManager(new RestClient.Builder()
                .withBaseUrl(credentials.environment(), AzureEnvironment.Endpoint.RESOURCE_MANAGER)
                .withCredentials(credentials)
                .withSerializerAdapter(new AzureJacksonAdapter())
                .withResponseBuilderFactory(new AzureResponseBuilder.Factory())
                .withInterceptor(new ProviderRegistrationInterceptor(credentials))
                .withInterceptor(new ResourceManagerThrottlingInterceptor())
                .build(), subscriptionId);
    }

    /**
     * Creates an instance of ContainerServiceManager that exposes Service resource management API entry points.
     *
     * @param restClient the RestClient to be used for API calls.
     * @param subscriptionId the subscription
     * @return the ContainerServiceManager
     */
    public static ContainerServiceManager authenticate(RestClient restClient, String subscriptionId) {
        return new ContainerServiceManager(restClient, subscriptionId);
    }

    /**
     * The interface allowing configurations to be set.
     */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
         * Creates an instance of ContainerServiceManager that exposes Service resource management API entry points.
         *
         * @param credentials the credentials to use
         * @param subscriptionId the subscription
         * @return the ContainerServiceManager
         */
        ContainerServiceManager authenticate(AzureTokenCredentials credentials, String subscriptionId);
    }

    /**
     * The implementation for Configurable interface.
     */
    private static final class ConfigurableImpl extends AzureConfigurableImpl<Configurable> implements  Configurable {
        @Override
        public ContainerServiceManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
            return ContainerServiceManager.authenticate(buildRestClient(credentials), subscriptionId);
        }
    }

    private ContainerServiceManager(RestClient restClient, String subscriptionId) {
        super(
                restClient,
                subscriptionId,
                new ContainerServiceManagementClientImpl(restClient).withSubscriptionId(subscriptionId));
    }

    /**
     * @return the Azure Container services resource management API entry point
     */
    public ContainerServices containerServices() {
        if (this.containerServices == null) {
            this.containerServices = new ContainerServicesImpl(this);
        }
        return this.containerServices;
    }

//    /**
//     * @return the Azure Kubernetes services resource management API entry point
//     */
//    public KubernetesServices kubernetesServices() {
//        if (this.kubernetesServices == null) {
//            this.kubernetesServices = new KubernetesServicesImpl(this);
//        }
//        return this.kubernetesServices;
//    }
}