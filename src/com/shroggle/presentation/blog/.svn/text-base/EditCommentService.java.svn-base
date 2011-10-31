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
import com.shroggle.entity.User;
import com.shroggle.logic.blog.BlogRight;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class EditCommentService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            entityClass = Comment.class,
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "commentId")
    @RemoteMethod
    public void execute(final EditCommentRequest request) {
        if (request.getCommentText() == null || request.getCommentText().trim().isEmpty()) {
            return;
        }

        final User user = new UsersManager().getLoginedUser();
        final Integer loginedUserId = user != null ? user.getUserId() : null;
        final Comment comment = BlogRight.getCommentOnEdit(
                loginedUserId, request.getCommentId());
        persistanceTransaction.execute(new Runnable() {

            public void run() {
                if (request.isAsDraft()) {
                    comment.setDraftText(request.getCommentText());
                } else {
                    comment.setText(request.getCommentText());
                }
            }

        });
    }

    private SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}