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

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;

import java.util.*;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class VoteDataManagerTest {
    
    @Test
    public void testCreateVoteDatasByFilledFormIds() {
        Calendar calendarStartDate = new GregorianCalendar(2009, 10, 10);
        Calendar calendarEndDate = new GregorianCalendar(2009, 10, 20);
        User user = TestUtil.createUser();
        Site site = TestUtil.createSite();
        DraftGallery gallery = TestUtil.createGallery(site);
        final VoteSettings voteSettings = new VoteSettings();
        voteSettings.setStartDate(calendarStartDate.getTime());
        voteSettings.setEndDate(calendarEndDate.getTime());
        gallery.setVoteSettings(voteSettings);

        Vote vote1 = TestUtil.createVote(gallery.getId(), 1, user.getUserId());
        vote1.setStartDate(voteSettings.getStartDate());
        vote1.setEndDate(voteSettings.getEndDate());
        vote1.setVoteValue(4);
        vote1.setWinner(true);

        Vote vote2 = TestUtil.createVote(gallery.getId(), 2, user.getUserId());
        vote2.setVoteDate(new GregorianCalendar(2009, 10, 15).getTime());
        vote2.setStartDate(null);
        vote2.setEndDate(null);
        vote2.setVoteValue(5);
        vote2.setWinner(false);

        Vote vote3 = TestUtil.createVote(gallery.getId(), 2, user.getUserId());
        vote3.setVoteDate(new GregorianCalendar(2009, 10, 20).getTime());
        vote3.setStartDate(null);
        vote3.setEndDate(null);
        vote3.setVoteValue(1);
        vote3.setWinner(false);

        VoteDataManager voteDataManager = new VoteDataManager(gallery, user);
        final List<VoteData> voteDatas = voteDataManager.createVoteDataList(new ArrayList<Integer>(Arrays.asList(1, 2, 3)));
        Assert.assertEquals(3, voteDatas.size());

        Assert.assertEquals(vote1.getVoteId(), voteDatas.get(0).getVoteId());
        Assert.assertEquals(vote1.getVoteValue(), voteDatas.get(0).getVoteValue());
        Assert.assertEquals(vote1.getFilledFormId(), voteDatas.get(0).getFilledFormId());
        Assert.assertEquals(vote1.isWinner(), voteDatas.get(0).isWinner());

        Assert.assertEquals(0, voteDatas.get(1).getVoteId());
        Assert.assertEquals(3, voteDatas.get(1).getVoteValue());
        Assert.assertEquals(vote2.getFilledFormId(), voteDatas.get(1).getFilledFormId());
        Assert.assertEquals(false, voteDatas.get(1).isWinner());

        Assert.assertEquals(0, voteDatas.get(2).getVoteId());
        Assert.assertEquals(0, voteDatas.get(2).getVoteValue());
        Assert.assertEquals(3, voteDatas.get(2).getFilledFormId());
        Assert.assertEquals(false, voteDatas.get(2).isWinner());
    }
}
