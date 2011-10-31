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
import java.util.*;

@CachePolicy(maxElementsInMemory = 4000)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity(name = "blogPosts")
@org.hibernate.annotations.Table(
        appliesTo = "blogPosts",
        indexes = {
                @org.hibernate.annotations.Index(
                        name = "creationDateIndex",
                        columnNames = {"creationDate"}
                ),
                @org.hibernate.annotations.Index(
                        name = "blogIdIndex",
                        columnNames = {"blogId"}
                )
        }
)
public class BlogPost {

    @Id
    private int blogPostId;


    // I change updatable from "false" to "true" because of:
    // http://jira.web-deva.com/browse/SW-3877
    // Tolik
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    private Integer visitorId;

    @Column(length = 250)
    private String postTitle;

    private int postRead;

    @ForeignKey(name = "blogPostsBlogId")
    @ManyToOne
    @JoinColumn(name = "blogId", nullable = false)
    private DraftBlog blog;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "blogPost")
    private List<Comment> comments = new ArrayList<Comment>();

    @Lob
    private String text;

    @Lob
    private String draftText;

    public int getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(int blogPostId) {
        this.blogPostId = blogPostId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    public DraftBlog getBlog() {
        return blog;
    }

    public void setBlog(DraftBlog blog) {
        this.blog = blog;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public List<Comment> getPostedToWorkComments() {
        List<Comment> tempList = new ArrayList<Comment>();
        for (Comment comment : comments) {
            if (comment.getText() != null)
                tempList.add(comment);
        }
        return Collections.unmodifiableList(tempList);
    }

    public void addComment(Comment comment) {
        comment.setBlogPost(this);
        comments.add(comment);
    }

    public String getDraftText() {
        return draftText;
    }

    public void setDraftText(String draftText) {
        this.draftText = draftText;
    }

    public void setVisitorId(final Integer visitorId) {
        this.visitorId = visitorId;
    }

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setPostRead(int postRead) {
        this.postRead = postRead;
    }

    public int getPostRead() {
        return postRead;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostTitle() {
        return postTitle;
    }

}
