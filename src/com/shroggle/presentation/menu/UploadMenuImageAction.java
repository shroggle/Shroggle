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
package com.shroggle.presentation.menu;

import com.shroggle.presentation.Action;
import com.shroggle.presentation.upload.UploadedFiles;
import com.shroggle.util.IOUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.resource.provider.ResourceGetterType;
import com.shroggle.entity.MenuImage;
import com.shroggle.entity.ResourceType;
import com.shroggle.exception.ImageReadException;
import net.sourceforge.stripes.action.*;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.image.BufferedImage;

/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/uploadMenuImage.action")
public class UploadMenuImageAction extends Action implements ActionBean, UploadedFiles {

    @DefaultHandler
    public Resolution upload() throws IOException {
        try {
            if (fileData == null || persistance.getSite(siteId) == null) {
                throw new UnsupportedOperationException();
            }
            final BufferedImage sourceImage;
            try {
                sourceImage = ImageIO.read(fileData.getInputStream());
                if (sourceImage == null) {
                    throw new ImageReadException();
                }
            } catch (IOException exception) {
                throw new ImageReadException(exception);
            }
            final MenuImage menuImage = new MenuImage();
            menuImage.setName(fileData.getFileName());
            final String extension = IOUtil.getExt(fileData.getFileName());
            menuImage.setExtension(extension);
            menuImage.setSiteId(siteId);
            persistanceTransaction.execute(new Runnable() {

                @Override
                public void run() {
                   persistance.putMenuImage(menuImage);
                    try {
                        fileSystem.setResourceStream(menuImage, fileData.getInputStream());
                        fileData.delete();
                    } catch (final IOException exception) {
                        throw new ImageReadException(exception);
                    }
                }

            });
            imageId = menuImage.getMenuImageId();
            imageUrl = ResourceGetterType.MENU_IMAGE.getUrl(menuImage.getMenuImageId());
            resourceType = menuImage.getResourceType();
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

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }    

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private FileBean fileData;
    private String imageUrl;
    private int siteId;
    private int imageId;
    private ResourceType resourceType;

}
