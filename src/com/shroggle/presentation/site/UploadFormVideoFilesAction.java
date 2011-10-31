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

import com.shroggle.logic.form.FilledFormManager;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.FileBean;
import com.shroggle.entity.*;
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.logic.gallery.GalleriesManager;
import com.shroggle.logic.video.FlvVideoManager;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.FileNameUtil;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.upload.UploadedFiles;
import com.shroggle.presentation.video.SourceVideoFileCreator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/uploadFormVideoFiles.action")
public class UploadFormVideoFilesAction extends Action implements UploadedFiles {
    @DefaultHandler
    public Resolution upload() {
        try {
            if (fileData == null || filledFormId < 1) {
                throw new UnsupportedOperationException();
            }
            final FilledForm filledForm = persistance.getFilledFormById(filledFormId);
            final DraftForm form = persistance.getFormById(filledForm.getFormId());
            final FilledFormItem filledFormItem = FilledFormManager.getFilledFormItemByItemPosition(filledForm, position);
            final FormVideo formVideo = getFormVideoOrCreateNew(filledFormItem);

            final int siteId = form.getSiteId();
            final SourceVideoFileCreator sourceVideoFileCreator = new SourceVideoFileCreator(siteId, FileNameUtil.createExtension(fileData.getFileName()), fileData.getFileName(), fileData.getInputStream());
            final Video video = sourceVideoFileCreator.execute();

            persistanceTransaction.execute(
                    new PersistanceTransactionContext<Void>() {
                        public Void execute() {
                            if (formItemName == FormItemName.VIDEO_FILE_UPLOAD) {
                                try {
                                    video.setFilledFormId(filledForm.getFilledFormId());
                                    video.setFilledFormItemId(filledFormItem.getItemId());
                                    formVideo.setVideoId(video.getVideoId());
                                    FlvVideoManager.getFlvVideoOrCreateNew(video.getVideoId(), video.getSourceWidth(), video.getSourceHeight(), formVideo.getQuality(), video.getSiteId());
                                } catch (Exception exception) {
                                    logger.log(Level.SEVERE, "Can`t create video file!", exception);
                                }
                            } else if (formItemName == FormItemName.IMAGE_FILE_UPLOAD) {
                                formVideo.setImageId((new CreateFormFileUtil().createFormFile(fileData, siteId)));
                            }
                            return null;
                        }
                    });
            ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                public void run() {
                    GalleriesManager.updateVideoFilesForGalleriesThatUseCurentForm(filledForm.getFormId());
                }
            });
        } catch (Exception exception1) {
            try {
                this.getContext().getResponse().getOutputStream().println("-200");
            } catch (Exception exception2) {
                logger.log(Level.SEVERE, "Error while writing file", exception2);
            }
            logger.log(Level.SEVERE, "Error while writing file", exception1);
            return resolutionCreator.forwardToUrl("/SWFUpload/error.jsp");
        }
        return resolutionCreator.forwardToUrl("/SWFUpload/success.jsp");
    }

    private FormVideo getFormVideoOrCreateNew(final FilledFormItem filledFormItem) {
        final FormVideo formVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItem));
        if (formVideo == null) {
            return persistanceTransaction.execute(
                    new PersistanceTransactionContext<FormVideo>() {
                        public FormVideo execute() {
                            final FormVideo newFormVideo = new FormVideo();
                            persistance.putFormVideo(newFormVideo);
                            filledFormItem.setValue("" + newFormVideo.getFormVideoId());
                            return newFormVideo;
                        }
                    });
        } else {
            return formVideo;
        }
    }


    public FormItemName getFormItemName() {
        return formItemName;
    }

    public void setFormItemName(FormItemName formItemName) {
        this.formItemName = formItemName;
    }

    public int getFilledFormId() {
        return filledFormId;
    }

    public void setFilledFormId(int filledFormId) {
        this.filledFormId = filledFormId;
    }

    public FileBean getFileData() {
        return fileData;
    }

    public void setFileData(FileBean fileData) {
        this.fileData = fileData;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private FormItemName formItemName;
    private int filledFormId;
    private int position;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private static final Logger logger = Logger.getLogger(UploadFormFilesAction.class.getName());
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
