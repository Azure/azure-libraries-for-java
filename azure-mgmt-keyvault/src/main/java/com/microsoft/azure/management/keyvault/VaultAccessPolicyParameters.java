package com.microsoft.azure.management.keyvault;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.keyvault.implementation.VaultAccessPolicyParametersInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * Parameters for updating the access policy in a vault.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
public interface VaultAccessPolicyParameters extends
    HasInner<VaultAccessPolicyParametersInner>,
    HasId,
    HasName {

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public String type();
    
    /**
     * Get the location value.
     *
     * @return the location value
     */
    public String location();
}
