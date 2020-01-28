/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.azure.management.network.implementation;

import com.azure.core.management.SubResource;
import com.azure.management.network.ApplicationGateway;
import com.azure.management.network.ApplicationGatewayAuthenticationCertificate;
import com.azure.management.network.ApplicationGatewayBackendHttpConfiguration;
import com.azure.management.network.ApplicationGatewayBackendHttpSettings;
import com.azure.management.network.ApplicationGatewayConnectionDraining;
import com.azure.management.network.ApplicationGatewayCookieBasedAffinity;
import com.azure.management.network.ApplicationGatewayProbe;
import com.azure.management.network.ApplicationGatewayProtocol;
import com.azure.management.resources.fluentcore.arm.ResourceUtils;
import com.azure.management.resources.fluentcore.arm.models.implementation.ChildResourceImpl;
import com.azure.management.resources.fluentcore.utils.SdkContext;
import com.azure.management.resources.fluentcore.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Base implementation for ApplicationGatewayBackendConfiguration.
 */
class ApplicationGatewayBackendHttpConfigurationImpl
        extends
        ChildResourceImpl<ApplicationGatewayBackendHttpSettings, ApplicationGatewayImpl, ApplicationGateway>
        implements
        ApplicationGatewayBackendHttpConfiguration,
        ApplicationGatewayBackendHttpConfiguration.Definition<ApplicationGateway.DefinitionStages.WithCreate>,
        ApplicationGatewayBackendHttpConfiguration.UpdateDefinition<ApplicationGateway.Update>,
        ApplicationGatewayBackendHttpConfiguration.Update {

    ApplicationGatewayBackendHttpConfigurationImpl(ApplicationGatewayBackendHttpSettings inner, ApplicationGatewayImpl parent) {
        super(inner, parent);
    }

    // Getters

    @Override
    public Map<String, ApplicationGatewayAuthenticationCertificate> authenticationCertificates() {
        Map<String, ApplicationGatewayAuthenticationCertificate> certs = new TreeMap<>();
        if (this.inner().getAuthenticationCertificates() == null) {
            return Collections.unmodifiableMap(certs);
        } else {
            for (SubResource ref : this.inner().getAuthenticationCertificates()) {
                ApplicationGatewayAuthenticationCertificate cert = this.parent().authenticationCertificates().get(ResourceUtils.nameFromResourceId(ref.getId()));
                if (cert != null) {
                    certs.put(cert.name(), cert);
                }
            }
        }
        return Collections.unmodifiableMap(certs);
    }

    @Override
    public String name() {
        return this.inner().getName();
    }

    @Override
    public ApplicationGatewayProbe probe() {
        if (this.parent().probes() != null && this.inner().getProbe() != null) {
            return this.parent().probes().get(ResourceUtils.nameFromResourceId(this.inner().getProbe().getId()));
        } else {
            return null;
        }
    }

    @Override
    public String hostHeader() {
        return this.inner().getHostName();
    }

    @Override
    public boolean isHostHeaderFromBackend() {
        return Utils.toPrimitiveBoolean(this.inner().isPickHostNameFromBackendAddress());
    }

    @Override
    public boolean isProbeEnabled() {
        return Utils.toPrimitiveBoolean(this.inner().isProbeEnabled());
    }

    @Override
    public int connectionDrainingTimeoutInSeconds() {
        if (this.inner().getConnectionDraining() == null) {
            return 0;
        } else if (!this.inner().getConnectionDraining().isEnabled()) {
            return 0;
        } else {
            return this.inner().getConnectionDraining().getDrainTimeoutInSec();
        }
    }

    @Override
    public String affinityCookieName() {
        return this.inner().getAffinityCookieName();
    }

    @Override
    public String path() {
        return this.inner().getPath();
    }

    @Override
    public int port() {
        return Utils.toPrimitiveInt(this.inner().getPort());
    }

    @Override
    public ApplicationGatewayProtocol protocol() {
        return this.inner().getProtocol();
    }

    @Override
    public boolean cookieBasedAffinity() {
        return this.inner().getCookieBasedAffinity().equals(ApplicationGatewayCookieBasedAffinity.ENABLED);
    }

    @Override
    public int requestTimeout() {
        return Utils.toPrimitiveInt(this.inner().getRequestTimeout());
    }

    // Verbs

    public ApplicationGatewayImpl attach() {
        this.parent().withBackendHttpConfiguration(this);
        return this.parent();
    }

    // Withers

    public ApplicationGatewayBackendHttpConfigurationImpl withPort(int port) {
        this.inner().setPort(port);
        return this;
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withCookieBasedAffinity() {
        this.inner().setCookieBasedAffinity(ApplicationGatewayCookieBasedAffinity.ENABLED);
        return this;
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withoutCookieBasedAffinity() {
        this.inner().setCookieBasedAffinity(ApplicationGatewayCookieBasedAffinity.DISABLED);
        return this;
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withProtocol(ApplicationGatewayProtocol protocol) {
        this.inner().setProtocol(protocol);
        return this;
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withRequestTimeout(int seconds) {
        this.inner().setRequestTimeout(seconds);
        return this;
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withProbe(String name) {
        if (name == null) {
            return this.withoutProbe();
        } else {
            SubResource probeRef = new SubResource()
                    .setId(this.parent().futureResourceId() + "/probes/" + name);
            this.inner().setProbe(probeRef);
            return this;
        }
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withoutProbe() {
        this.inner().setProbe(null);
        return this;
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withHostHeaderFromBackend() {
        this.inner()
                .setPickHostNameFromBackendAddress(true)
                .setHostName(null);
        return this;
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withHostHeader(String hostHeader) {
        this.inner()
                .setHostName(hostHeader)
                .setPickHostNameFromBackendAddress(false);
        return this;
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withoutHostHeader() {
        this.inner()
                .setHostName(null)
                .setPickHostNameFromBackendAddress(false);
        return this;
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withConnectionDrainingTimeoutInSeconds(int seconds) {
        if (this.inner().getConnectionDraining() == null) {
            this.inner().setConnectionDraining(new ApplicationGatewayConnectionDraining());
        }
        if (seconds > 0) {
            this.inner().getConnectionDraining().setDrainTimeoutInSec(seconds).setEnabled(true);
        }
        return this;
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withoutConnectionDraining() {
        this.inner().setConnectionDraining(null);
        return this;
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withAffinityCookieName(String name) {
        this.inner().setAffinityCookieName(name);
        return this;
    }

    public ApplicationGatewayBackendHttpConfigurationImpl withPath(String path) {
        if (path != null) {
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (!path.endsWith("/")) {
                path += "/";
            }
        }
        this.inner().setPath(path);
        return this;
    }

    @Override
    public ApplicationGatewayBackendHttpConfigurationImpl withAuthenticationCertificate(String name) {
        if (name == null) {
            return this;
        }
        SubResource certRef = new SubResource()
                .setId(this.parent().futureResourceId() + "/authenticationCertificates/" + name);
        List<SubResource> refs = this.inner().getAuthenticationCertificates();
        if (refs == null) {
            refs = new ArrayList<>();
            this.inner().setAuthenticationCertificates(refs);
        }
        for (SubResource ref : refs) {
            if (ref.getId().equalsIgnoreCase(certRef.getId())) {
                return this;
            }
        }
        refs.add(certRef);
        return this.withHttps();
    }

    @Override
    public ApplicationGatewayBackendHttpConfigurationImpl withAuthenticationCertificateFromBytes(byte[] derData) {
        if (derData == null) {
            return this;
        }

        String encoded = new String(Base64.getEncoder().encode(derData));
        return this.withAuthenticationCertificateFromBase64(encoded);
    }

    @Override
    public ApplicationGatewayBackendHttpConfigurationImpl withAuthenticationCertificateFromBase64(String base64Data) {
        if (base64Data == null) {
            return this;
        }

        String certName = null;
        for (ApplicationGatewayAuthenticationCertificate cert : this.parent().authenticationCertificates().values()) {
            if (cert.data().contentEquals(base64Data)) {
                certName = cert.name();
                break;
            }
        }

        // If matching cert reference not found, create a new one
        if (certName == null) {
            certName = SdkContext.randomResourceName("cert", 20);
            this.parent().defineAuthenticationCertificate(certName)
                    .fromBase64(base64Data)
                    .attach();
        }

        return this.withAuthenticationCertificate(certName).withHttps();
    }

    @Override
    public ApplicationGatewayBackendHttpConfigurationImpl withAuthenticationCertificateFromFile(File certificateFile) throws IOException {
        if (certificateFile == null) {
            return this;
        } else {
            byte[] content = Files.readAllBytes(certificateFile.toPath());
            return this.withAuthenticationCertificateFromBytes(content);
        }
    }

    @Override
    public ApplicationGatewayBackendHttpConfigurationImpl withoutAuthenticationCertificate(String name) {
        if (name == null) {
            return this;
        }
        for (SubResource ref : this.inner().getAuthenticationCertificates()) {
            if (ResourceUtils.nameFromResourceId(ref.getId()).equalsIgnoreCase(name)) {
                this.inner().getAuthenticationCertificates().remove(ref);
                break;
            }
        }
        return this;
    }

    @Override
    public ApplicationGatewayBackendHttpConfigurationImpl withHttps() {
        return this.withProtocol(ApplicationGatewayProtocol.HTTPS);
    }

    @Override
    public ApplicationGatewayBackendHttpConfigurationImpl withHttp() {
        return this
                .withoutAuthenticationCertificates()
                .withProtocol(ApplicationGatewayProtocol.HTTP);
    }

    @Override
    public ApplicationGatewayBackendHttpConfigurationImpl withoutAuthenticationCertificates() {
        this.inner().setAuthenticationCertificates(null);
        return this;
    }
}
