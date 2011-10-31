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


import com.shroggle.entity.FormFile;
import com.shroggle.util.FileNameUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.FileBean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.logging.Logger;


/**
 * @author Balakirev Anatoliy
 */


public class CreateFormFileUtil {

    public Integer createFormFile(FileBean fileData, final int siteId) {//execute in transaction
        final InputStream sourceInputStream;
        try {
            sourceInputStream = fileData.getInputStream();
        } catch (Exception e) {
            return null;
        }
        final String sourceExtension = FileNameUtil.createExtension(fileData.getFileName());
        if (sourceInputStream == null || sourceExtension == null) {
            return null;
        }
        Integer width = null;
        Integer height = null;
        try {
            final BufferedImage sourceBufferedImage = ImageIO.read(sourceInputStream);
            if (sourceBufferedImage != null) {
                width = sourceBufferedImage.getWidth();
                height = sourceBufferedImage.getHeight();
            }
        } catch (Exception exception) {
            logger.warning("Unable to get file width/height.");
        } finally {
            try {
                sourceInputStream.close();
            } catch (Exception e) {
                logger.warning("Unable to close input stream.");
            }
        }
        final FormFile formFile = new FormFile();
        formFile.setSiteId(siteId);
        formFile.setWidth(width);
        formFile.setHeight(height);
        formFile.setSourceExtension(sourceExtension);
        formFile.setSourceName(fileData.getFileName());
        persistance.putFormFile(formFile);
        try {
            fileSystem.setResourceStream(formFile, fileData.getInputStream());
        } catch (Exception exception) {
            return null;
        } finally {
            try {
                fileData.delete();
            } catch (Exception e) {
                logger.warning("Unable to delete fileData.");
            }
        }
        return formFile.getFormFileId();
    }

    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}