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

import com.shroggle.entity.*;
import com.shroggle.exception.UserException;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperties;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UrlBinding("/site/getBlueprintDetail.action")
public class GetBlueprintDetailAction extends Action {

    @SynchronizeByClassProperties({
            @SynchronizeByClassProperty(
                    entityClass = Site.class,
                    entityIdFieldPath = "blueprintId")})
    @DefaultHandler
    public Resolution execute() {
        try {
            new UsersManager().getLogined();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        }

        final Site blueprint = persistance.getSite(blueprintId);
        if (blueprint != null && blueprint.getActivated() != null) {
            final Template template = fileSystem.getTemplateByDirectory(blueprint.getThemeId().getTemplateDirectory());
            templateName = template.getName();

            for (final Theme theme : template.getThemes()) {
                if (theme.getFile().equals(blueprint.getThemeId().getThemeCss())) {
                    themeName = theme.getName();
                    break;
                }
            }

            for (final PageManager pageManager : new SiteManager(blueprint).getPages()) {
                final WorkPageSettings workPageSettings = pageManager.getWorkPageSettings();

                if (workPageSettings != null) {
                    for (final Widget widget : workPageSettings.getWidgets()) {
                        if (widget.isWidgetItem()) {
                            final WidgetItem widgetItem = (WidgetItem) widget;

                            final WorkItem workItem = persistance.getWorkItem(widgetItem.getDraftItem().getId());
                            if (workItem != null && !StringUtil.isNullOrEmpty(workItem.getName())) {
                                itemNames.add(workItem.getName());
                            }
                        }
                    }
                }
            }

            for (MenuItem menuItem : blueprint.getMenu().getMenuItems()){
                pageNames.add(new PageManager(ServiceLocator.getPersistance().getPage(menuItem.getPageId()), SiteShowOption.OUTSIDE_APP).getName());
            }

            Collections.sort(itemNames);
        }

        return resolutionCreator.forwardToUrl("/WEB-INF/pages/getBlueprintDetail.jsp");
    }

    public void setBlueprintId(final int blueprintId) {
        this.blueprintId = blueprintId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getThemeName() {
        return themeName;
    }

    public List<String> getItemNames() {
        return itemNames;
    }

    public List<String> getPageNames() {
        return pageNames;
    }

    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private List<String> pageNames = new ArrayList<String>();
    private String templateName;
    private String themeName;
    private List<String> itemNames = new ArrayList<String>();
    private int blueprintId;

}