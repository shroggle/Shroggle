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

import com.shroggle.entity.CreditCard;
import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.visitor.VisitorManager;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.StartAction;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByAllEntity;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
@UrlBinding("/account/deleteAccount.action")
public class DeleteUserAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() {
        user = new UsersManager().getLoginedUser();
        if (user == null) {
            return resolutionCreator.loginInUser(this);
        }
        return resolutionCreator.forwardToUrl("/account/deleteAccount.jsp");
    }

    @SynchronizeByAllEntity(
            entityClass = User.class)
    public Resolution execute() {
        final User user = new UsersManager().getLoginedUser();
        if (user == null) {
            return resolutionCreator.loginInUser(this);
        }
        persistanceTransaction.execute(new PersistanceTransactionContext<Void>() {

            public Void execute() {
                final List<CreditCard> userCreditCards = user.getCreditCards();
                while (userCreditCards.size() != 0) {
                    persistance.removeCreditCard(userCreditCards.get(0));
                }

                final List<UserOnSiteRight> rights = user.getUserOnSiteRights();
                while (rights.size() != 0) {
                    persistance.removeUserOnSiteRight(rights.get(0));
                }

                new VisitorManager(user).removeVisitor();

                persistance.removeUser(user);

                //If we were logined as deleted user let's logout this user
                if (ServiceLocator.getContextStorage().get().getUserId() != null &&
                        user.getUserId() == ServiceLocator.getContextStorage().get().getUserId()) {
                    UsersManager.logout();
                }

                return null;
            }

        });
        return resolutionCreator.redirectToAction(StartAction.class);
    }

    public User getLoginUser() {
        return user;
    }

    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private User user;

}