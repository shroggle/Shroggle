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
package com.shroggle.logic.form;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.FilledFormItemNotFoundException;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class FilledFormItemManagerTest {

    @Test(expected = FilledFormItemNotFoundException.class)
    public void testCreate_withoutItem() {
        new FilledFormItemManager(null);
    }


    /*------------------------------------------------Set formImageId-------------------------------------------------*/

    @Test
    public void testSetFormImageId_forNotIMAGE_FILE_UPLOAD() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        item.setValue(null);

        Assert.assertEquals(null, item.getValue());
        Assert.assertEquals(0, item.getValues().size());


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        manager.setFormImageId(10);


        Assert.assertEquals(null, item.getValue());
        Assert.assertEquals(0, item.getValues().size());
    }

    @Test
    public void testSetFormImageId_asString_withWrongId() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValue(null);

        Assert.assertEquals(null, item.getValue());
        Assert.assertEquals(0, item.getValues().size());


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        manager.setFormImageId("asdt23");


        Assert.assertEquals("-1", item.getValue());
        Assert.assertEquals(1, item.getValues().size());
    }

    @Test
    public void testSetFormImageId() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValue(null);

        Assert.assertEquals(null, item.getValue());
        Assert.assertEquals(0, item.getValues().size());


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        manager.setFormImageId(10);


        Assert.assertEquals("10", item.getValue());
        Assert.assertEquals(1, item.getValues().size());
        Assert.assertEquals("10", item.getValues().get(0));
    }

    @Test
    public void testSetFormImageId_withOldImageId() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValue("123");

        Assert.assertEquals("123", item.getValue());
        Assert.assertEquals(1, item.getValues().size());
        Assert.assertEquals("123", item.getValues().get(0));


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        manager.setFormImageId(10);


        Assert.assertEquals("10", item.getValue());
        Assert.assertEquals(1, item.getValues().size());
        Assert.assertEquals("10", item.getValues().get(0));
    }

    @Test
    public void testSetFormImageId_withOldImageIdAndKeywords() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValues(Arrays.asList("123", "keyword"));

        Assert.assertEquals(2, item.getValues().size());
        Assert.assertEquals("123", item.getValues().get(0));
        Assert.assertEquals("keyword", item.getValues().get(1));


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        manager.setFormImageId(10);


        Assert.assertEquals(2, item.getValues().size());
        Assert.assertEquals("10", item.getValues().get(0));
        Assert.assertEquals("keyword", item.getValues().get(1));
    }
    /*------------------------------------------------Set formImageId-------------------------------------------------*/


    /*-------------------------------------------------Set keywords---------------------------------------------------*/

    @Test
    public void testSetFormImageKeywords_forNotIMAGE_FILE_UPLOAD() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        item.setValue(null);

        Assert.assertEquals(null, item.getValue());
        Assert.assertEquals(0, item.getValues().size());


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        manager.setFormImageAlt("keywords");


        Assert.assertEquals(null, item.getValue());
        Assert.assertEquals(0, item.getValues().size());
    }

    @Test
    public void testSetFormImageKeywords() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValue(null);

        Assert.assertEquals(null, item.getValue());
        Assert.assertEquals(0, item.getValues().size());


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        manager.setFormImageAlt("keywords");


        Assert.assertEquals(2, item.getValues().size());
        Assert.assertEquals("-1", item.getValues().get(0));
        Assert.assertEquals("keywords", item.getValues().get(1));
    }

    @Test
    public void testSetFormImageKeywords_withOldImageId() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValue("123");

        Assert.assertEquals("123", item.getValue());
        Assert.assertEquals(1, item.getValues().size());
        Assert.assertEquals("123", item.getValues().get(0));


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        manager.setFormImageAlt("keywords");


        Assert.assertEquals(2, item.getValues().size());
        Assert.assertEquals("123", item.getValues().get(0));
        Assert.assertEquals("keywords", item.getValues().get(1));
    }

    @Test
    public void testSetFormImageKeywords_withOldImageIdAndKeywords() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValues(Arrays.asList("123", "old keyword"));

        Assert.assertEquals(2, item.getValues().size());
        Assert.assertEquals("123", item.getValues().get(0));
        Assert.assertEquals("old keyword", item.getValues().get(1));


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        manager.setFormImageAlt("keywords");


        Assert.assertEquals(2, item.getValues().size());
        Assert.assertEquals("123", item.getValues().get(0));
        Assert.assertEquals("keywords", item.getValues().get(1));
    }
    /*-------------------------------------------------Set keywords---------------------------------------------------*/

    /*------------------------------------------------Get formImageId-------------------------------------------------*/

    @Test
    public void testGetFormImageId_forNotIMAGE_FILE_UPLOAD() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        item.setValue("123");

        Assert.assertEquals("123", item.getValue());
        Assert.assertEquals(1, item.getValues().size());


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        Assert.assertEquals(null, manager.getFormImageId());
    }

    @Test
    public void testGetFormImageId() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValue(null);

        Assert.assertEquals(null, item.getValue());
        Assert.assertEquals(0, item.getValues().size());


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        Assert.assertEquals(null, manager.getFormImageId());
    }

    @Test
    public void testGetFormImageId_withOldImageId() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValue("123");

        Assert.assertEquals("123", item.getValue());
        Assert.assertEquals(1, item.getValues().size());
        Assert.assertEquals("123", item.getValues().get(0));


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        Assert.assertEquals(123, manager.getFormImageId().intValue());
    }

    @Test
    public void testGetFormImageId_withOldImageIdAndKeywords() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValues(Arrays.asList("123", "keyword"));

        Assert.assertEquals(2, item.getValues().size());
        Assert.assertEquals("123", item.getValues().get(0));
        Assert.assertEquals("keyword", item.getValues().get(1));


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        Assert.assertEquals(123, manager.getFormImageId().intValue());
    }
    /*------------------------------------------------Get formImageId-------------------------------------------------*/

    /*-------------------------------------------------Get keywords---------------------------------------------------*/

    @Test
    public void testGetFormImageKeywords_forNotIMAGE_FILE_UPLOAD() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.VIDEO_FILE_UPLOAD);
        item.setValue("value");

        Assert.assertEquals("value", item.getValue());
        Assert.assertEquals(1, item.getValues().size());


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        Assert.assertEquals("", manager.getFormImageAlt());
    }

    @Test
    public void testGetFormImageKeywords() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValue(null);

        Assert.assertEquals(null, item.getValue());
        Assert.assertEquals(0, item.getValues().size());


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        Assert.assertEquals("", manager.getFormImageAlt());
    }

    @Test
    public void testGetFormImageKeywords_withOldImageId() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValue("123");

        Assert.assertEquals("123", item.getValue());
        Assert.assertEquals(1, item.getValues().size());
        Assert.assertEquals("123", item.getValues().get(0));


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        Assert.assertEquals("", manager.getFormImageAlt());
    }

    @Test
    public void testGetFormImageKeywords_withOldImageIdAndKeywords() {
        final FilledFormItem item = new FilledFormItem();
        item.setFormItemName(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValues(Arrays.asList("123", "old keyword"));

        Assert.assertEquals(2, item.getValues().size());
        Assert.assertEquals("123", item.getValues().get(0));
        Assert.assertEquals("old keyword", item.getValues().get(1));


        final FilledFormItemManager manager = new FilledFormItemManager(item);
        Assert.assertEquals("old keyword", manager.getFormImageAlt());
    }
    /*-------------------------------------------------Get keywords---------------------------------------------------*/

    @Test
    public void testGetStringValueForCSV_NAME() throws Exception {
        final FilledFormItem item = TestUtil.createFilledFormItem(FormItemName.NAME);
        item.setValue("value");
        Assert.assertEquals("value", new FilledFormItemManager(item).getStringValueForDataExport());
    }

    @Test
    public void testGetStringValueForCSV_VIDEO_FILE_UPLOAD() throws Exception {
        final Video video = TestUtil.createVideoForSite("videoName", -1);
        final FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), -1);
        final FilledFormItem item = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        item.setValue(formVideo.getFormVideoId());
        Assert.assertEquals("videoName", new FilledFormItemManager(item).getStringValueForDataExport());
    }

    @Test
    public void testGetStringValueForCSV_VIDEO_FILE_UPLOAD_withoutFormVideo() throws Exception {
        final FilledFormItem item = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        item.setValue(-1);
        Assert.assertEquals("", new FilledFormItemManager(item).getStringValueForDataExport());
    }

    @Test
    public void testGetStringValueForCSV_VIDEO_FILE_UPLOAD_withoutVideo() throws Exception {
        final FormVideo formVideo = TestUtil.createFormVideo(-1, -1);
        final FilledFormItem item = TestUtil.createFilledFormItem(FormItemName.VIDEO_FILE_UPLOAD);
        item.setValue(formVideo.getFormVideoId());
        Assert.assertEquals("", new FilledFormItemManager(item).getStringValueForDataExport());
    }

    @Test
    public void testGetStringValueForCSV_IMAGE_FILE_UPLOAD() throws Exception {
        final FormFile formFile = TestUtil.createFormFile("formFileName", -1);
        final FilledFormItem item = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        item.setValue(formFile.getFormFileId());
        Assert.assertEquals("formFileName", new FilledFormItemManager(item).getStringValueForDataExport());
    }

    @Test
    public void testGetStringValueForCSV_SPECIAL() throws Exception {
        final FormFile formFile = TestUtil.createFormFile("formFileName", -1);
        final FilledFormItem item = TestUtil.createFilledFormItem(FormItemName.PAGE_BREAK);
        item.setValue(formFile.getFormFileId());
        Assert.assertEquals("", new FilledFormItemManager(item).getStringValueForDataExport());
    }

    @Test
    public void testGetStringValueForCSV_ACCESS_GROUPS() throws Exception {
        final FormFile formFile = TestUtil.createFormFile("formFileName", -1);
        final FilledFormItem item = TestUtil.createFilledFormItem(FormItemName.PRODUCT_ACCESS_GROUPS);
        item.setValue(formFile.getFormFileId());
        Assert.assertEquals("", new FilledFormItemManager(item).getStringValueForDataExport());
    }

    @Test
    public void testGetStringValueForCSV_CHECKBOX() throws Exception {
        final FormFile formFile = TestUtil.createFormFile("formFileName", -1);
        final FilledFormItem item = TestUtil.createFilledFormItem(FormItemName.REGISTER);
        item.setValue(formFile.getFormFileId());
        Assert.assertEquals("", new FilledFormItemManager(item).getStringValueForDataExport());
    }

    @Test
    public void testGetStringValueForCSV_RADIOBUTTON() throws Exception {
        final FormFile formFile = TestUtil.createFormFile("formFileName", -1);
        final FilledFormItem item = TestUtil.createFilledFormItem(FormItemName.SMOKING);
        item.setValue(formFile.getFormFileId());
        Assert.assertEquals("", new FilledFormItemManager(item).getStringValueForDataExport());
    }

    @Test
    public void testGetStringValueForCSV_TEXT_AREA() throws Exception {
        final FilledFormItem item = TestUtil.createFilledFormItem(FormItemName.PERSONAL_BIO);
        item.setValue("adfasdfasdf,asdf,asdfasdfas,dfasdfasd");
        Assert.assertEquals("\"adfasdfasdf,asdf,asdfasdfas,dfasdfasd\"", new FilledFormItemManager(item).getStringValueForDataExport());
    }

    @Test
    public void testGetFormattedValues_INTERNAL_LINK() {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.INTERNAL_LINK);
        final String link = "testlink";
        filledFormItem.setValue(link);

        Assert.assertEquals("<a href=\"" + link + "\">" + link + "</a>",
                new FilledFormItemManager(filledFormItem).getFormattedValue(null));
    }
    
    @Test
    public void testGetFormattedValues_PRICE_withoutTaxes() {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.PRICE);
        final double price = 28.8;
        filledFormItem.setValue("" + price);

        Assert.assertEquals("$28.80 ",
                new FilledFormItemManager(filledFormItem).getFormattedValue(null));
    }
    
    @Test
    public void testGetFormattedValues_URL() {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.URL);
        String link = "testlink";
        filledFormItem.setValue(link);

        Assert.assertEquals("<a href=\"http://" + link + "\">" + link + "</a>",
                new FilledFormItemManager(filledFormItem).getFormattedValue(null));

        link = "http://www.testlink.com";
        filledFormItem.setValue(link);

        Assert.assertEquals("<a href=\"" + link + "\">" + link + "</a>",
                new FilledFormItemManager(filledFormItem).getFormattedValue(null));
    }

    @Test
    public void testGetFormattedValues_AUDIO() {
        final Site site = TestUtil.createSite();
        final FormFile formFile = TestUtil.createFormFile("t.mp3", site.getSiteId());

        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        filledFormItem.setValue(formFile.getFormFileId());

        Assert.assertEquals("t.mp3 <a target='_blank' href=\"/resourceGetter.action?" +
                "resourceId=1&resourceSizeId=0&resourceSizeAdditionId=0&resourceGetterType=FORM_FILE&" +
                "resourceVersion=0&resourceDownload=false\">Play audio file</a>",
                new FilledFormItemManager(filledFormItem).getFormattedValue(null));
    }
    
    @Test
    public void testGetFormattedValues_AUDIO_WithoutFile() {
        final FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        filledFormItem.setValue(-1);

        Assert.assertEquals("",
                new FilledFormItemManager(filledFormItem).getFormattedValue(null));
    }


}
