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
package com.shroggle.logic.accessibility;

import com.shroggle.TestUtil;
import com.shroggle.entity.*;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersGroupManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.Arrays;

final class AccessibleForRenderManagerHelper {

    public AccessibleForRenderManagerHelper(
            AccessForRender widgetAccess,
            SiteAccessLevel createUserWithAccess, boolean widgetVisitors,
            Integer... visitorGroupIds) {
        this.widgetAccess = widgetAccess;
        this.createUserWithAccess = createUserWithAccess;
        this.widgetVisitors = widgetVisitors;
        this.visitorGroupIds = visitorGroupIds;
    }

    public void setUseCreatedGroup(boolean useCreatedGroup) {
        this.useCreatedGroup = useCreatedGroup;
    }

    public void setCreateGroup(boolean createGroup) {
        this.createGroup = createGroup;
    }

    public void setVisitorGroupIds(final Integer... visitorGroupIds) {
        this.visitorGroupIds = visitorGroupIds;
    }

    public void setWidgetAdministrators(boolean widgetAdministrators) {
        this.widgetAdministrators = widgetAdministrators;
    }

    public void setCreateSecondUser(SiteAccessLevel createSecondUser) {
        this.createSecondUser = createSecondUser;
    }

    public void setLoginCreatedUser(boolean loginCreatedUser) {
        this.loginCreatedUser = loginCreatedUser;
    }

    public void setPageAccess(final AccessForRender pageAccess) {
        this.pageAccess = pageAccess;
    }

    public void setPageAdministrators(boolean pageAdministrators) {
        this.pageAdministrators = pageAdministrators;
    }

    public void setSiteAdministrators(boolean siteAdministrators) {
        this.siteAdministrators = siteAdministrators;
    }

    public void setSiteAccess(AccessForRender siteAccess) {
        this.siteAccess = siteAccess;
    }

    public void setManagerFor(Class<? extends AccessibleForRender> managerFor) {
        this.managerFor = managerFor;
    }

    public void setItemAccess(AccessForRender itemAccess) {
        this.itemAccess = itemAccess;
    }

    public void setItemAdministrators(boolean itemAdministrators) {
        this.itemAdministrators = itemAdministrators;
    }

    public void setCreateItem(boolean createItem) {
        this.createItem = createItem;
    }

    public AccessibleForRenderManager create() {
        final Persistance persistance = ServiceLocator.getPersistance();

        final Site site = new Site();
        site.getAccessibleSettings().setAccess(siteAccess);
        site.getAccessibleSettings().setAdministrators(siteAdministrators);
        persistance.putSite(site);

        Group group = null;
        if (createUserWithAccess != null) {
            final User user;
            if (loginCreatedUser) {
                user = TestUtil.createUserAndLogin();
            } else {
                user = TestUtil.createUser();
            }

            if (createUserWithAccess == SiteAccessLevel.ADMINISTRATOR) {
                TestUtil.createUserOnSiteRightActiveAdmin(user, site);
            } else {
                TestUtil.createUserOnSiteRightActive(user, site, createUserWithAccess);
            }

            if (createGroup) {
                group = TestUtil.createGroup("name1", site);
                new UsersGroupManager(user).addAccessToGroup(group);
            }
        }

        if (createSecondUser != null) {
            final User user = TestUtil.createUserAndLogin();
            TestUtil.createUserOnSiteRightActiveAdmin(user, site);
        }

        final Page page = TestUtil.createPage(site);
        final PageManager pageVersion = new PageManager(page);
        pageVersion.getAccessibleSettings().setAccess(pageAccess);
        pageVersion.getAccessibleSettings().setAdministrators(pageAdministrators);

        final WidgetItem widgetItem = TestUtil.createWidgetItem();
        final WidgetManager widgetManager = new WidgetManager(widgetItem);
        widgetManager.setAccessibleSettings(null);
        if (widgetAccess != null) {
            final AccessibleSettings accessibleSettings = new AccessibleSettings();
            accessibleSettings.setAccess(widgetAccess);
            accessibleSettings.setAdministrators(widgetAdministrators);
            accessibleSettings.setVisitors(widgetVisitors);
            accessibleSettings.getVisitorsGroups().addAll(Arrays.asList(visitorGroupIds));
            if (group != null && useCreatedGroup) {
                accessibleSettings.getVisitorsGroups().add(group.getGroupId());
            }
            persistance.putAccessibleSettings(accessibleSettings);
            widgetManager.setAccessibleSettings(accessibleSettings);
        }

        persistance.putWidget(widgetItem);
        pageVersion.addWidget(widgetItem);

        if (createItem) {
            final DraftText draftText = new DraftText();
            final ItemManager itemManager = new ItemManager(draftText);
            if (itemAccess != null) {
                itemManager.setAccessibleSettings(new AccessibleSettings());
                itemManager.getAccessibleSettings(SiteShowOption.getDraftOption()).setAdministrators(itemAdministrators);
                itemManager.getAccessibleSettings(SiteShowOption.getDraftOption()).setAccess(itemAccess);
            }
            widgetItem.setDraftItem(draftText);
        }

        if (managerFor == WidgetManager.class) {
            return new AccessibleForRenderManager(new WidgetManager(widgetItem));
        } else if (managerFor == Site.class) {
            return new AccessibleForRenderManager(site);
        }
        return new AccessibleForRenderManager(pageVersion);
    }

    private AccessForRender siteAccess = AccessForRender.UNLIMITED;
    private AccessForRender pageAccess = AccessForRender.UNLIMITED;
    private AccessForRender itemAccess = AccessForRender.UNLIMITED;
    private boolean itemAdministrators = false;
    private boolean createItem = false;
    private final AccessForRender widgetAccess;
    private boolean widgetAdministrators = true;
    private final SiteAccessLevel createUserWithAccess;
    private SiteAccessLevel createSecondUser;
    private final boolean widgetVisitors;
    private boolean createGroup;
    private boolean pageAdministrators = false;
    private boolean useCreatedGroup;
    private Class<? extends AccessibleForRender> managerFor = WidgetManager.class;
    private boolean loginCreatedUser = true;
    private Integer[] visitorGroupIds = new Integer[0];
    private boolean siteAdministrators;

}
