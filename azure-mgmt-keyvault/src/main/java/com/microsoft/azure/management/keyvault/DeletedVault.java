package com.microsoft.azure.management.keyvault;

import java.util.Map;

import org.joda.time.DateTime;

import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.keyvault.implementation.DeletedVaultInner;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;

/**
 * An immutable client-side representation of an Azure Key Vault.
 */
@Fluent(ContainerName = "/Microsoft.Azure.Management.Fluent.KeyVault")
public interface DeletedVault extends
	HasInner<DeletedVaultInner>,
	HasName,
	HasId {
    
    /**
     * Get the location value.
     *
     * @return the location value
     */
    public String location();
    
    /**
     * Get the deletionDate value.
     *
     * @return the deletionDate value
     */
    public DateTime deletionDate();
    
    /**
     * Get the scheduledPurgeDate value.
     *
     * @return the scheduledPurgeDate value
     */
    public DateTime scheduledPurgeDate();
    
    /**
     * Get the tags value.
     *
     * @return the tags value
     */
    public Map<String, String> tags();
}
