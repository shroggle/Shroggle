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

import com.shroggle.entity.Border;
import com.shroggle.entity.Page;
import com.shroggle.entity.Widget;
import com.shroggle.exception.PageNotFoundException;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.borderBackground.BorderLogic;
import com.shroggle.logic.borderBackground.RenderBorderBackground;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageTitle;
import com.shroggle.logic.site.page.pageversion.PageTitleGetter;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.WithPageVersionTitle;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.Dimension;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class, params = {})
public class ConfigureBorderService extends AbstractService implements WithWidgetTitle {

    @SynchronizeByMethodParameter(entityClass = Widget.class)
    @RemoteMethod
    public void execute(final Integer widgetId, final Integer draftItemId, final Integer borderBackgroundId)
            throws IOException, ServletException {
        new UsersManager().getLogined();

        this.itemId = widgetId;
        this.draftItemId = draftItemId;

        widget = persistance.getWidget(widgetId);
        if ( widgetId != null && widget == null) {
            throw new WidgetNotFoundException();
        }
        siteId = getSiteId(widgetId, draftItemId);
        border = BorderLogic.create(widgetId, draftItemId, borderBackgroundId);

        savedInCurrentPlace = widgetId == null || new WidgetManager(widgetId).isBorderSavedInCurrentPlace();
        widgetTitle = widgetId == null ? null : new WidgetTitleGetter(widget, "New Border & Spacing");

        previewImageStyle = RenderBorderBackground.getBorderStyle(border);
        Dimension dimension = RenderBorderBackground.createImageSize(border);
        previewImageStyle += "width:" + dimension.getWidth() + "px; height:" + dimension.getHeight() + "px;";

        getContext().getHttpServletRequest().setAttribute("borderService", this);
    }

    /*------------------------------------------------private methods-------------------------------------------------*/
    private int getSiteId(final Integer itemId, final Integer draftItemId) {
        if (itemId != null) {
            final Widget widget = persistance.getWidget(itemId);
            return widget != null ? widget.getSite().getSiteId() : -1;
        } else {
            return new ItemManager(draftItemId).getSiteId();
        }
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public Border getDraftBorder() {
        return border;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getPreviewImageStyle() {
        return previewImageStyle;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Integer getDraftItemId() {
        return draftItemId;
    }

    public boolean isSavedInCurrentPlace() {
        return savedInCurrentPlace;
    }

    public Widget getWidget(){
        return widget;
    }

    private boolean savedInCurrentPlace;
    private WidgetTitle widgetTitle;
    private Widget widget;
    private Border border;
    private String previewImageStyle;
    private int siteId;
    private Integer itemId;
    private Integer draftItemId;
    private final Persistance persistance = ServiceLocator.getPersistance();

}