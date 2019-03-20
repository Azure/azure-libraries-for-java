/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.resources.fluentcore.model.Attachable;

public interface BaseBlobActions {

    boolean tierToCoolActionEnabled();
    boolean tierToArchiveActionEnabled();
    boolean deleteActionEnabled();
    Integer daysAfterModificationUntilCooling();
    Integer daysAfterModificationUntilArchiving();
    Integer daysAfterModificationUntilDelete();

    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.BaseBlobActionsAttachable {
    }
    interface DefinitionStages {
        interface Blank extends BaseBlobActionsAttachable{
        }

        interface BaseBlobActionsAttachable extends Attachable<PolicyRule.DefinitionStages.PolicyRuleAttachable> {
            BaseBlobActionsAttachable withTierToCoolAction(int daysAfterModificationUntilCooling);
            BaseBlobActionsAttachable withTierToArchiveAction(int daysAfterModificationUntilArchiving);
            BaseBlobActionsAttachable withDeleteAction(int daysAfterModificationUntilDelete);
        }
    }

}
