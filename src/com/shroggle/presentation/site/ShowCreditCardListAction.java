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
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.payment.UserCreditCardsInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByLoginUser;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.List;
import java.util.Locale;

/**
 * @author Balakirev Anatoliy
 */
@UrlBinding(value = "/account/showCreditCardList.action")
public class ShowCreditCardListAction extends Action
        implements LoginedUserInfo, UserCreditCardsInfo {

    @SynchronizeByLoginUser
    @DefaultHandler
    public Resolution execute() {
        user = new UsersManager().getLoginedUser();
        if (user == null) {
            return resolutionCreator.loginInUser(this);
        }
        creditCards = user.getCreditCards();
        return resolutionCreator.forwardToUrl("/account/creditCardList.jsp");
    }

    public International getInternatinal() {
        return intenational;
    }

    public User getLoginUser() {
        return user;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    private User user;
    private final International intenational = ServiceLocator.getInternationStorage().get("creditCardList", Locale.US);
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private List<CreditCard> creditCards = null;
}