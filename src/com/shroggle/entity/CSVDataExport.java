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
import java.util.Collections;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "customizeDataExports")
// todo. rename db table
public class CSVDataExport {

    @Id
    private int id;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "customizeDataExport")// todo. rename
    private List<CSVDataExportField> fields = new ArrayList<CSVDataExportField>();

    public List<CSVDataExportField> getFields() {
        return Collections.unmodifiableList(fields);
    }

    public void setFields(List<CSVDataExportField> fields) {
        this.fields = fields;
    }

    public void addField(CSVDataExportField field) {
        field.setCustomizeDataExport(this);
        this.fields.add(field);
    }

    public void removeField(CSVDataExportField field) {
        this.fields.remove(field);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
