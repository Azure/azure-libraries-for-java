/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.keyvault.implementation;

public class KeyVaultUtils {

    public static String nameFromId(String id) {
        String[] parts = id.split("/");
        return parts[parts.length - 1];
    }

    public static String vaultUrlFromid(String id) {
        String[] parts = id.replace("https://", "").split("/");
        return parts[0];
    }
}
