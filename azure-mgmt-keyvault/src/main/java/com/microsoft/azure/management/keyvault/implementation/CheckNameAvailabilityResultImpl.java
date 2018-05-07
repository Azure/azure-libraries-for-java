package com.microsoft.azure.management.keyvault.implementation;

import com.microsoft.azure.management.keyvault.CheckNameAvailabilityResult;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;


public class CheckNameAvailabilityResultImpl extends WrapperImpl<CheckNameAvailabilityResultInner> implements CheckNameAvailabilityResult{

    protected CheckNameAvailabilityResultImpl(CheckNameAvailabilityResultInner innerObject) {
        super(innerObject);
    }

}
