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

import com.shroggle.entity.*;
import com.shroggle.exception.TemplateNotFoundException;
import com.shroggle.logic.site.item.ItemCreator;
import com.shroggle.logic.site.item.ItemCreatorRequest;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageVersionNormalizerReal;
import com.shroggle.util.LoremIpsumGenerator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.filesystem.FileSystem;
import com.shroggle.util.filesystem.FileSystemException;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

/**
 * @author Artem Stasuk
 */
public class TemplateManager implements Comparable<TemplateManager> {

    public TemplateManager(final String directory) {
        try {
            this.template = fileSystem.getTemplateByDirectory(directory);
        } catch (FileSystemException exception) {
            throw new TemplateNotFoundException(exception);
        }
    }

    public TemplateManager(final Template template) {
        this.template = template;
    }

    public String getCorrectName() {
        return template.getName() == null ? template.getDirectory() : template.getName();
    }

    public List<ThemeLogic> getThemes() {
        final List<ThemeLogic> themeLogicList = new ArrayList<ThemeLogic>();
        final List<Theme> themes = new ArrayList<Theme>(template.getThemes());

        // We need some kind of sorting here to ensure that this method will return themes in some stable sequence.
        Collections.sort(themes, new ThemeComparator());

        for (final Theme theme : themes) {
            themeLogicList.add(new ThemeLogic(theme));
        }
        return themeLogicList;
    }

    private class ThemeComparator implements Comparator<Theme> {

        @Override
        public int compare(Theme o1, Theme o2) {
            return o1.getName().compareTo(o2.getName());
        }

    }

    public Template getTemplate() {
        return template;
    }

    public int getId() {
        return template.getDirectory().hashCode();
    }

    public Map<Theme, String> getThemeColorTileUrls() {
        final Map<Theme, String> themeColorTiles = new HashMap<Theme, String>();
        for (final Theme theme : template.getThemes()) {
            themeColorTiles.put(theme, fileSystem.getThemeColorTilePath(theme));
        }
        return themeColorTiles;
    }

    public int compareTo(final TemplateManager templateManager) {
        if (templateManager.template.getOrder() == null) {
            if (template.getOrder() == null) {
                return getCorrectName().compareToIgnoreCase(templateManager.getCorrectName());
            }
            return -1;
        }

        if (template.getOrder() == null) {
            return 1;
        }

        return template.getOrder().compareTo(templateManager.getTemplate().getOrder());
    }

    public TemplateCreatePageResponse createPage(final PageType type, final Site site, final String name) {
        final TemplateCreatePageResponse response = new TemplateCreatePageResponse();

        final LayoutPatternLogic pattern = getPatternByPageType(type);

        final DraftPageSettings draftPageSettings = new DraftPageSettings();
        draftPageSettings.setLayoutFile(pattern.getLayout().getFile());
        draftPageSettings.setName(name);
        draftPageSettings.setTitle(name);
        draftPageSettings.setChanged(true);
        persistance.putPageSettings(draftPageSettings);

        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        ServiceLocator.getPersistance().putAccessibleSettings(accessibleSettings);
        draftPageSettings.setAccessibleSettings(accessibleSettings);

        final Page page = new Page();
        site.addPage(page);
        page.setType(type);
        page.setPageSettings(draftPageSettings);
        draftPageSettings.setPage(page);
        persistance.putPage(page);

        final PageManager pageManager = new PageManager(page);
        response.setPageManager(pageManager);

        configurePageInternal(type, response, pattern, pageManager);

        if (site.getHtml() != null) {
            pageManager.setHtml(site.getHtml().getValue());
            new PageVersionNormalizerReal().execute(pageManager);
        }

        return response;
    }

    private void configurePageInternal(
            final PageType type, final TemplateCreatePageResponse response,
            final LayoutPatternLogic pattern, final PageManager pageManager) {
        new PageVersionNormalizerReal().execute(pageManager);

        for (final ItemType widgetType : type.getNeededItemTypes()) {
            final int widgetPosition = pattern.getPosition(widgetType);
            if (widgetPosition >= pageManager.getWidgets().size()) {
                continue;
            }

            final Widget widget = new WidgetItem();

            final WidgetComposit widgetComposit =
                    (WidgetComposit) pageManager.getWidgets().get(widgetPosition);
            widget.setPosition(widgetComposit.getChilds().size());
            widgetComposit.addChild(widget);
            persistance.putWidget(widget);
            pageManager.getDraftPageSettings().addWidget(widget);

            response.setCreatedWidget(widget);

            final DraftItem draftItem = ItemCreator.create(new ItemCreatorRequest(widgetType, widget.getSite()));

            if (draftItem.getItemType() == ItemType.TEXT) {
                ((DraftText) draftItem).setText(new LoremIpsumGenerator().getWords(50));
            }

            ((WidgetItem) widget).setDraftItem(draftItem);
        }
    }

    public TemplateCreatePageResponse configurePage(
            final PageType type, final PageManager pageManager) {
        final TemplateCreatePageResponse response = new TemplateCreatePageResponse();

        final LayoutPatternLogic pattern = getPatternByPageType(type);
        response.setPageManager(pageManager);

        configurePageInternal(type, response, pattern, pageManager);

        return response;
    }

    private LayoutPatternLogic getPatternByPageType(final PageType type) {
        LayoutPattern defaultPattern = null;
        final List<Layout> layouts = template.getLayouts();
        for (final Layout layout : layouts) {
            for (final LayoutPattern pattern : layout.getPatterns()) {
                if (pattern.getType() == null) {
                    defaultPattern = pattern;
                }

                if (pattern.getType() == type) {
                    return new LayoutPatternLogicReal(pattern);
                }
            }
        }

        if (defaultPattern != null) {
            return new LayoutPatternLogicReal(defaultPattern);
        }

        if (layouts.size() > 0) {
            return new LayoutPatternLogicEmpty(layouts.get(0));
        }

        throw new UnsupportedOperationException(
                "Can't find any layout for template " + template);
    }

    public Integer getBlueprintId() {
        return blueprintId;
    }

    public void setBlueprintId(Integer blueprintId) {
        this.blueprintId = blueprintId;
    }

    private final Template template;
    private Integer blueprintId;
    private final FileSystem fileSystem = ServiceLocator.getFileSystem();
    private final Persistance persistance = ServiceLocator.getPersistance();

}
