/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;


import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Beta.SinceVersion;
import com.microsoft.azure.management.resources.fluentcore.arm.models.ChildResource;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;

import java.io.File;
import java.io.IOException;

/**
 * A client-side representation of point-to-site configuration for a virtual network gateway.
 */
@Fluent
@Beta(SinceVersion.V1_6_0)
public interface PointToSiteConfiguration extends
        HasInner<VpnClientConfiguration>,
        ChildResource<VirtualNetworkGateway> {
    /**
     * Grouping of point-to-site configuration definition stages.
     */
    interface DefinitionStages {
        /**
         * The first stage of the point-to-site configuration definition.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Blank<ParentT> extends WithAddressPool<ParentT> {
        }

        interface WithAddressPool<ParentT> {
            WithAuthenticationType<ParentT> withAddressPool(String addressPool);
        }

        /**
         * The stage of the point-to-site configuration definition allowing to specify authentication type.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithAuthenticationType<ParentT> {
            /**
             * Specifies that Azure certificate authentication type will be used.
             * @return the next stage of the definition
             */
            WithRootCertificate<ParentT> withAzureCertificate();

            WithAttach<ParentT> withRadiusAuthentication(String serverIPAddress, String serverSecret);
        }

        interface WithRootCertificate<ParentT> {
            WithAttach<ParentT> withRootCertificate(String name, String certificateData);

            WithAttach<ParentT> withRootCertificateFromFile(String name, File certificateFile) throws IOException;
        }

        interface WithRevokedCertificate<ParentT> {
            WithAttach<ParentT> withRevokedCertificate(String name, String thumbprint);
        }

        /**
         * The stage of a point-to-site configuration definition allowing to specify which tunnel type will be used.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithTunnelType<ParentT> {
            WithAttach<ParentT> withSstpOnly();

            WithAttach<ParentT> withIkeV2Only();
        }

        /** The final stage of the point-to-site configuration definition.
         * <p>
         * At this stage, any remaining optional settings can be specified, or the point-to-site configuration definition
         * can be attached to the parent virtual network gateway definition.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithAttach<ParentT> extends
                Attachable.InDefinition<ParentT>,
                WithTunnelType<ParentT>,
                WithRootCertificate<ParentT>,
                WithRevokedCertificate<ParentT> {
        }
    }

    /** The entirety of a point-to-site configuration definition.
     * @param <ParentT> the stage of the parent definition to return to after attaching this definition
     */
    interface Definition<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithAuthenticationType<ParentT>,
            DefinitionStages.WithAddressPool<ParentT>,
            DefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Grouping of point-to-site configuration update stages.
     */
    interface UpdateStages {
        interface WithAddressPool<ParentT> {
            WithAuthenticationType<ParentT> withAddressPool(String addressPool);
        }

        /**
         * The stage of the point-to-site configuration definition allowing to specify authentication type.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithAuthenticationType<ParentT> {
            /**
             * Specifies that Azure certificate authentication type will be used.
             * @return the next stage of the definition
             */
            Update withAzureCertificate();

            Update withRadiusAuthentication(String serverIPAddress, String serverSecret);
        }

        interface WithRootCertificate<ParentT> {
            Update withRootCertificate(String name, String certificateData);

            Update withRootCertificateFromFile(String name, File certificateFile) throws IOException;

            Update withoutRootCertificate(String name);
        }

        interface WithRevokedCertificate<ParentT> {
            Update withRevokedCertificate(String name, String thumbprint);
        }

        /**
         * The stage of a point-to-site configuration definition allowing to specify which tunnel type will be used.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithTunnelType<ParentT> {
            Update withSstpOnly();

            Update withIkeV2Only();
        }
    }

    /**
     * The entirety of a subnet update as part of a network update.
     */
    interface Update extends
            UpdateStages.WithAddressPool,
            UpdateStages.WithAuthenticationType,
            UpdateStages.WithRootCertificate,
            UpdateStages.WithRevokedCertificate,
            UpdateStages.WithTunnelType,
            Settable<VirtualNetworkGateway.Update> {
    }

}
