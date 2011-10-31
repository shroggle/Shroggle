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
import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

@CachePolicy(maxElementsInMemory = 1000)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DataTransferObject
@Entity(name = "blogSummaries")
public class DraftBlogSummary extends DraftItem implements BlogSummary {

    private int numberOfWordsToDisplay = 5;
    
    @CollectionOfElements
    private List<Integer> includedCrossWidgetId;
    
    private int includedPostNumber;
    
    private boolean displayBlogSummaryHeader;
    
    private String blogSummaryHeader;
    
    private boolean showPostContents;
    
    private boolean showBlogName;
    
    private boolean showPostName;
    
    private boolean showPostAuthor;
    
    private boolean showPostDate;
    
    private boolean currentSiteBlogs;
    
    private boolean allSiteBlogs;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private PostSortCriteria postSortCriteria;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private PostDisplayCriteria postDisplayCriteria;


    public DraftBlogSummary() {
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
        displayBlogSummaryHeader = true;
    }

    public boolean isDisplayBlogSummaryHeader() {
        return displayBlogSummaryHeader;
    }

    public void setDisplayBlogSummaryHeader(boolean displayBlogSummaryHeader) {
        this.displayBlogSummaryHeader = displayBlogSummaryHeader;
    }

    @Override
    public int getNumberOfWordsToDisplay() {
        return numberOfWordsToDisplay;
    }

    public void setNumberOfWordsToDisplay(int numberOfWordsToDisplay) {
        this.numberOfWordsToDisplay = numberOfWordsToDisplay;
    }

    @Override
    public boolean isPostTitleALink() {
        return !showPostContents;
    }

    @Override
    public List<Integer> getIncludedCrossWidgetId() {
        return includedCrossWidgetId;
    }

    public void setIncludedCrossWidgetId(List<Integer> includedCrossWidgetId) {
        this.includedCrossWidgetId = includedCrossWidgetId;
    }

    @Override
    public PostSortCriteria getPostSortCriteria() {
        return postSortCriteria;
    }

    public void setPostSortCriteria(PostSortCriteria postSortCriteria) {
        this.postSortCriteria = postSortCriteria;
    }

    @Override
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

    @Override
    public int getIncludedPostNumber() {
        return includedPostNumber;
    }

    @Override
    public boolean isShowPostName() {
        return showPostName;
    }

    @Override
    public boolean isShowPostContents() {
        return showPostContents;
    }

    @Override
    public boolean isShowPostAuthor() {
        return showPostAuthor;
    }

    @Override
    public boolean isShowPostDate() {
        return showPostDate;
    }

    @Override
    public boolean isShowBlogName() {
        return showBlogName;
    }

    @Override
    public boolean isCurrentSiteBlogs() {
        return currentSiteBlogs;
    }

    @Override
    public boolean isAllSiteBlogs() {
        return allSiteBlogs;
    }

    public ItemType getItemType() {
        return ItemType.BLOG_SUMMARY;
    }

    public void setBlogSummaryHeader(String blogSummaryHeader) {
        this.blogSummaryHeader = blogSummaryHeader;
    }

    @Override
    public String getBlogSummaryHeader() {
        return blogSummaryHeader;
    }
}