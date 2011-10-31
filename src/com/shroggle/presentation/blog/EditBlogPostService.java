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
import com.shroggle.entity.User;
import com.shroggle.logic.blog.BlogRight;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Calendar;
import java.util.Date;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class EditBlogPostService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "blogPostId",
            entityClass = BlogPost.class)
    @RemoteMethod
    public String execute(final EditBlogPostRequest request) {
        if (request.getBlogPostText() == null || request.getBlogPostText().trim().isEmpty()) {
            return "";
        }
        final User user = new UsersManager().getLoginedUser();
        final Integer loginedUserId = user != null ? user.getUserId() : null;
        final BlogPost blogPost = BlogRight.getBlogPostOnEdit(
                loginedUserId, request.getBlogPostId());

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                blogPost.setPostTitle(request.getPostTitle());
                if (request.isAsDraft()) {
                    blogPost.setDraftText(request.getBlogPostText());
                } else {
                    blogPost.setDraftText(null);
                    blogPost.setText(request.getBlogPostText());
                }

                final Date newCreationDate = DateUtil.getByMonthDayAndYear(request.getCreationDateString());
                if (newCreationDate != null) {
                    // Setting year, month and day to new creationDate.
                    final Calendar newCreationDateCalendar = Calendar.getInstance();
                    newCreationDateCalendar.setTime(newCreationDate);

                    // Copying hour, minute, second, millisecond from old creation date to new one.
                    final Calendar oldCreationDate = Calendar.getInstance();
                    oldCreationDate.setTime(blogPost.getCreationDate());
                    newCreationDateCalendar.set(Calendar.HOUR, oldCreationDate.get(Calendar.HOUR));
                    newCreationDateCalendar.set(Calendar.MINUTE, oldCreationDate.get(Calendar.MINUTE));
                    newCreationDateCalendar.set(Calendar.SECOND, oldCreationDate.get(Calendar.SECOND));
                    newCreationDateCalendar.set(Calendar.MILLISECOND, oldCreationDate.get(Calendar.MILLISECOND));

                    // Setting new creationDate to blogPost
                    blogPost.setCreationDate(newCreationDateCalendar.getTime());
                }
            }
        });
        return DateUtil.getDateForBlogAndBlogSummary(blogPost.getCreationDate());
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}