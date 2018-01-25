/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.containerregistry.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.containerregistry.Registry;
import com.microsoft.azure.management.containerregistry.Webhook;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ExternalChildResourcesNonCachedImpl;

/**
 * Represents a webhook collection associated with a container registry.
 */
@LangDefinition
public class WebhooksImpl
    extends ExternalChildResourcesNonCachedImpl<WebhookImpl,
                                                Webhook,
                                                WebhookInner,
                                                RegistryImpl,
                                                Registry> {

    /**
     * Creates a new ExternalNonInlineChildResourcesImpl.
     *
     * @param parent            the parent Azure resource
     * @param childResourceName the child resource name
     */
    protected WebhooksImpl(RegistryImpl parent, String childResourceName) {
        super(parent, parent.taskGroup(), childResourceName);
    }

    WebhookImpl defineWebhook(String name) {
        return prepareInlineDefine(new WebhookImpl(name, this.parent(), new WebhookInner(), this.parent().manager()).setCreateMode(true));
    }

    WebhookImpl updateWebhook(String name) {
        return prepareInlineUpdate(new WebhookImpl(name, this.parent(), new WebhookInner(), this.parent().manager()).setCreateMode(false));
    }

    void withoutWebhook(String name) {
        prepareInlineRemove(new WebhookImpl(name, this.parent(), new WebhookInner(), this.parent().manager()).setCreateMode(false));
    }
}
