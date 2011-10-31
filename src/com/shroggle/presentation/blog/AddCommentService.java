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

import com.shroggle.entity.Comment;
import com.shroggle.entity.DraftBlog;
import com.shroggle.entity.User;
import com.shroggle.exception.CommentAddWithoutRightException;
import com.shroggle.exception.CommentWithNullTextException;
import com.shroggle.logic.blog.BlogRight;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Date;

/**
 * @author Stasuk Artem
 */
@RemoteProxy
public class AddCommentService extends AbstractService {

    @RemoteMethod
    public void execute(final AddCommentRequest request) {
        if (request.getCommentText() == null) {
            throw new CommentWithNullTextException(
                    "Can't add comment to comment " + request.getCommentId() + " without text!");
        }

        final Comment parentComment = persistance.getCommentById(request.getCommentId());
        if (parentComment == null) {
            return;
        }
        final User user = new UsersManager().getLoginedUser();
        final DraftBlog blog = parentComment.getBlogPost().getBlog();
        if (!BlogRight.allowAddCommentOnComment(user, blog)) {
            throw new CommentAddWithoutRightException();
        }

        final Comment comment = new Comment();
        comment.setVisitorId(user != null ? user.getUserId() : null);
        parentComment.addChildComment(comment);
        comment.setCreated(new Date());

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                if (request.isAsDraft()) {
                    comment.setDraftText(request.getCommentText());
                } else {
                    comment.setText(request.getCommentText());
                }
                parentComment.getBlogPost().addComment(comment);
                persistance.putComment(comment);
            }

        });
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}