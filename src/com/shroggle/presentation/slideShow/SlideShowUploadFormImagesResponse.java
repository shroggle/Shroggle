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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class SlideShowUploadFormImagesResponse {

    @RemoteProperty
    private int numberOfImagesUploaded;

    @RemoteProperty
    private String manageImagesDivHtml;

    public int getNumberOfImagesUploaded() {
        return numberOfImagesUploaded;
    }

    public void setNumberOfImagesUploaded(int numberOfImagesUploaded) {
        this.numberOfImagesUploaded = numberOfImagesUploaded;
    }

    public String getManageImagesDivHtml() {
        return manageImagesDivHtml;
    }

    public void setManageImagesDivHtml(String manageImagesDivHtml) {
        this.manageImagesDivHtml = manageImagesDivHtml;
    }

}
