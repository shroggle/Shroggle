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
import com.shroggle.presentation.upload.UploadedFiles;
import com.shroggle.util.FileNameUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.entity.ResourceType;
import com.shroggle.entity.Video;
import net.sourceforge.stripes.action.*;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/uploadVideo.action")
public class UploadVideoAction extends Action implements UploadedFiles {

    @DefaultHandler
    public Resolution upload() {
        try {
            if (fileData == null || siteId < 1) {
                throw new UnsupportedOperationException();
            }
            final SourceVideoFileCreator sourceVideoFileCreator = new SourceVideoFileCreator(siteId, FileNameUtil.createExtension(fileData.getFileName()), fileData.getFileName(), fileData.getInputStream());
            video = sourceVideoFileCreator.execute();              
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

    public FileBean getFileData() {
        return fileData;
    }

    public void setFileData(FileBean fileData) {
        this.fileData = fileData;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    private static final Logger logger = Logger.getLogger(UploadVideoAction.class.getName());
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private FileBean fileData;
    private int siteId;
    private Video video;

    public String getResourceUrl() {
        return null;
    }

    public int getResourceId() {
        return video.getResourceId();
    }

    public ResourceType getResourceType() {
        return video.getResourceType();
    }
}