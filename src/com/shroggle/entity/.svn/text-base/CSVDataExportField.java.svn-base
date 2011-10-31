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

import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "customizeDataExportFields")// todo. rename
public class CSVDataExportField {

    @Id
    private int id;

    @RemoteProperty
    private int position;

    @RemoteProperty
    private int formItemId;

    @RemoteProperty
    @Column(length = 250)
    private String customizeHeader;

    @RemoteProperty
    private boolean include;

    @ManyToOne// todo. rename
    @JoinColumn(name = "customizeDataExportId", nullable = false)
    @ForeignKey(name = "customizeDataExportFieldsCustomizeDataExportsId")
    private CSVDataExport customizeDataExport;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getFormItemId() {
        return formItemId;
    }

    public void setFormItemId(int formItemId) {
        this.formItemId = formItemId;
    }

    public String getCustomizeHeader() {
        return customizeHeader;
    }

    public void setCustomizeHeader(String customizeHeader) {
        this.customizeHeader = customizeHeader;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    public CSVDataExport getCustomizeDataExport() {
        return customizeDataExport;
    }

    public void setCustomizeDataExport(CSVDataExport customizeDataExport) {
        this.customizeDataExport = customizeDataExport;
    }
}
