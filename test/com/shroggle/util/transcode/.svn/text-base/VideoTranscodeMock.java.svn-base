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
import com.shroggle.util.process.ThreadUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Artem Stasuk
 */
public class VideoTranscodeMock implements VideoTranscode {

    public void execute(final FlvVideo flvVideo, Video video) {
        if (executeTime != null) {
            ThreadUtil.sleep(executeTime);
        }
    }

    @Override
    public boolean isExecuting(final int videoFlvId) {
        return executing.contains(videoFlvId);
    }

    @Override
    public void destroy() {

    }

    public void setExecuteTime(final Long executeTime) {
        this.executeTime = executeTime;
    }

    public Set<Integer> getExecuting() {
        return executing;
    }

    private Long executeTime;
    private Set<Integer> executing = new HashSet<Integer>();

}
