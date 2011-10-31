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

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "forumThreads")
@org.hibernate.annotations.Table(
        appliesTo = "forumThreads",
        indexes = {
                @org.hibernate.annotations.Index(
                        name = "subForumIdIndex",
                        columnNames = {"subForumId"}
                )
        }
)
public class ForumThread {

    @Id
    private int threadId;

    @ManyToOne
    @JoinColumn(name = "subForumId", nullable = false)
    @ForeignKey(name = "forumThreadsSubForumId")
    private SubForum subForum;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thread")
    private List<ForumPost> forumPosts = new ArrayList<ForumPost>();

    @CollectionOfElements
    private List<String> pollAnswers = new ArrayList<String>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "thread")
    private List<ForumPollVote> pollVotes = new ArrayList<ForumPollVote>();

    @Lob
    @Column
    private String pollQuestion;

    @Lob
    @Column(nullable = false)
    private String threadName;

    @Lob
    @Column
    private String threadDescription;

    @OneToOne
    @JoinColumn(name = "visitorId")
    @ForeignKey(name = "forumThreadsPageVisitorId")
    private User author;

    @Column
    private boolean closed;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate = new Date();

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public SubForum getSubForum() {
        return subForum;
    }

    public void setSubForum(SubForum subForum) {
        this.subForum = subForum;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public List<ForumPost> getForumPosts() {
        return forumPosts;
    }

    public void addForumPost(ForumPost forumPost) {
        forumPost.setThread(this);
        forumPosts.add(forumPost);
    }

    public String getThreadDescription() {
        return threadDescription;
    }

    public void setThreadDescription(String threadDescription) {
        this.threadDescription = threadDescription;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getPollQuestion() {
        return pollQuestion;
    }

    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    public List<String> getPollAnswers() {
        return pollAnswers;
    }

    public void addPollAnswer(String pollAnswer) {
        pollAnswers.add(pollAnswer);
    }

    public void setPollAnswers(List<String> pollAnswers) {
        this.pollAnswers = pollAnswers;
    }

    public List<ForumPollVote> getPollVotes() {
        return pollVotes;
    }

    public void addPollVote(ForumPollVote pollVote) {
        pollVotes.add(pollVote);
    }

    public void removeForumPost(ForumPost forumPost) {
        forumPosts.remove(forumPost);
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
