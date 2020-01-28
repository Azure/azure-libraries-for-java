/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.management.network.ApplicationGateway;
import com.azure.management.network.ApplicationGatewayProbe;
import com.azure.management.network.ApplicationGatewayProbeHealthResponseMatch;
import com.azure.management.network.ApplicationGatewayProtocol;
import com.azure.management.network.models.ApplicationGatewayProbeInner;
import com.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Implementation for ApplicationGatewayProbe.
 */
class ApplicationGatewayProbeImpl
        extends ChildResourceImpl<ApplicationGatewayProbeInner, ApplicationGatewayImpl, ApplicationGateway>
        implements
        ApplicationGatewayProbe,
        ApplicationGatewayProbe.Definition<ApplicationGateway.DefinitionStages.WithCreate>,
        ApplicationGatewayProbe.UpdateDefinition<ApplicationGateway.Update>,
        ApplicationGatewayProbe.Update {

    ApplicationGatewayProbeImpl(ApplicationGatewayProbeInner inner, ApplicationGatewayImpl parent) {
        super(inner, parent);
    }

    // Getters

    @Override
    public String name() {
        return this.inner().getName();
    }

    @Override
    public String healthyHttpResponseBodyContents() {
        ApplicationGatewayProbeHealthResponseMatch match = this.inner().getMatch();
        if (match == null) {
            return null;
        } else {
            return match.getBody();
        }
    }

    @Override
    public ApplicationGatewayProtocol protocol() {
        return this.inner().getProtocol();
    }

    @Override
    public int timeBetweenProbesInSeconds() {
        return (this.inner().getInterval() != null) ? this.inner().getInterval().intValue() : 0;
    }

    @Override
    public String path() {
        return this.inner().getPath();
    }

    @Override
    public Set<String> healthyHttpResponseStatusCodeRanges() {
        Set<String> httpResponseStatusCodeRanges = new TreeSet<>();
        if (this.inner().getMatch() == null) {
            // Empty
        } else if (this.inner().getMatch().getStatusCodes() == null) {
            // Empty
        } else {
            httpResponseStatusCodeRanges.addAll(this.inner().getMatch().getStatusCodes());
        }

        return Collections.unmodifiableSet(httpResponseStatusCodeRanges);
    }

    @Override
    public int timeoutInSeconds() {
        return (this.inner().getTimeout() != null) ? this.inner().getTimeout().intValue() : 0;
    }

    @Override
    public int retriesBeforeUnhealthy() {
        return (this.inner().getUnhealthyThreshold() != null) ? this.inner().getUnhealthyThreshold() : 0;
    }

    @Override
    public String host() {
        return this.inner().getHost();
    }

    // Fluent setters

    @Override
    public ApplicationGatewayProbeImpl withProtocol(ApplicationGatewayProtocol protocol) {
        this.inner().setProtocol(protocol);
        return this;
    }

    @Override
    public ApplicationGatewayProbeImpl withHttp() {
        return this.withProtocol(ApplicationGatewayProtocol.HTTP);
    }

    @Override
    public ApplicationGatewayProbeImpl withHttps() {
        return this.withProtocol(ApplicationGatewayProtocol.HTTPS);
    }

    @Override
    public ApplicationGatewayProbeImpl withPath(String path) {
        if (path != null && !path.startsWith("/")) {
            path = "/" + path;
        }
        this.inner().setPath(path);
        return this;
    }

    @Override
    public ApplicationGatewayProbeImpl withHost(String host) {
        this.inner().setHost(host);
        return this;
    }

    @Override
    public ApplicationGatewayProbeImpl withTimeoutInSeconds(int seconds) {
        this.inner().setTimeout(seconds);
        return this;
    }

    @Override
    public ApplicationGatewayProbeImpl withTimeBetweenProbesInSeconds(int seconds) {
        this.inner().setInterval(seconds);
        return this;
    }

    @Override
    public ApplicationGatewayProbeImpl withRetriesBeforeUnhealthy(int retryCount) {
        this.inner().setUnhealthyThreshold(retryCount);
        return this;
    }

    @Override
    public ApplicationGatewayProbeImpl withHealthyHttpResponseStatusCodeRanges(Set<String> ranges) {
        if (ranges != null) {
            for (String range : ranges) {
                this.withHealthyHttpResponseStatusCodeRange(range);
            }
        }
        return this;
    }

    @Override
    public ApplicationGatewayProbeImpl withHealthyHttpResponseStatusCodeRange(String range) {
        if (range != null) {
            ApplicationGatewayProbeHealthResponseMatch match = this.inner().getMatch();
            if (match == null) {
                match = new ApplicationGatewayProbeHealthResponseMatch();
                this.inner().setMatch(match);
            }

            List<String> ranges = match.getStatusCodes();
            if (ranges == null) {
                ranges = new ArrayList<>();
                match.setStatusCodes(ranges);
            }

            if (!ranges.contains(range)) {
                ranges.add(range);
            }
        }

        return this;
    }

    @Override
    public ApplicationGatewayProbeImpl withHealthyHttpResponseStatusCodeRange(int from, int to) {
        if (from < 0 || to < 0) {
            throw new IllegalArgumentException("The start and end of a range cannot be negative numbers.");
        } else if (to < from) {
            throw new IllegalArgumentException("The end of the range cannot be less than the start of the range.");
        } else {
            return this.withHealthyHttpResponseStatusCodeRange(String.valueOf(from) + "-" + String.valueOf(to));
        }
    }

    @Override
    public ApplicationGatewayProbeImpl withoutHealthyHttpResponseStatusCodeRanges() {
        ApplicationGatewayProbeHealthResponseMatch match = this.inner().getMatch();
        if (match != null) {
            match.setStatusCodes(null);
            if (match.getBody() == null) {
                this.inner().setMatch(null);
            }
        }

        return this;
    }

    @Override
    public ApplicationGatewayProbeImpl withHealthyHttpResponseBodyContents(String text) {
        ApplicationGatewayProbeHealthResponseMatch match = this.inner().getMatch();
        if (text != null) {
            if (match == null) {
                match = new ApplicationGatewayProbeHealthResponseMatch();
                this.inner().setMatch(match);
            }
            match.setBody(text);
        } else {
            if (match == null) {
                // Nothing else to do
            } else if (match.getStatusCodes() == null || match.getStatusCodes().isEmpty()) {
                // If match is becoming empty then remove altogether
                this.inner().setMatch(null);
            } else {
                match.setBody(null);
            }
        }
        return this;
    }

    // Verbs

    @Override
    public ApplicationGatewayImpl attach() {
        return this.parent().withProbe(this);
    }
}
