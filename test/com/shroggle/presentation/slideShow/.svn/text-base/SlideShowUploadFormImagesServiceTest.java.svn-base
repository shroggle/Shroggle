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
package com.shroggle.presentation.slideShow;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.FilledFormNotFoundException;
import com.shroggle.exception.FormFilterNotFoundException;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class SlideShowUploadFormImagesServiceTest {

    @Test
    public void executeWithForm() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = new DraftSlideShow();
        slideShow.setSiteId(site.getSiteId());
        slideShow.setName("aa");
        persistance.putItem(slideShow);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final DraftFormItem imageFormItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, customForm, 0);
        customForm.setFormItems(new ArrayList<DraftFormItem>() {{
            add(imageFormItem);
        }});

        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        final FilledFormItem imageFilledItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        imageFilledItem.setFormItemId(imageFormItem.getFormItemId());
        final FormFile formFile = TestUtil.createFormFile("img1", site.getSiteId());
        imageFilledItem.setValue(formFile.getFormFileId());
        filledForm.setFilledFormItems(new ArrayList<FilledFormItem>() {{
            add(imageFilledItem);
        }});

        final SlideShowUploadFormImagesRequest request = new SlideShowUploadFormImagesRequest();
        request.setSlideShowId(slideShow.getId());
        request.setSelectedFormId(customForm.getFormId());

        final SlideShowUploadFormImagesResponse response = service.execute(request);
        Assert.assertEquals(1, response.getNumberOfImagesUploaded());
        Assert.assertTrue(!response.getManageImagesDivHtml().isEmpty());

        Assert.assertEquals(1, slideShow.getImages().size());
        Assert.assertEquals(formFile.getFormFileId(), slideShow.getImages().get(0).getImageId());
        Assert.assertEquals(SlideShowImageType.FORM_IMAGE, slideShow.getImages().get(0).getImageType());
        Assert.assertEquals(1, slideShow.getImages().get(0).getPosition());
    }

    @Test
    public void executeWithFormAndExistingImagesInSlideShow() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = TestUtil.createSlideShow(site);

        final SlideShowImage slideShowImage = TestUtil.createSlideShowImage(slideShow);
        slideShowImage.setPosition(1);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final DraftFormItem imageFormItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, customForm, 0);
        customForm.setFormItems(new ArrayList<DraftFormItem>() {{
            add(imageFormItem);
        }});

        final FilledForm filledForm = TestUtil.createFilledForm(customForm);
        final FilledFormItem imageFilledItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        imageFilledItem.setFormItemId(imageFormItem.getFormItemId());
        final FormFile formFile = TestUtil.createFormFile("img1", site.getSiteId());
        imageFilledItem.setValue(formFile.getFormFileId());
        filledForm.setFilledFormItems(new ArrayList<FilledFormItem>() {{
            add(imageFilledItem);
        }});

        final SlideShowUploadFormImagesRequest request = new SlideShowUploadFormImagesRequest();
        request.setSlideShowId(slideShow.getId());
        request.setSelectedFormId(customForm.getFormId());

        final SlideShowUploadFormImagesResponse response = service.execute(request);
        Assert.assertEquals(1, response.getNumberOfImagesUploaded());
        Assert.assertTrue(!response.getManageImagesDivHtml().isEmpty());

        Assert.assertEquals(2, slideShow.getImages().size());
        Assert.assertEquals(formFile.getFormFileId(), slideShow.getImages().get(1).getImageId());
        Assert.assertEquals(SlideShowImageType.FORM_IMAGE, slideShow.getImages().get(1).getImageType());
        Assert.assertEquals(2, slideShow.getImages().get(1).getPosition());
    }

    @Test
    public void executeWithFormAndSelectedFormItemId() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = new DraftSlideShow();
        slideShow.setSiteId(site.getSiteId());
        slideShow.setName("aa");
        persistance.putItem(slideShow);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final DraftFormItem imageFormItem1 = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, customForm, 0);
        final DraftFormItem imageFormItem2 = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, customForm, 0);
        customForm.setFormItems(new ArrayList<DraftFormItem>() {{
            add(imageFormItem1);
            add(imageFormItem2);
        }});

        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        // Adding first form item and first image
        final FilledFormItem imageFilledItem1_1 = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        imageFilledItem1_1.setFormItemId(imageFormItem1.getFormItemId());
        final FormFile formFile1_1 = TestUtil.createFormFile("img1", site.getSiteId());
        imageFilledItem1_1.setValue(formFile1_1.getFormFileId());
        filledForm.setFilledFormItems(new ArrayList<FilledFormItem>() {{
            add(imageFilledItem1_1);
        }});

        // Adding second form item and first images
        final FilledFormItem imageFilledItem2_1 = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        imageFilledItem2_1.setFormItemId(imageFormItem2.getFormItemId());
        final FormFile formFile2_1 = TestUtil.createFormFile("img1", site.getSiteId());
        imageFilledItem2_1.setValue(formFile2_1.getFormFileId());
        filledForm.setFilledFormItems(new ArrayList<FilledFormItem>() {{
            add(imageFilledItem2_1);
        }});

        final FilledForm filledForm1 = TestUtil.createFilledForm(customForm);

        // Adding second form item and second images
        final FilledFormItem imageFilledItem2_2 = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        imageFilledItem2_2.setFormItemId(imageFormItem2.getFormItemId());
        final FormFile formFile2_2 = TestUtil.createFormFile("img1", site.getSiteId());
        imageFilledItem2_2.setValue(formFile2_2.getFormFileId());
        filledForm1.setFilledFormItems(new ArrayList<FilledFormItem>() {{
            add(imageFilledItem2_2);
        }});

        final SlideShowUploadFormImagesRequest request = new SlideShowUploadFormImagesRequest();
        request.setSlideShowId(slideShow.getId());
        request.setSelectedFormId(customForm.getFormId());
        request.setSelectedImageFormItemId(imageFormItem2.getFormItemId());

        final SlideShowUploadFormImagesResponse response = service.execute(request);
        Assert.assertEquals(2, response.getNumberOfImagesUploaded());
        Assert.assertTrue(!response.getManageImagesDivHtml().isEmpty());

        Assert.assertEquals(2, slideShow.getImages().size());
        Assert.assertEquals(formFile2_1.getFormFileId(), slideShow.getImages().get(0).getImageId());
        Assert.assertEquals(SlideShowImageType.FORM_IMAGE, slideShow.getImages().get(0).getImageType());
        Assert.assertEquals(1, slideShow.getImages().get(0).getPosition());
        Assert.assertEquals(formFile2_2.getFormFileId(), slideShow.getImages().get(1).getImageId());
        Assert.assertEquals(SlideShowImageType.FORM_IMAGE, slideShow.getImages().get(1).getImageType());
        Assert.assertEquals(2, slideShow.getImages().get(1).getPosition());
    }

    @Test
    public void executeWithFilter() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = new DraftSlideShow();
        slideShow.setSiteId(site.getSiteId());
        slideShow.setName("aa");
        persistance.putItem(slideShow);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final DraftFormItem imageFormItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, customForm, 0);
        customForm.setFormItems(new ArrayList<DraftFormItem>() {{
            add(imageFormItem);
        }});

        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        // Adding first image
        final FilledFormItem imageFilledItem1 = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        imageFilledItem1.setFormItemId(imageFormItem.getFormItemId());
        final FormFile formFile1 = TestUtil.createFormFile("img1", site.getSiteId());
        imageFilledItem1.setValue(formFile1.getFormFileId());
        filledForm.setFilledFormItems(new ArrayList<FilledFormItem>() {{
            add(imageFilledItem1);
        }});

        // Adding second image
        final FilledFormItem imageFilledItem2 = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        imageFilledItem2.setFormItemId(imageFormItem.getFormItemId());
        final FormFile formFile2 = TestUtil.createFormFile("img1", site.getSiteId());
        imageFilledItem2.setValue(formFile2.getFormFileId());
        filledForm.setFilledFormItems(new ArrayList<FilledFormItem>() {{
            add(imageFilledItem2);
        }});

        final DraftFormFilter filter = TestUtil.createFormFilter(customForm);
        final DraftFormFilterRule rule = new DraftFormFilterRule();
        rule.setFormItemId(imageFormItem.getFormItemId());
        rule.setCriteria(new ArrayList<String>() {{
            add("100");
            add("200");
        }});
        persistance.putFormFilterRule(rule);
        filter.addRule(rule);

        final SlideShowUploadFormImagesRequest request = new SlideShowUploadFormImagesRequest();
        request.setSlideShowId(slideShow.getId());
        request.setSelectedFormId(customForm.getFormId());
        request.setSelectedFilterId(filter.getFormFilterId());

        final SlideShowUploadFormImagesResponse response = service.execute(request);
        Assert.assertEquals(0, response.getNumberOfImagesUploaded());
        Assert.assertTrue(!response.getManageImagesDivHtml().isEmpty());
    }
    
    @Test(expected = FormFilterNotFoundException.class)
    public void executeWithNotFoundFilter() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = new DraftSlideShow();
        slideShow.setSiteId(site.getSiteId());
        slideShow.setName("aa");
        persistance.putItem(slideShow);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final DraftFormItem imageFormItem = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, customForm, 0);
        customForm.setFormItems(new ArrayList<DraftFormItem>() {{
            add(imageFormItem);
        }});

        final FilledForm filledForm = TestUtil.createFilledForm(customForm);

        // Adding first image
        final FilledFormItem imageFilledItem1 = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        imageFilledItem1.setFormItemId(imageFormItem.getFormItemId());
        final FormFile formFile1 = TestUtil.createFormFile("img1", site.getSiteId());
        imageFilledItem1.setValue(formFile1.getFormFileId());
        filledForm.setFilledFormItems(new ArrayList<FilledFormItem>() {{
            add(imageFilledItem1);
        }});

        // Adding second image
        final FilledFormItem imageFilledItem2 = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        imageFilledItem2.setFormItemId(imageFormItem.getFormItemId());
        final FormFile formFile2 = TestUtil.createFormFile("img1", site.getSiteId());
        imageFilledItem2.setValue(formFile2.getFormFileId());
        filledForm.setFilledFormItems(new ArrayList<FilledFormItem>() {{
            add(imageFilledItem2);
        }});

        final SlideShowUploadFormImagesRequest request = new SlideShowUploadFormImagesRequest();
        request.setSlideShowId(slideShow.getId());
        request.setSelectedFormId(customForm.getFormId());
        request.setSelectedFilterId(-1);

        final SlideShowUploadFormImagesResponse response = service.execute(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeWithFormWithoutImageItem() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = new DraftSlideShow();
        slideShow.setSiteId(site.getSiteId());
        slideShow.setName("aa");
        persistance.putItem(slideShow);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);

        final SlideShowUploadFormImagesRequest request = new SlideShowUploadFormImagesRequest();
        request.setSlideShowId(slideShow.getId());
        request.setSelectedFormId(customForm.getFormId());

        service.execute(request);
    }

    @Test(expected = FormNotFoundException.class)
    public void executeWithoutForm() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = new DraftSlideShow();
        slideShow.setSiteId(site.getSiteId());
        slideShow.setName("aa");
        persistance.putItem(slideShow);

        final SlideShowUploadFormImagesRequest request = new SlideShowUploadFormImagesRequest();
        request.setSlideShowId(slideShow.getId());
        request.setSelectedFormId(-1);

        service.execute(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeWithFormAndWithNotFoundSelectedImageItem() throws IOException, ServletException {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        final DraftSlideShow slideShow = new DraftSlideShow();
        slideShow.setSiteId(site.getSiteId());
        slideShow.setName("aa");
        persistance.putItem(slideShow);

        final DraftCustomForm customForm = TestUtil.createCustomForm(site);
        final DraftFormItem imageFormItem1 = TestUtil.createFormItem(FormItemName.IMAGE_FILE_UPLOAD, customForm, 0);
        customForm.setFormItems(new ArrayList<DraftFormItem>() {{
            add(imageFormItem1);
        }});

        final SlideShowUploadFormImagesRequest request = new SlideShowUploadFormImagesRequest();
        request.setSlideShowId(slideShow.getId());
        request.setSelectedFormId(customForm.getFormId());
        request.setSelectedImageFormItemId(-1);

        service.execute(request);
    }

    private SlideShowUploadFormImagesService service = new SlideShowUploadFormImagesService();
    private Persistance persistance = ServiceLocator.getPersistance();

}
