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

import com.shroggle.entity.DraftImage;
import com.shroggle.entity.Image;
import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.ImageItemNotFoundException;
import com.shroggle.exception.ImageNotSelectException;
import com.shroggle.exception.ImageSizeIncorrectException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Stasuk Artem
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class CreateImageService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            entityClass = Widget.class,
            entityIdPropertyPath = "widgetId",
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public FunctionalWidgetInfo savePrimaryImageTab(final SavePrimaryImageRequest request) throws ServletException, IOException {
        final International international =
                ServiceLocator.getInternationStorage().get("configureImageWidget", Locale.US);

        final UserManager userManager = new UsersManager().getLogined();

        if (request.getWidth() != null && request.getWidth() < 1 ||
                request.getHeight() != null && request.getHeight() < 1) {
            throw new ImageSizeIncorrectException(international.get("IMAGE_SIZE_INCORRECT"));
        }

        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        final DraftImage draftImage = persistance.getDraftItem(request.getImageItemId());
        if (draftImage == null || draftImage.getSiteId() <= 0) {
            throw new ImageItemNotFoundException("Cannot find image item by Id=" + request.getImageItemId());
        }

        if (draftImage.getExtension() == null) {
            // This widget is not edit
            if (request.getWidth() == null && request.getHeight() == null) {
                throw new ImageSizeIncorrectException(international.get("IMAGE_SIZE_INCORRECT"));
            }
            if (request.getImageId() == null) {
                throw new ImageNotSelectException(international.get("SELECT_IMAGE"));
            }
        }

        final Image sourceImage = persistance.getImageById(request.getImageId());
        if (sourceImage == null) {
            throw new ImageNotSelectException(international.get("SELECT_IMAGE"));
        }
        persistanceTransaction.execute(new Runnable() {

            public void run() {
                draftImage.setThumbnailWidth(request.getWidth());
                draftImage.setThumbnailHeight(request.getHeight());
                draftImage.setName(request.getName());
                draftImage.setVersion(draftImage.getVersion() + 1);
                draftImage.setSaveRatio(request.isSaveRatio());
                draftImage.setAligment(request.getAlignment());
                draftImage.setImageId(request.getImageId());
                draftImage.setExtension(sourceImage.getSourceExtension());
                draftImage.setMargin(request.getMargin());
                draftImage.setDescription(request.getDescription());
            }

        });
        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }


    @SynchronizeByMethodParameterProperty(
            entityClass = Widget.class,
            entityIdPropertyPath = "widgetId",
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public FunctionalWidgetInfo saveRollOverImageTab(final SaveRollOverImageRequest request) throws ServletException, IOException {
        final UserManager userManager = new UsersManager().getLogined();

        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        final DraftImage draftImage = persistance.getDraftItem(request.getImageItemId());
        if (draftImage == null || draftImage.getSiteId() <= 0) {
            throw new ImageItemNotFoundException("Cannot find image item by Id=" + request.getImageItemId());
        }

        final Image sourceRollOverImage = persistance.getImageById(request.getRollOverImageId());

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                draftImage.setRollOverImageId(request.getRollOverImageId());
                draftImage.setShowDescriptionOnMouseOver(request.isDescriptionOnMouseOver());
                draftImage.setOnMouseOverText(request.getOnMouseOverText());

                if (sourceRollOverImage != null) {
                    draftImage.setRollOverExtension(sourceRollOverImage.getSourceExtension());
                } else {
                    draftImage.setRollOverExtension(null);
                }
            }

        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }


    @SynchronizeByMethodParameterProperty(
            entityClass = Widget.class,
            entityIdPropertyPath = "widgetId",
            method = SynchronizeMethod.WRITE)
    @RemoteMethod
    public FunctionalWidgetInfo saveLabelsLinksImageTab(final SaveLabelLinksRequest request) throws ServletException, IOException {
        final UserManager userManager = new UsersManager().getLogined();

        final WidgetItem widget;
        if (request.getWidgetId() != null) {
            widget = (WidgetItem) userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        final DraftImage draftImage = persistance.getDraftItem(request.getImageItemId());
        if (draftImage == null || draftImage.getSiteId() <= 0) {
            throw new ImageItemNotFoundException("Cannot find image item by Id=" + request.getImageItemId());
        }

        persistanceTransaction.execute(new Runnable() {

            public void run() {
                draftImage.setVersion(draftImage.getVersion() + 1);
                draftImage.setTitle(request.getTitle());
                draftImage.setTitlePosition(request.getTitlePosition());
                draftImage.setLabelIsALinnk(request.isLabelIsALinnk());
                draftImage.setImageIsALinnk(request.isImageIsALinnk());
                draftImage.setCustomizeWindowSize(request.isCustomizeWindowSize());
                draftImage.setImageFileId(request.getImageFileId());
                draftImage.setNewWindowWidth(request.getNewWindowWidth());
                draftImage.setNewWindowHeight(request.getNewWindowHeight());
                draftImage.setImageFileType(request.getImageFileType());
                draftImage.setImageLinkType(request.getImageLinkType());
                draftImage.setImagePdfDisplaySettings(request.getImagePdfDisplaySettings());
                draftImage.setImageAudioDisplaySettings(request.getImageAudioDisplaySettings());
                draftImage.setImageFlashDisplaySettings(request.getImageFlashDisplaySettings());
                draftImage.setExternalUrl(request.getExternalUrl());
                draftImage.setExternalUrlDisplaySettings(request.getExternalUrlDisplaySettings());
                draftImage.setInternalPageId(request.getInternalPageId());
                draftImage.setInternalPageDisplaySettings(request.getInternalPageDisplaySettings());
                draftImage.setTextArea(request.getTextArea());
                draftImage.setTextAreaDisplaySettings(request.getTextAreaDisplaySettings());
            }

        });

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widget, "widget", true);
    }


    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
