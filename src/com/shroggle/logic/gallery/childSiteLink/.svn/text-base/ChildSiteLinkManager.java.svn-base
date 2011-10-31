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
package com.shroggle.logic.gallery.childSiteLink;

import com.shroggle.entity.ChildSiteLink;
import com.shroggle.exception.ChildSiteLinkNotFound;
import com.shroggle.logic.gallery.ChildSiteLinkData;
import com.shroggle.util.international.International;
import com.shroggle.util.ServiceLocator;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
public class ChildSiteLinkManager {

    public ChildSiteLinkManager(ChildSiteLink childSiteLink) {
        if (childSiteLink == null) {
            throw new ChildSiteLinkNotFound("Can`t create ChildSiteLinkManager by null ChildSiteLink!");
        }
        this.childSiteLink = childSiteLink;
    }

    public ChildSiteLinkData createChildSiteLinkData() {
        final String childSiteLinkName = childSiteLink.getChildSiteLinkName() != null ?
                childSiteLink.getChildSiteLinkName() : international.get("moreInfoAboutChildSite");

        return new ChildSiteLinkData(childSiteLink.getChildSiteLinkPosition(), childSiteLink.getChildSiteLinkAlign(),
                childSiteLink.getChildSiteLinkColumn(), international.get("textLinkToChildSite"), childSiteLinkName,
                childSiteLink.isShowChildSiteLink(), childSiteLink.getChildSiteLinkRow());
    }

    public static ChildSiteLink createChildSiteLink(ChildSiteLinkData edit) {
        ChildSiteLink childSiteLink = new ChildSiteLink();
        if (edit != null) {
            childSiteLink.setChildSiteLinkRow(edit.getRow());
            childSiteLink.setChildSiteLinkAlign(edit.getAlign());
            childSiteLink.setChildSiteLinkPosition(edit.getPosition());
            childSiteLink.setChildSiteLinkColumn(edit.getColumn());
            childSiteLink.setChildSiteLinkName(edit.getName());
            childSiteLink.setShowChildSiteLink(edit.isDisplay());
        } else {
            childSiteLink.setChildSiteLinkName(ServiceLocator.getInternationStorage().get("childSiteLink", Locale.US).get("moreInfoAboutChildSite"));
        }
        return childSiteLink;
    }

    private final ChildSiteLink childSiteLink;
    private final International international = ServiceLocator.getInternationStorage().get("childSiteLink", Locale.US);
}
