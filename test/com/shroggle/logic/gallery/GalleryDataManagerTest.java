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
package com.shroggle.logic.gallery;

import org.junit.Test;

/**
 * @author Balakirev Anatoliy
 *         Date: 27.08.2009
 */
public class GalleryDataManagerTest {


    @Test
    public void testInsertVotingItems() {
//        final TreeMap<Integer, GalleryColumnData> itemsByColumn = new TreeMap<Integer, GalleryColumnData>();
//
//        itemsByColumn.put(0, new GalleryColumnData());
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//        itemsByColumn.put(1, new GalleryColumnData());
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//
//        VoteSettings voteSettings = new VoteSettings();
//
//        voteSettings.setVotingStarsPosition(0);
//        voteSettings.setVotingStarsColumn(0);
//
//        voteSettings.setVotingTextLinksPosition(2);
//        voteSettings.setVotingTextLinksColumn(0);
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(2, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//        GalleryDataManager.insertVotingItems(voteSettings, itemsByColumn);
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(4, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//        Assert.assertEquals(GalleryItemType.VOTING_STARS, itemsByColumn.get(0).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(1).getItemType());
//        Assert.assertEquals(GalleryItemType.VOTING_LINKS, itemsByColumn.get(0).getItems().get(2).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(3).getItemType());
//
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(1).getItemType());
    }
//
//    @Test
//    public void testInsertVotingItems_withNegativePosition() {
//        final TreeMap<Integer, GalleryColumnData> itemsByColumn = new TreeMap<Integer, GalleryColumnData>();
//
//        itemsByColumn.put(0, new GalleryColumnData());
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//        itemsByColumn.put(1, new GalleryColumnData());
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//
//        VoteSettings voteSettings = new VoteSettings();
//
//        voteSettings.setVotingStarsPosition(-1);
//        voteSettings.setVotingStarsColumn(0);
//
//        voteSettings.setVotingTextLinksPosition(2);
//        voteSettings.setVotingTextLinksColumn(0);
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(2, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//        GalleryDataManager.insertVotingItems(voteSettings, itemsByColumn);
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(4, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//        Assert.assertEquals(GalleryItemType.VOTING_STARS, itemsByColumn.get(0).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(1).getItemType());
//        Assert.assertEquals(GalleryItemType.VOTING_LINKS, itemsByColumn.get(0).getItems().get(2).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(3).getItemType());
//
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(1).getItemType());
//    }
//
//
//    @Test
//    public void testInsertVotingItems_withoutColumn() {
//        final TreeMap<Integer, GalleryColumnData> itemsByColumn = new TreeMap<Integer, GalleryColumnData>();
//
//        itemsByColumn.put(0, new GalleryColumnData());
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//        itemsByColumn.put(1, new GalleryColumnData());
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//
//        VoteSettings voteSettings = new VoteSettings();
//
//        voteSettings.setVotingStarsPosition(0);
//        voteSettings.setVotingStarsColumn(2);
//
//        voteSettings.setVotingTextLinksPosition(2);
//        voteSettings.setVotingTextLinksColumn(2);
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(2, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//        GalleryDataManager.insertVotingItems(voteSettings, itemsByColumn);
//
//        Assert.assertEquals(3, itemsByColumn.size());
//        Assert.assertEquals(2, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(2).getItems().size());
//
//
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(1).getItemType());
//
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(1).getItemType());
//
//        Assert.assertEquals(GalleryItemType.VOTING_STARS, itemsByColumn.get(2).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.VOTING_LINKS, itemsByColumn.get(2).getItems().get(1).getItemType());
//    }
//
//    @Test
//    public void testInsertVotingItems_withoutSettings() {
//        final TreeMap<Integer, GalleryColumnData> itemsByColumn = new TreeMap<Integer, GalleryColumnData>();
//
//        itemsByColumn.put(0, new GalleryColumnData());
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//        itemsByColumn.put(1, new GalleryColumnData());
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(2, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//        GalleryDataManager.insertVotingItems(null, itemsByColumn);
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(2, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(1).getItemType());
//
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(1).getItemType());
//    }
//
//    @Test
//    public void testInsertVotingItems_withoutColumnsTree() {
//        VoteSettings voteSettings = new VoteSettings();
//        voteSettings.setVotingStarsPosition(0);
//        voteSettings.setVotingStarsColumn(2);
//        voteSettings.setVotingTextLinksPosition(2);
//        voteSettings.setVotingTextLinksColumn(2);
//        GalleryDataManager.insertVotingItems(voteSettings, null);
//    }
//
//    @Test
//    public void testInsertVotingItems_withoutSettingsAndColumnsTree() {
//        GalleryDataManager.insertVotingItems(null, null);
//    }
//
//
//    //--------------------------------------------------InsertChildSiteLink-------------------------------------------//
//    @Test
//    public void testInsertChildSiteLink() {
//        final TreeMap<Integer, GalleryColumnData> itemsByColumn = new TreeMap<Integer, GalleryColumnData>();
//
//        itemsByColumn.put(0, new GalleryColumnData());
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//        itemsByColumn.put(1, new GalleryColumnData());
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//
//        ChildSiteLink link = new ChildSiteLink();
//        link.setChildSiteLinkPosition(0);
//        link.setChildSiteLinkColumn(0);
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(2, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//        GalleryDataManager.insertChildSiteLink(link, itemsByColumn);
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(3, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//        Assert.assertEquals(GalleryItemType.CHILD_SITE_LINK, itemsByColumn.get(0).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(1).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(2).getItemType());
//
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(1).getItemType());
//    }
//
//    @Test
//    public void testInsertChildSiteLink_withNegativePosition() {
//        final TreeMap<Integer, GalleryColumnData> itemsByColumn = new TreeMap<Integer, GalleryColumnData>();
//
//        itemsByColumn.put(0, new GalleryColumnData());
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//        itemsByColumn.put(1, new GalleryColumnData());
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(2, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//        ChildSiteLink link = new ChildSiteLink();
//        link.setChildSiteLinkPosition(-1);
//        link.setChildSiteLinkColumn(0);
//
//        GalleryDataManager.insertChildSiteLink(link, itemsByColumn);
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(3, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//        Assert.assertEquals(GalleryItemType.CHILD_SITE_LINK, itemsByColumn.get(0).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(1).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(2).getItemType());
//
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(1).getItemType());
//    }
//
//
//    @Test
//    public void testInsertChildSiteLink_withoutColumn() {
//        final TreeMap<Integer, GalleryColumnData> itemsByColumn = new TreeMap<Integer, GalleryColumnData>();
//
//        itemsByColumn.put(0, new GalleryColumnData());
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//        itemsByColumn.put(1, new GalleryColumnData());
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//
//        ChildSiteLink link = new ChildSiteLink();
//        link.setChildSiteLinkPosition(2);
//        link.setChildSiteLinkColumn(2);
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(2, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//        GalleryDataManager.insertChildSiteLink(link, itemsByColumn);
//
//        Assert.assertEquals(3, itemsByColumn.size());
//        Assert.assertEquals(2, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//        Assert.assertEquals(1, itemsByColumn.get(2).getItems().size());
//
//
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(1).getItemType());
//
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(1).getItemType());
//
//        Assert.assertEquals(GalleryItemType.CHILD_SITE_LINK, itemsByColumn.get(2).getItems().get(0).getItemType());
//    }
//
//    @Test
//    public void testInsertChildSiteLink_withoutSettings() {
//        final TreeMap<Integer, GalleryColumnData> itemsByColumn = new TreeMap<Integer, GalleryColumnData>();
//
//        itemsByColumn.put(0, new GalleryColumnData());
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(0).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//        itemsByColumn.put(1, new GalleryColumnData());
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//        itemsByColumn.get(1).getItems().add(new GalleryItemData(GalleryItemType.GALLERY_DATA_ITEM));
//
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(2, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//        GalleryDataManager.insertChildSiteLink(null, itemsByColumn);
//
//        Assert.assertEquals(2, itemsByColumn.size());
//        Assert.assertEquals(2, itemsByColumn.get(0).getItems().size());
//        Assert.assertEquals(2, itemsByColumn.get(1).getItems().size());
//
//
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(0).getItems().get(1).getItemType());
//
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(0).getItemType());
//        Assert.assertEquals(GalleryItemType.GALLERY_DATA_ITEM, itemsByColumn.get(1).getItems().get(1).getItemType());
//    }
//
//    @Test
//    public void testInsertChildSiteLink_withoutColumnsTree() {
//        ChildSiteLink link = new ChildSiteLink();
//        link.setChildSiteLinkPosition(2);
//        link.setChildSiteLinkColumn(2);
//        GalleryDataManager.insertChildSiteLink(link, null);
//    }
//
//    @Test
//    public void testInsertChildSiteLink_withoutSettingsAndColumnsTree() {
//        GalleryDataManager.insertChildSiteLink(null, null);
//    }
}
