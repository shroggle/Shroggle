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
package com.shroggle.presentation.manageVotes;

import com.shroggle.entity.*;
import com.shroggle.exception.ManageVotesNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.manageVotes.ManageVotesGallerySettingsAvailableCreator;
import com.shroggle.logic.manageVotes.ManageVotesManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ConfigureManageVotesService extends AbstractService implements WithWidgetTitle {

    @RemoteMethod
    public void execute(final Integer widgetId, final Integer manageVotesId)
            throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && manageVotesId == null) {
            throw new ManageVotesNotFoundException("Both widgetId and manageVotesId cannot be null. " +
                    "This service is only for configuring existing manage votes.");
        }

        if (widgetId == null) {
            // edit forum from dashboard or manage items.
            selectedManageVotes = persistance.getDraftItem(manageVotesId);

            if (selectedManageVotes == null) {
                throw new ManageVotesNotFoundException("Cannot find manage votes by Id=" + manageVotesId);
            }

            site = persistance.getSite(selectedManageVotes.getSiteId());
        } else {
            widget = (WidgetItem) userRightManager.getSiteRight().getWidgetForEditInPresentationalService(
                    widgetId);
            site = widget.getSite();

            widgetTitle = new WidgetTitleGetter(widget);

            if (widget.getDraftItem() != null) {
                selectedManageVotes = (DraftManageVotes) widget.getDraftItem();
            } else {
                throw new ManageVotesNotFoundException("Seems like widget with Id= " + widgetId + " got no item." +
                        "This service is only for configuring existing manage votes.");
            }
        }

        //Getting existing manage votes gallery settings
        final List<? extends ManageVotesSettings> manageVotesGallerySettingsList =
                selectedManageVotes != null ? new ManageVotesManager(selectedManageVotes, SiteShowOption.getDraftOption()).getManageVotesGallerySettingsList() : null;

        //Getting available manage votes gallery settings
        final boolean showFromCurrentSite = (selectedManageVotes != null && selectedManageVotes.isShowVotingModulesFromCurrentSite()) || selectedManageVotes == null;
        availableGalleriesWithWidgets = manageVotesGallerySettingsAvailableCreator.getAvailable
                (manageVotesGallerySettingsList, showFromCurrentSite, userManager.getUserId(), site.getSiteId(), SiteShowOption.getDraftOption());


        getContext().getHttpServletRequest().setAttribute("manageVotesService", this);
    }

    @RemoteMethod
    public String getAvailableVotingModulesList(final boolean byCurrentSite, final int siteId, final int selectedManageVotesId)
            throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();

        final DraftManageVotes draftManageVotes = persistance.getDraftItem(selectedManageVotesId);
        List<DraftManageVotesSettings> manageVotesGallerySettingsList = draftManageVotes.getManageVotesGallerySettingsList();

        availableGalleriesWithWidgets = manageVotesGallerySettingsAvailableCreator.getAvailable
                (manageVotesGallerySettingsList, byCurrentSite, userManager.getUserId(), siteId, SiteShowOption.getDraftOption());

        getContext().getHttpServletRequest().setAttribute("availableGalleriesWithWidgets", availableGalleriesWithWidgets);
        return getContext().forwardToString("/site/manageVotesGallerySettingsList.jsp");
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public WidgetItem getWidget() {
        return widget;
    }

    public DraftManageVotes getSelectedManageVotes() {
        return selectedManageVotes;
    }

    public List<GalleryWithWidgets> getAvailableGalleriesWithWidgets() {
        return availableGalleriesWithWidgets;
    }

    public Site getSite() {
        return site;
    }

    private WidgetItem widget;
    private WidgetTitle widgetTitle;
    private List<GalleryWithWidgets> availableGalleriesWithWidgets;
    private ManageVotesGallerySettingsAvailableCreator manageVotesGallerySettingsAvailableCreator = new ManageVotesGallerySettingsAvailableCreator();
    private DraftManageVotes selectedManageVotes;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private Site site;
}
