/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator 0.15.0.0
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.management.compute;

import com.microsoft.azure.CloudException;
import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.management.compute.models.PageImpl;
import com.microsoft.azure.management.compute.models.VirtualMachineScaleSetVM;
import com.microsoft.azure.management.compute.models.VirtualMachineScaleSetVMInstanceView;
import com.microsoft.rest.ServiceCall;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceResponse;
import java.io.IOException;
import java.util.List;

/**
 * An instance of this class provides access to all the operations defined
 * in VirtualMachineScaleSetVMsOperations.
 */
public interface VirtualMachineScaleSetVMsOperations {
    /**
     * The operation to re-image a virtual machine scale set instance.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @throws InterruptedException exception thrown when long running operation is interrupted
     * @return the {@link ServiceResponse} object if successful.
     */
    ServiceResponse<Void> reimage(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException, InterruptedException;

    /**
     * The operation to re-image a virtual machine scale set instance.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall reimageAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to re-image a virtual machine scale set instance.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the {@link ServiceResponse} object if successful.
     */
    ServiceResponse<Void> beginReimage(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException;

    /**
     * The operation to re-image a virtual machine scale set instance.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall beginReimageAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to deallocate a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @throws InterruptedException exception thrown when long running operation is interrupted
     * @return the {@link ServiceResponse} object if successful.
     */
    ServiceResponse<Void> deallocate(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException, InterruptedException;

    /**
     * The operation to deallocate a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall deallocateAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to deallocate a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the {@link ServiceResponse} object if successful.
     */
    ServiceResponse<Void> beginDeallocate(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException;

    /**
     * The operation to deallocate a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall beginDeallocateAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to delete a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @throws InterruptedException exception thrown when long running operation is interrupted
     * @return the {@link ServiceResponse} object if successful.
     */
    ServiceResponse<Void> delete(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException, InterruptedException;

    /**
     * The operation to delete a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall deleteAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to delete a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the {@link ServiceResponse} object if successful.
     */
    ServiceResponse<Void> beginDelete(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException;

    /**
     * The operation to delete a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall beginDeleteAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to get a virtual machine scale set virtual machine.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the VirtualMachineScaleSetVM object wrapped in {@link ServiceResponse} if successful.
     */
    ServiceResponse<VirtualMachineScaleSetVM> get(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException;

    /**
     * The operation to get a virtual machine scale set virtual machine.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall getAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<VirtualMachineScaleSetVM> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to get a virtual machine scale set virtual machine.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the VirtualMachineScaleSetVMInstanceView object wrapped in {@link ServiceResponse} if successful.
     */
    ServiceResponse<VirtualMachineScaleSetVMInstanceView> getInstanceView(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException;

    /**
     * The operation to get a virtual machine scale set virtual machine.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall getInstanceViewAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<VirtualMachineScaleSetVMInstanceView> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to list virtual machine scale sets VMs.
     *
     * @param resourceGroupName The name of the resource group.
     * @param virtualMachineScaleSetName The name of the virtual machine scale set.
     * @param filter The filter to apply on the operation.
     * @param select The list parameters.
     * @param expand The expand expression to apply on the operation.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the List&lt;VirtualMachineScaleSetVM&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    ServiceResponse<List<VirtualMachineScaleSetVM>> list(final String resourceGroupName, final String virtualMachineScaleSetName, final VirtualMachineScaleSetVM filter, final String select, final String expand) throws CloudException, IOException, IllegalArgumentException;

    /**
     * The operation to list virtual machine scale sets VMs.
     *
     * @param resourceGroupName The name of the resource group.
     * @param virtualMachineScaleSetName The name of the virtual machine scale set.
     * @param filter The filter to apply on the operation.
     * @param select The list parameters.
     * @param expand The expand expression to apply on the operation.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall listAsync(final String resourceGroupName, final String virtualMachineScaleSetName, final VirtualMachineScaleSetVM filter, final String select, final String expand, final ListOperationCallback<VirtualMachineScaleSetVM> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to power off (stop) a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @throws InterruptedException exception thrown when long running operation is interrupted
     * @return the {@link ServiceResponse} object if successful.
     */
    ServiceResponse<Void> powerOff(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException, InterruptedException;

    /**
     * The operation to power off (stop) a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall powerOffAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to power off (stop) a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the {@link ServiceResponse} object if successful.
     */
    ServiceResponse<Void> beginPowerOff(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException;

    /**
     * The operation to power off (stop) a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall beginPowerOffAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to restart a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @throws InterruptedException exception thrown when long running operation is interrupted
     * @return the {@link ServiceResponse} object if successful.
     */
    ServiceResponse<Void> restart(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException, InterruptedException;

    /**
     * The operation to restart a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall restartAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to restart a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the {@link ServiceResponse} object if successful.
     */
    ServiceResponse<Void> beginRestart(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException;

    /**
     * The operation to restart a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall beginRestartAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to start a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @throws InterruptedException exception thrown when long running operation is interrupted
     * @return the {@link ServiceResponse} object if successful.
     */
    ServiceResponse<Void> start(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException, InterruptedException;

    /**
     * The operation to start a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall startAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to start a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the {@link ServiceResponse} object if successful.
     */
    ServiceResponse<Void> beginStart(String resourceGroupName, String vmScaleSetName, String instanceId) throws CloudException, IOException, IllegalArgumentException;

    /**
     * The operation to start a virtual machine scale set.
     *
     * @param resourceGroupName The name of the resource group.
     * @param vmScaleSetName The name of the virtual machine scale set.
     * @param instanceId The instance id of the virtual machine.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall beginStartAsync(String resourceGroupName, String vmScaleSetName, String instanceId, final ServiceCallback<Void> serviceCallback) throws IllegalArgumentException;

    /**
     * The operation to list virtual machine scale sets VMs.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws CloudException exception thrown from REST call
     * @throws IOException exception thrown from serialization/deserialization
     * @throws IllegalArgumentException exception thrown from invalid parameters
     * @return the List&lt;VirtualMachineScaleSetVM&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    ServiceResponse<PageImpl<VirtualMachineScaleSetVM>> listNext(final String nextPageLink) throws CloudException, IOException, IllegalArgumentException;

    /**
     * The operation to list virtual machine scale sets VMs.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceCall the ServiceCall object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if callback is null
     * @return the {@link ServiceCall} object
     */
    ServiceCall listNextAsync(final String nextPageLink, final ServiceCall serviceCall, final ListOperationCallback<VirtualMachineScaleSetVM> serviceCallback) throws IllegalArgumentException;

}
