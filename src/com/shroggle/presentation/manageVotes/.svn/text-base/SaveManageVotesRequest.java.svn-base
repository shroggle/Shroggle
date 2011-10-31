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
package com.shroggle.presentation.manageVotes;

import com.shroggle.entity.DraftManageVotesSettings;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.List;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class SaveManageVotesRequest {

    @RemoteProperty
    private Integer widgetId;

    @RemoteProperty
    private int manageVotesId;

    @RemoteProperty
    private String name;

    @RemoteProperty
    private String description;

    @RemoteProperty
    private boolean showDescription;

    @RemoteProperty
    private boolean showVotingModulesFromCurrentSite;

    @RemoteProperty
    private boolean pickAWinner;

    @RemoteProperty
    private List<DraftManageVotesSettings> manageVotesGallerySettingsListChecked = new ArrayList<DraftManageVotesSettings>();

    @RemoteProperty
    private List<DraftManageVotesSettings> manageVotesGallerySettingsListUnchecked = new ArrayList<DraftManageVotesSettings>();

    public List<DraftManageVotesSettings> getManageVotesGallerySettingsListChecked() {
        return manageVotesGallerySettingsListChecked;
    }

    public void setManageVotesGallerySettingsListChecked(List<DraftManageVotesSettings> manageVotesGallerySettingsListChecked) {
        this.manageVotesGallerySettingsListChecked = manageVotesGallerySettingsListChecked;
    }

    public List<DraftManageVotesSettings> getManageVotesGallerySettingsListUnchecked() {
        return manageVotesGallerySettingsListUnchecked;
    }

    public void setManageVotesGallerySettingsListUnchecked(List<DraftManageVotesSettings> manageVotesGallerySettingsListUnchecked) {
        this.manageVotesGallerySettingsListUnchecked = manageVotesGallerySettingsListUnchecked;
    }

    public boolean isPickAWinner() {
        return pickAWinner;
    }

    public void setPickAWinner(boolean pickAWinner) {
        this.pickAWinner = pickAWinner;
    }

    public int getManageVotesId() {
        return manageVotesId;
    }

    public void setManageVotesId(int manageVotesId) {
        this.manageVotesId = manageVotesId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isShowDescription() {
        return showDescription;
    }

    public void setShowDescription(boolean showDescription) {
        this.showDescription = showDescription;
    }

    public boolean isShowVotingModulesFromCurrentSite() {
        return showVotingModulesFromCurrentSite;
    }

    public void setShowVotingModulesFromCurrentSite(boolean showVotingModulesFromCurrentSite) {
        this.showVotingModulesFromCurrentSite = showVotingModulesFromCurrentSite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }
}
