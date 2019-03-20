package com.microsoft.azure.management.storage;

import com.microsoft.azure.management.resources.fluentcore.model.Attachable;

import java.util.List;

public interface PolicyRule {

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
}
