/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.v2.management.network.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.v2.management.network.Access;
import com.microsoft.azure.v2.management.network.ExpressRouteCircuitPeering;
import com.microsoft.azure.v2.management.network.RouteFilter;
import com.microsoft.azure.v2.management.network.RouteFilterRule;
import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation.GroupableParentResourceImpl;
import io.reactivex.Maybe;
import io.reactivex.Observable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *  Implementation for RouteFilter and its create and update interfaces.
 */
@LangDefinition
class RouteFilterImpl
        extends GroupableParentResourceImpl<
        RouteFilter,
        RouteFilterInner,
        RouteFilterImpl,
        NetworkManager>
        implements
        RouteFilter,
        RouteFilter.Definition,
        RouteFilter.Update {
    private static final String RULE_TYPE = "Community";

    private Map<String, RouteFilterRule> rules;
    private Map<String, ExpressRouteCircuitPeering> peerings;

    RouteFilterImpl(
            final String name,
            final RouteFilterInner innerModel,
            final NetworkManager networkManager) {
        super(name, innerModel, networkManager);
    }

    @Override
    protected Observable<RouteFilterInner> createInner() {
        return this.manager().inner().routeFilters().createOrUpdateAsync(resourceGroupName(), name(), inner())
                .toObservable();
    }

    @Override
    protected void initializeChildrenFromInner() {
        this.rules = new TreeMap<>();
        List<RouteFilterRuleInner> inners = this.inner().rules();
        if (inners != null) {
            for (RouteFilterRuleInner inner : inners) {
                this.rules.put(inner.name(), new RouteFilterRuleImpl(inner, this));
            }
        }
    }

    @Override
    protected void beforeCreating() {
        this.inner().withRules(innersFromWrappers(this.rules.values()));
    }

    @Override
    protected void afterCreating() {
    }

    @Override
    protected Maybe<RouteFilterInner> getInnerAsync() {
        return this.manager().inner().routeFilters().getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public Maybe<RouteFilter> refreshAsync() {
        return super.refreshAsync()
                .map(routeFilter -> {
                    RouteFilterImpl impl = (RouteFilterImpl) routeFilter;
                    impl.initializeChildrenFromInner();
                    return (RouteFilter) impl;
                });
    }

    @Override
    public String provisioningState() {
        return inner().provisioningState();
    }

    @Override
    public Map<String, RouteFilterRule> rules() {
        return Collections.unmodifiableMap(this.rules);
    }

    @Override
    public Map<String, ExpressRouteCircuitPeering> peerings() {
        return Collections.unmodifiableMap(this.peerings);
    }

    RouteFilterImpl withRule(RouteFilterRuleImpl rule) {
        this.rules.put(rule.name(), rule);
        return this;
    }

    @Override
    public Update withoutRule(String name) {
        this.rules.remove(name);
        return this;
    }

    @Override
    public RouteFilterRuleImpl defineRule(String name) {
        RouteFilterRuleInner inner = new RouteFilterRuleInner();
        inner.withName(name);
        inner.withRouteFilterRuleType(RULE_TYPE);
        inner.withAccess(Access.ALLOW);
        return new RouteFilterRuleImpl(inner, this);
    }

    @Override
    public RouteFilterRule.Update updateRule(String name) {
        return (RouteFilterRuleImpl) this.rules.get(name);
    }
}
