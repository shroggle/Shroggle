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

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@DataTransferObject
@Entity(name = "workBlogs")
public class WorkBlog extends WorkItem implements Blog {

    @Override
    public ItemType getItemType() {
        return ItemType.BLOG;
    }

    @Override
    public AccessGroup getAddPostRight() {
        return addPostRight;
    }

    @Override
    public void setAddPostRight(AccessGroup addPostRight) {
        this.addPostRight = addPostRight;
    }

    @Override
    public AccessGroup getAddCommentOnPostRight() {
        return addCommentOnPostRight;
    }

    @Override
    public void setAddCommentOnPostRight(AccessGroup addCommentOnPostRight) {
        this.addCommentOnPostRight = addCommentOnPostRight;
    }

    @Override
    public AccessGroup getAddCommentOnCommentRight() {
        return addCommentOnCommentRight;
    }

    @Override
    public void setAddCommentOnCommentRight(AccessGroup addCommentOnCommentRight) {
        this.addCommentOnCommentRight = addCommentOnCommentRight;
    }

    @Override
    public AccessGroup getEditCommentRight() {
        return editCommentRight;
    }

    @Override
    public void setEditCommentRight(AccessGroup editCommentRight) {
        this.editCommentRight = editCommentRight;
    }

    @Override
    public AccessGroup getEditBlogPostRight() {
        return editBlogPostRight;
    }

    @Override
    public void setEditBlogPostRight(AccessGroup editBlogPostRight) {
        this.editBlogPostRight = editBlogPostRight;
    }

    @Override
    public boolean isDisplayAuthorEmailAddress() {
        return displayAuthorEmailAddress;
    }

    @Override
    public void setDisplayAuthorEmailAddress(boolean displayAuthorEmailAddress) {
        this.displayAuthorEmailAddress = displayAuthorEmailAddress;
    }

    @Override
    public boolean isDisplayAuthorScreenName() {
        return displayAuthorScreenName;
    }

    @Override
    public void setDisplayAuthorScreenName(boolean displayAuthorScreenName) {
        this.displayAuthorScreenName = displayAuthorScreenName;
    }

    @Override
    public boolean isDisplayDate() {
        return displayDate;
    }

    @Override
    public void setDisplayDate(boolean displayDate) {
        this.displayDate = displayDate;
    }

    @Override
    public boolean isDisplayBlogName() {
        return displayBlogName;
    }

    @Override
    public void setDisplayBlogName(boolean displayBlogName) {
        this.displayBlogName = displayBlogName;
    }

    @Override
    public boolean isDisplayNextAndPreviousLinks() {
        return displayNextAndPreviousLinks;
    }

    @Override
    public void setDisplayNextAndPreviousLinks(boolean displayNextAndPreviousLinks) {
        this.displayNextAndPreviousLinks = displayNextAndPreviousLinks;
    }

    @Override
    public boolean isDisplayBackToTopLink() {
        return displayBackToTopLink;
    }

    @Override
    public void setDisplayBackToTopLink(boolean displayBackToTopLink) {
        this.displayBackToTopLink = displayBackToTopLink;
    }

    @Override
    public DisplayPosts getDisplayPosts() {
        return displayPosts;
    }

    @Override
    public void setDisplayPosts(DisplayPosts displayPosts) {
        this.displayPosts = displayPosts;
    }

    @Override
    public int getDisplayPostsFiniteNumber() {
        return displayPostsFiniteNumber;
    }

    @Override
    public void setDisplayPostsFiniteNumber(int displayPostsFiniteNumber) {
        this.displayPostsFiniteNumber = displayPostsFiniteNumber;
    }

    @Override
    public int getDisplayPostsWithinDateRange() {
        return displayPostsWithinDateRange;
    }

    @Override
    public void setDisplayPostsWithinDateRange(int displayPostsWithinDateRange) {
        this.displayPostsWithinDateRange = displayPostsWithinDateRange;
    }

    @RemoteProperty
    @Enumerated
    private AccessGroup addPostRight = AccessGroup.OWNER;

    @RemoteProperty
    @Enumerated
    private AccessGroup addCommentOnPostRight = AccessGroup.VISITORS;

    @RemoteProperty
    @Enumerated
    private AccessGroup editBlogPostRight = AccessGroup.VISITORS;

    @RemoteProperty
    @Enumerated
    private AccessGroup editCommentRight = AccessGroup.OWNER;

    @RemoteProperty
    @Enumerated
    private AccessGroup addCommentOnCommentRight = AccessGroup.VISITORS;

    private boolean displayAuthorEmailAddress;

    private boolean displayAuthorScreenName;

    private boolean displayDate;

    private boolean displayBlogName;

    private boolean displayNextAndPreviousLinks;

    private boolean displayBackToTopLink;

    @Enumerated(value = EnumType.STRING)
    private DisplayPosts displayPosts = DisplayPosts.DISPLAY_FINITE_NUMBER;

    private int displayPostsFiniteNumber = 10;

    private int displayPostsWithinDateRange = 1;
}