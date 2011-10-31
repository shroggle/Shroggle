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
import com.shroggle.entity.Comment;
import com.shroggle.entity.User;
import com.shroggle.exception.CommentWithNullTextException;
import com.shroggle.logic.blog.BlogRight;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Date;

/**
 * @author Stasuk Artem
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class AddBlogPostCommentService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            entityIdPropertyPath = "blogPostId",
            entityClass = BlogPost.class,
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public void execute(final AddBlogPostCommentRequest request) {
        final User user = new UsersManager().getLoginedUser();
        final Integer loginVisitorId = user != null ? user.getUserId() : null;
        final BlogPost blogPost = BlogRight.getBlogPostOnAddComment(loginVisitorId, request.getBlogPostId());

        if (request.getCommentText() == null) {
            throw new CommentWithNullTextException(
                    "Can't create comment with null text for blog post "
                            + request.getBlogPostId());
        }

        final Comment comment = new Comment();
        comment.setCreated(new Date());
        if (loginVisitorId != null) {
            comment.setVisitorId(loginVisitorId);
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                if (request.isAsDraft()) {
                    comment.setDraftText(request.getCommentText());
                } else {
                    comment.setText(request.getCommentText());
                }
                blogPost.addComment(comment);
                persistance.putComment(comment);
            }

        });
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();

}