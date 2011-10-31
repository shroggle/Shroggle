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
package com.shroggle.presentation.site.requestContent;

import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageSettingsManager;
import com.shroggle.logic.site.page.PagesWithoutSystem;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class GetRequestContentService extends AbstractService {

    @SynchronizeByMethodParameter(
            entityClass = Site.class)
    @RemoteMethod
    public GetRequestContentResponse execute(final int siteId) {
        new UsersManager().getLogined().getUserId();

        final Site site = persistance.getSite(siteId);
        if (site == null) {
            throw new SiteNotFoundException("Can't find site " + siteId);
        }
        final SiteManager siteManager = new SiteManager(site);
        final List<RequestContentItem> items = new ArrayList<RequestContentItem>();
        items.addAll(getRightsAsItems(siteId));
        return new GetRequestContentResponse(siteManager.getPublicUrl(), items);
    }

    public List<RequestContentItem> getRightsAsItems(final int siteId) {
        final Site site = persistance.getSite(siteId);
        final List<RequestContentItem> items = new ArrayList<RequestContentItem>();
        for (final Page page : PagesWithoutSystem.get(site.getPages())) {
            final PageManager pageManager = new PageManager(page);
            if (pageManager.getWorkPageSettings() == null) {
                continue;
            }

            for (final Widget widget : new PageSettingsManager(pageManager.getWorkPageSettings()).getWidgets()) {
                if (widget.isWidgetItem()) {
                    final WidgetItem widgetItem = (WidgetItem) widget;
                    if (widgetItem.getDraftItem() == null) {
                        continue;
                    }

                    final DraftItem draftItem = widgetItem.getDraftItem();

                    if (draftItem.getItemType().isShareable()) {
                        items.add(new RequestContentItem(draftItem.getName(), draftItem.getItemType(), draftItem.getId()));
                    }
                }
            }
        }
        return items;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
