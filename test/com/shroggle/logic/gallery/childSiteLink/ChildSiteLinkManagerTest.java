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

import com.shroggle.logic.gallery.ChildSiteLinkData;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.entity.GalleryAlign;
import com.shroggle.entity.ChildSiteLink;
import com.shroggle.entity.GalleryItemColumn;
import com.shroggle.util.international.International;
import com.shroggle.util.ServiceLocator;
import com.shroggle.TestRunnerWithMockServices;
import junit.framework.Assert;

import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 *         Date: 11.09.2009
 */
@RunWith(TestRunnerWithMockServices.class)
public class ChildSiteLinkManagerTest {

    @Test
    public void testCreateChildSiteLinkData() {
        ChildSiteLinkData childSiteLinkData = new ChildSiteLinkData(
                10, GalleryAlign.RIGHT, GalleryItemColumn.COLUMN_3, "itemName", "name", true, 0);
        ChildSiteLink childSiteLink = ChildSiteLinkManager.createChildSiteLink(childSiteLinkData);
        Assert.assertNotNull(childSiteLink);
        Assert.assertEquals(childSiteLinkData.getPosition(), childSiteLink.getChildSiteLinkPosition());
        Assert.assertEquals(childSiteLinkData.getAlign(), childSiteLink.getChildSiteLinkAlign());
        Assert.assertEquals(childSiteLinkData.getColumn(), childSiteLink.getChildSiteLinkColumn());
        Assert.assertEquals(childSiteLinkData.getName(), childSiteLink.getChildSiteLinkName());
        Assert.assertEquals(childSiteLinkData.isDisplay(), childSiteLink.isShowChildSiteLink());
    }

    @Test
    public void testCreateChildSiteLinkData_withoutChildSiteLinkData() {
        ChildSiteLink childSiteLink = ChildSiteLinkManager.createChildSiteLink(null);
        Assert.assertNotNull(childSiteLink);
        Assert.assertEquals(0, childSiteLink.getChildSiteLinkPosition());
        Assert.assertEquals(GalleryAlign.CENTER, childSiteLink.getChildSiteLinkAlign());
        Assert.assertEquals(GalleryItemColumn.COLUMN_1, childSiteLink.getChildSiteLinkColumn());
        Assert.assertEquals(international.get("moreInfoAboutChildSite"), childSiteLink.getChildSiteLinkName());
        Assert.assertEquals(false, childSiteLink.isShowChildSiteLink());
    }

    @Test
    public void testCreateChildSiteLink() {
        ChildSiteLink childSiteLink = new ChildSiteLink();
        childSiteLink.setChildSiteLinkAlign(GalleryAlign.LEFT);
        childSiteLink.setChildSiteLinkColumn(GalleryItemColumn.COLUMN_3);
        childSiteLink.setChildSiteLinkName("childSiteLinkName");
        childSiteLink.setChildSiteLinkPosition(5);
        childSiteLink.setShowChildSiteLink(true);

        ChildSiteLinkManager childSiteLinkManager = new ChildSiteLinkManager(childSiteLink);
        ChildSiteLinkData childSiteLinkData = childSiteLinkManager.createChildSiteLinkData();
        Assert.assertNotNull(childSiteLinkData);
        Assert.assertEquals(GalleryAlign.LEFT, childSiteLinkData.getAlign());
        Assert.assertEquals(GalleryItemColumn.COLUMN_3, childSiteLinkData.getColumn());
        Assert.assertEquals(5, childSiteLinkData.getPosition());
        Assert.assertEquals("childSiteLinkName", childSiteLinkData.getName());
        Assert.assertEquals(true, childSiteLinkData.isDisplay());
        Assert.assertEquals(true, childSiteLinkData.isChildSiteLink());
    }

    @Test
    public void testCreateChildSiteLink_forNewGallery() {
        ChildSiteLinkManager childSiteLinkManager = new ChildSiteLinkManager(new ChildSiteLink());
        ChildSiteLinkData childSiteLinkData = childSiteLinkManager.createChildSiteLinkData();

        Assert.assertNotNull(childSiteLinkData);
        Assert.assertEquals(GalleryAlign.CENTER, childSiteLinkData.getAlign());
        Assert.assertEquals(GalleryItemColumn.COLUMN_1, childSiteLinkData.getColumn());
        Assert.assertEquals(0, childSiteLinkData.getPosition());
        Assert.assertEquals(international.get("moreInfoAboutChildSite"), childSiteLinkData.getName());
        Assert.assertEquals(false, childSiteLinkData.isDisplay());
        Assert.assertEquals(true, childSiteLinkData.isChildSiteLink());
    }

    private final International international = ServiceLocator.getInternationStorage().get("childSiteLink", Locale.US);
}
