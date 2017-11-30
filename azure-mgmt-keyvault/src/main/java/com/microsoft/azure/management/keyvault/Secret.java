/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.PagedList;
import com.microsoft.azure.keyvault.SecretIdentifier;
import com.microsoft.azure.keyvault.models.Attributes;
import com.microsoft.azure.keyvault.models.SecretAttributes;
import com.microsoft.azure.keyvault.models.SecretBundle;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.Appliable;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;
import rx.Observable;

import java.util.Map;

/**
 * An immutable client-side representation of an Azure Key Vault.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
public interface Secret extends
        Indexable,
        HasInner<SecretBundle>,
        HasId,
        HasName,
        Updatable<Secret.Update> {
    /**
     * @return the secret value
     */
    String value();

    /**
     * @return the secret management attributes
     */
    SecretAttributes attributes();

    /**
     * @return application specific metadata in the form of key-value pairs
     */
    Map<String, String> tags();

    /**
     * @return type of the secret value such as a password
     */
    String contentType();

    /**
     * @return the corresponding key backing the KV certificate if this is a
     * secret backing a KV certificate
     */
    String kid();

    /**
     * @return true if the secret's lifetime is managed by key vault. If this is a key
     * backing a certificate, then managed will be true
     */
    boolean managed();

    PagedList<SecretIdentifier> listVersions();

    Observable<SecretIdentifier> listVersionsAsync();

    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.WithValue,
            DefinitionStages.WithCreate {
    }

    interface DefinitionStages {
        interface Blank extends WithValue {
        }

        interface WithValue {
            WithCreate withValue(String value);
        }

        interface WithContentType {
            WithCreate withContentType(String contentType);
        }

        interface WithAttributes {
            WithCreate withAttributes(Attributes attributes);
        }

        interface WithTags {
            WithCreate withTags(Map<String, String> tags);
        }

        interface WithCreate extends
                Creatable<Secret>,
                WithContentType,
                WithAttributes,
                WithTags {

        }
    }

    interface UpdateStages {
        interface WithValue {
            Update withValue(String value);
        }

        interface WithVersion {
            Update withVersion(String version);
        }

        interface WithContentType {
            Update withContentType(String contentType);
        }

        interface WithAttributes {
            Update withAttributes(Attributes attributes);
        }

        interface WithTags {
            Update withTags(Map<String, String> tags);
        }
    }

    interface Update extends
            Appliable<Secret>,
            UpdateStages.WithValue,
            UpdateStages.WithVersion,
            UpdateStages.WithAttributes,
            UpdateStages.WithContentType,
            UpdateStages.WithTags {
    }
}

