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

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import org.junit.runner.RunWith;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.logic.form.FormFileTypeForDeletion;
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.entity.*;
import com.shroggle.presentation.MockWebContext;
import net.sourceforge.stripes.mock.MockHttpServletRequest;

/**
 * @author Balakirev Anatoliy
 *         Date: 08.09.2009
 */
@RunWith(TestRunnerWithMockServices.class)
public class RemoveFormFileServiceTest {

    RemoveFormFileService service = new RemoveFormFileService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute_VIDEO() {
        Site site = TestUtil.createSite();
        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setValue("" + formVideo.getFormVideoId());

        String result = service.execute(filledFormItem.getItemId(), FormFileTypeForDeletion.VIDEO);
        Assert.assertEquals("ok", result);
        formVideo = persistance.getFormVideoById(formVideo.getFormVideoId());
        Assert.assertNull(formVideo.getVideoId());
        Assert.assertNotNull(formVideo);
        Assert.assertNotNull(formVideo.getImageId());
        Assert.assertNotNull(persistance.getVideoById(video.getVideoId()));
        Assert.assertEquals(FilledFormItemManager.getIntValue(filledFormItem), formVideo.getFormVideoId());
    }

    @Test
    public void testExecute_IMAGE() {
        Site site = TestUtil.createSite();
        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setValue("" + formVideo.getFormVideoId());

        String result = service.execute(filledFormItem.getItemId(), FormFileTypeForDeletion.IMAGE);
        Assert.assertEquals("ok", result);
        formVideo = persistance.getFormVideoById(formVideo.getFormVideoId());
        Assert.assertNull(formVideo.getImageId());
        Assert.assertNull(persistance.getFormFileById(image.getFormFileId()));
        Assert.assertNotNull(formVideo.getVideoId());
        Assert.assertNotNull(formVideo);
        Assert.assertNotNull(persistance.getVideoById(video.getVideoId()));
        Assert.assertEquals(FilledFormItemManager.getIntValue(filledFormItem), formVideo.getFormVideoId());
    }

    @Test
    public void testExecute_FORM_FILE() {
        Site site = TestUtil.createSite();
        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setValue("" + image.getFormFileId());

        String result = service.execute(filledFormItem.getItemId(), FormFileTypeForDeletion.FORM_FILE);
        Assert.assertEquals("ok", result);
        Assert.assertNull(persistance.getFormFileById(image.getFormFileId()));
        Assert.assertEquals(0, FilledFormItemManager.getIntValue(filledFormItem));
    }

    @Test
    public void testExecute_withoutFormVideo() {
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);

        String result = service.execute(filledFormItem.getItemId(), FormFileTypeForDeletion.IMAGE);
        Assert.assertEquals("error", result);
    }

    @Test
    public void testExecute_withWrongFilledFormItem() {
        Site site = TestUtil.createSite();
        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setValue("" + formVideo.getFormVideoId());

        String result = service.execute(-1, FormFileTypeForDeletion.IMAGE);
        Assert.assertEquals("error", result);
    }

    @Test
    public void testExecute_withoutFilledFormItem() {
        Site site = TestUtil.createSite();
        FormFile image = TestUtil.createFormFile("image", site.getSiteId());
        Video video = TestUtil.createVideoForSite("video", site.getSiteId());
        FormVideo formVideo = TestUtil.createFormVideo(video.getVideoId(), image.getFormFileId());
        FilledFormItem filledFormItem = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        filledFormItem.setValue("" + formVideo.getFormVideoId());

        String result = service.execute(null, FormFileTypeForDeletion.IMAGE);
        Assert.assertEquals("error", result);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
}
