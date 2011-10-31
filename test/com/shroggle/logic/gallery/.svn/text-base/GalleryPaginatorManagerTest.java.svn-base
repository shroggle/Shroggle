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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 *         Date: 29.07.2009
 */
@RunWith(TestRunnerWithMockServices.class)
public class GalleryPaginatorManagerTest {

    @Test
    public void testGetPaginatorType_PREVIOUS_NEXT() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT);

        Assert.assertEquals(PaginatorType.ABOVE, GalleryPaginatorManager.getPaginatorType(gallery));
    }

    @Test
    public void testGetPaginatorType_PREVIOUS_NEXT_WITH_BRDERED_NUMBERS() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_BORDERED_NUMBERS);

        Assert.assertEquals(PaginatorType.ABOVE, GalleryPaginatorManager.getPaginatorType(gallery));
    }

    @Test
    public void testGetPaginatorType_PREVIOUS_NEXT_WITH_NUMBERS() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT_WITH_NUMBERS);

        Assert.assertEquals(PaginatorType.ABOVE, GalleryPaginatorManager.getPaginatorType(gallery));
    }

    @Test
    public void testGetPaginatorType_SCROLL_HORIZONTALLY() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.SCROLL_HORIZONTALLY);

        Assert.assertEquals(PaginatorType.NONE, GalleryPaginatorManager.getPaginatorType(gallery));
    }

    @Test
    public void testGetPaginatorType_SCROLL_VERTICALLY() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ABOVE_DATA_BELOW);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.SCROLL_VERTICALLY);

        Assert.assertEquals(PaginatorType.NONE, GalleryPaginatorManager.getPaginatorType(gallery));
    }


    @Test
    public void testGetPaginatorType_DATA_ABOVE_NAVIGATION_BELOW() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.DATA_ABOVE_NAVIGATION_BELOW);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT);

        Assert.assertEquals(PaginatorType.BELOW, GalleryPaginatorManager.getPaginatorType(gallery));
    }

    @Test
    public void testGetPaginatorType_DATA_LEFT_NAVIGATION_RIGHT() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.DATA_LEFT_NAVIGATION_RIGHT);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT);

        Assert.assertEquals(PaginatorType.BELOW, GalleryPaginatorManager.getPaginatorType(gallery));
    }

    @Test
    public void testGetPaginatorType_NAVIGATION_LEFT_DATA_RIGHT() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_LEFT_DATA_RIGHT);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT);

        Assert.assertEquals(PaginatorType.BELOW, GalleryPaginatorManager.getPaginatorType(gallery));
    }

    @Test
    public void testGetPaginatorType_NAVIGATION_ONLY() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT);

        Assert.assertEquals(PaginatorType.BELOW, GalleryPaginatorManager.getPaginatorType(gallery));
    }

    @Test
    public void getPaginatorPagesNumber_withoutGallery() {
        Assert.assertEquals(0, GalleryPaginatorManager.getPaginatorPagesNumber(null, null, SiteShowOption.getDraftOption()));
    }

    @Test
    public void getPaginatorPagesNumber() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(8);
        gallery.setRows(1);


        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 20; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT);

        Assert.assertEquals(3, GalleryPaginatorManager.getPaginatorPagesNumber(gallery, null, SiteShowOption.getDraftOption()));
        Assert.assertFalse(GalleryPaginatorManager.hidePaginator(gallery, null, SiteShowOption.getDraftOption()));

    }


    @Test
    public void getPaginatorPagesNumber_onePage() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(8);
        gallery.setRows(1);


        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 8; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREVIOUS_NEXT);

        Assert.assertEquals(1, GalleryPaginatorManager.getPaginatorPagesNumber(gallery, null, SiteShowOption.getDraftOption()));
        Assert.assertTrue(GalleryPaginatorManager.hidePaginator(gallery, null, SiteShowOption.getDraftOption()));

    }

    @Test
    public void getPaginatorPagesNumber_PaginatorType_NONE_SCROLL_HORIZONTALLY() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(8);
        gallery.setRows(1);


        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 5; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.SCROLL_HORIZONTALLY);

        Assert.assertEquals(0, GalleryPaginatorManager.getPaginatorPagesNumber(gallery, null, SiteShowOption.getDraftOption()));
        Assert.assertTrue(GalleryPaginatorManager.hidePaginator(gallery, null, SiteShowOption.getDraftOption()));
    }

    @Test
    public void getPaginatorPagesNumber_PaginatorType_NONE_SCROLL_VERTICALLY() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(8);
        gallery.setRows(1);


        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 5; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.SCROLL_VERTICALLY);

        Assert.assertEquals(0, GalleryPaginatorManager.getPaginatorPagesNumber(gallery, null, SiteShowOption.getDraftOption()));
        Assert.assertTrue(GalleryPaginatorManager.hidePaginator(gallery, null, SiteShowOption.getDraftOption()));
    }


    @Test
    public void getPaginatorPagesNumber_rowsNumber0() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(8);
        gallery.setRows(0);


        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 5; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREV_NEXT);

        Assert.assertEquals(0, GalleryPaginatorManager.getPaginatorPagesNumber(gallery, null, SiteShowOption.getDraftOption()));
        Assert.assertTrue(GalleryPaginatorManager.hidePaginator(gallery, null, SiteShowOption.getDraftOption()));
    }


    @Test
    public void getPaginatorPagesNumber_columnsNumber0() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(0);
        gallery.setRows(1);


        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 5; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setOrientation(GalleryOrientation.NAVIGATION_ONLY);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREV_NEXT);

        Assert.assertEquals(0, GalleryPaginatorManager.getPaginatorPagesNumber(gallery, null, SiteShowOption.getDraftOption()));
        Assert.assertTrue(GalleryPaginatorManager.hidePaginator(gallery, null, SiteShowOption.getDraftOption()));
    }

    @Test
    public void createSelectedPageNumber_pageNumberNull() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(0);
        gallery.setRows(1);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREV_NEXT);

        Assert.assertEquals(new Integer(1), GalleryPaginatorManager.createSelectedPageNumber(gallery, null));
    }


    @Test
    public void createSelectedPageNumber_pageNumber5() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(0);
        gallery.setRows(1);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.PREV_NEXT);

        Assert.assertEquals(new Integer(5), GalleryPaginatorManager.createSelectedPageNumber(gallery, 5));
    }


    @Test
    public void createSelectedPageNumber_SCROLL_HORIZONTALLY() {
        User user = TestUtil.createUserAndLogin("aa");
        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);

        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(0);
        gallery.setRows(1);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.SCROLL_HORIZONTALLY);

        Assert.assertNull(GalleryPaginatorManager.createSelectedPageNumber(gallery, 5));
    }

}
