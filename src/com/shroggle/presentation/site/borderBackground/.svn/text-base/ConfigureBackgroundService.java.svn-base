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

package com.shroggle.presentation.site.borderBackground;

import com.shroggle.entity.*;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.borderBackground.BackgroundImageManager;
import com.shroggle.logic.borderBackground.BackgroundLogic;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageTitle;
import com.shroggle.logic.site.page.pageversion.PageTitleGetter;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.image.ImageData;
import com.shroggle.presentation.site.WithPageVersionTitle;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import com.shroggle.util.resource.ResourceGetterUrl;
import com.shroggle.util.resource.provider.ResourceGetterType;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigureBackgroundService extends AbstractService implements WithWidgetTitle, WithPageVersionTitle {

    @SynchronizeByMethodParameter(entityClass = Widget.class)
    public void execute(final Integer widgetId, final Integer draftItemId, final Integer backgroundId, final boolean showForPage)
            throws IOException, ServletException {
        new UsersManager().getLogined();

        this.itemId = widgetId;
        this.draftItemId = draftItemId;
        this.showForPage = showForPage;

        final Page page = persistance.getPage(widgetId);
        if (showForPage && page == null) {
            throw new PageNotFoundException();
        }

        widget = persistance.getWidget(widgetId);
        if (!showForPage && widgetId != null && widget == null) {
            throw new WidgetNotFoundException();
        }
        widgetTitle = (showForPage || widgetId == null) ? null : new WidgetTitleGetter(widget, "New Background and Border");
        pageTitle = showForPage ? new PageTitleGetter(new PageManager(page)) : null;
        siteId = getSiteId(widgetId, draftItemId, showForPage);


        savedInCurrentPlace = showForPage || widgetId == null || new WidgetManager(widgetId).isBackgroundSavedInCurrentPlace();
        background = BackgroundLogic.create(widgetId, draftItemId, backgroundId, showForPage);
        imageThumbnailsDatas = createBackroundImages(siteId);
        getContext().getHttpServletRequest().setAttribute("backgroundService", this);
    }

    @RemoteMethod
    public String showUploadedBackgroundImages(final Integer siteId) throws Exception {
        imageThumbnailsDatas = createBackroundImages(siteId);
        getContext().getHttpServletRequest().setAttribute("backgroundService", this);
        return getContext().forwardToString("/borderBackground/uploadedBackgroundImages.jsp");
    }

    /*------------------------------------------------private methods-------------------------------------------------*/
    private int getSiteId(final Integer itemId, final Integer draftItemId, final boolean showForPage) {
        if (showForPage) {
            return persistance.getPage(itemId).getSite().getSiteId();
        } else if (itemId != null) {
            final Widget widget = persistance.getWidget(itemId);
            return widget != null ? widget.getSite().getSiteId() : -1;
        } else {
            return new ItemManager(draftItemId).getSiteId();
        }
    }

    private List<ImageData> createBackroundImages(final Integer siteId) {
        final List<ImageData> imageThumbnailsDatas = new ArrayList<ImageData>();
        List<BackgroundImage> images = ServiceLocator.getPersistance().getBackgroundImagesBySiteId(siteId);
        Collections.sort(images, new Comparator<BackgroundImage>() {
            public int compare(final BackgroundImage i1, final BackgroundImage i2) {
                Date i1Date = i1.getCreated();
                Date i2Date = i2.getCreated();
                return i2Date.compareTo(i1Date);
            }
        });
        final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
        for (final BackgroundImage image : images) {
            if (!fileSystem.isResourceExist(image)) {
                continue;
            }
            final ResourceSize resourceSize = new BackgroundImageManager(image).getResourceSize();
            final String imageUrl;
            final Integer thumbnailHeight;
            if (resourceSize != null && resourceSize.getHeight() <= BackgroundImage.THUMBNAIL_HEIGHT) {
                thumbnailHeight = resourceSize.getHeight();
                imageUrl = resourceGetterUrl.get(ResourceGetterType.BACKGROUND_IMAGE, image.getBackgroundImageId(), 0, 0, 0, false);
            } else {
                thumbnailHeight = BackgroundImage.THUMBNAIL_HEIGHT;
                imageUrl = resourceGetterUrl.get(ResourceGetterType.BACKGROUND_IMAGE_THUMBNAIL, image.getBackgroundImageId(), 0, 0, 0, false);
            }
            ImageData imageData = new ImageData(image.getBackgroundImageId(), image.getTitle(), null, null, imageUrl, null, thumbnailHeight);
            imageThumbnailsDatas.add(imageData);
        }
        return imageThumbnailsDatas;
    }

    public List<ImageData> getImageThumbnailsDatas() {
        return imageThumbnailsDatas;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public int getSiteId() {
        return siteId;
    }

    public boolean isShowForPage() {
        return showForPage;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Background getDraftBackground() {
        return background;
    }


    public boolean isSavedInCurrentPlace() {
        return savedInCurrentPlace;
    }

    public Integer getDraftItemId() {
        return draftItemId;
    }

    public PageTitle getPageTitle() {
        return pageTitle;
    }

    public Widget getWidget() {
        return widget;
    }

    private boolean savedInCurrentPlace;
    private WidgetTitle widgetTitle;
    private Widget widget;
    private PageTitle pageTitle;
    private Background background;
    private int siteId;
    private Integer itemId;
    private Integer draftItemId;
    private boolean showForPage;
    private List<ImageData> imageThumbnailsDatas;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();

}