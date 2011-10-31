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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class VoteManagerTest {

    @Test
    public void testIsVoteValueCorrect() {
        Assert.assertTrue(VoteManager.isVoteValueCorrect(1));
        Assert.assertTrue(VoteManager.isVoteValueCorrect(2));
        Assert.assertTrue(VoteManager.isVoteValueCorrect(3));
        Assert.assertTrue(VoteManager.isVoteValueCorrect(4));
        Assert.assertTrue(VoteManager.isVoteValueCorrect(5));
        Assert.assertFalse(VoteManager.isVoteValueCorrect(-1));
        Assert.assertFalse(VoteManager.isVoteValueCorrect(6));
        Assert.assertFalse(VoteManager.isVoteValueCorrect(7));
    }

    @Test
    public void testCreateVotingStarsData_withoutVote() {
        TestUtil.createUserAndLogin("");
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);
        gallery.setIncludesVotingModule(true);


        FilledForm filledForm = TestUtil.createFilledForm(new DraftChildSiteRegistration());
        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, widget.getWidgetId(), widget.getSiteId(), filledForm.getFilledFormId());
        Assert.assertEquals(1, datas.size());
        VotingStarsData data = datas.get(0);

        Assert.assertNotNull(data);
        Assert.assertNull(data.getStartDate());
        Assert.assertNull(data.getEndDate());
        Assert.assertFalse(data.isWrongStartOrEndDate());
        Assert.assertNotNull(data.getVoteSettings());
        Assert.assertNotNull(data.getVoteData());
        Assert.assertEquals(gallery.getId(), data.getGalleryId());
        Assert.assertEquals(site.getSiteId(), data.getSiteId());
        Assert.assertEquals(widget.getWidgetId(), data.getWidgetId());
        Assert.assertEquals(gallery.getVoteSettings(), data.getVoteSettings());
        Assert.assertEquals(0, data.getVoteData().getVoteValue());
        Assert.assertEquals(0, data.getVoteData().getVoteId());
        Assert.assertEquals(1, data.getVoteData().getFilledFormId());
        Assert.assertEquals(true, data.isFilledFormExist());
        Assert.assertEquals(false, data.isDisabled());
        Assert.assertEquals(95, data.getVoteSettings().getMinimumPercentageOfTotalPlayed());
        Assert.assertEquals(11, data.getVoteSettings().getMinimumNumberOfMediaItemsPlayed());
    }

    @Test
    public void testCreateVotingStarsData_withoutFilledForm() {
        TestUtil.createUserAndLogin("");
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);


        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, widget.getWidgetId(), widget.getSiteId(), 0);
        Assert.assertEquals(1, datas.size());
        VotingStarsData data = datas.get(0);

        Assert.assertNotNull(data);
        Assert.assertNull(data.getStartDate());
        Assert.assertNull(data.getEndDate());
        Assert.assertFalse(data.isWrongStartOrEndDate());
        Assert.assertNotNull(data.getVoteSettings());
        Assert.assertNotNull(data.getVoteData());
        Assert.assertEquals(gallery.getId(), data.getGalleryId());
        Assert.assertEquals(site.getSiteId(), data.getSiteId());
        Assert.assertEquals(widget.getWidgetId(), data.getWidgetId());
        Assert.assertEquals(gallery.getVoteSettings(), data.getVoteSettings());
        Assert.assertEquals(0, data.getVoteData().getVoteValue());
        Assert.assertEquals(0, data.getVoteData().getVoteId());
        Assert.assertEquals(0, data.getVoteData().getFilledFormId());
        Assert.assertEquals(false, data.isFilledFormExist());
        Assert.assertEquals(true, data.isDisabled());
    }


    @Test
    public void testCreateVotingStarsData_withFilledFormIdButWithoutFilledForm() {
        TestUtil.createUserAndLogin("");
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);


        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, widget.getWidgetId(), widget.getSiteId(), 1);
        Assert.assertEquals(1, datas.size());
        VotingStarsData data = datas.get(0);

        Assert.assertNotNull(data);
        Assert.assertNull(data.getStartDate());
        Assert.assertNull(data.getEndDate());
        Assert.assertFalse(data.isWrongStartOrEndDate());
        Assert.assertNotNull(data.getVoteSettings());
        Assert.assertNotNull(data.getVoteData());
        Assert.assertEquals(gallery.getId(), data.getGalleryId());
        Assert.assertEquals(site.getSiteId(), data.getSiteId());
        Assert.assertEquals(widget.getWidgetId(), data.getWidgetId());
        Assert.assertEquals(gallery.getVoteSettings(), data.getVoteSettings());
        Assert.assertEquals(0, data.getVoteData().getVoteValue());
        Assert.assertEquals(0, data.getVoteData().getVoteId());
        Assert.assertEquals(1, data.getVoteData().getFilledFormId());
        Assert.assertEquals(false, data.isFilledFormExist());
        Assert.assertEquals(true, data.isDisabled());
    }

    @Test
    public void testCreateVotingStarsData_withoutUser() {
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);

        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, widget.getWidgetId(), widget.getSiteId(), 1);
        Assert.assertEquals(1, datas.size());
        Assert.assertEquals(0, datas.get(0).getVoteData().getVoteId());
        Assert.assertEquals(0, datas.get(0).getVoteData().getVoteValue());
        Assert.assertEquals(1, datas.get(0).getVoteData().getFilledFormId());
    }

    @Test
    public void testCreateVotingStarsData_withoutGallery() {
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);

        List<VotingStarsData> datas = VoteManager.createVotingStarsData(null, widget.getWidgetId(), widget.getSiteId(), 1);
        Assert.assertEquals(0, datas.size());
    }

    @Test
    public void testCreateVotingStarsData_withoutWidget() {
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);

        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, -1, 0, 1);
        Assert.assertEquals(0, datas.size());
    }

    @Test
    public void testCreateVotingStarsData_withoutFilledFormIds() {
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);

        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, widget.getWidgetId(), widget.getSiteId());
        Assert.assertEquals(0, datas.size());
    }


    @Test
    public void testCreateVotingStarsData_withOneNullFilledFormId() {
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);

        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, widget.getWidgetId(), widget.getSiteId());
        Assert.assertEquals(0, datas.size());
    }

    @Test
    public void testCreateVotingStarsData_withVotingEnded() {
        User user = TestUtil.createUserAndLogin("");
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);
        gallery.getVoteSettings().setDurationOfVoteLimited(false);

        Vote vote = new Vote();
        vote.setFilledFormId(1);
        vote.setUserId(user.getUserId());
        vote.setGalleryId(gallery.getId());
        vote.setVoteValue(4);
        vote.setVoteDate(new Date());
        ServiceLocator.getPersistance().putVote(vote);

        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, widget.getWidgetId(), widget.getSiteId(), 1);
        Assert.assertEquals(1, datas.size());
        VotingStarsData data = datas.get(0);

        Assert.assertNotNull(data);
        Assert.assertNull(data.getStartDate());
        Assert.assertNull(data.getEndDate());
        Assert.assertFalse(data.isWrongStartOrEndDate());
        Assert.assertNotNull(data.getVoteSettings());
        Assert.assertNotNull(data.getVoteData());
        Assert.assertEquals(gallery.getId(), data.getGalleryId());
        Assert.assertEquals(site.getSiteId(), data.getSiteId());
        Assert.assertEquals(widget.getWidgetId(), data.getWidgetId());
        Assert.assertEquals(1, data.getVoteData().getFilledFormId());
        Assert.assertEquals(gallery.getVoteSettings(), data.getVoteSettings());
        Assert.assertTrue(data.isVotingEnded());

        Assert.assertEquals(vote.getGalleryId(), data.getGalleryId());
        Assert.assertEquals(vote.getFilledFormId(), data.getVoteData().getFilledFormId());
        Assert.assertEquals(vote.getVoteValue(), data.getVoteData().getVoteValue());
        Assert.assertEquals(true, data.isDisabled());
    }


    @Test
    public void testCreateVotingStarsData_withVote() {
        User user = TestUtil.createUserAndLogin("");
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);
        gallery.getVoteSettings().setDurationOfVoteLimited(false);

        Vote vote = new Vote();
        vote.setFilledFormId(1);
        vote.setUserId(user.getUserId());
        vote.setGalleryId(gallery.getId());
        vote.setVoteValue(4);
        vote.setVoteDate(new Date());
        ServiceLocator.getPersistance().putVote(vote);

        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, widget.getWidgetId(), widget.getSiteId(), 1);
        Assert.assertEquals(1, datas.size());
        VotingStarsData data = datas.get(0);

        Assert.assertNotNull(data);
        Assert.assertNull(data.getStartDate());
        Assert.assertNull(data.getEndDate());
        Assert.assertFalse(data.isWrongStartOrEndDate());
        Assert.assertNotNull(data.getVoteSettings());
        Assert.assertNotNull(data.getVoteData());
        Assert.assertEquals(gallery.getId(), data.getGalleryId());
        Assert.assertEquals(site.getSiteId(), data.getSiteId());
        Assert.assertEquals(widget.getWidgetId(), data.getWidgetId());
        Assert.assertEquals(1, data.getVoteData().getFilledFormId());
        Assert.assertEquals(gallery.getVoteSettings(), data.getVoteSettings());

        Assert.assertEquals(vote.getGalleryId(), data.getGalleryId());
        Assert.assertEquals(vote.getFilledFormId(), data.getVoteData().getFilledFormId());
        Assert.assertEquals(vote.getVoteValue(), data.getVoteData().getVoteValue());
        Assert.assertEquals(true, data.isDisabled());
    }


    @Test
    public void testCreateVotingStarsData_withVotes() {
        User user = TestUtil.createUserAndLogin("");
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);
        gallery.getVoteSettings().setDurationOfVoteLimited(false);

        Vote vote1 = new Vote();
        vote1.setFilledFormId(1);
        vote1.setUserId(user.getUserId());
        vote1.setGalleryId(gallery.getId());
        vote1.setVoteValue(4);
        vote1.setVoteDate(new Date());
        ServiceLocator.getPersistance().putVote(vote1);


        Vote vote2 = new Vote();
        vote2.setFilledFormId(2);
        vote2.setUserId(user.getUserId());
        vote2.setGalleryId(gallery.getId());
        vote2.setVoteValue(5);
        vote2.setVoteDate(new Date());
        ServiceLocator.getPersistance().putVote(vote2);

        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, widget.getWidgetId(), widget.getSiteId(), 1, 2);
        Assert.assertEquals(2, datas.size());

        VotingStarsData dataVorVote1 = datas.get(0);
        Assert.assertNotNull(dataVorVote1);
        Assert.assertNull(dataVorVote1.getStartDate());
        Assert.assertNull(dataVorVote1.getEndDate());
        Assert.assertFalse(dataVorVote1.isWrongStartOrEndDate());
        Assert.assertNotNull(dataVorVote1.getVoteSettings());
        Assert.assertNotNull(dataVorVote1.getVoteData());
        Assert.assertEquals(gallery.getId(), dataVorVote1.getGalleryId());
        Assert.assertEquals(site.getSiteId(), dataVorVote1.getSiteId());
        Assert.assertEquals(widget.getWidgetId(), dataVorVote1.getWidgetId());
        Assert.assertEquals(gallery.getVoteSettings(), dataVorVote1.getVoteSettings());
        Assert.assertEquals(gallery.getId(), dataVorVote1.getGalleryId());
        Assert.assertEquals(vote1.getVoteId(), dataVorVote1.getVoteData().getVoteId());
        Assert.assertEquals(vote1.isWinner(), dataVorVote1.getVoteData().isWinner());
        Assert.assertEquals(vote1.getFilledFormId(), dataVorVote1.getVoteData().getFilledFormId());
        Assert.assertEquals(vote1.getVoteValue(), dataVorVote1.getVoteData().getVoteValue());
        Assert.assertEquals(true, dataVorVote1.isDisabled());

        VotingStarsData dataVorVote2 = datas.get(1);
        Assert.assertNotNull(dataVorVote2);
        Assert.assertNull(dataVorVote2.getStartDate());
        Assert.assertNull(dataVorVote2.getEndDate());
        Assert.assertFalse(dataVorVote2.isWrongStartOrEndDate());
        Assert.assertNotNull(dataVorVote2.getVoteSettings());
        Assert.assertNotNull(dataVorVote2.getVoteData());
        Assert.assertEquals(gallery.getId(), dataVorVote2.getGalleryId());
        Assert.assertEquals(site.getSiteId(), dataVorVote2.getSiteId());
        Assert.assertEquals(widget.getWidgetId(), dataVorVote2.getWidgetId());
        Assert.assertEquals(gallery.getVoteSettings(), dataVorVote2.getVoteSettings());
        Assert.assertEquals(gallery.getId(), dataVorVote2.getGalleryId());
        Assert.assertEquals(vote2.getVoteId(), dataVorVote2.getVoteData().getVoteId());
        Assert.assertEquals(vote2.isWinner(), dataVorVote2.getVoteData().isWinner());
        Assert.assertEquals(vote2.getFilledFormId(), dataVorVote2.getVoteData().getFilledFormId());
        Assert.assertEquals(vote2.getVoteValue(), dataVorVote2.getVoteData().getVoteValue());
        Assert.assertEquals(true, dataVorVote2.isDisabled());
    }


    @Test
    public void testCreateVotingStarsData_withVotesAndWithDurationOfVoteLimited() {
        final Date currentDate = new Date();
        final Date currentDateMinusTenDays = new Date(currentDate.getTime() - TEN_DAYS_MILIS);
        final Date currentDatePlusTenDays = new Date(currentDate.getTime() + TEN_DAYS_MILIS);

        User user = TestUtil.createUserAndLogin("");
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);
        gallery.getVoteSettings().setDurationOfVoteLimited(true);
        gallery.getVoteSettings().setStartDate(currentDateMinusTenDays);
        gallery.getVoteSettings().setEndDate(currentDatePlusTenDays);

        Vote vote = new Vote();
        vote.setFilledFormId(1);
        vote.setVoteDate(currentDateMinusTenDays);
        vote.setUserId(user.getUserId());
        vote.setGalleryId(gallery.getId());
        vote.setVoteValue(1);
        ServiceLocator.getPersistance().putVote(vote);

        Vote vote1 = new Vote();
        vote1.setFilledFormId(1);
        vote1.setVoteDate(currentDate);
        vote1.setUserId(user.getUserId());
        vote1.setGalleryId(gallery.getId());
        vote1.setVoteValue(2);
        ServiceLocator.getPersistance().putVote(vote1);

        Vote vote2 = new Vote();
        vote2.setFilledFormId(1);
        vote2.setVoteDate(currentDatePlusTenDays);
        vote2.setUserId(user.getUserId());
        vote2.setGalleryId(gallery.getId());
        vote2.setVoteValue(4);
        ServiceLocator.getPersistance().putVote(vote2);

        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, widget.getWidgetId(), widget.getSiteId(), 1);
        Assert.assertEquals(1, datas.size());
        VotingStarsData data = datas.get(0);

        Assert.assertNotNull(data);
        Assert.assertEquals(DateUtil.toMonthDayAndYear(gallery.getVoteSettings().getStartDate()), data.getStartDate());
        Assert.assertEquals(DateUtil.toMonthDayAndYear(gallery.getVoteSettings().getEndDate()), data.getEndDate());
        Assert.assertFalse(data.isWrongStartOrEndDate());
        Assert.assertNotNull(data.getVoteSettings());
        Assert.assertNotNull(data.getVoteData());
        Assert.assertEquals(gallery.getId(), data.getGalleryId());
        Assert.assertEquals(site.getSiteId(), data.getSiteId());
        Assert.assertEquals(widget.getWidgetId(), data.getWidgetId());
        Assert.assertEquals(1, data.getVoteData().getFilledFormId());
        Assert.assertEquals(gallery.getVoteSettings(), data.getVoteSettings());

        Assert.assertEquals(gallery.getId(), data.getGalleryId());
        Assert.assertEquals(2, data.getVoteData().getVoteValue());
        Assert.assertEquals(true, data.isDisabled());
    }

    @Test
    public void testCreateVotingStarsData_withVotesAndWithDurationOfVoteLimited_CurrentDateBeforeStartDate() {
        final Date currentDate = new Date();
        final Date currentDateMinusTenDays = new Date(currentDate.getTime() - TEN_DAYS_MILIS);
        final Date currentDatePlusTenDays = new Date(currentDate.getTime() + TEN_DAYS_MILIS);

        User user = TestUtil.createUserAndLogin("");
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);
        gallery.getVoteSettings().setDurationOfVoteLimited(true);
        gallery.getVoteSettings().setStartDate(currentDatePlusTenDays);
        gallery.getVoteSettings().setEndDate(currentDatePlusTenDays);

        Vote vote = new Vote();
        vote.setFilledFormId(1);
        vote.setVoteDate(currentDateMinusTenDays);
        vote.setUserId(user.getUserId());
        vote.setGalleryId(gallery.getId());
        vote.setVoteValue(1);
        ServiceLocator.getPersistance().putVote(vote);

        Vote vote1 = new Vote();
        vote1.setFilledFormId(1);
        vote1.setVoteDate(currentDate);
        vote1.setUserId(user.getUserId());
        vote1.setGalleryId(gallery.getId());
        vote1.setVoteValue(2);
        ServiceLocator.getPersistance().putVote(vote1);

        Vote vote2 = new Vote();
        vote2.setFilledFormId(1);
        vote2.setVoteDate(currentDatePlusTenDays);
        vote2.setUserId(user.getUserId());
        vote2.setGalleryId(gallery.getId());
        vote2.setVoteValue(4);
        ServiceLocator.getPersistance().putVote(vote2);

        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, widget.getWidgetId(), widget.getSiteId(), 1);
        Assert.assertEquals(1, datas.size());
        VotingStarsData data = datas.get(0);

        Assert.assertNotNull(data);
        Assert.assertEquals(DateUtil.toMonthDayAndYear(gallery.getVoteSettings().getStartDate()), data.getStartDate());
        Assert.assertEquals(DateUtil.toMonthDayAndYear(gallery.getVoteSettings().getEndDate()), data.getEndDate());
        Assert.assertTrue(data.isWrongStartOrEndDate());
        Assert.assertNotNull(data.getVoteSettings());
        Assert.assertNotNull(data.getVoteData());
        Assert.assertEquals(gallery.getId(), data.getGalleryId());
        Assert.assertEquals(site.getSiteId(), data.getSiteId());
        Assert.assertEquals(widget.getWidgetId(), data.getWidgetId());
        Assert.assertEquals(1, data.getVoteData().getFilledFormId());
        Assert.assertEquals(gallery.getVoteSettings(), data.getVoteSettings());

        Assert.assertEquals(gallery.getId(), data.getGalleryId());
        Assert.assertEquals(4, data.getVoteData().getVoteValue());
        Assert.assertEquals(true, data.isDisabled());
    }

    @Test
    public void testCreateVotingStarsData_withVotesAndWithDurationOfVoteLimited_CurrentDateAfterEndDate() {
        final Date currentDate = new Date();
        final Date currentDateMinusTenDays = new Date(currentDate.getTime() - TEN_DAYS_MILIS);
        final Date currentDatePlusTenDays = new Date(currentDate.getTime() + TEN_DAYS_MILIS);

        User user = TestUtil.createUserAndLogin("");
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        PageManager pageVersion = TestUtil.createPageVersion(page);
        Widget widget = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        pageVersion.addWidget(widget);
        DraftGallery gallery = TestUtil.createGallery(site);
        VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumPercentageOfTotalPlayed(95);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(11);
        gallery.setVoteSettings(voteSettings);
        gallery.getVoteSettings().setDurationOfVoteLimited(true);
        gallery.getVoteSettings().setStartDate(currentDateMinusTenDays);
        gallery.getVoteSettings().setEndDate(currentDateMinusTenDays);

        Vote vote = new Vote();
        vote.setFilledFormId(1);
        vote.setVoteDate(currentDateMinusTenDays);
        vote.setUserId(user.getUserId());
        vote.setGalleryId(gallery.getId());
        vote.setVoteValue(1);
        ServiceLocator.getPersistance().putVote(vote);

        Vote vote1 = new Vote();
        vote1.setFilledFormId(1);
        vote1.setVoteDate(currentDate);
        vote1.setUserId(user.getUserId());
        vote1.setGalleryId(gallery.getId());
        vote1.setVoteValue(2);
        ServiceLocator.getPersistance().putVote(vote1);

        Vote vote2 = new Vote();
        vote2.setFilledFormId(1);
        vote2.setVoteDate(currentDatePlusTenDays);
        vote2.setUserId(user.getUserId());
        vote2.setGalleryId(gallery.getId());
        vote2.setVoteValue(4);
        ServiceLocator.getPersistance().putVote(vote2);

        List<VotingStarsData> datas = VoteManager.createVotingStarsData(gallery, widget.getWidgetId(), widget.getSiteId(), 1);
        Assert.assertEquals(1, datas.size());
        VotingStarsData data = datas.get(0);

        Assert.assertNotNull(data);
        Assert.assertEquals(DateUtil.toMonthDayAndYear(gallery.getVoteSettings().getStartDate()), data.getStartDate());
        Assert.assertEquals(DateUtil.toMonthDayAndYear(gallery.getVoteSettings().getEndDate()), data.getEndDate());
        Assert.assertTrue(data.isWrongStartOrEndDate());
        Assert.assertNotNull(data.getVoteSettings());
        Assert.assertNotNull(data.getVoteData());
        Assert.assertEquals(gallery.getId(), data.getGalleryId());
        Assert.assertEquals(site.getSiteId(), data.getSiteId());
        Assert.assertEquals(widget.getWidgetId(), data.getWidgetId());
        Assert.assertEquals(1, data.getVoteData().getFilledFormId());
        Assert.assertEquals(gallery.getVoteSettings(), data.getVoteSettings());

        Assert.assertEquals(gallery.getId(), data.getGalleryId());
        Assert.assertEquals(1, data.getVoteData().getVoteValue());
        Assert.assertEquals(true, data.isDisabled());
    }

    @Test
    public void testCreateVotingLinksData_forNotCurrentPage_INSIDE_APP_withoutWidget() {
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setManageYourVotesCrossWidgetId(-1);
        VotingLinksData votingLinksData = VoteManager.createVotingLinksData(gallery, page.getPageId(), SiteShowOption.INSIDE_APP);
        Assert.assertEquals("#", votingLinksData.getManageVotesUrl());
        Assert.assertEquals(false, votingLinksData.isManageVotesOnCurrentPage());
    }

    @Test
    public void testCreateVotingLinksData_forNotCurrentPage_INSIDE_APP_withoutSiteShowOption() {
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        Widget widget = TestUtil.createCustomFormWidget(pageVersion);
        DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setManageYourVotesCrossWidgetId(widget.getCrossWidgetId());
        VotingLinksData votingLinksData = VoteManager.createVotingLinksData(gallery, page.getPageId(), null);
        Assert.assertEquals("#", votingLinksData.getManageVotesUrl());
        Assert.assertEquals(false, votingLinksData.isManageVotesOnCurrentPage());
    }

    @Test
    public void testCreateVotingLinksData_forNotCurrentPage_INSIDE_APP() {
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);
        Widget widget = TestUtil.createCustomFormWidget(new PageManager(TestUtil.createPage(site)));
        DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setManageYourVotesCrossWidgetId(widget.getCrossWidgetId());
        VotingLinksData votingLinksData = VoteManager.createVotingLinksData(gallery, page.getPageId(), SiteShowOption.INSIDE_APP);
        Assert.assertEquals("showPageVersion.action?pageId=" + widget.getPage().getPageId() + "&siteShowOption=ON_USER_PAGES#widget" + widget.getWidgetId(), votingLinksData.getManageVotesUrl());
        Assert.assertEquals(false, votingLinksData.isManageVotesOnCurrentPage());
    }

    @Test
    public void testCreateVotingLinksData_forNotCurrentPage_ON_USER_PAGES() {
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);

        Widget widget = TestUtil.createCustomFormWidget(new PageManager(TestUtil.createPage(site)));
        DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setManageYourVotesCrossWidgetId(widget.getCrossWidgetId());
        VotingLinksData votingLinksData = VoteManager.createVotingLinksData(gallery, page.getPageId(), SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals("showPageVersion.action?pageId=" + widget.getPage().getPageId() + "&siteShowOption=ON_USER_PAGES#widget" + widget.getWidgetId(), votingLinksData.getManageVotesUrl());
        Assert.assertEquals(false, votingLinksData.isManageVotesOnCurrentPage());
    }

    @Test
    public void testCreateVotingLinksData_forNotCurrentPage_OUTSIDE_APP_withUrlPrefix() {
        ServiceLocator.getConfigStorage().get().setUserSitesUrl("web-deva.com");
        Site site = TestUtil.createSite();
        Page page = TestUtil.createWorkPage(site);

        Page otherPage = TestUtil.createWorkPage(site);
        final PageManager otherPageVersion = new PageManager(otherPage);
        otherPageVersion.getWorkPageSettings().setUrl("manageVotesPageVersionUrl");


        Widget widget = TestUtil.createCustomFormWidget(otherPageVersion);
        widget.getSite().setSubDomain("site");
        otherPageVersion.getWorkPageSettings().addWidget(widget);


        DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setManageYourVotesCrossWidgetId(widget.getCrossWidgetId());
        VotingLinksData votingLinksData = VoteManager.createVotingLinksData(gallery, page.getPageId(), SiteShowOption.OUTSIDE_APP);
        Assert.assertEquals("http://site.web-deva.com/manageVotesPageVersionUrl#widget" + widget.getWidgetId(), votingLinksData.getManageVotesUrl());
        Assert.assertEquals(false, votingLinksData.isManageVotesOnCurrentPage());
    }

    @Test
    public void testCreateVotingLinksData_forNotCurrentPage_OUTSIDE_APP_withAliasUrl() {
        ServiceLocator.getConfigStorage().get().setUserSitesUrl("web-deva.com");
        Site site = TestUtil.createSite();
        Page page = TestUtil.createPage(site);

        Page otherPage = TestUtil.createWorkPage(site);
        final PageManager otherPageVersion = new PageManager(otherPage);
        otherPageVersion.getWorkPageSettings().setUrl("manageVotesPageVersionUrl");
        Widget widget = TestUtil.createCustomFormWidget(otherPageVersion);
        if (otherPageVersion.getWorkPageSettings() != null) {
            otherPageVersion.getWorkPageSettings().addWidget(widget);
        }
        widget.getSite().setSubDomain("site");
        widget.getSite().setCustomUrl("test.com.ua");


        DraftGallery gallery = TestUtil.createGallery(site);
        gallery.getVoteSettings().setManageYourVotesCrossWidgetId(widget.getCrossWidgetId());
        VotingLinksData votingLinksData = VoteManager.createVotingLinksData(gallery, page.getPageId(), SiteShowOption.OUTSIDE_APP);
        Assert.assertEquals("http://test.com.ua/manageVotesPageVersionUrl#widget" + widget.getWidgetId(), votingLinksData.getManageVotesUrl());
        Assert.assertEquals(false, votingLinksData.isManageVotesOnCurrentPage());
    }

    @Test
    public void testCreateVotingLinksData_forCurrentPage_OUTSIDE_APP() {
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = new PageManager(TestUtil.createWorkPage(site));
        Widget widget = TestUtil.createCustomFormWidget(pageVersion);
        if (pageVersion.getWorkPageSettings() != null) {
            pageVersion.getWorkPageSettings().addWidget(widget);
        }
        DraftGallery gallery = TestUtil.createGallery(site);

        gallery.getVoteSettings().setManageYourVotesCrossWidgetId(widget.getCrossWidgetId());
        VotingLinksData votingLinksData = VoteManager.createVotingLinksData(gallery, widget.getPage().getPageId(), SiteShowOption.OUTSIDE_APP);
        Assert.assertEquals("#widget" + widget.getWidgetId(), votingLinksData.getManageVotesUrl());
        Assert.assertEquals(true, votingLinksData.isManageVotesOnCurrentPage());
    }

    @Test
    public void testCreateVotingLinksData_forCurrentPage_ON_USER_PAGES() {
        final PageManager pageVersion = TestUtil.createPageVersionSiteUser();
        Widget widget = TestUtil.createCustomFormWidget(pageVersion);
        DraftGallery gallery = TestUtil.createGallery(new Site());
        gallery.getVoteSettings().setManageYourVotesCrossWidgetId(widget.getCrossWidgetId());
        VotingLinksData votingLinksData = VoteManager.createVotingLinksData(gallery, widget.getPage().getPageId(), SiteShowOption.ON_USER_PAGES);
        Assert.assertEquals("#widget" + widget.getWidgetId(), votingLinksData.getManageVotesUrl());
        Assert.assertEquals(true, votingLinksData.isManageVotesOnCurrentPage());
    }

    @Test
    public void testCreateVotingLinksData_forCurrentPage_INSIDE_APP() {
        final PageManager pageVersion = TestUtil.createPageVersionSiteUser();
        Widget widget = TestUtil.createCustomFormWidget(pageVersion);
        DraftGallery gallery = TestUtil.createGallery(new Site());
        gallery.getVoteSettings().setManageYourVotesCrossWidgetId(widget.getCrossWidgetId());
        VotingLinksData votingLinksData = VoteManager.createVotingLinksData(gallery, widget.getPage().getPageId(), SiteShowOption.INSIDE_APP);
        Assert.assertEquals("#widget" + widget.getWidgetId(), votingLinksData.getManageVotesUrl());
        Assert.assertEquals(true, votingLinksData.isManageVotesOnCurrentPage());
    }

    /*@Test
    public void testCreateAveragedVote() {
        Vote vote1 = TestUtil.createVote(1, 1, 1);
        vote1.setVoteValue(5);
        Vote vote2 = TestUtil.createVote(1, 1, 1);
        vote2.setVoteValue(4);
        Vote vote3 = TestUtil.createVote(1, 1, 1);
        vote3.setVoteValue(3);
        Vote newVote = VoteManager.createAveragedVote(Arrays.asList(vote1, vote2, vote3));
        Assert.assertEquals(newVote.getVoteId(), vote1.getVoteId());
        Assert.assertEquals(4, newVote.getVoteValue());
        Assert.assertEquals(newVote.getGalleryId(), vote1.getGalleryId());
        Assert.assertEquals(newVote.getFilledFormId(), vote1.getFilledFormId());
        Assert.assertEquals(newVote.getUserId(), vote1.getUserId());
    }*/

    private final long TEN_DAYS_MILIS = 10 * 24 * 60 * 60 * 1000L;
}
