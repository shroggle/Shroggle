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

import com.shroggle.entity.BlogPost;
import com.shroggle.entity.PostDisplayCriteria;
import com.shroggle.entity.PostSortCriteria;
import com.shroggle.presentation.blogSummary.BlogSummaryDataForPreview;

import java.util.*;


/**
 * @author Balakirev Anatoliy
 */
public class ShowBlogSummaryLogic {

    public static void sortBlogPosts(final PostSortCriteria sortCriteria, List<BlogSummaryDataForPreview> dataForPreview) {
        for (BlogSummaryDataForPreview blogSummaryData : dataForPreview) {
            Collections.sort(blogSummaryData.getBlogPosts(), sortCriteria.getComparator());
        }
    }

    public static void selectBlogPostsAndRemoveSuperfluous(final PostDisplayCriteria displayCriteria, final int includedPostsNumber,
                                                           final List<BlogSummaryDataForPreview> dataForPreview) {
        // Getting all posts with ids of their data.
        List<BlogPostData> blogPostData = new ArrayList<BlogPostData>();
        for (final BlogSummaryDataForPreview data : dataForPreview) {
            for (BlogPost blogPost : data.getBlogPosts()) {
                blogPostData.add(new BlogPostData(blogPost, data.getId()));
            }
        }
        // Sorting this posts by appropriate criteria.
        Collections.sort(blogPostData, displayCriteria.getComparator());

        // Removing superfluous posts from posts data.
        blogPostData = blogPostData.subList(0, Math.min(includedPostsNumber, blogPostData.size()));

        // Retaining in blogSummaryData only appropriate posts from postsData
        for (final BlogSummaryDataForPreview data : dataForPreview) {
            data.setBlogPosts(getRetainingBlogPosts(data.getId(), blogPostData));
        }

        // Removing data without posts.
        final Iterator<BlogSummaryDataForPreview> iterator = dataForPreview.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getBlogPosts().isEmpty()) {
                iterator.remove();
            }
        }
    }

    private static List<BlogPost> getRetainingBlogPosts(final long id, final List<BlogPostData> blogPostData) {
        final List<BlogPost> posts = new ArrayList<BlogPost>();
        for (BlogPostData postData : blogPostData) {
            if (postData.getBlogSummaryDataId() == id) {
                posts.add(postData.getBlogPost());
            }
        }
        return posts;
    }

    public static class BlogPostData {

        private BlogPostData(BlogPost blogPost, long blogSummaryDataId) {
            this.blogPost = blogPost;
            this.blogSummaryDataId = blogSummaryDataId;
        }

        private final BlogPost blogPost;

        private final long blogSummaryDataId;

        public BlogPost getBlogPost() {
            return blogPost;
        }

        public long getBlogSummaryDataId() {
            return blogSummaryDataId;
        }
    }
}