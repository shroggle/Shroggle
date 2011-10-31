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
import javax.persistence.Lob;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Artem Stasuk
 */
@Entity(name = "pageKeywords")
public class PageKeywords implements Serializable {

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Integer> getItems() {
        return (Set<Integer>) items;
    }

    @Column
    @Lob
    private String value;

    @Id
    private int id;

    @Lob
    private Serializable items = new HashSet<Integer>();

    private static final long serialVersionUID = 8413048104344555944L;

}
