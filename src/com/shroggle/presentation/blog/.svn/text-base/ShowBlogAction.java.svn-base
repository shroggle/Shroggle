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

import com.shroggle.entity.*;
import com.shroggle.exception.SiteItemNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.HashMap;

@UrlBinding("/blog/showBlog.action")
public class ShowBlogAction extends Action {

    @SynchronizeByClassProperty(
            entityClass = DraftBlog.class,
            entityIdFieldPath = "blogId")
    @DefaultHandler
    public Resolution execute() {
        try {
            final UserManager userManager = new UsersManager().getLogined();

            final DraftBlog blog;
            if (blogId != null) {
                blog = userManager.getSiteItemForViewById(blogId, ItemType.BLOG);
            } else if (widgetId != null) {
                final Item draftItem = new WidgetManager(widgetId).getItemManager().getDraftItem();
                if (draftItem != null && draftItem.getItemType() == ItemType.BLOG) {
                    blog = (DraftBlog) draftItem;
                } else {
                    return resolutionCreator.loginInUser(this);
                }
            } else {
                return resolutionCreator.loginInUser(this);
            }

            final WidgetItem widgetBlog = new WidgetItem();
            widgetBlog.setDraftItem(blog);

            return resolutionCreator.showWidgetPreview(
                    widgetBlog, getContext().getServletContext(), userManager.getUserId(),
                    new HashMap<ItemType, String>());
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        } catch (final SiteItemNotFoundException exception) {
            return resolutionCreator.loginInUser(this);
        }
    }

    public void setBlogId(final Integer blogId) {
        this.blogId = blogId;
    }

    public void setWidgetId(final Integer widgetId) {
        this.widgetId = widgetId;
    }

    private Integer blogId;
    private Integer widgetId;
    private ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}