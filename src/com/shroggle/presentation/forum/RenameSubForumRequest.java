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

@DataTransferObject
public class RenameSubForumRequest {
    @RemoteProperty
    private String subForumName;

    @RemoteProperty
    private String subForumDescription;

    @RemoteProperty
    private int subForumId;

    public int getSubForumId() {
        return subForumId;
    }

    public void setSubForumId(int subForumId) {
        this.subForumId = subForumId;
    }

    public String getSubForumName() {
        return subForumName;
    }

    public void setSubForumName(String subForumName) {
        this.subForumName = subForumName;
    }

    public String getSubForumDescription() {
        return subForumDescription;
    }

    public void setSubForumDescription(String subForumDescription) {
        this.subForumDescription = subForumDescription;
    }
}
