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
package com.shroggle.entity;

import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public interface PageSettings {

    List<Widget> getWidgets();

    void setWidgets(List<Widget> widgets);

    Page getPage();

    Site getSite();

    void setPage(Page page);

    int getPageSettingsId();

    void setPageSettingsId(int pageSettingsId);

    String getName();

    void setName(String name);

    String getTitle();

    void setTitle(String title);

    String getRawUrl();

    String getUrl();

    void setUrl(String url);

    String getOwnDomainName();

    void setOwnDomainName(String ownDomainName);

    String getHtml();

    void setHtml(String html);

    String getCss();

    void setCss(String css);

    String getKeywords();

    void setKeywords(String keywords);

    String getLayoutFile();

    void setLayoutFile(String layoutFile);

    PageSEOSettings getSeoSettings();

    void setSeoSettings(PageSEOSettings seoSettings);

    Date getCreationDate();

    void setCreationDate(Date creationDate);

    ThemeId getThemeId();

    void setThemeId(ThemeId themeId);

    boolean isBlueprintRequired();

    void setBlueprintRequired(boolean blueprintRequired);

    boolean isBlueprintNotEditable();

    void setBlueprintNotEditable(boolean blueprintNotEditable);

    boolean isBlueprintLocked();

    void setBlueprintLocked(boolean blueprintLocked);

    Border getBorder();

    Background getBackground();

    void setBorder(Border borderBackground);

    void setBackground(Background background);

    List<Integer> getKeywordsGroupsId();

    void setKeywordsGroupsId(List<Integer> keywordsGroupsId);

    void addWidget(Widget widget);

    void removeWidget(Widget widget);

    void removeKeywordsGroup(KeywordsGroup keywordsGroup);

    void addKeywordsGroup(KeywordsGroup keywordsGroup);

    AccessibleSettings getAccessibleSettings();

    void setAccessibleSettings(AccessibleSettings accessibleSettings);

    int getSiteId();
}
