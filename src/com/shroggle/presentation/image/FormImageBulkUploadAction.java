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


import com.shroggle.entity.*;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.upload.UploadedFiles;
import com.shroggle.presentation.site.CreateFormFileUtil;
import com.shroggle.presentation.site.UploadFormFilesAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.logic.visitor.VisitorManager;
import net.sourceforge.stripes.action.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/formImageBulkUpload.action")
public class FormImageBulkUploadAction extends Action  implements UploadedFiles {

    @DefaultHandler
    public Resolution upload() {
        try {
            if (fileData == null || formId < 1 || formItemId < 1 || loginedUserId < 1) {
                throw new UnsupportedOperationException();
            }
            persistanceTransaction.execute(new Runnable() {

                public void run() {
                    final DraftForm form = persistance.getFormById(formId);
                    if (form == null) {
                        return;
                    }
                    final int siteId = form.getSiteId();
                    Integer formImageId = new CreateFormFileUtil().createFormFile(fileData, siteId);

                    DraftFormItem formItem = persistance.getFormItemById(formItemId);
                    if (formImageId == null || formItem == null) {
                        return;
                    }
                    final List<FilledFormItem> filledFormItems = new ArrayList<FilledFormItem>();

                    final FilledFormItem imageFilledFormItem = new FilledFormItem();
                    imageFilledFormItem.setFormItemId(formItem.getFormItemId());
                    imageFilledFormItem.setFormItemName(formItem.getFormItemName());
                    imageFilledFormItem.setItemName(formItem.getItemName());
                    imageFilledFormItem.setPosition(formItem.getPosition());
                    FilledFormItemManager manager = new FilledFormItemManager(imageFilledFormItem);
                    manager.setFormImageId(formImageId);
                    manager.setFormImageAlt(" ");
                    filledFormItems.add(imageFilledFormItem);

                    DraftFormItem dateAdded = FormManager.getFormItemByFormItemName(FormItemName.DATE_ADDED, form);
                    if (dateAdded != null) {
                        final FilledFormItem dateAddedFilledFormItem = new FilledFormItem();
                        dateAddedFilledFormItem.setFormItemId(dateAdded.getFormItemId());
                        dateAddedFilledFormItem.setFormItemName(dateAdded.getFormItemName());
                        dateAddedFilledFormItem.setItemName(dateAdded.getItemName());
                        dateAddedFilledFormItem.setPosition(dateAdded.getPosition());
                        dateAddedFilledFormItem.setValues(FilledFormManager.getPrefilledOption(FormItemName.DATE_ADDED, form.getFormId()));
                        filledFormItems.add(dateAddedFilledFormItem);
                    }

                    DraftFormItem sortOrderFormItem = FormManager.getFormItemByFormItemName(FormItemName.SORT_ORDER, form);
                    if (sortOrderFormItem != null) {
                        final FilledFormItem sortOrderFilledFormItem = new FilledFormItem();
                        sortOrderFilledFormItem.setFormItemId(sortOrderFormItem.getFormItemId());
                        sortOrderFilledFormItem.setFormItemName(sortOrderFormItem.getFormItemName());
                        sortOrderFilledFormItem.setItemName(sortOrderFormItem.getItemName());
                        sortOrderFilledFormItem.setPosition(sortOrderFormItem.getPosition());
                        sortOrderFilledFormItem.setValues(FilledFormManager.getPrefilledOption(FormItemName.SORT_ORDER, form.getFormId()));
                        filledFormItems.add(sortOrderFilledFormItem);
                    }

                    final FormItem nameFormItem = FormManager.getFormItemByFormItemType(FormItemType.TEXT_INPUT_FIELD, form);
                    if (nameFormItem != null) {
                        final FilledFormItem nameFilledFormItem = new FilledFormItem();
                        nameFilledFormItem.setFormItemId(nameFormItem.getFormItemId());
                        nameFilledFormItem.setFormItemName(nameFormItem.getFormItemName());
                        nameFilledFormItem.setItemName(nameFormItem.getItemName());
                        nameFilledFormItem.setPosition(nameFormItem.getPosition());
                        nameFilledFormItem.setValue(fileData.getFileName());
                        filledFormItems.add(nameFilledFormItem);
                    }

                    final User user = persistance.getUserById(loginedUserId);

                    final FormItem enteredByFormItem = FormManager.getFormItemByFormItemName(FormItemName.ENTERED_BY, form);
                    if (enteredByFormItem != null) {
                        final FilledFormItem enteredByFilledFormItem = new FilledFormItem();
                        enteredByFilledFormItem.setFormItemId(enteredByFormItem.getFormItemId());
                        enteredByFilledFormItem.setFormItemName(enteredByFormItem.getFormItemName());
                        enteredByFilledFormItem.setItemName(enteredByFormItem.getItemName());
                        enteredByFilledFormItem.setPosition(enteredByFormItem.getPosition());
                        final StringBuilder stringBuilder = new StringBuilder("");
                        if(user != null){
                            stringBuilder.append(StringUtil.getEmptyOrString(user.getLastName()));
                            stringBuilder.append(" ");
                            stringBuilder.append(StringUtil.getEmptyOrString(user.getFirstName()));
                        }
                        enteredByFilledFormItem.setValue(stringBuilder.toString());
                        filledFormItems.add(enteredByFilledFormItem);
                    }
                    new VisitorManager(user).addFilledFormToVisitor(filledFormItems, form);
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


    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public int getFormItemId() {
        return formItemId;
    }

    public void setFormItemId(int formItemId) {
        this.formItemId = formItemId;
    }

    public FileBean getFileData() {
        return fileData;
    }

    public void setFileData(FileBean fileData) {
        this.fileData = fileData;
    }

    public Integer getLoginedUserId() {
        return loginedUserId;
    }

    public void setLoginedUserId(Integer loginedUserId) {
        this.loginedUserId = loginedUserId;
    }

    private int formId;
    private int formItemId;
    private FileBean fileData;
    private Integer loginedUserId;

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private static final Logger logger = Logger.getLogger(UploadFormFilesAction.class.getName());

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

