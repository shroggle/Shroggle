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

import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.Site;
import com.shroggle.entity.Template;
import com.shroggle.logic.childSites.childSiteRegistration.ChildSiteRegistrationManager;
import com.shroggle.util.ServiceLocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Artem Stasuk
 */
public class TemplatesLogic {

    public TemplatesLogic(final Site site) {
        final List<TemplateManager> blueprintItems = new ArrayList<TemplateManager>();
        if (site.getChildSiteSettings() != null) {
            final ChildSiteSettings childSiteSettings = site.getChildSiteSettings();
            if (childSiteSettings != null && childSiteSettings.getChildSiteRegistration() != null) {
                final ChildSiteRegistrationManager manager = new ChildSiteRegistrationManager(childSiteSettings.getChildSiteRegistration());
                for (Site blueprint : manager.getUsedBlueprints()) {
                    final TemplateManager templateManager = new TemplateManager(
                            blueprint.getThemeId().getTemplateDirectory());
                    templateManager.setBlueprintId(blueprint.getSiteId());
                    blueprintItems.add(templateManager);
                }
            }
            Collections.sort(blueprintItems);
        }
        this.blueprintItems = Collections.unmodifiableList(blueprintItems);

        final List<TemplateManager> items = new ArrayList<TemplateManager>();
        if (site.getChildSiteSettings() == null || !site.getChildSiteSettings().isRequiredToUseSiteBlueprint()) {
            for (final Template template : ServiceLocator.getFileSystem().getTemplates()) {
                if ("optima".equals(template.getDirectory()))
                    continue;
                items.add(new TemplateManager(template));
            }
            Collections.sort(items);
        }
        this.items = Collections.unmodifiableList(items);
    }

    public List<TemplateManager> getItems() {
        return items;
    }

    public TemplateManager getTemplateById(final int id) {
        for (final TemplateManager templateManager : items) {
            if (templateManager.getId() == id) {
                return templateManager;
            }
        }

        for (final TemplateManager templateManager : blueprintItems) {
            if (templateManager.getId() == id) {
                return templateManager;
            }
        }
        return null;
    }

    public List<TemplateManager> getBlueprintItems() {
        return blueprintItems;
    }

    /**
     * Contain unmodificable template worker in sort order.
     *
     * @see TemplateManager
     */
    private final List<TemplateManager> items;
    private final List<TemplateManager> blueprintItems;

}
