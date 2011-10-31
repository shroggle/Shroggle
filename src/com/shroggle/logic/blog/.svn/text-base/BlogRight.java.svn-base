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
import com.shroggle.exception.BlogPostNotFoundException;
import com.shroggle.exception.CommentAddWithoutRightException;
import com.shroggle.exception.CommentNotFoundException;
import com.shroggle.logic.site.AccessGroupManager;
import com.shroggle.util.ServiceLocator;

/**
 * @author Artem Stasuk
 */
public class BlogRight {

    public static BlogPost getBlogPostOnEdit(final Integer visitorId, final Integer blogPostId) {
        final BlogPost blogPost = ServiceLocator.getPersistance().getBlogPostById(blogPostId);
        if (blogPost == null) throw new BlogPostNotFoundException("Can't find blog post with id " + blogPostId);

        final AccessGroup editBlogPostRight = blogPost.getBlog().getEditBlogPostRight();
        final User user = ServiceLocator.getPersistance().getUserById(visitorId);
        final Site site = ServiceLocator.getPersistance().getSite(blogPost.getBlog().getSiteId());

        if (AccessGroupManager.isUserFitsForAccessGroup(editBlogPostRight, user, site)) {
            return blogPost;
        } else if (isAuthor(visitorId, blogPost.getVisitorId())) {
            return blogPost;
        }

        // in other cases denied
        throw new BlogPostNotFoundException(
                "You can't edit blog post with id " + blogPost.getBlogPostId());
    }

    public static boolean allowAddBlogPost(final User user, final Blog blog) {
        final AccessGroup addBlogPostRight = blog.getAddPostRight();
        return AccessGroupManager.isUserFitsForAccessGroup(addBlogPostRight, user,
                ServiceLocator.getPersistance().getSite(blog.getSiteId()));
    }

    public static boolean isAllowAddCommentOnBlogPost(final User user, final Blog blog) {
        final AccessGroup addCommentOnBlogPostRight = blog.getAddCommentOnPostRight();
        return AccessGroupManager.isUserFitsForAccessGroup(addCommentOnBlogPostRight, user,
                ServiceLocator.getPersistance().getSite(blog.getSiteId()));
    }

    public static boolean allowAddCommentOnComment(final User user, final DraftBlog blog) {
        final AccessGroup addCommentOnCommentRight = blog.getAddCommentOnCommentRight();
        return AccessGroupManager.isUserFitsForAccessGroup(addCommentOnCommentRight, user,
                ServiceLocator.getPersistance().getSite(blog.getSiteId()));
    }

    public static BlogPost getBlogPostOnAddComment(
            final Integer loginVisitorId, final Integer blogPostId) {
        final User loginUser = ServiceLocator.getPersistance().getUserById(loginVisitorId);
        final BlogPost blogPost = ServiceLocator.getPersistance().getBlogPostById(blogPostId);
        if (blogPost == null) throw new CommentAddWithoutRightException();
        if (!isAllowAddCommentOnBlogPost(loginUser, blogPost.getBlog())) {
            throw new CommentAddWithoutRightException();
        }
        return blogPost;
    }

//    public static boolean allowEditBlogPost(final User user, final DraftBlog blog) {
//        final AccessGroup editBlogPostRight = blog.getEditBlogPostRight();
//        return AccessGroupManager.isUserFitsForAccessGroup(editBlogPostRight, user,
//                ServiceLocator.getPersistance().getSite(blog.getSiteId()));
//    }

    public static Comment getCommentOnEdit(final Integer visitorId, final Integer commentId) {
        final Comment comment = ServiceLocator.getPersistance().getCommentById(commentId);
        if (comment == null) throw new CommentNotFoundException("Can't find comment with id " + commentId);
        if (allowEditComment(visitorId, comment)) {
            return comment;
        }

        // in other cases denied
        throw new CommentNotFoundException(
                "You can't edit comment with id " + comment.getCommentId());
    }

    public static boolean allowEditComment(final Integer visitorId, final Comment comment) {
        final AccessGroup editComment = comment.getBlogPost().getBlog().getEditCommentRight();
        final User user = ServiceLocator.getPersistance().getUserById(visitorId);
        final Site site = ServiceLocator.getPersistance().getSite(comment.getBlogPost().getBlog().getSiteId());
        return AccessGroupManager.isUserFitsForAccessGroup(editComment, user, site)
                || isAuthor(visitorId, comment.getVisitorId());
    }

    public static boolean isAuthor(final Integer possibleAuthorId, final Integer authorId) {
        return possibleAuthorId != null && authorId != null && possibleAuthorId.equals(authorId);
    }

}
