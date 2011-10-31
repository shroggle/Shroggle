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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import com.shroggle.logic.gallery.GalleryEdit;
import com.shroggle.entity.FormItemName;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class ConfigureGalleryResponse {

    public ConfigureGalleryResponse(final GalleryEdit gallery) {
        this.gallery = gallery;
        this.formItemsWithSize = new FormItemName[]{FormItemName.IMAGE_FILE_UPLOAD, FormItemName.VIDEO_FILE_UPLOAD};
        this.numberFormItems = FormItemName.getNumbers();
        this.textFormItems = FormItemName.getTexts();
    }

    public GalleryEdit getGallery() {
        return gallery;
    }

    public FormItemName[] getFormItemsWithSize() {
        return formItemsWithSize;
    }

    public FormItemName[] getTextFormItems() {
        return textFormItems;
    }

    public FormItemName[] getNumberFormItems() {
        return numberFormItems;
    }

    @RemoteProperty
    private final GalleryEdit gallery;

    @RemoteProperty
    private final FormItemName[] formItemsWithSize;

    @RemoteProperty
    private final FormItemName[] numberFormItems;

    @RemoteProperty
    private final FormItemName[] textFormItems;

}
