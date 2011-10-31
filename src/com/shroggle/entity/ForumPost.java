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
import java.util.Date;

@Entity(name = "forumPosts")
@org.hibernate.annotations.Table(
        appliesTo = "forumPosts",
        indexes = {
                @org.hibernate.annotations.Index(
                        name = "dateCreatedIndex",
                        columnNames = {"dateCreated"}
                ),
                @org.hibernate.annotations.Index(
                        name = "threadIdIndex",
                        columnNames = {"threadId"}
                )
        }
)
public class ForumPost implements java.lang.Comparable<ForumPost> {

    @Id
    private int forumPostId;

    @ManyToOne
    @JoinColumn(name = "threadId", nullable = false)
    @ForeignKey(name = "forumPostsForumThreadId")
    private ForumThread thread;

    @OneToOne
    @JoinColumn(name = "visitorId")
    @ForeignKey(name = "forumPostsUserId")
    private User author;

    @OneToOne
    @ForeignKey(name = "forumPostsPageVisitorId")
    private PageVisitor pageVisitor;

    @Lob
    private String text;

    @Lob
    private String draftText;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated = new Date();

    public int getForumPostId() {
        return forumPostId;
    }

    public void setForumPostId(int forumPostId) {
        this.forumPostId = forumPostId;
    }

    public ForumThread getThread() {
        return thread;
    }

    public void setThread(ForumThread thread) {
        this.thread = thread;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int compareTo(ForumPost forumPost) {
        Date thisDate = new Date();
        thisDate.setTime(this.dateCreated.getTime());
        return thisDate.compareTo(forumPost.dateCreated);
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getDraftText() {
        return draftText;
    }

    public void setDraftText(String draftText) {
        this.draftText = draftText;
    }

    public PageVisitor getPageVisitor() {
        return pageVisitor;
    }

    public void setPageVisitor(PageVisitor pageVisitor) {
        this.pageVisitor = pageVisitor;
    }
}
