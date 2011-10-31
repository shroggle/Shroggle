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
import com.shroggle.logic.gallery.comment.GalleryCommentsManager;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class EditGalleryCommentService {

    public void execute(final EditGalleryCommentRequest request) {
        final GalleryCommentsManager galleryCommentsManager = new GalleryCommentsManager();
        galleryCommentsManager.edit(request.getCommentId(), request.getText());
    }

}