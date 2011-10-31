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

package com.shroggle.presentation.image;


import com.shroggle.entity.*;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.HtmlUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.ResourceGetterUrl;
import com.shroggle.util.resource.provider.ResourceGetterType;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;


/**
 * @author Balakirev Anatoliy
 */
//todo add tests (Anatoliy Balakirev)
@RemoteProxy
public class ShowImageFilesService extends AbstractService {

    @RemoteMethod
    public String showImageText(final Integer imageId, final int windowWidth, final int windowHeight,
                                final SiteShowOption siteShowOption) throws Exception {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        final Persistance persistance = ServiceLocator.getPersistance();
        draftImage = siteShowOption.isDraft() ? (DraftImage) persistance.getDraftItem(imageId) : (WorkImage) persistance.getWorkItem(imageId);
        if (draftImage != null) {
            getContext().getHttpServletRequest().setAttribute("service", this);
            return getContext().forwardToString("/image/showImageItem.jsp");
        }
        return "";
    }

    @RemoteMethod
    public String getImageFileUrl(final Integer imageId, final int windowWidth, final int windowHeight,
                                  final int itemWidth, final int itemHeight, final SiteShowOption siteShowOption) throws Exception {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.itemWidth = itemWidth;
        this.itemHeight = itemHeight;
        xScroll = windowWidth < itemWidth ? "scroll" : "none";
        yScroll = windowHeight < itemHeight ? "scroll" : "none";
        final Persistance persistance = ServiceLocator.getPersistance();
        draftImage = siteShowOption.isDraft() ? (DraftImage) persistance.getDraftItem(imageId) : (WorkImage) persistance.getWorkItem(imageId);
        if (draftImage != null) {
            final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
            imageFileUrl = resourceGetterUrl.get(ResourceGetterType.IMAGE_FILE, draftImage.getImageFileId(), 0, 0, 0, false);
            imageFile = persistance.getImageFileById(draftImage.getImageFileId());
            if (imageFile != null) {
                getContext().getHttpServletRequest().setAttribute("service", this);
                return getContext().forwardToString("/image/showImageItem.jsp");
            }
        }
        return "";
    }

    @RemoteMethod
    public String getAudioUrl(final Integer imageId, final SiteShowOption siteShowOption) throws Exception {
        final Persistance persistance = ServiceLocator.getPersistance();
        draftImage = siteShowOption.isDraft() ? (DraftImage) persistance.getDraftItem(imageId) : (WorkImage) persistance.getWorkItem(imageId);
        if (draftImage != null) {
            final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
            imageFileUrl = resourceGetterUrl.get(ResourceGetterType.IMAGE_FILE, draftImage.getImageFileId(), 0, 0, 0, false);
            imageFile = persistance.getImageFileById(draftImage.getImageFileId());
            if (imageFile.getImageFileType().equals(ImageFileType.AUDIO)) {
                return HtmlUtil.encodeToPercent(imageFileUrl);
            }
        }
        return "";
    }

    public ImageFile getImageFile() {
        return imageFile;
    }

    public String getImageFileUrl() {
        return imageFileUrl;
    }

    public Image1 getDraftImage() {
        return draftImage;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getItemWidth() {
        return itemWidth;
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public String getXScroll() {
        return xScroll;
    }

    public String getYScroll() {
        return yScroll;
    }

    private String xScroll;
    private String yScroll;
    private int windowWidth;
    private int windowHeight;
    private int itemWidth;
    private int itemHeight;
    private ImageFile imageFile;
    private String imageFileUrl;
    private Image1 draftImage;
}

