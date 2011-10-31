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
package com.shroggle.logic.gallery;

import com.shroggle.entity.DraftForm;
import com.shroggle.entity.DraftGallery;
import com.shroggle.entity.ItemType;
import com.shroggle.exception.GalleryNotFoundException;
import com.shroggle.logic.SiteItemsManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

/**
 * @author Artem Stasuk
 */
public class GalleriesManager {

    public GalleryManager getNew(final int siteId) {
        final UserManager userManager = new UsersManager().getLogined();
        userManager.getRight().getSiteRight().getSiteForEdit(siteId);

        final DraftGallery gallery = new DraftGallery();
        gallery.setId(-1);
        gallery.setName(SiteItemsManager.getNextDefaultName(ItemType.GALLERY, siteId));
        gallery.setSiteId(siteId);

        return new GalleryManager(gallery);
    }

    /**
     * In this method we don't check access right, because it's use in show pages and edit pages.
     * All need access check we doing when we change gallery in need methods.
     *
     * @param id - exists gallery id
     * @return - gallery
     */
    public GalleryManager get(final int id) {
        final DraftGallery gallery = ServiceLocator.getPersistance().getDraftItem(
                id);
        if (gallery == null) {
            throw new GalleryNotFoundException("Can't find gallery " + id);
        }
        return new GalleryManager(gallery);
    }

    /**
     * In this method we don't check access right, because it's use in show pages and edit pages.
     * All need access check we doing when we change gallery in need methods.
     *
     * @param gallery - exists gallery
     * @return - gallery
     */
    public GalleryManager get(final DraftGallery gallery) {
        if (gallery == null) {
            throw new GalleryNotFoundException("Can't find gallery");
        }
        return new GalleryManager(gallery);
    }

    /**
     * Getting all galleries that use current form and creating video
     * with new dimensions and quality for them if needed.
     *
     * @param formId - formId
     */
    public static void updateVideoFilesForGalleriesThatUseCurentForm(final Integer formId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final DraftForm form = persistance.getFormById(formId);
        if (form != null) {
            for (DraftGallery gallery : persistance.getGalleriesByFormId(form.getFormId())) {
                GalleryManager manager = new GalleryManager(gallery);
                manager.createVideoFLVByNewSize();
            }
        }
    }


}
