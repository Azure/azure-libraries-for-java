/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.Access;
import com.azure.management.network.RouteFilter;
import com.azure.management.network.RouteFilterRule;
import com.azure.management.network.models.RouteFilterRuleInner;
import com.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implementation for {@link RouteFilterRule} and its create and update interfaces.
 */
class RouteFilterRuleImpl
        extends ChildResourceImpl<RouteFilterRuleInner, RouteFilterImpl, RouteFilter>
        implements
        RouteFilterRule,
        RouteFilterRule.Definition<RouteFilter.DefinitionStages.WithCreate>,
        RouteFilterRule.UpdateDefinition<RouteFilter.Update>,
        RouteFilterRule.Update {

    RouteFilterRuleImpl(RouteFilterRuleInner inner, RouteFilterImpl parent) {
        super(inner, parent);
    }

    @Override
    public RouteFilterImpl attach() {
        return this.parent().withRule(this);
    }

    @Override
    public RouteFilterRuleImpl withBgpCommunities(String... communities) {
        inner().setCommunities(Arrays.asList(communities));
        return this;
    }

    @Override
    public RouteFilterRuleImpl withBgpCommunity(String community) {
        if (inner().getCommunities() == null) {
            inner().setCommunities(new ArrayList<String>());
        }
        inner().getCommunities().add(community);
        return this;
    }

    @Override
    public Update withoutBgpCommunity(String community) {
        if (inner().getCommunities() != null) {
            inner().getCommunities().remove(community);
        }
        return this;
    }

    @Override
    public RouteFilterRuleImpl allowAccess() {
        inner().setAccess(Access.ALLOW);
        return this;
    }

    @Override
    public RouteFilterRuleImpl denyAccess() {
        inner().setAccess(Access.DENY);
        return this;
    }

    @Override
    public String name() {
        return inner().getName();
    }

    @Override
    public Access access() {
        return inner().getAccess();
    }

    @Override
    public String routeFilterRuleType() {
        return inner().getRouteFilterRuleType();
    }

    @Override
    public List<String> communities() {
        return Collections.unmodifiableList(inner().getCommunities());
    }

    @Override
    public String provisioningState() {
        return inner().getProvisioningState();
    }

    @Override
    public String location() {
        return inner().getLocation();
    }
}
