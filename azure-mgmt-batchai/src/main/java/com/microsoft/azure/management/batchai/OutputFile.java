/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.batchai;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.batchai.implementation.FileInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import org.joda.time.DateTime;

/**
 * An immutable client-side representation of Batch AI output file.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_6_0)
public interface OutputFile extends
        Indexable,
        HasInner<FileInner>,
        HasName {
    /**
     * @return file downloand url, example:
     * https://mystg.blob.core.windows.net/mycontainer/myModel_1.dnn.
     * This will be returned only if the model has been archived. During job
     * run, this won't be returned and customers can use SSH tunneling to
     * download. Users can use Get Remote Login Information API to get the IP
     * address and port information of all the compute nodes running the job.
     */
    String downloadUrl();

    /**
     * @return the time at which the file was last modified.
     */
    DateTime lastModified();

    /**
     * @return the file size.
     */
    long contentLength();

    /**
     * @return information about file type
     */
    FileType fileType();
}