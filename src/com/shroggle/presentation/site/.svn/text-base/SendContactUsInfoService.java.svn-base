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
import com.shroggle.exception.CannotSendMailWithEmptyMessageException;
import com.shroggle.exception.ContactUsNotFoundException;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.WrongVerificationCodeException;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.form.LinkedFormManager;
import com.shroggle.logic.site.WidgetManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.visitor.PageVisitorManager;
import com.shroggle.logic.visitor.VisitorManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ActionUtil;
import com.shroggle.presentation.site.forms.FormPageAdditionalParameters;
import com.shroggle.presentation.site.render.RenderContext;
import com.shroggle.presentation.site.render.RenderFormPageBreak;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.international.International;
import com.shroggle.util.mail.Mail;
import com.shroggle.util.mail.MailSender;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Balakirev Anatoliy
 */

@RemoteProxy
public class SendContactUsInfoService extends AbstractService {


    @RemoteMethod
    public SendContactUsInfoResponse execute(final SendContactUsInfoRequest request) throws Exception {
        final User user = new UsersManager().getLoginedUser();
        final Integer loginedVisitorId = user != null ? user.getUserId() : null;

        if (!request.isShowFromAddRecord() && (request.getPageBreaksToPass() == 0 || request.getPageBreaksToPass() == 1)) {
            final String noBotCode = ServiceLocator.getSessionStorage().getNoBotCode(this, "contactUs" + request.getWidgetId());
            if (!noBotCode.equals(request.getVerificationCode())) {
                throw new WrongVerificationCodeException(international.get("wrongCode"));
            }
        }

        final Widget widget = persistance.getWidget(request.getWidgetId());
        final ItemManager itemManager = (widget != null) ? new WidgetManager(widget).getItemManager() : new ItemManager(request.getFormId());
        final ContactUs contactUs = (ContactUs) itemManager.getItem(request.getSiteShowOption());


        if (contactUs == null) {
            throw new ContactUsNotFoundException("Cannot find contact us.");
        }

        //Creating or updating filledForm.
        final FilledForm filledForm = createOrUpdateFilledForm(loginedVisitorId, contactUs, request);

        //Sending message after last page of the form
        if (FormManager.getTotalPageBreaks(contactUs) == 0 ||
                (FormManager.getTotalPageBreaks(contactUs) + 1 == request.getPageBreaksToPass())) {
            sendForm(filledForm, contactUs);
        }

        final SendContactUsInfoResponse response = new SendContactUsInfoResponse();
        response.setFilledFormId(filledForm.getFilledFormId());

        // If user has filled out last form page then we should show him first page again.
        // In case we are showing form not from admin iface.
        if (!request.isShowFromAddRecord() &&
                request.getPageBreaksToPass() > FormManager.getTotalPageBreaks(contactUs)) {
            request.setPageBreaksToPass(0);
            response.setShowSuccessfullSubmitMessage(true);
        }

        if (request.isRequestNextPage() && !(request.getPageBreaksToPass() > FormManager.getTotalPageBreaks(contactUs))) {
            if (widget != null) {
                final RenderContext context = createRenderContext(false);

                response.setNextPageHtml(new RenderFormPageBreak().execute(request.getWidgetId(), request.getFormId(),
                        request.getPageBreaksToPass(), filledForm.getFilledFormId(), new ArrayList<FormPageAdditionalParameters>(), context));
            } else {
                response.setNextPageHtml(new RenderFormPageBreak().executeForAddRecord(ItemType.CONTACT_US,
                        request.getPageBreaksToPass(), filledForm.getFilledFormId(), contactUs.getId(), new ArrayList<FormPageAdditionalParameters>()));
            }
        }

        return response;
    }

    private void sendForm(final FilledForm filledForm, final ContactUs contactUs) {
        String messageBody = getMessageField(filledForm);

        if (messageBody.trim().equals("")) {
            throw new CannotSendMailWithEmptyMessageException(international.get("CannotSendMailWithEmptyMessage"));
        }

        messageBody += "\n";

        final Site site = persistance.getSite(contactUs.getSiteId());

        if (site == null) {
            throw new SiteNotFoundException("Cannot find site by Id=" + contactUs.getSiteId());
        }

        //Composing message
        final String siteName = site.getTitle() != null ? site.getTitle() : site.getSubDomain();
        for (FilledFormItem item : filledForm.getFilledFormItems()) {
            //Skip message field
            if (item.getFormItemName() == FormItemName.CONTACT_US_MESSAGE) {
                continue;
            }

            messageBody += item.getItemName();
            messageBody += ": ";

            // SW-6573 | SuperWiki - contact us form has a 'field shared from another form' displays as #
            List<String> itemValues = null;
            if (item.getFormItemName().getType().equals(FormItemType.LINKED)) {
                itemValues = LinkedFormManager.getLinkedValues(item);
            } else {
                itemValues = item.getValues();
            }

            for (String itemValue : itemValues) {
                messageBody += " " + itemValue;
            }

            messageBody += "\n";
        }

        //Sending message
        final MailSender mailSender = ServiceLocator.getMailSender();
        final Logger logger = Logger.getLogger(this.getClass().getName());
        final String messageSubject = international.get("messageHeading", siteName);
        try {
            final Mail mail = new Mail(contactUs.getEmail(), messageBody, messageSubject);
            final String visitorEmail = getEmailField(filledForm);
            if (!StringUtil.getEmptyOrString(visitorEmail).isEmpty()) {
                mail.setReply(visitorEmail);
                mail.setCc(visitorEmail);
            }
            mailSender.send(mail);
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Can't send mail!", exception);
        }
    }

    private String getMessageField(final FilledForm filledForm) {
        for (FilledFormItem filledFormItem : filledForm.getFilledFormItems()) {
            if (filledFormItem.getFormItemName() == FormItemName.CONTACT_US_MESSAGE) {
                return filledFormItem.getValue();
            }
        }
        return "";
    }

    private String getEmailField(final FilledForm filledForm) {
        for (FilledFormItem filledFormItem : filledForm.getFilledFormItems()) {
            if (filledFormItem.getFormItemName() == FormItemName.EMAIL) {
                return filledFormItem.getValue();
            }
        }
        return "";
    }

    private FilledForm createOrUpdateFilledForm(final Integer loginedVisitorId, final ContactUs contactUs, final SendContactUsInfoRequest request) {
        return persistanceTransaction.execute(new PersistanceTransactionContext<FilledForm>() {
            @Override
            public FilledForm execute() {
                FilledForm filledForm = null;
                if (request.getFilledFormId() != null) {
                    //If we already have filledForm (i.e: filledForm created on first form page and updated on second.) then just update it with new items.
                    filledForm = persistance.getFilledFormById(request.getFilledFormId());
                    FilledFormManager.updateFilledFormItems(request.getFilledFormItems(), filledForm);
                } else {
                    if (loginedVisitorId != null) {
                        User user = persistance.getUserById(loginedVisitorId);
                        filledForm = new VisitorManager(user).addFilledFormToVisitor(request.getFilledFormItems(), contactUs);
                    } else {
                        final Integer pageVisitorId = ActionUtil.getPageVisitorId(getContext().getHttpServletRequest().getCookies());
                        final PageVisitor pageVisitor = persistance.getPageVisitorById(pageVisitorId);
                        if (pageVisitor != null) {
                            filledForm = new PageVisitorManager(pageVisitor).addFilledFormToPageVisitor(request.getFilledFormItems(), contactUs);
                        }
                    }
                }
                return filledForm;
            }
        });
    }

    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final International international = ServiceLocator.getInternationStorage().get("widgetContactUs", Locale.US);
}