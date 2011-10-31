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
package com.shroggle.util.mail;

import com.shroggle.TestRunnerWithMockServices;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class MailTest {

    @Test
    public void test_withoutArguments() {
        final Mail mail = new Mail();
        Assert.assertEquals("shroggle-admin@email", mail.getFrom());
    }

    @Test
    public void test_withThreeArguments() {
        final Mail mail = new Mail("a@a.a", "text", "subject");
        Assert.assertEquals("shroggle-admin@email", mail.getFrom());
        Assert.assertEquals("a@a.a", mail.getTo());
        Assert.assertEquals("text", mail.getText());
        Assert.assertEquals("subject", mail.getSubject());
    }

    @Test
    public void test_withFourArguments() {
        final Mail mail = new Mail("a@a.a", "text", "html", "subject");
        Assert.assertEquals("shroggle-admin@email", mail.getFrom());
        Assert.assertEquals("a@a.a", mail.getTo());
        Assert.assertEquals("text", mail.getText());
        Assert.assertEquals("html", mail.getHtml());
        Assert.assertEquals("subject", mail.getSubject());
    }

    @Test
    public void test_withoutArguments_setFrom() {
        final Mail mail = new Mail();
        Assert.assertEquals("shroggle-admin@email", mail.getFrom());

        mail.setFrom("test@test.test");

        Assert.assertEquals("test@test.test <shroggle-admin@email>", mail.getFrom());
    }

}
