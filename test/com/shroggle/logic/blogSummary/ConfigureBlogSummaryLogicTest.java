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
package com.shroggle.logic.blogSummary;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.blogSummary.BlogSummaryData;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ConfigureBlogSummaryLogicTest {


    @Test
    public void testCreateSelectedBlogsIds_testCreateBlogsIdsAsString() {
        WidgetItem widget1 = TestUtil.createWidgetBlog(1);
        widget1.setCrossWidgetId(111);
        WidgetItem widget2 = TestUtil.createWidgetBlog(2);
        widget2.setCrossWidgetId(222);
        WidgetItem widget3 = TestUtil.createWidgetBlog(3);
        widget3.setCrossWidgetId(333);

        BlogSummaryData blogSummaryData1 = new BlogSummaryData();
        blogSummaryData1.setBlogId(1);
        blogSummaryData1.setWidgetItems(Arrays.asList(widget1));

        BlogSummaryData blogSummaryData2 = new BlogSummaryData();
        blogSummaryData2.setBlogId(2);
        blogSummaryData2.setWidgetItems(Arrays.asList(widget3));

        List<BlogSummaryData> blogSummaryDatas = Arrays.asList(blogSummaryData1, blogSummaryData2);


        List<Integer> selectedCrossWidgetsIds = Arrays.asList(111, 333);

        List<Integer> selectedBlogsIds = ConfigureBlogSummaryLogic.createSelectedBlogsIds(selectedCrossWidgetsIds, blogSummaryDatas);

        Assert.assertEquals(2, selectedBlogsIds.size());
        Assert.assertEquals(1, selectedBlogsIds.get(0).intValue());
        Assert.assertEquals(2, selectedBlogsIds.get(1).intValue());

        final String blogsIdsAsString = ConfigureBlogSummaryLogic.createBlogsIdsAsString(blogSummaryDatas);
        Assert.assertEquals("1;2", blogsIdsAsString);
    }

}
