/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.utils;

import com.google.common.primitives.Ints;
import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.CloudError;
import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.credentials.AzureTokenCredentials;
import com.microsoft.azure.management.resources.fluentcore.arm.ResourceId;
import com.microsoft.azure.management.resources.fluentcore.model.Indexable;
import com.microsoft.azure.management.resources.implementation.PageImpl;
import com.microsoft.azure.serializer.AzureJacksonAdapter;
import com.microsoft.rest.RestClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

import java.io.IOException;
import java.util.List;

/**
 * Defines a few utilities.
 */
public final class Utils {
    /**
     * Converts an object Boolean to a primitive boolean.
     *
     * @param value the Boolean value
     * @return false if the given Boolean value is null or false else true
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
     * @param value the Integer value
     * @return 0 if the given Integer value is null else integer value
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
     * @param value the Long value
     * @return 0 if the given Long value is null else integer value
     */
    public static int toPrimitiveInt(Long value) {
        if (value == null) {
            return 0;
        }
        // throws IllegalArgumentException - if value is greater than Integer.MAX_VALUE
        // or less than Integer.MIN_VALUE
        return Ints.checkedCast(value);
    }

    /**
     * Converts an object Long to a primitive long.
     *
     * @param value the Long value
     * @return 0 if the given Long value is null else long value
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
     * Gets an observable of type {@code U}, where U extends {@link Indexable}, that emits only the root
     * resource from a given observable of {@link Indexable}.
     *
     * @param stream the input observable of {@link Indexable}
     * @param <U> the specialized type of last item in the input stream
     * @return an observable that emits last item
     */
    @SuppressWarnings("unchecked")
    public static <U extends Indexable> Observable<U> rootResource(Observable<Indexable> stream) {
        return stream.last().map(new Func1<Indexable, U>() {
            @Override
            public U call(Indexable indexable) {
                return (U) indexable;
            }
        });
    }

    /**
     * Download a file asynchronously.
     * @param url the URL pointing to the file
     * @param retrofit the retrofit client
     * @return an Observable pointing to the content of the file
     */
    public static Observable<byte[]> downloadFileAsync(String url, Retrofit retrofit) {
        FileService service = retrofit.create(FileService.class);
        Observable<ResponseBody> response = service.download(url);
        return response.map(new Func1<ResponseBody, byte[]>() {
            @Override
            public byte[] call(ResponseBody responseBody) {
                try {
                    return responseBody.bytes();
                } catch (IOException e) {
                    throw Exceptions.propagate(e);
                }
            }
        });
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
    public static <OutT, InT> PagedList<OutT> toPagedList(List<InT> list, final Func1<InT, OutT> mapper) {
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
            public Observable<OutT> typeConvertAsync(InT inner) {
                return Observable.just(mapper.call(inner));
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
     * Try to extract the environment the client is authenticated to based
     * on the information on the rest client.
     * @param restClient the RestClient instance
     * @return the non-null AzureEnvironment
     */
    public static AzureEnvironment extractAzureEnvironment(RestClient restClient) {
        AzureEnvironment environment = null;
        if (restClient.credentials() instanceof AzureTokenCredentials) {
            environment = ((AzureTokenCredentials) restClient.credentials()).environment();
        } else {
            String baseUrl = restClient.retrofit().baseUrl().toString();
            for (AzureEnvironment env : AzureEnvironment.knownEnvironments()) {
                if (env.resourceManagerEndpoint().toLowerCase().contains(baseUrl.toLowerCase())) {
                    environment = env;
                    break;
                }
            }
            if (environment == null) {
                throw new IllegalArgumentException("Unknown resource manager endpoint " + baseUrl);
            }
        }
        return environment;
    }

    /**
     * A Retrofit service used to download a file.
     */
    private interface FileService {
        @GET
        Observable<ResponseBody> download(@Url String url);
    }

    /**
     * @param id resource id
     * @return resource group id for the resource id provided
     */
    public static String resourceGroupId(String id) {
        final ResourceId resourceId = ResourceId.fromString(id);
        return String.format("/subscriptions/%s/resourceGroups/%s",
                resourceId.subscriptionId(),
                resourceId.resourceGroupName());
    }

    /**
     * Get the response body as String
     * @param responseBody response body object
     * @return response body in string
     * @throws IOException
     */
    public static String getResponseBodyInString(ResponseBody responseBody) throws IOException {
        if (responseBody == null) {
            return null;
        }
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        return buffer.clone().readUtf8();
    }

    private Utils() {
    }
}
