/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network;

import com.microsoft.azure.management.apigeneration.Beta;
import com.microsoft.azure.management.apigeneration.Fluent;
import com.microsoft.azure.management.apigeneration.Method;
import com.microsoft.azure.management.network.implementation.ConnectionMonitorResultInner;
import com.microsoft.azure.management.network.model.HasNetworkInterfaces;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasId;
import com.microsoft.azure.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.management.resources.fluentcore.model.HasInner;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import org.joda.time.DateTime;
import rx.Completable;
import rx.Observable;

import java.util.Map;

/**
 * Client-side representation of Connection Monitor object, associated with Network Watcher.
 */
@Fluent
@Beta(Beta.SinceVersion.V1_10_0)
public interface ConnectionMonitor extends
        HasInner<ConnectionMonitorResultInner>,
        HasName,
        HasId,
        Indexable {
    /**
     * @return connection monitor location
     */
    String location();

    /**
     * @return connection monitor tags
     */
    Map<String, String> tags();

    /**
     * @return the source property
     */
    ConnectionMonitorSource source();

    /**
     * @return the destination property
     */
    ConnectionMonitorDestination destination();

    /**
     * Determines if the connection monitor will start automatically once
     * created.
     * @return true if the connection monitor will start automatically once created, false otherwise
     */
    boolean autoStart();
    /**
     * @return the provisioning state of the connection monitor
     */
    ProvisioningState provisioningState();

    /**
     * @return the date and time when the connection monitor was started
     */
    DateTime startTime();

    /**
     * @return the monitoring status of the connection monitor
     */
    String monitoringStatus();
    /**
     * @return monitoring interval in seconds
     */
    int monitoringIntervalInSeconds();
    /**
     * Stops a specified connection monitor.
     */
    @Method
    void stop();

    /**
     * Stops a specified connection monitor asynchronously.
     * @return the handle to the REST call
     */
    @Method
    Completable stopAsync();

    /**
     * Starts a specified connection monitor.
     */
    @Method
    void start();

    /**
     * Starts a specified connection monitor asynchronously.
     * @return the handle to the REST call
     */
    @Method
    Completable startAsync();

    /**
     * Query a snapshot of the most recent connection state of a connection monitor.
     * @return snapshot of the most recent connection state
     */
    @Method
    ConnectionMonitorQueryResult query();

    /**
     * Query a snapshot of the most recent connection state of a connection monitor asynchronously.
     * @return snapshot of the most recent connection state
     */
    @Method
    Observable<ConnectionMonitorQueryResult> queryAsync();

    /**
     * The entirety of the connection monitor definition.
     */
    interface Definition extends
            DefinitionStages.WithSource,
            DefinitionStages.WithDestination,
            DefinitionStages.WithDestinationPort,
            DefinitionStages.WithCreate {
    }

    /**
     * Grouping of connection monitor definition stages.
     */
    interface DefinitionStages {

        /**
         * Sets the source property.
         */
        interface WithSource {
            /**
             * @param resourceId the ID of the resource used as the source by connection monitor
             * @return next definition stage
             */
            WithDestination withSourceId(String resourceId);
            /**
             * @param vm virtual machine used as the source by connection monitor
             * @return next definition stage
             */
            WithDestination withSource(HasNetworkInterfaces vm);
        }

        /**
         * Sets the source port used by connection monitor.
         */
        interface WithSourcePort {
            /**
             * @param port source port used by connection monitor
             * @return next definition stage
             */
            WithDestination withSourcePort(int port);
        }

        /**
         * Sets the destination.
         */
        interface WithDestination {
            /**
             * @param resourceId the ID of the resource used as the source by connection monitor
             * @return next definition stage
             */
            WithDestinationPort withDestinationId(String resourceId);
            /**
             * @param vm virtual machine used as the source by connection monitor
             * @return next definition stage
             */
            WithDestinationPort withDestination(HasNetworkInterfaces vm);
            /**
             * @param address address of the connection monitor destination (IP or domain name)
             * @return next definition stage
             */
            WithDestinationPort withDestinationAddress(String address);
        }

        /**
         * Sets the destination port used by connection monitor.
         */
        interface WithDestinationPort {
            /**
             * @param port the ID of the resource used as the source by connection monitor
             * @return next definition stage
             */
            WithCreate withDestinationPort(int port);
        }

        /**
         * Determines if the connection monitor will start automatically once
         * created. By default it is started automatically.
         */
        interface WithAutoStart {
            /**
             * Disable auto start.
             * @return next definition stage
             */
            @Method
            WithCreate withoutAutoStart();
        }

        /**
         * Sets monitoring interval in seconds.
         */
        interface WithMonitoringInterval {
            /**
             * @param seconds monitoring interval in seconds
             * @return next definition stage
             */
            WithCreate withMonitoringInterval(int seconds);
        }

        /**
         * The stage of the connection monitor definition allowing to add or update tags.
         */
        interface WithTags {
            /**
             * Specifies tags for the connection monitor.
             * @param tags tags indexed by name
             * @return the next stage of the definition
             */
            WithCreate withTags(Map<String, String> tags);

            /**
             * Adds a tag to the connection monitor.
             * @param key the key for the tag
             * @param value the value for the tag
             * @return the next stage of the definition
             */
            WithCreate withTag(String key, String value);

            /**
             * Removes a tag from the connection monitor.
             * @param key the key of the tag to remove
             * @return the next stage of the definition
             */
            WithCreate withoutTag(String key);
        }

        interface WithCreate extends
                Creatable<ConnectionMonitor>,
                WithSourcePort,
                WithAutoStart,
                WithMonitoringInterval,
                WithTags {
        }
    }
}
