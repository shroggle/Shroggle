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
package com.shroggle.entity;

import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Balakirev Anatoliy
 */
@Embeddable
@DataTransferObject
public class GoogleBaseDataExport {

    private Integer galleryId;

    private Integer formItemIdForTitle;

    private Integer formItemIdForDescription;

    @Column(length = 255)
    private String googleBaseAccountUsername;

    @Column(length = 255)
    private String googleBaseAccountPassword;   


    public String getGoogleBaseAccountUsername() {
        return googleBaseAccountUsername;
    }

    public void setGoogleBaseAccountUsername(String googleBaseAccountUsername) {
        this.googleBaseAccountUsername = googleBaseAccountUsername;
    }

    public String getGoogleBaseAccountPassword() {
        return googleBaseAccountPassword;
    }

    public void setGoogleBaseAccountPassword(String googleBaseAccountPassword) {
        this.googleBaseAccountPassword = googleBaseAccountPassword;
    }

    public Integer getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Integer galleryId) {
        this.galleryId = galleryId;
    }

    public Integer getFormItemIdForTitle() {
        return formItemIdForTitle;
    }

    public void setFormItemIdForTitle(Integer formItemIdForTitle) {
        this.formItemIdForTitle = formItemIdForTitle;
    }

    public Integer getFormItemIdForDescription() {
        return formItemIdForDescription;
    }

    public void setFormItemIdForDescription(Integer formItemIdForDescription) {
        this.formItemIdForDescription = formItemIdForDescription;
    }
}
