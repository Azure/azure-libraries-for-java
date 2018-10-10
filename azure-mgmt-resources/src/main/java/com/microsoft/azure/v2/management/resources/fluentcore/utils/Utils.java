/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.utils;

import com.microsoft.azure.v2.Page;
import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.v2.management.resources.implementation.PageImpl;
import com.microsoft.rest.v2.RestProxy;
import com.microsoft.rest.v2.annotations.GET;
import com.microsoft.rest.v2.annotations.Host;
import com.microsoft.rest.v2.annotations.HostParam;
import com.microsoft.rest.v2.http.HttpClient;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.policy.DecodingPolicyFactory;
import com.microsoft.rest.v2.policy.HttpLogDetailLevel;
import com.microsoft.rest.v2.policy.HttpLoggingPolicyFactory;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

import java.util.List;

/**
 * Defines a few utilities.
 */
public final class Utils {
    /**
     * Converts an object Boolean to a primitive boolean.
     *
     * @param value the <tt>Boolean</tt> value
     * @return <tt>false</tt> if the given Boolean value is null or false else <tt>true</tt>
     */
    public static boolean toPrimitiveBoolean(Boolean value) {
        if (value == null) {
            return false;
        }
        return value.booleanValue();
    }

    /**
     * Converts an object Integer to a primitive int.
     *
     * @param value the <tt>Integer</tt> value
     * @return <tt>0</tt> if the given Integer value is null else <tt>integer value</tt>
     */
    public static int toPrimitiveInt(Integer value) {
        if (value == null) {
            return 0;
        }
        return value.intValue();
    }

    /**
     * Converts an object Long to a primitive int.
     *
     * @param value the <tt>Long</tt> value
     * @return <tt>0</tt> if the given Long value is null else <tt>integer value</tt>
     */
    public static int toPrimitiveInt(Long value) {
        if (value == null) {
            return 0;
        }
        long a = value;
        return (int) a;
    }

    /**
     * Converts an object Long to a primitive long.
     *
     * @param value the <tt>Long</tt> value
     * @return <tt>0</tt> if the given Long value is null else <tt>long value</tt>
     */
    public static long toPrimitiveLong(Long value) {
        if (value == null) {
            return 0;
        }
        return value;
    }
    /**
     * Creates an Odata filter string that can be used for filtering list results by tags.
     *
     * @param tagName the name of the tag. If not provided, all resources will be returned.
     * @param tagValue the value of the tag. If not provided, only tag name will be filtered.
     * @return the Odata filter to pass into list methods
     */
    public static String createOdataFilterForTags(String tagName, String tagValue) {
        if (tagName == null) {
            return null;
        } else if (tagValue == null) {
            return String.format("tagname eq '%s'", tagName);
        } else {
            return String.format("tagname eq '%s' and tagvalue eq '%s'", tagName, tagValue);
        }
    }

    /**
     * Gets an observable of {@link U} that emits only the root resource from a given
     * observable of {@link Indexable}.
     *
     * @param stream the input observable of {@link Indexable}
     * @param <U> the specialized type of last item in the input stream
     * @return an observable that emits last item
     */
    @SuppressWarnings("unchecked")
    public static <U extends Indexable> Single<U> rootResource(Observable<Indexable> stream) {
        return stream.lastOrError().map(new Function<Indexable, U>() {
            @Override
            public U apply(Indexable indexable) {
                return (U) indexable;
            }
        });
    }

    /**
     * Download a file asynchronously.
     * @param url the URL pointing to the file
     * @param httpClient the HTTP client
     * @return an Observable pointing to the content of the file
     */
    public static Flowable<byte[]> downloadFileAsync(String url, HttpClient httpClient) {
        FileService service = RestProxy.create(FileService.class, HttpPipeline.build(httpClient, new DecodingPolicyFactory(), new HttpLoggingPolicyFactory(HttpLogDetailLevel.BODY_AND_HEADERS)));
        return service.download(url);
    }

    /**
     * Converts the given list of a type to paged list of a different type.
     *
     * @param list the list to convert to paged list
     * @param mapper the mapper to map type in input list to output list
     * @param <OutT> the type of items in output paged list
     * @param <InT> the type of items in input paged list
     * @return the paged list
     */
    public static <OutT, InT> PagedList<OutT> toPagedList(List<InT> list, final Function<InT, OutT> mapper) {
        PageImpl<InT> page = new PageImpl<>();
        page.setItems(list);
        page.setNextPageLink(null);
        PagedList<InT> pagedList = new PagedList<InT>(page) {
            @Override
            public Page<InT> nextPage(String nextPageLink) {
                return null;
            }
        };
        PagedListConverter<InT, OutT> converter = new PagedListConverter<InT, OutT>() {
            @Override
            public Observable<OutT> typeConvertAsync(InT inner) throws Exception {
                return Observable.just(mapper.apply(inner));
            }
        };
        return converter.convert(pagedList);
    }

    /**
     * Adds a value to the list if does not already exists.
     *
     * @param list the list
     * @param value value to add if not exists in the list
     */
    public static void addToListIfNotExists(List<String> list, String value) {
        boolean found = false;
        for (String item : list) {
            if (item.equalsIgnoreCase(value)) {
                found = true;
                break;
            }
        }
        if (!found) {
            list.add(value);
        }
    }

    /**
     * Removes a value from the list.
     *
     * @param list the list
     * @param value value to remove
     */
    public static void removeFromList(List<String> list, String value) {
        int foundIndex = -1;
        int i = 0;
        for (String id : list) {
            if (id.equalsIgnoreCase(value)) {
                foundIndex = i;
                break;
            }
            i++;
        }
        if (foundIndex != -1) {
            list.remove(foundIndex);
        }
    }

    /**
     * A RestProxy service used to download a file.
     */
    @Host("{url}")
    private interface FileService {
        @GET("")
        Flowable<byte[]> download(@HostParam("url") String url);
    }

    private Utils() {
    }
}
