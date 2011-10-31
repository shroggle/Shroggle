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

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Stasuk Artem
 */
@RunWith(value = Parameterized.class)
public class MailAddressNoInternetUseValidatorTest {

    @Parameterized.Parameters
    public static Collection getParemeters() {
        return Arrays.asList(new Object[][]{
                {"artem", false},
                {"artem@", false},
                {"1@gmail.com", true},
                {"artem@2.3", true},
                {"artem@.", false},
                {"artem@..", false},
                {"artem@.@", false},
                {"русская@почта", false},
                {"@gmail", false},
                {"*@?.\\ ", false},
                {"a@gmail.com", true},
                {"artem@gmail.3.5", true},
                {"artem@gmail.35", true},
                {"artem@gmail.com3", true},
                {"artem@gmail.12com", true},
                {"_artem@gmail.com", true},
                {"artem_@gmail.com", true},
                {"artem@_gmail.com", false},
                {"artem@gmail._com", false},
                {"artem@1gmail.com", true},
                {"artem12.2stasuk@gmail.com", true},
                {"artem.stasuk@gmail.com", true},
                {"artem.stasuk@gmail.uk.org", true},
                {"artem.stasuk@gmail..com", false},
                {"artem.stasuk@gmail", false},
                {"artem@gmail.com", true},
                {"artem@@gmail", false},
                {"artem@gmail", false},
                {"a+a@gmail.com", true},
                {"a@A.com", true}});
    }

    /* @Parameterized.Parameters
    public static Collection getParemeters() {
        return Arrays.asList(new Object[][]{
                {"artem", false},
                {"artem@", false},
                {"1@gmail.com", false},
                {"artem@2.3", false},
                {"artem@.", false},
                {"artem@..", false},
                {"artem@.@", false},
                {"русская@почта", false},
                {"@gmail", false},
                {"*@?.\\ ", false},
                {"a@gmail.com", true},
                {"artem@gmail.3.5", true},
                {"artem@gmail.35", true},
                {"artem@gmail.com3", true},
                {"artem@gmail.12com", true},
                {"_artem@gmail.com", true},
                {"artem_@gmail.com", true},
                {"artem@_gmail.com", true},
                {"artem@gmail._com", true},
                {"artem@1gmail.com", false},
                {"artem12.2stasuk@gmail.com", true},
                {"artem.stasuk@gmail.com", true},
                {"artem.stasuk@gmail.uk.org", true},
                {"artem.stasuk@gmail..com", false},
                {"artem.stasuk@gmail", true},
                {"artem@gmail.com", true},
                {"artem@@gmail", false},
                {"artem@gmail", true}});
    }*/

    public MailAddressNoInternetUseValidatorTest(final String testEmail, final boolean testResult) {
        this.testEmail = testEmail;
        this.testResult = testResult;
    }

    @Test
    public void valid() {
        Assert.assertEquals(
                "Can't correct validate email: " + testEmail + ", but must be " + testResult,
                testResult, mailAddressValidator.valid(testEmail));
    }

    @Test
    public void validWithNullEmail() {
        Assert.assertFalse(mailAddressValidator.valid(null));
    }

    private final String testEmail;
    private final boolean testResult;
    private final MailAddressValidator mailAddressValidator =
            new MailAddressNoInternetUseValidator();

}
