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

package com.shroggle.presentation.site.htmlAndCss;

import com.shroggle.entity.Page;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class GetPageVersionTemplateHtmlService {

    @SynchronizeByMethodParameter(
            entityClass = Page.class)
    @RemoteMethod
    public String execute(final int pageId) {
        final UserManager userManager = new UsersManager().getLogined();
        final PageManager pageManager = userManager.getRight().getSiteRight().getPageForEdit(pageId);
        return pageManager.getTemplateHtml();
    }

}