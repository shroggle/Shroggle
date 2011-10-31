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

import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.site.SiteCopierFromActivatedBlueprint;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.ResolutionParameter;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperties;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/site/setSiteBlueprint.action")
public class SetSiteBlueprintAction extends Action implements LoginedUserInfo {

    @SynchronizeByClassProperties({
            @SynchronizeByClassProperty(
                    entityClass = Site.class,
                    entityIdFieldPath = "siteId")})
    @DefaultHandler
    public Resolution show() {
        final UserManager userManager;
        try {
            userManager = new UsersManager().getLogined();
            userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        } catch (final SiteNotFoundException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        user = userManager.getUser();

        return resolutionCreator.forwardToUrl("/WEB-INF/pages/setSiteBlueprint.jsp");
    }

    public Resolution execute() {
        final UserManager userManager;
        final Site site;
        try {
            userManager = new UsersManager().getLogined();
            site = userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        } catch (final SiteNotFoundException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        final Site activatedBlueprint = persistance.getSite(activatedBlueprintId);
        if (activatedBlueprint == null) {
            return resolutionCreator.forwardToUrl("/WEB-INF/pages/setSiteBlueprint.jsp");
        }

        if (activatedBlueprint.getPublicBlueprintsSettings() == null
                || activatedBlueprint.getPublicBlueprintsSettings().getActivated() == null) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                siteCopierFromActivatedBlueprint.execute(activatedBlueprint, site, false);
            }

        });

        return resolutionCreator.redirectToAction(SiteEditPageAction.class, new ResolutionParameter("siteId", site.getSiteId()));
    }

    public void setSiteId(final int siteId) {
        this.siteId = siteId;
    }

    public int getSiteId() {
        return siteId;
    }

    public User getLoginUser() {
        return user;
    }

    public boolean isEditingMode() {
        return editingMode;
    }

    public void setEditingMode(boolean editingMode) {
        this.editingMode = editingMode;
    }

    public boolean isCreateChildSite() {
        return createChildSite;
    }

    public void setCreateChildSite(boolean createChildSite) {
        this.createChildSite = createChildSite;
    }

    public void setActivatedBlueprintId(int activatedBlueprintId) {
        this.activatedBlueprintId = activatedBlueprintId;
    }

    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SiteCopierFromActivatedBlueprint siteCopierFromActivatedBlueprint =
            ServiceLocator.getSiteCopierFromActivatedBlueprint();
    private boolean createChildSite;
    private boolean editingMode;
    private User user;
    private int siteId;
    private int activatedBlueprintId;

}