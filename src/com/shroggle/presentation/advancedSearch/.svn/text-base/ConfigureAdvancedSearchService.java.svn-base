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

package com.shroggle.presentation.advancedSearch;

import com.shroggle.entity.DraftAdvancedSearch;
import com.shroggle.entity.ItemType;
import com.shroggle.entity.Site;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.AdvancedSearchNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameters;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class ConfigureAdvancedSearchService extends AbstractService implements WithWidgetTitle {

    @SynchronizeByMethodParameters({
            @SynchronizeByMethodParameter(
                    entityClass = DraftAdvancedSearch.class)})
    @RemoteMethod
    public void show(final Integer widgetId, final Integer advancedSearchId) {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && advancedSearchId == null) {
            throw new AdvancedSearchNotFoundException("Both widgetId and advancedSearchId cannot be null. " +
                    "This service is only for configuring existing advanced searches.");
        }

        if (widgetId == null) {
            // edit forum from dashboard or manage items.
            selectedAdvancedSearch = persistance.getDraftItem(advancedSearchId);

            if (selectedAdvancedSearch == null) {
                throw new AdvancedSearchNotFoundException("Cannot find advanced search by Id=" + advancedSearchId);
            }

            site = persistance.getSite(selectedAdvancedSearch.getSiteId());
        } else {
            widget = (WidgetItem) userRightManager.getSiteRight().getWidgetForEditInPresentationalService(
                    widgetId);
            site = widget.getSite();

            widgetTitle = new WidgetTitleGetter(widget);

            if (widget.getDraftItem() != null) {
                selectedAdvancedSearch = (DraftAdvancedSearch) widget.getDraftItem();
            } else {
                throw new AdvancedSearchNotFoundException("Seems like widget with Id= " + widgetId + " got no item." +
                        "This service is only for configuring existing advanced searches.");
            }
        }

        availableGalleries = ItemManager.siteItemsToManagers(
                userRightManager.getSiteItemsForView(ItemType.GALLERY));
        availableForms = ItemManager.siteItemsToManagers(
                userRightManager.getSiteItemsForView(ItemType.ALL_FORMS));

        getContext().getHttpServletRequest().setAttribute("advancedSearchService", this);
    }

    public WidgetItem getWidget() {
        return widget;
    }

    public DraftAdvancedSearch getSelectedAdvancedSearch() {
        return selectedAdvancedSearch;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public List<ItemManager> getAvailableForms() {
        return availableForms;
    }

    public List<ItemManager> getAvailableGalleries() {
        return availableGalleries;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    private Persistance persistance = ServiceLocator.getPersistance();

    private WidgetItem widget;
    private WidgetTitleGetter widgetTitle;
    private DraftAdvancedSearch selectedAdvancedSearch;
    private List<ItemManager> availableGalleries = new ArrayList<ItemManager>();
    private List<ItemManager> availableForms = new ArrayList<ItemManager>();
    private Site site;

}
