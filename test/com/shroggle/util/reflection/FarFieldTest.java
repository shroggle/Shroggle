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
package com.shroggle.util.reflection;

import com.shroggle.entity.Page;
import com.shroggle.entity.Site;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class FarFieldTest {

    @Test
    public void create() throws NoSuchFieldException {
        new FarField("site", Page.class);
    }

    @Test
    public void createWithPath() throws NoSuchFieldException, IllegalAccessException {
        final FarField farField = new FarField("site.siteId", Page.class);
        Page page = new Page();
        final Site site = new Site();
        site.getSitePaymentSettings().setUserId(-1);
        site.setSiteId(22);
        page.setSite(site);
        Assert.assertEquals(22, farField.get(page));
    }

    @Test
    public void toString1() throws NoSuchFieldException {
        final FarField farField = new FarField("site.siteId", Page.class);
        farField.toString();
    }

}
