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

import com.shroggle.entity.*;

/**
 * @author dmitry.solomadin
 */
public class ForumRightsManager {

    public static boolean isShouldShowRegisterLinks(final User user, final Forum forum) {
        return user == null && (forum.getCreatePollRight() != AccessGroup.ALL ||
                forum.getCreatePostRight() != AccessGroup.ALL ||
                forum.getCreateSubForumRight() != AccessGroup.ALL ||
                forum.getCreateThreadRight() != AccessGroup.ALL ||
                forum.getManagePostsRight() != AccessGroup.ALL ||
                forum.getManageSubForumsRight() != AccessGroup.ALL ||
                forum.getVoteInPollRight() != AccessGroup.ALL);
    }

}
