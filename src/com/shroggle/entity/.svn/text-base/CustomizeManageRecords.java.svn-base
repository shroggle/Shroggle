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

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "customizeManageRecords")
public class CustomizeManageRecords {

    @Id
    private int id;

    @Column(updatable = false)
    private int userId;

    @Column(updatable = false)
    private int formId;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "customizeManageRecords")
    private List<CustomizeManageRecordsField> fields = new ArrayList<CustomizeManageRecordsField>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public List<CustomizeManageRecordsField> getFields() {
        return fields;
    }

    public void setFields(List<CustomizeManageRecordsField> fields) {
        this.fields = fields;
    }

    public void addField(CustomizeManageRecordsField field) {
        field.setCustomizeManageRecords(this);
        this.fields.add(field);
    }

    public void removeField(CustomizeManageRecordsField field) {
        this.fields.remove(field);
    }
}
