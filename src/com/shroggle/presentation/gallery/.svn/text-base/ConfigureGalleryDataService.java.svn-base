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

package com.shroggle.presentation.gallery;

import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetGalleryDataManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigureGalleryDataService extends AbstractService implements WithWidgetTitle {

    @SynchronizeByMethodParameter(
            entityClass = Widget.class)
    @RemoteMethod
    public void execute(final int widgetGalleryDataId) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        final WidgetItem widgetGalleryData = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                widgetGalleryDataId);
        widgetGalleryDataManager = new WidgetGalleryDataManager(widgetGalleryData);

        galleryNames = "";
        siteAndPageVersionNames = new ArrayList<String>();

        final Set<Integer> galleryIds = new HashSet<Integer>();
        for (final DraftGallery gallery : persistance.getGalleriesByDataCrossWidgetIds(widgetGalleryData.getCrossWidgetId(),
                widgetGalleryData.getParentCrossWidgetId())) {
            galleryIds.add(gallery.getId());

            if (galleryNames.length() > 0) {
                galleryNames += ", ";
            }
            galleryNames += gallery.getName();
        }

        for (final Widget widget : persistance.getWidgetItemsByGalleriesId(galleryIds)) {
            final PageManager pageManager = new PageManager(widget.getPage());
            siteAndPageVersionNames.add("Site: " + pageManager.getSite().getTitle() + ", page: " + pageManager.getName());
        }

        getContext().getHttpServletRequest().setAttribute("galleryDataService", this);
    }

    public WidgetGalleryDataManager getWidgetGalleryData() {
        return widgetGalleryDataManager;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetGalleryDataManager.getTitle();
    }

    public String getGalleryNames() {
        return galleryNames;
    }

    public List<String> getSiteAndPageVersionNames() {
        return siteAndPageVersionNames;
    }

    private String galleryNames;
    private List<String> siteAndPageVersionNames;
    private WidgetGalleryDataManager widgetGalleryDataManager;
    private final Persistance persistance = ServiceLocator.getPersistance();

}