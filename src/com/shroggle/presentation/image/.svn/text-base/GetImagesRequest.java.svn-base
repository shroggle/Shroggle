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
package com.shroggle.presentation.image;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class GetImagesRequest {

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public Integer getImageItemId() {
        return imageItemId;
    }

    public void setImageItemId(Integer imageItemId) {
        this.imageItemId = imageItemId;
    }

    @RemoteProperty
    private int lineWidth;

    @RemoteProperty
    private int siteId;

    @RemoteProperty
    private Integer imageItemId;

    @RemoteProperty
    private List<String> keywords = new ArrayList<String>();

}
