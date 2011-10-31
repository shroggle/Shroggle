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
package com.shroggle.logic.gallery.voting;

import com.shroggle.entity.VoteSettings;
import com.shroggle.exception.VoteSettingsNotFoundException;
import com.shroggle.logic.gallery.VoteStarsLinks;
import com.shroggle.util.international.International;
import com.shroggle.util.ServiceLocator;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 *         Date: 21.08.2009
 */
public class VoteSettingsManager {

    public VoteSettingsManager(VoteSettings settings) {
        if (settings == null) {
            throw new VoteSettingsNotFoundException("Can`t create VoteSettingsManager by null VoteSettings");
        }
        this.voteSettings = settings;
    }

    public VoteStarsLinks createVoteStars() {
        return new VoteStarsLinks(voteSettings.getVotingStarsPosition(),
                voteSettings.getVotingStarsAlign(), voteSettings.getVotingStarsColumn(),
                voteSettings.getVotingStarsName(), international.get("votingStars"),
                voteSettings.getVotingStarsRow());
    }

    public VoteStarsLinks createVoteLinks() {
        return new VoteStarsLinks(voteSettings.getVotingTextLinksPosition(),
                voteSettings.getVotingTextLinksAlign(), voteSettings.getVotingTextLinksColumn(),
                voteSettings.getVotingTextLinksName(), international.get("votingTextLinks"),
                voteSettings.getVotingTextLinksRow());
    }

    private final VoteSettings voteSettings;
    private final International international = ServiceLocator.getInternationStorage().get("voteSettings", Locale.US);
}
