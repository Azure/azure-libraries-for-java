/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batchai.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.batchai.OutputFile;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.IndexableWrapperImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import org.joda.time.DateTime;

/**
 * Implementation for {@link OutputFile}.
 */
@LangDefinition
final class OutputFileImpl extends
        IndexableWrapperImpl<FileInner>
        implements
        OutputFile {

    OutputFileImpl(FileInner innerModel) {
        super(innerModel);
    }

    @Override
    public String downloadUrl() {
        return inner().downloadUrl();
    }

    @Override
    public DateTime lastModified() {
        return inner().lastModified();
    }

    @Override
    public long contentLength() {
        return Utils.toPrimitiveLong(inner().contentLength());
    }

    @Override
    public String name() {
        return inner().name();
    }
}
