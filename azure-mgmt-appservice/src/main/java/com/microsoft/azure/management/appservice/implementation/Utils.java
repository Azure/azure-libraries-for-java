/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.appservice.implementation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Utilities for AppService implementation.
 */
class Utils {

    /**
     * Completes docker image and tag with registry server.
     *
     * @param imageAndTag docker image and tag
     * @param serverUrl private registry server URL
     * @return docker image and tag completed with registry server
     */
    public static String smartCompletionPrivateRegistryImage(String imageAndTag, String serverUrl) {
        try {
            URL url = new URL(serverUrl);
            String registryServer = url.getAuthority();
            if (!registryServer.isEmpty() && !imageAndTag.trim().startsWith(registryServer)) {
                String[] segments = imageAndTag.split(Pattern.quote("/"));
                if (segments.length == 1) {
                    // it appears that imageAndTag does not contain registry server, add registry server before it.
                    imageAndTag = String.format("%s/%s", registryServer, imageAndTag);
                }
                if (segments.length > 1) {
                    String segment = segments[0];
                    if (!segment.isEmpty() && !segment.contains(".") && !segment.contains(":") && !segment.equals(registryServer)) {
                        // it appears that first segment of imageAndTag is not registry server, add registry server before it.
                        imageAndTag = String.format("%s/%s", registryServer, imageAndTag);
                    }
                }
            }
        } catch (MalformedURLException e) {
            // serverUrl is probably incorrect, abort
        }
        return imageAndTag;
    }
}
