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

import com.shroggle.entity.Icon;
import com.shroggle.entity.ResourceType;
import com.shroggle.exception.ImageWriteException;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.upload.UploadedFiles;
import com.shroggle.util.Dimension;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.resource.provider.ResourceGetterType;
import net.sourceforge.stripes.action.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
// todo. Add tests. Tolik
@UrlBinding("/uploadIcon.action")
public class UploadIconAction extends Action implements ActionBean, UploadedFiles {

    @DefaultHandler
    public Resolution upload() {
        try {
            if (fileData == null) {
                return error(null);
            }

            final InputStream sourceInputStream = fileData.getInputStream();
            final int index = fileData.getFileName().lastIndexOf('.');
            final String sourceExtension = fileData.getFileName().substring(index + 1).toLowerCase();

            if (sourceInputStream == null || sourceExtension == null || sourceExtension.trim().isEmpty()) {
                return error(null);
            }

            final Icon icon = new Icon();
            icon.setExtension(sourceExtension);
            icon.setName(fileData.getFileName());

            Dimension dimension;
            try {
                final BufferedImage sourceImage = ImageIO.read(fileData.getInputStream());
                dimension = new Dimension(sourceImage.getWidth(), sourceImage.getHeight());
            } catch (final Exception exception) {
                dimension = new Dimension(Icon.getDefaultWidth(), Icon.getDefaultHeight());
            }

            icon.setWidth(dimension.getWidth());
            icon.setHeight(dimension.getHeight());

            persistanceTransaction.execute(new Runnable() {

                @Override
                public void run() {
                    persistance.putIcon(icon);
                    iconId = icon.getIconId();
                    iconUrl = ResourceGetterType.ICON.getUrl(icon.getIconId());
                    try {
                        fileSystem.setResourceStream(icon, sourceInputStream);
                    } catch (final FileSystemException exception) {
                        throw new ImageWriteException(exception);
                    }
                }

            });
        } catch (final Exception exception) {
            return error(exception);
        }
        return new ForwardResolution("/SWFUpload/success.jsp");
    }

    private Resolution error(final Exception cause) {
        try {
            this.getContext().getResponse().getOutputStream().println("-200");
        } catch (final Exception exception) {
            logger.log(Level.SEVERE, "Error while writing file", exception);
        }
        logger.log(Level.SEVERE, "Error while writing file", cause);
        return new ForwardResolution("/SWFUpload/error.jsp");
    }

    public FileBean getFileData() {
        return fileData;
    }

    public void setFileData(FileBean fileData) {
        this.fileData = fileData;
    }


    public String getResourceUrl() {
        return iconUrl;
    }

    public int getResourceId() {
        return iconId;
    }

    public ResourceType getResourceType() {
        return ResourceType.ICON;
    }

    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private FileBean fileData;
    private String iconUrl;
    public int iconId;
}
