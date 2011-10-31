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
package com.shroggle.stresstest.util.transcode;

import com.shroggle.PersistanceMock;
import com.shroggle.entity.Resource;
import com.shroggle.entity.Video;
import com.shroggle.entity.FlvVideo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.MockConfigStorage;
import com.shroggle.util.context.ContextStorage;
import com.shroggle.util.filesystem.FileSystemReal;
import com.shroggle.util.journal.JournalToConsole;
import com.shroggle.util.process.SystemConsoleReal;
import com.shroggle.util.process.ThreadUtil;
import com.shroggle.util.transcode.VideoTranscodeAsync;
import com.shroggle.util.transcode.VideoTranscodeNative;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Artem Stasuk
 */
public class VideoTranscodeAsyncRun {

    public static void main(String[] args) throws InterruptedException {
        ServiceLocator.setSystemConsole(new SystemConsoleReal());
        ServiceLocator.setConfigStorage(new MockConfigStorage());
        ServiceLocator.setContextStorage(new ContextStorage());
        ServiceLocator.setJournal(new JournalToConsole());
        final Config config = ServiceLocator.getConfigStorage().get();
        config.setConcurrentConvertThreadCount(12);
        new File("e:/temp").mkdir();
        config.setSiteResourcesPath("e:/temp");
        config.getSiteResourcesVideo().setPath("e:/temp");

        final PersistanceMock persistance = new PersistanceMock();
        ServiceLocator.setPersistance(persistance);
        final VideoTranscodeAsync videoTranscode = new VideoTranscodeAsync(new VideoTranscodeNative());
        ServiceLocator.setFileSystem(new FileSystemReal(null, null,
                null, null, null, null, null, null, null) {

            public String getResourcePath(final Resource resource) {
                if (resource instanceof Video) {
                    return "e:/media/video/ce78~1/1-5~1/Futura~3/" + resource.getName() + ".avi";
                }
                return super.getResourcePath(resource);
            }

        });

        String[] videoNames = {
                "S05E01", "S05E02", "S05E03", "S05E04", "S05E05", "S05E06", "S05E07",
                "S05E08", "S05E09", "S05E10"
        };

        final long start = System.currentTimeMillis();

        final Set<Integer> videoFlvIds = new HashSet<Integer>();
        for (final String videoName : videoNames) {
            final Video video = new Video();
            video.setSourceExtension("avi");
            video.setSourceName(videoName);
            persistance.putVideo(video);

            final FlvVideo flvVideo = new FlvVideo();
            flvVideo.setWidth(640);
            flvVideo.setHeight(480);
            flvVideo.setSourceVideoId(video.getVideoId());
            persistance.putFlvVideo(flvVideo);
            videoFlvIds.add(flvVideo.getFlvVideoId());

            videoTranscode.execute(flvVideo, null);
        }

        while (!videoFlvIds.isEmpty()) {
            final Iterator<Integer> videoFlvIdIterator = videoFlvIds.iterator();
            while (videoFlvIdIterator.hasNext()) {
                if (!videoTranscode.isExecuting(videoFlvIdIterator.next())) {
                    videoFlvIdIterator.remove();
                }
            }
            ThreadUtil.sleep(1000);
        }

        videoTranscode.destroy();
        System.out.println("Total time: " + ((System.currentTimeMillis() - start) / 1000L) + " sec");
    }

}
