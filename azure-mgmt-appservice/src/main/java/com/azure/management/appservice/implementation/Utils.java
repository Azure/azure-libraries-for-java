/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice.implementation;

import org.apache.commons.lang3.StringUtils;

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
    static String smartCompletionPrivateRegistryImage(String imageAndTag, String serverUrl) {
        try {
            URL url = new URL(serverUrl);
            String registryServer = url.getAuthority();
            String path = url.getPath();
            if (!registryServer.isEmpty() && !imageAndTag.trim().startsWith(registryServer)) {
                String[] segments = imageAndTag.split(Pattern.quote("/"));
                if (segments.length == 1) {
                    // it appears that imageAndTag does not contain registry server, add registry server before it.
                    imageAndTag = completePrivateRegistryImage(imageAndTag, registryServer, path);
                }
                if (segments.length > 1) {
                    String segment = segments[0];
                    if (!segment.isEmpty() && !segment.contains(".") && !segment.contains(":") && !segment.equals(registryServer)) {
                        // it appears that first segment of imageAndTag is not registry server, add registry server before it.
                        imageAndTag = completePrivateRegistryImage(imageAndTag, registryServer, path);
                    }
                }
            }
        } catch (MalformedURLException e) {
            // serverUrl is probably incorrect, abort
        }
        return imageAndTag;
    }

    private static String completePrivateRegistryImage(String imageAndTag, String registryServer, String path) {
        path = StringUtils.strip(path, "/");
        if (path.isEmpty()) {
            imageAndTag = String.format("%s/%s", registryServer, imageAndTag.trim());
        } else {
            imageAndTag = String.format("%s/%s/%s", registryServer, path, imageAndTag.trim());
        }
        return imageAndTag;
    }
}
