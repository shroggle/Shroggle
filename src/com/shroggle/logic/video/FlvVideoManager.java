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

import com.shroggle.entity.FlvVideo;
import com.shroggle.exception.FlvVideoNotFoundException;
import com.shroggle.util.Dimension;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.transcode.VideoTranscode;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class FlvVideoManager {

    public FlvVideoManager(FlvVideo flvVideo) {
        this.flvVideo = flvVideo;
    }

    public Dimension createLargeVideoDimension() {
        if (flvVideo == null) {
            throw new FlvVideoNotFoundException("Can`t create FlvVideoManager by null FlvVideo!");
        }
        final Dimension dimension = createLargeVideoDimension(flvVideo.getWidth(), flvVideo.getHeight());
        if (dimension == null) {
            logger.log(Level.SEVERE, "Can`t create larger flvVideo dimension by null normal flvVideo width or height. " +
                    "FlvVideo id = " + flvVideo.getFlvVideoId() + ", normal width = " + flvVideo.getWidth() + ", normal height = " +
                    flvVideo.getHeight() + ".");
        }
        return dimension;
    }

    public String getFlvVideoStatusAndStartNewConversionIfNeeded() {
        if (flvVideo != null) {
            if (fileSystem.isResourceExist(flvVideo)) {
                logger.info("FlvVideo converted and file exists on hard disk.");
                return OK;
            } else if (videoTranscode.isExecuting(flvVideo.getFlvVideoId())) {
                logger.info("FlvVideo is converting now.");
                return CONVERTING;
            } else {
                final boolean newConversionStarted = startNewConversion();
                if (newConversionStarted) {
                    logger.info("FlvVideo exists in database but converted file has not found. New conversion has started.");
                    return CONVERTING;
                }
            }
        }
        logger.info("FlvVideo has not found in database.");
        return NOT_FOUND;
    }

    public static FlvVideo getFlvVideoOrCreateNew(Integer sourceVideoId, Integer width, Integer height, int siteId) {
        return getFlvVideoOrCreateNew(sourceVideoId, width, height, FlvVideo.DEFAULT_VIDEO_QUALITY, siteId);
    }

    public static FlvVideo getFlvVideoOrCreateNew(final Integer sourceVideoId, final Integer width, final Integer height,
                                                  final Integer quality, final int siteId) {
        FlvVideo flvVideo = ServiceLocator.getPersistance().getFlvVideo(sourceVideoId, width, height, quality);
        if (flvVideo == null) {
            flvVideo = ServiceLocator.getPersistanceTransaction().execute(new PersistanceTransactionContext<FlvVideo>() {
                @Override
                public FlvVideo execute() {
                    return createVideoFlv(sourceVideoId, width, height, quality, siteId);
                }
            });
        }
        if (flvVideo != null && !ServiceLocator.getFileSystem().isResourceExist(flvVideo)) {
            ServiceLocator.getVideoTranscode().execute(flvVideo, ServiceLocator.getPersistance().getVideoById(flvVideo.getSourceVideoId()));
        }
        return flvVideo;
    }

    public static FlvVideo createVideoFlv(Integer sourceVideoId, Integer width, Integer height, Integer quality, int siteId) {
        if (sourceVideoId == null || quality == null) {
            return null;
        }
        final FlvVideo flvVideo = new FlvVideo();
        flvVideo.setSiteId(siteId);
        flvVideo.setSourceVideoId(sourceVideoId);
        flvVideo.setWidth(width);
        flvVideo.setHeight(height);
        ServiceLocator.getPersistance().putFlvVideo(flvVideo);
        flvVideo.setQuality(quality);
        return flvVideo;
    }

    public static boolean isVideoQualityCorrect(final int quality) {
        return (quality <= FlvVideo.MIN_VIDEO_QUALITY && quality >= FlvVideo.MAX_VIDEO_QUALITY);
    }


    public static Dimension createLargeVideoDimension(final Integer oldWidth, final Integer oldHeight) {
        if (oldWidth == null || oldHeight == null) {
            logger.log(Level.SEVERE, "Can`t create larger flvVideo dimension by null width or height. " +
                    "Width = " + oldWidth + ", height = " + oldHeight + ".");
            return null;
        }
        int width = Math.round((oldWidth * LARGER_VIDEO_MULTIPLIER));
        int height = Math.round((oldHeight * LARGER_VIDEO_MULTIPLIER));
        return new Dimension(width, height, LARGER_VIDEO_MULTIPLIER, LARGER_VIDEO_MULTIPLIER);
    }


    private boolean startNewConversion() {
        try {
            logger.log(Level.INFO, "Converted flvVideo file (with source video id = " + flvVideo.getSourceVideoId() +
                    ", width = " + flvVideo.getWidth() + ", height = " + flvVideo.getHeight() + ", quality = "
                    + flvVideo.getQuality() + ") not found. " + "Executing new conversion process:");
            ServiceLocator.getVideoTranscode().execute(flvVideo, ServiceLocator.getPersistance().getVideoById(flvVideo.getSourceVideoId()));
            return true;
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Can`t create converted video for source video file with id = " + flvVideo.getSourceVideoId() +
                    ". Please, check source video file.", exception);
            return false;
        }
    }

    private final FlvVideo flvVideo;
    private final VideoTranscode videoTranscode = ServiceLocator.getVideoTranscode();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    public static final float LARGER_VIDEO_MULTIPLIER = 1.6f;
    private static final Logger logger = Logger.getLogger(FlvVideoManager.class.getName());
    private static final String OK = "ok";
    private static final String CONVERTING = "converting";
    private static final String NOT_FOUND = "notfound";
}
