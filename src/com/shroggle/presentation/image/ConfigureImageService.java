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

import com.shroggle.entity.DraftImage;
import com.shroggle.entity.ImageFile;
import com.shroggle.entity.ImageFileType;
import com.shroggle.entity.Widget;
import com.shroggle.logic.site.item.ConfigureItemData;
import com.shroggle.logic.site.item.ItemsManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * @author Stasuk Artem, Anatoliy Balakirev, dmitry.solomadin
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigureImageService extends AbstractService implements WithWidgetTitle {

    @SynchronizeByMethodParameter(
            entityClass = Widget.class,
            method = SynchronizeMethod.READ,
            entityIdParameterIndex = 0)
    @RemoteMethod
    public void execute(final Integer widgetId, final Integer imageId) throws IOException, ServletException {
        new UsersManager().getLogined();

        final ConfigureItemData<DraftImage> itemData =
                new ItemsManager().getConfigureItemData(widgetId, imageId);

        imageItem = itemData.getDraftItem();
        widgetTitle = itemData.getWidget() != null ? new WidgetTitleGetter(itemData.getWidget()) : null;

        configureImageData = new ConfigureImageData(imageItem, widgetId);
        imageFiles = configureImageData.createImageFiles(configureImageData.getImageFileType(), configureImageData.getImageFileId());
        getContext().getHttpServletRequest().setAttribute("imageService", this);
    }

    @SynchronizeByMethodParameter(
            entityClass = Widget.class,
            method = SynchronizeMethod.READ,
            entityIdParameterIndex = 0)
    @RemoteMethod
    public String createImageFileList(final Integer widgetId, final int imageItemId, final ImageFileType imageFileType) throws IOException, ServletException {
        new UsersManager().getLogined();

        showLastImageFile = true;
        configureImageData = new ConfigureImageData(persistance.<DraftImage>getDraftItem(imageItemId), widgetId);
        imageFiles = configureImageData.createImageFiles(imageFileType, configureImageData.getImageFileId());
        getContext().getHttpServletRequest().setAttribute("imageService", this);
        return getContext().forwardToString("/image/uploadedImageFilesSelect.jsp");
    }

    public ConfigureImageData getConfigureImageData() {
        return configureImageData;
    }

    public boolean isShowLastImageFile() {
        return showLastImageFile;
    }

    public List<ImageFile> getImageFiles() {
        return imageFiles;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public DraftImage getImageItem() {
        return imageItem;
    }

    private WidgetTitle widgetTitle;
    private boolean showLastImageFile;
    private DraftImage imageItem;
    private List<ImageFile> imageFiles;
    private ConfigureImageData configureImageData;
    private Persistance persistance = ServiceLocator.getPersistance();

}