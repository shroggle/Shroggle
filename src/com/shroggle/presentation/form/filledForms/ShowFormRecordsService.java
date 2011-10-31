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
package com.shroggle.presentation.form.filledForms;

import com.shroggle.entity.*;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author dmitry.solomadin, Artem Stasuk, Balakirev Anatoliy
 */
@RemoteProxy
public class ShowFormRecordsService extends ServiceWithExecutePage implements LoginedUserInfo {

    @RemoteMethod
    public ShowFilledFormsResponse execute(
            final ShowFilledFormsRequest request)
            throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();
        loggedUser = userManager.getUser();

        final Site selectedSite;
        if (request.getWidgetId() != null) {
            final Widget widget = persistance.getWidget(request.getWidgetId());
            if (widget == null) {
                throw new WidgetNotFoundException("Cannot find widget by Id=" + request.getWidgetId());
            }

            if (widget.isWidgetItem()) {
                final WidgetItem widgetItem = (WidgetItem) widget;
                if (widgetItem.getDraftItem() == null) {
                    return createResponse(false);
                }

                final DraftItem draftItem = widgetItem.getDraftItem();
                if (draftItem != null) {
                    itemType = draftItem.getItemType();
                    if (draftItem.getItemType() == ItemType.GALLERY) {
                        gallery = (DraftGallery) draftItem;
                        form = persistance.getFormById(gallery.getFormId1());
                        showFromGallery = true;
                    } else if (draftItem instanceof DraftForm) {
                        form = (DraftForm) draftItem;
                    } else if (draftItem instanceof DraftAdvancedSearch) {
                        final DraftAdvancedSearch advancedSearch = (DraftAdvancedSearch) draftItem;
                        final Gallery tempGallery = persistance.getGalleryById(advancedSearch.getGalleryId());

                        if (tempGallery != null) {
                            form = persistance.getFormById(tempGallery.getFormId1());
                        }
                    }
                }
            }

            selectedSite = widget.getSite();
        } else if (request.getFormId() != null) {
            form = persistance.getFormById(request.getFormId());
            selectedSite = persistance.getSite(form.getSiteId());
        } else {
            showFromGallery = true;
            gallery = persistance.getGalleryById(request.getGalleryId());
            selectedSite = persistance.getSite(gallery.getSiteId());
            if (persistance.getFormFilterById(gallery.getFormFilterId()) != null) {
                form = persistance.getFormFilterById(gallery.getFormFilterId()).getDraftForm();
            } else {
                form = persistance.getFormById(gallery.getFormId1());
            }
        }

        // If we were not able to find a form then either there was some exception witch is awful or item, such as
        // Advanced search is not configured properly, don't worry we will show a message about this to user on page.
        formFilters = form != null ? form.getDraftFilters() : Collections.<DraftFormFilter>emptyList();
        return createResponse(form != null && userManager.getRight().toSiteItem(form, selectedSite) == SiteOnItemRightType.READ);
    }

    private ShowFilledFormsResponse createResponse(final boolean readOnly)
            throws IOException, ServletException {
        if (form != null) {
            manageFormRecordsRequest = new ManageFormRecordsTableRequestBuilder(form).build();
        }
        final ShowFilledFormsResponse response = new ShowFilledFormsResponse();
        response.setConfigured(form != null);
        response.setHtml(executePage("/site/form/manageFormRecords.jsp"));

        response.setReadOnly(readOnly);
        return response;
    }


    public User getLoginUser() {
        return loggedUser;
    }

    public DraftForm getForm() {
        return form;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public boolean isShowFromGallery() {
        return showFromGallery;
    }

    public DraftGallery getGallery() {
        return gallery;
    }

    public ManageFormRecordsTableRequest getManageFormRecordsRequest() {
        return manageFormRecordsRequest;
    }

    public List<DraftFormFilter> getFormFilters() {
        return formFilters;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private ManageFormRecordsTableRequest manageFormRecordsRequest;

    private User loggedUser;
    private DraftForm form;
    private ItemType itemType;
    private List<DraftFormFilter> formFilters;
    private boolean showFromGallery;
    private DraftGallery gallery;
}
