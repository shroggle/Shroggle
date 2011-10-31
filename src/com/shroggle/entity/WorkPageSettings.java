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

import com.shroggle.util.cache.CachePolicy;
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@CachePolicy(maxElementsInMemory = 2000)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity(name = "workPageSettings")
@Table(
        name = "workPageSettings",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"ownDomainName"})
        }
)
public class WorkPageSettings implements PageSettings {

    @Id
    private int pageSettingsId;

    @Column(length = 250)
    private String name;

    @Column(length = 250)
    private String title;

    @Column(length = 250)
    private String url;

    @Column(length = 250)
    private String ownDomainName;

    @Lob
    private String html;

    @Lob
    private String css;

    @Lob
    private String keywords;

    @Column(length = 250)
    private String layoutFile;

    @Embedded
    private PageSEOSettings seoSettings = new PageSEOSettings();

    @OneToOne
    @JoinColumn(name = "pageId", nullable = false)
    @ForeignKey(name = "workPageSettingsPageId")
    private Page page;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date creationDate = new Date();

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "accessibleSettingsId")
    @ForeignKey(name = "workPageSettingsAccessibleSettingsId")
    private AccessibleSettings accessibleSettings;

    /**
     * This fields set contain site template setting. For non DRAFT page versions type this
     * field not null and contain old site themeId. For DRAFT page version type this field
     * can have null or not null. For null value system uses site themeId in other cases
     * page version themeId. When user change for site themeId we must reset all themeId only in
     * DRAFT page versions.
     *
     * @link http://jira.web-deva.com/browse/SW-1520
     */
    @Embedded
    private ThemeId themeId = null;

    private boolean blueprintRequired;

    private boolean blueprintNotEditable;

    private boolean blueprintLocked;

    @RemoteProperty
    @OneToOne
    @ForeignKey(name = "workPageSettingsBorderBackgroundId")
    @JoinColumn(name = "borderBackgroundId")
    private Border borderBackground;

    @RemoteProperty
    @OneToOne
    @ForeignKey(name = "workPageSettingsBackgroundId")
    @JoinColumn(name = "backgroundId")
    private Background background;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workPageSettings")
    private List<Widget> widgets = new ArrayList<Widget>();

    @CollectionOfElements
    private List<Integer> keywordsGroupsId = new ArrayList<Integer>();

    /*----------------------------------------------------Methods-----------------------------------------------------*/

    public Page getPage() {
        return page;
    }

    public Site getSite() {
        return getPage().getSite();
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public int getPageSettingsId() {
        return pageSettingsId;
    }

    public void setPageSettingsId(int pageSettingsId) {
        this.pageSettingsId = pageSettingsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRawUrl(){
        return url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOwnDomainName() {
        return ownDomainName;
    }

    public void setOwnDomainName(String ownDomainName) {
        this.ownDomainName = ownDomainName;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLayoutFile() {
        return layoutFile;
    }

    public void setLayoutFile(String layoutFile) {
        this.layoutFile = layoutFile;
    }

    public PageSEOSettings getSeoSettings() {
        return seoSettings;
    }

    public void setSeoSettings(PageSEOSettings seoSettings) {
        this.seoSettings = seoSettings;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public AccessibleSettings getAccessibleSettings() {
        return accessibleSettings;
    }

    public void setAccessibleSettings(AccessibleSettings accessibleSettings) {
        this.accessibleSettings = accessibleSettings;
    }

    public ThemeId getThemeId() {
        return themeId;
    }

    public void setThemeId(ThemeId themeId) {
        this.themeId = themeId;
    }

    public boolean isBlueprintRequired() {
        return blueprintRequired;
    }

    public void setBlueprintRequired(boolean blueprintRequired) {
        this.blueprintRequired = blueprintRequired;
    }

    public boolean isBlueprintNotEditable() {
        return blueprintNotEditable;
    }

    public void setBlueprintNotEditable(boolean blueprintNotEditable) {
        this.blueprintNotEditable = blueprintNotEditable;
    }

    public boolean isBlueprintLocked() {
        return blueprintLocked;
    }

    public void setBlueprintLocked(boolean blueprintLocked) {
        this.blueprintLocked = blueprintLocked;
    }

    public Border getBorder() {
        return borderBackground;
    }

    public void setBorder(Border borderBackground) {
        this.borderBackground = borderBackground;
    }

    public List<Integer> getKeywordsGroupsId() {
        return keywordsGroupsId;
    }

    public void setKeywordsGroupsId(List<Integer> keywordsGroupsId) {
        this.keywordsGroupsId = keywordsGroupsId;
    }

    @Override
    public int getSiteId() {
        return page.getSite().getSiteId();
    }

    public List<Widget> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<Widget> widgets) {
        this.widgets = widgets;
    }

    public void addWidget(Widget widget) {
        widget.setDraftPageSettings(null);
        widget.setWorkPageSettings(this);
        widgets.add(widget);
    }

    public void removeWidget(Widget widget) {
        widgets.remove(widget);
    }

    public void removeKeywordsGroup(KeywordsGroup keywordsGroup) {
        keywordsGroupsId.remove(keywordsGroup.getKeywordsGroupId());
    }

    public void addKeywordsGroup(KeywordsGroup keywordsGroup) {
        keywordsGroupsId.add(keywordsGroup.getKeywordsGroupId());
    }
    
    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

}
