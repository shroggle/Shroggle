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

package com.shroggle.presentation.site.page;

import com.shroggle.entity.*;
import com.shroggle.logic.site.page.*;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Stasuk Artem, dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class SavePageService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            entityClass = Site.class,
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "siteId")
    @RemoteMethod
    public SavePageResponse savePageNameTab(final SavePageRequest request) {
        new UsersManager().getLogined();

        final com.shroggle.logic.site.page.SavePageResponse pageCreatorResponse = new PageCreator().savePageNameTab(request, false);

        final SavePageResponse response = new SavePageResponse();
        response.setCreatedWidgetId(pageCreatorResponse.getCreateWidgetId());
        response.setTreeHtml(new PageTreeTextCreator().execute(request.getSiteId(), SiteShowOption.getDraftOption()));
        response.setPageSelectHtml(new PageSelectTextCreator().execute(request.getSiteId(), SiteShowOption.getDraftOption()));

        return response;
    }

    @SynchronizeByMethodParameterProperty(
            entityClass = Site.class,
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "siteId")
    @RemoteMethod
    public SavePageResponse savePageSeoMetaTagsTab(final SavePageRequest request) {
        new UsersManager().getLogined();

        new PageCreator().saveSeoMetaTagsTab(request);

        final SavePageResponse savePageResponse = new SavePageResponse();
        savePageResponse.setTreeHtml(new PageTreeTextCreator().execute(request.getSiteId(), SiteShowOption.getDraftOption()));
        savePageResponse.setPageSelectHtml(new PageSelectTextCreator().execute(request.getSiteId(), SiteShowOption.getDraftOption()));
        return savePageResponse;
    }

    @SynchronizeByMethodParameterProperty(
            entityClass = Site.class,
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "siteId")
    @RemoteMethod
    public com.shroggle.presentation.site.page.SavePageResponse savePageSeoHtmlTab(final SavePageRequest request) {
        new UsersManager().getLogined();

        new PageCreator().saveSeoHtmlTab(request);

        final SavePageResponse savePageResponse = new SavePageResponse();
        savePageResponse.setTreeHtml(new PageTreeTextCreator().execute(request.getSiteId(), SiteShowOption.getDraftOption()));
        savePageResponse.setPageSelectHtml(new PageSelectTextCreator().execute(request.getSiteId(), SiteShowOption.getDraftOption()));
        return savePageResponse;
    }

}