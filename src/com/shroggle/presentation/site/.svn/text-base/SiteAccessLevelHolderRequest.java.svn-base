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
package com.shroggle.presentation.site;

import com.shroggle.entity.SiteAccessLevel;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

/**
 * @author Igor Kanshin
 */

@DataTransferObject
public class SiteAccessLevelHolderRequest {

    @RemoteProperty
    private int siteId;

    @RemoteProperty
    private SiteAccessLevel accessLevel;

    public SiteAccessLevelHolderRequest() {
    }

    public SiteAccessLevelHolderRequest(int siteId, SiteAccessLevel accessLevel) {
        this.siteId = siteId;
        this.accessLevel = accessLevel;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public SiteAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(SiteAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

}
