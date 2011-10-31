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

package com.shroggle.logic.forum;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ForumRightsManagerTest extends TestCase {

    @Test
    public void testIsShouldShowRegisterLinks_withoutUser() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftForum forum = TestUtil.createForum(site);
        forum.setCreatePollRight(AccessGroup.GUEST);

        Assert.assertTrue(ForumRightsManager.isShouldShowRegisterLinks(null, forum));
    }

    @Test
    public void testIsShouldShowRegisterLinks_withoutUserButWithViewAllForum() throws Exception {
        final Site site = TestUtil.createSite();
        final DraftForum forum = TestUtil.createForum(site);
        forum.setCreatePollRight(AccessGroup.ALL);
        forum.setCreateSubForumRight(AccessGroup.ALL);
        forum.setCreatePostRight(AccessGroup.ALL);
        forum.setCreateThreadRight(AccessGroup.ALL);
        forum.setManagePostsRight(AccessGroup.ALL);
        forum.setManageSubForumsRight(AccessGroup.ALL);
        forum.setVoteInPollRight(AccessGroup.ALL);

        Assert.assertFalse(ForumRightsManager.isShouldShowRegisterLinks(null, forum));
    }

    @Test
    public void testIsShouldShowRegisterLinks_withUser() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final DraftForum forum = TestUtil.createForum(site);
        forum.setCreatePollRight(AccessGroup.GUEST);

        Assert.assertFalse(ForumRightsManager.isShouldShowRegisterLinks(user, forum));
    }

    @Test
    public void testIsShouldShowRegisterLinks_withUserAndViewAllForum() throws Exception {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite();
        final DraftForum forum = TestUtil.createForum(site);
        forum.setCreatePollRight(AccessGroup.ALL);
        forum.setCreateSubForumRight(AccessGroup.ALL);
        forum.setCreatePostRight(AccessGroup.ALL);
        forum.setCreateThreadRight(AccessGroup.ALL);
        forum.setManagePostsRight(AccessGroup.ALL);
        forum.setManageSubForumsRight(AccessGroup.ALL);
        forum.setVoteInPollRight(AccessGroup.ALL);

        Assert.assertFalse(ForumRightsManager.isShouldShowRegisterLinks(user, forum));
    }    
    
}
