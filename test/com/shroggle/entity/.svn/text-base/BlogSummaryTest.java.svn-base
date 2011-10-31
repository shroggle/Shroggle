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
package com.shroggle.entity;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 */
public class BlogSummaryTest {

    @Test
    public void createDefaultBlogSummary() {
        DraftBlogSummary blogSummary = new DraftBlogSummary();
        Assert.assertEquals(true, blogSummary.isCurrentSiteBlogs());
        Assert.assertEquals(false, blogSummary.isAllSiteBlogs());
        Assert.assertEquals("", blogSummary.getBlogSummaryHeader());
        Assert.assertEquals(-1, blogSummary.getId());
        Assert.assertEquals(1, blogSummary.getIncludedPostNumber());
        Assert.assertEquals(5, blogSummary.getNumberOfWordsToDisplay());
        Assert.assertEquals(PostDisplayCriteria.MOST_RECENT, blogSummary.getPostDisplayCriteria());
        Assert.assertEquals(PostSortCriteria.CHRONOLOGICALLY_BY_POST_DATE_DESC, blogSummary.getPostSortCriteria());
        Assert.assertNotNull(blogSummary.getIncludedCrossWidgetId());
        Assert.assertEquals(0, blogSummary.getIncludedCrossWidgetId().size());
        Assert.assertEquals(true, blogSummary.isShowPostName());
        Assert.assertEquals(false, blogSummary.isShowPostContents());
        Assert.assertEquals(true, blogSummary.isShowPostAuthor());
        Assert.assertEquals(false, blogSummary.isShowBlogName());
    }
}