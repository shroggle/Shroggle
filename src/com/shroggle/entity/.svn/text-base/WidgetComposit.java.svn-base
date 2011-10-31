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

import com.shroggle.logic.widget.WidgetComparator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(name = "widgetComposits")
public class WidgetComposit extends Widget {      

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Widget> childs = new ArrayList<Widget>();


    public List<Widget> getChilds() {
        Collections.sort(childs, WidgetComparator.INSTANCE);
        return childs;
    }

    public void addChild(Widget widget) {
        widget.setParent(this);
        childs.add(widget);
    }

    public void setChilds(List<Widget> childs) {
        this.childs = childs;
    }

    public void removeChild(Widget widget) {
        childs.remove(widget);
    }

    @Override
    public boolean isWidgetItem() {
        return false;
    }

    @Override
    public boolean isWidgetComposit() {
        return true;
    }

    public ItemType getItemType() {
        return ItemType.COMPOSIT;
    }

    @Override
    public String getItemName() {
        return null;
    }    
}