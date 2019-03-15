package com.microsoft.azure.management.storage;

public interface StoragePolicy {

    interface DefinitionStages {
        interface Blank {
            RuleDefinitionAttachable withVersion(String version);
        }

        interface RuleDefinitionAttachable {

        }
    }
}
