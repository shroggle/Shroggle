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
package com.shroggle.presentation.site.accessibilityForRender;

import com.shroggle.entity.*;
import com.shroggle.logic.manageRegistrants.ManageRegistrantsProvider;
import com.shroggle.logic.manageRegistrants.ManageRegistrantsSortType;
import com.shroggle.logic.paginator.Paginator;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageSelectTextCreator;
import com.shroggle.logic.site.page.PageTreeTextCreator;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.manageRegistrants.ShowManageRegistrantsRequest;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.copier.CopierUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;


/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class CreateAccessibilitySettingsService extends ServiceWithExecutePage {

    @RemoteMethod
    public CreateAccessibilitySettingsResponse execute(final CreateAccessibilitySettingsRequest request) throws Exception {
        new UsersManager().getLogined();

        final CreateAccessibilitySettingsResponse response = new CreateAccessibilitySettingsResponse();
        final AccessibleForRender accessibleForRender = persistance.getAccessibleElement(request.getElementId(), request.getElementType());
        persistanceTransaction.execute(new Runnable() {
            @Override
            public void run() {
                if (accessibleForRender instanceof WidgetManager) {
                    final WidgetManager widgetManager = (WidgetManager) accessibleForRender;
                    if (request.isSaveAccessibilityInCurrentPlace()) {
                        widgetManager.setAccessibleSettingsInCurrentPlace(request.getAccessibleSettings());
                    } else {
                        widgetManager.setAccessibleSettingsInAllPlaces(request.getAccessibleSettings());
                    }
                    new PageManager(widgetManager.getWidget().getPage()).setChanged(true);
                } else if (accessibleForRender instanceof ItemManager) {
                    ((ItemManager) accessibleForRender).updateAccessibleSettings(request.getAccessibleSettings());
                } else {
                    final AccessibleSettings settings = accessibleForRender.getAccessibleSettings();
                    CopierUtil.copyProperties(request.getAccessibleSettings(), settings, "AccessibleSettingsId");
                }
                if (accessibleForRender instanceof PageManager) {
                    final PageManager pageManager = (PageManager) accessibleForRender;

                    pageManager.setChanged(true);
                    pageManager.setSaved(true);
                }
            }
        });

        if (request.getElementType() == AccessibleElementType.WIDGET) {
            response.setFunctionalWidgetInfo(new GetFunctionalWidgetsService().createFunctionalWidgetInfo(((WidgetManager) accessibleForRender).getWidget(), "widget", true));
        }

        if (request.isShowManageRegistrants()) {
            response.setManageRegistrantsHtml(showManageRegistrants(request.getSiteId()));
        }

        if (request.getElementType() == AccessibleElementType.PAGE) {
            final PageManager pageManager = (PageManager) accessibleForRender;
            response.setTreeHtml(new PageTreeTextCreator().execute(pageManager.getSiteId(), SiteShowOption.getDraftOption()));
            response.setPageSelectHtml(new PageSelectTextCreator().execute(pageManager.getSiteId(), SiteShowOption.getDraftOption()));
        }

        return response;
    }

    private String showManageRegistrants(final Integer siteId) throws IOException, ServletException {
        final ShowManageRegistrantsRequest request = new ShowManageRegistrantsRequest(RegistrantFilterType.SHOW_ALL, "",
                siteId, ManageRegistrantsSortType.FIRST_NAME, false);

        final SiteManager siteManager = new UsersManager().getLogined().getRight().getSiteRight().getSiteForEdit(request.getSiteId());

        final Paginator paginator = new ManageRegistrantsProvider().execute(request, Paginator.getFirstPageNumber());

        getContext().getHttpServletRequest().setAttribute("paginator", paginator);
        getContext().getHttpServletRequest().setAttribute("manageRegistrantsSiteId", request.getSiteId());
        getContext().getHttpServletRequest().setAttribute("manageRegistrantsSiteName", siteManager.getName());
        getContext().getHttpServletRequest().setAttribute("manageRegistrantsSortType", ManageRegistrantsSortType.FIRST_NAME);
        getContext().getHttpServletRequest().setAttribute("manageRegistrantsDesc", false);
        getContext().getHttpServletRequest().setAttribute("availableGroups", siteManager.getSite().getOwnGroups());

        return executePage("/account/manageRegistrants/manageRegistrantsForService.jsp");
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
