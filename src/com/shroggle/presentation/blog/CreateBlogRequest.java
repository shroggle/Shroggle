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

package com.shroggle.presentation.blog;

import com.shroggle.entity.AccessGroup;
import com.shroggle.entity.DisplayPosts;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Stasuk Artem
 */
@DataTransferObject
public class CreateBlogRequest {

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public AccessGroup getWriteComments() {
        return writeComments;
    }

    public void setWriteComments(AccessGroup writeComments) {
        this.writeComments = writeComments;
    }

    public AccessGroup getWriteCommentsOnComments() {
        return writeCommentsOnComments;
    }

    public void setWriteCommentsOnComments(AccessGroup writeCommentsOnComments) {
        this.writeCommentsOnComments = writeCommentsOnComments;
    }

    public AccessGroup getWritePosts() {
        return writePosts;
    }

    public void setWritePosts(AccessGroup writePosts) {
        this.writePosts = writePosts;
    }

    public int getSiteId() {
        return siteId;
    }

    public AccessGroup getEditBlogPostRight() {
        return editBlogPostRight;
    }

    public void setEditBlogPostRight(AccessGroup editBlogPostRight) {
        this.editBlogPostRight = editBlogPostRight;
    }

    public void setSiteId(final int siteId) {
        this.siteId = siteId;
    }

    public AccessGroup getEditCommentRight() {
        return editCommentRight;
    }

    public void setEditCommentRight(AccessGroup editCommentRight) {
        this.editCommentRight = editCommentRight;
    }

    public boolean isDisplayAuthorEmailAddress() {
        return displayAuthorEmailAddress;
    }

    public void setDisplayAuthorEmailAddress(boolean displayAuthorEmailAddress) {
        this.displayAuthorEmailAddress = displayAuthorEmailAddress;
    }

    public boolean isDisplayAuthorScreenName() {
        return displayAuthorScreenName;
    }

    public void setDisplayAuthorScreenName(boolean displayAuthorScreenName) {
        this.displayAuthorScreenName = displayAuthorScreenName;
    }

    public boolean isDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(boolean displayDate) {
        this.displayDate = displayDate;
    }

    public boolean isDisplayBlogName() {
        return displayBlogName;
    }

    public void setDisplayBlogName(boolean displayBlogName) {
        this.displayBlogName = displayBlogName;
    }

    public boolean isDisplayNextAndPreviousLinks() {
        return displayNextAndPreviousLinks;
    }

    public void setDisplayNextAndPreviousLinks(boolean displayNextAndPreviousLinks) {
        this.displayNextAndPreviousLinks = displayNextAndPreviousLinks;
    }

    public boolean isDisplayBackToTopLink() {
        return displayBackToTopLink;
    }

    public void setDisplayBackToTopLink(boolean displayBackToTopLink) {
        this.displayBackToTopLink = displayBackToTopLink;
    }

    public DisplayPosts getDisplayPosts() {
        return displayPosts;
    }

    public void setDisplayPosts(DisplayPosts displayPosts) {
        this.displayPosts = displayPosts;
    }

    public int getDisplayPostsFiniteNumber() {
        return displayPostsFiniteNumber;
    }

    public void setDisplayPostsFiniteNumber(int displayPostsFiniteNumber) {
        this.displayPostsFiniteNumber = displayPostsFiniteNumber;
    }

    public int getDisplayPostsWithinDateRange() {
        return displayPostsWithinDateRange;
    }

    public void setDisplayPostsWithinDateRange(int displayPostsWithinDateRange) {
        this.displayPostsWithinDateRange = displayPostsWithinDateRange;
    }

    @RemoteProperty
    private int siteId;

    @RemoteProperty
    private String blogName;

    @RemoteProperty
    private AccessGroup writePosts;

    @RemoteProperty
    private AccessGroup writeComments;

    @RemoteProperty
    private AccessGroup editCommentRight;

    @RemoteProperty
    private AccessGroup editBlogPostRight;

    @RemoteProperty
    private AccessGroup writeCommentsOnComments;

    @RemoteProperty
    private boolean displayAuthorEmailAddress;

    @RemoteProperty
    private boolean displayAuthorScreenName;

    @RemoteProperty
    private boolean displayDate;

    @RemoteProperty
    private boolean displayBlogName;

    @RemoteProperty
    private boolean displayNextAndPreviousLinks;

    @RemoteProperty
    private boolean displayBackToTopLink;

    @RemoteProperty
    private DisplayPosts displayPosts;

    @RemoteProperty
    private int displayPostsFiniteNumber;

    @RemoteProperty
    private int displayPostsWithinDateRange;
}