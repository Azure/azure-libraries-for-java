/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.compute;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The UserArtifactManage model.
 */
public class UserArtifactManage {
    /**
     * Required. The path and arguments to install the gallery application.
     * This is limited to 4096 characters.
     */
    @JsonProperty(value = "install", required = true)
    private String install;

    /**
     * Required. The path and arguments to remove the gallery application. This
     * is limited to 4096 characters.
     */
    @JsonProperty(value = "remove", required = true)
    private String remove;

    /**
     * Optional. The path and arguments to update the gallery application. If
     * not present, then update operation will invoke remove command on the
     * previous version and install command on the current version of the
     * gallery application. This is limited to 4096 characters.
     */
    @JsonProperty(value = "update")
    private String update;

    /**
     * Get required. The path and arguments to install the gallery application. This is limited to 4096 characters.
     *
     * @return the install value
     */
    public String install() {
        return this.install;
    }

    /**
     * Set required. The path and arguments to install the gallery application. This is limited to 4096 characters.
     *
     * @param install the install value to set
     * @return the UserArtifactManage object itself.
     */
    public UserArtifactManage withInstall(String install) {
        this.install = install;
        return this;
    }

    /**
     * Get required. The path and arguments to remove the gallery application. This is limited to 4096 characters.
     *
     * @return the remove value
     */
    public String remove() {
        return this.remove;
    }

    /**
     * Set required. The path and arguments to remove the gallery application. This is limited to 4096 characters.
     *
     * @param remove the remove value to set
     * @return the UserArtifactManage object itself.
     */
    public UserArtifactManage withRemove(String remove) {
        this.remove = remove;
        return this;
    }

    /**
     * Get optional. The path and arguments to update the gallery application. If not present, then update operation will invoke remove command on the previous version and install command on the current version of the gallery application. This is limited to 4096 characters.
     *
     * @return the update value
     */
    public String update() {
        return this.update;
    }

    /**
     * Set optional. The path and arguments to update the gallery application. If not present, then update operation will invoke remove command on the previous version and install command on the current version of the gallery application. This is limited to 4096 characters.
     *
     * @param update the update value to set
     * @return the UserArtifactManage object itself.
     */
    public UserArtifactManage withUpdate(String update) {
        this.update = update;
        return this;
    }

}
