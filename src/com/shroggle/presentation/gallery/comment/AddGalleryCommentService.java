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
package com.shroggle.presentation.gallery.comment;

import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import com.shroggle.util.international.International;
import com.shroggle.util.ServiceLocator;
import com.shroggle.logic.gallery.comment.GalleryCommentsManager;
import com.shroggle.logic.gallery.comment.GalleryCommentInfo;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.exception.UserNotLoginedException;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class AddGalleryCommentService {

    public GalleryCommentInfo execute(final AddGalleryCommentRequest request) {
        if (!new UsersManager().isUserLoginedAndHasRightsToSite(request.getSiteId())) {
            throw new UserNotLoginedException(commentInternational.get("youHaveToBeLoggedIn"));
        }

        final GalleryCommentsManager galleryCommentsManager = new GalleryCommentsManager();
        return galleryCommentsManager.getInfo(
                galleryCommentsManager.add(request.getFilledFormId(), request.getGalleryId(), request.getText()));
    }

    private International commentInternational = ServiceLocator.getInternationStorage().get("comment", Locale.US);
}
