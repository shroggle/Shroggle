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

package com.shroggle.presentation.site;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.User;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.config.MockConfigStorage;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(TestRunnerWithMockServices.class)
public class CheckLocalSiteUrlPrefixServiceTest {
    private ConfigStorage oldConfigStorage;

    @Before
    public void setUp() {
        oldConfigStorage = ServiceLocator.getConfigStorage();

        MockConfigStorage mockConfigStorage = new MockConfigStorage();
        mockConfigStorage.get().setUserSitesUrl("localhost:8080");
        ServiceLocator.setConfigStorage(mockConfigStorage);
    }

    @After
    public void tearDown() {
        ServiceLocator.setConfigStorage(oldConfigStorage);

    }

    @Test
    public void execute_OK_onaLetter() {
        User account = new User();
        persistance.putUser(account);

        Assert.assertNull(service.execute("a", null));
    }

    @Test
    public void execute_OK() {
        User account = new User();
        persistance.putUser(account);

        Assert.assertNull(service.execute("sup", null));
    }

    @Test
    public void execute_noPort() {
        MockConfigStorage mockConfigStorage = new MockConfigStorage();
        mockConfigStorage.get().setUserSitesUrl("localhost");
        ServiceLocator.setConfigStorage(mockConfigStorage);


        User account = new User();
        persistance.putUser(account);

        Assert.assertNull(service.execute("cup", null));
    }


    @Test
    public void executeWithSpace() {
        User account = new User();
        persistance.putUser(account);

        Assert.assertNotNull(service.execute("a a", null));
    }

    private final CheckSiteUrlPrefixService service = new CheckSiteUrlPrefixService();
    private final Persistance persistance = ServiceLocator.getPersistance();

}