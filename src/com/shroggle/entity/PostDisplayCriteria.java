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

import com.shroggle.logic.blogSummary.ShowBlogSummaryLogic;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.convert.EnumConverter;

import java.util.Comparator;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject(converter = EnumConverter.class)
public enum PostDisplayCriteria {

    MOST_RECENT(new Comparator<ShowBlogSummaryLogic.BlogPostData>() {
        public int compare(final ShowBlogSummaryLogic.BlogPostData blogPost1, final ShowBlogSummaryLogic.BlogPostData blogPost2) {
            return blogPost2.getBlogPost().getCreationDate().compareTo(blogPost1.getBlogPost().getCreationDate());
        }
    }),
    MOST_READ(new Comparator<ShowBlogSummaryLogic.BlogPostData>() {
        public int compare(final ShowBlogSummaryLogic.BlogPostData blogPost1, final ShowBlogSummaryLogic.BlogPostData blogPost2) {
            return Integer.valueOf(blogPost2.getBlogPost().getPostRead()).compareTo(blogPost1.getBlogPost().getPostRead());
        }
    }),
    MOST_COMMENTED(new Comparator<ShowBlogSummaryLogic.BlogPostData>() {
        public int compare(final ShowBlogSummaryLogic.BlogPostData blogPost1, final ShowBlogSummaryLogic.BlogPostData blogPost2) {
            return Integer.valueOf(blogPost2.getBlogPost().getPostedToWorkComments().size()).compareTo(blogPost1.getBlogPost().getPostedToWorkComments().size());
        }
    });


    PostDisplayCriteria(final Comparator<ShowBlogSummaryLogic.BlogPostData> comparator) {
        this.comparator = comparator;
    }

    public Comparator<ShowBlogSummaryLogic.BlogPostData> getComparator() {
        return comparator;
    }

    private final Comparator<ShowBlogSummaryLogic.BlogPostData> comparator;
}