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
 * @author Artem Stasuk
 */
public class FileSystemCache implements FileSystem {

    public FileSystemCache(final FileSystem fileSystem) {
        templates = Collections.unmodifiableList(fileSystem.getTemplates());
        final Map<String, Template> tempTempalteByDirectorys = new HashMap<String, Template>();
        for (final Template template : templates) {
            tempTempalteByDirectorys.put(template.getDirectory(), template);
        }
        this.templateByDirectorys = Collections.unmodifiableMap(tempTempalteByDirectorys);
        this.sitesResourcesUrl = Collections.unmodifiableSet(fileSystem.getSitesResourcesUrl());
        this.loginPageDefaultHtml = fileSystem.getLoginPageDefaultHtml();
        this.loginAdminPageDefaultHtml = fileSystem.getLoginAdminPageDefaultHtml();
        this.fileSystem = fileSystem;
    }

    @Override
    public String getLoginPageDefaultHtml() {
        return loginPageDefaultHtml;
    }

    @Override
    public String getLoginAdminPageDefaultHtml() {
        return loginAdminPageDefaultHtml;
    }

    @Override
    public String getApplicationVersion() {
        return fileSystem.getApplicationVersion();
    }

    @Override
    public String getTemplateResource(final String templateDirectory, final String resourcePath) {
        return fileSystem.getTemplateResource(templateDirectory, resourcePath);
    }

    @Override
    public List<Template> getTemplates() {
        return templates;
    }

    @Override
    public Template getTemplateByDirectory(final String directory) {
        return templateByDirectorys.get(directory);
    }

    @Override
    public Set<String> getSitesResourcesUrl() {
        return sitesResourcesUrl;
    }

    @Override
    public List<String> getSpecies() {
        return fileSystem.getSpecies();
    }

    @Override
    public List<String> getGenus() {
        return fileSystem.getGenus();
    }

    @Override
    public List<String> getFamily() {
        return fileSystem.getFamily();
    }

    public List<CssParameter> getCssParameters(ItemType itemType, MenuStyleType menuStyleType) {
        return fileSystem.getCssParameters(itemType, menuStyleType);
    }

    @Override
    public String getResourcePath(final Resource resource) {
        return fileSystem.getResourcePath(resource);
    }

    @Override
    public String getResourceName(final Resource resource) {
        return fileSystem.getResourceName(resource);
    }

    @Override
    public boolean isResourceExist(final Resource resource) {
        return fileSystem.isResourceExist(resource);
    }

    @Override
    public BufferedImage getResource(final Resource resource) {
        return fileSystem.getResource(resource);
    }

    @Override
    public void setResourceStream(final Resource resource, final InputStream data) {
        fileSystem.setResourceStream(resource, data);
    }

    @Override
    public void setResource(final Resource resource, final BufferedImage data) {
        fileSystem.setResource(resource, data);
    }

    @Override
    public void removeResource(final Resource resource) {
        fileSystem.removeResource(resource);
    }

    @Override
    public String getLayoutThumbnailPath(final Layout layout) {
        return fileSystem.getLayoutThumbnailPath(layout);
    }

    @Override
    public String getThemeImagePath(final Theme theme) {
        return fileSystem.getThemeImagePath(theme);
    }

    @Override
    public String getThemeImageThumbnailPath(final Theme theme) {
        return fileSystem.getThemeImageThumbnailPath(theme);
    }

    @Override
    public String getThemeColorTilePath(final Theme theme) {
        return fileSystem.getThemeColorTilePath(theme);
    }

    @Override
    public InputStream getResourceStream(final Resource resource) {
        return fileSystem.getResourceStream(resource);
    }

    @Override
    public void inTransaction(final FileSystemTransaction transaction) {
        fileSystem.inTransaction(transaction);
    }

    @Override
    public void setPageResources(final String name, final String content) {
        fileSystem.setPageResources(name, content);
    }

    private final FileSystem fileSystem;
    private final Set<String> sitesResourcesUrl;
    private final Map<String, Template> templateByDirectorys;
    private final List<Template> templates;
    private final String loginPageDefaultHtml;
    private final String loginAdminPageDefaultHtml;

}
