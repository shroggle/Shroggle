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
import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collections;

/**
 * @author dmitry.solomadin
 */
@CachePolicy(maxElementsInMemory = 10000)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DataTransferObject
@Entity(name = "filledForms")
@org.hibernate.annotations.Table(
        appliesTo = "filledForms",
        indexes = {
                @org.hibernate.annotations.Index(
                        name = "formIdIndex",
                        columnNames = {"formId"}
                ),
                @org.hibernate.annotations.Index(
                        name = "fillDateIndex",
                        columnNames = {"fillDate"}
                )
        }
)
public class FilledForm {

    @Id
    private int filledFormId;

    private int formId;

    @Column(nullable = false, length = 30)
    @Enumerated(value = EnumType.STRING)
    private FormType type;

    @ManyToOne
    @ForeignKey(name = "filledFormsPageVisitorId")
    private PageVisitor pageVisitor;

    @ManyToOne
    @JoinColumn(name = "userId")
    @ForeignKey(name = "filledFormsUserId")
    private User user;

    @OneToMany(mappedBy = "filledForm", cascade = CascadeType.ALL)
    private List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date fillDate = new Date();

    @Lob
    @Column(nullable = false)
    private String formDescription;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedDate;

    private boolean hidden;

    private boolean networkRegistration;

    //We need link between filled child site registration form and child site settings. Tolik
    private Integer childSiteSettingsId;

    @OneToMany(mappedBy = "filledForm", cascade = CascadeType.ALL)
    private List<GalleryComment> comments = new ArrayList<GalleryComment>();

    public boolean isNetworkRegistration() {
        return networkRegistration;
    }

    public void setNetworkRegistration(boolean networkRegistration) {
        this.networkRegistration = networkRegistration;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getFormDescription() {
        return formDescription;
    }

    public void setFormDescription(String formDescription) {
        this.formDescription = formDescription;
    }

    public Integer getChildSiteSettingsId() {
        return childSiteSettingsId;
    }

    public void setChildSiteSettingsId(Integer childSiteSettingsId) {
        this.childSiteSettingsId = childSiteSettingsId;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getFillDate() {
        return fillDate;
    }

    public void setFillDate(Date fillDate) {
        this.fillDate = fillDate;
    }

    public FormType getType() {
        return type;
    }

    public void setType(FormType type) {
        this.type = type;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public PageVisitor getPageVisitor() {
        return pageVisitor;
    }

    public void setPageVisitor(PageVisitor pageVisitor) {
        this.pageVisitor = pageVisitor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(int filledFormId) {
        this.filledFormId = filledFormId;
    }

    public void addFilledFormItem(FilledFormItem filledFormItem) {
        this.filledFormItems.add(filledFormItem);
        filledFormItem.setFilledForm(this);
    }

    public void removeFilledFormItem(FilledFormItem filledFormItem) {
        this.filledFormItems.remove(filledFormItem);
    }

    public List<FilledFormItem> getFilledFormItems() {
        return filledFormItems;
    }

    public void setFilledFormItems(List<FilledFormItem> filledFormItems) {
        this.filledFormItems = filledFormItems;
    }

    @Override
    public String toString() {
        return "FilledForm [id: " + filledFormId + ", formId: " + formId + "]";
    }

    public List<GalleryComment> getComments() {
        return Collections.unmodifiableList(comments);
    }

    public void addComment(final GalleryComment comment) {
        comment.setFilledForm(this);
        comments.add(comment);
    }

    public Integer getValueHashCode() {
        int hashCode = 1;
        for (FilledFormItem item : getFilledFormItems()) {
            for (String value : item.getValues()) {
                hashCode = 31 * hashCode + value.hashCode();
            }
        }
        return 31 * hashCode + filledFormId;
    }

}
