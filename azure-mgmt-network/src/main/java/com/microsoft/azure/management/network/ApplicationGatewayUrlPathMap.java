/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.network.implementation.ApplicationGatewayUrlPathMapInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ChildResource;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * A client-side representation of an application gateway's URL path map.
 */
@Fluent()
@Beta(SinceVersion.V1_5_0)
public interface ApplicationGatewayUrlPathMap extends
    HasInner<ApplicationGatewayUrlPathMapInner>,
    ChildResource<ApplicationGateway> {


    /**
     * Grouping of application gateway URL path map definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of an application gateway URL path map definition.
         * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
         */
        interface Blank<ReturnT> extends WithAttach<ReturnT> {
        }

        /**
         * The final stage of an application gateway URL path map definition.
         * <p>
         * At this stage, any remaining optional settings can be specified, or the definition
         * can be attached to the parent application gateway definition.
         * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
         */
        interface WithAttach<ReturnT> extends
            Attachable.InDefinition<ReturnT> {
        }
    }

    /** The entirety of an application gateway URL path map definition.
     * @param <ReturnT> the stage of the parent application gateway definition to return to after attaching this definition
     */
    interface Definition<ReturnT> extends
        DefinitionStages.Blank<ReturnT>,
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
        interface Blank<ReturnT> extends WithAttach<ReturnT> {
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
        UpdateDefinitionStages.WithAttach<ReturnT> {
    }
}
