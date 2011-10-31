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
package com.shroggle.logic.gallery.comment;

import com.shroggle.entity.*;
import com.shroggle.exception.FilledFormNotFoundException;
import com.shroggle.exception.SiteItemNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.presentation.gallery.comment.GalleryCommentData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * @author Artem Stasuk
 */
public class GalleryCommentsManager {

    public GalleryCommentsManager() {
    }

    public GalleryCommentInfo getInfo(final GalleryComment galleryComment) {
        final GalleryCommentInfo galleryCommentInfo = new GalleryCommentInfo();
        galleryCommentInfo.setCommentId(galleryComment.getCommentId());
        galleryCommentInfo.setUserId(galleryComment.getUserId());

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy @ HH:mm:ss");

        galleryCommentInfo.setCreationDate(simpleDateFormat.format(galleryComment.getCreated()));
        galleryCommentInfo.setText(galleryComment.getText());
        galleryCommentInfo.setUserEmail(new UserManager(galleryComment.getUserId()).getEmail());

        return galleryCommentInfo;
    }

    public List<GalleryComment> get(final int filledFormId, final int galleryId) {
        final DraftGallery gallery = persistance.getDraftItem(galleryId);
        if (gallery == null) {
            throw new SiteItemNotFoundException("Can't find gallery " + galleryId);
        }

        Integer userId = null;
        if (!gallery.getVoteSettings().isDisplayComments()) {
            try {
                final User user = new UsersManager().getLoginedUser();
                userId = user != null ? user.getUserId() : null;
            } catch (final UserException exception) {
                return new ArrayList<GalleryComment>();
            }
        }

        Date start = null;
        Date finish = null;
        if (gallery.getVoteSettings().isDurationOfVoteLimited()) {
            start = gallery.getVoteSettings().getStartDate();
            finish = gallery.getVoteSettings().getEndDate();
        }

        return persistance.getGalleryCommentsByFilledFormAndGallery(
                filledFormId, gallery.getId(), userId, start, finish);
    }

    public int getCommentsCount(final int filledFormId, final int galleryId) {
        return get(filledFormId, galleryId).size();
    }

    public GalleryComment add(final int filledFormId, final int galleryId, final String text) {
        return persistanceTransaction.execute(new PersistanceTransactionContext<GalleryComment>() {

            @Override
            public GalleryComment execute() {
                final FilledForm filledForm = persistance.getFilledFormById(filledFormId);
                if (filledForm == null) {
                    throw new FilledFormNotFoundException("Can't find filled form " + filledFormId);
                }

                final DraftGallery gallery = persistance.getDraftItem(galleryId);
                if (gallery == null) {
                    throw new SiteItemNotFoundException("Can't find gallery " + galleryId);
                }

                Integer userId = null;
                try {
                    final User user = new UsersManager().getLoginedUser();
                    userId = user != null ? user.getUserId() : null;
                } catch (final UserException exception) {
                    // None...
                }

                final GalleryComment galleryComment;
                galleryComment = new GalleryComment();
                filledForm.addComment(galleryComment);
                galleryComment.setGallery(gallery);
                galleryComment.setUserId(userId);
                persistance.putGalleryComment(galleryComment);

                galleryComment.setText(StringUtil.getEmptyOrString(text));
                return galleryComment;
            }

        });
    }

    public void edit(final int commentId, final String text) {
        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                final GalleryComment galleryComment = getForEdit(commentId);
                if (galleryComment != null) {
                    galleryComment.setText(StringUtil.getEmptyOrString(text));
                }
            }

        });
    }

    public void remove(final int commentId) {
        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                final GalleryComment galleryComment = getForEdit(commentId);
                if (galleryComment != null) {
                    persistance.removeGalleryComment(galleryComment);
                }
            }

        });
    }

    private GalleryComment getForEdit(final int commentId) {
        final GalleryComment galleryComment = persistance.getGalleryCommentById(commentId);
        if (galleryComment != null) {
            if (!galleryComment.getGallery().getVoteSettings().isDisplayComments()) {
                try {
                    final User user = new UsersManager().getLoginedUser();
                    final int loginedUserId = user != null ? user.getUserId() : -1;
                    if (galleryComment.getUserId() == loginedUserId) {
                        return galleryComment;
                    }
                } catch (final UserException exception) {
                    // None...
                }
            } else {
                return galleryComment;
            }
        }
        return null;
    }

    public GalleryCommentData createData(final int filledFormId, final int galleryId, final int widgetId) {
        final GalleryCommentData galleryCommentData = new GalleryCommentData();
        galleryCommentData.setFilledFormId(filledFormId);
        galleryCommentData.setGalleryId(galleryId);
        galleryCommentData.setWidgetId(widgetId);
        galleryCommentData.setSiteId(persistance.getWidget(widgetId).getSite().getSiteId());

        return galleryCommentData;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
