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
package com.shroggle.presentation.page;

import com.shroggle.entity.Page;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class PageUrlGetterService {

    @RemoteMethod
    public String getWorkUrl(final Integer pageId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final Page page = persistance.getPage(pageId);
        final SiteShowOption siteShowOption = null;
        return page != null ? new PageManager(page, siteShowOption).getWorkPublicUrl() : null;
    }


}
