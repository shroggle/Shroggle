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
package com.shroggle.logic.site.template;

import com.shroggle.entity.Theme;
import com.shroggle.entity.ThemeId;
import com.shroggle.entity.Template;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;

/**
 * @author Artem Stasuk
 */
public class ThemeLogic {

    public ThemeLogic(final Theme theme) {
        this.theme = theme;
    }

    public String getName() {
        return theme.getName() == null ? theme.getFile() : theme.getName();
    }

    public String getThumbnailUrl() {
        return ServiceLocator.getFileSystem().getThemeImageThumbnailPath(theme);
    }

    public String getColorTilesUrl() {
        return ServiceLocator.getFileSystem().getThemeColorTilePath(theme);
    }

    public String getImageUrl() {
        return ServiceLocator.getFileSystem().getThemeImagePath(theme);
    }

    public int getId() {
        return theme.getFile().hashCode();
    }

    private final Theme theme;

}
