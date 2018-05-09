package com.microsoft.azure.management.keyvault;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.keyvault.implementation.CheckNameAvailabilityResultInner;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * The CheckNameAvailability operation response wrapper.
 *
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
public interface CheckNameAvailabilityResult extends
    HasInner<CheckNameAvailabilityResultInner>{
    
    /**
     * Get the nameAvailable value.
     *
     * @return the nameAvailable value
     */
    public Boolean nameAvailable();
    
    /**
     * Get the reason value.
     *
     * @return the reason value
     */
    public Reason reason();
    
    /**
     * Get the message value.
     *
     * @return the message value
     */
    public String message();

}
