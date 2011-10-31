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
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.presentation.video.VideoData;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 *         Date: 29.09.2009
 */
public class VideoDataManager {


    public static List<VideoData> createVideoDatas(final Integer siteId, final Integer selectedVideoId) {
        final List<Video> videos = getVideosWithExistingFilesBySiteId(siteId);
        Collections.sort(videos, new Comparator<Video>() {
            public int compare(final Video video1, final Video video2) {
                return video2.getCreated().compareTo(video1.getCreated());
            }
        });
        moveSelectedVideoOnFirstPosition(videos, selectedVideoId);
        List<VideoData> videoDatas = new ArrayList<VideoData>();
        final FileSystem fileSystem = ServiceLocator.getFileSystem();
        for (Video video : videos) {
            if (!fileSystem.isResourceExist(video)) {
                continue;
            }
            videoDatas.add(new VideoData(video));
        }
        return videoDatas;
    }


    /*-------------------------------------------------Hidden methods-------------------------------------------------*/
    private static void moveSelectedVideoOnFirstPosition(final List<Video> videos, final Integer selectedVideId) {
        if (videos != null && selectedVideId != null) {
            for (Video video : videos) {
                if (video.getVideoId() == selectedVideId) {
                    videos.remove(video);
                    videos.add(0, video);
                    break;
                }
            }
        }
    }

    private static List<Video> getVideosWithExistingFilesBySiteId(final Integer siteId) {
        final Persistance persistance = ServiceLocator.getPersistance();
        final List<Video> tempVideos = persistance.getVideosBySiteId(siteId);
        final List<Video> videos = new ArrayList<Video>();
        final FileSystem fileSystem = ServiceLocator.getFileSystem();
        for (Video video : tempVideos) {
            if (fileSystem.isResourceExist(video)) {
                videos.add(video);
            }
        }
        return videos;
    }

}
