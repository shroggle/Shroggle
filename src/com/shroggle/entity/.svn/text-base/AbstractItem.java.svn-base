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
import java.util.Date;

/**
 * @author Artem Stasuk
 * @see DraftItem
 * @see WorkItem
 */
@MappedSuperclass
public abstract class AbstractItem implements Item {

    private Integer siteId;

    @Column(length = 255, nullable = false)
    private String name = "";

    @Lob
    @Column(nullable = false)
    private String description = "";

    private boolean showDescription;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created = new Date();

    private Integer borderId;

    private Integer backgroundId;

    private Integer fontsAndColorsId;

    private Integer itemSizeId;

    private Integer accessibleSettingsId;

    public Integer getAccessibleSettingsId() {
        return accessibleSettingsId;
    }

    public void setAccessibleSettingsId(Integer accessibleSettingsId) {
        this.accessibleSettingsId = accessibleSettingsId;
    }

    public Integer getItemSizeId() {
        return itemSizeId;
    }

    public void setItemSizeId(Integer itemSizeId) {
        this.itemSizeId = itemSizeId;
    }

    public Integer getFontsAndColorsId() {
        return fontsAndColorsId;
    }

    public void setFontsAndColorsId(Integer fontsAndColorsId) {
        this.fontsAndColorsId = fontsAndColorsId;
    }

    public final int getSiteId() {
        return siteId != null ? siteId : -1;
    }

    public final void setSiteId(final Integer siteId) {
        this.siteId = siteId;
    }

    public abstract int getId();

    public abstract void setId(final int id);

    public final Date getCreated() {
        return created;
    }

    public final void setCreated(final Date created) {
        this.created = created;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final boolean isShowDescription() {
        return showDescription;
    }

    public final void setShowDescription(final boolean showDescription) {
        this.showDescription = showDescription;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
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