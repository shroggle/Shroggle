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

package com.shroggle.presentation.blogSummary;

import com.shroggle.entity.PostDisplayCriteria;
import com.shroggle.entity.PostSortCriteria;
import org.directwebremoting.annotations.DataTransferObject;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class CreateBlogSummaryRequest {
    
    private Integer widgetId;
    
    private String blogSummaryName;
    
    private int numberOfWordsToDisplay = 5;
    
    private String blogSummaryHeader;
    
    private List<Integer> includedCrossWidgetId;
    
    private int includedPostNumber;
    
    private int selectedBlogSummaryId;
    
    private PostSortCriteria postSortCriteria;
    
    private PostDisplayCriteria postDisplayCriteria;
    
    private boolean showPostName = false;
    
    private boolean showPostContents = false;
    
    private boolean showPostAuthor = false;

    private boolean showPostDate = false;

    private boolean showBlogName = false;

    private boolean currentSiteBlogs = false;

    private boolean allSiteBlogs = false;
    
    private boolean displayBlogSummaryHeader = false;

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

    public int getSelectedBlogSummaryId() {
        return selectedBlogSummaryId;
    }

    public void setSelectedBlogSummaryId(int selectedBlogSummaryId) {
        this.selectedBlogSummaryId = selectedBlogSummaryId;
    }

    public void setAllSiteBlogs(boolean allSiteBlogs) {
        this.allSiteBlogs = allSiteBlogs;
    }

    public void setCurrentSiteBlogs(boolean currentSiteBlogs) {
        this.currentSiteBlogs = currentSiteBlogs;
    }

    public boolean isAllSiteBlogs() {
        return allSiteBlogs;
    }

    public boolean isCurrentSiteBlogs() {
        return currentSiteBlogs;
    }

    public void setBlogSummaryName(String blogSummaryName) {
        this.blogSummaryName = blogSummaryName;
    }

    public String getBlogSummaryName() {
        return blogSummaryName;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(Integer widgetId) {
        this.widgetId = widgetId;
    }

    public void setShowBlogName(boolean showBlogName) {
        this.showBlogName = showBlogName;
    }

    public void setShowPostDate(boolean showPostDate) {
        this.showPostDate = showPostDate;
    }

    public void setShowPostAuthor(boolean showPostAuthor) {
        this.showPostAuthor = showPostAuthor;
    }

    public void setShowPostContents(boolean showPostContents) {
        this.showPostContents = showPostContents;
    }

    public void setShowPostName(boolean showPostName) {
        this.showPostName = showPostName;
    }

    public void setIncludedPostNumber(int includedPostNumber) {
        this.includedPostNumber = includedPostNumber;
    }


    public boolean isShowBlogName() {
        return showBlogName;
    }

    public boolean isShowPostDate() {
        return showPostDate;
    }

    public boolean isShowPostAuthor() {
        return showPostAuthor;
    }

    public boolean isShowPostContents() {
        return showPostContents;
    }

    public boolean isShowPostName() {
        return showPostName;
    }

    public int getIncludedPostNumber() {
        return includedPostNumber;
    }

    public List<Integer> getIncludedCrossWidgetId() {
        return includedCrossWidgetId;
    }

    public void setIncludedCrossWidgetId(List<Integer> includedCrossWidgetId) {
        this.includedCrossWidgetId = includedCrossWidgetId;
    }

    public void setBlogSummaryHeader(String blogSummaryHeader) {
        this.blogSummaryHeader = blogSummaryHeader;
    }

    public String getBlogSummaryHeader() {
        return blogSummaryHeader;
    }
}