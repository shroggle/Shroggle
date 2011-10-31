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
import com.shroggle.logic.childSites.childSiteRegistration.ChildSiteRegistrationManager;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.util.DateUtil;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MailSender;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Balakirev Anatoliy
 */
public class ChildSiteEmailSender {

    public static void execute(final ChildSiteSettings settings, final Site networkSite, final User user) {
        final International international = ServiceLocator.getInternationStorage().get("childSiteRegistration", Locale.US);
        final String messageBody = createMessageBody(settings, networkSite);
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        final String messageSubject = international.get("childSiteEmailSubject",
                new ChildSiteSettingsManager(settings).getNetworkName(), StringUtil.getEmptyOrString(user.getFirstName()), StringUtil.getEmptyOrString(user.getLastName()));
        try {
            final MailSender mailSender = ServiceLocator.getMailSender();
            final Mail mail = new Mail(user.getEmail(), null, messageBody, messageSubject);
            mail.setFrom(StringUtil.isNullOrEmpty(settings.getFromEmail()) ? configStorage.get().getMail().getNetworkFrom() : settings.getFromEmail());
            mail.setReply(!StringUtil.isNullOrEmpty(settings.getFromEmail()) ? settings.getFromEmail() : null);
            mailSender.send(mail);
        } catch (Exception exception) {
            final Logger logger = Logger.getLogger(ChildSiteEmailSender.class.getName());
            logger.log(Level.SEVERE, "Can't send mail!", exception);
        }
    }

    protected static String createMessageBody(final ChildSiteSettings settings, final Site networkSite) {
        DraftChildSiteRegistration form = settings.getChildSiteRegistration();
        final String emailText;
        if (!StringUtil.getEmptyOrString(form.getEmailText()).isEmpty()) {
            FilledForm filledForm = ServiceLocator.getPersistance().getFilledFormById(settings.getFilledFormId());
            final FilledFormItem itemFirstName = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.FIRST_NAME);
            final FilledFormItem itemLastName = FilledFormManager.getFilledFormItemByFormItemName(filledForm, FormItemName.LAST_NAME);

            emailText = ChildSiteRegistrationManager.createEmailBodyByCustomEmailText(
                    form.getEmailText(),
                    itemFirstName != null ? itemFirstName.getValues().get(0) : "",
                    itemLastName != null ? itemLastName.getValues().get(0) : "",
                    networkSite.getTitle(),
                    settings.getStartDate() != null ? DateUtil.toMonthDayAndYear(settings.getStartDate()) : "",
                    settings.getEndDate() != null ? DateUtil.toMonthDayAndYear(settings.getEndDate()) : "",
                    String.valueOf(settings.isUseOneTimeFee() ? settings.getOneTimeFee() : settings.getPrice250mb()),
                    form.getName(), createUrl(settings), settings.isUseOneTimeFee()
            );
        } else {
            final International international = ServiceLocator.getInternationStorage().get("childSiteRegistration", Locale.US);
            final String startDate = settings.getStartDate() != null ? international.get("childSiteEmailBodyStartDate", DateUtil.toMonthDayAndYear(settings.getStartDate())) : "";
            final String endDate = settings.getEndDate() != null ? international.get("childSiteEmailBodyEndDate", DateUtil.toMonthDayAndYear(settings.getEndDate())) : "";
            emailText = international.get("childSiteEmailBody", networkSite.getTitle(), startDate, endDate,
                    settings.getPrice250mb(), createUrl(settings), settings.getChildSiteRegistration().getName());
        }
        return emailText;
    }

    private static String createUrl(final ChildSiteSettings settings) {
        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        return "http://" + configStorage.get().getApplicationUrl() + "/childSiteRegistrationConfirmation.action?settingsId=" +
                settings.getChildSiteSettingsId() + "&confirmCode=" + settings.getConfirmCode();
    }
}