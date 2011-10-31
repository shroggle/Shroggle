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

package com.shroggle.entity;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "subforums")
@org.hibernate.annotations.Table(
        appliesTo = "subforums",
        indexes = {
                @org.hibernate.annotations.Index(
                        name = "forumIdIndex",
                        columnNames = {"forumId"}
                )
        }
)
public class SubForum {

    @Id
    private int subForumId;

    @ManyToOne
    @ForeignKey(name = "subForumsForumId")
    @JoinColumn(name = "forumId", nullable = false)
    private DraftForum forum;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subForum")
    private List<ForumThread> threads = new ArrayList<ForumThread>();

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Lob
    @Column(nullable = false)
    private String subForumName;

    @Lob
    @Column(nullable = false)
    private String subForumDescription;

    public int getSubForumId() {
        return subForumId;
    }

    public void setSubForumId(int subForumId) {
        this.subForumId = subForumId;
    }

    public String getSubForumName() {
        return subForumName;
    }

    public void setSubForumName(String subForumName) {
        this.subForumName = subForumName;
    }

    public DraftForum getForum() {
        return forum;
    }

    public void setForum(DraftForum forum) {
        this.forum = forum;
    }

    public List<ForumThread> getForumThreads() {
        return threads;
    }

    public void addForumThread(ForumThread thread) {
        thread.setSubForum(this);
        threads.add(thread);
    }

    public String getSubForumDescription() {
        return subForumDescription;
    }

    public void setSubForumDescription(String subForumDescription) {
        this.subForumDescription = subForumDescription;
    }

    public void removeForumThread(ForumThread forumThread) {
        threads.remove(forumThread);
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
