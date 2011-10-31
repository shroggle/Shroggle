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
package com.shroggle.logic.site;

import com.shroggle.entity.*;
import com.shroggle.exception.TellFriendCantSendException;
import com.shroggle.exception.TellFriendNameNotUniqueException;
import com.shroggle.exception.TellFriendNameNullOrEmptyException;
import com.shroggle.exception.TellFriendNotFoundException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MailAddressValidator;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;

import java.util.Locale;

/**
 * @author Artem Stasuk
 */
public class TellFriendsManager {

    public TellFriendsManager(final UserManager userManager) {
        this.userManager = userManager;
    }

    public void save(final TellFriendEdit edit) {
        final Widget widget;
        if (edit.getWidgetId() != null) {
            widget = userManager.getRight().getSiteRight().getWidgetForEditInPresentationalService(
                    edit.getWidgetId());
        } else {
            widget = null;
        }

        if (StringUtil.isNullOrEmpty(edit.getTellFriendName())) {
            throw new TellFriendNameNullOrEmptyException("Name empty!");
        }

        edit.setTellFriendName(StringUtil.cutIfNeed(edit.getTellFriendName(), 250));
        edit.setMailSubject(StringUtil.cutIfNeed(edit.getMailSubject(), 250));

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                final DraftTellFriend tellFriend = persistance.getDraftItem(edit.getTellFriendId());
                if (tellFriend == null || tellFriend.getSiteId() <= 0) {
                    throw new TellFriendNotFoundException("Cannot find tell friend by id = " + edit.getTellFriendId());
                }

                final Site site;
                if (widget != null) {
                    site = widget.getSite();
                } else {
                    site = persistance.getSite(tellFriend.getSiteId());
                }

                final DraftTellFriend duplicateTellFriend = persistance.getTellFriendByNameAndSiteId(
                        edit.getTellFriendName(), site.getSiteId());
                if (duplicateTellFriend != null && duplicateTellFriend != tellFriend) {
                    throw new TellFriendNameNotUniqueException("Please, enter unique Tell Friend name.");
                }

                tellFriend.setMailSubject(edit.getMailSubject());
                tellFriend.setMailText(edit.getMailText());
                tellFriend.setName(edit.getTellFriendName());
                tellFriend.setSendEmails(edit.isSendEmails());
            }

        });
    }

    public void send(final TellFriendSend send) {
        if (send == null) {
            return;
        }

        if (!isEmailsValid(send.getEmailTo())) {
            throw new TellFriendCantSendException();
        }

        if (!isEmailsValid(send.getEmailFrom())) {
            throw new TellFriendCantSendException();
        }
        final String messageBody;
        final String messageSubject;
        final SiteManager siteManager = new SiteManager(send.getSiteId());
        final WidgetItem widgetTellFriend = (WidgetItem) persistance.getWidget(send.getWidgetId());
        if (widgetTellFriend != null) {
            final DraftTellFriend tellFriend = (DraftTellFriend) widgetTellFriend.getDraftItem();
            if (tellFriend == null) {
                return;
            }
            messageSubject = StringUtil.getEmptyOrString(tellFriend.getMailSubject());
            messageBody = StringUtil.getEmptyOrString(tellFriend.getMailText()).replaceAll("<url of the page that the tell a friend was on>",
                    new WidgetManager(widgetTellFriend).getPageSettings().getPublicUrl() + "?" + send.getParameters());
        } else {
            final International international = ServiceLocator.getInternationStorage().get("configureTellFriend", Locale.US);

            messageSubject = international.get("mailDefaultSubject");
            messageBody = international.get("mailDefaultTextForChildSiteRegistrationConfirmPage",
                    siteManager.getPublicUrl(),
                    siteManager.getHisNetworkName(),
                    siteManager.getHisNetworkUrl()
            );
        }

        final Mail mail = new Mail();
        mail.setSubject(messageSubject);
        mail.setText(send.getEmail() + "\n\n" +
                messageBody.replaceAll("<`from email address`>", send.getEmailFrom()).replaceAll("<site name>", ("'" + siteManager.getName() + "'")));
        mail.setReply(send.getEmailFrom());
        mail.setFrom(send.getEmailFrom());
        mail.setTo(send.getEmailTo());

        if (send.isCcMe()) {
            mail.setCc(send.getEmailFrom());
        }

        mailSender.send(mail);
    }


    private boolean isEmailsValid(final String emails) {
        if (StringUtil.isNullOrEmpty(emails)) {
            return false;
        }
        final String[] emailsArray = emails.split("[,;]");
        for (String email : emailsArray) {
            if (!emailValidator.valid(email.trim())) {
                return false;
            }
        }
        return true;
    }

    private final UserManager userManager;
    private final MailSender mailSender = ServiceLocator.getMailSender();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final MailAddressValidator emailValidator = ServiceLocator.getMailAddressValidator();

}
