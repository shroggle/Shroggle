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
import com.shroggle.exception.ChildSiteRegistrationNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.image.ImageManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PagesWithoutSystem;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.form.ShowForm;
import com.shroggle.presentation.form.ShowFormService;
import com.shroggle.presentation.site.render.RenderPatternFooter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.resource.ResourceGetterUrl;
import com.shroggle.util.resource.provider.ResourceGetterType;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy, dmitry.solomadin
 */
@RemoteProxy
public class ConfigureChildSiteRegistrationService extends AbstractService implements WithWidgetTitle, ShowForm {

    @RemoteMethod
    public void execute(final Integer widgetId, final Integer childSiteRegistrationId) {
        final UserManager userManager = new UsersManager().getLogined();
        final UserRightManager userRightManager = userManager.getRight();

        if (widgetId == null && childSiteRegistrationId == null) {
            throw new IllegalArgumentException("Both widgetId and childSiteRegistrationId cannot be null. " +
                    "This service is only for configuring existing child site registrations.");
        }

        loginedUserEmail = userManager.getEmail();

        if (widgetId == null) {
            childSiteRegistration = persistance.getDraftItem(childSiteRegistrationId);

            if (childSiteRegistration == null) {
                throw new ChildSiteRegistrationNotFoundException("Cannot find child site registration by Id=" +
                        childSiteRegistrationId);
            }

            site = persistance.getSite(childSiteRegistration.getSiteId());
        } else {
            widget = userRightManager.getSiteRight().getWidgetItemForEditInPresentationalService(
                    widgetId);
            site = widget.getSite();
            childSiteRegistration = (DraftChildSiteRegistration) widget.getDraftItem();

            if (childSiteRegistration == null) {
                throw new ChildSiteRegistrationNotFoundException("It appears that widget by Id=" + widgetId + " has no item.");
            }

            widgetTitle = new WidgetTitleGetter(widget);
        }

        paypalEmail = userManager.getEmail();
        blueprints = persistance.getSites(userManager.getUserId(), SiteAccessLevel.getUserAccessLevels(), SiteType.BLUEPRINT);

        //Creating Form options
        ShowFormService showFormService = new ShowFormService();
        showFormService.fillFormItems(FormType.CHILD_SITE_REGISTRATION, childSiteRegistration.getFormId());
        initFormItems = showFormService.getInitFormItems();
        existingFormItems = showFormService.getExistingFormItems();

        if (ImageManager.imageFileExist(childSiteRegistration.getLogoId())) {
            imageUrl = resourceGetterUrl.get(ResourceGetterType.LOGO, childSiteRegistration.getLogoId(), 0, 0, 0, false);
        } else {
            imageUrl = DEFAULT_IMAGE_URL;
        }

        if (ImageManager.imageFileExist(childSiteRegistration.getFooterImageId())) {
            footerImageUrl = resourceGetterUrl.get(ResourceGetterType.FOOTER_IMAGE,
                    childSiteRegistration.getFooterImageId(), 0, 0, 0, false);
        } else {
            footerImageUrl = RenderPatternFooter.DEFAULT_SRC;
        }
        for (Page page : PagesWithoutSystem.get(site.getPages())) {
            pages.add(new PageManager(page));
        }
        getContext().getHttpServletRequest().setAttribute("childSiteRegistrationService", this);
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public WidgetItem getWidget() {
        return widget;
    }

    public DraftChildSiteRegistration getChildSiteRegistration() {
        return childSiteRegistration;
    }

    public String getLoginedUserEmail() {
        return loginedUserEmail;
    }

    public List<FormItemName> getInitFormItems() {
        return initFormItems;
    }

    public List<FormItem> getExistingFormItems() {
        return existingFormItems;
    }

    public FormManager getFormManager() {
        return formManager;
    }

    public List<Site> getBlueprints() {
        return blueprints;
    }

    public String getPaypalEmail() {
        return paypalEmail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public DraftForm getForm() {
        return childSiteRegistration;
    }

    public Site getSite() {
        return site;
    }

    public String getFooterImageUrl() {
        return footerImageUrl;
    }

    public List<PageManager> getPages() {
        return pages;
    }

    private final List<PageManager> pages = new ArrayList<PageManager>();
    private List<FormItemName> initFormItems = new ArrayList<FormItemName>();
    private List<FormItem> existingFormItems = new ArrayList<FormItem>();
    private DraftChildSiteRegistration childSiteRegistration;
    private List<Site> blueprints;
    private WidgetItem widget;
    private final ResourceGetterUrl resourceGetterUrl = ServiceLocator.getResourceGetter();
    private final FormManager formManager = new FormManager();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private WidgetTitle widgetTitle;
    private String loginedUserEmail;
    //Network settings
    private String paypalEmail;
    private String imageUrl;
    private String footerImageUrl;
    private Site site;

    private final static String DEFAULT_IMAGE_URL = "../images/imagesnew/logo24.png";

}