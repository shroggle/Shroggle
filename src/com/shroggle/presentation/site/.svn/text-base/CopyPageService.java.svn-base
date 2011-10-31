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
import com.shroggle.entity.SiteShowOption;
import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.item.*;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.site.page.PageSelectTextCreator;
import com.shroggle.logic.site.page.PageTreeTextCreator;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.page.SavePageResponse;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.process.synchronize.annotation.SynchronizeForDwrFilter;
import org.directwebremoting.annotations.Filter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stasuk Artem
 */
@RemoteProxy
// todo. add tests.
@Filter(type = SynchronizeForDwrFilter.class)
public class CopyPageService extends AbstractService {

    @RemoteMethod
    public SavePageResponse execute(final CopyPageRequest request) {
        return persistanceTransaction.execute(new PersistanceTransactionContext<SavePageResponse>() {

            @Override
            public SavePageResponse execute() {
                final Set<Integer> itemIds = new HashSet<Integer>();
                for (final Integer itemId : request.getItems().keySet()) {
                    itemIds.add(itemId);
                }

                final PageManager pageCopy = new PageManager(
                        persistance.getPage(request.getPageId())).copy(itemIds);

                final ItemCopierContext context = new ItemCopierContext();
                context.setCopiedSite(pageCopy.getSite());
                context.setItemNaming(new ItemNamingRealWithAddingCopyWord());
                final ItemCopier itemCopier = new ItemCopierWithSameMenuItems(
                        new ItemCopierCache(new ItemCopierSimple()));
                for (final Widget copiedWidget : pageCopy.getWidgets()) {
                    if (copiedWidget.isWidgetItem()) {

                        final WidgetItem widgetItem = (WidgetItem) copiedWidget;
                        final CopyPageItem pageItem = request.getItems().get(widgetItem.getDraftItem().getId());

                        if (pageItem != null && pageItem.getType() == CopyPageItemType.COPY) {
                            final ItemCopyResult itemCopyResult = itemCopier.execute(context, widgetItem.getDraftItem(), null);
                            widgetItem.setDraftItem((DraftItem) itemCopyResult.getDraftItem());
                        }
                    }
                }

                final SavePageResponse response = new SavePageResponse();
                response.setPageId(pageCopy.getPageId());
                response.setTreeHtml(new PageTreeTextCreator().execute(pageCopy.getSiteId(), SiteShowOption.getDraftOption()));
                response.setPageSelectHtml(new PageSelectTextCreator().execute(pageCopy.getSiteId(), SiteShowOption.getDraftOption()));
                return response;
            }

        });
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}
