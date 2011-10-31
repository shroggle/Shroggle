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
package com.shroggle.util.url;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.MockConfigStorage;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(Parameterized.class)
public class UrlValidatorSubDomainTest {

    @Parameterized.Parameters
    public static List getData() {
        return Arrays.asList(new Object[] {"web-deva.com", "www.zcontest", true},
                new Object[] {"web-deva.com", "zcontest", true},
                new Object[] {"web-deva.com", "a", true},
                new Object[] {"web-deva.com", "zcontest.ru/dir%201/dir_2/program.ext?var1=x&var2=my%20value", true},
                new Object[] {"web-deva.com", "zcon.com/index.html#bookmark", true},
                new Object[] {"web-deva.com", "localhost", true},
                new Object[] {"web-deva.com", "domain-", true},
                new Object[] {"web-deva.com", "localhost:8080", false},
                new Object[] {"web-deva.com", "sub.zcontest-ru.com:8080", false},
                new Object[] {"web-deva.com", "Just Text.", false},
                new Object[] {"web-deva.com", null, false});
    }

    public UrlValidatorSubDomainTest(final String userSitesUrl, final String subDomain, final boolean result) {
        this.userSitesUrl = userSitesUrl;
        this.subDomain = subDomain;
        this.result = result;
    }

    @Test
    public void test() {
        ServiceLocator.setConfigStorage(new MockConfigStorage());
        ServiceLocator.getConfigStorage().get().setUserSitesUrl(userSitesUrl);
        Assert.assertEquals(result, UrlValidator.isSystemSubDomainValid(subDomain));
    }

    private final String userSitesUrl;
    private final String subDomain;
    private final boolean result;

}