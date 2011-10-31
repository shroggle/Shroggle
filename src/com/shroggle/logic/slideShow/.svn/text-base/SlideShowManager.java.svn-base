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
package com.shroggle.logic.slideShow;

import com.shroggle.entity.SlideShow;
import com.shroggle.entity.SlideShowDisplayType;
import com.shroggle.entity.SlideShowImage;
import com.shroggle.entity.SlideShowType;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
public class SlideShowManager {

    public SlideShowManager(final SlideShow slideShow) {
        this.slideShow = slideShow;
    }

    public int getSlideShowTopEdgeWidth() {
        return getDirection().equals("vertical") ? slideShow.getImageWidth() :
                slideShow.getImageWidth() * new SlideShowManager(slideShow).getDisplayItems();
    }

    public boolean isTreatAsCarousel() {
        return slideShow.getSlideShowType() == SlideShowType.MULTIPLE_IMAGES &&
                (slideShow.getDisplayType() == SlideShowDisplayType.CAROUSEL_VERTICAL ||
                        slideShow.getDisplayType() == SlideShowDisplayType.CAROUSEL_HORIZONTAL);
    }

    public String getDirection() {
        if (slideShow.getSlideShowType() == SlideShowType.MULTIPLE_IMAGES &&
                (slideShow.getDisplayType() == SlideShowDisplayType.CAROUSEL_VERTICAL ||
                        slideShow.getDisplayType() == SlideShowDisplayType.MOVING_STRIP_VERTICAL)) {
            return "vertical";
        } else {
            return "horizontal";
        }
    }

    public int getDisplayItems() {
        if (slideShow.getSlideShowType() == SlideShowType.SINGLE_IMAGE) {
            return 1;
        } else {
            return slideShow.getNumberOfImagesShown();
        }
    }

    public String getEffect() {
        return slideShow.getTransitionEffectType().toJQueryEffect();
    }

    public int getAnimationSpeed() {
        return slideShow.getTransitionEffectType().getSpeed();
    }

    public int getMaxImagePosition() {
        int maxPosition = 0;
        for (SlideShowImage slideShowImage : slideShow.getImages()) {
            if (slideShowImage.getPosition() > maxPosition) {
                maxPosition = slideShowImage.getPosition();
            }
        }

        return maxPosition;
    }

    public List<SlideShowImage> getSortedImages() {
        final List<SlideShowImage> slideShowImages = new ArrayList<SlideShowImage>(slideShow.getImages());
        Collections.sort(slideShowImages, new ImagePositionComparator());
        return slideShowImages;
    }

    public SlideShowImage getImageAfterPosition(final int position) {
        final List<SlideShowImage> sortedImages = getSortedImages();
        for (int i = 0; i < sortedImages.size(); i++) {
            if (i == sortedImages.size() - 1) {
                return null;
            }

            if (sortedImages.get(i).getPosition() == position) {
                return sortedImages.get(i + 1);
            }
        }

        return null;
    }

    public SlideShowImage getImageBeforePosition(final int position) {
        final List<SlideShowImage> sortedImages = getSortedImages();
        for (int i = 0; i < sortedImages.size(); i++) {
            if (sortedImages.get(i).getPosition() == position) {
                if (i == 0) {
                    return null;
                }

                return sortedImages.get(i - 1);
            }
        }

        return null;
    }

    private class ImagePositionComparator implements Comparator<SlideShowImage> {

        @Override
        public int compare(SlideShowImage i1, SlideShowImage i2) {
            Integer p1 = i1.getPosition();
            Integer p2 = i2.getPosition();

            return p1.compareTo(p2);
        }
    }

    private SlideShow slideShow;

}
