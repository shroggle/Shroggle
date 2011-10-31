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

import com.shroggle.entity.BackgroundImage;
import com.shroggle.entity.Image;
import com.shroggle.entity.ImageForVideo;
import com.shroggle.exception.ImageReadException;
import com.shroggle.exception.ImageWriteException;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.SourceInputStreamNullException;
import com.shroggle.util.IOUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.sun.media.jai.codec.SeekableStream;
import net.sourceforge.stripes.action.FileBean;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author Stasuk Artem
 */
public class UploadImageToSiteCommand {

    public void execute() {
        try {
            executeTryCatch();
        } catch (final FileSystemException exception) {
            throw new ImageWriteException(exception);
        } finally {
            if (fileBean != null) {
                try {
                    fileBean.delete();
                } catch (final IOException exception) {
                    logger.log(Level.WARNING, "Can`t delete temporary image file!");
                }
            }
        }
    }

    private void executeTryCatch() {
        if (persistance.getSite(siteId) == null) {
            throw new SiteNotFoundException();
        }

        if (fileBean == null) {
            throw new SourceInputStreamNullException();
        }

        final String sourceExtension = IOUtil.getExt(fileBean.getFileName());

        final BufferedImage sourceImage;

        try {
//            Reading image via JAI.
//            sourceImage = JAI.create("stream", SeekableStream.wrapInputStream(fileBean.getInputStream(), true)).getAsBufferedImage();
            sourceImage = ImageIO.read(fileBean.getInputStream());
            if (sourceImage == null) {
                throw new ImageReadException();
            }
        } catch (IOException exception) {
            throw new ImageReadException(exception);
        }

        if (isBackgroundImage) {
            backgroundImage = new BackgroundImage();
            backgroundImage.setSiteId(siteId);
            backgroundImage.setDescription(description);
            backgroundImage.setKeywords(keywords);
            backgroundImage.setTitle(title);
            backgroundImage.setSourceExtension(sourceExtension);
            backgroundImage.setThumbnailExtension(sourceExtension);
            backgroundImage.setCreated(new Date());
        } else if (isImageForVideo) {
            imageForVideo = new ImageForVideo();
            imageForVideo.setSiteId(siteId);
            imageForVideo.setSourceExtension(sourceExtension);
            imageForVideo.setThumbnailExtension(sourceExtension);
            imageForVideo.setTitle(title);
            imageForVideo.setCreated(new Date());
        } else {
            image = new Image();
            image.setSiteId(siteId);
            image.setDescription(description);
            image.setKeywords(keywords);
            image.setName(title);
            image.setSourceExtension(sourceExtension);
            image.setThumbnailExtension(sourceExtension);
            image.setCreated(new Date());
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                if (isBackgroundImage) {
                    persistance.putBackgroundImage(backgroundImage);
                } else if (isImageForVideo) {
                    persistance.putImageForVideo(imageForVideo);
                } else {
                    persistance.putImage(image);
                }

                try {
                    if (isBackgroundImage) {
                        fileSystem.setResourceStream(backgroundImage, fileBean.getInputStream());

                    } else if (isImageForVideo) {
                        fileSystem.setResourceStream(imageForVideo, fileBean.getInputStream());
                        imageForVideo.setWidth(sourceImage.getWidth());
                        imageForVideo.setHeight(sourceImage.getHeight());
                    } else {
                        fileSystem.setResourceStream(image, fileBean.getInputStream());
                        image.setWidth(sourceImage.getWidth());
                        image.setHeight(sourceImage.getHeight());
                    }
                    fileBean.delete();
                } catch (final IOException exception) {
                    throw new ImageReadException(exception);
                }
            }

        });
    }

    public Image getImage() {
        return image;
    }

    public BackgroundImage getBackgroundImage() {
        return backgroundImage;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackgroundImage(boolean backgroundImage) {
        isBackgroundImage = backgroundImage;
    }

    public void setImageForVideo(boolean imageForVideo) {
        isImageForVideo = imageForVideo;
    }

    public ImageForVideo getImageForVideo() {
        return imageForVideo;
    }

    public void setFileBean(final FileBean fileBean) {
        this.fileBean = fileBean;
    }

    private Image image;
    private BackgroundImage backgroundImage;
    private ImageForVideo imageForVideo;
    private FileBean fileBean;
    private int siteId;
    private String title;
    private String keywords;
    private String description;
    private boolean isBackgroundImage = false;
    private boolean isImageForVideo = false;
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private Logger logger = Logger.getLogger(UploadImageToSiteCommand.class.getName());

}
