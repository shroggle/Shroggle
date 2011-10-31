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

package com.shroggle.util.filesystem;

import com.shroggle.entity.*;
import com.shroggle.presentation.site.cssParameter.CssParameter;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * @author Stasuk Artem
 */
public interface FileSystem {

    String getLoginPageDefaultHtml();

    String getLoginAdminPageDefaultHtml();

    String getApplicationVersion();

    String getTemplateResource(String templateDirectory, String resourcePath);

    List<Template> getTemplates();

    Template getTemplateByDirectory(String directory);

    Set<String> getSitesResourcesUrl();
    
    List<String> getSpecies();

    List<String> getGenus();

    List<String> getFamily();

    List<CssParameter> getCssParameters(ItemType itemType, MenuStyleType menuStyleType);

    String getResourcePath(Resource resource);

    String getResourceName(Resource resource);

    boolean isResourceExist(Resource resource);

    BufferedImage getResource(Resource resource);

    void setResourceStream(Resource resource, InputStream data);

    void setResource(Resource resource, BufferedImage data);

    void removeResource(Resource resource);

    String getLayoutThumbnailPath(Layout layout);

    String getThemeImagePath(Theme theme);

    String getThemeImageThumbnailPath(Theme theme);

    String getThemeColorTilePath(Theme theme);

    InputStream getResourceStream(Resource resource);

    void inTransaction(FileSystemTransaction transaction);

    void setPageResources(String name, String content);

}
