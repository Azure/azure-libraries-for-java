/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.network.implementation.ApplicationGatewayUrlPathMapInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ChildResource;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.Map;

/**
 * A client-side representation of an application gateway's URL path map.
 */
@Fluent
@Beta(SinceVersion.V1_10_0)
public interface ApplicationGatewayUrlPathMap extends
    HasInner<ApplicationGatewayUrlPathMapInner>,
    ChildResource<ApplicationGateway> {

    /**
     * @return default backend address pool
     */
    ApplicationGatewayBackend defaultBackend();

    /**
     * @return default backend HTTP settings configuration
     */
    ApplicationGatewayBackendHttpConfiguration defaultBackendHttpConfiguration();

    /**
     * @return default redirect configuration
     */
    ApplicationGatewayRedirectConfiguration defaultRedirectConfiguration();

    /**
     * @return path rules of URL path map resource
     */
    Map<String, ApplicationGatewayPathRule> pathRules();

    /**
     * Grouping of application gateway URL path map definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of an application gateway URL path map definition.
         * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
         */
        interface Blank<ReturnT> extends WithListenerOrFrontend<ReturnT> {
        }

        /**
         * The stage of an application gateway request routing rule definition allowing to specify an existing listener to
         * associate the routing rule with.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithListener<ParentT> {
            /**
             * Associates the request routing rule with a frontend listener.
             * <p>
             * If the listener with the specified name does not yet exist, it must be defined separately in the optional stages
             * of the application gateway definition. This only adds a reference to the listener by its name.
             * <p>
             * Also, note that a given listener can be used by no more than one request routing rule at a time.
             * @param name the name of a listener to reference
             * @return the next stage of the definition
             */
            WithBackendHttpConfigOrRedirect<ParentT> fromListener(String name);
        }

        /**
         * The stage of an application gateway request routing rule definition allowing to associate an existing listener
         * with the rule, or create a new one implicitly by specifying the frontend to listen to.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithListenerOrFrontend<ParentT> extends
                WithListener<ParentT>,
                WithFrontend<ParentT> {
        }

        /**
         * The stage of an application gateway request routing rule definition allowing to specify the frontend for the rule to apply to.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithFrontend<ParentT> {
            /**
             * Enables the rule to apply to the application gateway's public (Internet-facing) frontend.
             * <p>
             * If the public frontend IP configuration does not yet exist, it will be created under an auto-generated name.
             * <p>
             * If the application gateway does not have a public IP address specified for its public frontend, one will be created
             * automatically, unless a specific public IP address is specified in the application gateway definition's optional settings.
             * @return the next stage of the definition
             */
            @Method
            WithFrontendPort<ParentT> fromPublicFrontend();

            /**
             * Enables the rule to apply to the application gateway's private (internal) frontend.
             * <p>
             * If the private frontend IP configuration does not yet exist, it will be created under an auto-generated name.
             * <p>
             * If the application gateway does not have a subnet specified for its private frontend, one will be created automatically,
             * unless a specific subnet is specified in the application gateway definition's optional settings.
             * @return the next stage of the definition
             */
            @Method
            WithFrontendPort<ParentT> fromPrivateFrontend();
        }

        /**
         * The stage of an application gateway request routing rule definition allowing to create an associate listener and frontend
         * for a specific port number and protocol.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithFrontendPort<ParentT> {
            /**
             * Associates a new listener for the specified port number and the HTTP protocol with this rule.
             * @param portNumber the port number to listen to
             * @return the next stage of the definition, or null if the specified port number is already used for a different protocol
             */
            WithBackendHttpConfigOrRedirect<ParentT> fromFrontendHttpPort(int portNumber);

            /**
             * Associates a new listener for the specified port number and the HTTPS protocol with this rule.
             * @param portNumber the port number to listen to
             * @return the next stage of the definition, or null if the specified port number is already used for a different protocol
             */
//            WithSslCertificate<ParentT> fromFrontendHttpsPort(int portNumber);
        }

        /**
         * The stage of an application gateway request routing rule definition allowing to select either a backend or a redirect configuration.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithBackendHttpConfigOrRedirect<ParentT> extends WithBackendHttpConfiguration<ParentT>, WithRedirectConfig<ParentT> {
        }

        /**
         * The stage of an application gateway request routing rule definition allowing to specify the backend HTTP settings configuration
         * to associate the routing rule with.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithBackendHttpConfiguration<ParentT> {
            /**
             * Associates the specified backend HTTP settings configuration with this request routing rule.
             * <p>
             * If the backend configuration does not exist yet, it must be defined in the optional part of the application gateway
             * definition. The request routing rule references it only by name.
             * @param name the name of a backend HTTP settings configuration
             * @return the next stage of the definition
             */
            WithBackend<ParentT> toBackendHttpConfiguration(String name);

            /**
             * Creates a backend HTTP settings configuration for the specified backend port and the HTTP protocol, and associates it with this
             * request routing rule.
             * <p>
             * An auto-generated name will be used for this newly created configuration.
             * @param portNumber the port number for a new backend HTTP settings configuration
             * @return the next stage of the definition
             */
            WithBackend<ParentT> toBackendHttpPort(int portNumber);
        }


        /**
         * The stage of an application gateway request routing rule definition allowing to specify the backend to associate the routing rule with.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithBackend<ParentT> {
            /**
             * Associates the request routing rule with a backend on this application gateway.
             * <p>
             * If the backend does not yet exist, it will be automatically created.
             * @param name the name of an existing backend
             * @return the next stage of the definition
             */
            WithPathRule<ParentT> toBackend(String name);
        }

        /**
         * The stage of an application gateway request routing rule definition allowing to associate the rule with a redirect configuration.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithRedirectConfig<ParentT> {
            /**
             * Associates the specified redirect configuration with this request routing rule.
             * @param name the name of a redirect configuration on this application gateway
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withRedirectConfiguration(String name);
        }

        interface WithPathRule<ParentT> {
            ApplicationGatewayPathRule.DefinitionStages.Blank<WithAttach<ParentT>> definePathRule(String name);
        }

        /**
         * The final stage of an application gateway URL path map definition.
         * <p>
         * At this stage, any remaining optional settings can be specified, or the definition
         * can be attached to the parent application gateway definition.
         * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
         */
        interface WithAttach<ReturnT> extends
            Attachable.InDefinition<ReturnT>,
            WithPathRule<ReturnT> {
        }
    }

    /** The entirety of an application gateway URL path map definition.
     * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
     */
    interface Definition<ReturnT> extends
        DefinitionStages.Blank<ReturnT>,
        DefinitionStages.WithFrontendPort<ReturnT>,
        DefinitionStages.WithBackendHttpConfigOrRedirect<ReturnT>,
        DefinitionStages.WithBackend<ReturnT>,
        DefinitionStages.WithAttach<ReturnT> {
    }

    /**
     * Grouping of application gateway URL path map update stages.
     */
    interface UpdateStages {
    }

    /**
     * The entirety of an application gateway URL path map update as part of an application gateway update.
     */
    interface Update extends
        Settable<ApplicationGateway.Update> {
    }

    /**
     * Grouping of application gateway URL path map definition stages applicable as part of an application gateway update.
     */
    interface UpdateDefinitionStages {
        /**
         * The first stage of an application gateway URL path map  definition.
         * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
         */
        interface Blank<ReturnT> extends WithBackendHttpConfiguration<ReturnT> {
        }

        /**
         * The stage of an application gateway request routing rule definition allowing to select either a backend or a redirect configuration.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithBackendHttpConfigOrRedirect<ParentT> extends WithBackendHttpConfiguration<ParentT>, WithRedirectConfig<ParentT> {
        }

        /**
         * The stage of an application gateway request routing rule definition allowing to specify the backend HTTP settings configuration
         * to associate the routing rule with.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithBackendHttpConfiguration<ParentT> {
            /**
             * Associates the specified backend HTTP settings configuration with this request routing rule.
             * <p>
             * If the backend configuration does not exist yet, it must be defined in the optional part of the application gateway
             * definition. The request routing rule references it only by name.
             * @param name the name of a backend HTTP settings configuration
             * @return the next stage of the definition
             */
            WithBackend<ParentT> toBackendHttpConfiguration(String name);

            /**
             * Creates a backend HTTP settings configuration for the specified backend port and the HTTP protocol, and associates it with this
             * request routing rule.
             * <p>
             * An auto-generated name will be used for this newly created configuration.
             * @param portNumber the port number for a new backend HTTP settings configuration
             * @return the next stage of the definition
             */
            WithBackend<ParentT> toBackendHttpPort(int portNumber);
        }


        /**
         * The stage of an application gateway request routing rule definition allowing to specify the backend to associate the routing rule with.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithBackend<ParentT> {
            /**
             * Associates the request routing rule with a backend on this application gateway.
             * <p>
             * If the backend does not yet exist, it will be automatically created.
             * @param name the name of an existing backend
             * @return the next stage of the definition
             */
            WithAttach<ParentT> toBackend(String name);
        }

        /**
         * The stage of an application gateway request routing rule definition allowing to associate the rule with a redirect configuration.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithRedirectConfig<ParentT> {
            /**
             * Associates the specified redirect configuration with this request routing rule.
             * @param name the name of a redirect configuration on this application gateway
             * @return the next stage of the definition
             */
            @Beta(SinceVersion.V1_4_0)
            WithAttach<ParentT> withRedirectConfiguration(String name);
        }


        /** The final stage of an application gateway URL path map definition.
         * <p>
         * At this stage, any remaining optional settings can be specified, or the definition
         * can be attached to the parent application gateway definition.
         * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
         */
        interface WithAttach<ReturnT> extends
            Attachable.InUpdate<ReturnT> {
        }
    }

    /** The entirety of an application gateway URL path map definition as part of an application gateway update.
     * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
     */
    interface UpdateDefinition<ReturnT> extends
        UpdateDefinitionStages.Blank<ReturnT>,
        UpdateDefinitionStages.WithBackendHttpConfigOrRedirect<ReturnT>,
        UpdateDefinitionStages.WithBackend<ReturnT>,
        UpdateDefinitionStages.WithAttach<ReturnT> {
    }
}
