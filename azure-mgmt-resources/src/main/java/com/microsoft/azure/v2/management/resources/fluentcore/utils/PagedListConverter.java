/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.utils;

import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.management.resources.implementation.PageImpl;
import com.microsoft.rest.RestException;
import rx.Observable;
import rx.functions.Func1;

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
     * @return the converted resource
     */
    public abstract Observable<V> typeConvertAsync(U u);

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
        Observable.from(uPage.items())
                .filter(new Func1<U, Boolean>() {
                    @Override
                    public Boolean call(U u) {
                        return filter(u);
                    }
                })
                .flatMap(new Func1<U, Observable<V>>() {
                    @Override
                    public Observable<V> call(U u) {
                        return typeConvertAsync(u);
                    }
                })
                .map(new Func1<V, V>() {
                    @Override
                    public V call(V v) {
                        vPage.items().add(v);
                        return v;
                    }
                }).toBlocking().subscribe();
    }
}
