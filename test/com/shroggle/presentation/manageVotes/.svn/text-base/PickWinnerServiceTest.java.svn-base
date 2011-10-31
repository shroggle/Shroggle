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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.VoteNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class PickWinnerServiceTest {

    final PickWinnerService service = new PickWinnerService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute() throws IOException, ServletException {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final PageManager pageVersion = TestUtil.createPageVersionAndPage(site);

        final WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion);
        widgetGallery.setCrossWidgetId(1001);
        final DraftGallery gallery = TestUtil.createGallery(site);
        widgetGallery.setDraftItem(gallery);

        final DraftForm form = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(form);

        final Vote oldWinnerVote = TestUtil.createVote(gallery.getId(), filledForm.getFilledFormId(), user.getUserId());
        oldWinnerVote.setWinner(true);
        final Vote newWinnerVote = TestUtil.createVote(gallery.getId(), filledForm.getFilledFormId(), user.getUserId());

        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site);
        TestUtil.createManageVotesGallerySettings(widgetGallery);

        final String response = service.execute(gallery.getId(), newWinnerVote.getVoteId(), manageVotes.getId(), SiteShowOption.ON_USER_PAGES);

        Assert.assertEquals("/site/render/manageVotes/winnerList.jsp", response);
        Assert.assertEquals(true, newWinnerVote.isWinner());
        Assert.assertEquals(false, oldWinnerVote.isWinner());
    }

    @Test
    public void testExecuteWithAnotherSelectedGallery() throws IOException, ServletException {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftGallery gallery1 = TestUtil.createGallery(site);
        final DraftForm form = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(form);

        final Vote oldWinnerVote = TestUtil.createVote(gallery.getId(), filledForm.getFilledFormId(), user.getUserId());
        oldWinnerVote.setWinner(true);
        final Vote newWinnerVote = TestUtil.createVote(gallery.getId(), filledForm.getFilledFormId(), user.getUserId());

        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site);

        String response = service.execute(gallery1.getId(), newWinnerVote.getVoteId(), manageVotes.getId(), SiteShowOption.ON_USER_PAGES);

        Assert.assertEquals("/site/render/manageVotes/winnerList.jsp", response);
        Assert.assertEquals(true, newWinnerVote.isWinner());
        Assert.assertEquals(true, oldWinnerVote.isWinner());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecuteWithoutLoginedUser() throws IOException, ServletException {
        service.execute(-1, -1, -1, SiteShowOption.ON_USER_PAGES);
    }

    @Test(expected = VoteNotFoundException.class)
    public void testExecuteWithoutVote() throws IOException, ServletException {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);

        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site);

        service.execute(gallery.getId(), -1, manageVotes.getId(), SiteShowOption.ON_USER_PAGES);
    }

}
