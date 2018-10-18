package com.microsoft.azure.management.storage;

public class OverridingValue {
    private final String value;
    private final boolean isSecret;

    public OverridingValue(String value, boolean isSecret){
        this.value = value;
        this.isSecret = isSecret;
    }

    public String value(){
        return this.value;
    }

    public boolean isSecret(){
        return this.isSecret;
    }
}
