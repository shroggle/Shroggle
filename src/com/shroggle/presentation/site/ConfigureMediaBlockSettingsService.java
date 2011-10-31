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

import com.shroggle.entity.Widget;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.borderBackground.ConfigureBackgroundService;
import com.shroggle.presentation.site.borderBackground.ConfigureBorderService;
import com.shroggle.presentation.site.cssParameter.ConfigureFontsAndColorsService;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ConfigureMediaBlockSettingsService extends AbstractService {

    @SynchronizeByMethodParameter(
            entityClass = Widget.class)
    @RemoteMethod
    public String execute(final int widgetId, final ConfigureMediaBlockSettingsTab tab,
                          final boolean showSeparateTab)
            throws Exception {
        new UsersManager().getLogined();

        this.tab = tab;
        this.widgetId = widgetId;

        if (tab == ConfigureMediaBlockSettingsTab.FONTS_COLORS){
            fontsColorsService.execute(widgetId, null);
        } else if (tab == ConfigureMediaBlockSettingsTab.BORDER){
            borderService.execute(widgetId, null, null);
        } else if (tab == ConfigureMediaBlockSettingsTab.BACKGROUND){
            backgroundService.execute(widgetId, null, null, false);
        }

        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("mediaBlockSettingsService", this);

        if (showSeparateTab){
            return webContext.forwardToString("/site/configureMediaBlockSettingsSeparateTab.jsp");
        } else {
            return webContext.forwardToString("/site/configureMediaBlockSettings.jsp");
        }
    }

    public ConfigureMediaBlockSettingsTab getTab() {
        return tab;
    }

    public int getWidgetId() {
        return widgetId;
    }

    private ConfigureMediaBlockSettingsTab tab;
    private int widgetId;

    private final ConfigureFontsAndColorsService fontsColorsService = new ConfigureFontsAndColorsService();
    private final ConfigureBorderService borderService = new ConfigureBorderService();
    private final ConfigureBackgroundService backgroundService = new ConfigureBackgroundService();

}