/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.utils;

import org.joda.time.DateTime;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.io.File;
import java.io.IOException;

/**
 * The class to contain the common factory methods required for SDK framework.
 */
public class SdkContext {
    private static ResourceNamerFactory resourceNamerFactory = new ResourceNamerFactory();
    private static DelayProvider delayProvider = new DelayProvider();
    private static Scheduler rxScheduler = Schedulers.io();
    private static FileProvider fileProvider = new FileProvider();

    /**
     * Function to override the ResourceNamerFactory.
     *
     * @param resourceNamerFactory factory to override.
     */
    public static void setResourceNamerFactory(ResourceNamerFactory resourceNamerFactory) {
        SdkContext.resourceNamerFactory = resourceNamerFactory;
    }

    /**
     * Gets the current factory for ResourceNamer.
     * @return resourceNamer factory.
     */
    public static ResourceNamerFactory getResourceNamerFactory() {
        return SdkContext.resourceNamerFactory;
    }

    /**
     * Gets a random name.
     *
     * @param prefix the prefix to be used if possible
     * @param maxLen the maximum length for the random generated name
     * @return the random name
     */
    public static String randomResourceName(String prefix, int maxLen) {
        ResourceNamer resourceNamer = SdkContext.getResourceNamerFactory().createResourceNamer("");
        return resourceNamer.randomName(prefix, maxLen);
    }

    /**
     * Generates the specified number of random resource names with the same prefix.
     * @param prefix the prefix to be used if possible
     * @param maxLen the maximum length for the random generated name
     * @param count the number of names to generate
     * @return random names
     */
    public static String[] randomResourceNames(String prefix, int maxLen, int count) {
        String[] names = new String[count];
        ResourceNamer resourceNamer = SdkContext.getResourceNamerFactory().createResourceNamer("");
        for (int i = 0; i < count; i++) {
            names[i] = resourceNamer.randomName(prefix, maxLen);
        }
        return names;
    }

    /**
     * Gets a random UUID.
     *
     * @return the random UUID.
     */
    public static String randomUuid() {
        ResourceNamer resourceNamer = SdkContext.getResourceNamerFactory().createResourceNamer("");
        return resourceNamer.randomUuid();
    }

    /**
     * Function to override the DelayProvider.
     *
     * @param delayProvider delayProvider to override.
     */
    public static void setDelayProvider(DelayProvider delayProvider) {
        SdkContext.delayProvider = delayProvider;
    }

    /**
     * Wrapper for sleep, based on delayProvider.
     * @param milliseconds number of millisecond for which thread should put on sleep.
     */
    public static void sleep(int milliseconds) {
        delayProvider.sleep(milliseconds);
    }

    /**
     * @return the current date time.
     */
    public static DateTime dateTimeNow() {
        ResourceNamer resourceNamer = SdkContext.getResourceNamerFactory().createResourceNamer("");
        return resourceNamer.dateTimeNow();
    }
    /**
     * Wrapper delayed emission, based on delayProvider.
     *
     * @param event the event to emit
     * @param milliseconds the delay in milliseconds
     * @param <T> the type of event
     * @return delayed observable
     */
    public static <T> Observable<T> delayedEmitAsync(T event, int milliseconds) {
        return delayProvider.delayedEmitAsync(event, milliseconds);
    }

    /**
     * Gets the current Rx Scheduler for the SDK framework.
     * @return current rx scheduler.
     */
    public static Scheduler getRxScheduler() {
        return rxScheduler;
    }

    /**
     * Sets the Rx Scheduler for SDK framework, by default is Scheduler.io().
     * @param rxScheduler current Rx Scheduler to be used in SDK framework.
     */
    public static void setRxScheduler(Scheduler rxScheduler) {
        SdkContext.rxScheduler = rxScheduler;
    }

    /**
     * Sets the FileProvider for SDK framework, by default it does nothing.
     * @param fileProvider the FileProvider to override.
     */
    public static void setFileProvider(FileProvider fileProvider) {
        SdkContext.fileProvider = fileProvider;
    }

    /**
     * Prepares the location for file to be created.
     * @param files the files to be created.
     * @throws IOException thrown when failed on IO.
     */
    public static void prepareFileLocation(File... files) throws IOException {
        fileProvider.prepareFileLocation(files);
    }
}
