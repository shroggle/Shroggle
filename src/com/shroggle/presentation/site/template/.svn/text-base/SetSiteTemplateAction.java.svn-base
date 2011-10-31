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

package com.shroggle.presentation.site.template;

import com.shroggle.entity.*;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserException;
import com.shroggle.logic.site.SiteCopierFromBlueprint;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.template.TemplateManager;
import com.shroggle.logic.site.template.TemplatesLogic;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.ResolutionParameter;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.presentation.site.QuicklyCreatePagesAction;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperties;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UrlBinding("/site/setSiteTemplate.action")
public class SetSiteTemplateAction extends Action implements LoginedUserInfo {

    @SynchronizeByClassProperties({
            @SynchronizeByClassProperty(
                    entityClass = Site.class,
                    entityIdFieldPath = "siteId")})
    @DefaultHandler
    public Resolution show() {
        final UserManager userManager;
        final Site site;
        try {
            userManager = new UsersManager().getLogined();
            site = userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        } catch (final SiteNotFoundException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }
        user = userManager.getUser();

        siteTitle = site.getTitle();
        final TemplatesLogic templatesLogic = new TemplatesLogic(site);
        this.templatesManager = templatesLogic.getItems();

        final ThemeId themeId = site.getThemeId();
        if (themeId == null) {
            throw new UnsupportedOperationException(
                    "Can't get selected theme, theme id from site: " + siteId + " is null!");
        }

        if (editingMode) {
            selectTemplateId = themeId.getTemplateDirectory().hashCode();
            selectThemeId = themeId.getThemeCss().hashCode();
            if (site.getBlueprintParent() != null) {
                final TemplateManager templateManager = new TemplateManager(
                        site.getBlueprintParent().getThemeId().getTemplateDirectory());
                templateManager.setBlueprintId(site.getBlueprintParent().getSiteId());
                selectBlueprintId = site.getBlueprintParent().getSiteId();
                templateBlueprintsManager.add(templateManager);
            }
        }

        if (!editingMode && site.getChildSiteSettings() != null) {
            templateBlueprintsManager = templatesLogic.getBlueprintItems();
        }

        return resolutionCreator.forwardToUrl("/site/setTemplate/setSiteTemplate.jsp");
    }

    @SynchronizeByClassProperties({
            @SynchronizeByClassProperty(
                    entityClass = Site.class,
                    method = SynchronizeMethod.WRITE,
                    entityIdFieldPath = "siteId")})
    public Resolution execute() {
        final UserManager userManager;
        try {
            userManager = new UsersManager().getLogined();
        } catch (final UserException exception) {
            return resolutionCreator.loginInUser(this);
        }
        user = userManager.getUser();

        final Site site = persistance.getSite(siteId);
        final UserOnSiteRight userOnSiteRight = userManager.getRight().toSite(site);
        if (userOnSiteRight != null && !userOnSiteRight.getSiteAccessType().isUserAccessLevel()) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        Template selectTemplate = null;
        if (selectTemplateId != null) {
            final TemplatesLogic templatesLogic = new TemplatesLogic(site);
            final TemplateManager templateManager = templatesLogic.getTemplateById(selectTemplateId);
            if (templateManager != null) {
                selectTemplate = templateManager.getTemplate();
            }
        }

        if (selectTemplate == null) {
            addValidationError("Please, select a template for site.");
            return show();
        }

        Theme selectTheme = null;
        for (final Theme theme : selectTemplate.getThemes()) {
            if (theme.getFile().hashCode() == selectThemeId) {
                selectTheme = theme;
                break;
            }
        }

        if (selectTheme == null) {
            addValidationError("Please, select a theme for site.");
            return show();
        }

        final Site selectBlueprint;
        if (selectBlueprintId != null) {
            selectBlueprint = persistance.getSite(selectBlueprintId);
            if (selectBlueprint == null) {
                addValidationError("You can't select this blueprint!");
                return show();
            }
        } else {
            selectBlueprint = null;
        }

        final Template tempSelectTemplate = selectTemplate;
        final Theme tempSelectTheme = selectTheme;
        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                site.setThemeId(new ThemeId(tempSelectTemplate.getDirectory(), tempSelectTheme.getFile()));

                Layout defaultSelectLayout = null;
                final Set<String> layoutFilesFroSelectTemplate = new HashSet<String>();
                for (final Layout layout : tempSelectTemplate.getLayouts()) {
                    layoutFilesFroSelectTemplate.add(layout.getFile());

                    if (defaultSelectLayout == null || layout.isUseAsDefault()) {
                        defaultSelectLayout = layout;
                    }
                }

                for (final PageManager draftPageManager : new SiteManager(site).getPages()) {
                    draftPageManager.setHtml(null);
                    draftPageManager.setCss(null);
                    draftPageManager.setThemeId(null);

                    if (defaultSelectLayout != null) {
                        if (!layoutFilesFroSelectTemplate.contains(draftPageManager.getLayoutFile())) {
                            draftPageManager.setLayoutFile(defaultSelectLayout.getFile());
                        }
                    }

                    draftPageManager.setChanged(true);

                    ServiceLocator.getPageVersionNormalizer().execute(draftPageManager);
                }

                if (selectBlueprint == null && site.getBlueprintParent() != null) {
                    new SiteManager(site).disconnectFromBlueprint();
                }

                if (selectBlueprint != null && site.getBlueprintParent() == null) {
                    copierFromBlueprint.execute(
                            selectBlueprint, site, site.getSitePaymentSettings().getSiteStatus() == SiteStatus.ACTIVE);
                }
            }

        });

        if (editingMode) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        } else {
            return resolutionCreator.redirectToAction(
                    QuicklyCreatePagesAction.class, new ResolutionParameter("siteId", siteId));
        }
    }

    public void setSiteId(final int siteId) {
        this.siteId = siteId;
    }

    public int getSiteId() {
        return siteId;
    }

    public Integer getSelectThemeId() {
        return selectThemeId;
    }

    public void setSelectThemeId(final Integer selectThemeId) {
        this.selectThemeId = selectThemeId;
    }

    public Integer getSelectTemplateId() {
        return selectTemplateId;
    }

    public void setSelectTemplateId(final Integer selectTemplateId) {
        this.selectTemplateId = selectTemplateId;
    }

    public User getLoginUser() {
        return user;
    }

    public boolean isEditingMode() {
        return editingMode;
    }

    public void setEditingMode(boolean editingMode) {
        this.editingMode = editingMode;
    }

    public Integer getSelectBlueprintId() {
        return selectBlueprintId;
    }

    public void setSelectBlueprintId(final Integer selectBlueprintId) {
        this.selectBlueprintId = selectBlueprintId;
    }

    public List<TemplateManager> getTemplatesLogic() {
        return templatesManager;
    }

    public List<TemplateManager> getTemplateBlueprintsLogic() {
        return templateBlueprintsManager;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final SiteCopierFromBlueprint copierFromBlueprint = ServiceLocator.getSiteCopierFromBlueprint();
    private List<TemplateManager> templateBlueprintsManager = new ArrayList<TemplateManager>();
    private List<TemplateManager> templatesManager = new ArrayList<TemplateManager>();
    private Integer selectTemplateId;
    private Integer selectThemeId;
    private Integer selectBlueprintId;
    private boolean editingMode;
    private User user;
    private int siteId;
    private String siteTitle;

}