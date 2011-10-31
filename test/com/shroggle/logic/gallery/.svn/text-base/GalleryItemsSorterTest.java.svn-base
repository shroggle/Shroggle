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

import com.shroggle.logic.form.FilledFormManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.util.context.Context;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.*;
import junit.framework.Assert;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class GalleryItemsSorterTest {

    @Test
    public void getSortedFilledForms_ASCENDING_DESCENDING() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);


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


        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);

        List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
        Assert.assertEquals(filledForms.get(0).getFilledFormId(), sortedFilledForms.get(0).getFilledFormId());
        Assert.assertEquals(filledForms.get(1).getFilledFormId(), sortedFilledForms.get(1).getFilledFormId());
        Assert.assertEquals(filledForms.get(2).getFilledFormId(), sortedFilledForms.get(2).getFilledFormId());

    }

    @Test
    public void getSortedFilledFormsAscWithNumericOnlyChecker() throws Exception {
        final User user = TestUtil.createUserAndLogin();

        final Site site = TestUtil.createSite();
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);


        final List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();

        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(2);
        gallery.setRows(1);

        final List<FilledForm> filledForms = new ArrayList<FilledForm>();
        for (int i = 0; i < 3; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (final DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), "" + (100 - i));
                filledFormItems.add(filledFormItem);
            }
            filledForms.add(TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems));
        }

        gallery.setFirstSortItemId(items.get(4).getFormItemId());
        gallery.setSecondSortItemId(items.get(0).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);


        List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
//        sortedFilledForms = sorter.reduceSortedItems(sortedFilledForms, (gallery.getRows() * gallery.getColumns()), 1);
        Assert.assertEquals(3, sortedFilledForms.size());
        Assert.assertEquals(filledForms.get(0).getFilledFormId(), sortedFilledForms.get(2).getFilledFormId());
        Assert.assertEquals(filledForms.get(1).getFilledFormId(), sortedFilledForms.get(1).getFilledFormId());
        Assert.assertEquals(filledForms.get(2).getFilledFormId(), sortedFilledForms.get(0).getFilledFormId());

    }

    @Test
    public void getSortedFilledForms_ASCENDING_DESCENDING_reduceElementsNumberByPageNumber() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(8);
        gallery.setRows(2);

        //---------------------------------------------filled forms---------------------------------------------
        List<FilledForm> filledForms = new ArrayList<FilledForm>();
        for (int i = 0; i < 100; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            //for (FormItem formItem : items) {
            for (int j = 0; j < items.size(); j++) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(items.get(j).getFormItemId(),
                        items.get(j).getFormItemName(), items.get(j).getItemName() + 200 % (1 + j + i));
                filledFormItems.add(filledFormItem);
            }
            filledForms.add(TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems));
        }
        //---------------------------------------------filled forms---------------------------------------------

        //first sort item
        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        //second sort item
        gallery.setSecondSortItemId(items.get(2).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);

        List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, (TestUtil.createWidgetItem()).getDraftItem(), SiteShowOption.getDraftOption());
        sortedFilledForms = sorter.reduceSortedItems(sortedFilledForms, (gallery.getRows() * gallery.getColumns()), 3);
        Assert.assertEquals(16, sortedFilledForms.size());
        String[] names = {"Name2", "Name2", "Name2", "Name2", "Name2", "Name2", "Name2", "Name20", "Name20", "Name20", "Name20", "Name20", "Name22", "Name23", "Name24", "Name24"};
        String[] descriptions = {"Description64", "Description5", "Description25", "Description2", "Description0", "Description0",
                "Description0", "Description8", "Description16", "Description14", "Description12", "Description10", "Description18",
                "Description17", "Description20", "Description16"};
        for (int i = 0; i < sortedFilledForms.size(); i++) {
            //first sort item
            final FilledFormItem filledFormItem = FilledFormManager.getFilledFormItemByFormItemId(sortedFilledForms.get(i), items.get(0).getFormItemId());
            Assert.assertEquals(names[i], filledFormItem.getValues().get(0));
            //second sort item
            final FilledFormItem filledFormItem2 = FilledFormManager.getFilledFormItemByFormItemId(sortedFilledForms.get(i), items.get(2).getFormItemId());
            Assert.assertEquals(descriptions[i], filledFormItem2.getValues().get(0));
        }
        Assert.assertEquals(filledForms.get(65).getFilledFormId(), sortedFilledForms.get(0).getFilledFormId());
        Assert.assertEquals(filledForms.get(10).getFilledFormId(), sortedFilledForms.get(1).getFilledFormId());
    }

    @Test
    public void getSortedFilledForms_ASCENDING_DESCENDING_reduceElementsNumber() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(2);
        gallery.setRows(1);

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

        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);


        List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, (TestUtil.createWidgetItem()).getDraftItem(), SiteShowOption.getDraftOption());
        sortedFilledForms = sorter.reduceSortedItems(sortedFilledForms, (gallery.getRows() * gallery.getColumns()), 1);
        Assert.assertEquals(2, sortedFilledForms.size());
        Assert.assertEquals(filledForms.get(0).getFilledFormId(), sortedFilledForms.get(0).getFilledFormId());
        Assert.assertEquals(filledForms.get(1).getFilledFormId(), sortedFilledForms.get(1).getFilledFormId());
    }

    @Test
    public void getSortedFilledForms_ASCENDING_DESCENDING_reduceElementsWithNullPageNumber() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setColumns(8);
        gallery.setRows(2);

        //---------------------------------------------filled forms---------------------------------------------
        List<FilledForm> filledForms = new ArrayList<FilledForm>();
        for (int i = 0; i < 100; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            //for (FormItem formItem : items) {
            for (int j = 0; j < items.size(); j++) {
                int index = j == 2 ? (i - j) : (i + j);
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(items.get(j).getFormItemId(),
                        items.get(j).getFormItemName(), items.get(j).getItemName() + index);
                filledFormItems.add(filledFormItem);
            }
            filledForms.add(TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems));
        }
        //---------------------------------------------filled forms---------------------------------------------

        //first sort item
        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        //second sort item
        gallery.setSecondSortItemId(items.get(2).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);

        List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
        Assert.assertEquals(100, sortedFilledForms.size());
        String[] names = {"Name0", "Name1", "Name10", "Name11", "Name12", "Name13", "Name14", "Name15"};
        String[] descriptions = {"Description-2", "Description-1", "Description8", "Description9", "Description10", "Description11",
                "Description12", "Description13"};
        for (int i = 0; i < names.length; i++) {
            //first sort item
            final FilledFormItem filledFormItem = FilledFormManager.getFilledFormItemByFormItemId(sortedFilledForms.get(i), items.get(0).getFormItemId());
            Assert.assertEquals(names[i], filledFormItem.getValues().get(0));
            //second sort item
            final FilledFormItem filledFormItem2 = FilledFormManager.getFilledFormItemByFormItemId(sortedFilledForms.get(i), items.get(2).getFormItemId());
            Assert.assertEquals(descriptions[i], filledFormItem2.getValues().get(0));
        }
        final FilledFormItem filledFormItem1 = FilledFormManager.getFilledFormItemByFormItemId(sortedFilledForms.get(0), items.get(0).getFormItemId());
        final FilledFormItem filledFormItem2 = FilledFormManager.getFilledFormItemByFormItemId(sortedFilledForms.get(1), items.get(0).getFormItemId());
        Assert.assertEquals("Name0", filledFormItem1.getValues().get(0));
        Assert.assertEquals("Name1", filledFormItem2.getValues().get(0));
        Assert.assertEquals(filledForms.get(0).getFilledFormId(), sortedFilledForms.get(0).getFilledFormId());
        Assert.assertEquals(filledForms.get(1).getFilledFormId(), sortedFilledForms.get(1).getFilledFormId());
    }

    @Test
    public void getSortedFilledForms_DESCENDING_ASCENDING() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);


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


        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.DESCENDING);
        gallery.setSecondSortType(GallerySortOrder.ASCENDING);
        List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
        Assert.assertEquals(filledForms.get(0).getFilledFormId(), sortedFilledForms.get(2).getFilledFormId());
        Assert.assertEquals(filledForms.get(1).getFilledFormId(), sortedFilledForms.get(1).getFilledFormId());
        Assert.assertEquals(filledForms.get(2).getFilledFormId(), sortedFilledForms.get(0).getFilledFormId());
    }


    @Test
    public void getSortedFilledForms_ASCENDING_DESCENDING_withEqualsFirstSortValue() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);

        int i = 0;
        //---------------------------------------------filled form 1---------------------------------------------
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        for (DraftFormItem formItem : items) {
            FilledFormItem filledFormItem;
            if (i == 0) {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), "value");
            } else {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + 1);
            }
            i++;
            filledFormItems.add(filledFormItem);
        }
        FilledForm filledForm1 = TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        //---------------------------------------------filled form 1---------------------------------------------


        i = 0;
        //---------------------------------------------filled form 2---------------------------------------------
        final List<FilledFormItem> filledFormItems2 = new ArrayList<FilledFormItem>();
        for (DraftFormItem formItem : items) {
            FilledFormItem filledFormItem;
            if (i == 0) {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), "value");
            } else {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + 2);
            }
            i++;
            filledFormItems2.add(filledFormItem);
        }
        FilledForm filledForm2 = TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems2);
        //---------------------------------------------filled form 2---------------------------------------------

        i = 0;
        //---------------------------------------------filled form 3---------------------------------------------
        final List<FilledFormItem> filledFormItems3 = new ArrayList<FilledFormItem>();
        for (DraftFormItem formItem : items) {
            FilledFormItem filledFormItem;
            if (i == 0) {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), "value");
            } else {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + 3);
            }
            i++;
            filledFormItems2.add(filledFormItem);
            filledFormItems3.add(filledFormItem);
        }
        FilledForm filledForm3 = TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems3);
        //---------------------------------------------filled form 3---------------------------------------------


        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);

        List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
        Assert.assertEquals(filledForm1.getFilledFormId(), sortedFilledForms.get(2).getFilledFormId());
        Assert.assertEquals(filledForm2.getFilledFormId(), sortedFilledForms.get(1).getFilledFormId());
        Assert.assertEquals(filledForm3.getFilledFormId(), sortedFilledForms.get(0).getFilledFormId());
    }


    @Test
    public void getSortedFilledForms_DESCENDING_ASCENDING_withEqualsFirstSortValue() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);

        int i = 0;
        //---------------------------------------------filled form 1---------------------------------------------
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        for (DraftFormItem formItem : items) {
            FilledFormItem filledFormItem;
            if (i == 0) {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), "value");
            } else {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + 1);
            }
            i++;
            filledFormItems.add(filledFormItem);
        }
        FilledForm filledForm1 = TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        //---------------------------------------------filled form 1---------------------------------------------


        i = 0;
        //---------------------------------------------filled form 2---------------------------------------------
        final List<FilledFormItem> filledFormItems2 = new ArrayList<FilledFormItem>();
        for (DraftFormItem formItem : items) {
            FilledFormItem filledFormItem;
            if (i == 0) {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), "value");
            } else {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + 2);
            }
            i++;
            filledFormItems2.add(filledFormItem);
        }
        FilledForm filledForm2 = TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems2);
        //---------------------------------------------filled form 2---------------------------------------------

        i = 0;
        //---------------------------------------------filled form 3---------------------------------------------
        final List<FilledFormItem> filledFormItems3 = new ArrayList<FilledFormItem>();
        for (DraftFormItem formItem : items) {
            FilledFormItem filledFormItem;
            if (i == 0) {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), "value");
            } else {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + 3);
            }
            i++;
            filledFormItems2.add(filledFormItem);
            filledFormItems3.add(filledFormItem);
        }
        FilledForm filledForm3 = TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems3);
        //---------------------------------------------filled form 3---------------------------------------------


        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.DESCENDING);
        gallery.setSecondSortType(GallerySortOrder.ASCENDING);
        List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
        Assert.assertEquals(filledForm1.getFilledFormId(), sortedFilledForms.get(0).getFilledFormId());
        Assert.assertEquals(filledForm2.getFilledFormId(), sortedFilledForms.get(1).getFilledFormId());
        Assert.assertEquals(filledForm3.getFilledFormId(), sortedFilledForms.get(2).getFilledFormId());
    }

    @Test
    public void getSortedFilledForms_RANDOM() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);

        int i = 0;
        //---------------------------------------------filled form 1---------------------------------------------
        final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
        for (DraftFormItem formItem : items) {
            FilledFormItem filledFormItem;
            if (i == 0) {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), "value");
            } else {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + 1);
            }
            i++;
            filledFormItems.add(filledFormItem);
        }
        TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        //---------------------------------------------filled form 1---------------------------------------------


        i = 0;
        //---------------------------------------------filled form 2---------------------------------------------
        final List<FilledFormItem> filledFormItems2 = new ArrayList<FilledFormItem>();
        for (DraftFormItem formItem : items) {
            FilledFormItem filledFormItem;
            if (i == 0) {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), "value");
            } else {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + 2);
            }
            i++;
            filledFormItems2.add(filledFormItem);
        }
        TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems2);
        //---------------------------------------------filled form 2---------------------------------------------

        i = 0;
        //---------------------------------------------filled form 3---------------------------------------------
        final List<FilledFormItem> filledFormItems3 = new ArrayList<FilledFormItem>();
        for (DraftFormItem formItem : items) {
            FilledFormItem filledFormItem;
            if (i == 0) {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), "value");
            } else {
                filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + 3);
            }
            i++;
            filledFormItems2.add(filledFormItem);
            filledFormItems3.add(filledFormItem);
        }
        TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems3);
        //---------------------------------------------filled form 3---------------------------------------------


        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.RANDOM);
        gallery.setSecondSortType(GallerySortOrder.RANDOM);
        List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
        Assert.assertNotNull(sortedFilledForms);
        Assert.assertEquals(3, sortedFilledForms.size());
    }

    @Test
    public void getSortedFilledFormsOnlyMyForNotMy() {
        final User user = TestUtil.createUserAndLogin("aa");
        final Site site = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "form1", items);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        filledForm.setUser(TestUtil.createUser());

        final DraftGallery gallery = TestUtil.createGallery(site.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setShowOnlyMyRecords(true);

        final List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
        Assert.assertEquals(0, sortedFilledForms.size());
    }

    @Test
    public void getSortedFilledFormsOnlyMyNotLogined() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "form1", items);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        filledForm.setUser(user);

        final DraftGallery gallery = TestUtil.createGallery(site.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setShowOnlyMyRecords(true);

        final List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
        Assert.assertEquals(0, sortedFilledForms.size());
    }
    
    @Test
    public void getSortedFilledFormsWithHidden() {
        final User user = TestUtil.createUser();
        final Site site = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "form1", items);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        final FilledForm filledFormHidden = TestUtil.createFilledForm(customForm);
        filledFormHidden.setHidden(true);
        filledForm.setUser(user);

        final DraftGallery gallery = TestUtil.createGallery(site.getSiteId(), "galleryName1", "commentsNotes1", customForm);

        final List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
        Assert.assertEquals(1, sortedFilledForms.size());
        Assert.assertEquals(filledForm.getFilledFormId(), sortedFilledForms.get(0).getFilledFormId());
    }

    @Test
    public void getSortedFilledFormsOnlyMy() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site.getSiteId(), "form1", items);
        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        filledForm.setUser(user);

        final DraftGallery gallery = TestUtil.createGallery(site.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setShowOnlyMyRecords(true);

        final List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
        Assert.assertEquals(filledForm, sortedFilledForms.get(0));
        Assert.assertEquals(1, sortedFilledForms.size());
    }

    @Test
    public void getSortedFilledFormsOnlyMyChildSiteWithoutSite() {
        final User user = TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite("1", "1");
        final Site childSite = TestUtil.createSite("2", "2");
        context.setSiteId(childSite.getSiteId());
        TestUtil.createUserOnSiteRightActive(user, site, SiteAccessLevel.ADMINISTRATOR);

        final DraftChildSiteRegistration childSiteRegistration = TestUtil.createChildSiteRegistration(site);
        final ChildSiteSettings childSiteSettings = TestUtil.createChildSiteSettings(childSiteRegistration, site, childSite);
        childSiteSettings.setFilledFormId(233);

        final DraftGallery gallery = TestUtil.createGallery(site.getSiteId(), "", "", childSiteRegistration);
        gallery.setShowOnlyMyRecords(true);

        final List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
        Assert.assertEquals(0, sortedFilledForms.size());
    }

    @Test
    public void getSortedFilledForms_withoutFilledForms() throws Exception {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);

        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.DESCENDING);
        gallery.setSecondSortType(GallerySortOrder.ASCENDING);
        List<FilledForm> sortedFilledForms = sorter.getFilledForms(gallery, null, SiteShowOption.getDraftOption());
        Assert.assertEquals(0, sortedFilledForms.size());
    }

    private final GalleryItemsSorter sorter = new GalleryItemsSorter();
    private final Context context = ServiceLocator.getContextStorage().get();

}