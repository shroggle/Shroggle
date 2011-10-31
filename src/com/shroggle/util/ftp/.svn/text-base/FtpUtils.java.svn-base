/*********************************************************************
 *                                                                   *
 * Copyright (c) 2007-2011 by Web-Deva.                              *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/
package com.shroggle.util.ftp;

import com.shroggle.util.StringUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.tools.ant.filters.StringInputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class FtpUtils {

    public FtpUtils(String ftpServerName, String ftpLogin, String ftpPassword) {
        if (StringUtil.isNullOrEmpty(ftpServerName)) {
            throw new IllegalArgumentException("Unable to create FtpUtil without ftp server name.");
        }
        ftpServerName = ftpServerName.replaceFirst("ftp://", "");
        if (ftpServerName.endsWith("/")) {
            ftpServerName = ftpServerName.substring(0, ftpServerName.length() - 1);
        }

        this.ftpServerName = ftpServerName;
        this.ftpLogin = ftpLogin;
        this.ftpPassword = ftpPassword;
    }

    public void uploadFile(final String file, final String fileName) {
        final FTPClient ftp = connect();

        try {
            if (!StringUtil.isNullOrEmpty(ftpLogin) && !StringUtil.isNullOrEmpty(ftpPassword)) {
                final boolean logined = ftp.login(ftpLogin, ftpPassword);
                if (!logined) {
                    ftp.logout();
                    return;
                }
            }

            // Use passive mode as default because most of us are behind firewalls these days.
            ftp.enterLocalPassiveMode();
            final StringInputStream input = new StringInputStream(file);
            ftp.storeFile(fileName, input);
            input.close();
            ftp.logout();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to save file to ftp.", e);
        } finally {
            disconnect(ftp);
        }
    }

    public void downloadFile(final String fileName, final OutputStream outputStream) {
        final FTPClient ftp = connect();

        try {
            if (!StringUtil.isNullOrEmpty(ftpLogin) && !StringUtil.isNullOrEmpty(ftpPassword)) {
                final boolean logined = ftp.login(ftpLogin, ftpPassword);
                if (!logined) {
                    ftp.logout();
                    return;
                }
            }

            // Use passive mode as default because most of us are behind firewalls these days.
            ftp.enterLocalPassiveMode();
            ftp.retrieveFile(fileName, outputStream);
            outputStream.flush();
            outputStream.close();
            ftp.logout();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to save file to ftp.", e);
        } finally {
            disconnect(ftp);
        }
    }

    /*------------------------------------------------Private methods-------------------------------------------------*/

    private FTPClient connect() {
        final FTPClient ftp = new FTPClient();
        try {
            ftp.connect(ftpServerName);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                disconnect(ftp);
                logger.info("Unable to save file to ftp. Connection was refused by ftp server.");
                return null;
            }
        } catch (Exception e) {
            disconnect(ftp);
            logger.info("Unable to connect to server.");
            return null;
        }
        return ftp;
    }

    private void disconnect(FTPClient ftp) {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IOException f) {
                logger.info("Unable to disconnect from ftp server.");
            }
        }
    }
    /*------------------------------------------------Private methods-------------------------------------------------*/
    private final String ftpServerName;

    private final String ftpLogin;

    private final String ftpPassword;

    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
