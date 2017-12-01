/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.keyvault.models.Attributes;
import com.microsoft.azure.keyvault.models.KeyAttributes;
import com.microsoft.azure.keyvault.models.KeyBundle;
import com.microsoft.azure.keyvault.webkey.JsonWebKey;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyOperation;
import com.microsoft.azure.keyvault.webkey.JsonWebKeyType;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.keyvault.Key.DefinitionStages.WithKey;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

import java.util.List;
import java.util.Map;

/**
 * An immutable client-side representation of an Azure Key Vault.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
public interface Key extends
        Indexable,
        HasInner<KeyBundle>,
        HasId,
        HasName,
        Updatable<Key.Update> {
    /**
     * @return the Json web key.
     */
    JsonWebKey jsonWebKey();

    /**
     * @return the key management attributes.
     */
    KeyAttributes attributes();

    /**
     * @return application specific metadata in the form of key-value pairs.
     */
    Map<String, String> tags();

    /**
     * True if the key's lifetime is managed by key vault. If this is a key
     * @return backing a certificate, then managed will be true.
     */
    boolean managed();

    interface Definition extends
            DefinitionStages.Blank,
            WithKey,
            DefinitionStages.WithImport,
            DefinitionStages.WithCreate {
    }

    interface DefinitionStages {
        interface Blank extends WithKey {
        }

        interface WithKey {
            WithCreate withKeyType(JsonWebKeyType keyType);
            WithImport withKey(JsonWebKey key);
        }

        interface WithKeyOperations {
            WithCreate withKeyOperations(List<JsonWebKeyOperation> keyOperations);
        }

        interface WithHsm {
            WithImport withHsm(boolean isHsm);
        }

        interface WithAttributes {
            WithCreate withAttributes(Attributes attributes);
        }

        interface WithTags {
            WithCreate withTags(Map<String, String> tags);
        }

        interface WithCreate extends
                Creatable<Key>,
                WithKeyOperations,
                WithAttributes,
                WithTags {

        }

        interface WithImport extends
                Creatable<Key>,
                WithHsm,
                WithAttributes,
                WithTags {

        }
    }

    interface UpdateStages {
        interface WithKeyOperations {
            Update withKeyOperations(List<JsonWebKeyOperation> keyOperations);
        }

        interface WithAttributes {
            Update withAttributes(Attributes attributes);
        }

        interface WithTags {
            Update withTags(Map<String, String> tags);
        }
    }

    interface Update extends
            Appliable<Key>,
            UpdateStages.WithKeyOperations,
            UpdateStages.WithAttributes,
            UpdateStages.WithTags {
    }
}

