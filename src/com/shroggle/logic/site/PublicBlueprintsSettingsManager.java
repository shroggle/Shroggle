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
package com.shroggle.logic.site;

import com.shroggle.entity.BlueprintCategory;
import com.shroggle.entity.PublicBlueprintsSettings;
import com.shroggle.util.StringUtil;

import java.util.Date;

/**
 * @author Balakirev Anatoliy
 */
public class PublicBlueprintsSettingsManager {

    public PublicBlueprintsSettingsManager(PublicBlueprintsSettings publicBlueprintsSettings) {
        if (publicBlueprintsSettings == null) {
            throw new IllegalArgumentException("Unable to create PublicBlueprintsSettingsManager without PublicBlueprintsSettings.");
        }
        this.publicBlueprintsSettings = publicBlueprintsSettings;
    }

    public String getDescription() {
        return StringUtil.getEmptyOrString(publicBlueprintsSettings.getDescription());
    }

    public Date getPublished() {
        return publicBlueprintsSettings.getPublished();
    }

    public Date getActivated() {
        return publicBlueprintsSettings.getActivated();
    }

    public void setDescription(String description) {
        this.publicBlueprintsSettings.setDescription(description);
    }

    public void publish() {
        this.publicBlueprintsSettings.setPublished(new Date());
    }

    public void removePublishing() {
        this.publicBlueprintsSettings.setPublished(null);
    }

    public void activate() {
        this.publicBlueprintsSettings.setActivated(new Date());
    }

    public BlueprintCategory getBlueprintCategory() {
        return publicBlueprintsSettings.getBlueprintCategory();
    }

    public void setBlueprintCategory(BlueprintCategory blueprintCategory) {
        this.publicBlueprintsSettings.setBlueprintCategory(blueprintCategory);
    }

    private final PublicBlueprintsSettings publicBlueprintsSettings;
}
