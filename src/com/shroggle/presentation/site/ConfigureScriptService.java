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

import com.shroggle.entity.DraftScript;
import com.shroggle.entity.SiteOnItemRightType;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.item.ConfigureItemData;
import com.shroggle.logic.site.item.ItemsManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ServiceWithExecutePage;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

/**
 * @author Artem Stasuk, dmitry.solomadin
 */
@RemoteProxy
public class ConfigureScriptService extends AbstractService implements WithWidgetTitle {

    @RemoteMethod
    public void execute(final Integer widgetId, final Integer scriptItemId) throws Exception {
        new UsersManager().getLogined();

        final ConfigureItemData<DraftScript> itemData =
                new ItemsManager().getConfigureItemData(widgetId, scriptItemId);

        widgetItem = itemData.getWidget();
        draftScript = itemData.getDraftItem();
        widgetTitle = widgetItem != null ? new WidgetTitleGetter(widgetItem) : null;

        getContext().getHttpServletRequest().setAttribute("scriptService", this);
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public DraftScript getDraftScript() {
        return draftScript;
    }

    public WidgetItem getWidgetItem() {
        return widgetItem;
    }

    private DraftScript draftScript;
    private WidgetItem widgetItem;
    private WidgetTitle widgetTitle;

}