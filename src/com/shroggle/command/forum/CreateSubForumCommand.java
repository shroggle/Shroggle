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

package com.shroggle.command.forum;

import com.shroggle.entity.DraftForum;
import com.shroggle.entity.SubForum;
import com.shroggle.exception.ForumNotFoundException;
import com.shroggle.exception.SubForumWithNullOrEmptyDescriptionException;
import com.shroggle.exception.SubForumWithNullOrEmptyNameException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;

import java.util.Date;

public class CreateSubForumCommand {

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private String subForumName;
    private String subForumDescription;
    private int forumId;

    public void execute() {
        if (subForumName == null || subForumName.trim().isEmpty()) {
            throw new SubForumWithNullOrEmptyNameException();
        }

        if (subForumDescription == null || subForumDescription.trim().isEmpty()) {
            throw new SubForumWithNullOrEmptyDescriptionException();
        }

        final DraftForum forum = persistance.getDraftItem(forumId);

        if (forum == null) {
            throw new ForumNotFoundException("Cannot find forum by Id=" + forumId);
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                final SubForum subForum = new SubForum();
                subForum.setSubForumName(subForumName);
                subForum.setSubForumDescription(subForumDescription);
                subForum.setForum(forum);
                subForum.setDateCreated(new Date());

                persistance.putSubForum(subForum);
            }

        });

    }

    public String getSubForumName() {
        return subForumName;
    }

    public void setSubForumName(String subForumName) {
        this.subForumName = subForumName;
    }

    public String getSubForumDescription() {
        return subForumDescription;
    }

    public void setSubForumDescription(String subForumDescription) {
        this.subForumDescription = subForumDescription;
    }

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }
}
