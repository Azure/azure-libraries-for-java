/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.batchai;


import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasParent;
import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;

/**
 * Specifies settings for container image.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_8_0)
public interface ContainerImageSettings extends Indexable,
        HasParent<BatchAIJob>,
        HasInner<OutputDirectory> {

    /**
     * Definition of ContainerImage settings.
     *
     * @param <ParentT> the stage of the parent definition to return to after attaching this definition
     */
    interface Definition<ParentT> extends
            DefinitionStages.Blank<ParentT>,
            DefinitionStages.WithRegistryCredentials<ParentT>,
            DefinitionStages.WithAttach<ParentT> {
    }

    /**
     * Definition stages for container image settings.
     */
    interface DefinitionStages {

        /**
         * The final stage of the output directory settings definition.
         * At this stage, any remaining optional settings can be specified, or the output directory settings definition
         * can be attached to the parent Batch AI job definition.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithAttach<ParentT> extends
                Attachable.InDefinition<ParentT> {
            /**
             * Specifies url for container registry.
             * @param serverUrl URL for image repository
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withRegistryUrl(String serverUrl);

            /**
             * Specifies username to login to container registry.
             * @param username user name to login
             * @return the next stage of the definition
             */
            WithRegistryCredentials<ParentT> withRegistryUsername(String username);
        }

        /**
         * The first stage of the output directory settings definition.
         *
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface Blank<ParentT> extends WithAttach<ParentT> {
        }

        /**
         * Specifies container registry credentials.
         * @param <ParentT> the stage of the parent definition to return to after attaching this definition
         */
        interface WithRegistryCredentials<ParentT> {
            /**
             * Specifies password for container registry.
             * @param password password to login
             * @return the next stage of the definition
             */
            WithAttach<ParentT> withRegistryPassword(String password);

            /**
             * Specifies the location of the password, which is a Key Vault Secret.
             * Users can store their secrets in Azure KeyVault and pass it to the Batch
             * AI Service to integrate with KeyVault.
             **/
            WithAttach<ParentT> withRegistrySecretReference(String keyVaultId, String secretUrl);
        }
    }

}
