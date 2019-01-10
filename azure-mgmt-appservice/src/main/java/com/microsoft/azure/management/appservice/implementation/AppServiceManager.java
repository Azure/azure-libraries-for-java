/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.AzureResponseBuilder;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.appservice.*;
import com.microsoft.azure.management.graphrbac.implementation.GraphRbacManager;
import com.microsoft.azure.management.keyvault.implementation.KeyVaultManager;
import com.microsoft.azure.management.resources.fluentcore.arm.AzureConfigurable;
import com.microsoft.azure.management.resources.fluentcore.arm.implementation.AzureConfigurableImpl;
import com.microsoft.azure.management.resources.fluentcore.arm.implementation.Manager;
import com.microsoft.azure.management.resources.fluentcore.utils.ProviderRegistrationInterceptor;
import com.microsoft.azure.management.resources.fluentcore.utils.ResourceManagerThrottlingInterceptor;
import com.microsoft.azure.management.storage.implementation.StorageManager;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.RestClient;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Entry point to Azure storage resource management.
 */
@Beta
public final class AppServiceManager extends Manager<AppServiceManager, WebSiteManagementClientImpl> {
    // Managers
    private GraphRbacManager rbacManager;
    private KeyVaultManager keyVaultManager;
    private StorageManager storageManager;
    // Collections
    private WebApps webApps;
    private AppServicePlans appServicePlans;
    private AppServiceCertificateOrders appServiceCertificateOrders;
    private AppServiceCertificates appServiceCertificates;
    private AppServiceDomains appServiceDomains;
    private FunctionApps functionApps;
    private RestClient restClient;

    /**
     * Get a Configurable instance that can be used to create StorageManager with optional configuration.
     *
     * @return the instance allowing configurations
     */
    public static Configurable configure() {
        return new AppServiceManager.ConfigurableImpl();
    }

    /**
     * Creates an instance of StorageManager that exposes storage resource management API entry points.
     *
     * @param credentials the credentials to use
     * @param subscriptionId the subscription UUID
     * @return the StorageManager
     */
    public static AppServiceManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
        return new AppServiceManager(new RestClient.Builder()
                .withBaseUrl(credentials.environment(), AzureEnvironment.Endpoint.RESOURCE_MANAGER)
                .withCredentials(credentials)
                .withSerializerAdapter(new AzureJacksonAdapter())
                .withResponseBuilderFactory(new AzureResponseBuilder.Factory())
                .withInterceptor(new ProviderRegistrationInterceptor(credentials))
                .withInterceptor(new ResourceManagerThrottlingInterceptor())
                .build(), credentials.domain(), subscriptionId);
    }

    /**
     * Creates an instance of StorageManager that exposes storage resource management API entry points.
     *
     * @param restClient the RestClient to be used for API calls.
     * @param tenantId the tenant UUID
     * @param subscriptionId the subscription UUID
     * @return the StorageManager
     */
    public static AppServiceManager authenticate(RestClient restClient, String tenantId, String subscriptionId) {
        return new AppServiceManager(restClient, tenantId, subscriptionId);
    }

    /**
     * The interface allowing configurations to be set.
     */
    public interface Configurable extends AzureConfigurable<Configurable> {
        /**
         * Creates an instance of StorageManager that exposes storage management API entry points.
         *
         * @param credentials the credentials to use
         * @param subscriptionId the subscription UUID
         * @return the interface exposing AppService management API entry points that work across subscriptions
         */
        AppServiceManager authenticate(AzureTokenCredentials credentials, String subscriptionId);
    }

    /**
     * The implementation for Configurable interface.
     */
    private static final class ConfigurableImpl extends AzureConfigurableImpl<Configurable> implements Configurable {
        public AppServiceManager authenticate(AzureTokenCredentials credentials, String subscriptionId) {
            return AppServiceManager.authenticate(buildRestClient(credentials), credentials.domain(), subscriptionId);
        }
    }

    private AppServiceManager(RestClient restClient, String tenantId, String subscriptionId) {
        super(
                restClient,
                subscriptionId,
                new WebSiteManagementClientImpl(restClient).withSubscriptionId(subscriptionId));
        keyVaultManager = KeyVaultManager.authenticate(restClient, tenantId, subscriptionId);
        storageManager = StorageManager.authenticate(restClient, subscriptionId);
        rbacManager = GraphRbacManager.authenticate(restClient, tenantId);
        this.restClient = restClient;
    }

    /**
     * @return the Graph RBAC manager instance.
     */
    GraphRbacManager rbacManager() {
        return rbacManager;
    }

    /**
     * @return the key vault manager instance.
     */
    KeyVaultManager keyVaultManager() {
        return keyVaultManager;
    }

    /**
     * @return the storage manager instance.
     */
    StorageManager storageManager() {
        return storageManager;
    }

    RestClient restClient() {
        return restClient;
    }


    /**
     * @return the web app management API entry point
     */
    public WebApps webApps() {
        if (webApps == null) {
            webApps = new WebAppsImpl(this);
        }
        return webApps;
    }

    /**
     * @return the app service plan management API entry point
     */
    public AppServicePlans appServicePlans() {
        if (appServicePlans == null) {
            appServicePlans = new AppServicePlansImpl(this);
        }
        return appServicePlans;
    }

    /**
     * @return the certificate order management API entry point
     */
    public AppServiceCertificateOrders certificateOrders() {
        if (appServiceCertificateOrders == null) {
            appServiceCertificateOrders = new AppServiceCertificateOrdersImpl(this);
        }
        return appServiceCertificateOrders;
    }

    /**
     * @return the certificate management API entry point
     */
    public AppServiceCertificates certificates() {
        if (appServiceCertificates == null) {
            appServiceCertificates = new AppServiceCertificatesImpl(this);
        }
        return appServiceCertificates;
    }

    /**
     * @return the app service plan management API entry point
     */
    public AppServiceDomains domains() {
        if (appServiceDomains == null) {
            appServiceDomains = new AppServiceDomainsImpl(this);
        }
        return appServiceDomains;
    }
    /**
     * @return the web app management API entry point
     */
    public FunctionApps functionApps() {
        if (functionApps == null) {
            functionApps = new FunctionAppsImpl(this);
        }
        return functionApps;
    }

    /**
     *
     * @return Returns the latest Windows runtime stacks
     */
    public Runtimes latestRuntimes() {
        Hashtable<String, RuntimeVersion> variousRuntimes = new Hashtable<>();
        variousRuntimes.put(JavaVersion.ComponentName.toLowerCase(), JavaVersion.OFF);
        variousRuntimes.put(NodeVersion.ComponentName.toLowerCase(), NodeVersion.OFF);
        variousRuntimes.put(WebContainer.ComponentName.toLowerCase(), WebContainer.OFF);
        variousRuntimes.put(PythonVersion.ComponentName.toLowerCase(), PythonVersion.OFF);
        variousRuntimes.put(PhpVersion.ComponentName.toLowerCase(), PhpVersion.OFF);
        variousRuntimes.put(NetFrameworkVersion.ComponentName.toLowerCase(), NetFrameworkVersion.OFF);

        Iterator<ApplicationStackInner> stackIter = this.innerManagementClient.providers().getAvailableStacks("Windows").iterator();
        while (stackIter.hasNext()) {
            ApplicationStackInner stack = stackIter.next();

            RuntimeVersion runtime = variousRuntimes.get(stack.name().toLowerCase());
            if (runtime != null) {
                runtime.parseApplicationStackInner(stack);
            } else {
                //throw new FileNotFoundException("Runtime " + stack.name() + " not supported");
            }
        }

        Runtimes runtimes = new Runtimes();

        runtimes.withJavaVersions(JavaVersion.values());
        runtimes.withNetFrameworkVersions(NetFrameworkVersion.values());
        runtimes.withPhpVersions(PhpVersion.values());
        runtimes.withPythonVersions(PythonVersion.values());
        runtimes.withWebContainers(WebContainer.values());
        runtimes.withNodeVersions(NodeVersion.values());

        return runtimes;
    }

    public class Runtimes {
        private Collection<NetFrameworkVersion> netframeworkVersions;
        private Collection<PythonVersion> pythonVersions;
        private Collection<PhpVersion> phpVersions;
        private Collection<JavaVersion> javaVersions;
        private Collection<NodeVersion> nodeVersions;
        private Collection<WebContainer> webContainers;

        public Runtimes() {
        }

        public Runtimes withNetFrameworkVersions(Collection<NetFrameworkVersion> versions){
            this.netframeworkVersions = versions;
            return this;
        }

        public Runtimes withPythonVersions(Collection<PythonVersion> versions) {
            this.pythonVersions = versions;
            return this;
        }

        public Runtimes withPhpVersions(Collection<PhpVersion> versions) {
            this.phpVersions = versions;
            return this;
        }

        public Runtimes withJavaVersions(Collection<JavaVersion> versions) {
            this.javaVersions = versions;
            return this;
        }

        public Runtimes withWebContainers(Collection<WebContainer> versions) {
            this.webContainers = versions;
            return this;
        }

        public Runtimes withNodeVersions(Collection<NodeVersion> versions) {
            this.nodeVersions = versions;
            return this;
        }

        public Collection<NetFrameworkVersion> NetFrameworkVersions() {
            return this.netframeworkVersions;
        }

        public Collection<PythonVersion> PythonVersions() {
            return this.pythonVersions;
        }

        public Collection<PhpVersion> PhpVersions() {
            return this.phpVersions;
        }

        public Collection<JavaVersion> JavaVersions() {
            return this.javaVersions;
        }

        public Collection<WebContainer> Webcontainers() { return this.webContainers; }

        public Collection<NodeVersion> NodeVersins() {
            return  this.nodeVersions;
        }
    }
}