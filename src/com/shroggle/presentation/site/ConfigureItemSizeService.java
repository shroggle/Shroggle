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
package com.shroggle.presentation.site;

import com.shroggle.entity.ItemSize;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.Widget;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ConfigureItemSizeService extends AbstractService implements WithWidgetTitle {

    @SynchronizeByMethodParameter(
            entityClass = Widget.class)
    @RemoteMethod
    public void execute(final Integer widgetId, final Integer draftItemId) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();
        if (widgetId != null) {
            final Widget widget = userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(widgetId);
            final WidgetManager widgetManager = new WidgetManager(widget);
            itemSize = widgetManager.getItemSize(SiteShowOption.getDraftOption());
            savedInCurrentPlace = widgetManager.isItemSizeSavedInCurrentPlace();
            widgetTitle = new WidgetTitleGetter(widget, "Item Size");
            this.widget = widget;
        } else {
           itemSize = new ItemManager(draftItemId).getItemSize(SiteShowOption.getDraftOption());
            savedInCurrentPlace = true;
        }
        this.widgetId = widgetId;
        this.draftItemId = draftItemId;

        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("itemSizeService", this);
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public Widget getWidget() {
        return widget;
    }
    public ItemSize getItemSize() {
        return itemSize;
    }

    public boolean isSavedInCurrentPlace() {
        return savedInCurrentPlace;
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public Integer getDraftItemId() {
        return draftItemId;
    }

    private boolean savedInCurrentPlace;
    private ItemSize itemSize;
    private Integer widgetId;
    private Integer draftItemId;
    private WidgetTitle widgetTitle;
    private Widget widget;

}
