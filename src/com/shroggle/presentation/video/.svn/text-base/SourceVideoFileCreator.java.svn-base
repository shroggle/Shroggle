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

package com.shroggle.presentation.video;

import com.shroggle.entity.Video;
import com.shroggle.exception.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.FileNameUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceContext;
import com.shroggle.logic.video.VideoManager;

import java.io.InputStream;
import java.util.Date;
import java.awt.*;

/**
 * @author Balakirev Anatoliy
 */
public class SourceVideoFileCreator {

    public SourceVideoFileCreator(int siteId, String sourceExtension, String sourceName, InputStream sourceInputStream) {
        this.siteId = siteId;
        this.sourceExtension = sourceExtension;
        this.sourceName = sourceName;
        this.sourceInputStream = sourceInputStream;
    }

    public Video execute() throws SourceInputStreamNullException, ExtensionNullOrEmptyException {

        if (sourceInputStream == null) {
            throw new SourceInputStreamNullException();
        }

        if (sourceExtension == null || sourceExtension.trim().isEmpty()) {
            throw new ExtensionNullOrEmptyException();
        }

        final Video video = new Video();
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                video.setSiteId(siteId);
                video.setCreated(new Date());
                video.setSourceExtension(sourceExtension);
                video.setSourceName(FileNameUtil.getAvailableVideoName(sourceName, siteId));
                persistance.putVideo(video);
            }
        });


        ServiceLocator.getFilesWriter().setResourceStream(video, sourceInputStream, new Runnable() { // Save file asynchronously.

            public void run() {// after save action
                final Persistance persistance = ServiceLocator.getPersistance();
                persistance.inContext(new PersistanceContext<Void>() {
                    @Override
                    public Void execute() {
                        final Video tempVideo = persistance.getVideoById(video.getVideoId());
                        final VideoManager manager = new VideoManager(tempVideo);
                        final Dimension dimension = manager.createSourceVideoDimension();
                        if (dimension != null) {
                            ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                                public void run() {
                                    tempVideo.setSourceWidth((int) dimension.getWidth());
                                    tempVideo.setSourceHeight((int) dimension.getHeight());
                                }
                            });
                        }
                        return null;
                    }
                });
            }
        });
        return video;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final int siteId;
    private final String sourceExtension;
    private final String sourceName;
    private final InputStream sourceInputStream;
}