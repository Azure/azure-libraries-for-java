/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.cosmosdb.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.cosmosdb.CosmosDBAccount;
import com.microsoft.azure.management.cosmosdb.PrivateEndpointConnection;
import com.microsoft.azure.management.cosmosdb.PrivateEndpointProperty;
import com.microsoft.azure.management.cosmosdb.PrivateLinkServiceConnectionStateProperty;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.management.resources.fluentcore.utils.RXMapper;
import rx.Observable;
import rx.functions.Func1;

/**
 * A private endpoint connection.
 */
@LangDefinition
public class PrivateEndpointConnectionImpl
        extends ExternalChildResourceImpl<PrivateEndpointConnection,
            PrivateEndpointConnectionInner,
            CosmosDBAccountImpl,
            CosmosDBAccount>
        implements PrivateEndpointConnection,
        PrivateEndpointConnection.Definition<CosmosDBAccount.DefinitionStages.WithCreate>,
        PrivateEndpointConnection.UpdateDefinition<CosmosDBAccount.UpdateStages.WithOptionals>,
        PrivateEndpointConnection.Update {
    private final PrivateEndpointConnectionsInner client;

    PrivateEndpointConnectionImpl(String name,
                                  CosmosDBAccountImpl parent,
                                  PrivateEndpointConnectionInner inner,
                                  PrivateEndpointConnectionsInner client) {
        super(name, parent, inner);
        this.client = client;
    }

    @Override
    public String id() {
        return this.inner().id();
    }

    @Override
    public PrivateEndpointProperty privateEndpoint() {
        return inner().privateEndpoint();
    }

    @Override
    public PrivateLinkServiceConnectionStateProperty privateLinkServiceConnectionState() {
        return inner().privateLinkServiceConnectionState();
    }

    @Override
    public PrivateEndpointConnectionImpl withStateProperty(PrivateLinkServiceConnectionStateProperty property) {
        this.inner().withPrivateLinkServiceConnectionState(property);
        return this;
    }

    @Override
    public PrivateEndpointConnectionImpl withStatus(String status) {
        if (this.inner().privateLinkServiceConnectionState() == null) {
            this.inner().withPrivateLinkServiceConnectionState(new PrivateLinkServiceConnectionStateProperty());
        }
        this.inner().privateLinkServiceConnectionState().withStatus(status);
        return this;
    }

    @Override
    public PrivateEndpointConnectionImpl withDescription(String description) {
        if (this.inner().privateLinkServiceConnectionState() == null) {
            this.inner().withPrivateLinkServiceConnectionState(new PrivateLinkServiceConnectionStateProperty());
        }
        this.inner().privateLinkServiceConnectionState().withDescription(description);
        return this;
    }

    @Override
    public Observable<PrivateEndpointConnection> createResourceAsync() {
        final PrivateEndpointConnectionImpl self = this;
        return this.client.createOrUpdateAsync(this.parent().resourceGroupName(),
                this.parent().name(),
                this.name(),
                this.inner())
                .map(new Func1<PrivateEndpointConnectionInner, PrivateEndpointConnection>() {
                    @Override
                    public PrivateEndpointConnection call(PrivateEndpointConnectionInner privateEndpointConnectionInner) {
                        self.setInner(inner());
                        return self;
                    }
                });
    }

    @Override
    public Observable<PrivateEndpointConnection> updateResourceAsync() {
        return this.createResourceAsync();
    }

    @Override
    public Observable<Void> deleteResourceAsync() {
        return RXMapper.mapToVoid(this.client.deleteAsync(
                this.parent().resourceGroupName(),
                this.parent().name(),
                this.name()
        ));
    }

    @Override
    protected Observable<PrivateEndpointConnectionInner> getInnerAsync() {
        return this.client.getAsync(
                this.parent().resourceGroupName(),
                this.parent().name(),
                this.name()
        );
    }

    @Override
    public CosmosDBAccountImpl attach() {
        return this.parent();
    }
}
