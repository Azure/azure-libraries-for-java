/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.utils;

import com.microsoft.azure.v2.Page;
import com.microsoft.azure.v2.PagedList;
import com.microsoft.azure.v2.management.resources.implementation.PageImpl;
import com.microsoft.rest.v2.RestException;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The base class for converting {@link PagedList} of one type of resource to
 * another, without polling down all the items in a list.
 * This converter is useful in converting inner top level resources into fluent
 * top level resources.
 *
 * @param <U> the type of Resource to convert from
 * @param <V> the type of Resource to convert to
 */
public abstract class PagedListConverter<U, V> {
    /**
     * Override this method to define how to convert each Resource item
     * individually.
     *
     * @param u the resource to convert from
     * @throws Exception exception from type conversion
     * @return the converted resource
     */
    public abstract Observable<V> typeConvertAsync(U u) throws Exception;

    /**
     * Override this method to define what items should be fetched.
     * @param u an original item to test
     * @return true if this item should be fetched
     */
    protected boolean filter(U u) {
        return true;
    }

    /**
     * Converts the paged list.
     *
     * @param uList the resource list to convert from
     * @return the converted list
     */
    public PagedList<V> convert(final PagedList<U> uList) {
        if (uList == null || uList.isEmpty()) {
            return new PagedList<V>() {
                @Override
                public Page<V> nextPage(String s) throws RestException, IOException {
                    return null;
                }
            };
        }
        Page<U> uPage = uList.currentPage();
        final PageImpl<V> vPage = new PageImpl<>();
        vPage.setNextPageLink(uPage.nextPageLink());
        vPage.setItems(new ArrayList<V>());
        loadConvertedList(uPage, vPage);
        return new PagedList<V>(vPage) {
            @Override
            public Page<V> nextPage(String nextPageLink) throws RestException, IOException {
                Page<U> uPage = uList.nextPage(nextPageLink);
                final PageImpl<V> vPage = new PageImpl<>();
                vPage.setNextPageLink(uPage.nextPageLink());
                vPage.setItems(new ArrayList<V>());
                loadConvertedList(uPage, vPage);
                return vPage;
            }
        };
    }

    private void loadConvertedList(final Page<U> uPage, final Page<V> vPage) {
        Observable.fromIterable(uPage.items())
                .filter(new Predicate<U>() {
                    @Override
                    public boolean test(U u) {
                        return filter(u);
                    }
                })
                .flatMap(new Function<U, Observable<V>>() {
                    @Override
                    public Observable<V> apply(U u) throws Exception {
                        return typeConvertAsync(u);
                    }
                })
                .map(new Function<V, V>() {
                    @Override
                    public V apply(V v) {
                        vPage.items().add(v);
                        return v;
                    }
                }).blockingSubscribe();
    }
}
