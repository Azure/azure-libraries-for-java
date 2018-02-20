package com.microsoft.azure.management.datalake.analytics.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubResource {
    @JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
    private String type;

    public String id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public String type() {
        return this.type;
    }
}
