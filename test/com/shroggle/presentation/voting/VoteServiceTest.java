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
package com.shroggle.presentation.voting;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.*;
import com.shroggle.logic.gallery.VideoRangeEdit;
import com.shroggle.logic.gallery.voting.VoteData;
import com.shroggle.logic.gallery.voting.VoteDataManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.MockWebContextBuilder;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.Context;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import org.directwebremoting.WebContextFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class VoteServiceTest {


    @Before
    public void before() {
        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "-1");
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);
    }

    @Test
    public void testVote() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(0);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);


        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        int voteId = service.vote(voteServiceRequest);


        final VoteDataManager voteDataManager = new VoteDataManager(gallery, user);
        final List<VoteData> votes = voteDataManager.createVoteDataList(new ArrayList<Integer>(Arrays.asList(1)));

        Assert.assertEquals(persistance.getVoteById(voteId).getVoteId(), votes.get(0).getVoteId());
        Assert.assertEquals(1, votes.size());
        Assert.assertEquals(4, votes.get(0).getVoteValue());
        Assert.assertEquals(1, votes.get(0).getFilledFormId());
    }

    @Test
    public void testVoteWithContextVideoRanges() {
        final PageVisitor pageVisitor = TestUtil.createPageVisitor();
        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(0);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        GalleryVideoRange videoRange = new GalleryVideoRange();
        videoRange.setStart(0);
        videoRange.setFinish(3);
        videoRange.setTotal(10);
        videoRange.setGalleryId(gallery.getId());
        videoRange.setFilledFormId(filledForm.getFilledFormId());
        persistance.putGalleryVideoRange(videoRange);
        pageVisitor.addVideoRangeId(videoRange.getRangeId());

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        service.vote(voteServiceRequest);

        Assert.assertEquals("Service must move ranges from session to db.", 0, pageVisitor.getVideoRangeIds().size());
        Assert.assertEquals("Service must set ranges to user in db.", 1, user.getVideoRanges().size());
        final GalleryVideoRange galleryVideoRange = user.getVideoRanges().get(0);
        Assert.assertEquals(gallery.getId(), galleryVideoRange.getGalleryId());
        Assert.assertEquals(filledForm.getFilledFormId(), galleryVideoRange.getFilledFormId());
        Assert.assertEquals(0.0, galleryVideoRange.getStart(), 1);
        Assert.assertEquals(3.0, galleryVideoRange.getFinish(), 1);
        Assert.assertEquals(10.0, galleryVideoRange.getTotal(), 1);
    }

    @Test
    public void testVoteWithContextVideoRangeForOtherGallery() {
        final PageVisitor pageVisitor = TestUtil.createPageVisitor();
        MockWebContextBuilder mockWebContextBuilder = new MockWebContextBuilder();
        WebContextFactory.setWebContextBuilder(mockWebContextBuilder);

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest("", "");
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        Cookie[] cookies = new Cookie[1];
        cookies[0] = new Cookie("sh_pvid", "" + pageVisitor.getPageVisitorId());
        mockHttpServletRequest.setCookies(cookies);
        mockWebContext.setHttpServletRequest(mockHttpServletRequest);
        mockWebContext.setHttpServletResponse(mockHttpServletResponse);

        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(0);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        GalleryVideoRange videoRange = new GalleryVideoRange();
        videoRange.setStart(0);
        videoRange.setFinish(3);
        videoRange.setTotal(10);
        videoRange.setGalleryId(gallery.getId() + 90);
        videoRange.setFilledFormId(filledForm.getFilledFormId());
        persistance.putGalleryVideoRange(videoRange);
        pageVisitor.addVideoRangeId(videoRange.getRangeId());

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        int voteId = service.vote(voteServiceRequest);

        Assert.assertEquals("Service must move ranges from session to db.", 0, pageVisitor.getVideoRangeIds().size());
        Assert.assertEquals("Service must set ranges to user in db.", 1, user.getVideoRanges().size());
        final GalleryVideoRange galleryVideoRange = user.getVideoRanges().get(0);
        Assert.assertEquals(gallery.getId() + 90, galleryVideoRange.getGalleryId());
        Assert.assertEquals(filledForm.getFilledFormId(), galleryVideoRange.getFilledFormId());
        Assert.assertEquals(0.0, galleryVideoRange.getStart(), 1);
        Assert.assertEquals(3.0, galleryVideoRange.getFinish(), 1);
        Assert.assertEquals(10.0, galleryVideoRange.getTotal(), 1);
        Assert.assertEquals(filledForm.getFilledFormId(), galleryVideoRange.getFilledFormId());
    }

    @Test
    public void testVoteWithNotZeroMinimumMedia() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(5);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        int voteId = service.vote(voteServiceRequest);

//        final List<Vote> votes = persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1);
        /*Assert.assertEquals(persistance.getVoteById(voteId), votes.get(0));
        Assert.assertEquals(1, votes.size());
        Assert.assertEquals(4, votes.get(0).getVoteValue());
        Assert.assertEquals(1, votes.get(0).getFilledFormId());
        Assert.assertEquals(gallery.getDraftItem(), votes.get(0).getGalleryId());
        Assert.assertEquals(gallery.getVoteSettings().getStartDate(), votes.get(0).getStartDate());
        Assert.assertEquals(gallery.getVoteSettings().getEndDate(), votes.get(0).getEndDate());
        Assert.assertEquals(user.getUserId(), votes.get(0).getUserId().intValue());*/
    }

    @Test
    public void testVoteWithWatchCountOkBuTimeBad() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(1);
        voteSettings.setMinimumPercentageOfTotalPlayed(50);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "", Arrays.asList(formItem));
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        TestUtil.createGalleryVideoRange(user, gallery, filledForm, 0, 1, 10);

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        boolean exceptionThrown = false;
        try {
            service.vote(voteServiceRequest);
        } catch (NotEnoughWatchedPercentageOfCurrentFileException e) {
            exceptionThrown = true;
            Assert.assertEquals("Please watch at least 50% of this movie before rating it!", e.getMessage());
        }
        Assert.assertTrue(exceptionThrown);
    }

    @Test
    public void testVoteWithWatchCountOkBuTimeBad_with100Percent() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(1);
        voteSettings.setMinimumPercentageOfTotalPlayed(100);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "", Arrays.asList(formItem));
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        TestUtil.createGalleryVideoRange(user, gallery, filledForm, 0, 1, 10);

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        boolean exceptionThrown = false;
        try {
            service.vote(voteServiceRequest);
        } catch (NotEnoughWatchedPercentageOfCurrentFileException e) {
            exceptionThrown = true;
            Assert.assertEquals("Please watch 100% of this movie before you can rate it!", e.getMessage());
        }
        Assert.assertTrue(exceptionThrown);
    }


    @Test
    public void testVoteWithoutVideoRangeButWithVideoRangeEditInRequest() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(1);
        voteSettings.setMinimumPercentageOfTotalPlayed(50);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "", Arrays.asList(formItem));
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);


        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);
        voteServiceRequest.setVideoRangeEdit(new VideoRangeEdit(gallery.getId(), filledForm.getFilledFormId(), 0.0f, 5.0f, 10.0f));

        int voteId = service.vote(voteServiceRequest);
        final Vote vote = persistance.getVoteById(voteId);
        Assert.assertEquals(gallery.getId(), vote.getGalleryId());
        Assert.assertEquals(filledForm.getFilledFormId(), vote.getFilledFormId());
        Assert.assertEquals(4, vote.getVoteValue());
        Assert.assertEquals(user.getUserId(), vote.getUserId().intValue());
    }

    @Test(expected = NotEnoughWatchedFilesException.class)
    public void testVoteWithFullWatchOneButNeed2() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(2);
        voteSettings.setMinimumPercentageOfTotalPlayed(0);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "", Arrays.asList(formItem));
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        TestUtil.createGalleryVideoRange(user, gallery, filledForm, 0, 1, 10);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm, 1, 2, 10);

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        service.vote(voteServiceRequest);
    }

    @Test(expected = NotEnoughWatchedPercentageOfCurrentFileException.class)
    public void testVoteWithFullWatchOneButNowWatchSecondNeed50Percent() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(1);
        voteSettings.setMinimumPercentageOfTotalPlayed(50);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "", Arrays.asList(formItem));
        final FilledForm filledForm1 = TestUtil.createFilledForm(customForm);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm1, 0, 9, 10);

        final FilledForm filledForm2 = TestUtil.createFilledForm(customForm);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm2, 0, 2, 10);

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm2.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        service.vote(voteServiceRequest);
    }

    @Test
    public void testVoteWithPercentMediaAndVideoItemOk() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(0);
        voteSettings.setMinimumPercentageOfTotalPlayed(10);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "", Arrays.asList(formItem));
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryVideoRange galleryVideoRange = new GalleryVideoRange();
        galleryVideoRange.setTotal(10);
        galleryVideoRange.setStart(0);
        galleryVideoRange.setFinish(3);
        galleryVideoRange.setFilledFormId(filledForm.getFilledFormId());
        galleryVideoRange.setGalleryId(gallery.getId());
        user.addVideoRange(galleryVideoRange);
        persistance.putGalleryVideoRange(galleryVideoRange);

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        int voteId = service.vote(voteServiceRequest);

//        final List<Vote> votes = persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1);
//        Assert.assertEquals(persistance.getVoteById(voteId), votes.get(0));
//        Assert.assertEquals(1, votes.size());
//        Assert.assertEquals(4, votes.get(0).getVoteValue());
//        Assert.assertEquals(1, votes.get(0).getFilledFormId());
//        Assert.assertEquals(gallery.getDraftItem(), votes.get(0).getGalleryId());
//        Assert.assertEquals(gallery.getVoteSettings().getStartDate(), votes.get(0).getStartDate());
//        Assert.assertEquals(gallery.getVoteSettings().getEndDate(), votes.get(0).getEndDate());
//        Assert.assertEquals(user.getUserId(), votes.get(0).getUserId().intValue());
    }

    @Test
    public void testVoteWithTwoItemsAndPercentOk() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(0);
        voteSettings.setMinimumPercentageOfTotalPlayed(50);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "", Arrays.asList(formItem));
        final FilledForm filledForm1 = TestUtil.createFilledForm(customForm);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm1, 0, 7, 10);

        final FilledForm filledForm2 = TestUtil.createFilledForm(customForm);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm2, 0, 7, 10);

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm1.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        int voteId = service.vote(voteServiceRequest);

//        final List<Vote> votes = persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1);
        /*Assert.assertEquals(persistance.getVoteById(voteId), votes.get(0));
        Assert.assertEquals(1, votes.size());
        Assert.assertEquals(4, votes.get(0).getVoteValue());
        Assert.assertEquals(1, votes.get(0).getFilledFormId());
        Assert.assertEquals(gallery.getDraftItem(), votes.get(0).getGalleryId());
        Assert.assertEquals(gallery.getVoteSettings().getStartDate(), votes.get(0).getStartDate());
        Assert.assertEquals(gallery.getVoteSettings().getEndDate(), votes.get(0).getEndDate());
        Assert.assertEquals(user.getUserId(), votes.get(0).getUserId().intValue());*/
    }

    @Test
    public void testVoteWithTwoItemsAndLotOfPercentsLowOk() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(0);
        voteSettings.setMinimumPercentageOfTotalPlayed(25);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "", Arrays.asList(formItem));
        final FilledForm filledForm1 = TestUtil.createFilledForm(customForm);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm1, 0, 0.4f, 10);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm1, 0, 0.4f, 10);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm1, 0, 0.4f, 10);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm1, 0, 0.4f, 10);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm1, 0, 0.4f, 10);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm1, 0, 0.4f, 10);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm1, 0, 0.4f, 10);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm1, 0, 0.4f, 10);

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm1.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        int voteId = service.vote(voteServiceRequest);

//        final List<Vote> votes = persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1);
        /* Assert.assertEquals(persistance.getVoteById(voteId), votes.get(0));
       Assert.assertEquals(1, votes.size());
       Assert.assertEquals(4, votes.get(0).getVoteValue());
       Assert.assertEquals(1, votes.get(0).getFilledFormId());
       Assert.assertEquals(gallery.getDraftItem(), votes.get(0).getGalleryId());
       Assert.assertEquals(gallery.getVoteSettings().getStartDate(), votes.get(0).getStartDate());
       Assert.assertEquals(gallery.getVoteSettings().getEndDate(), votes.get(0).getEndDate());
       Assert.assertEquals(user.getUserId(), votes.get(0).getUserId().intValue());*/
    }

    @Test(expected = NotEnoughWatchedPercentageOfCurrentFileException.class)
    public void testVoteWithTwoItemsAndPercentOkOnlyForFirstButVoteOnSecond() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(0);
        voteSettings.setMinimumPercentageOfTotalPlayed(50);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "", Arrays.asList(formItem));
        final FilledForm filledForm1 = TestUtil.createFilledForm(customForm);
        TestUtil.createGalleryVideoRange(user, gallery, filledForm1, 0, 7, 10);

        final FilledForm filledForm2 = TestUtil.createFilledForm(customForm);

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm2.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        service.vote(voteServiceRequest);
    }

    @Test
    public void testVoteWithPercentMediaOk() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(0);
        voteSettings.setMinimumPercentageOfTotalPlayed(100);
        voteSettings.setRegistrationFormIdForVoters(-1);

        gallery.setVoteSettings(voteSettings);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryVideoRange galleryVideoRange = new GalleryVideoRange();
        galleryVideoRange.setTotal(10);
        galleryVideoRange.setStart(0);
        galleryVideoRange.setFinish(3);
        galleryVideoRange.setFilledFormId(filledForm.getFilledFormId());
        galleryVideoRange.setGalleryId(gallery.getId());
        user.addVideoRange(galleryVideoRange);
        persistance.putGalleryVideoRange(galleryVideoRange);

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        int voteId = service.vote(voteServiceRequest);

//        final List<Vote> votes = persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1);
        /*  Assert.assertEquals(persistance.getVoteById(voteId), votes.get(0));
       Assert.assertEquals(1, votes.size());
       Assert.assertEquals(4, votes.get(0).getVoteValue());
       Assert.assertEquals(1, votes.get(0).getFilledFormId());
       Assert.assertEquals(gallery.getDraftItem(), votes.get(0).getGalleryId());
       Assert.assertEquals(gallery.getVoteSettings().getStartDate(), votes.get(0).getStartDate());
       Assert.assertEquals(gallery.getVoteSettings().getEndDate(), votes.get(0).getEndDate());
       Assert.assertEquals(user.getUserId(), votes.get(0).getUserId().intValue());*/
    }

    @Test(expected = NotEnoughWatchedPercentageOfCurrentFileException.class)
    public void testVoteWithPercentMediaAndVideoItem() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setMinimumNumberOfMediaItemsPlayed(0);
        voteSettings.setMinimumPercentageOfTotalPlayed(50);
        voteSettings.setRegistrationFormIdForVoters(-1);
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        gallery.setVoteSettings(voteSettings);

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "", Arrays.asList(formItem));
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryVideoRange galleryVideoRange = new GalleryVideoRange();
        galleryVideoRange.setTotal(10);
        galleryVideoRange.setStart(0);
        galleryVideoRange.setFinish(1);
        galleryVideoRange.setFilledFormId(filledForm.getFilledFormId());
        galleryVideoRange.setGalleryId(gallery.getId());
        user.addVideoRange(galleryVideoRange);
        persistance.putGalleryVideoRange(galleryVideoRange);

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        service.vote(voteServiceRequest);
    }

    @Test(expected = NotEnoughWatchedFilesException.class)
    public void testVoteWithNotZeroMinimumMediaAndVideoItem() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(5);
        voteSettings.setRegistrationFormIdForVoters(-1);
        gallery.setVoteSettings(voteSettings);

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "f", Arrays.asList(formItem));
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

//        Assert.assertEquals(0, persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1).size());

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        service.vote(voteServiceRequest);
    }

    @Test
    public void testVoteWithNotZeroMinimumMediaAndVideoItemAndOk() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(1);
        voteSettings.setMinimumPercentageOfTotalPlayed(0);
        voteSettings.setRegistrationFormIdForVoters(-1);
        gallery.setVoteSettings(voteSettings);

        final DraftFormItem formItem = new DraftFormItem();
        formItem.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "f", Arrays.asList(formItem));
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        final GalleryVideoRange galleryVideoRange = new GalleryVideoRange();
        galleryVideoRange.setTotal(10);
        galleryVideoRange.setFilledFormId(filledForm.getFilledFormId());
        galleryVideoRange.setGalleryId(gallery.getId());
        user.addVideoRange(galleryVideoRange);
        persistance.putGalleryVideoRange(galleryVideoRange);

//        Assert.assertEquals(0, persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1).size());

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        service.vote(voteServiceRequest);
    }

    @Test
    public void testReVote() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        final Date startDate = new Date(System.currentTimeMillis() - 10000000);
        final Date endDate = new Date(System.currentTimeMillis() + 10000000);
        voteSettings.setStartDate(startDate);
        voteSettings.setEndDate(endDate);
        voteSettings.setMinimumNumberOfMediaItemsPlayed(0);
        voteSettings.setRegistrationFormIdForVoters(-1);
        gallery.setVoteSettings(voteSettings);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

//        Assert.assertEquals(0, persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1).size());

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(filledForm.getFilledFormId());
        voteServiceRequest.setVoteValue(4);

        int voteId = service.vote(voteServiceRequest);

//        final List<Vote> votes = persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1);
        /* Assert.assertEquals(persistance.getVoteById(voteId), votes.get(0));
       Assert.assertEquals(1, votes.size());
       Assert.assertEquals(4, votes.get(0).getVoteValue());
       Assert.assertEquals(1, votes.get(0).getFilledFormId());
       Assert.assertEquals(gallery.getDraftItem(), votes.get(0).getGalleryId());
       Assert.assertEquals(gallery.getVoteSettings().getStartDate(), votes.get(0).getStartDate());
       Assert.assertEquals(gallery.getVoteSettings().getEndDate(), votes.get(0).getEndDate());
       Assert.assertEquals(user.getUserId(), votes.get(0).getUserId().intValue());*/
    }

    @Test(expected = IncorrectVoteValueException.class)
    public void testVote_withWrongVoteValue() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);

//        Assert.assertEquals(0, persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1).size());

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(1);
        voteServiceRequest.setVoteValue(-4);

        service.vote(voteServiceRequest);
    }

    @Test(expected = GalleryNotFoundException.class)
    public void testVote_withWrongGalleryId() {
        final User user = TestUtil.createUserAndLogin("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);

//        Assert.assertEquals(0, persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1).size());

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(-1);
        voteServiceRequest.setFilledFormId(1);
        voteServiceRequest.setVoteValue(4);

        service.vote(voteServiceRequest);
    }


    @Test(expected = UserNotLoginedException.class)
    public void testVote_withNotLoginedUser() {
        final User user = TestUtil.createUser("a@a");
        Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        DraftGallery gallery = TestUtil.createGallery(site);

//        Assert.assertEquals(0, persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1).size());

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(1);
        voteServiceRequest.setVoteValue(4);

        service.vote(voteServiceRequest);
    }

    @Test(expected = UserNotLoginedException.class)
    public void testVote_withLoginedUserButWithoutRightsToSite() {
        final User user = TestUtil.createUser("a@a");
        context.setUserId(user.getUserId());
        Site site = TestUtil.createSite();
        DraftGallery gallery = TestUtil.createGallery(site);

//        Assert.assertEquals(0, persistance.getVotesByGalleryIdAndFilledFormId(gallery.getDraftItem(), 1).size());

        final VoteServiceRequest voteServiceRequest = new VoteServiceRequest();
        voteServiceRequest.setSiteId(site.getSiteId());
        voteServiceRequest.setGalleryId(gallery.getId());
        voteServiceRequest.setFilledFormId(1);
        voteServiceRequest.setVoteValue(4);

        service.vote(voteServiceRequest);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final VoteService service = new VoteService();
    private final Context context = ServiceLocator.getContextStorage().get();
}
