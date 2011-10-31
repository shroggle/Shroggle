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
package com.shroggle.presentation.taxRates;

import com.shroggle.entity.DraftTaxRatesUS;
import com.shroggle.entity.SiteOnItemRightType;
import com.shroggle.logic.site.item.ConfigureItemData;
import com.shroggle.logic.site.item.ItemsManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.presentation.site.WithWidgetTitle;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Balakirev Anatoliy, dmitry.solomadin
 */
@RemoteProxy
public class ConfigureTaxRatesService extends ServiceWithExecutePage implements WithWidgetTitle {

    @RemoteMethod
    public String execute(final Integer widgetId, final Integer itemId) throws Exception {
        final UserManager userManager = new UsersManager().getLogined();

        final ConfigureItemData<DraftTaxRatesUS> itemData =
                new ItemsManager().getConfigureItemData(widgetId, itemId);

        draftTaxRates = itemData.getDraftItem();
        widgetTitle = itemData.getWidget() != null ? new WidgetTitleGetter(itemData.getWidget()) : null;
        siteOnItemRightType = userManager.getRight().toSiteItem(draftTaxRates, itemData.getSite());

        return executePage("/taxRates/configureTaxRates.jsp");
    }

    @Override
    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public SiteOnItemRightType getSiteOnItemRightType() {
        return siteOnItemRightType;
    }

    public DraftTaxRatesUS getDraftTaxRates() {
        return draftTaxRates;
    }

    private DraftTaxRatesUS draftTaxRates;
    private WidgetTitle widgetTitle;
    private SiteOnItemRightType siteOnItemRightType;

}
