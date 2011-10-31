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
package com.shroggle.logic.widget;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class WidgetSortTest {

    @Test
    public void test() {
        User user = TestUtil.createUserAndLogin("a@a.com");
        Site site = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site, "page1");
        DraftBlog blog = TestUtil.createBlog(site);

        List<WidgetItem> widgetBlogs = new ArrayList<WidgetItem>();
        for (int i = 0; i < 100; i++) {
            widgetBlogs.add(TestUtil.createWidgetBlogForPageVersion(pageVersion, blog.getId(), 100 - i));
        }
        Assert.assertEquals(100, widgetBlogs.get(0).getPosition());
        Assert.assertEquals(99, widgetBlogs.get(1).getPosition());
        Assert.assertEquals(2, widgetBlogs.get(98).getPosition());
        Assert.assertEquals(1, widgetBlogs.get(99).getPosition());
        Collections.sort(widgetBlogs, WidgetSort.widgetPositionComparator);
        Assert.assertEquals(1, widgetBlogs.get(0).getPosition());
        Assert.assertEquals(2, widgetBlogs.get(1).getPosition());
        Assert.assertEquals(99, widgetBlogs.get(98).getPosition());
        Assert.assertEquals(100, widgetBlogs.get(99).getPosition());
    }
}
