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

import com.shroggle.entity.DraftBlog;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import com.shroggle.entity.SiteOnItemRightType;

/**
 * Author: dmitry.solomadin
 * Date: 04.04.2009
 */
@DataTransferObject
public class BlogInfo {
    @RemoteProperty
    private DraftBlog blog;

    @RemoteProperty
    private SiteOnItemRightType siteOnItemRightType;

    public DraftBlog getBlog() {
        return blog;
    }

    public void setBlog(DraftBlog blog) {
        this.blog = blog;
    }

    public SiteOnItemRightType getShowOnItemRightType() {
        return siteOnItemRightType;
    }

    public void setShowOnItemRightType(SiteOnItemRightType siteOnItemRightType) {
        this.siteOnItemRightType = siteOnItemRightType;
    }
}
