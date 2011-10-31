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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.timecounter.TimeCounter;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * @author Stasuk Artem
 */
public class FileSystemTimeCounter implements FileSystem {

    public FileSystemTimeCounter(final FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    @Override
    public String getLoginPageDefaultHtml() {
        final TimeCounter timeCounter = createTimeCounter("getLoginPageDefaultHtml");
        try {
            return fileSystem.getLoginPageDefaultHtml();
        } finally {
            timeCounter.stop();
        }
    }

    private TimeCounter createTimeCounter(final String name) {
        return ServiceLocator.getTimeCounterCreator().create("fileSystem://" + name);
    }

    @Override
    public String getLoginAdminPageDefaultHtml() {
        final TimeCounter timeCounter = createTimeCounter("getLoginAdminPageDefaultHtml");
        try {
            return fileSystem.getLoginAdminPageDefaultHtml();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public String getApplicationVersion() {
        final TimeCounter timeCounter = createTimeCounter("getApplicationVersion");
        try {
            return fileSystem.getApplicationVersion();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public String getTemplateResource(String templateDirectory, String resourcePath) {
        final TimeCounter timeCounter = createTimeCounter("getTemplateResource");
        try {
            return fileSystem.getTemplateResource(templateDirectory, resourcePath);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<Template> getTemplates() {
        final TimeCounter timeCounter = createTimeCounter("getTemplates");
        try {
            return fileSystem.getTemplates();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Template getTemplateByDirectory(String directory) {
        final TimeCounter timeCounter = createTimeCounter("getTemplateByDirectory");
        try {
            return fileSystem.getTemplateByDirectory(directory);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public Set<String> getSitesResourcesUrl() {
        final TimeCounter timeCounter = createTimeCounter("getSitesResourcesUrl");
        try {
            return fileSystem.getSitesResourcesUrl();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<String> getSpecies() {
        final TimeCounter timeCounter = createTimeCounter("getSpecies");
        try {
            return fileSystem.getSpecies();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<String> getGenus() {
        final TimeCounter timeCounter = createTimeCounter("getGenus");
        try {
            return fileSystem.getGenus();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<String> getFamily() {
        final TimeCounter timeCounter = createTimeCounter("getFamily");
        try {
            return fileSystem.getFamily();
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public List<CssParameter> getCssParameters(ItemType itemType, MenuStyleType menuStyleType) {
        final TimeCounter timeCounter = createTimeCounter("getCssParameters");
        try {
            return fileSystem.getCssParameters(itemType, menuStyleType);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public String getResourcePath(Resource resource) {
        final TimeCounter timeCounter = createTimeCounter("getResourcePath");
        try {
            return fileSystem.getResourcePath(resource);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public String getResourceName(Resource resource) {
        final TimeCounter timeCounter = createTimeCounter("getResourceName");
        try {
            return fileSystem.getResourceName(resource);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public boolean isResourceExist(Resource resource) {
        final TimeCounter timeCounter = createTimeCounter("isResourceExist");
        try {
            return fileSystem.isResourceExist(resource);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public BufferedImage getResource(Resource resource) {
        final TimeCounter timeCounter = createTimeCounter("getResource");
        try {
            return fileSystem.getResource(resource);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void setResourceStream(Resource resource, InputStream data) {
        final TimeCounter timeCounter = createTimeCounter("setResourceStream");
        try {
            fileSystem.setResourceStream(resource, data);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void setResource(Resource resource, BufferedImage data) {
        final TimeCounter timeCounter = createTimeCounter("setResource");
        try {
            fileSystem.setResource(resource, data);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void removeResource(Resource resource) {
        final TimeCounter timeCounter = createTimeCounter("removeResource");
        try {
            fileSystem.removeResource(resource);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public String getLayoutThumbnailPath(Layout layout) {
        final TimeCounter timeCounter = createTimeCounter("getLayoutThumbnailPath");
        try {
            return fileSystem.getLayoutThumbnailPath(layout);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public String getThemeImagePath(Theme theme) {
        final TimeCounter timeCounter = createTimeCounter("getThemeImagePath");
        try {
            return fileSystem.getThemeImagePath(theme);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public String getThemeImageThumbnailPath(Theme theme) {
        final TimeCounter timeCounter = createTimeCounter("getThemeImageThumbnailPath");
        try {
            return fileSystem.getThemeImageThumbnailPath(theme);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public String getThemeColorTilePath(Theme theme) {
        final TimeCounter timeCounter = createTimeCounter("getThemeColorTilePath");
        try {
            return fileSystem.getThemeColorTilePath(theme);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public InputStream getResourceStream(Resource resource) {
        final TimeCounter timeCounter = createTimeCounter("getResourceStream");
        try {
            return fileSystem.getResourceStream(resource);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void inTransaction(FileSystemTransaction transaction) {
        final TimeCounter timeCounter = createTimeCounter("inTransaction");
        try {
            fileSystem.inTransaction(transaction);
        } finally {
            timeCounter.stop();
        }
    }

    @Override
    public void setPageResources(final String name, final String content) {
        final TimeCounter timeCounter = createTimeCounter("setPageResources");
        try {
            fileSystem.setPageResources(name, content);
        } finally {
            timeCounter.stop();
        }
    }

    private final FileSystem fileSystem;

}
