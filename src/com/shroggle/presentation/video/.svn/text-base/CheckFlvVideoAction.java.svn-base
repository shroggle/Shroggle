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

import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.logic.video.FlvVideoManager;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Stasuk Artem
 */
@UrlBinding("/video/checkFlvVideo.action")
public class CheckFlvVideoAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        FlvVideoManager flvVideoManager = new FlvVideoManager(persistance.getFlvVideo(flvVideoId));
        result = flvVideoManager.getFlvVideoStatusAndStartNewConversionIfNeeded();
        return resolutionCreator.forwardToUrl("/video/checkFlvVideo.jsp");
    }

    public void setFlvVideoId(final int flvVideoId) {
        this.flvVideoId = flvVideoId;
    }

    public String getResult() {
        return result;
    }

    private int flvVideoId;
    private String result;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}
