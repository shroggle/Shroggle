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

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.exception.VoteNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.*;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.presentation.manageVotes.RemoveWinnerService;
import net.sourceforge.stripes.mock.MockHttpServletRequest;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class RemoveWinnerServiceTest {

    final RemoveWinnerService service = new RemoveWinnerService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute() throws IOException, ServletException {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final DraftGallery gallery = TestUtil.createGallery(site);
        final DraftForm form = TestUtil.createCustomForm(site);
        final FilledForm filledForm = TestUtil.createFilledForm(form);

        final Vote vote = TestUtil.createVote(gallery.getId(), filledForm.getFilledFormId(), user.getUserId());
        vote.setWinner(true);
        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site);

        final String response = service.execute(vote.getVoteId(), manageVotes.getId(), SiteShowOption.ON_USER_PAGES);

        Assert.assertEquals("/site/render/manageVotes/winnerList.jsp", response);
        Assert.assertEquals(false, ServiceLocator.getPersistance().getVoteById(vote.getVoteId()).isWinner());
    }

    @Test(expected = VoteNotFoundException.class)
    public void testExecuteWithoutVote() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        final DraftManageVotes manageVotes = TestUtil.createManageVotes(site);

        service.execute(-1, manageVotes.getId(), SiteShowOption.ON_USER_PAGES);
    }

}
