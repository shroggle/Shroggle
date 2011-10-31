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
package com.shroggle.presentation.gallery;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import com.shroggle.entity.*;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class NavigationSortedFormsInSessionTest {


    @Test
    public void testAllItemsAreNull_NotNull() {
        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(1);
        formsInSession.setMaxId(1);
        formsInSession.setSortedFilledFormsIds(new ArrayList<Integer>());
        formsInSession.setSecondSortItemId(1);
        formsInSession.setFirstSortType(GallerySortOrder.DESCENDING);
        formsInSession.setSecondSortType(GallerySortOrder.DESCENDING);
        Assert.assertFalse(formsInSession.allItemsAreNull());
    }

    @Test
    public void testAllItemsAreNull_NullMaxId() {
        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(0);
        formsInSession.setMaxId(1);
        formsInSession.setSortedFilledFormsIds(new ArrayList<Integer>());
        formsInSession.setSecondSortItemId(1);
        formsInSession.setFirstSortType(GallerySortOrder.DESCENDING);
        formsInSession.setSecondSortType(GallerySortOrder.DESCENDING);
        Assert.assertFalse(formsInSession.allItemsAreNull());
    }

    @Test
    public void testAllItemsAreNull_NullSortItemId() {
        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(1);
        formsInSession.setMaxId(0);
        formsInSession.setSortedFilledFormsIds(new ArrayList<Integer>());
        formsInSession.setSecondSortItemId(1);
        formsInSession.setFirstSortType(GallerySortOrder.DESCENDING);
        formsInSession.setSecondSortType(GallerySortOrder.DESCENDING);
        Assert.assertFalse(formsInSession.allItemsAreNull());
    }

    @Test
    public void testAllItemsAreNull_NullSortedFilledForms() {
        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(1);
        formsInSession.setMaxId(1);
        formsInSession.setSortedFilledFormsIds(null);
        formsInSession.setSecondSortItemId(1);
        formsInSession.setFirstSortType(GallerySortOrder.DESCENDING);
        formsInSession.setSecondSortType(GallerySortOrder.DESCENDING);
        Assert.assertTrue(formsInSession.allItemsAreNull());
    }

    @Test
    public void testAllItemsAreNull_NullSecondSortItemId() {
        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(1);
        formsInSession.setMaxId(1);
        formsInSession.setSortedFilledFormsIds(new ArrayList<Integer>());
        formsInSession.setSecondSortItemId(0);
        formsInSession.setFirstSortType(GallerySortOrder.DESCENDING);
        formsInSession.setSecondSortType(GallerySortOrder.DESCENDING);
        Assert.assertFalse(formsInSession.allItemsAreNull());
    }

    @Test
    public void testAllItemsAreNull_NullFirstSortType() {
        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(1);
        formsInSession.setMaxId(1);
        formsInSession.setSortedFilledFormsIds(new ArrayList<Integer>());
        formsInSession.setSecondSortItemId(1);
        formsInSession.setFirstSortType(null);
        formsInSession.setSecondSortType(GallerySortOrder.DESCENDING);
        Assert.assertTrue(formsInSession.allItemsAreNull());
    }

    @Test
    public void testAllItemsAreNull_NullSecondSortType() {
        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(1);
        formsInSession.setMaxId(1);
        formsInSession.setSortedFilledFormsIds(new ArrayList<Integer>());
        formsInSession.setSecondSortItemId(1);
        formsInSession.setFirstSortType(GallerySortOrder.DESCENDING);
        formsInSession.setSecondSortType(null);
        Assert.assertTrue(formsInSession.allItemsAreNull());
    }

    @Test
    public void testEquals() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);

        final DraftGallery gallery = new DraftGallery();
        gallery.setFirstSortItemId(1);
        gallery.setSecondSortItemId(2);
        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);
        gallery.setName("galleryName1");
        gallery.setDescription("commentsNotes1");
        gallery.setSiteId(site1.getSiteId());
        gallery.setFormId1(customForm.getFormId());
        ServiceLocator.getPersistance().putItem(gallery);


        //---------------------------------------------filled forms---------------------------------------------
        List<FilledForm> filledForms = new ArrayList<FilledForm>();
        for (int i = 0; i < 3; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            filledForms.add(TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems));
        }
        //---------------------------------------------filled forms---------------------------------------------

        final int maxFilledFormId = ServiceLocator.getPersistance().getMaxFilledFormIdByFormId(customForm.getFormId());
        final long filledFormsNumber = ServiceLocator.getPersistance().getFilledFormsNumberByFormId(customForm.getFormId());


        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(gallery.getFirstSortItemId());
        formsInSession.setSecondSortItemId(gallery.getSecondSortItemId());
        formsInSession.setFirstSortType(gallery.getFirstSortType());
        formsInSession.setSecondSortType(gallery.getSecondSortType());
        formsInSession.setMaxId(maxFilledFormId);

        List<Integer> sortedFilledFormsIds = new ArrayList<Integer>();
        for (FilledForm filledForm : filledForms) {
            sortedFilledFormsIds.add(filledForm.getFilledFormId());
        }
        formsInSession.setSortedFilledFormsIds(sortedFilledFormsIds);


        Assert.assertTrue(formsInSession.equals(gallery, maxFilledFormId, filledFormsNumber));
    }

    @Test
    public void testEquals_withNotEqualFirstSortItemId() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);

        final DraftGallery gallery = new DraftGallery();
        gallery.setFirstSortItemId(1);
        gallery.setSecondSortItemId(2);
        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);
        gallery.setName("galleryName1");
        gallery.setDescription("commentsNotes1");
        gallery.setSiteId(site1.getSiteId());
        gallery.setFormId1(customForm.getFormId());
        ServiceLocator.getPersistance().putItem(gallery);


        //---------------------------------------------filled forms---------------------------------------------
        List<FilledForm> filledForms = new ArrayList<FilledForm>();
        for (int i = 0; i < 3; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            filledForms.add(TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems));
        }
        //---------------------------------------------filled forms---------------------------------------------

        final int maxFilledFormId = ServiceLocator.getPersistance().getMaxFilledFormIdByFormId(customForm.getFormId());
        final long filledFormsNumber = ServiceLocator.getPersistance().getFilledFormsNumberByFormId(customForm.getFormId());


        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(123);
        formsInSession.setSecondSortItemId(gallery.getSecondSortItemId());
        formsInSession.setFirstSortType(gallery.getFirstSortType());
        formsInSession.setSecondSortType(gallery.getSecondSortType());
        formsInSession.setMaxId(maxFilledFormId);
        List<Integer> sortedFilledFormsIds = new ArrayList<Integer>();
        for (FilledForm filledForm : filledForms) {
            sortedFilledFormsIds.add(filledForm.getFilledFormId());
        }
        formsInSession.setSortedFilledFormsIds(sortedFilledFormsIds);


        Assert.assertFalse(formsInSession.equals(gallery, maxFilledFormId, filledFormsNumber));
    }

    @Test
    public void testEquals_withNotEqualtSecondSortItemId() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);

        final DraftGallery gallery = new DraftGallery();
        gallery.setFirstSortItemId(1);
        gallery.setSecondSortItemId(2);
        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);
        gallery.setName("galleryName1");
        gallery.setDescription("commentsNotes1");
        gallery.setSiteId(site1.getSiteId());
        gallery.setFormId1(customForm.getFormId());
        ServiceLocator.getPersistance().putItem(gallery);


        //---------------------------------------------filled forms---------------------------------------------
        List<FilledForm> filledForms = new ArrayList<FilledForm>();
        for (int i = 0; i < 3; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            filledForms.add(TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems));
        }
        //---------------------------------------------filled forms---------------------------------------------

        final int maxFilledFormId = ServiceLocator.getPersistance().getMaxFilledFormIdByFormId(customForm.getFormId());
        final long filledFormsNumber = ServiceLocator.getPersistance().getFilledFormsNumberByFormId(customForm.getFormId());


        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(gallery.getFirstSortItemId());
        formsInSession.setSecondSortItemId(-12);
        formsInSession.setFirstSortType(gallery.getFirstSortType());
        formsInSession.setSecondSortType(gallery.getSecondSortType());
        formsInSession.setMaxId(maxFilledFormId);
        List<Integer> sortedFilledFormsIds = new ArrayList<Integer>();
        for (FilledForm filledForm : filledForms) {
            sortedFilledFormsIds.add(filledForm.getFilledFormId());
        }
        formsInSession.setSortedFilledFormsIds(sortedFilledFormsIds);


        Assert.assertFalse(formsInSession.equals(gallery, maxFilledFormId, filledFormsNumber));
    }

    @Test
    public void testEquals_withNotEqualFirstSortType() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);

        final DraftGallery gallery = new DraftGallery();
        gallery.setFirstSortItemId(1);
        gallery.setSecondSortItemId(2);
        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);
        gallery.setName("galleryName1");
        gallery.setDescription("commentsNotes1");
        gallery.setSiteId(site1.getSiteId());
        gallery.setFormId1(customForm.getFormId());
        ServiceLocator.getPersistance().putItem(gallery);


        //---------------------------------------------filled forms---------------------------------------------
        List<FilledForm> filledForms = new ArrayList<FilledForm>();
        for (int i = 0; i < 3; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            filledForms.add(TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems));
        }
        //---------------------------------------------filled forms---------------------------------------------

        final int maxFilledFormId = ServiceLocator.getPersistance().getMaxFilledFormIdByFormId(customForm.getFormId());
        final long filledFormsNumber = ServiceLocator.getPersistance().getFilledFormsNumberByFormId(customForm.getFormId());


        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(gallery.getFirstSortItemId());
        formsInSession.setSecondSortItemId(gallery.getSecondSortItemId());
        formsInSession.setFirstSortType(GallerySortOrder.RANDOM);
        formsInSession.setSecondSortType(gallery.getSecondSortType());
        formsInSession.setMaxId(maxFilledFormId);
        List<Integer> sortedFilledFormsIds = new ArrayList<Integer>();
        for (FilledForm filledForm : filledForms) {
            sortedFilledFormsIds.add(filledForm.getFilledFormId());
        }
        formsInSession.setSortedFilledFormsIds(sortedFilledFormsIds);


        Assert.assertFalse(formsInSession.equals(gallery, maxFilledFormId, filledFormsNumber));
    }

    @Test
    public void testEquals_withNotEqualSecondSortType() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);

        final DraftGallery gallery = new DraftGallery();
        gallery.setFirstSortItemId(1);
        gallery.setSecondSortItemId(2);
        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);
        gallery.setName("galleryName1");
        gallery.setDescription("commentsNotes1");
        gallery.setSiteId(site1.getSiteId());
        gallery.setFormId1(customForm.getFormId());
        ServiceLocator.getPersistance().putItem(gallery);


        //---------------------------------------------filled forms---------------------------------------------
        List<FilledForm> filledForms = new ArrayList<FilledForm>();
        for (int i = 0; i < 3; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            filledForms.add(TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems));
        }
        //---------------------------------------------filled forms---------------------------------------------

        final int maxFilledFormId = ServiceLocator.getPersistance().getMaxFilledFormIdByFormId(customForm.getFormId());
        final long filledFormsNumber = ServiceLocator.getPersistance().getFilledFormsNumberByFormId(customForm.getFormId());


        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(gallery.getFirstSortItemId());
        formsInSession.setSecondSortItemId(gallery.getSecondSortItemId());
        formsInSession.setFirstSortType(gallery.getFirstSortType());
        formsInSession.setSecondSortType(GallerySortOrder.RANDOM);
        formsInSession.setMaxId(maxFilledFormId);
        List<Integer> sortedFilledFormsIds = new ArrayList<Integer>();
        for (FilledForm filledForm : filledForms) {
            sortedFilledFormsIds.add(filledForm.getFilledFormId());
        }
        formsInSession.setSortedFilledFormsIds(sortedFilledFormsIds);


        Assert.assertFalse(formsInSession.equals(gallery, maxFilledFormId, filledFormsNumber));
    }

    @Test
    public void testEquals_withNotEqualMaxId() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);

        final DraftGallery gallery = new DraftGallery();
        gallery.setFirstSortItemId(1);
        gallery.setSecondSortItemId(2);
        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);
        gallery.setName("galleryName1");
        gallery.setDescription("commentsNotes1");
        gallery.setSiteId(site1.getSiteId());
        gallery.setFormId1(customForm.getFormId());
        ServiceLocator.getPersistance().putItem(gallery);


        //---------------------------------------------filled forms---------------------------------------------
        List<FilledForm> filledForms = new ArrayList<FilledForm>();
        for (int i = 0; i < 3; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            filledForms.add(TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems));
        }
        //---------------------------------------------filled forms---------------------------------------------

        final int maxFilledFormId = ServiceLocator.getPersistance().getMaxFilledFormIdByFormId(customForm.getFormId());
        final long filledFormsNumber = ServiceLocator.getPersistance().getFilledFormsNumberByFormId(customForm.getFormId());


        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(gallery.getFirstSortItemId());
        formsInSession.setSecondSortItemId(gallery.getSecondSortItemId());
        formsInSession.setFirstSortType(gallery.getFirstSortType());
        formsInSession.setSecondSortType(gallery.getSecondSortType());
        formsInSession.setMaxId(-1);
        List<Integer> sortedFilledFormsIds = new ArrayList<Integer>();
        for (FilledForm filledForm : filledForms) {
            sortedFilledFormsIds.add(filledForm.getFilledFormId());
        }
        formsInSession.setSortedFilledFormsIds(sortedFilledFormsIds);


        Assert.assertFalse(formsInSession.equals(gallery, maxFilledFormId, filledFormsNumber));
    }

    @Test
    public void testEquals_withNotEqualSortedFilledFormsSize() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);

        final DraftGallery gallery = new DraftGallery();
        gallery.setFirstSortItemId(1);
        gallery.setSecondSortItemId(2);
        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);
        gallery.setName("galleryName1");
        gallery.setDescription("commentsNotes1");
        gallery.setSiteId(site1.getSiteId());
        gallery.setFormId1(customForm.getFormId());
        ServiceLocator.getPersistance().putItem(gallery);


        //---------------------------------------------filled forms---------------------------------------------
        List<FilledForm> filledForms = new ArrayList<FilledForm>();
        for (int i = 0; i < 3; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            filledForms.add(TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems));
        }
        //---------------------------------------------filled forms---------------------------------------------

        final int maxFilledFormId = ServiceLocator.getPersistance().getMaxFilledFormIdByFormId(customForm.getFormId());
        final long filledFormsNumber = ServiceLocator.getPersistance().getFilledFormsNumberByFormId(customForm.getFormId());


        NavigationSortedFormsInSession formsInSession = new NavigationSortedFormsInSession();
        formsInSession.setFirstSortItemId(gallery.getFirstSortItemId());
        formsInSession.setSecondSortItemId(gallery.getSecondSortItemId());
        formsInSession.setFirstSortType(gallery.getFirstSortType());
        formsInSession.setSecondSortType(gallery.getSecondSortType());
        formsInSession.setMaxId(maxFilledFormId);
        formsInSession.setSortedFilledFormsIds(new ArrayList<Integer>());


        Assert.assertFalse(formsInSession.equals(gallery, maxFilledFormId, filledFormsNumber));
    }

}

