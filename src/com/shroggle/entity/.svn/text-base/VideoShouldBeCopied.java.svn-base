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

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Balakirev Anatoliy
 */
@Entity(name = "videoShouldBeCopied")
public class VideoShouldBeCopied {

    public VideoShouldBeCopied() {
    }

    public VideoShouldBeCopied(int copiedVideo, int sourceVideo) {
        this.copiedVideo = copiedVideo;
        this.sourceVideo = sourceVideo;
    }

    @Id
    private int id;

    private int sourceVideo;

    private int copiedVideo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSourceVideo() {
        return sourceVideo;
    }

    public void setSourceVideo(int sourceVideo) {
        this.sourceVideo = sourceVideo;
    }

    public int getCopiedVideo() {
        return copiedVideo;
    }

    public void setCopiedVideo(int copiedVideo) {
        this.copiedVideo = copiedVideo;
    }
}
