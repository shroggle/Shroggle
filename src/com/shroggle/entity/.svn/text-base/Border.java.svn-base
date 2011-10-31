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
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

@DataTransferObject
@Entity(name = "bordersBackgrounds")
public class Border {

    @Id
    @Column(name = "formId")
    private int id;

    private int siteId;

    /*@Column(length = 255, nullable = false)
    private String name;// todo. Remove this field from DB. Tolik
*/
    @RemoteProperty
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "borderWidthId")
    @ForeignKey(name = "bordersBackgroundsBorderWidthId")
    private Style borderWidth = new Style();

    @RemoteProperty
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "borderStyleId")
    @ForeignKey(name = "bordersBackgroundsBorderStyleId")
    private Style borderStyle = new Style();

    @RemoteProperty
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "borderColorId")
    @ForeignKey(name = "bordersBackgroundsBorderColorId")
    private Style borderColor = new Style();

    @RemoteProperty
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "borderPaddingId")
    @ForeignKey(name = "bordersBackgroundsBorderPaddingId")
    private Style borderPadding = new Style();

    @RemoteProperty
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "borderMarginId")
    @ForeignKey(name = "bordersBackgroundsBorderMarginId")
    private Style borderMargin = new Style();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public Style getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(Style borderWidth) {
        this.borderWidth = borderWidth;
    }

    public Style getBorderStyle() {
        return borderStyle;
    }

    public void setBorderStyle(Style borderStyle) {
        this.borderStyle = borderStyle;
    }

    public Style getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Style borderColor) {
        this.borderColor = borderColor;
    }

    public Style getBorderPadding() {
        return borderPadding;
    }

    public void setBorderPadding(Style borderPadding) {
        this.borderPadding = borderPadding;
    }

    public Style getBorderMargin() {
        return borderMargin;
    }

    public void setBorderMargin(Style borderMargin) {
        this.borderMargin = borderMargin;
    }
}