/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.trafficmanager;

/**
 * An immutable client-side representation of an Azure traffic manager profile external endpoint.
 */
public interface ExternalEndpoint extends Endpoint {
    /**
     * @return the fully qualified DNS name of the external endpoint.
     */
    String fqdn();
}
