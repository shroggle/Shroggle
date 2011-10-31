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
import org.directwebremoting.annotations.RemoteProperty;
import org.directwebremoting.annotations.DataTransferObject;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
@Entity(name = "galleryComments")
public class GalleryComment {

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DraftGallery getGallery() {
        return gallery;
    }

    public void setGallery(DraftGallery gallery) {
        this.gallery = gallery;
    }

    public FilledForm getFilledForm() {
        return filledForm;
    }

    public void setFilledForm(FilledForm filledForm) {
        this.filledForm = filledForm;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    @RemoteProperty
    @Lob
    private String text;

    private Integer userId;

    private String userEmail;

    @ManyToOne
    @ForeignKey(name = "galleryCommentsFilledFormId")
    @JoinColumn(name = "filledFormId", nullable = false)
    private FilledForm filledForm;

    @ManyToOne
    @ForeignKey(name = "galleryCommentsGalleryId")
    @JoinColumn(name = "galleryId", nullable = false)
    private DraftGallery gallery;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created = new Date();

    @RemoteProperty
    @Id
    private int commentId;

}
