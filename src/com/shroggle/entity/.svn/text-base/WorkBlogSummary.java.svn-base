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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

@DataTransferObject
@Entity(name = "workBlogSummaries")
public class WorkBlogSummary extends WorkItem implements BlogSummary {

    private int numberOfWordsToDisplay = 5;

    @RemoteProperty
    @CollectionOfElements
    private List<Integer> includedCrossWidgetId;

    @RemoteProperty
    private int includedPostNumber;
    @RemoteProperty
    private String blogSummaryHeader;
    @RemoteProperty
    private boolean showPostContents;
    @RemoteProperty
    private boolean showBlogName;
    @RemoteProperty
    private boolean showPostName;
    @RemoteProperty
    private boolean showPostAuthor;
    @RemoteProperty
    private boolean showPostDate;
    @RemoteProperty
    private boolean currentSiteBlogs;
    @RemoteProperty
    private boolean allSiteBlogs;
    @RemoteProperty
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private PostSortCriteria postSortCriteria;
    @RemoteProperty
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private PostDisplayCriteria postDisplayCriteria;

    private boolean displayBlogSummaryHeader;


    public WorkBlogSummary() {
        numberOfWordsToDisplay = 5;
        currentSiteBlogs = true;
        allSiteBlogs = false;
        blogSummaryHeader = "";
        setId(-1);
        setName(null);
        includedPostNumber = 1;
        postDisplayCriteria = PostDisplayCriteria.MOST_RECENT;
        postSortCriteria = PostSortCriteria.CHRONOLOGICALLY_BY_POST_DATE_DESC;
        includedCrossWidgetId = new ArrayList<Integer>();
        showPostName = true;
        showPostContents = false;
        showPostAuthor = true;
        showPostDate = true;
        showBlogName = false;
    }

    public boolean isDisplayBlogSummaryHeader() {
        return displayBlogSummaryHeader;
    }

    public void setDisplayBlogSummaryHeader(boolean displayBlogSummaryHeader) {
        this.displayBlogSummaryHeader = displayBlogSummaryHeader;
    }

    public int getNumberOfWordsToDisplay() {
        return numberOfWordsToDisplay;
    }

    public void setNumberOfWordsToDisplay(int numberOfWordsToDisplay) {
        this.numberOfWordsToDisplay = numberOfWordsToDisplay;
    }

    public boolean isPostTitleALink() {
        return !showPostContents;
    }

    public List<Integer> getIncludedCrossWidgetId() {
        return includedCrossWidgetId;
    }

    public void setIncludedCrossWidgetId(List<Integer> includedCrossWidgetId) {
        this.includedCrossWidgetId = includedCrossWidgetId;
    }

    public PostSortCriteria getPostSortCriteria() {
        return postSortCriteria;
    }

    public void setPostSortCriteria(PostSortCriteria postSortCriteria) {
        this.postSortCriteria = postSortCriteria;
    }

    public PostDisplayCriteria getPostDisplayCriteria() {
        return postDisplayCriteria;
    }

    public void setPostDisplayCriteria(PostDisplayCriteria postDisplayCriteria) {
        this.postDisplayCriteria = postDisplayCriteria;
    }

    public void setAllSiteBlogs(boolean allSiteBlogs) {
        this.allSiteBlogs = allSiteBlogs;
    }

    public void setIncludedPostNumber(int includedPostNumber) {
        this.includedPostNumber = includedPostNumber;
    }

    public void setShowPostName(boolean showPostName) {
        this.showPostName = showPostName;
    }

    public void setShowPostContents(boolean showPostContents) {
        this.showPostContents = showPostContents;
    }

    public void setShowPostAuthor(boolean showPostAuthor) {
        this.showPostAuthor = showPostAuthor;
    }

    public void setShowPostDate(boolean showPostDate) {
        this.showPostDate = showPostDate;
    }

    public void setShowBlogName(boolean showBlogName) {
        this.showBlogName = showBlogName;
    }

    public void setCurrentSiteBlogs(boolean currentSiteBlogs) {
        this.currentSiteBlogs = currentSiteBlogs;
    }

    public int getIncludedPostNumber() {
        return includedPostNumber;
    }

    public boolean isShowPostName() {
        return showPostName;
    }

    public boolean isShowPostContents() {
        return showPostContents;
    }

    public boolean isShowPostAuthor() {
        return showPostAuthor;
    }

    public boolean isShowPostDate() {
        return showPostDate;
    }

    public boolean isShowBlogName() {
        return showBlogName;
    }

    public boolean isCurrentSiteBlogs() {
        return currentSiteBlogs;
    }

    public boolean isAllSiteBlogs() {
        return allSiteBlogs;
    }

    public ItemType getItemType() {
        return ItemType.BLOG_SUMMARY;
    }

    public void setBlogSummaryHeader(String blogSummaryHeader) {
        this.blogSummaryHeader = blogSummaryHeader;
    }

    public String getBlogSummaryHeader() {
        return blogSummaryHeader;
    }
}