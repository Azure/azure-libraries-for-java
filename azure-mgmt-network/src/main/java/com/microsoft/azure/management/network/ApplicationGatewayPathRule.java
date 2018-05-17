/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.network.implementation.ApplicationGatewayPathRuleInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ChildResource;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

import java.util.List;

/**
 * A client-side representation of an application gateway's URL path map.
 */
@Fluent()
@Beta(SinceVersion.V1_10_0)
public interface ApplicationGatewayPathRule extends
        HasInner<ApplicationGatewayPathRuleInner>,
        ChildResource<ApplicationGatewayUrlPathMap> {

    /**
     * @return default backend address pool
     */
    ApplicationGatewayBackend backend();

    /**
     * @return default backend HTTP settings configuration
     */
    ApplicationGatewayBackendHttpConfiguration backendHttpConfiguration();

    /**
     * @return default redirect configuration
     */
    ApplicationGatewayRedirectConfiguration redirectConfiguration();

    /**
     * @return paths for URL path map rule.
     */
    List<String> paths();

    /**
     * Grouping of application gateway URL path map definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of an application gateway URL path map definition.
         * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
         */
        interface Blank<ReturnT> extends WithBackendHttpConfiguration<ReturnT> {//extends WithBackendHttpConfiguration<ReturnT> {
        }

        interface WithPath<ReturnT> {
            WithAttach<ReturnT> withPath(String path);

            WithAttach<ReturnT> withPaths(String... paths);
        }

        /**
         * The stage of an application gateway request routing rule definition allowing to specify the backend HTTP settings configuration
         * to associate the routing rule with.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithBackendHttpConfiguration<ParentT> {
            /**
             * Associates the specified backend HTTP settings configuration with this path rule.
             * <p>
             * If the backend configuration does not exist yet, it must be defined in the optional part of the application gateway
             * definition. The request routing rule references it only by name.
             * @param name the name of a backend HTTP settings configuration
             * @return the next stage of the definition
             */
            WithBackend<ParentT> toBackendHttpConfiguration(String name);
        }


        /**
         * The stage of an application gateway request routing rule definition allowing to specify the backend to associate the routing rule with.
         * @param <ParentT> the stage of the application gateway definition to return to after attaching this definition
         */
        interface WithBackend<ParentT> {
            /**
             * Associates the path rule with a backend on this application gateway.
             * <p>
             * If the backend does not yet exist, it will be automatically created.
             * @param name the name of an existing backend
             * @return the next stage of the definition
             */
            WithPath<ParentT> toBackend(String name);
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

        /**
         * The final stage of an application gateway URL path map definition.
         * <p>
         * At this stage, any remaining optional settings can be specified, or the definition
         * can be attached to the parent application gateway definition.
         * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
         */
        interface WithAttach<ReturnT> extends
                Attachable.InDefinition<ReturnT>,
                WithPath<ReturnT>,
                WithRedirectConfig<ReturnT> {
        }
    }

    /** The entirety of an application gateway URL path map definition.
     * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
     */
    interface Definition<ReturnT> extends
            DefinitionStages.Blank<ReturnT>,
            DefinitionStages.WithBackendHttpConfiguration<ReturnT>,
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
            Settable<ApplicationGatewayUrlPathMap.Update> {
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
        }

        /**
         * The stage of an application gateway path rule definition allowing to specify the the paths to associate with URL path map.
         * @param <ParentT> the stage of the application gateway URL path map definition to return to after attaching this definition
         */
        interface WithBackend<ParentT> {
            /**
             * Associates the request routing rule with a backend on this application gateway.
             * <p>
             * If the backend does not yet exist, it will be automatically created.
             * @param name the name of an existing backend
             * @return the next stage of the definition
             */
            WithPath<ParentT> toBackend(String name);
        }

        interface WithPath<ReturnT> {
            WithAttach<ReturnT> withPath(String path);

            WithAttach<ReturnT> withPaths(String... paths);
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
                Attachable.InUpdate<ReturnT>,
                UpdateDefinitionStages.WithRedirectConfig<ReturnT> {
        }
    }

    /** The entirety of an application gateway URL path map definition as part of an application gateway update.
     * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
     */
    interface UpdateDefinition<ReturnT> extends
            UpdateDefinitionStages.Blank<ReturnT>,
            UpdateDefinitionStages.WithBackendHttpConfiguration<ReturnT>,
            UpdateDefinitionStages.WithBackend<ReturnT>,
            UpdateDefinitionStages.WithPath<ReturnT>,
            UpdateDefinitionStages.WithAttach<ReturnT> {
    }
}
