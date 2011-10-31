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
package com.shroggle.logic.manageVotes;

import com.shroggle.entity.DraftManageVotesSettings;
import com.shroggle.entity.ManageVotesSettings;
import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author dmitry.solomadin
 */
@DataTransferObject
public class WinnerInfo {

    private ManageVotesSettings manageVotesGallerySettings;

    private int voteId;

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public ManageVotesSettings getManageVotesGallerySettings() {
        return manageVotesGallerySettings;
    }

    public void setManageVotesGallerySettings(ManageVotesSettings manageVotesGallerySettings) {
        this.manageVotesGallerySettings = manageVotesGallerySettings;
    }
}
