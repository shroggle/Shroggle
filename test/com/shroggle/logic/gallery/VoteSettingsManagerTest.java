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
package com.shroggle.logic.gallery;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.entity.VoteSettings;
import com.shroggle.entity.GalleryAlign;
import com.shroggle.entity.GalleryItemColumn;
import com.shroggle.logic.gallery.VoteStarsLinks;
import com.shroggle.util.international.International;
import com.shroggle.util.ServiceLocator;
import com.shroggle.exception.VoteSettingsNotFoundException;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.logic.gallery.voting.VoteSettingsManager;
import junit.framework.Assert;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 *         Date: 21.08.2009
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class VoteSettingsManagerTest {

    @Test(expected = VoteSettingsNotFoundException.class)
    public void create_withoutSettings() {
        new VoteSettingsManager(null);
    }


    @Test
    public void testCreateVoteStars() {
        VoteSettings settings = new VoteSettings();
        settings.setVotingStarsAlign(GalleryAlign.LEFT);
        settings.setVotingStarsPosition(0);
        settings.setVotingStarsColumn(GalleryItemColumn.COLUMN_1);
        settings.setVotingStarsName("starsName");
        VoteSettingsManager manager = new VoteSettingsManager(settings);
        VoteStarsLinks voteStars = manager.createVoteStars();
        Assert.assertNotNull(voteStars);
        Assert.assertEquals(settings.getVotingStarsAlign(), voteStars.getAlign());
        Assert.assertEquals(settings.getVotingStarsPosition(), voteStars.getPosition());
        Assert.assertEquals(settings.getVotingStarsColumn(), voteStars.getColumn());
        Assert.assertEquals(settings.getVotingStarsName(), voteStars.getName());
        Assert.assertEquals(international.get("votingStars"), voteStars.getItemName());
    }


    @Test
    public void testCreateVoteLinks() {
        VoteSettings settings = new VoteSettings();
        settings.setVotingTextLinksAlign(GalleryAlign.LEFT);
        settings.setVotingTextLinksPosition(0);
        settings.setVotingTextLinksColumn(GalleryItemColumn.COLUMN_1);
        settings.setVotingTextLinksName("votingTextLinksName");
        VoteSettingsManager manager = new VoteSettingsManager(settings);
        VoteStarsLinks voteTextLinks = manager.createVoteLinks();
        Assert.assertNotNull(voteTextLinks);
        Assert.assertEquals(settings.getVotingTextLinksAlign(), voteTextLinks.getAlign());
        Assert.assertEquals(settings.getVotingTextLinksPosition(), voteTextLinks.getPosition());
        Assert.assertEquals(settings.getVotingTextLinksColumn(), voteTextLinks.getColumn());
        Assert.assertEquals(settings.getVotingTextLinksName(), voteTextLinks.getName());
        Assert.assertEquals(international.get("votingTextLinks"), voteTextLinks.getItemName());
    }

    private final International international = ServiceLocator.getInternationStorage().get("voteSettings", Locale.US);
}
