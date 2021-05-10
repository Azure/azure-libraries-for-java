/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.compute;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.util.Hashtable;

/**
 * Utility class to run commands on Linux VM via SSH.
 */
public class SshShell {
    private final Session session;
    private final ChannelShell channel;

    /**
     * Creates SSHShell.
     *
     * @param host the host name
     * @param port the ssh port
     * @param userName the ssh user name
     * @param password the ssh password
     * @return the shell
     * @throws JSchException
     */
    private SshShell(String host, int port, String userName, String password)
            throws JSchException {
        JSch jsch = new JSch();
        this.session = jsch.getSession(userName, host, port);
        session.setPassword(password);
        Hashtable<String,String> config = new Hashtable<>();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect(60000);
        this.channel = (ChannelShell) session.openChannel("shell");
        channel.connect();
    }

    /**
     * Opens a SSH shell.
     *
     * @param host the host name
     * @param port the ssh port
     * @param userName the ssh user name
     * @param password the ssh password
     * @return the shell
     * @throws JSchException
     * @throws IOException
     */
    public static SshShell open(String host, int port, String userName, String password)
            throws JSchException, IOException {
        return new SshShell(host, port, userName, password);
    }

    /**
     * Closes shell.
     */
    public void close() {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
}
