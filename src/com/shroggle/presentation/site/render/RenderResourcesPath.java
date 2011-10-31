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

package com.shroggle.presentation.site.render;

import com.shroggle.entity.ThemeId;
import com.shroggle.logic.site.page.PageManager;

/**
 * @author Stasuk Artem
 */
public class RenderResourcesPath implements Render {

    public RenderResourcesPath(final String pathToTemplates, final PageManager pageManager) {
        this.pathToTemplates = pathToTemplates;
        this.pageManager = pageManager;
    }

    public void execute(final RenderContext context, final StringBuilder html) {
        final ThemeId themeId = pageManager.getRealThemeId();
        int i = html.indexOf("<!-- TEMPLATE_RESOURCE -->");
        while (i > -1) {
            html.replace(i, i + "<!-- TEMPLATE_RESOURCE -->".length(), pathToTemplates + "/" + themeId.getTemplateDirectory());
            i = html.indexOf("<!-- TEMPLATE_RESOURCE -->");
        }
    }

    private final String pathToTemplates;
    private final PageManager pageManager;

}