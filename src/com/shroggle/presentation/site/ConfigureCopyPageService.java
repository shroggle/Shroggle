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

import com.shroggle.entity.DraftItem;
import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.pageversion.PageTitle;
import com.shroggle.logic.site.page.pageversion.PageTitleGetter;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.*;

/**
 * @author Artem Stasuk
 */
@RemoteProxy
@Filter(type = SynchronizeForDwrFilter.class)
public class ConfigureCopyPageService extends ServiceWithExecutePage implements WithPageVersionTitle {

    @SynchronizeByMethodParameter(
            entityClass = PageManager.class)
    @RemoteMethod
    public String execute(final int pageId) throws Exception {
        this.pageId = pageId;

        final UserManager userManager = new UsersManager().getLogined();
        final PageManager pageManager = userManager.getRight().getSiteRight().getPageForEdit(pageId);

        final Set<DraftItem> noSortDraftItems = new HashSet<DraftItem>();
        for (final Widget widget : pageManager.getWidgets()) {
            if (widget.isWidgetItem()) {
                final WidgetItem widgetItem = (WidgetItem) widget;
                noSortDraftItems.add(widgetItem.getDraftItem());
            }
        }

        draftItems = new ArrayList<DraftItem>(noSortDraftItems);
        Collections.sort(draftItems, new Comparator<DraftItem>() {

            @Override
            public int compare(final DraftItem o1, final DraftItem o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }

        });

        pageTitle = new PageTitleGetter(pageManager);
        return executePage("/site/configureCopyPage.jsp");
    }

    @Override
    public PageTitle getPageTitle() {
        return pageTitle;
    }

    public List<DraftItem> getDraftItems() {
        return draftItems;
    }

    public int getPageId() {
        return pageId;
    }

    private List<DraftItem> draftItems;
    private PageTitle pageTitle;
    private int pageId;

}