/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import java.util.Map;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.network.implementation.ApplicationGatewayInner;
import com.microsoft.azure.management.network.model.HasPublicIpAddress;
import com.microsoft.azure.management.resources.fluentcore.arm.models.GroupableResource;
import com.microsoft.azure.management.resources.fluentcore.arm.models.Resource;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.Refreshable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import com.microsoft.azure.management.resources.fluentcore.model.Wrapper;

/**
 * Entry point for application gateway management API in Azure.
 */
@Fluent
public interface ApplicationGateway extends
        GroupableResource,
        Refreshable<ApplicationGateway>,
        Wrapper<ApplicationGatewayInner>,
        Updatable<ApplicationGateway.Update> {

    // Getters

    /**
     * @return the SKU of this application gateway
     */
    ApplicationGatewaySku sku();

    /**
     * @return the operational state of the application gateway
     */
    ApplicationGatewayOperationalState operationalState();

    /**
     * @return the SSL policy for the application gateway
     */
    ApplicationGatewaySslPolicy sslPolicy();

    /**
     * @return IP configurations of this application gateway, indexed by name
     */
    Map<String, ApplicationGatewayIpConfiguration> ipConfigurations();

    /**
     * @return backend address pools of this application gateway, indexed by name
     */
    Map<String, ApplicationGatewayBackend> backends();

    /**
     * @return frontend IP configurations of this application gateway, indexed by name
     */
    Map<String, ApplicationGatewayFrontend> frontends();

    /**
     * @return named frontend ports of this application gateway, indexed by name
     */
    Map<String, Integer> frontendPorts();

    /**
     * @return backend HTTP configurations of this application gateway, indexed by name
     */
    Map<String, ApplicationGatewayBackendHttpConfiguration> backendHttpConfigurations();

    /**
     * @return SSL certificates, indexed by name
     */
    Map<String, ApplicationGatewaySslCertificate> sslCertificates();

    /**
     * @return Frontend listeners, indexed by name
     */
    Map<String, ApplicationGatewayFrontendListener> frontendListeners();

    /**
     * @return request routing rules, indexed by name
     */
    Map<String, ApplicationGatewayRequestRoutingRule> requestRoutingRules();

    /**
     * Returns the name of the existing port, if any, that is associated with the specified port number.
     * @param portNumber a port number
     * @return the existing port name for that port number, or null if none found
     */
    String frontendPortNameFromNumber(int portNumber);

    /**
     * Finds a frontend listener associated with the specified frontend port number, if any.
     * @param portNumber a used port number
     * @return a frontend listener, or null if none found
     */
    ApplicationGatewayFrontendListener getFrontendListenerByPortNumber(int portNumber);

    /**
     * The entirety of the application gateway definition.
     */
    interface Definition extends
        DefinitionStages.Blank,
        DefinitionStages.WithGroup,
        DefinitionStages.WithCreate,
        DefinitionStages.WithSku,
        DefinitionStages.WithContainingSubnet,
        DefinitionStages.WithPrivateFrontend,
        DefinitionStages.WithPrivateFrontendOptional,
        DefinitionStages.WithPublicFrontend,
        DefinitionStages.WithBackend,
        DefinitionStages.WithBackendOrRequestRoutingRule,
        DefinitionStages.WithRequestRoutingRule,
        DefinitionStages.WithRequestRoutingRuleOrCreate {
    }

    /**
     * Grouping of application gateway definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of an application gateway definition.
         */
        interface Blank
            extends GroupableResource.DefinitionWithRegion<WithGroup> {
        }

        /**
         * The stage of an application gateway definition allowing to specify the resource group.
         */
        interface WithGroup
            extends GroupableResource.DefinitionStages.WithGroup<WithSku> {
        }

        /**
         * The stage of an application gateway definition allowing to add a public IP address as the default public frontend.
         * @param <ReturnT> the next stage of the definition
         */
        interface WithPublicIpAddress<ReturnT> extends HasPublicIpAddress.DefinitionStages.WithPublicIpAddressNoDnsLabel<ReturnT> {
        }

        /**
         * The stage of an application gateway definition allowing to define one or more public, or Internet-facing, frontends.
         */
        interface WithPublicFrontend extends WithPublicIpAddress<WithPrivateFrontendOptional> {
            /**
             * Begins the definition of a new public, or Internet-facing, frontend.
             * @param name the name for the frontend
             * @return the first stage of the new frontend definition
             */
            ApplicationGatewayPublicFrontend.DefinitionStages.Blank<WithPrivateFrontendOptional> definePublicFrontend(String name);

            /**
             * Specifies that the application gateway should not be Internet-facing.
             * @return the next stage of the definition
             */
            @Method
            WithPrivateFrontend withoutPublicFrontend();
        }

        /**
         * The stage of an internal application gateway definition allowing to define a private frontend.
         */
        interface WithPrivateFrontend {
            /**
             * Begins the definition of a private, or internal, application gateway frontend IP configuration.
             * @param name the name for the frontend
             * @return the first stage of a private frontend IP configuration definition
             */
            /* TODO Multiple frontends are not yet supported by Azure, so this should be revisited when they are
            ApplicationGatewayPrivateFrontend.DefinitionStages.Blank<WithHttpListener> definePrivateFrontend(String name); */

            /**
             * Enables a private default frontend in the subnet containing the application gateway.
             * <p>
             * A frontend with the name "default" will be created if needed.
             * @return the next stage of the definition
             */
            @Method
            WithBackend withPrivateFrontend();

            /**
             * Enables a private frontend in the subnet containing the application gateway.
             * @param frontendName the name for the frontend to create
             * @return the next stage of the definition
             */
            /* Multiple frontends are not yet supported by Azure, so this should be revisited when they are
            WithHttpListener withPrivateFrontend(String frontendName); */
        }

        /**
         * The stage of an internal application gateway definition allowing to optionally define a private,
         * or internal, frontend IP configuration.
         */
        interface WithPrivateFrontendOptional extends WithPrivateFrontend {
            /**
             * Specifies that no private, or internal, frontend should be enabled.
             * @return the next stage of the definition
             */
            @Method
            WithBackend withoutPrivateFrontend();
        }

        /**
         * The stage of an application gateway definition allowing to add a listener.
         */
        interface WithListener {
            /**
             * Begins the definition of a new application gateway listener to be attached to the gateway.
             * @param name a unique name for the listener
             * @return the first stage of the listener definition
             */
            ApplicationGatewayFrontendListener.DefinitionStages.Blank<WithCreate> defineFrontendListener(String name);
        }

        /**
         * The stage of an application gateway definition allowing to add a frontend port.
         */
        interface WithFrontendPort {
            /**
             * Creates a frontend port with an auto-generated name and the specified port number, unless one already exists.
             * @param portNumber a port number
             * @return the next stage of the definition
             */
            WithCreate withFrontendPort(int portNumber);

            /**
             * Creates a frontend port with the specified name and port number, unless a port matching this name and/or number already exists.
             * @param portNumber a port number
             * @param name the name to assign to the port
             * @return the next stage of the definition, or null if a port matching either the name or the number, but not both, already exists.
             */
            WithCreate withFrontendPort(int portNumber, String name);
        }

        /**
         * The stage of an application gateway definition allowing to add an SSL certificate to be used by HTTPS listeners.
         */
        interface WithSslCert {
            /**
             * Begins the definition of a new application gateway SSL certificate to be attached to the gateway for use in HTTPS listeners.
             * @param name a unique name for the certificate
             * @return the first stage of the certificate definition
             */
            ApplicationGatewaySslCertificate.DefinitionStages.Blank<WithCreate> defineSslCertificate(String name);
        }

        /**
         * The stage of an application gateway definition allowing to add a backend.
         */
        interface WithBackend {
            /**
             * Begins the definition of a new application gateway backend to be attached to the gateway.
             * @param name a unique name for the backend
             * @return the first stage of the backend definition
             */
            ApplicationGatewayBackend.DefinitionStages.Blank<WithBackendOrRequestRoutingRule> defineBackend(String name);

            /**
             * Adds an IP address to the default backend.
             * <p>
             * A backend with the name "default" will be created if it does not already exist.
             * @param ipAddress an IP address
             * @return the next stage of the definition
             */
            WithBackendOrRequestRoutingRule withBackendIpAddress(String ipAddress);

            /**
             * Adds an FQDN (fully qualified domain name) to the default backend.
             * <p>
             * A backend with the name "default" will be created if it does not already exist.
             * @param fqdn a fully qualified domain name
             * @return the next stage of the definition
             */
            WithBackendOrRequestRoutingRule withBackendFqdn(String fqdn);

            /**
             * Adds an IP address to a backend.
             * @param ipAddress an IP address
             * @param backendName the name for the backend to add the address to
             * @return the next stage of the definition
             */
            WithBackendOrRequestRoutingRule withBackendIpAddress(String ipAddress, String backendName);

            /**
             * Adds an FQDN (fully qualified domain name) to a backend.
             * @param fqdn a fully qualified domain name
             * @param backendName the name for the backend to add the FQDN to
             * @return the next stage of the definition
             */
            WithBackendOrRequestRoutingRule withBackendFqdn(String fqdn, String backendName);
        }

        /**
         * The stage of an application gateway definition allowing to continue adding backends or start adding request routing rules.
         */
        interface WithBackendOrRequestRoutingRule extends WithBackend, WithRequestRoutingRule {
        }

        /**
         * The stage of an application gateway definition allowing to add a backend HTTP configuration.
         */
        interface WithBackendHttpConfig {
            /**
             * Begins the definition of a new application gateway backend HTTP configuration to be attached to the gateway.
             * @param name a unique name for the backend HTTP configuration
             * @return the first stage of the backend HTTP configuration definition
             */
            ApplicationGatewayBackendHttpConfiguration.DefinitionStages.Blank<WithCreate> defineBackendHttpConfiguration(String name);
        }

        /**
         * The stage of an application gateway definition allowing to add a request routing rule.
         */
        interface WithRequestRoutingRule {
            /**
             * Begins the definition of a new application gateway request routing rule to be attached to the gateway.
             * @param name a unique name for the request routing rule
             * @return the first stage of the request routing rule
             */
            ApplicationGatewayRequestRoutingRule.DefinitionStages.Blank<WithRequestRoutingRuleOrCreate> defineRequestRoutingRule(String name);
        }

        /**
         * The stage of an application gateway definition allowing to continue adding more request routing rules,
         * or start specifying optional settings, or create the application gateway.
         */
        interface WithRequestRoutingRuleOrCreate extends WithRequestRoutingRule, WithCreate {
        }

        /**
         * The stage of an application gateway definition allowing to specify the SKU.
         */
        interface WithSku {
            /**
             * Specifies the SKU of the application gateway to create.
             * @param skuName an application gateway SKU name
             * @param capacity the capacity of the SKU, between 1 and 10
             * @return the next stage of the definition
             */
            WithContainingSubnet withSku(ApplicationGatewaySkuName skuName, int capacity);
        }

        /**
         * The stage of an application gateway definition allowing to specify the subnet the app gateway is getting
         * its private IP address from.
         */
        interface WithContainingSubnet {
            /**
             * Specifies the default subnet the application gateway gets its private IP address from.
             * <p>
             * This will create an IP configuration named "default", if it does not already exist.
             * @param subnet an existing subnet
             * @return the next stage of the definition
             */
            WithPublicFrontend withContainingSubnet(Subnet subnet);

            /**
             * Specifies the default subnet the application gateway gets its private IP address from.
             * <p>
             * This will create an IP configuration named "default", if it does not already exist.
             * @param network the virtual network the subnet is part of
             * @param subnetName the name of a subnet within the selected network
             * @return the next stage of the definition
             */
            WithPublicFrontend withContainingSubnet(Network network, String subnetName);

            /**
             * Specifies the default subnet the application gateway gets its private IP address from.
             * <p>
             * This will create an IP configuration named "default", if it does not already exist.
             * @param networkResourceId the resource ID of the virtual network the subnet is part of
             * @param subnetName the name of a subnet within the selected network
             * @return the next stage of the definition
             */
            WithPublicFrontend withContainingSubnet(String networkResourceId, String subnetName);

            /**
             * Begins the definition of a new IP configuration to add to this application gateway.
             * @param name a name to assign to the IP configuration
             * @return the first stage of the IP configuration definition
             */
            ApplicationGatewayIpConfiguration.DefinitionStages.Blank<WithPublicFrontend> defineIpConfiguration(String name);
        }

        /**
         * The stage of an application gateway definition containing all the required inputs for
         * the resource to be created (via {@link WithCreate#create()}), but also allowing
         * for any other optional settings to be specified.
         */
        interface WithCreate extends
            Creatable<ApplicationGateway>,
            Resource.DefinitionWithTags<WithCreate>,
            WithSslCert,
            WithFrontendPort,
            WithListener,
            WithBackendHttpConfig {
        }
    }

    /**
     * Grouping of application gateway update stages.
     */
    interface UpdateStages {
        /**
         * The stage of an application gateway update allowing to modify IP configurations.
         */
        interface WithIpConfig {
            /**
             * Removes the specified IP configuration.
             * <p>
             * Note that removing an IP configuration referenced by other settings may break the application gateway.
             * Also, there must be at least one IP configuration for the application gateway to function.
             * @param ipConfigurationName the name of the IP configuration to remove
             * @return the next stage of the update
             */
            Update withoutIpConfiguration(String ipConfigurationName);

            // TODO Other IP config updates...
        }

        /**
         * The stage of an application gateway update allowing to modify backends.
         */
        interface WithBackend {
            /**
             * Begins the definition of a new application gateway backend to be attached to the gateway.
             * @param name a unique name for the backend
             * @return the first stage of the backend definition
             */
            ApplicationGatewayBackend.UpdateDefinitionStages.Blank<Update> defineBackend(String name);

            /**
             * Adds an IP address to the default backend.
             * <p>
             * A backend with the name "default" will be created if it does not already exist.
             * @param ipAddress an IP address
             * @return the next stage of the update
             */
            Update withBackendIpAddress(String ipAddress);

            /**
             * Adds an FQDN (fully qualified domain name) to the default backend.
             * <p>
             * A backend with the name "default" will be created if it does not already exist.
             * @param fqdn a fully qualified domain name
             * @return the next stage of the update
             */
            Update withBackendFqdn(String fqdn);

            /**
             * Adds an IP address to a backend.
             * @param ipAddress an IP address
             * @param backendName the name for the backend to add the address to
             * @return the next stage of the update
             */
            Update withBackendIpAddress(String ipAddress, String backendName);

            /**
             * Adds an FQDN (fully qualified domain name) to a backend.
             * @param fqdn a fully qualified domain name
             * @param backendName the name for the backend to add the FQDN to
             * @return the next stage of the update
             */
            Update withBackendFqdn(String fqdn, String backendName);

            /**
             * Ensures the specified fully qualified domain name (FQDN) is not associated with any backend.
             * @param fqdn a fully qualified domain name (FQDN)
             * @return the next stage of the update
             */
            Update withoutBackendFqdn(String fqdn);

            /**
             * Ensures the specified IP address is not associated with any backend.
             * @param ipAddress an IP address
             * @return the next stage of the update
             */
            Update withoutBackendIpAddress(String ipAddress);

            /**
             * Removes the specified backend.
             * <p>
             * Note that removing a backend referenced by other settings may break the application gateway.
             * @param backendName the name of an existing backend on this application gateway
             * @return the next stage of the update
             */
            Update withoutBackend(String backendName);

            /**
             * Begins the update of an existing backend on this application gateway.
             * @param name the name of the backend
             * @return the first stage of an update of the backend
             */
            ApplicationGatewayBackend.Update updateBackend(String name);
        }

        /**
         * The stage of an application gateway update allowing to modify frontends.
         */
        interface WithFrontend {
            /**
             * Removes the specified frontend IP configuration.
             * <p>
             * Note that removing a frontend referenced by other settings may break the application gateway.
             * @param frontendName the name of the frontend IP configuration to remove
             * @return the next stage of the update
             */
            Update withoutFrontend(String frontendName);

            // TODO Other frontend updates...
        }

        /**
         * The stage of an application gateway definition allowing to modify frontend ports.
         */
        interface WithFrontendPort {
            /**
             * Creates a frontend port with an auto-generated name and the specified port number, unless one already exists.
             * @param portNumber a port number
             * @return the next stage of the definition
             */
            Update withFrontendPort(int portNumber);

            /**
             * Creates a frontend port with the specified name and port number, unless a port matching this name and/or number already exists.
             * @param portNumber a port number
             * @param name the name to assign to the port
             * @return the next stage of the definition, or null if a port matching either the name or the number, but not both, already exists.
             */
            Update withFrontendPort(int portNumber, String name);

            /**
             * Removes the specified frontend port.
             * <p>
             * Note that removing a frontend port referenced by other settings may break the application gateway.
             * @param name the name of the frontend port to remove
             * @return the next stage of the update
             */
            Update withoutFrontendPort(String name);

            /**
             * Removes the specified frontend port.
             * <p>
             * Note that removing a frontend port referenced by other settings may break the application gateway.
             * @param portNumber the port number of the frontend port to remove
             * @return the next stage of the update
             */
            Update withoutFrontendPort(int portNumber);
        }

        /**
         * The stage of an application gateway update allowing to modify the SKU.
         */
        interface WithSku {
            /**
             * Specifies the SKU of the application gateway.
             * @param skuName an application gateway SKU name
             * @param capacity the capacity of the SKU, between 1 and 10
             * @return the next stage of the update
             */
            Update withSku(ApplicationGatewaySkuName skuName, int capacity);
        }

        /**
         * The stage of an application gateway update allowing to modify SSL certificates.
         */
        interface WithSslCert {
            /**
             * Begins the definition of a new application gateway SSL certificate to be attached to the gateway for use in frontend HTTPS listeners.
             * @param name a unique name for the certificate
             * @return the first stage of the certificate definition
             */
            ApplicationGatewaySslCertificate.UpdateDefinitionStages.Blank<Update> defineSslCertificate(String name);

            /**
             * Removes the specified SSL certificate from the application gateway.
             * <p>
             * Note that removing a certificate referenced by other settings may break the application gateway.
             * @param name the name of the certificate to remove
             * @return the next stage of the update
             */
            Update withoutCertificate(String name);
        }

        /**
         * The stage of an application gateway update allowing to modify frontend listeners.
         */
        interface WithListener {
            /**
             * Begins the definition of a new application gateway listener to be attached to the gateway.
             * @param name a unique name for the listener
             * @return the first stage of the listener definition
             */
            ApplicationGatewayFrontendListener.UpdateDefinitionStages.Blank<Update> defineFrontendListener(String name);

            /**
             * Removes a frontend listener from the application gateway.
             * <p>
             * Note that removing a listener referenced by other settings may break the application gateway.
             * @param name the name of the listener to remove
             * @return the next stage of the update
             */
            Update withoutFrontendHttpListener(String name);
        }

        /**
         * The stage of an application gateway update allowing to modify backend HTTP configurations.
         */
        interface WithBackendHttpConfig {
            /**
             * Begins the definition of a new application gateway backend HTTP configuration to be attached to the gateway.
             * @param name a unique name for the backend HTTP configuration
             * @return the first stage of the backend HTTP configuration definition
             */
            ApplicationGatewayBackendHttpConfiguration.UpdateDefinitionStages.Blank<Update> defineBackendHttpConfiguration(String name);

            /**
             * Removes the specified backend HTTP configuration from this application gateway.
             * <p>
             * Note that removing a backend HTTP configuration referenced by other settings may break the application gateway.
             * @param name the name of an existing backend HTTP configuration on this application gateway
             * @return the next stage of the update
             */
            Update withoutBackendHttpConfiguration(String name);

            /**
             * Begins the update of a backend HTTP configuration.
             * @param name the name of an existing backend HTTP configuration on this application gateway
             * @return the next stage of the update
             */
            ApplicationGatewayBackendHttpConfiguration.Update updateBackendHttpConfiguration(String name);
        }

        /**
         * The stage of an application gateway update allowing to modify request routing rules.
         */
        interface WithRequestRoutingRule {
            /**
             * Begins the definition of a new application gateway request routing rule to be attached to the gateway.
             * @param name a unique name for the request routing rule
             * @return the first stage of the request routing rule
             */
            ApplicationGatewayRequestRoutingRule.UpdateDefinitionStages.Blank<Update> defineRequestRoutingRule(String name);

            /**
             * Removes a request routing rule from the application gateway.
             * @param name the name of the request routing rule to remove
             * @return the next stage of the update
             */
            Update withoutRequestRoutingRule(String name);
        }
    }

    /**
     * The template for an application gateway update operation, containing all the settings that
     * can be modified.
     * <p>
     * Call {@link Update#apply()} to apply the changes to the resource in Azure.
     */
    interface Update extends
        Appliable<ApplicationGateway>,
        Resource.UpdateWithTags<Update>,
        UpdateStages.WithSku,
        UpdateStages.WithBackend,
        UpdateStages.WithBackendHttpConfig,
        UpdateStages.WithIpConfig,
        UpdateStages.WithFrontend,
        UpdateStages.WithFrontendPort,
        UpdateStages.WithSslCert,
        UpdateStages.WithListener,
        UpdateStages.WithRequestRoutingRule {
    }
}
