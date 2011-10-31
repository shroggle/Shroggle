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
package com.shroggle.util.resource.resize;

import com.shroggle.entity.Video;
import com.shroggle.exception.VideoNotFoundException;
import com.shroggle.util.Dimension;
import com.shroggle.util.ResourceSizeCreator;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author Balakirev Anatoliy
 *         Date: 22.09.2009
 */
public class FfmpegVideoSizeCreator {

    public FfmpegVideoSizeCreator(Video video) {
        if (video == null) {
            throw new VideoNotFoundException("Can`t create FfmpegVideoSizeCreator by null video!");
        }
        this.video = video;
    }


    public String execute(final Integer requiredWidth, final Integer requiredHeight) {
        if (requiredWidth == null || requiredHeight == null) {
            return " ";
        }
        final Dimension dimension = ResourceSizeCreator.execute(requiredWidth, requiredHeight, video.getSourceWidth(), video.getSourceHeight(), true);
        if (!isMultipleOfTwo(dimension.getWidth())) {
            dimension.setWidth(createMultipleOfTwo(dimension.getWidth(), "width"));
        }
        if (!isMultipleOfTwo(dimension.getHeight())) {
            dimension.setHeight(createMultipleOfTwo(dimension.getHeight(), "height"));
        }
        String ffmpegSettings = " -s " + dimension.getWidth() + "x" + dimension.getHeight() + " ";
        if (dimension.getWidth() == requiredWidth && dimension.getHeight() < requiredHeight) {
            int pad = ((requiredHeight - dimension.getHeight()) / 2);
            pad = isMultipleOfTwo(pad) ? pad : createMultipleOfTwo(pad, "padtop/padbottom");
            ffmpegSettings += "-padtop " + pad;
            ffmpegSettings += " -padbottom " + pad;
            ffmpegSettings += " ";
        } else if (dimension.getHeight() == requiredHeight && dimension.getWidth() < requiredWidth) {
            int pad = ((requiredWidth - dimension.getWidth()) / 2);
            pad = isMultipleOfTwo(pad) ? pad : createMultipleOfTwo(pad, "padleft/padright");
            ffmpegSettings += "-padleft " + pad;
            ffmpegSettings += " -padright " + pad;
            ffmpegSettings += " ";
        }
        return ffmpegSettings;
    }


    private int createMultipleOfTwo(final int oldValue, final String valueName) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.log(Level.INFO, "Video " + valueName + " must be multiple of 2! Old video " + valueName + " = " + oldValue);
        int newValue = oldValue + 1;
        logger.log(Level.INFO, "New video " + valueName + " = " + newValue);
        return newValue;
    }

    private boolean isMultipleOfTwo(int number) {
        return number % 2 == 0;
    }


    private final Video video;
}
