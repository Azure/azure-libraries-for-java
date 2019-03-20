/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.resources.fluentcore.model.Attachable;

public interface SnapshotActions {

    boolean deleteActionEnabled();
    Integer daysAfterCreationUntilDelete();

    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.SnapshotActionsAttachable {
    }
    interface DefinitionStages {
        interface Blank extends SnapshotActionsAttachable {
        }

        interface SnapshotActionsAttachable extends Attachable<PolicyRule.DefinitionStages.PolicyRuleAttachable> {
            SnapshotActionsAttachable withDeleteAction(int daysAfterCreationUntilDelete);
        }
    }
}
