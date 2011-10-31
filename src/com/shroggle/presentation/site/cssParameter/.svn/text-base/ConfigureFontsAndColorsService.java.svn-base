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

package com.shroggle.presentation.site.cssParameter;

import com.shroggle.entity.*;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.fontsAndColors.FontsAndColorsManager;
import com.shroggle.logic.fontsAndColors.FontsAndColorsValueManager;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.pageversion.PageTitle;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.WithPageVersionTitle;
import com.shroggle.presentation.site.WithWidgetTitle;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigureFontsAndColorsService extends AbstractService
        implements WithWidgetTitle, WithPageVersionTitle {

    @SynchronizeByMethodParameter(
            entityClass = Widget.class)
    @RemoteMethod
    public void execute(final Integer widgetId, final Integer draftItemId) throws IOException, ServletException {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();
        this.widgetId = widgetId;
        this.draftItemId = draftItemId;

        MenuStyleType menuStyleType = MenuStyleType.TREE_STYLE_HORIZONTAL;
        final FontsAndColorsManager fontsAndColorsManager;
        if (widgetId != null) {
            widget = userRightManager.getSiteRight().getWidgetForEditInPresentationalService(widgetId);
            widgetTitle = new WidgetTitleGetter(widget, "Fonts & Styles");
            if (widget.isWidgetItem()) {
                final DraftItem item = ((WidgetItem) widget).getDraftItem();
                if (item != null && item.getItemType() == ItemType.MENU) {
                    menuStyleType = ((DraftMenu) item).getMenuStyleType();
                }
            }
            cssParameters = fileSystem.getCssParameters(widget.getItemType(), menuStyleType);
            final WidgetManager widgetManager = new WidgetManager(widget);
            fontsAndColorsManager = new FontsAndColorsManager(widgetManager.getFontsAndColors(SiteShowOption.getDraftOption()));
            savedInCurrentPlace = widgetManager.isFontsAndColorsSavedInCurrentPlace();
        } else {
            final ItemManager itemManager = new ItemManager(draftItemId);
            if (itemManager.getType() == ItemType.MENU) {
                menuStyleType = ((DraftMenu) itemManager.getDraftItem()).getMenuStyleType();
            }
            cssParameters = fileSystem.getCssParameters(itemManager.getType(), menuStyleType);
            fontsAndColorsManager = new FontsAndColorsManager(itemManager.getExistingFontsAndColorsOrCreateNew(SiteShowOption.getDraftOption()));
        }
        Collections.sort(cssParameters);
        
        for (final CssParameter cssParameter : cssParameters) {
            final FontsAndColorsValueManager valueManager = fontsAndColorsManager.getFontsAndColorsValue(cssParameter.getName(), cssParameter.getSelector());
            if (valueManager != null) {
                cssParameter.setValue(valueManager.getValue());
            }
        }

        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("fontsColorsService", this);
    }

    public Integer getWidgetId() {
        return widgetId;
    }

    public Widget getWidget() {
        return widget;
    }

    public List<CssParameter> getCssParameters() {
        return cssParameters;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public PageTitle getPageTitle() {
        return pageTitle;
    }

    public boolean isSavedInCurrentPlace() {
        return savedInCurrentPlace;
    }

    public Integer getDraftItemId() {
        return draftItemId;
    }

    private boolean savedInCurrentPlace;
    private Integer widgetId;
    private Widget widget;
    private Integer draftItemId;
    private WidgetTitle widgetTitle;
    private PageTitle pageTitle;
    private List<CssParameter> cssParameters;
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();

}