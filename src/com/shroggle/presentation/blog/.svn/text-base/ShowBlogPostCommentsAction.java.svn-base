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

package com.shroggle.presentation.blog;

import com.shroggle.entity.BlogPost;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.User;
import com.shroggle.logic.blog.BlogManager;
import com.shroggle.logic.blog.BlogRight;
import com.shroggle.logic.blog.CommentSecurity;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Stasuk Artem
 */
@UrlBinding(value = "/blog/showBlogPostComments.action")
public class ShowBlogPostCommentsAction extends Action {

    @SynchronizeByClassProperty(
            entityIdFieldPath = "blogPostId",
            entityClass = BlogPost.class)
    @DefaultHandler
    public Resolution execute() {
        final BlogPost blogPost = persistance.getBlogPostById(blogPostId);
        if (blogPost == null) {
            return resolutionCreator.forwardToUrl("/blog/showBlogPostCommentsNotFound.jsp");
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                blogPost.setPostRead(blogPost.getPostRead() + 1);
            }

        });

        final BlogManager blogManager = new BlogManager(blogPost.getBlog().getId(), SiteShowOption.INSIDE_APP); // todo. Fix (add correct site show option). Tolik
        final List<CommentSecurity> commentSecurities = blogManager.getBlogPostComments(blogPost);
        comments = commentSecurities.size();

        commentNodes = new ArrayList<CommentNode>();
        for (final CommentSecurity commentSecurity : commentSecurities) {
            if (commentSecurity.getComment().getParentComment() == null) {
                final CommentNode commentNode = new CommentNode();
                commentNode.setCommentSecurity(commentSecurity);
                if (commentSecurity.getComment().getVisitorId() != null) {
                    commentNode.setVisitor(persistance.getUserById(commentSecurity.getComment().getVisitorId()));
                }
                commentNodes.add(commentNode);
                findChild(commentNode, commentSecurities);
            }
        }

        Collections.sort(commentNodes, new BlogCommentComparator());

        final User loginUser = new UsersManager().getLoginedUser();
        showAddComment = BlogRight.allowAddCommentOnComment(loginUser, blogPost.getBlog());

        return resolutionCreator.forwardToUrl("/blog/showBlogPostComments.jsp");
    }

    private void findChild(
            final CommentNode commentNode, final List<CommentSecurity> commentSecurities) {
        for (final CommentSecurity tempCommentSecurity : commentSecurities) {
            if (commentNode.getCommentSecurity().getComment() == tempCommentSecurity.getComment().getParentComment()) {
                final CommentNode childCommentNode = new CommentNode();
                if (tempCommentSecurity.getComment().getVisitorId() != null) {
                    childCommentNode.setVisitor(persistance.getUserById(tempCommentSecurity.getComment().getVisitorId()));
                }
                childCommentNode.setCommentSecurity(tempCommentSecurity);
                commentNode.getChildCommentNodes().add(childCommentNode);
                findChild(childCommentNode, commentSecurities);
            }
        }

        Collections.sort(commentNode.getChildCommentNodes(), new BlogCommentComparator());
    }

    public List<CommentNode> getCommentNodes() {
        return commentNodes;
    }

    public boolean isShowAddComment() {
        return showAddComment;
    }

    public int getWidgetBlogId() {
        return widgetBlogId;
    }

    public int getComments() {
        return comments;
    }

    public int getBlogPostId() {
        return blogPostId;
    }

    public void setWidgetBlogId(final int widgetBlogId) {
        this.widgetBlogId = widgetBlogId;
    }

    public void setBlogPostId(final int blogPostId) {
        this.blogPostId = blogPostId;
    }

    public static final class CommentNode {

        private User user;
        private CommentSecurity commentSecurity;
        private List<CommentNode> childCommentNodes = new ArrayList<CommentNode>();

        public CommentSecurity getCommentSecurity() {
            return commentSecurity;
        }

        public List<CommentNode> getChildCommentNodes() {
            return childCommentNodes;
        }

        public void setCommentSecurity(CommentSecurity commentSecurity) {
            this.commentSecurity = commentSecurity;
        }

        public void setChildCommentNodes(List<CommentNode> childCommentNodes) {
            this.childCommentNodes = childCommentNodes;
        }

        public User getVisitor() {
            return user;
        }

        public void setVisitor(User user) {
            this.user = user;
        }

    }

    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private int widgetBlogId;
    private int blogPostId;
    private boolean showAddComment;
    private int comments;
    private List<CommentNode> commentNodes;

    private static class BlogCommentComparator implements Comparator<CommentNode> {

        public int compare(CommentNode o1, CommentNode o2) {
            return o1.getCommentSecurity().getComment().getCreated().compareTo(
                    o2.getCommentSecurity().getComment().getCreated());
        }

    }

}