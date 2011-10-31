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
import com.shroggle.exception.VideoNotFoundException;
import com.shroggle.util.process.SystemConsole;
import com.shroggle.util.process.ProcessResponse;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;

import java.awt.Dimension;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * @author Balakirev Anatoliy
 *         Date: 21.09.2009
 */
public class VideoManager {

    public VideoManager(Video video) {
        if (video == null) {
            throw new VideoNotFoundException("Can`t create VideoManager by null Video!");
        }
        this.video = video;
    }

    public Dimension createSourceVideoDimension() {
        logger.log(Level.INFO, "Getting video dimension.");
        String sourcePath = fileSystem.getResourcePath(video);
        try {
            ProcessResponse response = new ProcessResponse();
            systemConsole.execute("ffmpeg -i " + sourcePath, response);
            return createDimensionByResponseString(response.getResponseText());
        } catch (final Exception exception) {
            logger.log(Level.SEVERE, "Can`t create dimension for video with id = " + video.getVideoId());
        }
        return null;
    }

    public boolean isAudio() {
        return video.getExtension().endsWith("mp3") || video.getExtension().endsWith("wav");
    }

    /*-------------------------------------------------Hidden methods-------------------------------------------------*/

    public Dimension createDimensionByResponseString(final String response) {
        Pattern pattern = Pattern.compile("\\s+(\\d+)x(\\d+)[\\s+,+]");
        Matcher matcher = pattern.matcher(response);
        while (matcher.find()) {
            try {
                int width = Integer.valueOf(matcher.group(1));
                int height = Integer.valueOf(matcher.group(2));
                if (width <= 0 || height <= 0) {
                    continue;
                }
                return new Dimension(width, height);
            } catch (Exception exception) {
                //logger.log(Level.INFO, "Can`t create video dimension by \"" + matcher.group(0) + "\" string!");
            }
        }
        return null;
    }

    private final Video video;
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final SystemConsole systemConsole = ServiceLocator.getSystemConsole();
    private final Logger logger = Logger.getLogger(VideoManager.class.getName());
}
