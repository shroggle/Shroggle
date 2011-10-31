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
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class DeleteCommentService extends AbstractService {

    @SynchronizeByMethodParameter(
            entityClass = Comment.class,
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public void execute(final int commentId) {
        final User user = new UsersManager().getLoginedUser();
        final Integer loginedUserId = user != null ? user.getUserId() : null;
        final Comment comment = BlogRight.getCommentOnEdit(loginedUserId, commentId);
        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                persistance.removeComment(comment);
            }

        });
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}