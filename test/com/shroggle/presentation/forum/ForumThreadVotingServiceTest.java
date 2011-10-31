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

package com.shroggle.presentation.forum;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.entity.DraftForum;
import com.shroggle.entity.ForumThread;
import com.shroggle.entity.SubForum;
import com.shroggle.exception.CannotFindAnswerException;
import com.shroggle.exception.CannotFindThreadException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(TestRunnerWithMockServices.class)
public class ForumThreadVotingServiceTest {

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        httpServletRequest = new MockHttpServletRequest("", "");
        mockWebContext.setHttpServletRequest(httpServletRequest);
    }

    @Test(expected = CannotFindThreadException.class)
    public void executeWithoutThreadId() throws Exception {
        final DraftForum forum = new DraftForum();
        forum.setName("My forum");
        persistance.putItem(forum);

        final SubForum subForum = new SubForum();
        subForum.setSubForumName("Subforum Name");
        subForum.setSubForumDescription("Subforum Description");
        persistance.putSubForum(subForum);

        final ForumThread forumThread = new ForumThread();
        forumThread.setThreadName("Name");
        forumThread.setThreadDescription("Desc");
        forumThread.setSubForum(subForum);
        forumThread.setClosed(false);
        forumThread.setPollQuestion("question");
        List<String> answers = new ArrayList<String>();
        answers.add("ans1");
        answers.add("ans2");
        forumThread.setPollAnswers(answers);
        persistance.putForumThread(forumThread);

        service.execute(0, 0, -1, false);
    }

    @Test(expected = CannotFindAnswerException.class)
    public void executeWithoutProperlySetAnswerNumber() throws Exception {

        final DraftForum forum = new DraftForum();
        forum.setName("My forum");
        persistance.putItem(forum);

        final SubForum subForum = new SubForum();
        subForum.setSubForumName("Subforum Name");
        subForum.setSubForumDescription("Subforum Description");
        persistance.putSubForum(subForum);

        final ForumThread forumThread = new ForumThread();
        forumThread.setThreadName("Name");
        forumThread.setThreadDescription("Desc");
        forumThread.setSubForum(subForum);
        forumThread.setClosed(false);
        forumThread.setPollQuestion("question");
        List<String> answers = new ArrayList<String>();
        answers.add("ans1");
        answers.add("ans2");
        forumThread.setPollAnswers(answers);
        persistance.putForumThread(forumThread);

        service.execute(forumThread.getThreadId(), 3, -1, false);
    }

    @Test
    public void executeWithCorrectData() throws Exception {
        final DraftForum forum = new DraftForum();
        forum.setName("My forum");
        persistance.putItem(forum);

        final SubForum subForum = new SubForum();
        subForum.setSubForumName("Subforum Name");
        subForum.setSubForumDescription("Subforum Description");
        persistance.putSubForum(subForum);

        final ForumThread forumThread = new ForumThread();
        forumThread.setThreadName("Name");
        forumThread.setThreadDescription("Desc");
        forumThread.setSubForum(subForum);
        forumThread.setClosed(false);
        forumThread.setPollQuestion("question");
        List<String> answers = new ArrayList<String>();
        answers.add("ans1");
        answers.add("ans2");
        forumThread.setPollAnswers(answers);
        persistance.putForumThread(forumThread);

        service.execute(forumThread.getThreadId(), 2, -1, false);
        Assert.assertEquals(forumThread.getPollVotes().size(), 1);
        Assert.assertEquals(forumThread.getPollVotes().get(0).getRespondentId(), httpServletRequest.getRemoteAddr());
        Assert.assertEquals(forumThread.getPollVotes().get(0).getAnswerNumber(), (Integer) 2);
    }

    private MockHttpServletRequest httpServletRequest;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ForumThreadVotingService service = new ForumThreadVotingService();

}
