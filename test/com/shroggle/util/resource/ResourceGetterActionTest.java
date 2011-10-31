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

package com.shroggle.util.resource;

import com.shroggle.entity.FormFile;
import com.shroggle.presentation.ResolutionMock;
import com.shroggle.presentation.TestAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.provider.ResourceGetterType;
import junit.framework.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class ResourceGetterActionTest extends TestAction<ResourceGetterAction> {

    public ResourceGetterActionTest() {
        super(ResourceGetterAction.class);
    }

    @Test
    public void executeWithoutResourceType() throws Exception {
        actionOrService.setResourceId(0);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeImageWithNotFound() throws Exception {
        actionOrService.setResourceId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.IMAGE);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeImageFileWithNotFound() throws Exception {
        actionOrService.setResourceId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.IMAGE_FILE);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeBackgroundImageWithNotFound() throws Exception {
        actionOrService.setResourceId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.BACKGROUND_IMAGE);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeBackgroundImageThumbnailWithNotFound() throws Exception {
        actionOrService.setResourceId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.BACKGROUND_IMAGE_THUMBNAIL);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeFormFileWithNotFound() throws Exception {
        actionOrService.setResourceId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.FORM_FILE);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeFormFileThumbnailWithNotFound() throws Exception {
        actionOrService.setResourceId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.FORM_FILE_THUMBNAIL);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeGalleryWithNotFound() throws Exception {
        actionOrService.setResourceId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.GALLERY);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeGalleryWithNotFoundSize() throws Exception {
        final FormFile formFile = new FormFile();
        persistance.putFormFile(formFile);

        actionOrService.setResourceId(formFile.getResourceId());
        actionOrService.setResourceSizeId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.GALLERY);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeGalleryDataWithNotFoundSize() throws Exception {
        final FormFile formFile = new FormFile();
        persistance.putFormFile(formFile);

        actionOrService.setResourceId(formFile.getResourceId());
        actionOrService.setResourceSizeId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.GALLERY_DATA);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executePreviewImageFormFileWithNotFoundSize() throws Exception {
        final FormFile formFile = new FormFile();
        formFile.setSourceExtension("png");
        persistance.putFormFile(formFile);

        fileSystem.setResourceStream(formFile, new ByteArrayInputStream(new byte[1]));

        actionOrService.setResourceId(formFile.getResourceId());
        actionOrService.setResourceSizeId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.PREVIEW_IMAGE_FORM_FILE);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isResourceGetter());
    }

    @Test
    public void executeWithNotFoundData() throws Exception {
        final FormFile formFile = new FormFile();
        formFile.setSourceExtension("png");
        persistance.putFormFile(formFile);

        actionOrService.setResourceId(formFile.getResourceId());
        actionOrService.setResourceSizeId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.PREVIEW_IMAGE_FORM_FILE);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeGalleryDataWithNotFound() throws Exception {
        actionOrService.setResourceId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.GALLERY_DATA);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeImageForVideoWithNotFound() throws Exception {
        actionOrService.setResourceId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.IMAGE_FOR_VIDEO);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeImageThumbnailWithNotFound() throws Exception {
        actionOrService.setResourceId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.IMAGE_FOR_VIDEO_THUMBNAIL);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executePreviewImageFormFileWithNotFound() throws Exception {
        actionOrService.setResourceId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.PREVIEW_IMAGE_FORM_FILE);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    @Test
    public void executeImageForVideoThumbnailWithNotFound() throws Exception {
        actionOrService.setResourceId(1);
        actionOrService.setResourceGetterType(ResourceGetterType.IMAGE_THUMBNAIL);
        final ResolutionMock resolutionMock = (ResolutionMock) actionOrService.execute();

        Assert.assertTrue(resolutionMock.isNotFound());
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}