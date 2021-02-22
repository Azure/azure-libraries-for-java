/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.core;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Common utility functions for the tests.
 */
public class TestUtilities {
    /**
     * Wrapper on the SdkContext.sleep, in case of record mode will not sleep, otherwise sleep.
     * @param milliseconds time in milliseconds for which to sleep.
     */
    public static void sleep(int milliseconds, boolean isRecordMode) {
        if (isRecordMode) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String createPassword() {
        return "12NewPA$$w0rd!";
    }

    private static String sshPublicKey;

    public static String createSshPublicKey() {
        if (sshPublicKey == null) {
            try {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(1024);
                KeyPair pair = keyGen.generateKeyPair();
                PublicKey publicKey = pair.getPublic();

                RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
                ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(byteOs);
                dos.writeInt("ssh-rsa".getBytes(StandardCharsets.US_ASCII).length);
                dos.write("ssh-rsa".getBytes(StandardCharsets.US_ASCII));
                dos.writeInt(rsaPublicKey.getPublicExponent().toByteArray().length);
                dos.write(rsaPublicKey.getPublicExponent().toByteArray());
                dos.writeInt(rsaPublicKey.getModulus().toByteArray().length);
                dos.write(rsaPublicKey.getModulus().toByteArray());
                String publicKeyEncoded = new String(Base64.encodeBase64(byteOs.toByteArray()), StandardCharsets.US_ASCII);
                sshPublicKey = "ssh-rsa " + publicKeyEncoded;
            } catch (NoSuchAlgorithmException | IOException e) {
                throw new IllegalStateException("failed to generate ssh key", e);
            }
        }
        return sshPublicKey;
    }
}
