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
import com.shroggle.exception.IncorrectEmailException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByAllEntity;
import com.shroggle.logic.user.EmailChecker;
import com.shroggle.logic.site.SiteManager;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.WebContext;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Balakirev Anatoliy
 */

// todo. Add tests. Tolik
@RemoteProxy
public class CreateIncomeSettingsService extends AbstractService {


    @RemoteMethod
    @SynchronizeByAllEntity(
            entityClass = WidgetItem.class)
    public String execute(final Integer siteId) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();
        final Site site = persistance.getSite(siteId);
        final User user = userManager.getUser();
        String paypalEmail = user.getEmail();
        if (site != null) {
            IncomeSettings incomeSettings = new SiteManager(site).getOrCreateIncomeSettings();
            if (incomeSettings.getPaypalAddress() != null) {
                paypalEmail = incomeSettings.getPaypalAddress();
            }
            final WebContext webContext = getContext();
            webContext.getHttpServletRequest().setAttribute("paypalEmail", paypalEmail);
            webContext.getHttpServletRequest().setAttribute("siteId", siteId);
            return webContext.forwardToString("/site/incomeSettings.jsp");
        }
        return "";
    }


    @RemoteMethod
    @SynchronizeByAllEntity(
            entityClass = Site.class)
    public void setReceivePayments(final Integer siteId, final String paypalAddress) throws IncorrectEmailException {
        new UsersManager().getLogined();
        final EmailChecker emailChecker = new EmailChecker();
        try {
            emailChecker.execute(paypalAddress);
        } catch (IllegalArgumentException exception) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Can't get mail address validate service!", exception);
        }
        final Site site = persistance.getSite(siteId);
        final IncomeSettings incomeSettings = new SiteManager(site).getOrCreateIncomeSettings();
        persistanceTransaction.execute(new Runnable() {
            public void run() {
                incomeSettings.setPaypalAddress(paypalAddress);
            }
        });
    }

    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
}