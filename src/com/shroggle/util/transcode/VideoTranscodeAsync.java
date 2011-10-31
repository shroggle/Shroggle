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
package com.shroggle.util.transcode;

import com.shroggle.entity.FlvVideo;
import com.shroggle.entity.Video;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Execute convert in separeted thread (async), but has limit on paraller
 * working threads.
 *
 * @author Artem Stasuk
 * @see com.shroggle.util.config.Config
 */
public class VideoTranscodeAsync implements VideoTranscode {

    public VideoTranscodeAsync(final VideoTranscode videoTranscode) {
        this.videoTranscode = videoTranscode;
        final Config config = ServiceLocator.getConfigStorage().get();
        this.executorService = Executors.newFixedThreadPool(config.getConcurrentConvertThreadCount());
    }

    @Override
    public void execute(final FlvVideo flvVideo, final Video video) {
        if (flvVideo == null) {
            return;
        }

        synchronized (executing) {
            executing.add(flvVideo.getFlvVideoId());
        }

        /**
         * Be careful, new thread doesn't have persistance context and
         * you must create manual. 
         */
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                Thread.currentThread().setName(super.getClass().getSimpleName()
                        + " {videoFlvId: " + flvVideo.getFlvVideoId() + "}");

                try {
                    videoTranscode.execute(flvVideo, video);
                } finally {
                    synchronized (executing) {
                        executing.remove(flvVideo.getFlvVideoId());
                    }
                }
            }

        });
    }

    @Override
    public boolean isExecuting(final int videoFlvId) {
        synchronized (executing) {
            return executing.contains(videoFlvId);
        }
    }

    @Override
    public void destroy() {
        executorService.shutdown();
    }

    private final VideoTranscode videoTranscode;
    private final ExecutorService executorService;
    private final Set<Integer> executing = new HashSet<Integer>();

}
