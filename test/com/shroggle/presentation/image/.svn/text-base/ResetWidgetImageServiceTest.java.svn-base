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
package com.shroggle.presentation.image;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class ResetWidgetImageServiceTest {

    ResetWidgetImageService service = new ResetWidgetImageService();

    @Before
    public void before() {
        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));
    }

    @Test
    public void testExecute() throws Exception {
        TestUtil.createUserAndLogin();
        final Site site = TestUtil.createSite();
        final Page page = TestUtil.createPage(site);
        final WidgetItem widgetItem = TestUtil.createWidgetImage(1, 2);
        new PageManager(page).addWidget(widgetItem);
        final DraftImage draftImageOld = (DraftImage)(widgetItem.getDraftItem());


        final FunctionalWidgetInfo info = service.execute(widgetItem.getWidgetId());


        Assert.assertNotNull(info);
        Assert.assertEquals(widgetItem.getWidgetId(), info.getWidgetId().intValue());

        final DraftImage draftImage = (DraftImage)(widgetItem.getDraftItem());
        Assert.assertNotSame(draftImageOld, draftImage);
        Assert.assertEquals(null, draftImage.getThumbnailWidth());
        Assert.assertEquals(null, draftImage.getThumbnailHeight());
        Assert.assertEquals("", draftImage.getName());
        Assert.assertEquals(true, draftImage.isSaveRatio());
        Assert.assertEquals(null, draftImage.getAligment());
        Assert.assertEquals(null, draftImage.getImageId());
        Assert.assertEquals(null, draftImage.getRollOverImageId());
        Assert.assertEquals("", draftImage.getDescription());
        Assert.assertEquals(false, draftImage.isShowDescriptionOnMouseOver());
        Assert.assertEquals(null, draftImage.getTitle());
        Assert.assertEquals(TitlePosition.NONE, draftImage.getTitlePosition());
        Assert.assertEquals(false, draftImage.isLabelIsALinnk());
        Assert.assertEquals(false, draftImage.isImageIsALinnk());
        Assert.assertEquals(false, draftImage.isCustomizeWindowSize());
        Assert.assertEquals(0, draftImage.getImageFileId().intValue());
        Assert.assertEquals(0, draftImage.getNewWindowWidth());
        Assert.assertEquals(0, draftImage.getNewWindowHeight());
        Assert.assertEquals(ImageFileType.PDF, draftImage.getImageFileType());
        Assert.assertEquals(ImageLinkType.EXTERNAL_URL, draftImage.getImageLinkType());
        Assert.assertEquals(ImagePdfDisplaySettings.OPEN_IN_NEW_WINDOW, draftImage.getImagePdfDisplaySettings());
        Assert.assertEquals(ImageAudioDisplaySettings.PLAY_IN_CURRENT_WINDOW, draftImage.getImageAudioDisplaySettings());
        Assert.assertEquals(ImageFlashDisplaySettings.OPEN_IN_NEW_WINDOW, draftImage.getImageFlashDisplaySettings());
        Assert.assertEquals("", draftImage.getExternalUrl());
        Assert.assertEquals(ExternalUrlDisplaySettings.OPEN_IN_NEW_WINDOW, draftImage.getExternalUrlDisplaySettings());
        Assert.assertEquals(-1, draftImage.getInternalPageId());
        Assert.assertEquals(site.getSiteId(), draftImage.getSiteId());
        Assert.assertEquals(InternalPageDisplaySettings.OPEN_IN_NEW_WINDOW, draftImage.getInternalPageDisplaySettings());
        Assert.assertEquals("", draftImage.getTextArea());
        Assert.assertEquals(TextAreaDisplaySettings.OPEN_IN_NEW_WINDOW, draftImage.getTextAreaDisplaySettings());
        Assert.assertEquals(null, draftImage.getExtension());
        Assert.assertEquals(0, draftImage.getMargin());
        Assert.assertEquals(null, draftImage.getRollOverExtension());
    }

    @Test(expected = UserNotLoginedException.class)
    public void testExecute_notLogined() throws Exception {
        service.execute(-1);
    }

    @Test(expected = WidgetNotFoundException.class)
    public void testExecute_withoutWidgetImage() throws Exception {
        TestUtil.createUserAndLogin();

        service.execute(-1);
    }
}
