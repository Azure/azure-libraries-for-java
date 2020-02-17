/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.appservice.implementation;

import com.azure.management.appservice.DomainLegalAgreement;
import com.azure.management.apigeneration.LangDefinition;
import com.azure.management.resources.Tenant;
import com.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

/**
 * Implementation for {@link Tenant}.
 */
@LangDefinition(ContainerName = "/Microsoft.Azure.Management.AppService.Fluent")
final class DomainLegalAgreementImpl extends
        WrapperImpl<TldLegalAgreementInner>
        implements
        DomainLegalAgreement {

    DomainLegalAgreementImpl(TldLegalAgreementInner innerModel) {
        super(innerModel);
    }

    @Override
    public String agreementKey() {
        return inner().agreementKey();
    }

    @Override
    public String title() {
        return inner().title();
    }

    @Override
    public String content() {
        return inner().content();
    }

    @Override
    public String url() {
        return inner().url();
    }
}
