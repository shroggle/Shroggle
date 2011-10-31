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
package com.shroggle.presentation.forum;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import com.shroggle.entity.DraftForum;
import com.shroggle.entity.SiteOnItemRightType;

@DataTransferObject
public class ForumInfo {
    @RemoteProperty
    private DraftForum forum;

    @RemoteProperty
    private SiteOnItemRightType siteOnItemRightType;

    public DraftForum getForum() {
        return forum;
    }

    public void setForum(DraftForum forum) {
        this.forum = forum;
    }

    public SiteOnItemRightType getShowOnItemRightType() {
        return siteOnItemRightType;
    }

    public void setShowOnItemRightType(SiteOnItemRightType siteOnItemRightType) {
        this.siteOnItemRightType = siteOnItemRightType;
    }
}
