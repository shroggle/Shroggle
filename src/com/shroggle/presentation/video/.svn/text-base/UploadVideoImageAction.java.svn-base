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

package com.shroggle.presentation.video;

import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.upload.UploadedFiles;
import com.shroggle.presentation.image.UploadImageToSiteCommand;
import com.shroggle.util.ServiceLocator;
import com.shroggle.entity.ResourceType;
import net.sourceforge.stripes.action.*;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Balakirev Anatoliy
 */
@UrlBinding(value = "/uploadVideoImage.action")
public class UploadVideoImageAction extends Action implements UploadedFiles {

    @DefaultHandler
    public Resolution upload() {
        try {
            if (fileData == null || siteId < 1) {
                throw new UnsupportedOperationException();
            }
            final UploadImageToSiteCommand command = new UploadImageToSiteCommand();
            command.setSiteId(siteId);
            command.setImageForVideo(true);
            command.setBackgroundImage(false);
            command.setFileBean(fileData);
            command.setTitle(fileData.getFileName());
            command.execute();
        } catch (Exception exception) {
            try {
                this.getContext().getResponse().getOutputStream().println("-200");
            } catch (Exception exception2) {
                logger.log(Level.SEVERE, "Error while writing file", exception2);
            }
            logger.log(Level.SEVERE, "Error while writing file", exception);
            return resolutionCreator.forwardToUrl("/SWFUpload/error.jsp");
        }
        return resolutionCreator.forwardToUrl("/SWFUpload/success.jsp");
    }


    public void setFileData(FileBean fileData) {
        this.fileData = fileData;
    }

    public void setSiteId(final int siteId) {
        this.siteId = siteId;
    }

    private static final Logger logger = Logger.getLogger(UploadVideoAction.class.getName());
    private ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private FileBean fileData;
    private int siteId;

    public String getResourceUrl() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getResourceId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ResourceType getResourceType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}