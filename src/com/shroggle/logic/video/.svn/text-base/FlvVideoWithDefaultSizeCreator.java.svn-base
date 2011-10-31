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

import com.shroggle.entity.FormVideo;
import com.shroggle.entity.Video;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceContext;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class FlvVideoWithDefaultSizeCreator {
    public static void execute() {
        final Logger logger = Logger.getLogger(SourceVideoSizeCreator.class.getName());
        logger.log(Level.INFO, "FlvVideoWithDefaultSizeCreator started!");
        final Persistance persistance = ServiceLocator.getPersistance();
        persistance.inContext(new PersistanceContext<List<Void>>() {
            @Override
            public List<Void> execute() {
                for (final FormVideo formVideo : persistance.getAllFormVideos()) {
                    final Video sourceVideo = persistance.getVideoById(formVideo.getVideoId());
                    if (sourceVideo != null && sourceVideo.getSourceWidth() > 0 && sourceVideo.getSourceHeight() > 0) {

                        FlvVideoManager.getFlvVideoOrCreateNew(sourceVideo.getVideoId(), sourceVideo.getSourceWidth(), sourceVideo.getSourceHeight(), formVideo.getQuality(), sourceVideo.getSiteId());

                    }
                }
                return null;
            }
        });
        logger.log(Level.INFO, "FlvVideoWithDefaultSizeCreator finished!");
    }
}
