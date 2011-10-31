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
import com.shroggle.exception.FormFilterNotFoundException;
import com.shroggle.exception.FormNotFoundException;
import com.shroggle.exception.SlideShowNotFoundException;
import com.shroggle.logic.form.FormItemManager;
import com.shroggle.logic.form.FormItemsManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.form.filter.FormFilterManager;
import com.shroggle.logic.slideShow.SlideShowManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class SlideShowUploadFormImagesService extends AbstractService {

    @RemoteMethod
    public SlideShowUploadFormImagesResponse execute(final SlideShowUploadFormImagesRequest request) throws IOException, ServletException {
        new UsersManager().getLogined();

        final DraftSlideShow slideShow = (DraftSlideShow) persistance.getDraftItem(request.getSlideShowId());

        if (slideShow == null) {
            throw new SlideShowNotFoundException("Cannot find slide show by Id=" + request.getSlideShowId());
        }

        final Form form = persistance.getFormById(request.getSelectedFormId());

        if (form == null) {
            throw new FormNotFoundException("Cannot find form by Id=" + request.getSelectedFormId());
        }

        // User may choose other image item than first image item, if so then we will get images from this item.
        final FormItem formItem;
        if (request.getSelectedImageFormItemId() == null) {
            formItem = FormItemsManager.getFirstImageField(form.getFormItems());

            if (formItem == null) {
                throw new IllegalArgumentException("It appears that form that came in request have no Image Item");
            }
        } else {
            formItem = persistance.getFormItemById(request.getSelectedImageFormItemId());

            if (formItem == null || formItem.getFormItemName() != FormItemName.IMAGE_FILE_UPLOAD ||
                    formItem.getForm().getId() != form.getId()) {
                throw new IllegalArgumentException("Illegal item in request.");
            }
        }

        final List<FilledFormItem> imageFilledItems =
                persistance.getFilledFormItemByFormItemId(formItem.getFormItemId());

        // Filtering filled form items if filter was provided by user.
        if (request.getSelectedFilterId() != null) {
            final DraftFormFilter filter = persistance.getFormFilterById(request.getSelectedFilterId());

            if (filter == null) {
                throw new FormFilterNotFoundException("Cannot find filter by Id=" + request.getSelectedFilterId());
            }

            final List<FilledForm> acceptableForms = new FormFilterManager(filter).getFilledForms();
            final List<FilledFormItem> notAcceptedImageItems = new ArrayList<FilledFormItem>();
            for (FilledFormItem imageItem : imageFilledItems) {
                if (!acceptableForms.contains(imageItem.getFilledForm())) {
                    notAcceptedImageItems.add(imageItem);
                }
            }

            for (FilledFormItem notAcceptedImageItem : notAcceptedImageItems) {
                imageFilledItems.remove(notAcceptedImageItem);
            }
        }

        numberOfImagesUploaded = 0;
        persistanceTransaction.execute(new Runnable() {
            public void run() {
                int maxPosition = new SlideShowManager(slideShow).getMaxImagePosition();

                for (FilledFormItem filledFormItem : imageFilledItems) {
                    if (filledFormItem.getIntValue() == null) {
                        continue;
                    }

                    maxPosition++;

                    final DraftSlideShowImage slideShowImage = new DraftSlideShowImage();
                    slideShowImage.setImageType(SlideShowImageType.FORM_IMAGE);
                    slideShowImage.setSlideShow(slideShow);
                    slideShowImage.setImageId(filledFormItem.getIntValue());
                    slideShowImage.setPosition(maxPosition);
                    slideShowImage.setLinkBackGalleryWidgetId(request.getSelectedLinkBackToGalleryId());

                    persistance.putSlideShowImage(slideShowImage);

                    slideShow.addSlideShowImage(slideShowImage);

                    numberOfImagesUploaded++;
                }
            }
        });

        final SlideShowUploadFormImagesResponse response = new SlideShowUploadFormImagesResponse();
        response.setNumberOfImagesUploaded(numberOfImagesUploaded);

        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("slideShowImages",
                new SlideShowManager(slideShow).getSortedImages());
        response.setManageImagesDivHtml(webContext.forwardToString("/slideShow/configureSlideShowManageImages.jsp"));

        return response;
    }

    private int numberOfImagesUploaded = 0;
    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
