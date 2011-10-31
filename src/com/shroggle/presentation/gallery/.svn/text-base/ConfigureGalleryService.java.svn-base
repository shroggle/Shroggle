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
import com.shroggle.logic.form.FormLogic;
import com.shroggle.logic.form.filter.FormFiltersManager;
import com.shroggle.logic.gallery.GalleriesManager;
import com.shroggle.logic.gallery.GalleryManager;
import com.shroggle.logic.manageVotes.ManageVotesForVotingSettings;
import com.shroggle.logic.manageVotes.ManageVotesForVotingSettingsCreator;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetGalleryLogic;
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
import java.util.Arrays;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigureGalleryService extends AbstractService implements WithWidgetTitle {

    @SynchronizeByMethodParameters({
            @SynchronizeByMethodParameter(
                    entityClass = Widget.class),
            @SynchronizeByMethodParameter(
                    entityClass = DraftGallery.class,
                    entityIdParameterIndex = 1)})
    @RemoteMethod
    public ConfigureGalleryResponse execute(final Integer widgetId, final Integer galleryId) throws Exception {
        user = new UsersManager().getLogined();
        formFiltersManager = new FormFiltersManager(user);
        this.widgetId = widgetId;
        sitesForData = persistance.getSites(user.getUserId(), SiteAccessLevel.getUserAccessLevels());

        if (widgetId == null) {
            gallery = new GalleriesManager().get(galleryId);

            siteManager = user.getRight().getSiteRight().getSiteForView(gallery.getEntity().getSiteId());
        } else {
            // from edit site pages
            final WidgetItem widget = (WidgetItem) user.getRight().getSiteRight().getWidgetForView(widgetId);
            widgetGallery = new WidgetGalleryLogic(widget);

            widgetTitle = new WidgetTitleGetter(widgetGallery.getWidget(), "New Gallery");
            siteManager = user.getRight().getSiteRight().getSiteForView(widget.getSiteId());

            gallery = new GalleriesManager().get((DraftGallery) widget.getDraftItem());
        }
        form = gallery.getFormLogic();

        // If data widget was deleted then we should remove dataCrossWidgetId from gallery.
        removeDataCrossWidgetIdIfWidgetWasDeleted();

        widgetGalleryDatas = new ArrayList<WidgetItem>();
        final List<Integer> sitesId = new ArrayList<Integer>();
        for (Site site : sitesForData) {
            sitesId.add(site.getSiteId());
        }
        final List<WidgetItem> siteWidgetGalleryDatas =
                persistance.getGalleryDataWidgetsBySitesId(sitesId, SiteShowOption.getDraftOption());
        widgetGalleryDatas.addAll(siteWidgetGalleryDatas);

        shoppingCartWidgets = new ArrayList<WidgetItem>();
        for (Widget widget : persistance.getWidgetsBySitesId(Arrays.asList(siteManager.getId()), SiteShowOption.getDraftOption())) {
            if (widget.isWidgetItem() && widget.getItemType() == ItemType.SHOPPING_CART) {
                shoppingCartWidgets.add((WidgetItem) widget);
            }
        }

        purchaseHistoryWidgets = new ArrayList<WidgetItem>();
        for (Widget widget : persistance.getWidgetsBySitesId(Arrays.asList(siteManager.getId()), SiteShowOption.getDraftOption())) {
            if (widget.isWidgetItem() && widget.getItemType() == ItemType.PURCHASE_HISTORY) {
                purchaseHistoryWidgets.add((WidgetItem) widget);
            }
        }

        registrationFormsForVoters.addAll(persistance.getDraftItemsByUserId(user.getUser().getUserId(), ItemType.REGISTRATION));
        getContext().getHttpServletRequest().setAttribute("includeLinkToManageYourVotes", gallery.getVoteSettings().isIncludeLinkToManageYourVotes());
        getContext().getHttpServletRequest().setAttribute("showAllAvailablePages", gallery.getVoteSettings().isShowAllAvailablePages());
        getContext().getHttpServletRequest().setAttribute("crossWidgetId", gallery.getVoteSettings().getManageYourVotesCrossWidgetId());
        getContext().getHttpServletRequest().setAttribute("manageVotesLinks", createManageVotes(siteManager.getId(), user.getUser().getUserId(), gallery.getVoteSettings().isShowAllAvailablePages()));
        getContext().getHttpServletRequest().setAttribute("galleryService", this);

        return new ConfigureGalleryResponse(gallery.getEdit());
    }

    @RemoteMethod
    public String getManageVotesForVotingSettings(final Integer crossWidgetId, final int siteId, final boolean showAllAvailablePages) throws Exception {
        user = new UsersManager().getLogined();
        getContext().getHttpServletRequest().setAttribute("includeLinkToManageYourVotes", true);
        getContext().getHttpServletRequest().setAttribute("showAllAvailablePages", showAllAvailablePages);
        getContext().getHttpServletRequest().setAttribute("crossWidgetId", crossWidgetId);
        getContext().getHttpServletRequest().setAttribute("manageVotesLinks", createManageVotes(siteId, user.getUser().getUserId(), showAllAvailablePages));
        getContext().getHttpServletRequest().setAttribute("galleryService", this);

        return getContext().forwardToString("/gallery/manageVotesLinks.jsp");
    }

    private List<ManageVotesForVotingSettings> createManageVotes(final int siteId, final int userId, final boolean showAllAvailablePages) {
        if (showAllAvailablePages) {
            return ManageVotesForVotingSettingsCreator.executeForAllAvailableSites(userId, SiteShowOption.getDraftOption());
        } else {
            return ManageVotesForVotingSettingsCreator.executeForCurrentSite(siteId, SiteShowOption.getDraftOption());
        }
    }

    private void removeDataCrossWidgetIdIfWidgetWasDeleted() {
        final Widget dataWidget = persistance.getWidgetByCrossWidgetsId(gallery.getEntity().getDataCrossWidgetId(),
                SiteShowOption.getDraftOption());
        if (dataWidget == null) {
            ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                @Override
                public void run() {
                    gallery.getEntity().setDataCrossWidgetId(null);
                }
            });
        }
    }

    public WidgetGalleryLogic getWidgetGallery() {
        return widgetGallery;
    }

    public GalleryManager getGallery() {
        return gallery;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public SiteManager getSiteManager() {
        return siteManager;
    }

    public FormLogic getForm() {
        return form;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public FormFiltersManager getFormFiltersLogic() {
        return formFiltersManager;
    }

    public UserManager getUser() {
        return user;
    }

    public List<DraftItem> getRegistrationFormsForVoters() {
        return registrationFormsForVoters;
    }

    public List<WidgetItem> getWidgetGalleryDatas() {
        return widgetGalleryDatas;
    }

    public List<Site> getSitesForData() {
        return sitesForData;
    }

    public List<WidgetItem> getShoppingCartWidgets() {
        return shoppingCartWidgets;
    }

    public List<WidgetItem> getPurchaseHistoryWidgets() {
        return purchaseHistoryWidgets;
    }

    public int getDefaultFormId() {
        return siteManager != null ? siteManager.getDefaultFormId() : -1;
    }

    private UserManager user;
    private WidgetTitle widgetTitle;
    private GalleryManager gallery;
    private List<WidgetItem> shoppingCartWidgets;
    private List<WidgetItem> purchaseHistoryWidgets;
    private List<Site> sitesForData;
    private WidgetGalleryLogic widgetGallery;
    private FormFiltersManager formFiltersManager;
    private FormLogic form;
    private SiteManager siteManager;
    private Integer widgetId;
    private List<WidgetItem> widgetGalleryDatas;
    private List<DraftItem> registrationFormsForVoters = new ArrayList<DraftItem>();
    private final Persistance persistance = ServiceLocator.getPersistance();


}