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
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.ResolutionParameter;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stasuk Artem
 */
@UrlBinding("/showHtmlAsPage.action")
public class ShowHtmlAsPageAction extends Action {

    public Resolution execute() throws IOException, ServletException {
        int pageId = -1;

        final Site site;
        try {
            final UserManager userManager = new UsersManager().getLogined();
            site = userManager.getRight().getSiteRight().getSiteForEdit(siteId).getSite();

            pageId = persistanceTransaction.execute(
                    new PersistanceTransactionContext<Integer>() {

                        @Override
                        public Integer execute() {
                            Page page = persistance.getPageByNameAndSite("test", site.getId());
                            if (page == null) {
                                page = new Page();
                                site.addPage(page);
                                page.setType(PageType.BLANK);
                                persistance.putPage(page);

                                final AccessibleSettings accessibleSettings = new AccessibleSettings();
                                ServiceLocator.getPersistance().putAccessibleSettings(accessibleSettings);

                                final DraftPageSettings draftPageSettings = new DraftPageSettings();
                                draftPageSettings.setAccessibleSettings(accessibleSettings);
                                draftPageSettings.setName("test");
                                draftPageSettings.setUrl("test");
                                draftPageSettings.setPage(page);
                                page.setPageSettings(draftPageSettings);
                                persistance.putPageSettings(draftPageSettings);
                            }

                            page.getPageSettings().setHtml(StringUtil.getEmptyOrString(html));

                            final PageManager pageManager = new PageManager(page, SiteShowOption.INSIDE_APP);
                            ServiceLocator.getPageVersionNormalizer().execute(pageManager);

                            final List<Widget> widgets = new ArrayList<Widget>(pageManager.getWidgets());
                            for (final Widget widget : widgets) {
                                if (widget.getParent() == null) {
                                    final WidgetComposit widgetComposit = (WidgetComposit) widget;
                                    for (int i = 1; i < widgetComposit.getChilds().size(); i++) {
                                        final WidgetItem widgetItem = (WidgetItem) widgetComposit.getChilds().get(i);
                                        final DraftItem draftItem = widgetItem.getDraftItem();
                                        persistance.removeWidget(widgetItem);
                                        persistance.removeDraftItem(draftItem);
                                    }

                                    if (widgetComposit.getChilds().size() == 0) {
                                        final DraftText draftText = new DraftText();
                                        draftText.setSiteId(siteId);
                                        draftText.setText("Preview Text Component");
                                        persistance.putItem(draftText);

                                        final WidgetItem widgetItem = new WidgetItem();
                                        widgetItem.setDraftItem(draftText);
                                        widgetComposit.addChild(widgetItem);
                                        pageManager.addWidget(widgetItem);
                                        persistance.putWidget(widgetItem);
                                    }
                                }
                            }

                            return page.getPageId();
                        }

                    });
        } catch (final UserNotLoginedException exception) {
            // none
        } catch (final SiteNotFoundException exception) {
            // none
        }

        return resolutionCreator.redirectToAction(
                ShowPageVersionAction.class,
                new ResolutionParameter("pageId", pageId),
                new ResolutionParameter("siteShowOption", SiteShowOption.INSIDE_APP)
        );
    }

    public void setHtml(final String html) {
        this.html = html;
    }

    public void setSiteId(final int siteId) {
        this.siteId = siteId;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private String html;
    private int siteId;

}