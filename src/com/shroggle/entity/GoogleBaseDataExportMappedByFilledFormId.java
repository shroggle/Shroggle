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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "googleBaseDataExportMappedByFilledFormId")
public class GoogleBaseDataExportMappedByFilledFormId {

    @Id
    private int id;

    private int filledFormId;

    @Column(nullable = false, length = 255)
    private String googleBaseItemId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(int filledFormId) {
        this.filledFormId = filledFormId;
    }

    public String getGoogleBaseItemId() {
        return googleBaseItemId;
    }

    public void setGoogleBaseItemId(String googleBaseItemId) {
        this.googleBaseItemId = googleBaseItemId;
    }
}
