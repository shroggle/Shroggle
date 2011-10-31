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
package com.shroggle.presentation.site.page;

import com.shroggle.entity.AccessibleElementType;
import com.shroggle.entity.Site;
import com.shroggle.entity.Widget;
import com.shroggle.exception.ScriptNotFoundException;
import com.shroggle.logic.site.page.*;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.accessibilityForRender.ConfigureAccessibleSettingsService;
import com.shroggle.presentation.site.borderBackground.ConfigureBackgroundService;
import com.shroggle.presentation.site.htmlAndCss.ConfigurePageHtmlAndCssService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ConfigurePageSettingsService extends AbstractService {

    @SynchronizeByMethodParameter(entityClass = Widget.class)
    @RemoteMethod
    public ConfigurePageSettingsResponse execute(final int siteId, Integer pageId,
                                                 final ConfigurePageSettingsTab tab, final boolean showSeparateTab) throws Exception {
        new UsersManager().getLogined();

        final ConfigurePageSettingsResponse response = new ConfigurePageSettingsResponse();

        if (pageId == null) {
            com.shroggle.logic.site.page.SavePageResponse savePageResponse = new PageCreator().createDefault(siteId);
            pageId = savePageResponse.getPageId();
            response.setCreatedDefaultPageId(savePageResponse.getPageId());
        }

        this.tab = tab;
        this.pageId = pageId;

        site = ServiceLocator.getPersistance().getSite(siteId);

        if (site == null) {
            throw new ScriptNotFoundException("Cannot find site by Id=" + siteId);
        }

        if (tab == ConfigurePageSettingsTab.PAGE_NAME ||
                tab == ConfigurePageSettingsTab.SEO_HTML ||
                tab == ConfigurePageSettingsTab.SEO_META_TAGS) {
            pageToEditGetterService.execute(siteId, pageId);
        } else if (tab == ConfigurePageSettingsTab.BACKGROUND) {
            backgroundService.execute(pageId, null, null, true);
        } else if (tab == ConfigurePageSettingsTab.ACCESSIBILITY) {
            accessibleService.execute(pageId, AccessibleElementType.PAGE, false);
        } else if (tab == ConfigurePageSettingsTab.HTML_CSS) {
            htmlAndCssService.execute(pageId);
        } else if (tab == ConfigurePageSettingsTab.LAYOUT) {
            layoutService.execute(pageId);
        } else if (tab == ConfigurePageSettingsTab.BLUEPRINT_PERMISSIONS){
            blueprintPagePermissionsService.execute(pageId);
        }

        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("pageSettingsService", this);

        if (showSeparateTab) {
            response.setHtml(webContext.forwardToString("/site/page/configurePageSettingsSeparateTab.jsp"));
        } else {
            response.setHtml(webContext.forwardToString("/site/page/configurePageSettings.jsp"));
        }

        return response;
    }

    public ConfigurePageSettingsTab getTab() {
        return tab;
    }

    public int getPageId() {
        return pageId;
    }

    public Site getSite() {
        return site;
    }

    private ConfigurePageSettingsTab tab;
    private int pageId;
    private Site site;

    private final PageToEditGetterService pageToEditGetterService = new PageToEditGetterService();
    private final ConfigureBackgroundService backgroundService = new ConfigureBackgroundService();
    private final ConfigureAccessibleSettingsService accessibleService = new ConfigureAccessibleSettingsService();
    private final ConfigurePageHtmlAndCssService htmlAndCssService = new ConfigurePageHtmlAndCssService();
    private final ConfigurePageLayoutService layoutService = new ConfigurePageLayoutService();
    private final ConfigureBlueprintPagePermissionsService blueprintPagePermissionsService =
            new ConfigureBlueprintPagePermissionsService();

}
