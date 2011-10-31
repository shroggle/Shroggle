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
package com.shroggle.presentation.background;

import net.sourceforge.stripes.action.*;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.upload.UploadedFiles;
import com.shroggle.presentation.video.UploadVideoAction;
import com.shroggle.presentation.image.UploadImageToSiteCommand;
import com.shroggle.entity.ResourceType;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 *         Date: 23.09.2009
 */
@UrlBinding(value = "/uploadBackgroundImage.action")
public class UploadBackgroundImageAction extends Action implements UploadedFiles {

    @DefaultHandler
    public Resolution upload() {
        try {
            if (fileData == null || siteId < 1) {
                throw new UnsupportedOperationException();
            }
            command.setSiteId(siteId);
            command.setBackgroundImage(true);
            command.setImageForVideo(false);
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
            return new ForwardResolution("/SWFUpload/error.jsp");
        }
        return new ForwardResolution("/SWFUpload/success.jsp");
    }


    public void setFileData(FileBean fileData) {
        this.fileData = fileData;
    }

    public void setSiteId(final int siteId) {
        this.siteId = siteId;
    }

    public String getResourceUrl() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getResourceId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ResourceType getResourceType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private static final Logger logger = Logger.getLogger(UploadVideoAction.class.getName());
    private final UploadImageToSiteCommand command = new UploadImageToSiteCommand();
    private FileBean fileData;
    private int siteId;
}
