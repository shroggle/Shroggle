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
package com.shroggle.logic.blog;

import com.shroggle.entity.BlogPost;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class BlogPostsManager {

    public BlogPostsManager(BlogManager blogManager, final int start, final Integer exactBlogPostId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final Integer loginVisitorId;
        if (blogManager.isVisitorOwner()) {
            loginVisitorId = null;
        } else if (blogManager.getLoginVisitorId() == null) {
            loginVisitorId = Integer.MIN_VALUE;
        } else {
            loginVisitorId = blogManager.getLoginVisitorId();
        }

        final List<BlogPost> blogPosts;
        if (exactBlogPostId != null) {
            //Displaying one concrete post.
            size = 1;
            blogPosts = persistance.getBlogPosts(blogManager.getBlogId(), loginVisitorId, exactBlogPostId,
                    0, size, blogManager.getNotOlderThanDateForPosts(new Date()));
        } else {
            size = blogManager.getDisplayedPostsSize();
            blogPosts = persistance.getBlogPosts(blogManager.getBlogId(), loginVisitorId, null,
                    start, size, blogManager.getNotOlderThanDateForPosts(new Date()));
        }

        final List<BlogPostManager> blogPostManagers = new ArrayList<BlogPostManager>();
        for (final BlogPost blogPost : blogPosts) {
            final boolean isOnlyDraft = blogPost.getText() == null && blogPost.getDraftText() != null;
            final boolean isAuthor = blogManager.getLoginVisitorId() != null
                    && blogManager.getLoginVisitorId().equals(blogPost.getVisitorId());
            final boolean isAuthorOrOwnerOrEditable = isAuthor || blogManager.isVisitorOwner() || blogManager.allowEditBlogPosts();
            if (isOnlyDraft && !isAuthorOrOwnerOrEditable) continue;

            final BlogPostManager blogPostManager = new BlogPostManager(blogManager);
            blogPostManager.setEditable(isAuthorOrOwnerOrEditable);
            blogPostManager.setDraft(isAuthorOrOwnerOrEditable && blogPost.getDraftText() != null);
            blogPostManager.setAuthor(isAuthor);
            blogPostManager.setOwner(blogManager.isVisitorOwner());
            blogPostManager.setBlogPost(blogPost);
            blogPostManagers.add(blogPostManager);
        }
        items = blogPostManagers;

        if (items.size() > 0) {
            final BlogPostManager lastItem = items.get(items.size() - 1);
            itemsBefore = persistance.getBlogPostsBeforeByBlogAndUserId(
                    blogManager.getBlogId(), loginVisitorId, lastItem.getBlogPostId());

            final BlogPostManager firstItem = items.get(0);
            itemsAfter = persistance.getBlogPostsAfterByBlogAndUserId(
                    blogManager.getBlogId(), loginVisitorId, firstItem.getBlogPostId());

            //Be aware that posts in blog are in revert order of their adding.
            if (exactBlogPostId != null){
                prevBlogPostId = persistance.getPrevOrNextBlogPostId(blogManager.getBlogId(),
                        loginVisitorId, exactBlogPostId, true);
                nextBlogPostId = persistance.getPrevOrNextBlogPostId(blogManager.getBlogId(),
                        loginVisitorId, exactBlogPostId, false);
            } else {
                nextStartIndex = start - size;
                prevStartIndex = start + size;
            }
        } else {
            itemsBefore = 0;
            itemsAfter = 0;
        }
    }

    /**
     * @return - blog post items for current page
     */
    public List<BlogPostManager> getItems() {
        return items;
    }

    /**
     * @return - count items on next page
     */
    public int getItemsBefore() {
        return Math.min(itemsBefore, size);
    }

    /**
     * @return - start index for next page
     */
    public int getStartBefore() {
        return itemsBefore;
    }

    /**
     * @return - start index for previous page
     */
    public int getStartAfter() {
        return Math.max(itemsAfter - size, 0);
    }

    /**
     * @return - count items on previous page
     */
    public int getItemsAfter() {
        return Math.min(itemsAfter, size);
    }

    /**
     * @return return recommendation size from config, in real getItems can has less size.
     */
    public int getSize() {
        return size;
    }

    public int getNextStartIndex() {
        return nextStartIndex;
    }

    public int getPrevStartIndex() {
        return prevStartIndex;
    }

    public Integer getPrevBlogPostId() {
        return prevBlogPostId;
    }

    public Integer getNextBlogPostId() {
        return nextBlogPostId;
    }

    private final List<BlogPostManager> items;
    private final int itemsAfter;
    private final int itemsBefore;
    private int nextStartIndex = 0;
    private int prevStartIndex = 0;
    private Integer prevBlogPostId;
    private Integer nextBlogPostId;
    private final int size;
}
