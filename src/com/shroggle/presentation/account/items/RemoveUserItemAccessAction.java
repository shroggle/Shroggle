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

package com.shroggle.presentation.account.items;

import com.shroggle.entity.*;
import com.shroggle.exception.SiteOnItemRightNotFoundException;
import com.shroggle.exception.SiteOnItemRightOwnerException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.item.ItemTypeManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.ResolutionParameter;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalStorage;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.*;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/user/deleteItemAccess.action")
public class RemoveUserItemAccessAction extends Action {

    @DefaultHandler
    public Resolution execute() throws Exception {
        if (itemType == null) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(
                itemType.getItemClass(), SynchronizeMethod.WRITE, itemId, 1);
        return synchronize.execute(synchronizeRequest, new SynchronizeContext<Resolution>() {

            @Override
            public Resolution execute() throws Exception {
                return executeInSynchronize();
            }

        });
    }

    private Resolution executeInSynchronize() {
        final UserManager userManager;
        final SiteOnItem siteOnItemRight;
        try {
            userManager = new UsersManager().getLogined();
            final UserRightManager userRightManager = userManager.getRight();
            siteOnItemRight = userRightManager.getSiteOnItemRightForEdit(itemId, siteId, itemType);
        } catch (final UserException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        } catch (final SiteOnItemRightNotFoundException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        } catch (final SiteOnItemRightOwnerException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                persistance.removeSiteOnItemRight(siteOnItemRight);
            }

        });

        final International international = internationalStorage.get("manageItems", Locale.US);
        final Config config = configStorage.get();
        for (final UserOnSiteRight userOnSiteRight : siteOnItemRight.getSite().getUserOnSiteRights()) {
            if (userOnSiteRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR) {
                final User user = userOnSiteRight.getId().getUser();
                final Site site = userOnSiteRight.getId().getSite();
                mailSender.send(new Mail(user.getEmail(),
                        international.get("ITEM_DELETE_SHARE_TEXT",
                                StringUtil.getEmptyOrString(user.getFirstName()),
                                StringUtil.getEmptyOrString(user.getLastName()),
                                new ItemTypeManager(itemType).getItemName(),
                                ("'" + siteOnItemRight.getItem().getName() + "'"),
                                new SiteManager(site).getPublicUrl(),
                                config.getApplicationUrl(),
                                config.getSupportEmail()),
                        international.get("ITEM_DELETE_SHARE_SUBJECT"))
                );
            }
        }

        return resolutionCreator.redirectToAction(
                ManageItemsAction.class,
                new ResolutionParameter("itemType", itemType),
                new ResolutionParameter("infoText", international.get(
                        "ITEM_DELETE_SHARE_ALERT", siteOnItemRight.getItem().getName(),
                        siteOnItemRight.getSite().getTitle())));
    }


    public void setItemId(final int itemId) {
        this.itemId = itemId;
    }

    public void setSiteId(final int siteId) {
        this.siteId = siteId;
    }

    public void setItemType(final ItemType itemType) {
        this.itemType = itemType;
    }

    private ItemType itemType;
    private int itemId;
    private int siteId;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Synchronize synchronize = ServiceLocator.getSynchronize();
    private final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final MailSender mailSender = ServiceLocator.getMailSender();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}