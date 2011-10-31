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

import com.shroggle.entity.*;
import com.shroggle.exception.BlogNotFoundException;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;

import java.util.*;

/**
 * @author Artem Stasuk
 */
public class BlogManager {

    public BlogManager(final int blogId, final SiteShowOption siteShowOption) {
        this.loginUser = new UsersManager().getLoginedUser();

        blog = (Blog) new ItemManager(blogId).getItem(siteShowOption);

        if (blog == null) throw new BlogNotFoundException("Can't find blog " + blogId);
        boolean tempOwner = false;
        if (this.loginUser != null) {
            final UserRightManager userRightManager = new UserRightManager(loginUser);
            tempOwner = userRightManager.toSiteItem(blog) == SiteOnItemRightType.EDIT;
        }
        this.editableBlogPosts = blog.getEditBlogPostRight() == AccessGroup.ALL
                || blog.getEditBlogPostRight() == AccessGroup.GUEST
                || this.loginUser != null && blog.getEditBlogPostRight() == AccessGroup.VISITORS;
        this.ownerBlog = tempOwner;
    }

    public boolean allowAddBlogPost() {
        return BlogRight.allowAddBlogPost(loginUser, blog);
    }

    public Integer getLoginVisitorId() {
        return this.loginUser != null ? this.loginUser.getUserId() : null;
    }

    public String getName() {
        return blog.getName();
    }

    public int getBlogId() {
        return blog.getId();
    }

    public boolean allowEditBlogPosts() {
        return editableBlogPosts;
    }

    public boolean isAllowAddComment() {
        return BlogRight.isAllowAddCommentOnBlogPost(loginUser, blog);
    }

    /**
     * @return - true if user can see comments for post false in other cases.
     */
    public boolean isAllowComments() {
        return blog.getAddCommentOnPostRight() != null;
    }

//    public DraftBlog getBlog() {
//        return blog;
//    }

    public boolean isVisitorOwner() {
        return ownerBlog;
    }

    public List<CommentSecurity> getBlogPostComments(final BlogPost blogPost) {
        final List<CommentSecurity> commentSecurities = new ArrayList<CommentSecurity>();
        final boolean blogPostOnlyDraft = blogPost.getText() == null && blogPost.getDraftText() != null;
        final boolean blogPostAuthor = loginUser != null && blogPost.getVisitorId() != null && loginUser.getUserId() == blogPost.getVisitorId();
        final boolean blogPostAuthorOrOwnerOrEditable = blogPostAuthor || ownerBlog || editableBlogPosts;
        if (blogPostOnlyDraft && !blogPostAuthorOrOwnerOrEditable) return commentSecurities;

        for (final Comment comment : blogPost.getComments()) {
            final boolean isOnlyDraft = comment.getText() == null && comment.getDraftText() != null;

            final Integer userId = loginUser == null ? null : loginUser.getUserId();
            final boolean editableComment = BlogRight.allowEditComment(userId, comment);

            if (isOnlyDraft && !editableComment) continue;
            final CommentSecurity commentSecurity = new CommentSecurity();
            commentSecurity.setComment(comment);
            commentSecurity.setEditable(editableComment);
            commentSecurity.setDraft(editableComment && comment.getDraftText() != null);
            commentSecurity.setOwner(ownerBlog);
            commentSecurities.add(commentSecurity);
        }
        return commentSecurities;
    }

//    public int getShowBlogPostsCount() {
//        return showBlogPostsCount;
//    }

    public boolean isDisplayAuthorEmailAddress() {
        return blog.isDisplayAuthorEmailAddress();
    }

    public boolean isDisplayAuthorScreenName() {
        return blog.isDisplayAuthorScreenName();
    }

    public boolean isDisplayDate() {
        return blog.isDisplayDate();
    }

    public boolean isDisplayBlogName() {
        return blog.isDisplayBlogName();
    }

    public boolean isDisplayNextAndPreviousLinks() {
        return blog.isDisplayNextAndPreviousLinks();
    }

    public boolean isDisplayBackToTopLink() {
        return blog.isDisplayBackToTopLink();
    }

    public DisplayPosts getDisplayPosts() {
        return blog.getDisplayPosts();
    }

    public int getDisplayPostsFiniteNumber() {
        return blog.getDisplayPostsFiniteNumber();
    }

    public int getDisplayPostsWithinDateRange() {
        return blog.getDisplayPostsWithinDateRange();
    }

    /**
     * @return int:
     *         -1 for displaying all posts
     *         displayPostsFiniteNumber from blog if DISPLAY_FINITE_NUMBER selected
     *         otherwise - showBlogPostCount from Config
     */
    public int getDisplayedPostsSize() {
        switch (getDisplayPosts()) {
            case DISPLAY_ALL: {
                return -1;
            }
            case DISPLAY_FINITE_NUMBER: {
                return getDisplayPostsFiniteNumber();
            }
            default: {
                return ServiceLocator.getConfigStorage().get().getShowBlogPostCount();
            }
        }
    }

    /**
     * @param currentDate - Current Date
     * @return Date:
     *         If displayPosts in blog == DISPLAY_WITHIN_DATE_RANGE - return (currentDate - displayPostsFiniteNumber (1 - 12 months))
     *         otherwise - return null
     */
    public Date getNotOlderThanDateForPosts(final Date currentDate) {
        if (getDisplayPosts() == DisplayPosts.DISPLAY_WITHIN_DATE_RANGE) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(currentDate);
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            int months = getDisplayPostsWithinDateRange();
            calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) - months));
            return calendar.getTime();
        }
        return null;
    }

    public Blog getBlog() {
        return blog;
    }

    private final boolean editableBlogPosts;
    private final boolean ownerBlog;
    private final Blog blog;
    private final User loginUser;

}
