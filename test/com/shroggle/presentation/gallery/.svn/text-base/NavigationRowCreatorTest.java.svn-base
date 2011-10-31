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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.page.PageManager;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class NavigationRowCreatorTest {

    @Test
    public void testCreateRows() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site1);
        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

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


        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);
        gallery.setRows(2);

        DraftGalleryLabel label0 = new DraftGalleryLabel();
        label0.setAlign(GalleryAlign.CENTER);
        label0.setColumn(0);
        label0.setPosition(0);
        label0.getId().setFormItemId(items.get(0).getFormItemId());
        gallery.addLabel(label0);

        DraftGalleryLabel label1 = new DraftGalleryLabel();
        label1.setAlign(GalleryAlign.LEFT);
        label1.setColumn(1);
        label1.setPosition(1);
        label1.getId().setFormItemId(items.get(4).getFormItemId());
        gallery.addLabel(label1);

        List<NavigationCell> navigationCells = NavigationCellCreator.createSortedCells(gallery, widgetGallery, 1, null, SiteShowOption.INSIDE_APP);
        List<NavigationRow> navigationRows = NavigationRowCreator.createRows(gallery, widgetGallery, 1, null, SiteShowOption.INSIDE_APP);

        final int elementsNumber = gallery.getRows() * gallery.getColumns();
        Assert.assertEquals(elementsNumber, navigationCells.size());
        Assert.assertEquals(gallery.getRows(), navigationRows.size());
        Assert.assertEquals(gallery.getColumns(), navigationRows.get(0).getCells().size());
    }

    @Test
    public void testCreateRows_withNotEnoughCells() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site1);
        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 10; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);
        gallery.setRows(2);

        DraftGalleryLabel label0 = new DraftGalleryLabel();
        label0.setAlign(GalleryAlign.CENTER);
        label0.setColumn(0);
        label0.setPosition(0);
        label0.getId().setFormItemId(items.get(0).getFormItemId());
        gallery.addLabel(label0);

        DraftGalleryLabel label1 = new DraftGalleryLabel();
        label1.setAlign(GalleryAlign.LEFT);
        label1.setColumn(1);
        label1.setPosition(1);
        label1.getId().setFormItemId(items.get(4).getFormItemId());
        gallery.addLabel(label1);

        List<NavigationCell> navigationCells = NavigationCellCreator.createSortedCells(gallery, widgetGallery, 1, null, SiteShowOption.INSIDE_APP);
        List<NavigationRow> navigationRows = NavigationRowCreator.createRows(gallery, widgetGallery, 1, null, SiteShowOption.INSIDE_APP);

        final int elementsNumber = gallery.getRows() * gallery.getColumns();
        Assert.assertEquals(elementsNumber, navigationCells.size());
        Assert.assertEquals(gallery.getRows(), navigationRows.size());
        Assert.assertEquals(gallery.getColumns(), navigationRows.get(0).getCells().size());
    }

    @Test
    public void testCreateRows_SCROLL_HORIZONTALLY() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site1);
        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        //---------------------------------------------filled forms---------------------------------------------
        final int elementsNumber = 20;
        for (int i = 0; i < elementsNumber; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);
        gallery.setRows(2);

        DraftGalleryLabel label0 = new DraftGalleryLabel();
        label0.setAlign(GalleryAlign.CENTER);
        label0.setColumn(0);
        label0.setPosition(0);
        label0.getId().setFormItemId(items.get(0).getFormItemId());
        gallery.addLabel(label0);

        DraftGalleryLabel label1 = new DraftGalleryLabel();
        label1.setAlign(GalleryAlign.LEFT);
        label1.setColumn(1);
        label1.setPosition(1);
        label1.getId().setFormItemId(items.get(4).getFormItemId());
        gallery.addLabel(label1);

        List<NavigationCell> navigationCells = NavigationCellCreator.createSortedCells(gallery, widgetGallery, null, null, SiteShowOption.INSIDE_APP);
        List<NavigationRow> navigationRows = NavigationRowCreator.createRows(gallery, widgetGallery, null, null, SiteShowOption.INSIDE_APP);

        Assert.assertEquals(elementsNumber, navigationCells.size());
        Assert.assertEquals(gallery.getRows(), navigationRows.size());

        for (NavigationRow navigationRow : navigationRows) {
            Assert.assertEquals(10, navigationRow.getCells().size());
        }
    }


    @Test
    public void testCreateRows_SCROLL_HORIZONTALLY_withNotEnoughCells() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site1);
        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        //---------------------------------------------filled forms---------------------------------------------
        final int elementsNumber = 22;
        for (int i = 0; i < elementsNumber; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);
        gallery.setRows(7);
        gallery.setColumns(1);

        DraftGalleryLabel label0 = new DraftGalleryLabel();
        label0.setAlign(GalleryAlign.CENTER);
        label0.setColumn(0);
        label0.setPosition(0);
        label0.getId().setFormItemId(items.get(0).getFormItemId());
        gallery.addLabel(label0);

        DraftGalleryLabel label1 = new DraftGalleryLabel();
        label1.setAlign(GalleryAlign.LEFT);
        label1.setColumn(1);
        label1.setPosition(1);
        label1.getId().setFormItemId(items.get(4).getFormItemId());
        gallery.addLabel(label1);

        List<NavigationCell> navigationCells = NavigationCellCreator.createSortedCells(gallery, widgetGallery, null, null, SiteShowOption.INSIDE_APP);
        List<NavigationRow> navigationRows = NavigationRowCreator.createRows(gallery, widgetGallery, null, null, SiteShowOption.INSIDE_APP);

        Assert.assertEquals(elementsNumber, navigationCells.size());
        Assert.assertEquals(gallery.getRows(), navigationRows.size());
        Assert.assertEquals(4, navigationRows.get(0).getCells().size());
        for (int i = 1; i < navigationRows.size(); i++) {
            Assert.assertEquals(3, navigationRows.get(i).getCells().size());
        }
    }


    @Test
    public void testCreateRows_SCROLL_VERTICALLY() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        gallery.setNavigationPaginatorType(GalleryNavigationPaginatorType.SCROLL_VERTICALLY);
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site1);
        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        final int elementsNumber = 20;
        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < elementsNumber; i++) {
            final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();
            for (DraftFormItem formItem : items) {
                FilledFormItem filledFormItem = TestUtil.createFilledFormItem(formItem.getFormItemId(),
                        formItem.getFormItemName(), formItem.getItemName() + i);
                filledFormItems.add(filledFormItem);
            }
            TestUtil.createFilledFormByFormId(customForm.getFormId(), filledFormItems);
        }
        //---------------------------------------------filled forms---------------------------------------------


        gallery.setFirstSortItemId(items.get(0).getFormItemId());
        gallery.setSecondSortItemId(items.get(1).getFormItemId());

        gallery.setFirstSortType(GallerySortOrder.ASCENDING);
        gallery.setSecondSortType(GallerySortOrder.DESCENDING);
        gallery.setRows(2);

        DraftGalleryLabel label0 = new DraftGalleryLabel();
        label0.setAlign(GalleryAlign.CENTER);
        label0.setColumn(0);
        label0.setPosition(0);
        label0.getId().setFormItemId(items.get(0).getFormItemId());
        gallery.addLabel(label0);

        DraftGalleryLabel label1 = new DraftGalleryLabel();
        label1.setAlign(GalleryAlign.LEFT);
        label1.setColumn(1);
        label1.setPosition(1);
        label1.getId().setFormItemId(items.get(4).getFormItemId());
        gallery.addLabel(label1);

        List<NavigationCell> navigationCells = NavigationCellCreator.createSortedCells(gallery, widgetGallery, null, null, SiteShowOption.INSIDE_APP);
        List<NavigationRow> navigationRows = NavigationRowCreator.createRows(gallery, widgetGallery, null, null, SiteShowOption.INSIDE_APP);

        Assert.assertEquals(elementsNumber, navigationCells.size());
        Assert.assertEquals(((int) Math.ceil(elementsNumber / (double) gallery.getColumns())), navigationRows.size());
        for (int i = 0; i < navigationRows.size() - 1; i++) {
            Assert.assertEquals(8, navigationRows.get(i).getCells().size());
        }
        Assert.assertEquals(4, navigationRows.get(navigationRows.size() - 1).getCells().size());

        Assert.assertEquals(gallery.getColumns(), navigationRows.get(0).getCells().size());
    }
}
