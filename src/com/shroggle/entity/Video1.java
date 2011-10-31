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
 * @author Artem Stasuk
 */
public interface Video1 {

    boolean isDisplaySmallOptions();

    void setDisplaySmallOptions(boolean displaySmallOptions);

    boolean isDisplayLargeOptions();

    void setDisplayLargeOptions(boolean displayLargeOptions);

    Integer getImageWidth();

    void setImageWidth(Integer imageWidth);

    Integer getImageHeight();

    void setImageHeight(Integer imageHeight);

    boolean isSaveRatio();

    void setSaveRatio(boolean saveRatio);

    String getVideoSmallSize();

    void setVideoSmallSize(String videoSmallSize);

    String getVideoLargeSize();

    void setVideoLargeSize(String videoLargeSize);

    boolean isIncludeDescription();

    void setIncludeDescription(boolean includeDescription);

    String getKeywords();

    void setKeywords(String keywords);

    boolean isPlayInCurrentPage();

    void setPlayInCurrentPage(boolean playInCurrentPage);

    Integer getImageId();

    void setImageId(Integer imageId);

    Integer getFlvVideoId();

    void setFlvVideoId(Integer flvVideoId);

    Integer getLargeFlvVideoId();

    void setLargeFlvVideoId(Integer largeFlvVideoId);

    Integer getSmallFlvVideoId();

    void setSmallFlvVideoId(Integer smallFlvVideoId);

    String getDescription();

}
