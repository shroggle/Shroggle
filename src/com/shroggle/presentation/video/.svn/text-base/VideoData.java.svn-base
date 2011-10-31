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
import com.shroggle.logic.video.VideoManager;
import com.shroggle.exception.VideoNotFoundException;
import com.shroggle.util.StringUtil;

/**
 * @author Balakirev Anatoliy
 *         Date: 29.09.2009
 */
public class VideoData {

    public VideoData(final Video video) {
        if (video == null) {
            throw new VideoNotFoundException("Can`t create VideoData by null video");
        }
        id = video.getVideoId();
        name = StringUtil.getEmptyOrString(video.getSourceName());
        audioFile = new VideoManager(video).isAudio();
        int sourceWidth = video.getSourceWidth() % 2 == 0 ? video.getSourceWidth() : video.getSourceWidth() + 1;
        int sourceHeight= video.getSourceHeight() % 2 == 0 ? video.getSourceHeight() : video.getSourceHeight() + 1;
        width = audioFile ? "" : String.valueOf(sourceWidth);
        height = audioFile ? "" : String.valueOf(sourceHeight);
    }

    public VideoData() {
        id = -1;
        name = "";
        audioFile = false;
        width = "";
        height = "";
    }

    private final int id;

    private final String width;

    private final String height;

    private final String name;

    private final boolean audioFile;

    public int getId() {
        return id;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public boolean isAudioFile() {
        return audioFile;
    }
}
