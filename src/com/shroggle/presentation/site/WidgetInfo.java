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
package com.shroggle.presentation.site;

import com.shroggle.entity.*;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import org.directwebremoting.annotations.DataTransferObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class WidgetInfo {

    public WidgetInfo(final Widget widget, final SiteShowOption siteShowOption) {
        final WidgetManager widgetManager = new WidgetManager(widget);
        this.widgetId = widget.getWidgetId();
        final Border border = widgetManager.getBorder(siteShowOption);
        this.borderId = border != null ? border.getId() : -1;
        final Background background = widgetManager.getBackground(siteShowOption);
        this.backgroundId = background != null ? background.getId() : -1;
        this.itemSize = widgetManager.getItemSize(siteShowOption);
        this.position = widget.getPosition();
        this.itemType = widget.getItemType();
        this.itemName = widget.getItemName();
        this.type = widget.getItemType();
        this.blueprintEditable = widget.isBlueprintEditable();
        this.blueprintRequired = widget.isBlueprintRequired();
        this.blueprintEditRuche = widget.isBlueprintEditRuche();
        this.createdByBlueprintWidget = widget.isCreatedByBlueprintWidget();
        final List<Widget> children = widget.isWidgetComposit() ? ((WidgetComposit) widget).getChilds() : Collections.<Widget>emptyList();
        childs.addAll(getAllChildren(children, siteShowOption));
        if (widget.isWidgetItem() && itemType.isFormType() || itemType == ItemType.GALLERY) {
            final ItemManager itemManager = new ItemManager(((WidgetItem) widget).getDraftItem());
            this.formId = itemType == ItemType.GALLERY ? ((DraftGallery) itemManager.getDraftItem()).getFormId1() : itemManager.getId();
            this.eCommerceStoreWidget = itemManager.getItemTypeConsiderGalleryType() == ItemType.E_COMMERCE_STORE;
        } else {
            this.formId = null;
            this.eCommerceStoreWidget = false;
        }
    }

    private List<WidgetInfo> getAllChildren(final List<Widget> children, final SiteShowOption siteShowOption) {
        final List<WidgetInfo> widgetInfoList = new ArrayList<WidgetInfo>();
        for (Widget widget : children) {
            final WidgetInfo widgetInfo = new WidgetInfo(widget, siteShowOption);
            widgetInfoList.add(widgetInfo);
        }
        return widgetInfoList;
    }

    private final int widgetId;

    private final int borderId;

    private final int backgroundId;

    private final int position;

    private final Integer formId;

    private final ItemSize itemSize;

    private final ItemType itemType;

    private final String itemName;

    private final ItemType type;

    private final boolean blueprintEditable;

    private final boolean blueprintRequired;

    private final boolean blueprintEditRuche;

    private final boolean createdByBlueprintWidget;

    private final boolean eCommerceStoreWidget;

    private List<WidgetInfo> childs = new ArrayList<WidgetInfo>();

    public boolean iseCommerceStoreWidget() {
        return eCommerceStoreWidget;
    }

    public Integer getFormId() {
        return formId;
    }

    public String getItemName() {
        return itemName;
    }

    public boolean isBlueprintEditable() {
        return blueprintEditable;
    }

    public boolean isBlueprintRequired() {
        return blueprintRequired;
    }

    public boolean isBlueprintEditRuche() {
        return blueprintEditRuche;
    }

    public boolean isCreatedByBlueprintWidget() {
        return createdByBlueprintWidget;
    }

    public ItemType getType() {
        return type;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public List<WidgetInfo> getChilds() {
        return childs;
    }

    public int getBorderId() {
        return borderId;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public int getPosition() {
        return position;
    }

    public ItemSize getItemSize() {
        return itemSize;
    }

    public int getWidgetId() {
        return widgetId;
    }
}
