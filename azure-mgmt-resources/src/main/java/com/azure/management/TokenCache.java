// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.management;

import com.azure.core.credential.AccessToken;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * Caches the access token
 */
public class TokenCache {
    private final ConcurrentMap<String, AccessToken> cache = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, ReentrantLock> lock = new ConcurrentHashMap<>();

    public Mono<AccessToken> getTokenWithCache(String digest, Supplier<Mono<AccessToken>> requireAccessToken) {
        AccessToken token = cache.get(digest);
        if (token != null && !token.isExpired()) {
            return Mono.just(token);
        }
        lock.putIfAbsent(digest, new ReentrantLock());
        lock.get(digest).lock();
        try {
            token = cache.get(digest);
            if (token != null && !token.isExpired()) {
                return Mono.just(token);
            }
            return requireAccessToken.get()
                    .doOnNext(accessToken -> cache.put(digest, accessToken));
        } finally {
            lock.get(digest).unlock();
        }
    }
}
