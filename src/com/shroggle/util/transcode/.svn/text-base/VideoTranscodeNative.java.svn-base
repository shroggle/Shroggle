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
import com.shroggle.entity.JournalItemPriority;
import com.shroggle.entity.Video;
import com.shroggle.exception.VideoNotFoundException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemContext;
import com.shroggle.util.filesystem.FileSystemItem;
import com.shroggle.util.filesystem.FileSystemTransaction;
import com.shroggle.util.journal.JournalEasy;
import com.shroggle.util.process.SystemConsole;
import com.shroggle.util.process.SystemConsoleException;
import com.shroggle.util.resource.resize.FfmpegVideoSizeCreator;

import java.util.logging.Logger;

/**
 * This transcoder use native programs for convert video to flv format ffmpeg and add
 * need metadata for support stream play flvmeta.
 * <p/>
 * Please test only with real file system service because its use
 * native programs their don't know about mock file system.
 *
 * @author Stasuk Artem
 * @link http://code.google.com/p/flvmeta/
 * @link http://www.ffmpeg.org/
 */
public class VideoTranscodeNative implements VideoTranscode {

    public void execute(final FlvVideo flvVideo, final Video video) {

        if (video == null) {
            throw new VideoNotFoundException("Can't find video " + flvVideo.getSourceVideoId());
        }

        final FileSystem fileSystem = ServiceLocator.getFileSystem();
        fileSystem.inTransaction(new FileSystemTransaction() {

            @Override
            public void execute(final FileSystemContext context) {
                // Convert source video file to flv.
                final String sourcePath = fileSystem.getResourcePath(video);
                final FileSystemItem convertedItem = context.createItem();
                logger.info("Converting video with id = " + video.getVideoId() + ".\nSource file name:\n" + sourcePath +
                        "\nTemporary file name for converted video:\n" + convertedItem.getPath() + "");
                executeProcess("ffmpeg -i " + sourcePath + new FfmpegVideoSizeCreator(video).execute(flvVideo.getWidth(), flvVideo.getHeight())
                        + "-v 0 -qscale " + flvVideo.getQuality() + " -ar 44100 -ab 128k -ac 2 -f flv " + convertedItem.getPath());

                final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
                final String flvmeta = configStorage.get().getFlvmeta();
                try {
                    // Add metadata to flv file and move it to start.
                    final FileSystemItem preparedItem = context.createItem();
                    executeProcess(flvmeta + " " + convertedItem.getPath() + " " + preparedItem.getPath());
                    journalEasy.add(JournalItemPriority.INFO, "Success add metadata over " + flvmeta);
                    preparedItem.mustStayAs(fileSystem.getResourcePath(flvVideo));
                } catch (VideoTranscodeException exception) {
                    journalEasy.add(JournalItemPriority.INFO, "Can't add metadata over " + flvmeta, exception);
                    convertedItem.mustStayAs(fileSystem.getResourcePath(flvVideo));
                }
            }

        });
    }

    /**
     * @param videoFlvId - videoFlvId
     * @return - always false because it's synchro transcoder
     */
    @Override
    public boolean isExecuting(final int videoFlvId) {
        return false;
    }

    @Override
    public void destroy() {

    }

    private void executeProcess(final String command) {
        final SystemConsole systemConsole = ServiceLocator.getSystemConsole();
        try {
            final int error = systemConsole.execute(command);
            if (error != 0) {
                throw new VideoTranscodeException(
                        "Couldn't transcoded video, fail executed process, error (" + error + "): " + command);
            }
        } catch (final SystemConsoleException exception) {
            throw new VideoTranscodeException(exception);
        }
    }

    private final JournalEasy journalEasy = new JournalEasy(VideoTranscodeNative.class);
    private final Logger logger = Logger.getLogger(this.getClass().getName());

}
