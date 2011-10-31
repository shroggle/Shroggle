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
import com.shroggle.exception.AttemptToDeleteSiteWithoutRightException;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/site/deleteSite.action")
public class DeleteSiteAction extends Action {

    @SynchronizeByClassProperty(
            method = SynchronizeMethod.WRITE,
            entityIdFieldPath = "siteId",
            entityClass = Site.class)
    @DefaultHandler
    public Resolution execute() {
        try {
            final UserManager userManager = new UsersManager().getLogined();

            final Site site = userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();

            if (!userManager.getRight().getSiteRight().hasRightToDelete(site)) {
                throw new AttemptToDeleteSiteWithoutRightException("Attempt to delete site with Id=" + siteId +
                        " without rights to do it by user with Id=" + userManager.getUserId());
            }

            persistanceTransaction.execute(new Runnable() {

                @Override
                public void run() {
                    persistance.removeSite(site);
                }

            });
        } catch (final UserException exception) {
            // None
        } catch (final SiteNotFoundException exception) {
            // None
        }

        return resolutionCreator.redirectToAction(DashboardAction.class);
    }

    public void setSiteId(final int siteId) {
        this.siteId = siteId;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private int siteId;

}
