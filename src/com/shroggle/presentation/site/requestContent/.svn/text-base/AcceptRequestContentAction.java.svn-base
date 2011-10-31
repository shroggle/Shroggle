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
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.item.ItemTypeManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/site/acceptRequestContent.action")
public class AcceptRequestContentAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() {
        final Resolution resolution = init();
        if (resolution != null) {
            return resolution;
        }

        return showInternal(siteOnItemRight);
    }

    private Resolution showInternal(final SiteOnItem siteOnItemRight) {
        siteItemName = siteOnItemRight.getItem().getName();
        itemType = siteOnItemRight.getItem().getItemType();
        siteTitle = persistance.getSite(siteOnItemRight.getItem().getSiteId()).getTitle();
        final Site targetSite = persistance.getSite(targetSiteId);
        targetSiteTitle = targetSite.getTitle();
        targetSiteUrl = new SiteManager(targetSite).getPublicUrl();
        return resolutionCreator.forwardToUrl("/site/acceptRequestContent.jsp");
    }

    private Resolution init() {
        siteOnItemRight = persistance.getSiteOnItemRightBySiteIdItemIdAndType(targetSiteId, siteItemId, itemType);
        if (siteOnItemRight == null || acceptCode == null || !acceptCode.equals(siteOnItemRight.getAcceptCode())) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }
        if (siteOnItemRight.getAcceptDate() != null) {
            return resolutionCreator.forwardToUrl("/site/acceptRequestContentTwice.jsp");
        }

        try {
            requestedUser = new UserManager(siteOnItemRight.getRequestedUserId());
        } catch (final UserNotFoundException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        return null;
    }

    public Resolution execute() {
        final Resolution resolution = init();
        if (resolution != null) {
            return resolution;
        }

        persistanceTransaction.execute(new PersistanceTransactionContext<Void>() {

            public Void execute() {
                siteOnItemRight.setType(siteOnItemRightType);
                siteOnItemRight.setAcceptDate(new Date());
                return null;
            }

        });

        //If shared item is gallery then share it's form in read only mode also.
        // todo: this code work's but duplicates gallery sharing code in Site Manager. Try to think up a workaround to remove duplicate code. Also add tests. Dmitry Solomadin.
        persistanceTransaction.execute(new Runnable() {
            public void run() {
                if (itemType == ItemType.GALLERY) {
                    final DraftItem gallery = persistance.getDraftItem(siteItemId);
                    final Site site = persistance.getSite(targetSiteId);
                    final DraftForm form = persistance.getFormById(((DraftGallery) gallery).getFormId1());

                    new ItemManager(form).share(site, SiteOnItemRightType.READ);

                    if (((DraftGallery) gallery).getVoteSettings().getRegistrationFormIdForVoters() != null) {
                        final DraftForm votingRegistrationForm =
                                persistance.getFormById(((DraftGallery) gallery).getVoteSettings().getRegistrationFormIdForVoters());

                        if (votingRegistrationForm != null) {
                            new ItemManager(votingRegistrationForm).share(site, SiteOnItemRightType.READ);
                        }
                    }
                }
            }
        });

        if (siteOnItemRightType != null && siteOnItemRightType == SiteOnItemRightType.READ) {
            sucessRead = true;
        } else if (siteOnItemRightType != null && siteOnItemRightType == SiteOnItemRightType.EDIT) {
            sucessEdit = true;
        }

        sendEmailToContentOwner();
        sendEmailToContentRequester();

        return showInternal(siteOnItemRight);
    }

    private void sendEmailToContentOwner() {
        final User requestedUser = persistance.getUserById(siteOnItemRight.getRequestedUserId());
        final String firstName = StringUtil.getEmptyOrString(requestedUser != null ? requestedUser.getFirstName() : "");
        final String lastName = StringUtil.getEmptyOrString(requestedUser != null ? requestedUser.getLastName() : "");
        final Site ownerSite = persistance.getSite(siteOnItemRight.getItem().getSiteId());
        final Site requestorsSite = persistance.getSite(targetSiteId);
        final User owner = persistance.getUserById(ownerId);
        final String emailBody = international.get("toContentOwnerBody",
                getOwnerFirstLastName(),
                new ItemTypeManager(siteOnItemRight.getItem().getItemType()).getName(),
                ("'" + siteOnItemRight.getItem().getName() + "'"),
                ("'" + ownerSite.getTitle() + "'"),
                ("'" + requestorsSite.getTitle() + "'"),
                (firstName + " " + lastName),
                getRightType(),
                configStorage.get().getSupportEmail(),
                siteOnItemRight.getItem().getItemType().getGroupName()
        );

        mailSender.send(new Mail((owner != null ? owner.getEmail() : ""), emailBody, international.get("toContentOwnerSubject")));
    }

    private void sendEmailToContentRequester() {
        final Site requestorsSite = persistance.getSite(targetSiteId);
        final SiteManager siteManager = new SiteManager(requestorsSite);

        final String emailBody = international.get("toRequesterBodyAccepted",
                new ItemTypeManager(siteOnItemRight.getItem().getItemType()).getName(),
                ("'" + siteOnItemRight.getItem().getName() + "'"),
                ("'" + requestorsSite.getTitle() + "'"),
                siteManager.getPublicUrl(),
                getOwnerFirstLastName(),
                getRightType(),
                configStorage.get().getSupportEmail(),
                siteOnItemRight.getItem().getItemType().getGroupName()
        );
        final User requestedUser = persistance.getUserById(siteOnItemRight.getRequestedUserId());
        if (requestedUser != null) {
            mailSender.send(new Mail(requestedUser.getEmail(), emailBody, international.get("toRequesterSubjectAccepted")));
        } else {
            logger.warning("User which have been requested for contend has not been found. userId = " +
                    siteOnItemRight.getRequestedUserId() + ", siteItemId = " + siteOnItemRight.getItem().getId());
        }
    }

    private String getOwnerFirstLastName() {
        final User owner = persistance.getUserById(ownerId);
        if (owner != null) {
            return StringUtil.getEmptyOrString(owner.getFirstName()) + " " + StringUtil.getEmptyOrString(owner.getLastName());
        }
        return "";
    }

    private String getRightType() {
        return (sucessEdit ? international.get("administrative") : international.get("readOnly"));
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

    public String getSiteTitle() {
        return siteTitle;
    }

    public void setShowOnItemRightType(final SiteOnItemRightType siteOnItemRightType) {
        this.siteOnItemRightType = siteOnItemRightType;
    }

    public String getSiteItemName() {
        return siteItemName;
    }

    public ItemTypeManager getSiteItemType() {
        return new ItemTypeManager(itemType);
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public User getLoginUser() {
        return null;
    }

    public boolean isSucessRead() {
        return sucessRead;
    }

    public boolean isSucessEdit() {
        return sucessEdit;
    }

    public UserManager getRequestedUser() {
        return requestedUser;
    }

    public String getTargetSiteUrl() {
        return targetSiteUrl;
    }

    public String getTargetSiteTitle() {
        return targetSiteTitle;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    private int ownerId;
    private int targetSiteId;
    private String targetSiteUrl;
    private String targetSiteTitle;
    private int siteItemId;
    private SiteOnItemRightType siteOnItemRightType;
    private ItemType itemType;
    private String siteTitle;
    private String siteItemName;
    private String acceptCode;
    private boolean sucessRead;
    private boolean sucessEdit;
    private UserManager requestedUser;
    private SiteOnItem siteOnItemRight;
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final MailSender mailSender = ServiceLocator.getMailSender();
    private final International international = ServiceLocator.getInternationStorage().get("acceptRequestContent", Locale.US);
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
