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

import com.shroggle.util.cache.CachePolicy;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@CachePolicy(maxElementsInMemory = 8000)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity(name = "comments")
public class Comment {

    @Id
    private int commentId;

    @ManyToOne
    @JoinColumn(name = "blogPostId")
    @ForeignKey(name = "commentsBlogPostId")
    private BlogPost blogPost;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionComment")
    private List<Comment> answerComments = new ArrayList<Comment>();

    @ManyToOne
    @JoinColumn(name = "questionColumnId")
    @ForeignKey(name = "commentsCommentId")
    private Comment questionComment;

    @Lob
    private String text;

    @Lob
    private String draftText;

    private Integer visitorId;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }

    public void addChildComment(Comment comment) {
        comment.setQuestionComment(this);
        answerComments.add(comment);
    }

    public List<Comment> getAnswerComments() {
        return Collections.unmodifiableList(answerComments);
    }

    public Comment getParentComment() {
        return questionComment;
    }

    public void setQuestionComment(Comment questionComment) {
        this.questionComment = questionComment;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDraftText() {
        return draftText;
    }

    public void setDraftText(String draftText) {
        this.draftText = draftText;
    }

}
