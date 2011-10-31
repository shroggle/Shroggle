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

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

/**
 * @author dmitry.solomadin
 *         papap--papap--parara-papap - Em
 *         papap--papap--parara-papap - Am
 *         papap--papap--parara-papap - Cm
 *         parara--ra--pap - C
 *         Guess what?
 */
@DataTransferObject
@Entity(name = "workVotingModules")
public class WorkManageVotesSettings implements ManageVotesSettings {

    @Id
    private int id;

    @Column(length = 250)
    private String customName;

    private String colorCode;

    // This id necessary to get record name for which user had voted.
    private int formItemId;

    private int galleryCrossWidgetId;

    @ManyToOne
    @ForeignKey(name = "votingModulesManageVoteIdWork")
    @JoinColumn(name = "manageVotesId", nullable = false)
    private WorkManageVotes manageVotes;

    public int getFormItemId() {
        return formItemId;
    }

    public void setFormItemId(int formItemId) {
        this.formItemId = formItemId;
    }

    public int getGalleryCrossWidgetId() {
        return galleryCrossWidgetId;
    }

    public void setGalleryCrossWidgetId(int galleryCrossWidgetId) {
        this.galleryCrossWidgetId = galleryCrossWidgetId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WorkManageVotes getManageVotes() {
        return manageVotes;
    }

    public void setManageVotes(ManageVotes manageVotes) {
        if (manageVotes != null && !(manageVotes instanceof WorkManageVotes)){
            throw new IllegalArgumentException("Here you should pass a WorkManageVotes.");
        }

        this.manageVotes = (WorkManageVotes) manageVotes;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}