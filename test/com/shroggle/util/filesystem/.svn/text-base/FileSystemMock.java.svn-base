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
import java.util.*;

/**
 * @author Stasuk Artem
 */
public class FileSystemMock implements FileSystem {

    public String getLoginPageDefaultHtml() {
        return loginPageDefaultHtml;
    }

    public String getLoginAdminPageDefaultHtml() {
        return loginAdminPageDefaultHtml;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(final String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

    public String getTemplateResource(final String templateDirectory, final String resourcePath) {
        final String resource = templateResources.get(templateDirectory + resourcePath);
        if (resource == null) {
            throw new FileSystemException(
                    "Can't find resource " + resourcePath + " for template " + templateDirectory);
        }
        return resource;
    }

    public void addTemplateResource(final String templateDirectory, final String resourcePath, final String resource) {
        templateResources.put(templateDirectory + resourcePath, resource);
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public Template getTemplateByDirectory(final String directory) {
        for (final Template template : templates) {
            if (template.getDirectory().equals(directory)) {
                return template;
            }
        }
        throw new FileSystemException("Can't find template: " + directory);
    }

    public String getTemplateThumbnailPath(final Template template) {
        return "";
    }

    @Override
    public InputStream getResourceStream(final Resource resource) {
        return resourceStreams.get(getResourceId(resource));
    }

    @Override
    public void inTransaction(final FileSystemTransaction transaction) {
        throw new UnsupportedOperationException("Use real file system.");
    }

    @Override
    public void setPageResources(String name, String content) {

    }

    public String getLayoutThumbnailPath(final Layout layout) {
        return layoutThumbnailPaths.get(layout);
    }

    public String getThemeImageThumbnailPath(Theme theme) {
        return "";
    }

    public String getThemeColorTilePath(final Theme theme) {
        return themeColorTileUrls.get(theme);
    }

    public Set<String> getSitesResourcesUrl() throws FileSystemException {
        return sitesResourcesUrl;
    }

    public List<String> getSpecies() throws FileSystemException {
        return species;
    }

    public List<String> getGenus() throws FileSystemException {
        return genus;
    }

    public List<String> getFamily() throws FileSystemException {
        return families;
    }

    public List<CssParameter> getCssParameters(ItemType itemType, MenuStyleType menuStyleType) throws FileSystemException {
        final List<CssParameter> result = cssParameters.get(itemType);
        if (result != null) {
            return result;
        }

        return new ArrayList<CssParameter>();
    }

    @Override
    public String getResourcePath(final Resource resource) {
        return null;
    }

    @Override
    public String getResourceName(final Resource resource) {
        return null;
    }

    @Override
    public boolean isResourceExist(final Resource resource) {
        return resource != null && resourceStreams.get(getResourceId(resource)) != null;
    }

    @Override
    public BufferedImage getResource(final Resource resource) {
        return resourceDatas.get(resource);
    }

    @Override
    public void setResourceStream(final Resource resource, final InputStream data) {
        resourceStreams.put(getResourceId(resource), data);
    }

    private String getResourceId(final Resource resource) {
        return "" + resource.getResourceId() + resource.getSiteId() + resource.getVersion();
    }

    @Override
    public void setResource(final Resource resource, final BufferedImage data) {
        resourceDatas.put(resource, data);
    }

    @Override
    public void removeResource(final Resource resource) {
        resourceDatas.remove(resource);
    }


    public String getThemeImagePath(final Theme theme) {
        return null;
    }

    public void putTemplate(Template template) {
        templates.add(template);
    }

    public void addSitesResourcesUrl(String sitesResourceUrl) {
        sitesResourcesUrl.add(sitesResourceUrl);
    }

    public void addCssParameter(ItemType itemType, CssParameter cssParameter) {
        List<CssParameter> cssParameters = this.cssParameters.get(itemType);
        if (cssParameters == null) {
            cssParameters = new ArrayList<CssParameter>();
            this.cssParameters.put(itemType, cssParameters);
        }
        cssParameters.add(cssParameter);
    }

    public void setLoginPageDefaultHtml(final String loginPageDefaultHtml) {
        this.loginPageDefaultHtml = loginPageDefaultHtml;
    }

    public void setLoginAdminPageDefaultHtml(final String loginAdminPageDefaultHtml) {
        this.loginAdminPageDefaultHtml = loginAdminPageDefaultHtml;
    }

    public void setThemeColorTileUrl(final Theme theme, final String url) {
        themeColorTileUrls.put(theme, url);
    }

    public void setLayoutThumbnailPath(final Layout layout, final String path) {
        layoutThumbnailPaths.put(layout, path);
    }

    private final Map<Resource, BufferedImage> resourceDatas = new HashMap<Resource, BufferedImage>();
    private final Map<String, InputStream> resourceStreams = new HashMap<String, InputStream>();
    private List<String> species = new ArrayList<String>();
    private List<String> genus = new ArrayList<String>();
    private List<String> families = new ArrayList<String>();
    private Set<String> sitesResourcesUrl = new HashSet<String>();
    private Map<ItemType, List<CssParameter>> cssParameters = new HashMap<ItemType, List<CssParameter>>();
    private String applicationVersion;
    private String loginPageDefaultHtml;
    private String loginAdminPageDefaultHtml;
    private final List<Template> templates = new ArrayList<Template>();
    private final Map<String, String> templateResources = new HashMap<String, String>();
    private final Map<Theme, String> themeColorTileUrls = new HashMap<Theme, String>();
    private final Map<Layout, String> layoutThumbnailPaths = new HashMap<Layout, String>();
}

