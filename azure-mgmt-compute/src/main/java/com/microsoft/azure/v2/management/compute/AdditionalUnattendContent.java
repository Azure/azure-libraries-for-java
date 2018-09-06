/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.v2.management.compute;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Specifies additional XML formatted information that can be included in the
 * Unattend.xml file, which is used by Windows Setup. Contents are defined by
 * setting name, component name, and the pass in which the content is applied.
 */
public final class AdditionalUnattendContent {
    /**
     * The pass name. Currently, the only allowable value is OobeSystem.
     * Possible values include: 'OobeSystem'.
     */
    @JsonProperty(value = "passName")
    private PassNames passName;

    /**
     * The component name. Currently, the only allowable value is
     * Microsoft-Windows-Shell-Setup. Possible values include:
     * 'Microsoft-Windows-Shell-Setup'.
     */
    @JsonProperty(value = "componentName")
    private ComponentNames componentName;

    /**
     * Specifies the name of the setting to which the content applies. Possible
     * values are: FirstLogonCommands and AutoLogon. Possible values include:
     * 'AutoLogon', 'FirstLogonCommands'.
     */
    @JsonProperty(value = "settingName")
    private SettingNames settingName;

    /**
     * Specifies the XML formatted content that is added to the unattend.xml
     * file for the specified path and component. The XML must be less than 4KB
     * and must include the root element for the setting or feature that is
     * being inserted.
     */
    @JsonProperty(value = "content")
    private String content;

    /**
     * Get the passName value.
     *
     * @return the passName value.
     */
    public PassNames passName() {
        return this.passName;
    }

    /**
     * Set the passName value.
     *
     * @param passName the passName value to set.
     * @return the AdditionalUnattendContent object itself.
     */
    public AdditionalUnattendContent withPassName(PassNames passName) {
        this.passName = passName;
        return this;
    }

    /**
     * Get the componentName value.
     *
     * @return the componentName value.
     */
    public ComponentNames componentName() {
        return this.componentName;
    }

    /**
     * Set the componentName value.
     *
     * @param componentName the componentName value to set.
     * @return the AdditionalUnattendContent object itself.
     */
    public AdditionalUnattendContent withComponentName(ComponentNames componentName) {
        this.componentName = componentName;
        return this;
    }

    /**
     * Get the settingName value.
     *
     * @return the settingName value.
     */
    public SettingNames settingName() {
        return this.settingName;
    }

    /**
     * Set the settingName value.
     *
     * @param settingName the settingName value to set.
     * @return the AdditionalUnattendContent object itself.
     */
    public AdditionalUnattendContent withSettingName(SettingNames settingName) {
        this.settingName = settingName;
        return this;
    }

    /**
     * Get the content value.
     *
     * @return the content value.
     */
    public String content() {
        return this.content;
    }

    /**
     * Set the content value.
     *
     * @param content the content value to set.
     * @return the AdditionalUnattendContent object itself.
     */
    public AdditionalUnattendContent withContent(String content) {
        this.content = content;
        return this;
    }
}
