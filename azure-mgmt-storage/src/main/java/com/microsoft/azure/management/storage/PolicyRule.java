package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Settable;

import java.util.List;

public interface PolicyRule extends
        HasInner<ManagementPolicyRule> {

    String name();
    String type();
    List<String> blobTypesToFilterFor();
    List<String> prefixesToFilterFor();
    ManagementPolicyBaseBlob actionsOnBaseBlob();
    ManagementPolicySnapShot actionsOnSnapShot();
    boolean tierToCoolActionOnBaseBlobEnabled();
    boolean tierToArchiveActionOnBaseBlobEnabled();
    boolean deleteActionOnBaseBlobEnabled();
    boolean deleteActionOnSnapShotEnabled();
    Integer daysAfterBaseBlobModificationUntilCooling();
    Integer daysAfterBaseBlobModificationUntilArchiving();
    Integer daysAfterBaseBlobModificationUntilDeleting();
    Integer daysAfterSnapShotCreationUntilDeleting();

    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.PolicyRuleType,
            DefinitionStages.BlobTypesToFilterFor,
            DefinitionStages.PrefixesToFilterFor,
            DefinitionStages.RuleActions,
            DefinitionStages.PolicyRuleAttachable {
    }

    interface Update extends
            UpdateStages.Name,
            UpdateStages.Type,
            UpdateStages.BlobTypesToFilterFor,
            UpdateStages.PrefixesToFilterFor,
            UpdateStages.Actions,
            Settable<ManagementPolicy.Update> {
    }

    interface DefinitionStages {
        interface Blank extends PolicyRuleType{
        }

        interface PolicyRuleType {
            BlobTypesToFilterFor withType(String type);
        }

        interface BlobTypesToFilterFor {
            PolicyRuleAttachable withBlobTypesToFilterFor(List<String> blobTypes);
            PolicyRuleAttachable withBlobTypeToFilterFor(String blobType);
        }

        interface PrefixesToFilterFor {
            PolicyRuleAttachable withPrefixesToFilterFor(List<String> prefixes);
            PolicyRuleAttachable withPrefixToFilterFor(String prefix);
        }

        interface RuleActions {
            PolicyRuleAttachable withTierToCoolActionOnBaseBlob(int daysAfterBaseBlobModificationUntilCooling);
            PolicyRuleAttachable withTierToArchiveActionOnBaseBlob(int daysAfterBaseBlobModificationUntilArchiving);
            PolicyRuleAttachable withDeleteActionOnBaseBlob(int daysAfterBaseBlobModificationUntilDeleting);
            PolicyRuleAttachable withDeleteActionOnSnapShot(int daysAfterSnapShotCreationUntilDeleting);
            PolicyRuleAttachable withActionsOnBaseBlob(ManagementPolicyBaseBlob baseBlobActions);
            PolicyRuleAttachable withActionsOnSnapShot(ManagementPolicySnapShot snapShotActions);
        }

        interface PolicyRuleAttachable extends PolicyRule.DefinitionStages.RuleActions,
                PolicyRule.DefinitionStages.PrefixesToFilterFor,
                Attachable<ManagementPolicy.DefinitionStages.WithCreate>  {

        }
    }

    interface UpdateStages {
        interface Name {
            Update withName(String ruleName);
        }
        interface Type {
            Update withType(String type);
        }
        interface BlobTypesToFilterFor {
            Update withBlobTypesToFilterFor(List<String> blobTypes);
            Update withBlobTypeToFilterFor(String blobType);
            Update withoutBlobTypesToFilterFor();
        }
        interface PrefixesToFilterFor {
            Update withPrefixesToFilterFor(List<String> prefixes);
            Update withPrefixToFilterFor(String prefix);
            Update withoutPrefixesToFilterFor();
        }
        interface Actions {
            Update withTierToCoolActionOnBaseBlob(int daysAfterBaseBlobModificationUntilCooling);
            Update withTierToArchiveActionOnBaseBlob(int daysAfterBaseBlobModificationUntilArchiving);
            Update withDeleteActionOnBaseBlob(int daysAfterBaseBlobModificationUntilDeleting);
            Update withDeleteActionOnSnapShot(int daysAfterSnapShotCreationUntilDeleting);
            Update updateActionsOnBaseBlob(ManagementPolicyBaseBlob baseBlobActions);
            Update updateActionsOnSnapShot(ManagementPolicySnapShot snapShotActions);
        }
    }
}