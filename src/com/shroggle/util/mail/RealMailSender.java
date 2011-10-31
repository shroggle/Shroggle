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

package com.shroggle.util.mail;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.ConfigSmtp;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.mail.Message.RecipientType;

public class RealMailSender implements MailSender {

    @Override
    public void send(Mail mail) {
        final ConfigSmtp configSmtp = ServiceLocator.getConfigStorage().get().getConfigSmtp();
        final String smtpLogin = configSmtp.getLogin();
        final Session session = createSession(configSmtp, smtpLogin);

        try {
            final MimeMessage message = createMessage(mail, session);
            setMessageContent(message, mail);
            sendMessage(session, message);
            logger.log(Level.INFO, mail.getText());
            logger.log(Level.INFO, mail.getHtml());
        } catch (final Exception exception) {
            logger.log(Level.INFO, mail.getText());
            logger.log(Level.INFO, mail.getHtml());
            throw new MailSenderException(exception);
        }
    }

    private void sendMessage(final Session session, final MimeMessage message) throws MessagingException {
        final Transport transport = session.getTransport();
        transport.connect();
        transport.sendMessage(message, message.getRecipients(RecipientType.TO));
        if (message.getRecipients(RecipientType.CC) != null) {
            transport.sendMessage(message, message.getRecipients(RecipientType.CC));
        }
        transport.close();
    }

    private MimeMessage createMessage(final Mail mail, final Session session) throws MessagingException {
        final MimeMessage message = new MimeMessage(session);
        message.setSubject(mail.getSubject(), "UTF-8");
        message.addRecipients(RecipientType.TO, mail.getTo());
        message.setFrom(new InternetAddress(mail.getFrom()));
        if (mail.getReply() != null) {
            message.setReplyTo(new InternetAddress[]{new InternetAddress(mail.getReply())});
        }

        if (mail.getCc() != null) {
            message.addRecipients(RecipientType.CC, mail.getCc());
        }
        return message;
    }

    private Session createSession(final ConfigSmtp configSmtp, final String smtpLogin) {
        final Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.allow8bitmime", "true");
        properties.setProperty("mail.host", configSmtp.getUrl());
        properties.setProperty("mail.user", smtpLogin);
        properties.setProperty("mail.password", configSmtp.getPassword());

        final Session mailSession = Session.getDefaultInstance(properties, null);
        mailSession.setDebug(false);
        return mailSession;
    }

    private void setMessageContent(final MimeMessage message, final Mail mail) throws MessagingException {
        if (mail.getHtml() == null) {
            message.setText(mail.getText(), "UTF-8");
        } else {
            final MimeBodyPart text = new MimeBodyPart();
            text.setText(mail.getText() == null ? mail.getHtml() : mail.getText());
            text.setHeader("MIME-Version", "1.0");
            text.setHeader("Content-Type", text.getContentType());

            final MimeBodyPart html = new MimeBodyPart();
            html.setContent(mail.getHtml(), "text/html");
            html.setHeader("MIME-Version", "1.0");
            html.setHeader("Content-Type", "text/html");

            final MimeMultipart content = new MimeMultipart("alternative");
            content.addBodyPart(text);
            content.addBodyPart(html);
            message.setContent(content);
        }
    }

    private final Logger logger = Logger.getLogger(RealMailSender.class.getName());
}
