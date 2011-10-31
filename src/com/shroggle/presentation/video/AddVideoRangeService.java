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

import com.shroggle.entity.GalleryVideoRange;
import com.shroggle.entity.PageVisitor;
import com.shroggle.exception.UserException;
import com.shroggle.logic.gallery.VideoRangeEdit;
import com.shroggle.logic.gallery.VideoRangesManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ActionUtil;
import com.shroggle.util.ServiceLocator;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
public class AddVideoRangeService extends AbstractService {

    @RemoteMethod
    public void execute(final VideoRangeEdit edit) {
        final GalleryVideoRange galleryVideoRange = VideoRangesManager.createGalleryVideoRange(edit);
        if (galleryVideoRange != null) {
            try {
                final UserManager userManager = new UsersManager().getLogined();
                userManager.getVideoRanges().add(galleryVideoRange);
            } catch (final UserException exception) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Can`t add video range to logined user. User not found. Trying to add videoRange to pageVisitor:");
                final Integer pageVisitorId = ActionUtil.getPageVisitorId(getContext().getHttpServletRequest().getCookies());
                final PageVisitor pageVisitor = ServiceLocator.getPersistance().getPageVisitorById(pageVisitorId);
                if (pageVisitor != null) {
                    ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                        @Override
                        public void run() {
                            pageVisitor.addVideoRangeId(galleryVideoRange.getRangeId());
                        }
                    });
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "VideoRange  successfully added to pageVisitor with id = " + pageVisitor.getPageVisitorId());
                } else {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Can`t add videoRange! Page visitor not found!");
                }
            }
        }
    }

}
