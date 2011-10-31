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
package com.shroggle.logic.gallery;

import com.shroggle.entity.FilledForm;
import com.shroggle.entity.GalleryVideoRange;
import com.shroggle.logic.user.UserManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Artem Stasuk
 */
public class VideoRangesManager {

    public VideoRangesManager(final UserManager userManager) {
        this.userManager = userManager;
    }

    public static GalleryVideoRange createGalleryVideoRange(final VideoRangeEdit edit) {
        if (edit == null || edit.getStart() > edit.getFinish()
                || edit.getFinish() < 0.1f || edit.getFinish() > edit.getTotal()) {
            return null;
        }
        return ServiceLocator.getPersistanceTransaction().execute(
                new PersistanceTransactionContext<GalleryVideoRange>() {
                    public GalleryVideoRange execute() {
                        final GalleryVideoRange range = new GalleryVideoRange();
                        range.setGalleryId(edit.getGalleryId());
                        range.setStart(edit.getStart());
                        range.setFilledFormId(edit.getFilledFormId());
                        range.setFinish(edit.getFinish());
                        range.setTotal(edit.getTotal());
                        ServiceLocator.getPersistance().putGalleryVideoRange(range);
                        return range;
                    }
                });
    }

    public void add(final GalleryVideoRange videoRange) {
        if (videoRange == null || videoRange.getUser() != null || userManager.getUser().getVideoRanges().contains(videoRange)) {
            return;
        }
        persistanceTransaction.execute(new Runnable() {
            @Override
            public void run() {
                userManager.getUser().addVideoRange(videoRange);
            }
        });
    }

    public Set<Integer> getFilledFormIds() {
        final Set<Integer> filledFormIds = new HashSet<Integer>();
        for (final GalleryVideoRange range : userManager.getUser().getVideoRanges()) {
            filledFormIds.add(range.getFilledFormId());
        }
        return filledFormIds;
    }

    public int getPercent(final int galleryId, final int filledFormId) {
        double total = 0;
        for (final GalleryVideoRange range : userManager.getUser().getVideoRanges()) {
            if (range.getGalleryId() == galleryId) {
                if (range.getFilledFormId() == filledFormId) {
                    total += (range.getFinish() - range.getStart()) * 100 / range.getTotal();
                }
            }
        }

        return (int) total;
    }

    public int getCount(final int galleryId, final int formId) {
        final Set<Integer> counts = new HashSet<Integer>();
        for (final GalleryVideoRange range : userManager.getUser().getVideoRanges()) {
            if (range.getGalleryId() == galleryId) {
                final FilledForm filledForm = persistance.getFilledFormById(range.getFilledFormId());
                if (filledForm != null && filledForm.getFormId() == formId) {
                    counts.add(filledForm.getFilledFormId());
                }
            }
        }
        return counts.size();
    }

    private final UserManager userManager;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
