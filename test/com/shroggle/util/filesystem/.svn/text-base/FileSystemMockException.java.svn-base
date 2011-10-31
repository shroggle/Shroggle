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
 * @author Balakirev Anatoliy
 */
public class FileSystemMockException implements FileSystem {

    public String getLoginPageDefaultHtml() {
        return null;
    }

    public String getLoginAdminPageDefaultHtml() {
        return null;
    }

    public String getApplicationVersion() {
        return null;
    }

    public String getTemplateResource(final String templateDirectory, final String resourcePath) {
        throw new FileSystemException("");
    }

    public List<Template> getTemplates() {
        return null;
    }

    public Template getTemplateByDirectory(final String directory) {
        throw new FileSystemException("");
    }

    public String getTemplateThumbnailPath(Template template) {
        return null;
    }

    @Override
    public InputStream getResourceStream(final Resource resource) {
        throw new FileSystemException("Special exception!");
    }

    @Override
    public void inTransaction(final FileSystemTransaction transaction) {

    }

    @Override
    public void setPageResources(String name, String content) {
        
    }

    public String getLayoutThumbnailPath(Layout layout) {
        return null;
    }

    public String getThemeImageThumbnailPath(Theme theme) {
        return null;
    }

    public String getThemeColorTilePath(Theme theme) {
        return null;
    }

    public Set<String> getSitesResourcesUrl() throws FileSystemException {
        throw new FileSystemException("");
    }

    public List<String> getSpecies() throws FileSystemException {
        throw new FileSystemException("");
    }

    public List<String> getGenus() throws FileSystemException {
        throw new FileSystemException("");
    }

    public List<String> getFamily() throws FileSystemException {
        throw new FileSystemException("");
    }

    public List<CssParameter> getCssParameters(ItemType itemType, MenuStyleType menuStyleType) throws FileSystemException {
        throw new FileSystemException("");
    }

    @Override
    public String getResourcePath(final Resource resource) {
        throw new FileSystemException("Special exception!");
    }

    @Override
    public String getResourceName(final Resource resource) {
        throw new FileSystemException("Special exception!");
    }

    @Override
    public boolean isResourceExist(final Resource resource) {
        throw new FileSystemException("Special exception!");
    }


    @Override
    public BufferedImage getResource(final Resource resource) {
        throw new FileSystemException("Special exception!");
    }

    @Override
    public void setResourceStream(final Resource resource, final InputStream data) {
        throw new FileSystemException("Special exception!");
    }

    @Override
    public void setResource(final Resource resource, final BufferedImage data) {
        throw new FileSystemException("Special exception!");
    }

    @Override
    public void removeResource(final Resource resource) {

    }

    public String getThemeImagePath(final Theme theme) {
        return null;
    }

}