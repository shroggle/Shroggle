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

import com.shroggle.util.cache.CachePolicy;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@CachePolicy(maxElementsInMemory = 4000)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "widgets")
@org.hibernate.annotations.Table(
        appliesTo = "widgets",
        indexes = {
                @org.hibernate.annotations.Index(
                        name = "crossWidgetIdIndex",
                        columnNames = {"crossWidgetId"}
                )
        }
)
public abstract class Widget implements StylesOwner {

    // It`s very big piece of work to split Widget into DraftWidget and WorkWidget so
    // I just add here links to both work and draft pageSettings but one of them is always null. Tolik
    @OneToOne
    @JoinColumn(name = "draftPageSettingsId")
    @ForeignKey(name = "widgetsDraftPageSettingsId")
    private DraftPageSettings draftPageSettings;

    @OneToOne
    @JoinColumn(name = "workPageSettingsId")
    @ForeignKey(name = "widgetsWorkPageSettingsId")
    private WorkPageSettings workPageSettings;

    private Integer borderId;

    private Integer backgroundId;

    private Integer fontsAndColorsId;

    private Integer itemSizeId;

    private Integer accessibleSettingsId;

    @ManyToOne
    @JoinColumn(name = "parentId")
    @ForeignKey(name = "widgetsWidgetCompositId")
    private WidgetComposit parent;

    @Column(nullable = false)
    private int position;

    @Id
    @GeneratedValue(generator = "hibernatePeristanceId")
    @GenericGenerator(name = "hibernatePeristanceId",
            strategy = "com.shroggle.util.persistance.hibernate.HibernatePersistanceId")
    private int widgetId;

    /**
     * This field setted only if widget create. If widget copy this field copying.
     * This field updated when we are creating site by blueprint. In this case crossWidgetId moved to parentCrossWidgetId
     * and this crossWidgetId changed to widgetId. Tolik
     */
    @Column(updatable = true)
    private int crossWidgetId; // todo. remove this. Tolik

    private boolean blueprintEditable;

    private boolean blueprintRequired;

    private boolean blueprintEditRuche;

    private boolean createdByBlueprintWidget;

    /**
     * Set when widget based on blueprint widget;
     */
    private Integer parentCrossWidgetId; // todo. remove this. Tolik

    private boolean blueprintShareble;


    /*----------------------------------------------------Methods-----------------------------------------------------*/

    public Integer getAccessibleSettingsId() {
        return accessibleSettingsId;
    }

    public void setAccessibleSettingsId(Integer accessibleSettingsId) {
        this.accessibleSettingsId = accessibleSettingsId;
    }

    public Integer getFontsAndColorsId() {
        return fontsAndColorsId;
    }

    public void setFontsAndColorsId(Integer fontsAndColorsId) {
        this.fontsAndColorsId = fontsAndColorsId;
    }

    public Integer getItemSizeId() {
        return itemSizeId;
    }

    public void setItemSizeId(Integer itemSizeId) {
        this.itemSizeId = itemSizeId;
    }

    public int getSiteId() {
        return getSite().getSiteId();
    }

    public Site getSite() {
        return getPage().getSite();
    }

    public Page getPage() {
        return getPageSettings().getPage();
    }

    public PageSettings getPageSettings() {
        return getDraftPageSettings() != null ? getDraftPageSettings() : getWorkPageSettings();
    }

    public DraftPageSettings getDraftPageSettings() {
        return draftPageSettings;
    }

    public void setDraftPageSettings(DraftPageSettings draftPageSettings) {
        this.draftPageSettings = draftPageSettings;
    }

    public WorkPageSettings getWorkPageSettings() {
        return workPageSettings;
    }

    public void setWorkPageSettings(WorkPageSettings workPageSettings) {
        this.workPageSettings = workPageSettings;
    }

    public abstract boolean isWidgetItem();

    public abstract boolean isWidgetComposit();

    public abstract ItemType getItemType();

    public abstract String getItemName();

    public WidgetComposit getParent() {
        return parent;
    }

    public void setParent(WidgetComposit parent) {
        this.parent = parent;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isBlueprintEditable() {
        return blueprintEditable;
    }

    public void setBlueprintEditable(boolean blueprintEditable) {
        this.blueprintEditable = blueprintEditable;
    }

    public boolean isBlueprintRequired() {
        return blueprintRequired;
    }

    public void setBlueprintRequired(boolean blueprintRequired) {
        this.blueprintRequired = blueprintRequired;
    }

    public boolean isBlueprintEditRuche() {
        return blueprintEditRuche;
    }

    public void setBlueprintEditRuche(boolean blueprintEditRuche) {
        this.blueprintEditRuche = blueprintEditRuche;
    }

    public int getCrossWidgetId() {
        return crossWidgetId;
    }

    public void setCrossWidgetId(final int crossWidgetId) {
        this.crossWidgetId = crossWidgetId;
    }

    public Integer getParentCrossWidgetId() {
        return parentCrossWidgetId;
    }

    public void setParentCrossWidgetId(Integer parentCrossWidgetId) {
        this.parentCrossWidgetId = parentCrossWidgetId;
    }

    public boolean isCreatedByBlueprintWidget() {
        return createdByBlueprintWidget;
    }

    public void setCreatedByBlueprintWidget(boolean createdByBlueprintWidget) {
        this.createdByBlueprintWidget = createdByBlueprintWidget;
    }

    public boolean isBlueprintShareble() {
        return blueprintShareble;
    }

    public void setBlueprintShareble(boolean blueprintShareble) {
        this.blueprintShareble = blueprintShareble;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof Widget && widgetId == ((Widget) o).widgetId);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + widgetId;
        return hash;
    }

    public Integer getBorderId() {
        return borderId;
    }

    public void setBorderId(Integer borderId) {
        this.borderId = borderId;
    }

    public Integer getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(Integer backgroundId) {
        this.backgroundId = backgroundId;
    }
}
