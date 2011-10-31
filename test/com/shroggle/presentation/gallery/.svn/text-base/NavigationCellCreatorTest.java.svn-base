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
import com.shroggle.util.StringUtil;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class NavigationCellCreatorTest {

    @Test
    public void testGetSortedNavigationThumbnailCells() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site1);
        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 3; i++) {
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

        gallery.setRows(1);
        gallery.setColumns(5);
        gallery.setThumbnailWidth(50);
        gallery.setThumbnailHeight(55);
        gallery.setCellHorizontalMargin(39);
        gallery.setCellVerticalMargin(2);
        gallery.setCellBorderWidth(9);
        gallery.setCellWidth(578);
        gallery.setCellHeight(10);
        gallery.setBackgroundColor("#111111");


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
        final int elementsNumber = gallery.getRows() * gallery.getColumns();
        Assert.assertEquals(elementsNumber, navigationCells.size());
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(2, navigationCells.get(i).getNavigationInnerCells().size());
            /*//-------------------------------------------inner cells----------------------------------------------------
            for (NavigationInnerCell innerCell : navigationCells.get(i).getNavigationInnerCells()) {
                Assert.assertEquals(gallery.getThumbnailWidth() + "px;", innerCell.getThumbnailWidth());
                Assert.assertEquals(gallery.getThumbnailHeight() + "px;", innerCell.getThumbnailHeight());
                //Assert.assertEquals("text-align: center;width : 55;height : 55;", innerCell.getStyle());
            }
            //-------------------------------------------inner cells----------------------------------------------------
*/
            //-------------------------------------------main cell------------------------------------------------------
            Assert.assertEquals("background : #111111; ", navigationCells.get(i).getBackground());
            Assert.assertEquals("margin : " + gallery.getCellVerticalMargin() + "px " +
                    gallery.getCellHorizontalMargin() + "px; ", navigationCells.get(i).getMargin());
            Assert.assertEquals("border : " + gallery.getCellBorderWidth() + "px " +
                    StringUtil.getEmptyOrString(gallery.getBorderStyle()) + " " +
                    StringUtil.getEmptyOrString(gallery.getBorderColor()) + "; ",
                    navigationCells.get(i).getBorder());
            Assert.assertEquals(gallery.getCellWidth() + "px", navigationCells.get(i).getCellWidth());
            Assert.assertEquals(gallery.getCellHeight() + "px", navigationCells.get(i).getCellHeight());
            Assert.assertEquals(gallery.getThumbnailWidth(), navigationCells.get(i).getThumbnailWidth());
            Assert.assertEquals(gallery.getThumbnailHeight(), navigationCells.get(i).getThumbnailHeight());
            Assert.assertEquals("margin : 2px 39px; border : 9px  transparent; background : #111111; width : 578px; height : 10px;", navigationCells.get(i).getStyle());
            //-------------------------------------------main cell------------------------------------------------------
        }
        for (int i = 3; i < elementsNumber; i++) {
            //-------------------------------------------main cell------------------------------------------------------
            Assert.assertEquals(0, navigationCells.get(i).getNavigationInnerCells().size());
            Assert.assertEquals("background : #F0F0F0; ", navigationCells.get(i).getBackground());
            Assert.assertEquals("margin : " + gallery.getCellVerticalMargin() + "px " +
                    gallery.getCellHorizontalMargin() + "px; ", navigationCells.get(i).getMargin());
            Assert.assertEquals("border : " + gallery.getCellBorderWidth() + "px " +
                    StringUtil.getEmptyOrString(gallery.getBorderStyle()) + " " +
                    StringUtil.getEmptyOrString(gallery.getBorderColor()) + "; ",
                    navigationCells.get(i).getBorder());
            Assert.assertEquals(gallery.getCellWidth() + "px", navigationCells.get(i).getCellWidth());
            Assert.assertEquals(gallery.getCellHeight() + "px", navigationCells.get(i).getCellHeight());
            Assert.assertEquals("margin : 2px 39px; border : 9px  transparent; background : #F0F0F0; width : 578px; height : 10px;", navigationCells.get(i).getStyle());
            //-------------------------------------------main cell------------------------------------------------------
        }
    }

    @Test
    public void testGetSortedNavigationThumbnailCellsWithoutElementsForSelectedPage() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site1);
        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 3; i++) {
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


        gallery.setRows(1);
        gallery.setColumns(5);
        gallery.setThumbnailWidth(50);
        gallery.setThumbnailHeight(55);
        gallery.setCellHorizontalMargin(39);
        gallery.setCellVerticalMargin(2);
        gallery.setCellBorderWidth(9);
        gallery.setCellWidth(578);
        gallery.setCellHeight(10);
        gallery.setBackgroundColor("#111111");


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


        List<NavigationCell> navigationCells = NavigationCellCreator.createSortedCells(gallery, widgetGallery, 2, null, SiteShowOption.INSIDE_APP);
        final int elementsNumber = gallery.getRows() * gallery.getColumns();
        Assert.assertEquals(elementsNumber, navigationCells.size());
        for (int i = 0; i < elementsNumber; i++) {
            //-------------------------------------------main cell------------------------------------------------------
            Assert.assertEquals(0, navigationCells.get(i).getNavigationInnerCells().size());
            Assert.assertEquals("background : #F0F0F0; ", navigationCells.get(i).getBackground());
            Assert.assertEquals("margin : " + gallery.getCellVerticalMargin() + "px " +
                    gallery.getCellHorizontalMargin() + "px; ", navigationCells.get(i).getMargin());
            Assert.assertEquals("border : " + gallery.getCellBorderWidth() + "px " +
                    StringUtil.getEmptyOrString(gallery.getBorderStyle()) + " " +
                    StringUtil.getEmptyOrString(gallery.getBorderColor()) + "; ", navigationCells.get(i).getBorder());
            Assert.assertEquals(gallery.getCellWidth() + "px", navigationCells.get(i).getCellWidth());
            Assert.assertEquals(gallery.getCellHeight() + "px", navigationCells.get(i).getCellHeight());
            Assert.assertEquals("margin : 2px 39px; border : 9px  transparent; background : #F0F0F0; width : 578px; height : 10px;", navigationCells.get(i).getStyle());
            //-------------------------------------------main cell------------------------------------------------------
        }
    }

    @Test
    public void testGetInnerCellsForLeftColumn() {
        User user = TestUtil.createUserAndLogin("aa");

        Site site1 = TestUtil.createSite("title1", "url1");
        TestUtil.createUserOnSiteRightActive(user, site1, SiteAccessLevel.ADMINISTRATOR);


        List<DraftFormItem> items = TestUtil.createDefaultFormItemsForGallery();
        final DraftCustomForm customForm = TestUtil.createCustomForm(site1.getSiteId(), "form1", items);
        DraftGallery gallery = TestUtil.createGallery(site1.getSiteId(), "galleryName1", "commentsNotes1", customForm);
        PageManager pageVersion = TestUtil.createPageVersionAndPage(site1);
        WidgetItem widgetGallery = TestUtil.createWidgetGalleryForPageVersion(pageVersion, gallery.getId(), true, true);

        //---------------------------------------------filled forms---------------------------------------------
        for (int i = 0; i < 3; i++) {
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


        DraftGalleryLabel label2 = new DraftGalleryLabel();
        label2.setAlign(GalleryAlign.LEFT);
        label2.setColumn(2);
        label2.setPosition(1);
        label2.getId().setFormItemId(items.get(3).getFormItemId());
        gallery.addLabel(label2);


        List<NavigationCell> navigationCells = NavigationCellCreator.createSortedCells(gallery, widgetGallery, 1, null, SiteShowOption.INSIDE_APP);
        Assert.assertEquals(0, navigationCells.get(0).getInnerCellsForLeftColumn().get(0).getColumn());
        Assert.assertEquals(1, navigationCells.get(0).getInnerCellsForCenterColumn().get(0).getColumn());
        Assert.assertEquals(2, navigationCells.get(0).getInnerCellsForRightColumn().get(0).getColumn());

        Assert.assertEquals(items.get(0).getFormItemId(), navigationCells.get(0).getInnerCellsForLeftColumn().get(0).getFormItemId());
        Assert.assertEquals(items.get(4).getFormItemId(), navigationCells.get(0).getInnerCellsForCenterColumn().get(0).getFormItemId());
        Assert.assertEquals(items.get(3).getFormItemId(), navigationCells.get(0).getInnerCellsForRightColumn().get(0).getFormItemId());
    }


    @Test
    public void testGetInnerCellsForCenterColumn() {
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

        DraftGalleryLabel label0 = new DraftGalleryLabel();
        label0.setAlign(GalleryAlign.CENTER);
        label0.setColumn(1);
        label0.setPosition(2);
        label0.getId().setFormItemId(items.get(0).getFormItemId());
        gallery.addLabel(label0);

        DraftGalleryLabel label1 = new DraftGalleryLabel();
        label1.setAlign(GalleryAlign.LEFT);
        label1.setColumn(1);
        label1.setPosition(0);
        label1.getId().setFormItemId(items.get(4).getFormItemId());
        gallery.addLabel(label1);


        DraftGalleryLabel label2 = new DraftGalleryLabel();
        label2.setAlign(GalleryAlign.RIGHT);
        label2.setColumn(1);
        label2.setPosition(5);
        label2.getId().setFormItemId(items.get(3).getFormItemId());
        gallery.addLabel(label2);


        List<NavigationCell> navigationCells = NavigationCellCreator.createSortedCells(gallery, widgetGallery, 1, null, SiteShowOption.INSIDE_APP);

        List<NavigationInnerCell> innerCells = navigationCells.get(0).getInnerCellsForCenterColumn();
        Assert.assertEquals(0, innerCells.get(0).getPosition());
        Assert.assertEquals("text-align:left;", innerCells.get(0).getAlign());

        Assert.assertEquals(2, innerCells.get(1).getPosition());
        Assert.assertEquals("text-align:center;", innerCells.get(1).getAlign());

        Assert.assertEquals(5, innerCells.get(2).getPosition());
        Assert.assertEquals("text-align:right;", innerCells.get(2).getAlign());
    }

}
