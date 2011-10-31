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
package com.shroggle.logic.site.page;

import com.shroggle.entity.*;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.item.ItemCopierUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.copier.CopierUtil;
import com.shroggle.util.persistance.Persistance;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Balakirev Anatoliy
 */
public class PageSettingsManager {

    public PageSettingsManager(PageSettings pageSettings) {
        this.pageSettings = pageSettings;
    }

    public Page getPage() {
        if (pageSettings == null) {
            return null;
        }
        return pageSettings.getPage();
    }

    public String getPublicUrl() {
        if (pageSettings == null) {
            return "";
        }
        return new SiteManager(pageSettings.getPage().getSite()).getPublicUrl() + "/" + pageSettings.getUrl();
    }

    public List<Widget> getWidgets() {
        if (pageSettings == null) {
            return Collections.emptyList();
        }
        return pageSettings.getWidgets();
    }

    public List<KeywordsGroup> getKeywordsGroups() {
        if (pageSettings == null) {
            return Collections.emptyList();
        }
        return persistance.getKeywordsGroups(pageSettings.getKeywordsGroupsId());
    }

    public String getHtml() {
        if (pageSettings == null) {
            return null;
        }
        return Doctype.addDoctypeIfNeeded(pageSettings.getHtml());
    }

    public String getLayoutFile() {
        if (pageSettings == null) {
            return "";
        }
        return pageSettings.getLayoutFile();
    }

    public ThemeId getThemeId() {
        if (pageSettings == null) {
            return null;
        }
        return pageSettings.getThemeId();
    }

    public String getCss() {
        if (pageSettings == null) {
            return null;
        }
        return pageSettings.getCss();
    }

    public String getName() {
        if (pageSettings == null) {
            return "";
        }
        return StringUtil.getEmptyOrString(pageSettings.getName());
    }

    public String getTitle() {
        if (pageSettings == null) {
            return "";
        }
        return StringUtil.getEmptyOrString(pageSettings.getTitle());
    }

    public String getUrl() {
        if (pageSettings instanceof WorkPageSettings) {
            return "/" + pageSettings.getUrl();
        } else if (pageSettings instanceof DraftPageSettings) {
            return "showPageVersion.action?pageId=" + pageSettings.getPage().getPageId() + "&siteShowOption=" + SiteShowOption.ON_USER_PAGES.toString();
        } else {
            return "";
        }
    }

    public String getRawUrl() {
        if (pageSettings == null) {
            return "";
        }
        return pageSettings.getUrl();
    }

    public Date getUpdated() {
        return getDraftPageSettings().getUpdated();
    }

    public Border getBorder() {
        if (pageSettings == null) {
            return null;
        }
        return pageSettings.getBorder();
    }

    public Background getBackground() {
        if (pageSettings == null) {
            return null;
        }
        return pageSettings.getBackground();
    }

    public PageSettings getPageSettings() {
        return pageSettings;
    }

    public WorkPageSettings getWorkPageSettings() {
        if (pageSettings == null) {
            return null;
        }
        return pageSettings instanceof WorkPageSettings ? (WorkPageSettings) pageSettings : persistance.getWorkPageSettings(pageSettings.getPageSettingsId());
    }

    public DraftPageSettings getDraftPageSettings() {
        if (pageSettings == null) {
            return null;
        }
        return pageSettings instanceof DraftPageSettings ? (DraftPageSettings) pageSettings : persistance.getDraftPageSettings(pageSettings.getPageSettingsId());
    }

    public boolean isWork() {
        return pageSettings instanceof WorkPageSettings;
    }

    public boolean isBlueprintLocked() {
        return pageSettings != null && pageSettings.isBlueprintLocked();
    }

    public boolean isBlueprintRequired() {
        return pageSettings != null && pageSettings.isBlueprintRequired();
    }

    public boolean isBlueprintNotEditable() {
        return pageSettings != null && pageSettings.isBlueprintNotEditable();
    }

    public AccessibleSettings getAccessibleSettings() {
        if (pageSettings == null) {
            return null;
        }
        return pageSettings.getAccessibleSettings();
    }

    public String getOwnDomainName() {
        if (pageSettings == null) {
            return "";
        }
        return StringUtil.getEmptyOrString(pageSettings.getOwnDomainName());
    }


    public String getKeywords() {
        if (pageSettings == null) {
            return "";
        }
        return StringUtil.getEmptyOrString(pageSettings.getKeywords());
    }

    public List<Integer> getKeywordsGroupsId() {
        if (pageSettings == null) {
            return Collections.emptyList();
        }
        return pageSettings.getKeywordsGroupsId();
    }

    public Date getCreationDate() {
        if (pageSettings == null) {
            return null;
        }
        return pageSettings.getCreationDate();
    }

    public int getPageSettingsId() {
        if (pageSettings == null) {
            return 0;
        }
        return pageSettings.getPageSettingsId();
    }

    public PageSEOSettings getSeoSettings() {
        if (pageSettings == null) {
            return null;
        }
        return pageSettings.getSeoSettings();
    }

    public void copyPageSettingsTo(final PageSettings copy) {
        copyPageSettingsTo(copy, null);
    }

    public void copyPageSettingsTo(final PageSettings copy, final Set<Integer> itemIds) {
        CopierUtil.copyProperties(pageSettings, copy,
                "PageSettingsId", "Page", //This items are not copied here. They should be setted before copying (or will be null by default). Tolik
                "CreationDate", "SeoSettings", "Border", "Background", "AccessibleSettings", "KeywordsGroupsId", "Widgets");
        copy.setCreationDate(new Date());
        copy.setSeoSettings(CopierUtil.copy(pageSettings.getSeoSettings()));
        copy.setBorder(ItemCopierUtil.copyBorder(pageSettings.getBorder()));
        copy.setBackground(ItemCopierUtil.copyBackground(pageSettings.getBackground()));
        copy.setAccessibleSettings(ItemCopierUtil.copyAccessibleSettings(pageSettings.getAccessibleSettings()));
        copy.setKeywordsGroupsId(ItemCopierUtil.copyKeywordsGroups(getKeywordsGroups()));

        persistance.putPageSettings(copy);

        WidgetsCopier.copyWidgets(pageSettings, copy, itemIds);
    }

    private final PageSettings pageSettings;
    private final Persistance persistance = ServiceLocator.getPersistance();
}
