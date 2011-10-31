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
package com.shroggle.logic.site;

import com.shroggle.entity.DraftVideo;
import com.shroggle.entity.FlvVideo;
import com.shroggle.entity.Video;
import com.shroggle.entity.WidgetItem;
import com.shroggle.exception.VideoNotFoundException;
import com.shroggle.exception.VideoWriteException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.video.CreateVideoRequest;
import com.shroggle.logic.video.FlvVideoManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.transcode.VideoTranscode;

import static java.lang.Integer.parseInt;

/**
 * @author Artem Stasuk
 */
public class VideoItemManager {

    public VideoItemManager(final UserManager userManager, final int videoItemId) {
        videoItem = persistance.getDraftItem(videoItemId);
    }

    /**
     * Added by problem when user convert big file in this case we have transaction timeout
     * or transaction blocking and many other problems.
     * When user set some video to widget and press save, we are creating videoFlv entity and starting
     * converting procedure in other thread, after this we return
     * control to user, but his see sun in motion, js code on page every X second call server to
     * check condition "Is converting finished?" if server return true js hide sun and user
     * continue work in other case user wait, wait and wait... Or if there was an exception - we show it to user.
     *
     * @param edit - request
     * @link http://jira.web-deva.com/browse/SW-3290
     * @see com.shroggle.entity.FlvVideo
     */
    public void edit(final CreateVideoRequest edit) {
        final Video video = persistance.getVideoById(edit.getVideoId());
        if (video == null) {
            throw new VideoNotFoundException("Cant find Video " + edit.getVideoId());
        }

        persistanceTransaction.execute(new Runnable() {
            @Override
            public void run() {
                setVideoInTransaction(edit, video);
            }
        });
    }

    private void setVideoInTransaction(final CreateVideoRequest edit, final Video video) {
        //------------------------------------------create small video------------------------------------------
        videoItem.setVideoSmallSize(edit.getVideoSmallSize());
        if (edit.isDisplaySmallOptions() && !edit.getVideoSmallSize().isEmpty()) {
            Integer smallWidth = parseInt(edit.getVideoSmallSize().split("x")[0]);
            Integer smallHeight = parseInt(edit.getVideoSmallSize().split("x")[1]);
            if (smallWidth != null && smallHeight != null) {
                final FlvVideo smallFlvVideo = FlvVideoManager.getFlvVideoOrCreateNew(edit.getVideoId(), smallWidth, smallHeight, video.getSiteId());
                Integer smallFlvVideoId = smallFlvVideo != null ? smallFlvVideo.getFlvVideoId() : null;
                videoItem.setSmallFlvVideoId(smallFlvVideoId);
            } else {
                videoItem.setSmallFlvVideoId(null);
            }
        } else {
            videoItem.setSmallFlvVideoId(null);
        }
        //------------------------------------------create small video------------------------------------------

        //------------------------------------------create large video------------------------------------------
        videoItem.setVideoLargeSize(edit.getVideoLargeSize());
        if (edit.isDisplayLargeOptions() && !edit.getVideoLargeSize().isEmpty()) {
            Integer largeWidth = parseInt(edit.getVideoLargeSize().split("x")[0]);
            Integer largeHeight = parseInt(edit.getVideoLargeSize().split("x")[1]);
            if (largeWidth != null && largeHeight != null) {
                final FlvVideo largeFlvVideo = FlvVideoManager.getFlvVideoOrCreateNew(edit.getVideoId(), largeWidth, largeHeight, video.getSiteId());
                Integer largeFlvVideoId = largeFlvVideo != null ? largeFlvVideo.getFlvVideoId() : null;
                videoItem.setLargeFlvVideoId(largeFlvVideoId);
            } else {
                videoItem.setLargeFlvVideoId(null);
            }
        } else {
            videoItem.setLargeFlvVideoId(null);
        }
        //------------------------------------------create large video------------------------------------------

        //-----------------------------------------create normal video------------------------------------------
        final FlvVideo normalFlvVideo = FlvVideoManager.getFlvVideoOrCreateNew(edit.getVideoId(), edit.getWidth(), edit.getHeight(), video.getSiteId());
        videoItem.setFlvVideoId(normalFlvVideo.getFlvVideoId());
        //-----------------------------------------create normal video------------------------------------------

        videoItem.setSaveRatio(edit.isSaveRatio());
        if (edit.getVideoImageId() > 0) {
            videoItem.setImageId(edit.getVideoImageId());
            videoItem.setImageWidth(edit.getImageWidth());
            videoItem.setImageHeight(edit.getImageHeight());
        } else {
            videoItem.setImageId(null);
            videoItem.setImageWidth(null);
            videoItem.setImageHeight(null);
        }
        videoItem.setDescription(edit.getVideoDescription());
        videoItem.setIncludeDescription(edit.isIncludeDescription());
        videoItem.setKeywords(edit.getKeywords());
        videoItem.setName(edit.getVideoName());
        videoItem.setPlayInCurrentPage(edit.isPlayInCurrentPage());
        videoItem.setDisplaySmallOptions(edit.isDisplaySmallOptions());
        videoItem.setDisplayLargeOptions(edit.isDisplayLargeOptions());
    }

    public boolean isVideoPrepared() {
        final VideoTranscode videoTranscode = ServiceLocator.getVideoTranscode();
        if (videoItem.getFlvVideoId() != null) {
            if (videoTranscode.isExecuting(videoItem.getFlvVideoId())) {
                return false;
            }
            final FlvVideo flvVideo = persistance.getFlvVideo(videoItem.getFlvVideoId());
            checkConvertedVideoFileExistence(flvVideo);
        }
        if (videoItem.getLargeFlvVideoId() != null) {
            if (videoTranscode.isExecuting(videoItem.getLargeFlvVideoId())) {
                return false;
            }

            final FlvVideo largeFlvVideo = persistance.getFlvVideo(videoItem.getLargeFlvVideoId());
            checkConvertedVideoFileExistence(largeFlvVideo);
        }
        if (videoItem.getSmallFlvVideoId() != null) {
            if (videoTranscode.isExecuting(videoItem.getSmallFlvVideoId())) {
                return false;
            }

            final FlvVideo smallFlvVideo = persistance.getFlvVideo(videoItem.getFlvVideoId());
            checkConvertedVideoFileExistence(smallFlvVideo);
        }
        return true;
    }

    private void checkConvertedVideoFileExistence(final FlvVideo video) {
        if (!fileSystem.isResourceExist(video)) {
            throw new VideoWriteException("Video has converted successfuly but has not written on hard disk. " +
                    "Maybe there is no free space or something is wrong with rights. FlvVideo id = " + video.getFlvVideoId());
        }
    }

    private final DraftVideo videoItem;
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
