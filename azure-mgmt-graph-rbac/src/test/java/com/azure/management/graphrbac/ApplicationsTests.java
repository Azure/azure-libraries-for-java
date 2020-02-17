/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.graphrbac;

import com.azure.management.resources.fluentcore.utils.SdkContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

public class ApplicationsTests extends GraphRbacManagementTest {
    @Test
    public void canCRUDApplication() throws Exception {
        String name = SdkContext.randomResourceName("javasdkapp", 20);

        ActiveDirectoryApplication application = null;
        try {
            application = graphRbacManager.applications().define(name)
                    .withSignOnUrl("http://easycreate.azure.com/" + name)
                    .definePasswordCredential("passwd")
                        .withPasswordValue("P@ssw0rd")
                        .withDuration(Duration.ofDays(700))
                        .attach()
                    .defineCertificateCredential("cert")
                        .withAsymmetricX509Certificate()
                        .withPublicKey(readAllBytes(this.getClass().getResourceAsStream("/myTest.cer")))
                        .withDuration(Duration.ofDays(100))
                        .attach()
                    .create();
            System.out.println(application.id() + " - " + application.applicationId());
            Assert.assertNotNull(application.id());
            Assert.assertNotNull(application.applicationId());
            Assert.assertEquals(name, application.name());
            Assert.assertEquals(1, application.certificateCredentials().size());
            Assert.assertEquals(1, application.passwordCredentials().size());
            Assert.assertEquals(1, application.replyUrls().size());
            Assert.assertEquals(1, application.identifierUris().size());
            Assert.assertEquals("http://easycreate.azure.com/" + name, application.signOnUrl().toString());

            application.update()
                    .withoutCredential("passwd")
                    .apply();
            System.out.println(application.id() + " - " + application.applicationId());
            Assert.assertEquals(0, application.passwordCredentials().size());
        } finally {
            if (application != null) {
                graphRbacManager.applications().deleteById(application.id());
            }
        }
    }

}
