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


import com.shroggle.entity.*;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.upload.UploadedFiles;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import net.sourceforge.stripes.action.*;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/uploadFormFiles.action")
public class UploadFormFilesAction extends Action implements UploadedFiles {

    @DefaultHandler
    public Resolution upload() {
        try {
            if (fileData == null || filledFormId < 1) {
                throw new UnsupportedOperationException();
            }
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    final FilledForm filledForm = persistance.getFilledFormById(filledFormId);
                    final DraftForm form = persistance.getFormById(filledForm.getFormId());
                    final FilledFormItem filledFormItem = FilledFormManager.getFilledFormItemByItemPosition(filledForm, position);
                    final int siteId = form.getSiteId();
                    Integer formImageId = new CreateFormFileUtil().createFormFile(fileData, siteId);
                    if (formImageId == null) {
                        return;
                    }
                    if (filledFormItem.getFormItemName() == FormItemName.IMAGE_FILE_UPLOAD) {
                        final FilledFormItemManager manager = new FilledFormItemManager(filledFormItem);
                        manager.setFormImageId(formImageId);
                    } else {
                        filledFormItem.setValue(formImageId);
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
            return resolutionCreator.forwardToUrl("/SWFUpload/error.jsp");
        }
        return resolutionCreator.forwardToUrl("/SWFUpload/success.jsp");
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

