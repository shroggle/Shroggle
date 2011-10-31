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

package com.shroggle.presentation.site;

import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.site.PublishingInfoResponse;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageSelectTextCreator;
import com.shroggle.logic.site.page.PageTreeTextCreator;
import com.shroggle.logic.site.page.PagesWithoutSystem;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.Context;
import com.shroggle.util.config.Config;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;


/**
 * Show one page for edit his widgets, make it default for this site or delete
 *
 * @author Stasuk Artem
 */
@UrlBinding("/site/siteEditPage.action")
public class SiteEditPageAction extends Action implements LoginedUserInfo {

    @SynchronizeByClassProperty(
            entityClass = Site.class, entityIdFieldPath = "siteId")
    @DefaultHandler
    public Resolution execute() throws Exception {
        final UserManager userManager;
        try {
            userManager = new UsersManager().getLogined();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        }
        user = userManager.getUser();
        userId = user.getUserId();

        final SiteManager siteManager;
        try {
            siteManager = userManager.getRight().getSiteRight().getSiteForEdit(siteId);
        } catch (final SiteNotFoundException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }
        site = siteManager.getSite();
        context.setSiteId(siteId);

        selectHtml = new PageSelectTextCreator().execute(site.getSiteId(), SiteShowOption.getDraftOption());
        treeHtml = new PageTreeTextCreator().execute(site.getSiteId(), SiteShowOption.getDraftOption());

        keywordCount = site.getKeywordsGroups().size();
        siteType = site.getType();
        siteName = siteManager.getName();

        int accountWithAdminRightsCount = 0;
        for (final UserOnSiteRight userOnUserRight : user.getUserOnSiteRights()) {
            if (userOnUserRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR) {
                accountWithAdminRightsCount = accountWithAdminRightsCount + 1;

                if (accountWithAdminRightsCount > 1) {
                    hasAdminRightsOnManyUsers = true;
                    break;
                }
            }
        }
        admin = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), site.getSiteId()).getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR;
        siteActive = site.getSitePaymentSettings().getSiteStatus() == SiteStatus.ACTIVE;
        mustBePaidBeforePublishing = config.getBillingInfoProperties().isMustBePaidBeforePublishing();
        canBePublished = new SiteManager(site).checkChildSiteStartDate();
        currentlyViewedPageId = ServiceLocator.getSessionStorage().getCurrentlyViewedPageId(
                getContext().getRequest().getSession(), site.getSiteId());

        return resolutionCreator.forwardToUrl("/site/siteEditPage/siteEditPage.jsp");
    }

    public int getUserId() {
        return userId;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public int getFirstNodeId() {
        return firstNodeId;
    }

    public void setFirstNodeId(int firstNodeId) {
        this.firstNodeId = firstNodeId;
    }

    public int getKeywordCount() {
        return keywordCount;
    }

    public User getLoginUser() {
        return user;
    }

    public boolean isHasAdminRightsOnManyUsers() {
        return hasAdminRightsOnManyUsers;
    }

    public boolean isUserAdmin() {
        return admin;
    }

    public boolean isSiteActive() {
        return siteActive;
    }

    public boolean isMustBePaidBeforePublishing() {
        return mustBePaidBeforePublishing;
    }

    public SiteType getSiteType() {
        return siteType;
    }

    public PublishingInfoResponse getCanBePublished() {
        return canBePublished;
    }

    public String getSelectHtml() {
        return selectHtml;
    }

    public Site getSite() {
        return site;
    }

    public String getTreeHtml() {
        return treeHtml;
    }

    public Integer getCurrentlyViewedPageId() {
        return currentlyViewedPageId;
    }

    public String getPagesIds() {
        String ids = "";
        for (Page page : PagesWithoutSystem.get(site.getPages())) {
            ids += page.getPageId() + ";";
        }
        ids = ids.endsWith(";") ? ids.substring(0, ids.length() - 1) : ids;
        return ids;
    }

    private boolean mustBePaidBeforePublishing;
    private boolean admin = false;
    private boolean siteActive = false;
    private boolean hasAdminRightsOnManyUsers;
    private int keywordCount;
    private int siteId;
    private int userId;
    private Site site;
    private String siteName;
    private SiteType siteType;
    private int firstNodeId;
    private User user;
    private String selectHtml;
    private String treeHtml;
    private Integer currentlyViewedPageId;
    private PublishingInfoResponse canBePublished;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Context context = ServiceLocator.getContextStorage().get();
    private final Config config = ServiceLocator.getConfigStorage().get();

}