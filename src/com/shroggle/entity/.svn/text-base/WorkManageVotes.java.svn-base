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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@Entity(name = "workManageVotes")
public class WorkManageVotes extends WorkItem implements ManageVotes {

    public void addGallerySettings(WorkManageVotesSettings manageVotesGallerySettings) {
        manageVotesGallerySettingsList.add(manageVotesGallerySettings);
    }

    public boolean isPickAWinner() {
        return pickAWinner;
    }

    public void setPickAWinner(boolean pickAWinner) {
        this.pickAWinner = pickAWinner;
    }

    public List<WorkManageVotesSettings> getManageVotesGallerySettingsList() {
        return manageVotesGallerySettingsList;
    }

    public boolean isShowVotingModulesFromCurrentSite() {
        return showVotingModulesFromCurrentSite;
    }

    public void setShowVotingModulesFromCurrentSite(boolean showVotingModulesFromCurrentSite) {
        this.showVotingModulesFromCurrentSite = showVotingModulesFromCurrentSite;
    }

    public void removeManageVotesGallerySettings(ManageVotesSettings manageVotesGallerySettings) {
        if (!(manageVotesGallerySettings instanceof WorkManageVotesSettings)) {
            throw new IllegalArgumentException("What the heck are draftManageVotesSettings are doing in workManageVotes?");
        }

        WorkManageVotesSettings manageVotesGallerySettingsToRemove = (WorkManageVotesSettings) manageVotesGallerySettings;
        manageVotesGallerySettingsList.remove(manageVotesGallerySettingsToRemove);
    }

    public ItemType getItemType() {
        return ItemType.MANAGE_VOTES;
    }

    private boolean showVotingModulesFromCurrentSite;

    private boolean pickAWinner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manageVotes")
    private List<WorkManageVotesSettings> manageVotesGallerySettingsList = new ArrayList<WorkManageVotesSettings>();

}