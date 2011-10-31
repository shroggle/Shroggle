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
package com.shroggle.presentation.childSites;

import com.shroggle.entity.*;
import com.shroggle.logic.childSites.childSiteRegistration.ChildSiteRegistrationManager;
import com.shroggle.logic.childSites.childSiteSettings.ChildSiteSettingsManager;
import com.shroggle.logic.registrationConfirmation.RegistrationConfirmationManager;
import com.shroggle.logic.site.CreateSiteRequest;
import com.shroggle.logic.site.SiteCreator;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionParameter;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.presentation.site.SiteEditPageAction;
import com.shroggle.presentation.site.render.RenderWidgets;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */

@UrlBinding("/childSiteRegistrationConfirmation.action")
public class ChildSiteRegistrationConfirmationAction extends Action implements LoginedUserInfo {

    @DefaultHandler
    public Resolution show() throws IOException {
        childSiteSettings = persistance.getChildSiteSettingsById(settingsId);
        user = getUser(childSiteSettings, confirmCode);
        if (user == null) {
            return new ForwardResolution("/account/registration/registrationLinkNotValid.jsp");
        } else if (RegistrationConfirmationManager.isLinkExpired(childSiteSettings)) {
            return new ForwardResolution("/account/registration/registrationLinkExpired.jsp");
        } else {
            if (user.getActiveted() == null) {
                persistanceTransaction.execute(new Runnable() {
                    public void run() {
                        user.setActiveted(new Date());
                    }
                });
            }
            new UsersManager().login(user.getEmail(), user.getPassword(), null);
            if (childSiteSettings.getSite() != null) {
                return ServiceLocator.getResolutionCreator().redirectToAction(
                        SiteEditPageAction.class, new ResolutionParameter("siteId", childSiteSettings.getSite().getSiteId()));
            }
            final ChildSiteSettingsManager childSiteSettingsManager = new ChildSiteSettingsManager(childSiteSettings);
            final String contactUsPageUrl = childSiteSettingsManager.getHisNetworkContactUsUrl();
            getHttpServletRequest().setAttribute("contactUsPageUrl", contactUsPageUrl);

            if (childSiteSettingsManager.childSiteShouldBeCreated()) {
                final String urlPrefix = childSiteSettingsManager.getUniqueDomainName();
                final String title = childSiteSettingsManager.getSiteTitle();

                persistanceTransaction.execute(new Runnable() {

                    @Override
                    public void run() {
                        CreateSiteRequest createSiteRequest = new CreateSiteRequest(null, user, urlPrefix, null, title,
                                null, null, null, childSiteSettings.getChildSiteSettingsId(), SiteType.COMMON, new SEOSettings(), null);
                        final Site site = SiteCreator.updateSiteOrCreateNew(createSiteRequest);

                        final ChildSiteRegistrationManager childSiteRegistrationManager = new ChildSiteRegistrationManager(childSiteSettings.getChildSiteRegistration());
                        final Site blueprint = childSiteRegistrationManager.getDefaultBlueprint();
                        final SiteManager siteManager = new SiteManager(site);
                        siteManager.connectToBlueprint(new SiteManager(blueprint), siteManager.isActive());
                        if (blueprint != null) {
                            site.setThemeId(new ThemeId(blueprint.getThemeId().getTemplateDirectory(), blueprint.getThemeId().getThemeCss()));
                        }

                        url = siteManager.getPublicUrl();
                        tellFriendHtml = createTellFriendHtml(site);
                    }

                });

                return new ForwardResolution("/account/registration/childSiteRigistrationConfirmationFirstState.jsp");
            } else {
                return new ForwardResolution("/account/registration/childSiteRigistrationConfirmationSecondState.jsp");
            }
        }
    }

    private User getUser(final ChildSiteSettings childSiteSettings, final String confirmCode) {
        if (childSiteSettings != null && childSiteSettings.getConfirmCode() != null && confirmCode != null &&
                confirmCode.equals(childSiteSettings.getConfirmCode())) {
            return persistance.getUserById(childSiteSettings.getUserId());
        }
        return null;
    }

    private String createTellFriendHtml(final Site site) {
        try {
            String tellFriendHtml = new RenderWidgets(null, SiteShowOption.ON_USER_PAGES).renderWidgetTellFriend(-1, site.getSiteId(), true, createRenderContext(false));
            return tellFriendHtml.replaceFirst("(?<=>)(.|\n)*?(?=</a>)", international.get("tellPeopleAboutYourSite"));
        } catch (Exception e) {
            logger.log(Level.INFO, "Can`t create tell friend block for child site registration confirmation page.");
            return "";
        }
    }


    @Override
    public User getLoginUser() {
        return user;
    }

    public ChildSiteSettings getChildSiteSettings() {
        return childSiteSettings;
    }

    public Integer getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(Integer settingsId) {
        this.settingsId = settingsId;
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    public String getUrl() {
        return url;
    }

    public String getTellFriendHtml() {
        return tellFriendHtml;
    }

    private String url;
    private User user;
    private String tellFriendHtml;
    private Integer settingsId;
    private String confirmCode;
    private ChildSiteSettings childSiteSettings;
    private Persistance persistance = ServiceLocator.getPersistance();
    private PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Logger logger = Logger.getLogger(ChildSiteRegistrationConfirmationAction.class.getName());
    private final International international = ServiceLocator.getInternationStorage().get("childSiteRigistrationConfirmationFirstState", Locale.US);
}
