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

import com.shroggle.entity.Html;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.ResolutionParameter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;


@UrlBinding("/editSiteTemplateHtml.action")
public class EditSiteTemplateHtmlAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() {
        if (check()) return resolutionCreator.loginInUser(this);

        return resolutionCreator.forwardToUrl("/WEB-INF/pages/editSiteTemplateHtml.jsp");
    }

    public Resolution execute() {
        if (check()) return resolutionCreator.loginInUser(this);

        value = StringUtil.cutIfNeed(value, 100000);
        if (StringUtil.isNullOrEmpty(value)) {
            addValidationError("nullOrEmptyValue");
            return resolutionCreator.forwardToUrl("/WEB-INF/pages/editSiteTemplateHtml.jsp");
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                Html html = site.getHtml();
                if (html == null) {
                    html = new Html();
                    html.setValue(value);
                    site.setHtml(html);
                    persistance.putHtml(html);
                }

                html.setValue(value);
            }

        });

        return resolutionCreator.redirectToAction(
                QuicklyCreatePagesAction.class, new ResolutionParameter("siteId", siteId));
    }

    private boolean check() {
        try {
            final UserManager userManager = new UsersManager().getLogined();
            loginedUser = userManager.getUser();
            site = userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();
        } catch (final UserException exception) {
            return true;
        } catch (final SiteNotFoundException exception) {
            return true;
        }
        return false;
    }

    public User getLoginUser() {
        return loginedUser;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private int siteId;
    private String value;
    private Site site;
    private User loginedUser;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}