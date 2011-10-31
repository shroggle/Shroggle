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

import java.util.List;

/**
 * @author Artem Stasuk
 */
public interface BlogSummary {
    int getNumberOfWordsToDisplay();

    boolean isPostTitleALink();

    List<Integer> getIncludedCrossWidgetId();

    PostSortCriteria getPostSortCriteria();

    PostDisplayCriteria getPostDisplayCriteria();

    int getIncludedPostNumber();

    boolean isShowPostName();

    boolean isShowPostContents();

    boolean isShowPostAuthor();

    boolean isShowPostDate();

    boolean isShowBlogName();

    boolean isCurrentSiteBlogs();

    boolean isAllSiteBlogs();

    String getBlogSummaryHeader();

    boolean isDisplayBlogSummaryHeader();
}
