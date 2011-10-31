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
package com.shroggle.logic.system;

import com.shroggle.entity.*;
import com.shroggle.logic.site.CreateSiteRequest;
import com.shroggle.logic.site.SiteCreator;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.presentation.site.CreateSiteKeywordsGroup;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.Collections;
import java.util.Date;

/**
 * @author Artem Stasuk
 */
public class CreateSitesNetworkChildMethod implements CreateSitesMethod {

    public CreateSitesNetworkChildMethod(
            final int filledFormId, final int blueprintId, final String urlPrefix) {
        this.filledFormId = filledFormId;
        this.blueprintId = blueprintId;
        this.urlPrefix = urlPrefix;
    }

    @Override
    public void execute(CreateSitesStatus status) {
        final FilledForm copyFilled = persistance.getFilledFormById(filledFormId);
        final DraftChildSiteRegistration registration = persistance.getChildSiteRegistrationById(copyFilled.getFormId());

        final FilledForm filledForm = new FilledForm();
        filledForm.setPageVisitor(copyFilled.getPageVisitor());
        filledForm.setFormId(registration.getFormId());
        filledForm.setNetworkRegistration(true);
        filledForm.setFormDescription(copyFilled.getFormDescription());
        filledForm.setType(FormType.CHILD_SITE_REGISTRATION);
        copyFilled.getUser().addFilledForm(filledForm);
        persistance.putFilledForm(filledForm);

        for (final FilledFormItem copyItem : copyFilled.getFilledFormItems()) {
            final FilledFormItem item = new FilledFormItem();
            filledForm.addFilledFormItem(item);
            item.setFilledForm(filledForm);
            item.setItemId(copyItem.getItemId());
            item.setFormItemId(copyItem.getFormItemId());
            item.setItemName(copyItem.getItemName());
            item.setPosition(copyItem.getPosition());
            item.setFormItemName(copyItem.getFormItemName());
            item.setValue(copyItem.getValue());
            persistance.putFilledFormItem(item);
        }

        final ChildSiteSettings settings = new ChildSiteSettings();
        settings.setParentSite(persistance.getSite(registration.getSiteId()));
        settings.setWelcomeText("GG");
        settings.setCreatedDate(new Date());
        settings.setFilledFormId(filledForm.getFilledFormId());
        settings.setChildSiteRegistration(registration);
        settings.setUserId(filledForm.getUser().getUserId());
        settings.getSitePaymentSettings().setUserId(filledForm.getUser().getUserId());
        persistance.putChildSiteSettings(settings);
        filledForm.setChildSiteSettingsId(settings.getChildSiteSettingsId());

        final Site blueprint = persistance.getSite(blueprintId);
        if (blueprint == null) {
            info = "Can't find blueprint.";
            throw new UnsupportedOperationException(info);
        }

        final CreateSiteRequest request = new CreateSiteRequest(
                null, filledForm.getUser(), urlPrefix + index, null, urlPrefix + index, null, null,
                Collections.<CreateSiteKeywordsGroup>emptyList(), settings.getChildSiteSettingsId(),
                SiteType.COMMON, new SEOSettings(), null);
        index++;
        final Site child = SiteCreator.updateSiteOrCreateNew(request);

        child.setThemeId(blueprint.getThemeId());

        new SiteManager(child).connectToBlueprint(new SiteManager(blueprint), true);
    }

    @Override
    public String toString() {
        return "Url prefix: " + urlPrefix + ", blueprint site id: " + blueprintId
                + ", copy filled form id: " + filledFormId;
    }

    private String info;
    private int index = 0;
    private String urlPrefix;
    private int filledFormId;
    private int blueprintId;
    private final Persistance persistance = ServiceLocator.getPersistance();

}