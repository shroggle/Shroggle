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

import com.shroggle.entity.DraftSlideShow;
import com.shroggle.exception.SlideShowNotFoundException;
import com.shroggle.logic.slideShow.SlideShowManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class UpdateSlideShowImagesService extends AbstractService {

    @RemoteMethod
    public String execute(final int slideShowId) throws IOException, ServletException {
        final DraftSlideShow slideShow = (DraftSlideShow) persistance.getDraftItem(slideShowId);

        if (slideShow == null) {
            throw new SlideShowNotFoundException("Cannot find slide show by Id=" + slideShowId);
        }

        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("slideShowImages",
                new SlideShowManager(slideShow).getSortedImages());
        return webContext.forwardToString("/slideShow/configureSlideShowManageImages.jsp");
    }

    private Persistance persistance = ServiceLocator.getPersistance();

}
