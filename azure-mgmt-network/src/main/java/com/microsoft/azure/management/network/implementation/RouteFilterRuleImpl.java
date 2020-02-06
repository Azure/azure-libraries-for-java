/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.network.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.network.Access;
import com.microsoft.azure.management.network.RouteFilter;
import com.microsoft.azure.management.network.RouteFilterRule;
import com.microsoft.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *  Implementation for {@link RouteFilterRule} and its create and update interfaces.
 */
@LangDefinition
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
        inner().withCommunities(Arrays.asList(communities));
        return this;
    }

    @Override
    public RouteFilterRuleImpl withBgpCommunity(String community) {
        if (inner().communities() == null) {
            inner().withCommunities(new ArrayList<String>());
        }
        inner().communities().add(community);
        return this;
    }

    @Override
    public Update withoutBgpCommunity(String community) {
        if (inner().communities() != null) {
            inner().communities().remove(community);
        }
        return this;
    }

    @Override
    public RouteFilterRuleImpl allowAccess() {
        inner().withAccess(Access.ALLOW);
        return this;
    }

    @Override
    public RouteFilterRuleImpl denyAccess() {
        inner().withAccess(Access.DENY);
        return this;
    }

    @Override
    public String name() {
        return inner().name();
    }

    @Override
    public Access access() {
        return inner().access();
    }

    @Override
    public String routeFilterRuleType() {
        return inner().routeFilterRuleType();
    }

    @Override
    public List<String> communities() {
        return Collections.unmodifiableList(inner().communities());
    }

    @Override
    public String provisioningState() {
        return inner().provisioningState();
    }

    @Override
    public String location() {
        return inner().location();
    }
}
