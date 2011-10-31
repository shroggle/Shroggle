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

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Artem Stasuk
 */
@DataTransferObject
public class CopyPageRequest {

    public Map<Integer, CopyPageItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CopyPageItem> items) {
        this.items = items;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    @RemoteProperty
    private int pageId;

    @RemoteProperty
    private Map<Integer, CopyPageItem> items = new HashMap<Integer, CopyPageItem>();

}
