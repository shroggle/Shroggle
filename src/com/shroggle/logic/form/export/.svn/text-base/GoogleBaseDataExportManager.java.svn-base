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
package com.shroggle.logic.form.export;

import com.shroggle.entity.FilledForm;
import com.shroggle.entity.FilledFormItem;
import com.shroggle.entity.GoogleBaseDataExport;
import com.shroggle.entity.GoogleBaseDataExportMappedByFilledFormId;
import com.shroggle.exception.HttpConnectionException;
import com.shroggle.logic.form.FilledFormItemManager;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.http.HttpConnection;
import com.shroggle.util.persistance.Persistance;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class GoogleBaseDataExportManager {

    public GoogleBaseDataExportManager(GoogleBaseDataExport googleBaseDataExport) {
        if (googleBaseDataExport == null) {
            throw new UnsupportedOperationException("Unable to create GoogleBaseDataExportManager without GoogleBaseDataExport.");
        }
        this.googleBaseDataExport = googleBaseDataExport;
    }

    public void updateOrCreateGoogleBaseItems(final List<FilledForm> filledForms) {
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                for (FilledForm filledForm : filledForms) {
                    final GoogleBaseDataExportMappedByFilledFormId dataExportMappedByFilledFormId =
                            persistance.getGoogleBaseDataExportMappedByFilledFormIdByFilledFormId(filledForm.getFilledFormId());
                    if (dataExportMappedByFilledFormId != null) {
                        updateItem(dataExportMappedByFilledFormId.getGoogleBaseItemId(), filledForm);
                    } else {
                        final String id = insertItem(filledForm);
                        if (id != null) {
                            final GoogleBaseDataExportMappedByFilledFormId newDataExportMappedByFilledFormId =
                                    new GoogleBaseDataExportMappedByFilledFormId();
                            newDataExportMappedByFilledFormId.setFilledFormId(filledForm.getFilledFormId());
                            newDataExportMappedByFilledFormId.setGoogleBaseItemId(id);
                            persistance.putGoogleBaseDataExportMappedByFilledFormId(newDataExportMappedByFilledFormId);
                        }
                    }
                }
            }
        });
    }


    /*----------------------------------------------------Getters-----------------------------------------------------*/

    public String getGoogleBaseAccountUsername() {
        return googleBaseDataExport.getGoogleBaseAccountUsername();
    }

    public String getGoogleBaseAccountPassword() {
        return googleBaseDataExport.getGoogleBaseAccountPassword();
    }

    public Integer getGalleryId() {
        return googleBaseDataExport.getGalleryId();
    }

    public Integer getFormItemIdForTitle() {
        return googleBaseDataExport.getFormItemIdForTitle();
    }

    public Integer getFormItemIdForDescription() {
        return googleBaseDataExport.getFormItemIdForDescription();
    }
    /*----------------------------------------------------Getters-----------------------------------------------------*/


    /*------------------------------------------------Private Methods-------------------------------------------------*/

    private void updateItem(final String itemId, final FilledForm filledForm) {
        try {
            final HttpConnection httpConnection = new HttpConnection((ITEMS_FEED + "/" + itemId), createItemsAtomValue(filledForm));
            httpConnection.setContentType("application/atom+xml");
            httpConnection.addHeader("Authorization", ("GoogleLogin auth=" + getGoogleLoginAuthenticationKey()));
            httpConnection.setMethod(HttpConnection.HttpMethod.PUT);

            httpConnection.submitData();
        } catch (Exception e) {
            logger.log(Level.SEVERE, ("Unable to update google base item for filledForm with id = " + filledForm.getFilledFormId()), e);
        }
    }

    private String insertItem(final FilledForm filledForm) {
        try {
            final HttpConnection httpConnection = new HttpConnection(ITEMS_FEED, createItemsAtomValue(filledForm));
            httpConnection.setContentType("application/atom+xml");
            httpConnection.addHeader("Authorization", ("GoogleLogin auth=" + getGoogleLoginAuthenticationKey()));
            httpConnection.setMethod(HttpConnection.HttpMethod.POST);

            final String response = httpConnection.submitData().getResponseBodyAsString();

            int startIndex = response.indexOf("<id>") + 4;
            int endIndex = response.indexOf("</id>");
            String itemUrl = response.substring(startIndex, endIndex);
            return itemUrl.replace(itemUrl.substring(0, (itemUrl.lastIndexOf("/") + 1)), "").trim();
        } catch (Exception e) {
            logger.log(Level.SEVERE, ("Unable to insert google base item for filledForm with id = " + filledForm.getFilledFormId()), e);
            return null;
        }
    }

    private String getGoogleLoginAuthenticationKey() {
        String authKey = null;
        try {
            final Map<String, String> postValues = new LinkedHashMap<String, String>();
            postValues.put("accountType", "GOOGLE");
            postValues.put("Email", getGoogleBaseAccountUsername());
            postValues.put("Passwd", getGoogleBaseAccountPassword());
            postValues.put("service", "gbase");
            postValues.put("source", ServiceLocator.getConfigStorage().get().getApplicationUrl());

            final List<String> response = new HttpConnection(AUTHENTICATION_URL, postValues).submitData().getResponseBody();

            for (String string : response) {
                if (string.startsWith("Auth")) {
                    authKey = string.replaceFirst("Auth=", "");
                }
            }
        } catch (HttpConnectionException e) {
            logger.severe("Unable to get google`s authKey.");
        }
        return authKey;
    }

    private String createItemsAtomValue(final FilledForm filledForm) {
        final FilledFormItem title = FilledFormManager.getFilledFormItemByFormItemId(filledForm, getFormItemIdForTitle());
        final FilledFormItem description = FilledFormManager.getFilledFormItemByFormItemId(filledForm, getFormItemIdForDescription());
        final StringBuilder value = new StringBuilder();
        value.append("<?xml version='1.0'?>\n");
        value.append("<entry xmlns='http://www.w3.org/2005/Atom'\n");
        value.append(" xmlns:g='http://base.google.com/ns/1.0'>\n");
        value.append("<title type=\"text\">");
        value.append(new FilledFormItemManager(title).getStringValueForDataExport());
        value.append("</title>\n");
        value.append("<g:item_language type=\"text\">en</g:item_language>\n");
        value.append("<g:item_type type=\"text\">Web-Deva`s custom item</g:item_type>\n");
        value.append("<link  href=\"");
        value.append("http://");//todo. Add correct gallery`s url. Tolik
        value.append(ServiceLocator.getConfigStorage().get().getApplicationUrl());//todo. Add correct gallery`s url. Tolik
        value.append("\"/> \n");
        value.append("<summary>");
        value.append(new FilledFormItemManager(description).getStringValueForDataExport());
        value.append("</summary>");
        value.append("</entry>");
        return value.toString();
    }
    /*------------------------------------------------Private Methods-------------------------------------------------*/

    private final GoogleBaseDataExport googleBaseDataExport;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * URL used for authenticating and obtaining an authentication token.
     * More details about how it works:
     * <code>http://code.google.com/apis/accounts/AuthForInstalledApps.html<code>
     */
    private static final String AUTHENTICATION_URL = "https://www.google.com/accounts/ClientLogin";
    /**
     * URL of the authenticated customer feed.
     */
    private static final String ITEMS_FEED = "http://base.google.com/base/feeds/items";
}
