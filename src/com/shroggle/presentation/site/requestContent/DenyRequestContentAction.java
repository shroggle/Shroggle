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
package com.shroggle.presentation.site.requestContent;

import com.shroggle.entity.*;
import com.shroggle.exception.UserException;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.item.ItemTypeManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/site/denyRequestContent.action")
public class DenyRequestContentAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution execute() {
        final SiteOnItem siteOnItemRight =
                persistance.getSiteOnItemRightBySiteIdItemIdAndType(targetSiteId, siteItemId, itemType);
        if (siteOnItemRight == null || acceptCode == null || !acceptCode.equals(siteOnItemRight.getAcceptCode())) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        persistanceTransaction.execute(new PersistanceTransactionContext<Void>() {

            public Void execute() {
                persistance.removeSiteOnItemRight(siteOnItemRight);
                return null;
            }

        });

        try {
            final UserManager userManager = new UserManager(siteOnItemRight.getRequestedUserId());
            final International international = ServiceLocator.getInternationStorage().get("acceptRequestContent", Locale.US);
            final Site targetSite = persistance.getSite(targetSiteId);
            
            final String emailBody = international.get("toRequesterBodyDenied",
                    new ItemTypeManager(siteOnItemRight.getItem().getItemType()).getName(),
                    ("'" + siteOnItemRight.getItem().getName() + "'"),
                    ("'" + targetSite.getTitle() + "'"),
                    new SiteManager(targetSite).getPublicUrl(),
                    ServiceLocator.getConfigStorage().get().getSupportEmail()
            );
            mailSender.send(new Mail(userManager.getUser().getEmail(), emailBody, international.get("toRequesterSubjectDenied")));
        } catch (final UserException exception) {
            // None...
        }
        return resolutionCreator.forwardToUrl("/site/denyRequestContent.jsp");
    }

    public void setTargetSiteId(int targetSiteId) {
        this.targetSiteId = targetSiteId;
    }

    public void setAcceptCode(String acceptCode) {
        this.acceptCode = acceptCode;
    }

    public void setSiteItemId(int siteItemId) {
        this.siteItemId = siteItemId;
    }

    public void setSiteItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public SiteOnItemRightType getShowOnItemRightType() {
        return siteOnItemRightType;
    }

    public void setShowOnItemRightType(final SiteOnItemRightType siteOnItemRightType) {
        this.siteOnItemRightType = siteOnItemRightType;
    }

    public ItemType getSiteItemType() {
        return itemType;
    }

    public User getLoginUser() {
        return null;
    }

    private int targetSiteId;
    private int siteItemId;
    private SiteOnItemRightType siteOnItemRightType;
    private ItemType itemType;
    private String acceptCode;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final MailSender mailSender = ServiceLocator.getMailSender();

}