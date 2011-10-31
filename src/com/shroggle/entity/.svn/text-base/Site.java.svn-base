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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//@DataTransferObject

@CachePolicy(maxElementsInMemory = 500)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity(name = "sites")
public class Site implements PaymentSettingsOwner, AccessibleForRender {

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "accessibleSettingsId")
    @ForeignKey(name = "sitesAccessibleSettingsId")
    private AccessibleSettings accessibleSettings = new AccessibleSettings();

    @OneToOne
    @JoinColumn(name = "incomeSettingsId")
    @ForeignKey(name = "sitesIncomeSettingsId")
    private IncomeSettings incomeSettings;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "childSiteSettingsId")
    @ForeignKey(name = "sitesChildSiteSettingsId")
    private ChildSiteSettings childSiteSettings;

    private Integer childSiteFilledFormId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentSettingsId")
    @ForeignKey(name = "sitesSitePaymentSettingsId")
    private SitePaymentSettings sitePaymentSettings = new SitePaymentSettings();

    @Column(length = 250, nullable = false)
    private String title;

    /**
     * Field is null for blueprint sites or child sites if his network use branded domain.
     */
    @Column(length = 250, unique = true)
    private String subDomain;

    @Column(length = 250, unique = true)
    private String customUrl;

    /**
     * @link http://jira.web-deva.com/browse/SW-5899
     */
    @Column(length = 250)
    private String brandedSubDomain;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "site")
    private List<KeywordsGroup> keywordsGroups = new ArrayList<KeywordsGroup>();

    @OneToMany(mappedBy = "site")
    private List<Page> pages = new ArrayList<Page>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date creationDate = new Date();

    @AttributeOverrides({
            @AttributeOverride(name = "templateDirectory", column = @Column(nullable = false)),
            @AttributeOverride(name = "themeCss", column = @Column(nullable = false))})
    @Embedded
    private ThemeId themeId = new ThemeId();

    private int defaultFormId;

    @OneToOne
    @JoinColumn(name = "loginPageId")
    @ForeignKey(name = "sitesLoginPageId")
    private Page loginPage;

    /**
     * It's special page. It use for show login when user view page with only admin access right.
     * <p/>
     * http://jira.web-deva.com/browse/SW-1338
     */
    @OneToOne
    @JoinColumn(name = "loginAdminPageId")
    @ForeignKey(name = "sitesLoginAdminPageId")
    private Page loginAdminPage;

    @Id
    private int siteId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "id.site")
    private List<UserOnSiteRight> userOnSiteRights = new ArrayList<UserOnSiteRight>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private SiteType type = SiteType.COMMON;

    /**
     * This field uses only for blueprint sites.
     *
     * @link http://jira.web-deva.com/browse/SW-1493
     */
    @Lob
    private String description;

    /**
     * For site with type COMMON this field must be null. To properly get this value for site based on blueprint use:
     * new SiteManager(site).isAddPagesRestrictedByBlueprint();
     */
    @Enumerated(EnumType.STRING)
    private SiteBlueprintRightType blueprintRightType = null;

    @OneToMany(mappedBy = "blueprintParent")
    private List<Site> blueprintChilds = new ArrayList<Site>();

    /**
     * If this field not null, this site used blueprint site as template
     */
    @ManyToOne
    @ForeignKey(name = "sitesBlueprintParentId")
    @JoinColumn(name = "blueprintParentId")
    private Site blueprintParent;

    @CollectionOfElements
    private List<Integer> childSiteRegistrationsId = new ArrayList<Integer>();

    @OneToOne
    @JoinColumn(name = "menuId")
    @ForeignKey(name = "sitesMenuId")
    private DraftMenu menu;

    @Embedded
    private SEOSettings seoSettings = new SEOSettings();

    @OneToMany(mappedBy = "owner")
    private List<Group> ownGroups = new ArrayList<Group>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iconId")
    @ForeignKey(name = "sitesIconsId")
    private Icon icon;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "htmlId")
    private Html html;

    /*------------------------------------------------SW-6138, SW-6140------------------------------------------------*/
    @Embedded
    private PublicBlueprintsSettings publicBlueprintsSettings = new PublicBlueprintsSettings();
    /*------------------------------------------------SW-6138, SW-6140------------------------------------------------*/


    /*----------------------------------------------------Methods-----------------------------------------------------*/

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public int getDefaultFormId() {
        return defaultFormId;
    }

    public void setDefaultFormId(int defaultFormId) {
        this.defaultFormId = defaultFormId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    @Override
    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int id) {
        this.siteId = id;
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public List<Page> getPages() {
        return Collections.unmodifiableList(pages);
    }

    public void addPage(final Page page) {
        page.setSite(this);
        pages.add(page);
    }

    public void removePage(final Page page) {
        pages.remove(page);
    }

    public List<KeywordsGroup> getKeywordsGroups() {
        return Collections.unmodifiableList(keywordsGroups);
    }

    public void addKeywordsGroup(KeywordsGroup keywordsGroup) {
        keywordsGroup.setSite(this);
        keywordsGroups.add(keywordsGroup);
    }

    public void removeKeywordsGroup(KeywordsGroup keywordsGroup) {
        keywordsGroups.remove(keywordsGroup);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public ThemeId getThemeId() {
        return themeId;
    }

    public void setThemeId(final ThemeId themeId) {
        this.themeId = themeId;
    }

    /**
     * replace by persistance.getUserOnSiteRights(). Tolik
     *
     * @return - list
     */
    public List<UserOnSiteRight> getUserOnSiteRights() {
        return userOnSiteRights;
    }

    public void addUserOnSiteRight(final UserOnSiteRight userOnSiteRight) {
        userOnSiteRight.getId().setSite(this);
        userOnSiteRights.add(userOnSiteRight);
    }

    public void removeUserOnSiteRight(final UserOnSiteRight userOnSiteRight) {
        userOnSiteRights.remove(userOnSiteRight);
    }

    public Page getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(final Page loginPage) {
        this.loginPage = loginPage;
    }

    public Page getLoginAdminPage() {
        return loginAdminPage;
    }

    public void setLoginAdminPage(final Page loginAdminPage) {
        this.loginAdminPage = loginAdminPage;
    }

    /**
     * Use SiteLogic.createIncomeSettings
     *
     * @return - t
     * @see com.shroggle.logic.site.SiteManager
     */
    public IncomeSettings getIncomeSettings() {
        return incomeSettings;
    }

    public void setIncomeSettings(IncomeSettings incomeSettings) {
        this.incomeSettings = incomeSettings;
    }

    public ChildSiteSettings getChildSiteSettings() {
        return childSiteSettings;
    }

    public void setChildSiteSettings(final ChildSiteSettings childSiteSettings) {
        this.childSiteSettings = childSiteSettings;
    }

    public SiteType getType() {
        return type;
    }

    public void setType(final SiteType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public SiteBlueprintRightType getBlueprintRightType() {
        return blueprintRightType;
    }

    public void setBlueprintRightType(final SiteBlueprintRightType blueprintRightType) {
        this.blueprintRightType = blueprintRightType;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Site getBlueprintParent() {
        return blueprintParent;
    }

    /**
     * This method can use only addBlueprintSite method.
     *
     * @param blueprintParent - parent
     */
    void setBlueprintParent(final Site blueprintParent) {
        this.blueprintParent = blueprintParent;
    }

    public List<Site> getBlueprintChilds() {
        return Collections.unmodifiableList(blueprintChilds);
    }

    public void addBlueprintChild(final Site blueprintChild) {
        blueprintChild.setBlueprintParent(this);
        blueprintChilds.add(blueprintChild);
    }

    public List<Integer> getChildSiteRegistrationsId() {
        return childSiteRegistrationsId;
    }

    public void setChildSiteRegistrationsId(List<Integer> childSiteRegistrationsId) {
        this.childSiteRegistrationsId = childSiteRegistrationsId;
    }

    public void addChildSiteRegistrationId(int childSiteRegistrationId) {
        if (!childSiteRegistrationsId.contains(childSiteRegistrationId)) {
            childSiteRegistrationsId.add(childSiteRegistrationId);
        }
    }

    public void removeBlueprintChild(final Site site) {
        blueprintChilds.remove(site);
        site.blueprintParent = null;
    }

    @Override
    public SitePaymentSettings getSitePaymentSettings() {
        return sitePaymentSettings;
    }

    @Override
    public void setSitePaymentSettings(SitePaymentSettings sitePaymentSettings) {
        this.sitePaymentSettings = sitePaymentSettings;
    }

    public DraftMenu getMenu() {
        return menu;
    }

    public void setMenu(DraftMenu menu) {
        this.menu = menu;
    }

    @Override
    public int getId() {
        return siteId;
    }

    @Override
    public AccessibleForRender getAccessibleParent() {
        return null;
    }

    @Override
    public AccessibleElementType getAccessibleElementType() {
        return AccessibleElementType.SITE;
    }

    public SEOSettings getSeoSettings() {
        return seoSettings;
    }

    public void setSeoSettings(SEOSettings seoSettings) {
        this.seoSettings = seoSettings;
    }

    public List<Group> getOwnGroups() {
        return Collections.unmodifiableList(ownGroups);
    }

    @Override
    public List<Group> getAvailableGroups() {
        return getOwnGroups();
    }

    public void setOwnGroups(List<Group> ownGroups) {
        this.ownGroups = ownGroups;
    }

    public void addGroup(Group group) {
        this.ownGroups.add(group);
    }

    public void removeGroup(Group group) {
        this.ownGroups.remove(group);
    }

    @Override
    public AccessibleSettings getAccessibleSettings() {
        return accessibleSettings;
    }

    @Override
    public void setAccessibleSettings(AccessibleSettings accessibleSettings) {
        this.accessibleSettings = accessibleSettings;
    }

    public Integer getChildSiteFilledFormId() {
        return childSiteFilledFormId;
    }

    public void setChildSiteFilledFormId(Integer childSiteFilledFormId) {
        this.childSiteFilledFormId = childSiteFilledFormId;
    }

    public String getBrandedSubDomain() {
        return brandedSubDomain;
    }

    public void setBrandedSubDomain(final String brandedSubDomain) {
        this.brandedSubDomain = brandedSubDomain;
    }

    public Html getHtml() {
        return html;
    }

    public void setHtml(Html html) {
        this.html = html;
    }

    public Date getPublished() {
        return getPublicBlueprintsSettings().getPublished();
    }

    public void setPublished(Date published) {
        getPublicBlueprintsSettings().setPublished(published);
    }

    public Date getActivated() {
        return getPublicBlueprintsSettings().getActivated();
    }

    public void setActivated(Date activated) {
        getPublicBlueprintsSettings().setActivated(activated);
    }

    public PublicBlueprintsSettings getPublicBlueprintsSettings() {
        return publicBlueprintsSettings;
    }

    public void setPublicBlueprintsSettings(PublicBlueprintsSettings publicBlueprintsSettings) {
        this.publicBlueprintsSettings = publicBlueprintsSettings;
    }

}
