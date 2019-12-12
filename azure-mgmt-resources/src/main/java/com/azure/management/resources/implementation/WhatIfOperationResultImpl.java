/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.implementation;

import com.azure.management.resources.ErrorResponse;
import com.azure.management.resources.WhatIfChange;
import com.azure.management.resources.WhatIfOperationResult;
import com.azure.management.resources.ErrorResponse;
import com.azure.management.resources.WhatIfChange;
import com.azure.management.resources.WhatIfOperationResult;
import com.azure.management.resources.fluentcore.model.implementation.WrapperImpl;

import java.util.Collections;
import java.util.List;

/**
 * Implementation for {@link WhatIfOperationResult}.
 */
public class WhatIfOperationResultImpl extends
        WrapperImpl<WhatIfOperationResultInner>
        implements
        WhatIfOperationResult {

    WhatIfOperationResultImpl(WhatIfOperationResultInner inner) {
        super(inner);
    }

    @Override
    public String status() {
        return this.inner().status();
    }

    @Override
    public List<WhatIfChange> changes() {
        return Collections.unmodifiableList(this.inner().changes());
    }

    @Override
    public ErrorResponse error() {
        return this.inner().error();
    }
}
