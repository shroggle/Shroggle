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

import javax.persistence.*;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
@Entity(name = "itemSize")
public class ItemSize {

    @Id
    private int id;

    private boolean floatable = false;

    private boolean createClearDiv = false;

    private int width = 100;

    private Integer height;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private WidgetSizeType heightSizeType = WidgetSizeType.PERCENT;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private WidgetSizeType widthSizeType = WidgetSizeType.PERCENT;

    @Column(nullable = false, length = 10)
    @Enumerated(value = EnumType.STRING)
    private WidgetOverflowType overflow_x = WidgetOverflowType.VISIBLE;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private WidgetOverflowType overflow_y = WidgetOverflowType.VISIBLE;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFloatable() {
        return floatable;
    }

    public void setFloatable(boolean floatable) {
        this.floatable = floatable;
    }

    public boolean isCreateClearDiv() {
        return createClearDiv;
    }

    public void setCreateClearDiv(boolean createClearDiv) {
        this.createClearDiv = createClearDiv;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public WidgetSizeType getHeightSizeType() {
        return heightSizeType;
    }

    public void setHeightSizeType(WidgetSizeType heightSizeType) {
        this.heightSizeType = heightSizeType;
    }

    public WidgetSizeType getWidthSizeType() {
        return widthSizeType;
    }

    public void setWidthSizeType(WidgetSizeType widthSizeType) {
        this.widthSizeType = widthSizeType;
    }

    public WidgetOverflowType getOverflow_x() {
        return overflow_x;
    }

    public void setOverflow_x(WidgetOverflowType overflow_x) {
        this.overflow_x = overflow_x;
    }

    public WidgetOverflowType getOverflow_y() {
        return overflow_y;
    }

    public void setOverflow_y(WidgetOverflowType overflow_y) {
        this.overflow_y = overflow_y;
    }

}
