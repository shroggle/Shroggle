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
package com.shroggle.presentation.slideShow;

import com.shroggle.entity.*;
import com.shroggle.exception.SlideShowNotFoundException;
import com.shroggle.logic.slideShow.SlideShowManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.image.UploadImageToSiteCommand;
import com.shroggle.presentation.upload.UploadedFiles;
import com.shroggle.util.FileNameUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.resource.provider.ResourceGetterType;
import net.sourceforge.stripes.action.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dmitry.solomadin
 */
@UrlBinding("/slideShowUploadImage.action")
public class SlideShowUploadImageAction extends Action implements ActionBean, UploadedFiles {

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

            // Creating slide show image based on image.
            final DraftSlideShow slideShow = (DraftSlideShow) persistance.getDraftItem(slideShowId);

            if (slideShow == null) {
                throw new SlideShowNotFoundException("Cannot find slide show by Id=" + slideShowId);
            }

            persistanceTransaction.execute(new Runnable() {
                @Override
                public void run() {
                    final DraftSlideShowImage slideShowImage = new DraftSlideShowImage();
                    slideShowImage.setImageType(SlideShowImageType.IMAGE);
                    slideShowImage.setImageId(imageId);
                    slideShowImage.setSlideShow(slideShow);
                    slideShowImage.setPosition(new SlideShowManager(slideShow).getMaxImagePosition() + 1);

                    persistance.putSlideShowImage(slideShowImage);

                    slideShow.addSlideShowImage(slideShowImage);
                }
            });

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
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

    private FileBean fileData;
    private String imageUrl;
    public int siteId;
    public int imageId;
    public int slideShowId;
    private ResourceType resourceType;

}
