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
public class UrlValidatorTest {

    @Parameterized.Parameters
    public static List getData() {
        return Arrays.asList(
                new Object[]{"web-deva.com", "http://www.zcontest.ru", true},
                new Object[]{"web-deva.com", "http://zcontest.ru", true},
                new Object[]{"web-deva.com", "http://zcontest.com", true},
                new Object[]{"web-deva.com", "https://zcontest.ru", true},
                new Object[]{"web-deva.com", "https://sub.zcontest-ru.com:8080", true},
                new Object[]{"web-deva.com", "http://zcontest.ru/dir%201/dir_2/program.ext?var1=x&var2=my%20value", true},
                new Object[]{"web-deva.com", "zcon.com/index.html#bookmark", true},
                new Object[]{"web-deva.com", "http://www.shroggle.com/site/createSite.action?createChildSite=true&settingsId=10", true},
                new Object[]{"web-deva.com", "http://www.domain-.shroggle.com", true},
                new Object[]{"web-deva.com", "http://www.google.com.ua/search?hl=ru&client=firefox&rls=org.mozilla%3Aru%3Aofficial&q=regexp+valid+url+examples&btnG=%D0%9F%D0%BE%D0%B8%D1%81%D0%BA&meta=", true},
                new Object[]{"web-deva.com", "http://jira.web-deva.com/browse/SW-3533", true},
                new Object[]{"web-deva.com", "http://localhost:8080/site/createSite.action?createChildSite=true&settingsId=10", false},
                new Object[]{"web-deva.com", "Just Text.", false},
                new Object[]{"web-deva.com", "http://www.my_domain.shroggle.com", false},
                new Object[]{"web-deva.com", "http://a.com", false},
                new Object[]{"web-deva.com", "http://www.domain-.com", false},
                new Object[]{"web-deva.com", null, false}
        );
    }

    public UrlValidatorTest(final String userSitesUrl, final String domain, final boolean result) {
        this.userSitesUrl = userSitesUrl;
        this.domain = domain;
        this.result = result;
    }

    @Test
    public void test() {
        ServiceLocator.setConfigStorage(new MockConfigStorage());
        ServiceLocator.getConfigStorage().get().setUserSitesUrl(userSitesUrl);
        Assert.assertEquals("Need: " + result + ", test: " + domain, result, UrlValidator.isDomainValid(domain));
    }

    private final String userSitesUrl;
    private final String domain;
    private final boolean result;

}
