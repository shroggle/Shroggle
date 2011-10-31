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
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.entity.*;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.TestAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystemMockException;
import junit.framework.Assert;
import net.sourceforge.stripes.action.FileBean;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

public class UploadFormFilesActionTest extends TestAction<UploadFormFilesAction> {

    public UploadFormFilesActionTest() {
        super(UploadFormFilesAction.class, true);
    }

    @Test
    public void execute_AUDIO_FILE_UPLOAD() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftCustomForm form = TestUtil.createCustomForm(site);
        FilledFormItem item = TestUtil.createFilledFormItem(FormItemName.AUDIO_FILE_UPLOAD);
        item.setPosition(1);
        item.setItemName("name");
        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setFilledFormItems(Arrays.asList(item));


        File file = TestUtil.getTempImageFile();
        FileBean image = new FileBean(file, "file", "test.png");

        actionOrService.setFileData(image);
        actionOrService.setFilledFormId(filledForm.getFilledFormId());
        actionOrService.setPosition(item.getPosition());

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();
        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());


        Assert.assertEquals(filledForm.getFilledFormItems().size(), 1);
        Assert.assertEquals("name", filledForm.getFilledFormItems().get(0).getItemName());
        Assert.assertEquals(FormItemName.AUDIO_FILE_UPLOAD, filledForm.getFilledFormItems().get(0).getFormItemName());
        Assert.assertEquals(1, filledForm.getFilledFormItems().get(0).getValues().size());
        Assert.assertNotNull(persistance.getFormFileById(FilledFormItemManager.getIntValue(filledForm.getFilledFormItems().get(0))));
    }

    @Test
    public void execute_IMAGE_FILE_UPLOAD() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        DraftCustomForm form = TestUtil.createCustomForm(site);
        FilledFormItem item = TestUtil.createFilledFormItem(FormItemName.IMAGE_FILE_UPLOAD);
        item.setPosition(1);
        item.setItemName("name");
        item.setValues(Arrays.asList("1234", "keywords"));
        FilledForm filledForm = TestUtil.createFilledForm(form.getFormId());
        filledForm.setFilledFormItems(Arrays.asList(item));


        File file = TestUtil.getTempImageFile();
        FileBean image = new FileBean(file, "file", "test.png");

        actionOrService.setFileData(image);
        actionOrService.setFilledFormId(filledForm.getFilledFormId());
        actionOrService.setPosition(item.getPosition());

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();
        Assert.assertEquals("/SWFUpload/success.jsp", resolutionMock.getForwardToUrl());


        Assert.assertEquals(filledForm.getFilledFormItems().size(), 1);
        Assert.assertEquals("name", filledForm.getFilledFormItems().get(0).getItemName());
        Assert.assertEquals(FormItemName.IMAGE_FILE_UPLOAD, filledForm.getFilledFormItems().get(0).getFormItemName());
        Assert.assertEquals(2, filledForm.getFilledFormItems().get(0).getValues().size());

        final FilledFormItemManager manager = new FilledFormItemManager(filledForm.getFilledFormItems().get(0));
        Assert.assertNotNull(manager.getFormImageId());
        Assert.assertNotNull(persistance.getFormFileById(FilledFormItemManager.getIntValue(filledForm.getFilledFormItems().get(0))));
        Assert.assertNotNull(persistance.getFormFileById(manager.getFormImageId()));
        Assert.assertEquals("keywords", manager.getFormImageAlt());
    }

    @Test
    public void executeWithoutData() {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);


        DraftCustomForm form = TestUtil.createCustomForm(site);

        TestUtil.createFilledForm(form.getFormId());

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/error.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithException() throws Exception {
        final Site site = TestUtil.createSite();
        TestUtil.createUserAndUserOnSiteRightAndLogin(site, SiteAccessLevel.ADMINISTRATOR);

        File file = TestUtil.getTempImageFile();
        FileBean image = new FileBean(file, "file", "test.png");

        actionOrService.setFileData(image);
        ServiceLocator.setFileSystem(new FileSystemMockException());

        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.upload();

        Assert.assertEquals("/SWFUpload/error.jsp", resolutionMock.getForwardToUrl());
    }

}