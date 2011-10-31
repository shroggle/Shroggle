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
package com.shroggle.entity;

/**
 * @author dmitry.solomadin
 */
public interface SlideShowImage {

    public int getSlideShowImageId();

    public void setSlideShowImageId(int slideShowImageId);

    public int getPosition();

    public void setPosition(int position);

    public SlideShowImageType getImageType();

    public void setImageType(SlideShowImageType imageType);

    public int getImageId();

    public void setImageId(int imageId);

    public Integer getLinkBackGalleryWidgetId();

    public void setLinkBackGalleryWidgetId(Integer linkBackGalleryWidgetId);

    public SlideShow getSlideShow();

    public void setSlideShow(SlideShow slideShow);

}
