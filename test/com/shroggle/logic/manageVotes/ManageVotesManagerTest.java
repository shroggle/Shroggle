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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.page.PageManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ManageVotesManagerTest {

    @Test
    public void testConstructWinnerList() {
        final User user = TestUtil.createUserAndLogin();
        final User user1 = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final DraftForm form = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(form);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1008);
        final DraftGallery gallery = TestUtil.createGallery(site.getSiteId(), "", "", form);
        widgetGallery.setDraftItem(gallery);

        final WidgetItem widgetGallery1 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery1.setCrossWidgetId(1009);
        final DraftGallery gallery1 = TestUtil.createGallery(site.getSiteId(), "", "", form);
        widgetGallery1.setDraftItem(gallery1);


        final Vote winnerVote = TestUtil.createVote(gallery.getId(), filledForm.getFilledFormId(), user.getUserId());
        winnerVote.setWinner(true);
        final Vote winnerNotMyVote = TestUtil.createVote(gallery.getId(), filledForm.getFilledFormId(), user1.getUserId());
        winnerNotMyVote.setWinner(true);
        final Vote winnerVoteFormNotSelectedGallery = TestUtil.createVote(gallery1.getId(), filledForm.getFilledFormId(), user.getUserId());
        winnerVoteFormNotSelectedGallery.setWinner(true);
        TestUtil.createVote(gallery.getId(), filledForm.getFilledFormId(), user.getUserId());

        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site);
        manageVotes.addGallerySettings(TestUtil.createManageVotesGallerySettings(widgetGallery));

        final List<WinnerInfo> winnerInfoList = new ManageVotesManager(manageVotes, SiteShowOption.getDraftOption()).constructWinnerList();
        Assert.assertEquals(1, winnerInfoList.size());
        Assert.assertEquals(winnerVote.getVoteId(), winnerInfoList.get(0).getVoteId());
    }

    @Test
    public void testGetManageVotesGallerySettingsList() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1008);
        final DraftGallery gallery = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery);

        final WidgetItem widgetGallery1 = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery1.setCrossWidgetId(1009);
        final DraftGallery gallery1 = TestUtil.createGallery(site);
        widgetGallery1.setDraftItem(gallery1);

        final WidgetItem removedWidget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        removedWidget.setCrossWidgetId(1010);
        final DraftGallery gallery3 = TestUtil.createGallery(site);
        removedWidget.setDraftItem(gallery3);

        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site);
        final DraftManageVotesSettings manageVotesGallerySettings1 = TestUtil.createManageVotesGallerySettings(widgetGallery);
        manageVotesGallerySettings1.setManageVotes(manageVotes);
        manageVotes.addGallerySettings(manageVotesGallerySettings1);
        final DraftManageVotesSettings manageVotesGallerySettings2 = TestUtil.createManageVotesGallerySettings(removedWidget);
        manageVotesGallerySettings2.setManageVotes(manageVotes);
        manageVotes.addGallerySettings(manageVotesGallerySettings2);

        new WidgetManager(removedWidget.getWidgetId()).remove();

        List<? extends ManageVotesSettings> settingsList = new ManageVotesManager(manageVotes, SiteShowOption.getDraftOption()).getManageVotesGallerySettingsList();
        Assert.assertEquals(1, settingsList.size());
        Assert.assertEquals(widgetGallery.getCrossWidgetId(), settingsList.get(0).getGalleryCrossWidgetId());
    }
}
