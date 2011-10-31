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


import com.shroggle.entity.ImageFile;
import com.shroggle.entity.ImageFileType;
import com.shroggle.entity.ResourceType;
import com.shroggle.exception.ExtensionNullOrEmptyException;
import com.shroggle.exception.ImageWriteException;
import com.shroggle.exception.SourceInputStreamNullException;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.upload.UploadedFiles;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.FileNameUtil;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import net.sourceforge.stripes.action.*;

import java.io.InputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/uploadImageFiles.action")
public class UploadImageFilesAction extends Action implements UploadedFiles {

    @DefaultHandler
    public Resolution upload() {
        try {
            if (fileData == null || siteId < 1) {
                throw new UnsupportedOperationException();
            }
            final InputStream sourceInputStream = fileData.getInputStream();
            final int index = fileData.getFileName().lastIndexOf('.');
            final String sourceExtension = fileData.getFileName().substring(index + 1).toLowerCase();
            if (sourceInputStream == null) {
                throw new SourceInputStreamNullException();
            }
            if (sourceExtension == null || sourceExtension.trim().isEmpty()) {
                throw new ExtensionNullOrEmptyException();
            }
            final ImageFile imageFile = new ImageFile();
            imageFile.setSiteId(siteId);
            imageFile.setSourceExtension(sourceExtension);
            imageFile.setSourceName(FileNameUtil.getAvailableImageFileName(fileData.getFileName(), siteId, imageFileType));
            imageFile.setImageFileType(imageFileType);
            imageFile.setCreated(new Date());
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    persistance.putImageFile(imageFile);
                    try {
                        fileSystem.setResourceStream(imageFile, sourceInputStream);
                    } catch (FileSystemException exception) {
                        throw new ImageWriteException(exception);
                    }
                }
            });
        } catch (Exception exception1) {
            try {
                this.getContext().getResponse().getOutputStream().println("-200");
            } catch (Exception exception2) {
                logger.log(Level.SEVERE, "Error while writing file", exception2);
            }
            logger.log(Level.SEVERE, "Error while writing file", exception1);
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

    public ImageFileType getImageFileType() {
        return imageFileType;
    }

    public void setImageFileType(ImageFileType imageFileType) {
        this.imageFileType = imageFileType;
    }

    private int siteId;
    private ImageFileType imageFileType;
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private static final Logger logger = Logger.getLogger(UploadImageFilesAction.class.getName());
    private FileBean fileData;

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