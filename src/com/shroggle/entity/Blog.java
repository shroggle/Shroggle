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

/**
 * @author Artem Stasuk
 */
public interface Blog extends Item {

    AccessGroup getAddPostRight();

    void setAddPostRight(AccessGroup addPostRight);

    AccessGroup getAddCommentOnPostRight();

    void setAddCommentOnPostRight(AccessGroup addCommentOnPostRight);

    AccessGroup getAddCommentOnCommentRight();

    void setAddCommentOnCommentRight(AccessGroup addCommentOnCommentRight);

    AccessGroup getEditCommentRight();

    void setEditCommentRight(AccessGroup editCommentRight);

    AccessGroup getEditBlogPostRight();

    void setEditBlogPostRight(AccessGroup editBlogPostRight);

    boolean isDisplayAuthorEmailAddress();

    void setDisplayAuthorEmailAddress(boolean displayAuthorEmailAddress);

    boolean isDisplayAuthorScreenName();

    void setDisplayAuthorScreenName(boolean displayAuthorScreenName);

    boolean isDisplayDate();

    void setDisplayDate(boolean displayDate);

    boolean isDisplayBlogName();

    void setDisplayBlogName(boolean displayBlogName);

    boolean isDisplayNextAndPreviousLinks();

    void setDisplayNextAndPreviousLinks(boolean displayNextAndPreviousLinks);

    boolean isDisplayBackToTopLink();

    void setDisplayBackToTopLink(boolean displayBackToTopLink);

    DisplayPosts getDisplayPosts();

    void setDisplayPosts(DisplayPosts displayPosts);

    int getDisplayPostsFiniteNumber();

    void setDisplayPostsFiniteNumber(int displayPostsFiniteNumber);

    int getDisplayPostsWithinDateRange();

    void setDisplayPostsWithinDateRange(int displayPostsWithinDateRange);

}