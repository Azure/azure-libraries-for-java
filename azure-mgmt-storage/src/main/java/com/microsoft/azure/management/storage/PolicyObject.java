package com.microsoft.azure.management.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PolicyObject {

    @JsonProperty(value = "version")
    public String version;

    @JsonProperty(value = "rules")
    public List<PolicyRule> rules;

    public static class PolicyRule {
        @JsonProperty(value = "name")
        public String name;

        @JsonProperty(value = "type")
        public String type;

        @JsonProperty(value = "definition")
        public RuleDefinition definition;

        public static class RuleDefinition {
            @JsonProperty(value = "filters")
            public RuleFilters filters;

            @JsonProperty(value = "actions")
            public RuleActions actions;

            public static class RuleFilters {
                @JsonProperty(value = "blobTypes")
                public List<BlobTypes> blobTypes;

                @JsonProperty(value = "prefixMatches")
                public List<String> prefixMatches;

                public enum BlobTypes {
                    /** Enum value blockBlob. */
                    BLOCKBLOB("blockBlob");

                    /** The actual serialized value for a PublicAccess instance. */
                    private String value;

                    BlobTypes(String value) {
                        this.value = value;
                    }

                    /**
                     * Parses a serialized value to a PublicAccess instance.
                     *
                     * @param value the serialized value to parse.
                     * @return the parsed PublicAccess object, or null if unable to parse.
                     */
                    @JsonCreator
                    public static BlobTypes fromString(String value) {
                        BlobTypes[] items = BlobTypes.values();
                        for (BlobTypes item : items) {
                            if (item.toString().equalsIgnoreCase(value)) {
                                return item;
                            }
                        }
                        return null;
                    }

                    @JsonValue
                    @Override
                    public String toString() {
                        return this.value;
                    }
                }
            }

            public static class RuleActions {
                @JsonProperty(value = "baseBlob")
                public Map<RuleAction, RuleActionCondition> baseBlobConditions;

                @JsonProperty(value = "snapshot")
                public Map<RuleAction, RuleActionCondition> snapshotConditions;

                public enum RuleAction {
                    /** Enum value tierToCool. */
                    TIERTOCOOL("tierToCool"),

                    /** Enum value tierToArchive. */
                    TIERTOARCHIVE("tierToArchive"),

                    /** Enum value delete. */
                    DELETE("delete");

                    /** The actual serialized value for a PublicAccess instance. */
                    private String value;

                    RuleAction(String value) {
                        this.value = value;
                    }

                    /**
                     * Parses a serialized value to a PublicAccess instance.
                     *
                     * @param value the serialized value to parse.
                     * @return the parsed PublicAccess object, or null if unable to parse.
                     */
                    @JsonCreator
                    public static RuleAction fromString(String value) {
                        RuleAction[] items = RuleAction.values();
                        for (RuleAction item : items) {
                            if (item.toString().equalsIgnoreCase(value)) {
                                return item;
                            }
                        }
                        return null;
                    }

                    @JsonValue
                    @Override
                    public String toString() {
                        return this.value;
                    }
                }

                public static class RuleActionCondition {
                    @JsonProperty(value = "daysAfterModificationGreaterThan")
                    public Integer daysAfterModificationGreaterThan;

                    @JsonProperty(value = "daysAfterCreationGreaterThan")
                    public Integer daysAfterCreationGreaterThan;

                }
            }
        }
    }
}
