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
package com.shroggle.presentation.site;

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.TestAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import junit.framework.Assert;
import net.sourceforge.stripes.action.FileBean;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 *         Date: 07.09.2009
 */
public class UploadFormVideoFilesActionTest extends TestAction<UploadFormVideoFilesAction> {

    public UploadFormVideoFilesActionTest() {
        super(UploadFormVideoFilesAction.class, true);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

    @Test
    public void execute_VIDEO_FILE_UPLOAD() throws Exception {
        final Site site = TestUtil.createSite();
        DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "");

        FormVideo formVideo = TestUtil.createFormVideo(null, null);
        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.ACADEMIC_DEGREE, FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItems.get(0).setPosition(0);
        filledFormItems.get(1).setPosition(10);
        filledFormItems.get(1).setValue("" + formVideo.getFormVideoId());

        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setFilledFormItems(filledFormItems);

        File file = TestUtil.getTempVideoFile();
        FileBean fileBean = new FileBean(file, "file", "test.avi");

        actionOrService.setFileData(fileBean);
        actionOrService.setFilledFormId(filledForm.getFilledFormId());
        actionOrService.setFormItemName(filledFormItems.get(1).getFormItemName());
        actionOrService.setPosition(filledFormItems.get(1).getPosition());


        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();


        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());
        FormVideo newFormVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItems.get(1)));
        Assert.assertNotNull(newFormVideo);
        Assert.assertEquals(formVideo.getQuality(), newFormVideo.getQuality());
        Assert.assertEquals(formVideo.getFormVideoId(), newFormVideo.getFormVideoId());
        Assert.assertNotNull(newFormVideo.getVideoId());
        Assert.assertNull(newFormVideo.getImageId());
        Video video = persistance.getVideoById(newFormVideo.getVideoId());
        Assert.assertNotNull(video);
        Assert.assertEquals(filledForm.getFilledFormId(), video.getFilledFormId().intValue());
        Assert.assertEquals(filledFormItems.get(1).getItemId(), video.getFilledFormItemId().intValue());
        FlvVideo videoFLV = persistance.getFlvVideo(newFormVideo.getVideoId(), video.getSourceWidth(), video.getSourceHeight(), newFormVideo.getQuality());
        Assert.assertNotNull(videoFLV);
    }


    @Test
    public void execute_withTwoVIDEO_FILE_UPLOAD_field() throws Exception {
        final Site site = TestUtil.createSite();
        DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "");

        FormVideo formVideo = TestUtil.createFormVideo(null, null);
        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.VIDEO_FILE_UPLOAD, FormItemName.VIDEO_FILE_UPLOAD);
        filledFormItems.get(0).setPosition(0);
        String value = null;
        filledFormItems.get(0).setValue(value);
        filledFormItems.get(1).setPosition(10);
        filledFormItems.get(1).setValue("" + formVideo.getFormVideoId());

        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setFilledFormItems(filledFormItems);

        File file = TestUtil.getTempVideoFile();
        FileBean fileBean = new FileBean(file, "file", "test.avi");

        actionOrService.setFileData(fileBean);
        actionOrService.setFilledFormId(filledForm.getFilledFormId());
        actionOrService.setFormItemName(filledFormItems.get(1).getFormItemName());
        actionOrService.setPosition(filledFormItems.get(1).getPosition());


        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();


        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNull(persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItems.get(0))));

        FormVideo newFormVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItems.get(1)));
        Assert.assertNotNull(newFormVideo);
        Assert.assertEquals(formVideo.getQuality(), newFormVideo.getQuality());
        Assert.assertEquals(formVideo.getFormVideoId(), newFormVideo.getFormVideoId());
        Assert.assertNotNull(newFormVideo.getVideoId());
        Assert.assertNull(newFormVideo.getImageId());
        Video video = persistance.getVideoById(newFormVideo.getVideoId());
        Assert.assertNotNull(video);
        Assert.assertEquals(filledForm.getFilledFormId(), video.getFilledFormId().intValue());
        Assert.assertEquals(filledFormItems.get(1).getItemId(), video.getFilledFormItemId().intValue());
        FlvVideo videoFLV = persistance.getFlvVideo(newFormVideo.getVideoId(), video.getSourceWidth(), video.getSourceHeight(), newFormVideo.getQuality());
        Assert.assertNotNull(videoFLV);
    }

    @Test
    public void execute_VIDEO_FILE_UPLOAD_withoutFormVideo() throws Exception {
        final Site site = TestUtil.createSite();
        DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "");

        // FormVideo formVideo = TestUtil.createFormVideo(null, null);
        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.ACADEMIC_DEGREE, FormItemName.VIDEO_FILE_UPLOAD);
        String value = null;
        filledFormItems.get(0).setPosition(0);
        filledFormItems.get(0).setValue(value);
        filledFormItems.get(1).setPosition(10);
        filledFormItems.get(1).setValue(value);

        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setFilledFormItems(filledFormItems);

        File file = TestUtil.getTempVideoFile();
        FileBean fileBean = new FileBean(file, "file", "test.avi");

        actionOrService.setFileData(fileBean);
        actionOrService.setFilledFormId(filledForm.getFilledFormId());
        actionOrService.setFormItemName(filledFormItems.get(1).getFormItemName());
        actionOrService.setPosition(filledFormItems.get(1).getPosition());


        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();


        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());
        FormVideo newFormVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItems.get(1)));
        Assert.assertNotNull(newFormVideo);
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, newFormVideo.getQuality());
        Assert.assertNotNull(newFormVideo.getVideoId());
        Assert.assertNull(newFormVideo.getImageId());
        Video video = persistance.getVideoById(newFormVideo.getVideoId());
        Assert.assertNotNull(video);
        Assert.assertEquals(filledForm.getFilledFormId(), video.getFilledFormId().intValue());
        Assert.assertEquals(filledFormItems.get(1).getItemId(), video.getFilledFormItemId().intValue());
        FlvVideo videoFLV = persistance.getFlvVideo(newFormVideo.getVideoId(), video.getSourceWidth(), video.getSourceHeight(), newFormVideo.getQuality());
        Assert.assertNotNull(videoFLV);
    }


    @Test
    public void execute_IMAGE_FILE_UPLOAD() throws Exception {
        final Site site = TestUtil.createSite();
        DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "");

        FormVideo formVideo = TestUtil.createFormVideo(null, null);
        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.ACADEMIC_DEGREE, FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItems.get(0).setPosition(0);
        filledFormItems.get(1).setPosition(10);
        filledFormItems.get(1).setValue("" + formVideo.getFormVideoId());

        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setFilledFormItems(filledFormItems);

        File file = TestUtil.getTempVideoFile();
        FileBean fileBean = new FileBean(file, "file", "test.avi");

        actionOrService.setFileData(fileBean);
        actionOrService.setFilledFormId(filledForm.getFilledFormId());
        actionOrService.setFormItemName(filledFormItems.get(1).getFormItemName());
        actionOrService.setPosition(filledFormItems.get(1).getPosition());


        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();


        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());
        FormVideo newFormVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItems.get(1)));
        Assert.assertNotNull(newFormVideo);
        Assert.assertEquals(formVideo.getQuality(), newFormVideo.getQuality());
        Assert.assertEquals(formVideo.getFormVideoId(), newFormVideo.getFormVideoId());
        Assert.assertNull(newFormVideo.getVideoId());
        Assert.assertNotNull(newFormVideo.getImageId());
        FormFile formFile = persistance.getFormFileById(newFormVideo.getImageId());
        Assert.assertNotNull(formFile);
    }


    @Test
    public void execute_withTwoIMAGE_FILE_UPLOAD_field() throws Exception {
        final Site site = TestUtil.createSite();
        DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "");

        FormVideo formVideo = TestUtil.createFormVideo(null, null);
        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.IMAGE_FILE_UPLOAD, FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItems.get(0).setPosition(0);
        String value = null;
        filledFormItems.get(0).setValue(value);
        filledFormItems.get(1).setPosition(10);
        filledFormItems.get(1).setValue("" + formVideo.getFormVideoId());

        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setFilledFormItems(filledFormItems);

        File file = TestUtil.getTempVideoFile();
        FileBean fileBean = new FileBean(file, "file", "test.avi");

        actionOrService.setFileData(fileBean);
        actionOrService.setFilledFormId(filledForm.getFilledFormId());
        actionOrService.setFormItemName(filledFormItems.get(1).getFormItemName());
        actionOrService.setPosition(filledFormItems.get(1).getPosition());


        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();


        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());
        Assert.assertNull(persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItems.get(0))));

        FormVideo newFormVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItems.get(1)));
        Assert.assertNotNull(newFormVideo);
        Assert.assertEquals(formVideo.getQuality(), newFormVideo.getQuality());
        Assert.assertEquals(formVideo.getFormVideoId(), newFormVideo.getFormVideoId());
        Assert.assertNull(newFormVideo.getVideoId());
        Assert.assertNotNull(newFormVideo.getImageId());
        FormFile formFile = persistance.getFormFileById(newFormVideo.getImageId());
        Assert.assertNotNull(formFile);
    }

    @Test
    public void execute_IMAGE_FILE_UPLOAD_withoutFormVideo() throws Exception {
        final Site site = TestUtil.createSite();
        DraftCustomForm form = TestUtil.createCustomForm(site.getSiteId(), "");

        List<FilledFormItem> filledFormItems = TestUtil.createFilledFormItems(FormItemName.ACADEMIC_DEGREE, FormItemName.IMAGE_FILE_UPLOAD);
        String value = null;
        filledFormItems.get(0).setPosition(0);
        filledFormItems.get(0).setValue(value);
        filledFormItems.get(1).setPosition(10);
        filledFormItems.get(1).setValue(value);

        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setFilledFormItems(filledFormItems);

        File file = TestUtil.getTempVideoFile();
        FileBean fileBean = new FileBean(file, "file", "test.avi");

        actionOrService.setFileData(fileBean);
        actionOrService.setFilledFormId(filledForm.getFilledFormId());
        actionOrService.setFormItemName(filledFormItems.get(1).getFormItemName());
        actionOrService.setPosition(filledFormItems.get(1).getPosition());


        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();


        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());
        FormVideo newFormVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItems.get(1)));
        Assert.assertNotNull(newFormVideo);
        Assert.assertEquals(FlvVideo.DEFAULT_VIDEO_QUALITY, newFormVideo.getQuality());
        Assert.assertNull(newFormVideo.getVideoId());
        Assert.assertNotNull(newFormVideo.getImageId());
        FormFile formFile = persistance.getFormFileById(newFormVideo.getImageId());
        Assert.assertNotNull(formFile);
    }
}
