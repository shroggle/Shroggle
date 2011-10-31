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

import com.shroggle.entity.SlideShow;
import com.shroggle.entity.SlideShowImage;
import com.shroggle.exception.SlideShowImageNotFoundException;
import com.shroggle.exception.SlideShowNotFoundException;
import com.shroggle.logic.slideShow.SlideShowImageManager;
import com.shroggle.logic.slideShow.SlideShowManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ConfigureSlideShowManageImagesService extends AbstractService {

    @RemoteMethod
    public void removeImage(int slideShowImageId) {
        final SlideShowImage slideShowImage = persistance.getSlideShowImageById(slideShowImageId);

        if (slideShowImage == null) {
            throw new SlideShowImageNotFoundException("Cannot find slide show image by Id=" + slideShowImageId);
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                persistance.removeSlideShowImage(slideShowImage);
            }

        });
    }

    @RemoteMethod
    public void moveImageLeft(int slideShowImageId) {
        final SlideShowImage slideShowImage = persistance.getSlideShowImageById(slideShowImageId);

        if (slideShowImage == null) {
            throw new SlideShowImageNotFoundException("Cannot find slide show image by Id=" + slideShowImageId);
        }

        final SlideShowImage prevImage = new SlideShowManager(slideShowImage.getSlideShow()).
                getImageBeforePosition(slideShowImage.getPosition());

        if (prevImage == null){
            return;
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                int prevImagePosition = prevImage.getPosition();

                // swap positions
                prevImage.setPosition(slideShowImage.getPosition());
                slideShowImage.setPosition(prevImagePosition);
            }

        });
    }

    @RemoteMethod
    public void moveImageRight(int slideShowImageId) {
        final SlideShowImage slideShowImage = persistance.getSlideShowImageById(slideShowImageId);

        if (slideShowImage == null) {
            throw new SlideShowImageNotFoundException("Cannot find slide show image by Id=" + slideShowImageId);
        }

        final SlideShowImage nextImage = new SlideShowManager(slideShowImage.getSlideShow()).
                getImageAfterPosition(slideShowImage.getPosition());

        if (nextImage == null){
            return;
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                int nextImagePosition = nextImage.getPosition();

                // swap positions
                nextImage.setPosition(slideShowImage.getPosition());
                slideShowImage.setPosition(nextImagePosition);
            }

        });
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
