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
package com.shroggle.entity;

import javax.persistence.*;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "formVideos")
public class FormVideo {

    @Id
    private int formVideoId;
    private Integer imageId;
    private Integer videoId;// Link to the Video.class
    private int quality = FlvVideo.DEFAULT_VIDEO_QUALITY;

    public int getFormVideoId() {
        return formVideoId;
    }

    public void setFormVideoId(int formVideoId) {
        this.formVideoId = formVideoId;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

}
