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
import com.shroggle.util.IOUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigStorage;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * @author Stasuk Artem
 */
public class FileSystemReal implements FileSystem {

    @SuppressWarnings({"EmptyCatchBlock"})
    public FileSystemReal(
            final String pageTemplatesPath,
            final String cssParametersFile,
            final String sitesResourcesUrlFile,
            final String speciesFile,
            final String genusFile,
            final String familyFile,
            final String applicationVersionFile,
            final String loginPageDefaultHtmlFile,
            final String loginAdminPageDefaultHtmlFile) {
        this.cssParametersFile = cssParametersFile;
        this.pageTemplatesPath = pageTemplatesPath;
        this.sitesResourcesUrlFile = sitesResourcesUrlFile;
        this.loginPageDefaultHtmlFile = loginPageDefaultHtmlFile;
        this.loginAdminPageDefaultHtmlFile = loginAdminPageDefaultHtmlFile;
        this.speciesFile = speciesFile;
        this.genusFile = genusFile;
        this.familyFile = familyFile;

        String tempApplicationVersion = null;
        if (applicationVersionFile != null) {
            try {
                tempApplicationVersion = IOUtil.read(applicationVersionFile);
            } catch (final IOException exception) {
            }
        }
        this.applicationVersion = tempApplicationVersion;

        ImageIO.setUseCache(false);

        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        if (configStorage == null) {
            throw new UnsupportedOperationException(
                    "Can't get account resources path without config storage service!");
        }

        final Config config = configStorage.get();
        siteResourcesPath = config.getSiteResourcesPath();
        siteResourcesVideoPath = config.getSiteResourcesVideo().getPath();
        if (config.getSiteResourcesVideo().getCachePath() != null) {
            siteResourcesFlvVideoPath = config.getSiteResourcesVideo().getCachePath();
        } else {
            siteResourcesFlvVideoPath = siteResourcesVideoPath;
        }

        resourceDirectoryByTypes = new HashMap<ResourceType, String>();
        resourceDirectoryByTypes.put(ResourceType.VIDEO, "videoSource");
        resourceDirectoryByTypes.put(ResourceType.VIDEO_FLV, "video");
        resourceDirectoryByTypes.put(ResourceType.IMAGE, "image");
        resourceDirectoryByTypes.put(ResourceType.IMAGE_FILE, "imageFile");
        resourceDirectoryByTypes.put(ResourceType.FORM_FILE, "formFile");
        resourceDirectoryByTypes.put(ResourceType.WIDGET_IMAGE, "widgetImageThumbnail");
        resourceDirectoryByTypes.put(ResourceType.WIDGET_IMAGE_ROLL_OVER, "widgetImageRollOverThumbnail");
        resourceDirectoryByTypes.put(ResourceType.BACKGROUND_IMAGE, "backgroundImages/image");
        resourceDirectoryByTypes.put(ResourceType.IMAGE_FOR_VIDEO, "imagesForVideo/image");
        resourceDirectoryByTypes.put(ResourceType.MENU_IMAGE, "menuImages/image");
        resourceDirectoryByTypes.put(ResourceType.ICON, "icon");
    }

    @Override
    public List<CssParameter> getCssParameters(final ItemType itemType, final MenuStyleType menuStyleType) {
        try {
            final JAXBContext context = JAXBContext.newInstance(CssParametersLibrary.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            final File file = new File(cssParametersFile);
            if (file.exists()) {
                final CssParametersLibrary library = (CssParametersLibrary) unmarshaller.unmarshal(file);
                for (final CssParametersRecord record : library.getRecords()) {
                    if (record.getItemType() == itemType) {
                        if (record.getItemType() == ItemType.MENU) {
                            if (record.getMenuStyleType() == menuStyleType.getOldValue()) {
                                return record.getCssParameters();
                            }
                        } else {
                            return record.getCssParameters();
                        }
                    }
                }

                for (final CssParametersFloatRecord floatRecord : library.getFloatRecords()) {
                    if (floatRecord.getItemType() == itemType) {
                        if (floatRecord.getItemType() == ItemType.MENU) {
                            if (floatRecord.getMenuStyle() == menuStyleType) {
                                return fromCssParametersFloat(floatRecord.getFloatCssParameters());
                            }
                        } else {
                            return fromCssParametersFloat(floatRecord.getFloatCssParameters());
                        }
                    }
                }
            }
            return Collections.emptyList();
        } catch (JAXBException exception) {
            throw new FileSystemException(exception);
        }
    }

    private List<CssParameter> fromCssParametersFloat(final List<CssParameterFloat> cssParametersFloat) {
        final List<CssParameter> cssParameters = new ArrayList<CssParameter>();
        for (final CssParameterFloat cssParameterFloat : cssParametersFloat) {
            final CssParameter cssParameter = new CssParameter();
            cssParameter.setDescription(cssParameterFloat.getDescription());
            cssParameter.setName(cssParameterFloat.getName());
            cssParameter.setType(cssParameterFloat.getType());
            cssParameter.setSelector(cssParameterFloat.getSelector());
            cssParameters.add(cssParameter);
        }
        return cssParameters;
    }

    private InputStream getInputStreamInternal(final String path) {
        try {
            return new FileInputStream(path);
        } catch (final FileNotFoundException exception) {
            throw new FileSystemException(exception);
        }
    }

    @Override
    public BufferedImage getResource(final Resource resource) {
        if (resource != null) {
            return readBufferedImage(getResourcePath(resource));
        } else {
            return null;
        }
    }

    private BufferedImage readBufferedImage(final String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException exception) {
            return null;
        }
    }

    @Override
    public void setResourceStream(final Resource resource, final InputStream data) {
        writeInputStreamToFile(data, getResourcePath(resource));
    }

    @Override
    public void setResource(final Resource resource, final BufferedImage data) {
        if (resource != null && data != null) {
            writeBufferedImage(data, resource.getExtension(), getResourcePath(resource));
        }
    }

    @Override
    public String getResourcePath(final Resource resource) {
        if (resource == null) {
            return "";
        }

        final StringBuilder path = new StringBuilder(200);
        final ResourceType type = resource.getResourceType();

        if (type == ResourceType.VIDEO) {
            path.append(siteResourcesVideoPath);
        } else if (type == ResourceType.VIDEO_FLV) {
            path.append(siteResourcesFlvVideoPath);
        } else {
            path.append(siteResourcesPath);
        }

        path.append("/");
        path.append(getResourceName(resource));
        return path.toString();
    }

    @Override
    public String getResourceName(final Resource resource) {
        final StringBuilder path = new StringBuilder(200);
        path.append("site");
        path.append(resource.getSiteId());
        path.append("/");
        path.append(resourceDirectoryByTypes.get(resource.getResourceType()));
        path.append("_id_");
        path.append(resource.getResourceId());
        final ResourceSize resourceSize = resource.getResourceSize();
        if (resourceSize != null) {
            path.append("_width_");
            path.append(resourceSize.getWidth());
            path.append("_height_");
            path.append(resourceSize.getHeight());
        }
        if (resource instanceof FlvVideo) {
            int quality = ((FlvVideo) resource).getQuality();
            path.append("_quality_");
            path.append(quality);
        }
        path.append(".");
        path.append(resource.getExtension());
        return path.toString();
    }

    @Override
    public boolean isResourceExist(final Resource resource) {
        return resource != null && new File(getResourcePath(resource)).exists();
    }

    private void writeInputStreamToFile(final InputStream data, final String path) {
        FileOutputStream out = null;
        try {
            out = createFileOutputStream(path);
            IOUtil.copyStream(data, out);
            data.close();
        } catch (final IOException exception) {
            throw new FileSystemException(exception);
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (Exception exception) {
                Logger.getLogger(this.getClass().getName()).severe("Error while closing output stream.");
            }
        }
    }

    @Override
    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    public void removeResource(final Resource resource) {
        new File(getResourcePath(resource)).delete();
    }

    private void writeBufferedImage(
            final BufferedImage data, final String extension, final String path) {
        try {
            final FileOutputStream out = createFileOutputStream(path);
            ImageIO.write(data, extension, out);
            out.close();
        } catch (IOException exception) {
            throw new FileSystemException(exception);
        }
    }

    private FileOutputStream createFileOutputStream(final String path) {
        final File file = new File(path);
        final File parentFile = file.getParentFile();
        if (!parentFile.mkdirs() && !parentFile.exists()) {
            throw new FileSystemException("Can't create path " + path);
        }
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException exception) {
            throw new FileSystemException(exception);
        }
    }

    @Override
    public String getLoginPageDefaultHtml() {
        try {
            return IOUtil.read(loginPageDefaultHtmlFile);
        } catch (IOException exception) {
            throw new FileSystemException(exception);
        }
    }

    @Override
    public String getLoginAdminPageDefaultHtml() {
        try {
            return IOUtil.read(loginAdminPageDefaultHtmlFile);
        } catch (IOException exception) {
            throw new FileSystemException(exception);
        }
    }

    @Override
    public String getApplicationVersion() {
        return applicationVersion;
    }

    @Override
    public String getTemplateResource(final String templateDirectory, final String resourcePath) {
        try {
            return IOUtil.read(pageTemplatesPath + "/" + templateDirectory + "/" + resourcePath);
        } catch (IOException exception) {
            throw new FileSystemException(exception);
        }
    }

    @Override
    public List<Template> getTemplates() {
        try {
            final List<Template> pageTemplates = new ArrayList<Template>();
            final List<File> searchDescriptors = IOUtil.searchFiles(descriptorPattern, pageTemplatesPath);
            final JAXBContext context = JAXBContext.newInstance(Template.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            for (File descriptor : searchDescriptors) {
                final Template template = (Template) unmarshaller.unmarshal(descriptor);
                prepareTemplate(descriptor, template);
                pageTemplates.add(template);
            }
            return pageTemplates;
        } catch (JAXBException exception) {
            throw new FileSystemException(exception);
        }
    }

    @Override
    public Template getTemplateByDirectory(final String directory) {
        try {
            final JAXBContext context = JAXBContext.newInstance(Template.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            final File descriptor = new File(pageTemplatesPath + "/" + directory + "/descriptor.xml");
            final Template template = (Template) unmarshaller.unmarshal(descriptor);
            if (template != null) {
                prepareTemplate(descriptor, template);
            }
            return template;
        } catch (JAXBException exception) {
            throw new FileSystemException(exception);
        }
    }

    private void prepareTemplate(final File descriptor, final Template template) {
        template.setDirectory(descriptor.getParentFile().getName());
        for (final Theme theme : template.getThemes()) {
            theme.setTemplate(template);
        }
        for (final Layout layout : template.getLayouts()) {
            layout.setTemplate(template);
            for (final LayoutPattern pattern : layout.getPatterns()) {
                pattern.setLayout(layout);
            }
        }
    }

    @Override
    public InputStream getResourceStream(final Resource resource) {
        return getInputStreamInternal(getResourcePath(resource));
    }

    @Override
    public void inTransaction(final FileSystemTransaction transaction) {
        final FileSystemContextReal context = new FileSystemContextReal(this);
        try {
            transaction.execute(context);
        } finally {
            context.removeIfNotStay();
        }
    }

    @Override
    public void setPageResources(final String name, final String content) {
        try {
            writeInputStreamToFile(new ByteArrayInputStream(content.getBytes("utf8")), name);
        } catch (final UnsupportedEncodingException exception) {
            throw new FileSystemException(exception);
        }
    }

    @Override
    public String getLayoutThumbnailPath(final Layout layout) {
        return "/site/templates/" + layout.getTemplate().getDirectory() + "/" + layout.getThumbnailFile();
    }

    @Override
    public String getThemeImagePath(final Theme theme) {
        return "/site/templates/" + theme.getTemplate().getDirectory() + "/" + theme.getImageFile();
    }

    @Override
    public String getThemeImageThumbnailPath(final Theme theme) {
        return "/site/templates/" + theme.getTemplate().getDirectory() + "/" + theme.getThumbnailFile();
    }

    @Override
    public String getThemeColorTilePath(final Theme theme) {
        return "/site/templates/" + theme.getTemplate().getDirectory() + "/"
                + (theme.getColorTileFile() != null ? theme.getColorTileFile() : theme.getThumbnailFile());
    }

    @Override
    public Set<String> getSitesResourcesUrl() {
        try {
            final JAXBContext context = JAXBContext.newInstance(SitesResourcesUrl.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();
            final SitesResourcesUrl sitesResourcesUrl =
                    (SitesResourcesUrl) unmarshaller.unmarshal(new File(sitesResourcesUrlFile));
            return sitesResourcesUrl.getRecords();
        } catch (JAXBException exception) {
            throw new FileSystemException(exception);
        }
    }

    public List<String> getSpecies() {
        try {
            return IOUtil.readByLine(speciesFile);
        } catch (IOException exception) {
            throw new FileSystemException(exception);
        }
    }


    public List<String> getGenus() {
        try {
            return IOUtil.readByLine(genusFile);
        } catch (IOException exception) {
            throw new FileSystemException(exception);
        }
    }

    public List<String> getFamily() {
        try {
            return IOUtil.readByLine(familyFile);
        } catch (IOException exception) {
            throw new FileSystemException(exception);
        }
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    protected File createTemp() {
        final String path = siteResourcesPath;
        do {
            final String fileName = (path + "/temp" + Math.random());
            Logger.getLogger(this.getClass().getName()).info("Creating temporary file. Its name:\n" + fileName);
            File temp = new File(fileName);
            temp.getParentFile().mkdirs();
            if (!temp.exists()) {
                return temp;
            }
        } while (true);
    }

    private final String siteResourcesPath;
    private final String siteResourcesVideoPath;
    private final String siteResourcesFlvVideoPath;
    private final String applicationVersion;
    private final String speciesFile;
    private final String genusFile;
    private final String familyFile;
    private final String cssParametersFile;
    private final String pageTemplatesPath;
    private final String sitesResourcesUrlFile;
    private final String loginPageDefaultHtmlFile;
    private final String loginAdminPageDefaultHtmlFile;
    private final Map<ResourceType, String> resourceDirectoryByTypes;
    private final Pattern descriptorPattern = Pattern.compile("^descriptor.xml");

}
