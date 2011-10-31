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
package com.shroggle.logic.video;

import com.shroggle.entity.Video;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceContext;
import com.shroggle.util.persistance.PersistanceTransaction;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class SourceVideoSizeCreator {

    public static void execute() {
        final Persistance persistance = ServiceLocator.getPersistance();
        final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
        final Logger logger = Logger.getLogger(SourceVideoSizeCreator.class.getName());
        persistance.inContext(new PersistanceContext<Void>() {
            @Override
            public Void execute() {
                logger.log(Level.INFO, "SourceVideoSizeCreator started!");
                for (final Video video : persistance.getVideos()) {
                    if (video.getSourceWidth() <= 0 || video.getSourceHeight() <= 0) {
                        logger.log(Level.INFO, "Creating dimension for video with id = " + video.getVideoId());
                        VideoManager manager = new VideoManager(video);
                        final Dimension dimension = manager.createSourceVideoDimension();
                        if (dimension != null) {
                            persistanceTransaction.execute(new Runnable() {
                                public void run() {
                                    video.setSourceWidth((int) dimension.getWidth());
                                    video.setSourceHeight((int) dimension.getHeight());
                                }
                            });

                        } else {
                            logger.log(Level.SEVERE, "Can`t create size for video with id = " + video.getVideoId());
                        }
                    }
                }
                logger.log(Level.INFO, "SourceVideoSizeCreator finished!");
                return null;
            }
        });
    }

}
