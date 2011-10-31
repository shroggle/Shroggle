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
import com.shroggle.exception.SlideShowNameEmptyException;
import com.shroggle.exception.SlideShowNameNotUniqueException;
import com.shroggle.exception.SlideShowNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.FunctionalWidgetInfo;
import com.shroggle.presentation.site.GetFunctionalWidgetsService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameterProperty;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Locale;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class SaveSlideShowService extends AbstractService {

    @SynchronizeByMethodParameterProperty(
            entityClass = Widget.class,
            method = SynchronizeMethod.WRITE,
            entityIdPropertyPath = "widgetId")
    @RemoteMethod
    public FunctionalWidgetInfo execute(final SaveSlideShowRequest request) throws ServletException, IOException {
        final UserManager userManager = new UsersManager().getLogined();

        final Widget widget;
        if (request.getWidgetId() != null) {
            widget = userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widget = null;
        }

        if (StringUtil.isNullOrEmpty(request.getName())) {
            throw new SlideShowNameEmptyException(international.get("SlideShowNameEmptyException"));
        }

        persistanceTransaction.execute(new Runnable() {
            public void run() {
                final DraftSlideShow slideShow = persistance.getDraftItem(request.getSlideShowId());
                if (slideShow == null || slideShow.getSiteId() <= 0) {
                    throw new SlideShowNotFoundException("Cannot find slide show by Id=" + request.getSlideShowId());
                }

                final Site site;
                if (widget != null) {
                    site = widget.getSite();
                } else {
                    site = persistance.getSite(slideShow.getSiteId());
                }

                final DraftSlideShow duplicateSlideShow = persistance.getSlideShowByNameAndSiteId(
                        request.getName(), site.getSiteId());
                if (duplicateSlideShow != null && duplicateSlideShow != slideShow) {
                    throw new SlideShowNameNotUniqueException(international.get("SlideShowNameNotUniqueException"));
                }

                slideShow.setName(request.getName());
                slideShow.setDescription(request.getHeader());
                slideShow.setShowDescription(request.isShowHeader());
                slideShow.setImageWidth(request.getImageWidth());
                slideShow.setImageHeight(request.getImageHeight());
                SlideShowType  slideShowType = SlideShowType.SINGLE_IMAGE;
                if (request.getNumberOfImagesShown() > 1) {
                    slideShowType = SlideShowType.MULTIPLE_IMAGES;
                }
                slideShow.setSlideShowType(slideShowType);
                slideShow.setNumberOfImagesShown(request.getNumberOfImagesShown());
                slideShow.setDisplayType(request.getDisplayType());
                slideShow.setTransitionEffectType(request.getTransitionEffectType());
                slideShow.setDisplayControls(request.isDisplayControls());
                slideShow.setAutoPlay(request.isAutoPlay());
                slideShow.setAutoPlayInterval(request.getAutoPlayInterval());
            }
        });

        final WidgetItem widgetSlideShow;
        if (request.getWidgetId() != null) {
            final UserRightManager userRightManager = userManager.getRight();
            widgetSlideShow = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    request.getWidgetId());
        } else {
            widgetSlideShow = null;
        }

        return new GetFunctionalWidgetsService().createFunctionalWidgetInfo(widgetSlideShow, "widget", true);
    }

    private International international = ServiceLocator.getInternationStorage().get("slideShow", Locale.US);
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private Persistance persistance = ServiceLocator.getPersistance();

}
