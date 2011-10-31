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

import com.shroggle.presentation.Action;
import com.shroggle.presentation.upload.UploadedFiles;
import com.shroggle.util.FileNameUtil;
import com.shroggle.util.resource.provider.ResourceGetterType;
import com.shroggle.entity.Image;
import com.shroggle.entity.ResourceType;
import net.sourceforge.stripes.action.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Stasuk Artem, Balakirev Anatoliy
 */
@UrlBinding("/uploadImage.action")
public class UploadImageToSiteAction extends Action implements ActionBean, UploadedFiles {

    @DefaultHandler
    public Resolution upload() throws IOException {
        try {
            if (fileData == null || siteId < 1) {
                throw new UnsupportedOperationException();
            }
            command.setSiteId(siteId);
            command.setBackgroundImage(false);
            command.setImageForVideo(false);
            command.setTitle(FileNameUtil.getAvailableImageName(fileData.getFileName(), siteId));
            command.setFileBean(fileData);
            command.execute();
            Image image = command.getImage();
            if (image == null) {
                throw new UnsupportedOperationException();
            }
            imageId = image.getImageId();
            imageUrl = ResourceGetterType.IMAGE.getUrl(image.getImageId());
            resourceType = image.getResourceType();
        } catch (final Exception exception) {
            logger.log(Level.SEVERE, "Error while writing file", exception);
            try {
                this.getContext().getResponse().getOutputStream().println("-200");
            } catch (final Exception exception1) {
                logger.log(Level.SEVERE, "Error while writing file", exception1);
            }
            return new ForwardResolution("/SWFUpload/error.jsp");
        }
        return new ForwardResolution("/SWFUpload/success.jsp");
    }

    public FileBean getFileData() {
        return fileData;
    }

    public void setFileData(FileBean fileData) {
        this.fileData = fileData;
    }

    public String getResourceUrl() {
        return imageUrl;
    }

    public int getResourceId() {
        return imageId;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    private final UploadImageToSiteCommand command = new UploadImageToSiteCommand();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private FileBean fileData;
    private String imageUrl;
    public int siteId;
    public int imageId;
    private ResourceType resourceType;

}
