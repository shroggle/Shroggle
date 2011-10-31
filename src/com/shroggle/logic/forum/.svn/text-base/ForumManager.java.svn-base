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

import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.ForumThread;
import com.shroggle.entity.User;
import com.shroggle.entity.ForumPost;

import java.util.List;

/**
 * Author: dmitry.solomadin
 */
public class ForumManager {
    private Persistance persistance = ServiceLocator.getPersistance();

    public void cleanVisitorForumActivity(final User user){
        final List<ForumThread> forumThreadsToClean = persistance.getForumThreadsByUserId(user.getUserId());

        for (ForumThread forumThread : forumThreadsToClean){
            forumThread.setAuthor(null);
        }

        final List<ForumPost> forumPostsToClean = persistance.getForumPostsByUserId(user.getUserId());

        for (ForumPost forumPost : forumPostsToClean){
            forumPost.setAuthor(null);
        }
    }
}
