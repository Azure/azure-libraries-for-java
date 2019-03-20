package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.resources.fluentcore.model.Attachable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Updatable;

import java.util.List;

public interface PolicyRule extends
        HasInner<ManagementPolicyRule>,
        Updatable<PolicyRule.Update> {

    String name();
    String type();
    List<String> blobTypesToFilterFor();
    List<String> prefixesToFilterFor();
    BaseBlobActions actionsOnBaseBlob();
    SnapshotActions actionsOnSnapshot();

    interface Definition extends
            DefinitionStages.Blank,
            DefinitionStages.PolicyRuleName,
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
            UpdateStages.Actions {
    }

    interface DefinitionStages {
        interface Blank extends PolicyRuleName{
        }

        interface PolicyRuleName {
            PolicyRuleType withName(String ruleName);
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
            BaseBlobActions.DefinitionStages.Blank defineActionsOnBaseBlob();
            SnapshotActions.DefinitionStages.Blank defineActionsOnSnapshot();
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
            BaseBlobActions.Update updateActionsOnBaseBlob();
            Update updateActionsOnBaseBlob(BaseBlobActions baseBlobActions);
            SnapshotActions.Update updateActionsOnSnapshot();
            Update updateActionsOnSnapshot(SnapshotActions snapshotActions);
        }
    }
}