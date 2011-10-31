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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.DraftItem;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.WorkImage;
import com.shroggle.entity.WorkItem;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.util.ServiceLocator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class ItemManagerTest {

    @Test
    public void testCreateByItem() throws Exception {
        final DraftItem draftItem = TestUtil.createDraftImage(TestUtil.createSite());
        final ItemManager manager = new ItemManager(draftItem);

        Assert.assertEquals(draftItem, manager.getDraftItem());
    }

    @Test
    public void testCreateByItemId() throws Exception {
        final DraftItem draftItem = TestUtil.createDraftImage(TestUtil.createSite());
        final ItemManager manager = new ItemManager(draftItem.getId());

        Assert.assertEquals(draftItem, manager.getDraftItem());
    }
    
    @Test
    public void testCreateByItemId_withoutItem() throws Exception {
        final ItemManager manager = new ItemManager(-1);

        Assert.assertEquals(null, manager.getDraftItem());
    }

    @Test
    public void testGetWorkItem() throws Exception {
        final DraftItem draftItem = TestUtil.createDraftImage(TestUtil.createSite());
        final WorkItem workItem = new WorkImage();
        workItem.setId(draftItem.getId());
        ServiceLocator.getPersistance().putItem(workItem);
        final ItemManager manager = new ItemManager(draftItem);

        Assert.assertEquals(workItem, manager.getWorkItem());
    }

    @Test
    public void testGetWorkItem_withoutWorkItem() throws Exception {
        final DraftItem draftItem = TestUtil.createDraftImage(TestUtil.createSite());
        final ItemManager manager = new ItemManager(draftItem);

        Assert.assertEquals(null, manager.getWorkItem());
    }

    @Test
    public void testGetItem_OUTSIDE_APP() throws Exception {
        final DraftItem draftItem = TestUtil.createDraftImage(TestUtil.createSite());
        final WorkItem workItem = new WorkImage();
        workItem.setId(draftItem.getId());
        ServiceLocator.getPersistance().putItem(workItem);
        final ItemManager manager = new ItemManager(draftItem);

        Assert.assertEquals(workItem, manager.getItem(SiteShowOption.OUTSIDE_APP));
    }

    @Test
    public void testGetItem_INSIDE_APP() throws Exception {
        final DraftItem draftItem = TestUtil.createDraftImage(TestUtil.createSite());
        final WorkItem workItem = new WorkImage();
        workItem.setId(draftItem.getId());
        ServiceLocator.getPersistance().putItem(workItem);
        final ItemManager manager = new ItemManager(draftItem);

        Assert.assertEquals(draftItem, manager.getItem(SiteShowOption.INSIDE_APP));
    }

    @Test
    public void testRemoveWorkItem() throws Exception {
        final DraftItem draftItem = TestUtil.createDraftImage(TestUtil.createSite());
        final WorkItem workItem = new WorkImage();
        workItem.setId(draftItem.getId());
        ServiceLocator.getPersistance().putItem(workItem);
        final ItemManager manager = new ItemManager(draftItem);

        Assert.assertEquals(workItem, manager.getWorkItem());
        manager.removeWorkItem();
        Assert.assertEquals(null, manager.getWorkItem());
    }
}
