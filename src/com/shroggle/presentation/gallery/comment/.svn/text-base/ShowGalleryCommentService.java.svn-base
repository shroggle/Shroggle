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

import com.shroggle.entity.GalleryComment;
import com.shroggle.logic.gallery.comment.GalleryCommentsManager;
import com.shroggle.logic.gallery.comment.GalleryCommentInfo;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ShowGalleryCommentService {

    public List<GalleryCommentInfo> execute(final ShowGalleryCommentRequest request)
            throws IOException, ServletException {
        final GalleryCommentsManager galleryCommentsManager = new GalleryCommentsManager();
        final List<GalleryComment> galleryComments =
                galleryCommentsManager.get(request.getFilledFormId(), request.getGalleryId());

        final List<GalleryCommentInfo> galleryCommentInfos = new ArrayList<GalleryCommentInfo>();
        for (GalleryComment galleryComment : galleryComments) {
            galleryCommentInfos.add(galleryCommentsManager.getInfo(galleryComment));
        }

        return galleryCommentInfos;
    }

}
