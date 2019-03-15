package com.microsoft.azure.management.storage;

import java.util.List;

public interface StoragePolicyRule {
    interface DefinitionStages {
        interface Blank extends RuleName {
        }

        interface RuleVersion {
            RuleName withVersion(String version);
        }

        interface RuleName {
            RuleType withName(String name);
        }

        interface RuleType {
            BlobTypesToFilter withType(String ruleType);
        }

        interface BlobTypesToFilter {
            Prefixes withBlobTypesToFilterFor(List<String> blobTypes);
        }

        interface Prefixes {
            BaseBlobActions withPrefixesToFilterFor(List<String> prefixes);
        }

        interface BaseBlobActions {

        }
    }
}
