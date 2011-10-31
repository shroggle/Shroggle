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
package com.shroggle.util.config;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 15.10.2008
 */
public class ConfigSmtpTest {

    @Test
    public void getRegistrationUrl() {
        ConfigSmtp configSmtp = new ConfigSmtp();
        Assert.assertNull(configSmtp.getLogin());
        configSmtp.setLogin("ff");
        Assert.assertEquals("ff", configSmtp.getLogin());
    }

    @Test
    public void getPassword() {
        ConfigSmtp configSmtp = new ConfigSmtp();
        Assert.assertNull(configSmtp.getPassword());
        configSmtp.setPassword("ff");
        Assert.assertEquals("ff", configSmtp.getPassword());
    }

    @Test
    public void getUrl() {
        ConfigSmtp configSmtp = new ConfigSmtp();
        Assert.assertNull(configSmtp.getUrl());
        configSmtp.setUrl("ff");
        Assert.assertEquals("ff", configSmtp.getUrl());
    }

    @Test
    public void getEmailUpdateApprovalLink() {
        ConfigSmtp configSmtp = new ConfigSmtp();
        Assert.assertNull(configSmtp.getEmailUpdateApprovalLink());
        configSmtp.setEmailUpdateApprovalLink("ff");
        Assert.assertEquals("ff", configSmtp.getEmailUpdateApprovalLink());
    }

}