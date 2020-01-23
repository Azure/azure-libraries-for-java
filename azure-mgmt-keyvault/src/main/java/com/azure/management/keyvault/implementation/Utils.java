package com.azure.management.keyvault.implementation;

import com.azure.core.http.HttpRequest;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.http.rest.PagedResponseBase;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class Utils {

//    public static <T, S> Function<PagedResponse<T>, Mono<PagedResponse<S>>> flatMapPagedResponse(Function<? super T, ? extends Publisher<? extends S>> mapper) {
//        return pagedResponse ->
//                Flux.fromIterable(pagedResponse.getValue())
//                        .flatMapSequential(mapper)
//                        .collectList()
//                        .map(values -> new PagedResponseBase<HttpRequest, S>(pagedResponse.getRequest(),
//                                pagedResponse.getStatusCode(),
//                                pagedResponse.getHeaders(),
//                                values,
//                                pagedResponse.getContinuationToken(),
//                                null));
//    }
}
