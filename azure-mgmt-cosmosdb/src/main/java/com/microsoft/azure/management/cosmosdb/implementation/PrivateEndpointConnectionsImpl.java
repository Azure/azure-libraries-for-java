/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.cosmosdb.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.cosmosdb.CosmosDBAccount;
import com.microsoft.azure.management.cosmosdb.PrivateEndpointConnection;
import com.microsoft.azure.management.resources.fluentcore.arm.collection.implementation.ExternalChildResourcesCachedImpl;
import rx.Observable;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Represents a private endpoint connection collection.
 */
@LangDefinition
class PrivateEndpointConnectionsImpl extends
        ExternalChildResourcesCachedImpl<PrivateEndpointConnectionImpl,
                PrivateEndpointConnection,
                PrivateEndpointConnectionInner,
                CosmosDBAccountImpl,
                CosmosDBAccount> {
    private final PrivateEndpointConnectionsInner client;

    PrivateEndpointConnectionsImpl(PrivateEndpointConnectionsInner client, CosmosDBAccountImpl parent) {
        super(parent, parent.taskGroup(), "PrivateEndpointConnection");
        this.client = client;
        this.cacheCollection();
    }

    public PrivateEndpointConnectionImpl define(String name) {
        return this.prepareInlineDefine(name);
    }

    public PrivateEndpointConnectionImpl update(String name) {
        return this.prepareInlineUpdate(name);
    }

    public void remove(String name) {
        this.prepareInlineRemove(name);
    }

    public Map<String, PrivateEndpointConnection> asMap() {
        return asMapAsync().toBlocking().last();
    }

    public Observable<Map<String, PrivateEndpointConnection>> asMapAsync() {
        return listAsync().map(new Func1<List<PrivateEndpointConnectionImpl>, Map<String, PrivateEndpointConnection>>() {
            @Override
            public Map<String, PrivateEndpointConnection> call(List<PrivateEndpointConnectionImpl> privateEndpointConnections) {
                Map<String, PrivateEndpointConnection> privateEndpointConnectionMap = new HashMap<>();
                for (PrivateEndpointConnectionImpl privateEndpointConnection : privateEndpointConnections) {
                    privateEndpointConnectionMap.put(privateEndpointConnection.name(), privateEndpointConnection);
                }
                return privateEndpointConnectionMap;
            }
        });
    }
    
    public Observable<List<PrivateEndpointConnectionImpl>> listAsync() {
        return this.client.listByDatabaseAccountAsync(parent().resourceGroupName(), parent().name())
                .map(new Func1<List<PrivateEndpointConnectionInner>, List<PrivateEndpointConnectionImpl>>() {
                    @Override
                    public List<PrivateEndpointConnectionImpl> call(List<PrivateEndpointConnectionInner> privateEndpointConnectionInners) {
                        List<PrivateEndpointConnectionImpl> privateEndpointConnections = new ArrayList<>();
                        for (PrivateEndpointConnectionInner inner : privateEndpointConnectionInners) {
                            privateEndpointConnections.add(new PrivateEndpointConnectionImpl(inner.name(), parent(), inner, client));
                        }
                        return Collections.unmodifiableList(privateEndpointConnections);
                    }
                });
    }

    public Observable<PrivateEndpointConnectionImpl> getImplAsync(String name) {
        return this.client.getAsync(parent().resourceGroupName(), parent().name(), name)
                .map(new Func1<PrivateEndpointConnectionInner, PrivateEndpointConnectionImpl>() {
                    @Override
                    public PrivateEndpointConnectionImpl call(PrivateEndpointConnectionInner privateEndpointConnectionInner) {
                        return new PrivateEndpointConnectionImpl(privateEndpointConnectionInner.name(),
                                parent(),
                                privateEndpointConnectionInner,
                                client);
                    }
                });
    }

    @Override
    protected List<PrivateEndpointConnectionImpl> listChildResources() {
        return new ArrayList<>();
    }

    @Override
    protected PrivateEndpointConnectionImpl newChildResource(String name) {
        return new PrivateEndpointConnectionImpl(name, parent(), new PrivateEndpointConnectionInner(), this.client);
    }
}
