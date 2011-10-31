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

package com.shroggle.presentation;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.User;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stasuk Artem
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class NoBotImageActionTest {

    @Test
    public void executeWithoutLogin() throws Exception {
        configStorage.get().setNoBotImageTemplates("aaaaa");

        action.setNoBotPrefix("fff");
        action.execute();

        Assert.assertNotNull(sessionStorage.getNoBotCode(action, "fff"));
    }

    @Test
    public void execute() throws Exception {
        configStorage.get().setNoBotImageTemplates("aaaa");

        User account = new User();
        persistance.putUser(account);

        action.setNoBotPrefix("fff");
        action.execute();

        Assert.assertNotNull(sessionStorage.getNoBotCode(action, "fff"));
    }

    @Test
    public void executeWithEmptyTemplate() throws Exception {
        configStorage.get().setNoBotImageTemplates("");

        User account = new User();
        persistance.putUser(account);

        action.setNoBotPrefix("fff");
        action.execute();

        Assert.assertNotNull(sessionStorage.getNoBotCode(action, "fff"));
        Assert.assertEquals("aaaaaa", sessionStorage.getNoBotCode(action, "fff"));
    }

    @Test
    public void executeWithNullTemplate() throws Exception {
        User account = new User();
        persistance.putUser(account);

        action.setNoBotPrefix("fff");
        action.execute();

        Assert.assertNotNull(sessionStorage.getNoBotCode(action, "fff"));
        Assert.assertEquals(GetNoBotImageCommand.DEFAULT_TEMPLATE, sessionStorage.getNoBotCode(action, "fff"));
    }

    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final NoBotImageAction action = new NoBotImageAction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();

}