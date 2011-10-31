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

import com.shroggle.entity.FilledForm;
import com.shroggle.entity.FilledFormItem;
import com.shroggle.entity.FormFile;
import com.shroggle.entity.FormVideo;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.logic.form.FormFileTypeForDeletion;
import com.shroggle.logic.form.FilledFormItemManager;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;


/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class RemoveFormFileService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            entityClass = FilledForm.class,
            entityIdPropertyPath = "filledFormId",
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public String execute(final Integer filledFormItemId, final FormFileTypeForDeletion formFileTypeForDeletion) {
        final FilledFormItem filledFormItem = persistance.getFilledFormItemById(filledFormItemId);
        if (filledFormItem == null) {
            return "error";
        }
        if (formFileTypeForDeletion == FormFileTypeForDeletion.FORM_FILE) {
            final FormFile formFile = persistance.getFormFileById(FilledFormItemManager.getIntValue(filledFormItem));
            if (formFile == null) {
                return "error";
            }
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    fileSystem.removeResource(formFile);
                    persistance.removeFormFile(formFile);
                    filledFormItem.setValues(new ArrayList<String>());
                }
            });
        } else {
            final FormVideo formVideo = persistance.getFormVideoById(FilledFormItemManager.getIntValue(filledFormItem));
            if (formVideo == null) {
                return "error";
            }
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    if (formFileTypeForDeletion == FormFileTypeForDeletion.VIDEO) {
                        formVideo.setVideoId(null);
                    } else {
                        FormFile formFile = persistance.getFormFileById(formVideo.getImageId());
                        formVideo.setImageId(null);
                        persistance.removeFormFile(formFile);
                    }
                }
            });
        }
        return "ok";
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
}