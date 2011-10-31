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
package com.shroggle.util.security;

import junit.framework.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author Artem Stasuk
 */
public class SecurityCheckCreatorTest {

    @Test
    public void execute() throws NoSuchMethodException {
        final Method method = SecurityCheckMock.class.getMethod("test");
        final SecurityCheck check = creator.execute(SecurityCheckMock.class, method);
        Assert.assertEquals(SecurityCheckTrue.class, check.getClass());
    }

    @Test
    public void executeWithUser() throws NoSuchMethodException {
        final Method method = SecurityCheckUserMock.class.getMethod("test");
        final SecurityCheck check = creator.execute(SecurityCheckUserMock.class, method);
        Assert.assertEquals(SecurityCheckUser.class, check.getClass());
    }

    private SecurityCheckCreator creator = new SecurityCheckCreator();

}
