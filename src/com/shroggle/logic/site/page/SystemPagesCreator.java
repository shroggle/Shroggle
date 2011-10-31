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
package com.shroggle.logic.site.page;

import com.shroggle.entity.*;
import com.shroggle.logic.site.item.ItemPosterReal;
import com.shroggle.util.ServiceLocator;

/**
 * @author Balakirev Anatoliy
 */
public class SystemPagesCreator {

    public static Page createDefaultLoginPageForSite(final Site site) {
        return createLoginPageForSiteInternal(site, false);
    }

    public static Page createAdminLoginPageForSite(final Site site) {
        return createLoginPageForSiteInternal(site, true);
    }

    private static Page createLoginPageForSiteInternal(
            final Site site, final boolean admin) {
        final DraftPageSettings draftPageSettings = new DraftPageSettings();
        final String name = admin ? "defaultLoginAdminPage" : "defaultLoginPage";
        draftPageSettings.setName(name);
        draftPageSettings.setUrl(name);

        String html = ServiceLocator.getFileSystem().getLoginPageDefaultHtml();
        if (admin) {
            html = ServiceLocator.getFileSystem().getLoginAdminPageDefaultHtml();
        }
        draftPageSettings.setHtml(html);
        ServiceLocator.getPersistance().putPageSettings(draftPageSettings);

        final AccessibleSettings accessibleSettings = new AccessibleSettings();
        ServiceLocator.getPersistance().putAccessibleSettings(accessibleSettings);
        draftPageSettings.setAccessibleSettings(accessibleSettings);


        final Page page = new Page();
        page.setType(PageType.LOGIN);
        page.setSystem(true);
        page.setPageSettings(draftPageSettings);
        draftPageSettings.setPage(page);
        site.addPage(page);
        if (admin) {
            site.setLoginAdminPage(page);
        } else {
            site.setLoginPage(page);
        }
        ServiceLocator.getPersistance().putPage(page);

        final WidgetComposit widgetComposit = new WidgetComposit();
        ServiceLocator.getPersistance().putWidget(widgetComposit);
        draftPageSettings.addWidget(widgetComposit);


        final DraftText text = new DraftText();
        text.setSiteId(draftPageSettings.getSiteId());
        text.setText("");
        if (admin) {
            text.setText("Access Restricted - Administrator Level Only.<br><br>");
        }
        text.setText(text.getText() +
                "This is a restricted access area<br>" +
                "Please Login, or Contact the site owner for details");
        ServiceLocator.getPersistance().putItem(text);
        new ItemPosterReal().publish(text);

        final WidgetItem widgetItem1 = new WidgetItem();
        widgetItem1.setDraftItem(text);
        widgetComposit.addChild(widgetItem1);
        ServiceLocator.getPersistance().putWidget(widgetItem1);
        draftPageSettings.addWidget(widgetItem1);

        final DraftLogin draftLogin = new DraftLogin();
        ServiceLocator.getPersistance().putItem(draftLogin);
        new ItemPosterReal().publish(draftLogin);

        final WidgetItem widgetItem = new WidgetItem();
        widgetItem.setPosition(1);
        widgetComposit.addChild(widgetItem);
        widgetItem.setDraftItem(draftLogin);
        ServiceLocator.getPersistance().putWidget(widgetItem);
        draftPageSettings.addWidget(widgetItem);
        

        new PageManager(page).publish();
        return page;
    }

}
