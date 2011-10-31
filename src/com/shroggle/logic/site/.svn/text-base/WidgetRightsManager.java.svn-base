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
package com.shroggle.logic.site;

import com.shroggle.entity.SiteType;
import com.shroggle.entity.Widget;
import com.shroggle.logic.site.page.PageManager;

/**
 * @author Artem Stasuk
 */
public class WidgetRightsManager {

    WidgetRightsManager(final Widget widget) {
        this.widget = widget;
        this.pageManager = new PageManager(widget.getPage());
    }

    public boolean isEditable() {
        return isCommon() || !isPageVersionLock() && widget.isBlueprintEditable();
    }

    public boolean isPageVersionLock() {
        return pageManager.isBlueprintLocked();
    }

    public boolean isRequired() {
        return !isCommon() && (isPageVersionLock() || widget.isBlueprintRequired());
    }

    public boolean isEditableRuche() {
        return isCommon() || !isPageVersionLock() && widget.isBlueprintEditRuche();
    }

    private boolean isCommon() {
        return pageManager.getPage().getSite().getType() == SiteType.COMMON;
    }

    private final Widget widget;
    private final PageManager pageManager;

}
