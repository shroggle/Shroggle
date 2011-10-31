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
package com.shroggle.logic.slideShow;

import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.provider.ResourceGetterType;

/**
 * @author dmitry.solomadin
 */
public class SlideShowImageManager {

    public SlideShowImageManager(final SlideShowImage slideShowImage) {
        this.slideShowImage = slideShowImage;
    }

    public String getOriginalImageUrl() {
        if (slideShowImage.getImageType() == SlideShowImageType.FORM_IMAGE) {
            final FormFile formFile = persistance.getFormFileById(slideShowImage.getImageId());

            return ServiceLocator.getResourceGetter().get(ResourceGetterType.SLIDE_SHOW_ORIGINAL_FORM_IMAGE_PROVIDER,
                    formFile.getResourceId(), 0, 0, 0, false);
        } else {
            final Image image = persistance.getImageById(slideShowImage.getImageId());

            return ServiceLocator.getResourceGetter().get(ResourceGetterType.SLIDE_SHOW_ORIGINAL_IMAGE_PROVIDER,
                    image.getResourceId(), 0, 0, 0, false);
        }
    }

    public String getResizedImageUrl() {
        if (slideShowImage.getImageType() == SlideShowImageType.FORM_IMAGE) {
            final FormFile formFile = persistance.getFormFileById(slideShowImage.getImageId());

            return ServiceLocator.getResourceGetter().get(ResourceGetterType.SLIDE_SHOW_FORM_IMAGE_PROVIDER,
                    formFile.getResourceId(), 0, 0, 0, false);
        } else {
            final Image image = persistance.getImageById(slideShowImage.getImageId());

            return ServiceLocator.getResourceGetter().get(ResourceGetterType.SLIDE_SHOW_IMAGE_PROVIDER,
                    image.getResourceId(), 0, 0, 0, false);
        }
    }

    public String getTitle() {
        final String imageTitle;
        if (slideShowImage.getImageType() == SlideShowImageType.FORM_IMAGE) {
            imageTitle = persistance.getFormFileById(slideShowImage.getImageId()).getName();
        } else {
            imageTitle = "";
        }

        return imageTitle;
    }

    private SlideShowImage slideShowImage;
    private final Persistance persistance = ServiceLocator.getPersistance();

}
