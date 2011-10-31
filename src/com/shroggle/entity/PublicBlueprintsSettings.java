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
 * @author Balakirev Anatoliy
 */
@Embeddable
public class PublicBlueprintsSettings {

    @Temporal(TemporalType.TIMESTAMP)
    private Date published;

    @Temporal(TemporalType.TIMESTAMP)
    private Date activated;

    @Column(name = "publicBlueprintsDescription", length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private BlueprintCategory blueprintCategory = BlueprintCategory.BUSINESS_SERVICES;

    public BlueprintCategory getBlueprintCategory() {
        return blueprintCategory;
    }

    public void setBlueprintCategory(BlueprintCategory blueprintCategory) {
        this.blueprintCategory = blueprintCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Date getActivated() {
        return activated;
    }

    public void setActivated(Date activated) {
        this.activated = activated;
    }
}
