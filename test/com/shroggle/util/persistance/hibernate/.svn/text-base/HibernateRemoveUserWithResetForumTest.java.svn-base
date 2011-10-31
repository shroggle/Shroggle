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
package com.shroggle.util.persistance.hibernate;

import com.shroggle.entity.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Artem Stasuk
 */
public class HibernateRemoveUserWithResetForumTest extends HibernatePersistanceTestBase {

    @Before
    public void before() {
        super.before();

        final User user1 = new User();
        user1.setEmail("a1@a");
        persistance.putUser(user1);

        final User user2 = new User();
        user2.setEmail("a2@a");
        persistance.putUser(user2);

        final DraftForum forum = new DraftForum();
        forum.setName("gg");
        persistance.putItem(forum);

        final SubForum subForum = new SubForum();
        forum.addSubForum(subForum);
        subForum.setSubForumDescription("g");
        subForum.setSubForumName("hh");
        persistance.putSubForum(subForum);

        final ForumThread forumThread1 = new ForumThread();
        subForum.addForumThread(forumThread1);
        forumThread1.setThreadName("hh1");
        forumThread1.setAuthor(user1);
        persistance.putForumThread(forumThread1);
        forumThreadId1 = forumThread1.getThreadId();

        final ForumThread forumThread2 = new ForumThread();
        forumThread2.setThreadName("22");
        subForum.addForumThread(forumThread2);
        forumThread2.setAuthor(user2);
        persistance.putForumThread(forumThread2);
        forumThreadId2 = forumThread2.getThreadId();

        final ForumPost forumPost1 = new ForumPost();
        forumThread1.addForumPost(forumPost1);
        forumPost1.setAuthor(user1);
        persistance.putForumPost(forumPost1);
        forumPostId1 = forumPost1.getForumPostId();

        final ForumPost forumPost2 = new ForumPost();
        forumThread2.addForumPost(forumPost2);
        forumPost2.setAuthor(user2);
        persistance.putForumPost(forumPost2);
        forumPostId2 = forumPost2.getForumPostId();

        userId1 = user1.getUserId();
    }

    @Test
    public void execute() {
        persistance.removeUser(persistance.getUserById(userId1));

        Assert.assertNull(persistance.getForumThreadById(forumThreadId1).getAuthor());
        Assert.assertNull(persistance.getForumPostById(forumPostId1).getAuthor());
        Assert.assertNotNull(persistance.getForumPostById(forumPostId2).getAuthor());
        Assert.assertNotNull(persistance.getForumThreadById(forumThreadId2).getAuthor());
    }

    private int userId1;
    private int forumThreadId2;
    private int forumThreadId1;
    private int forumPostId1;
    private int forumPostId2;

}