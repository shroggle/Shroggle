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
import com.shroggle.exception.SiteItemNotFoundException;
import com.shroggle.exception.SiteOnItemRightExistException;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.item.ItemTypeManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.MD5;
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
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.Date;
import java.util.Locale;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class SendRequestContentService extends AbstractService {

    @SynchronizeByMethodParameter(
            method = SynchronizeMethod.WRITE,
            entityClass = Site.class)
    @RemoteMethod
    public void execute(
            final int targetSiteId, final int siteItemId,
            final ItemType itemType, final String note) {
        final UserManager userManager = new UsersManager().getLogined();

        final Site targetSite = userManager.getRight().getSiteRight().getSiteForEdit(targetSiteId).getSite();
        final DraftItem siteItem = persistance.getDraftItem(siteItemId);
        if (siteItem == null) {
            throw new SiteItemNotFoundException("Can't find site item " + siteItemId + " with type " + itemType);
        }

        if (siteItem.getSiteId() == targetSiteId) {
            throw new SiteOnItemRightExistException("");
        }

        final SiteOnItem siteOnItemRight = persistance.getSiteOnItemRightBySiteIdItemIdAndType(
                targetSiteId, siteItemId, itemType);
        if (siteOnItemRight != null) {
            throw new SiteOnItemRightExistException("");
        }

        final String acceptCode = MD5.crypt("requestContent" + Math.random());
        persistanceTransaction.execute(new PersistanceTransactionContext<Void>() {

            public Void execute() {
                final SiteOnItem siteOnItemRight = siteItem.createSiteOnItemRight(targetSite);
                siteOnItemRight.setRequestedUserId(userManager.getUserId());
                siteOnItemRight.setSendDate(new Date());
                siteOnItemRight.setAcceptCode(acceptCode);
                persistance.putSiteOnItem(siteOnItemRight);
                return null;
            }

        });

        final International international = internationalStorage.get("sendRequestContent", Locale.US);
        final Config config = configStorage.get();

        final String acceptUrl =
                "http://" + config.getApplicationUrl() + "/site/acceptRequestContent.action?itemType="
                        + itemType + "&siteItemId=" + siteItemId + "&targetSiteId=" + targetSiteId
                        + "&acceptCode=" + acceptCode;
        final String denyUrl =
                "http://" + config.getApplicationUrl() + "/site/denyRequestContent.action?itemType="
                        + itemType + "&siteItemId=" + siteItemId + "&targetSiteId=" + targetSiteId
                        + "&acceptCode=" + acceptCode;

        String internationalNode = "";
        if (!StringUtil.isNullOrEmpty(note)) {
            internationalNode = international.get("withNote", userManager.getUser().getFirstName(),
                    userManager.getUser().getLastName(), note);
        }

        final Site site = persistance.getSite(siteItem.getSiteId());
        for (final User itemOwner : new SiteManager(site).getAdmins()) {
            mailSender.send(new Mail(
                    itemOwner.getEmail(),
                    international.get(
                            "requestContentMailText",
                            itemOwner.getFirstName(),
                            userManager.getUser().getFirstName(),
                            userManager.getUser().getLastName(),
                            new ItemTypeManager(itemType).getName(),
                            ("'" + siteItem.getName() + "'"),
                            targetSite.getTitle(),
                            new SiteManager(targetSite).getPublicUrl(),
                            internationalNode,
                            (acceptUrl + "&ownerId=" + itemOwner.getUserId()),
                            denyUrl,
                            userManager.getUser().getEmail(),
                            config.getSupportEmail()
                    ),
                    international.get("requestContentMailSubject")
            ));
        }
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final MailSender mailSender = ServiceLocator.getMailSender();
    private final InternationalStorage internationalStorage = ServiceLocator.getInternationStorage();

}
