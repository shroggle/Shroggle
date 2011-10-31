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
package com.shroggle.logic.form;

import com.shroggle.entity.FormVideo;
import com.shroggle.entity.Video;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;

/**
 * @author Balakirev Anatoliy
 */
public class FormVideoManager {

    public static FormVideo createNewFormVideoOrUpdateExisting(final Integer formVideoId, final String quality) {
        final Persistance persistance = ServiceLocator.getPersistance();
        FormVideo formVideo = persistance.getFormVideoById(formVideoId);
        final boolean formVideoExist = formVideo != null;
        formVideo = formVideoExist ? formVideo : new FormVideo();
        formVideo.setQuality(Integer.parseInt(quality.trim()));
        Video video = persistance.getVideoById(formVideo.getVideoId());
        if (video == null) {
            //if we can`t find Video by videoId in DB - remove videoId from formVideo.
            formVideo.setVideoId(null);
        }
        if (!formVideoExist) {
            persistance.putFormVideo(formVideo);
        }
        return formVideo;
    }
}
