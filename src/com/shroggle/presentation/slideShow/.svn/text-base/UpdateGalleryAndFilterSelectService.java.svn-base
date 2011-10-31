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
package com.shroggle.presentation.slideShow;

import com.shroggle.entity.*;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.*;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class UpdateGalleryAndFilterSelectService extends AbstractService {

    @RemoteMethod
    public UpdateGalleryAndFilterSelectResponse execute(final int formId, final int siteId) {
        final UserManager userManager = new UsersManager().getLogined();

        final DraftForm form = persistance.getDraftItem(formId);

        if (form == null) {
            throw new FormNotFoundException("Cannot find form by Id=" + formId);
        }

        final SiteManager siteManager = userManager.getRight().getSiteRight().getSiteForView(siteId);

        final List<Integer> linkBackToGalleryIds = new ArrayList<Integer>();

        //Getting all galleries that are connected to this form
        for (Gallery gallery : persistance.getGalleriesByFormId(formId)) {
            linkBackToGalleryIds.add(gallery.getId());
        }

        //Getting all gallery widget for current site.
        final List<WidgetItem> linkBackToGalleryAllWidgets = new ArrayList<WidgetItem>();
        for (Widget widget : persistance.getWidgetsBySitesId(Arrays.asList(siteManager.getId()), SiteShowOption.getDraftOption())) {
            if (widget.isWidgetItem() && (widget.getItemType() == ItemType.GALLERY ||
                    widget.getItemType() == ItemType.GALLERY_DATA)) {
                linkBackToGalleryAllWidgets.add((WidgetItem) widget);
            }
        }

        //Getting only those gallery widgets whose galleries are connected to this form.
        final Map<Integer, String> linkBackToGalleryWidgetIdLocationPair = new HashMap<Integer, String>();
        for (WidgetItem linkBackToGalleryWidget : linkBackToGalleryAllWidgets) {
            if (linkBackToGalleryIds.contains(linkBackToGalleryWidget.getDraftItem().getId())) {
                linkBackToGalleryWidgetIdLocationPair.put(linkBackToGalleryWidget.getWidgetId(),
                        new PageManager(linkBackToGalleryWidget.getPage()).getName() + " / " +
                                linkBackToGalleryWidget.getDraftItem().getName());
            }
        }

        final UpdateGalleryAndFilterSelectResponse response = new UpdateGalleryAndFilterSelectResponse();
        response.setFilters(form.getFilters());
        response.setLinkBackToGalleryWidgetIdLocationPair(linkBackToGalleryWidgetIdLocationPair);
        response.setImageFormItems(FormManager.getFormItemListByFormItemName(FormItemName.IMAGE_FILE_UPLOAD,
                form));

        return response;
    }

    private Persistance persistance = ServiceLocator.getPersistance();

}
