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
@Entity(name = "manageVotes")
public class DraftManageVotes extends DraftItem implements ManageVotes {

    public void addGallerySettings(DraftManageVotesSettings manageVotesGallerySettings) {
        manageVotesGallerySettingsList.add(manageVotesGallerySettings);
    }

    public boolean isPickAWinner() {
        return pickAWinner;
    }

    public void setPickAWinner(boolean pickAWinner) {
        this.pickAWinner = pickAWinner;
    }

    public List<DraftManageVotesSettings> getManageVotesGallerySettingsList() {
        return manageVotesGallerySettingsList;
    }

    public void setManageVotesGallerySettingsList(List<DraftManageVotesSettings> manageVotesGallerySettingses) {
        this.manageVotesGallerySettingsList = manageVotesGallerySettingses;
    }

    public boolean isShowVotingModulesFromCurrentSite() {
        return showVotingModulesFromCurrentSite;
    }

    public void setShowVotingModulesFromCurrentSite(boolean showVotingModulesFromCurrentSite) {
        this.showVotingModulesFromCurrentSite = showVotingModulesFromCurrentSite;
    }

    public void removeManageVotesGallerySettings(ManageVotesSettings manageVotesGallerySettings) {
        if (!(manageVotesGallerySettings instanceof DraftManageVotesSettings)) {
            throw new IllegalArgumentException("What the heck are workManageVotesSettings are doing in draftManageVotes?");
        }

        DraftManageVotesSettings manageVotesGallerySettingsToRemove = (DraftManageVotesSettings) manageVotesGallerySettings;
        manageVotesGallerySettingsList.remove(manageVotesGallerySettingsToRemove);
    }

    public ItemType getItemType() {
        return ItemType.MANAGE_VOTES;
    }

    private boolean showVotingModulesFromCurrentSite;

    private boolean pickAWinner;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manageVotes")
    private List<DraftManageVotesSettings> manageVotesGallerySettingsList = new ArrayList<DraftManageVotesSettings>();

}
