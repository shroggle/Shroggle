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

import com.shroggle.logic.gallery.GalleryEdit;
import com.shroggle.logic.gallery.GalleriesManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class GetGalleryService extends AbstractService {

    /**
     * It's most simple service in our application.
     *
     * @param galleryId - gallery id
     * @return - data for gallery
     */
    @RemoteMethod
    public GalleryEdit execute(final int galleryId) {
        return new GalleriesManager().get(galleryId).getEdit();
    }

}
