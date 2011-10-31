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

import com.shroggle.entity.BlueprintCategory;
import com.shroggle.entity.Page;
import com.shroggle.entity.Site;
import com.shroggle.exception.UserException;
import com.shroggle.logic.site.SiteManager;
import com.shroggle.logic.site.page.PageManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.persistance.Persistance;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.ArrayList;
import java.util.List;

@UrlBinding("/site/getBlueprints.action")
public class GetBlueprintsAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        try {
            new UsersManager().getLogined();
            final Persistance persistance = ServiceLocator.getPersistance();
            for (final Site blueprint : persistance.getActiveBlueprints(blueprintCategory)) {
                final GetBlueprintsItem item = new GetBlueprintsItem();
                item.setTitle(blueprint.getTitle());
                item.setId(blueprint.getId());

                for (PageManager pageManager : new SiteManager(blueprint).getPages()){
                    final String screenShowUrl = pageManager.getScreenShotUrl();

                    if (StringUtil.isNullOrEmpty(screenShowUrl) || screenShowUrl.equals("#")){
                        continue;
                    }

                    item.addScreenShowUrl(screenShowUrl);
                }

                items.add(item);
            }

        } catch (final UserException exception) {
            // none
        }

        return ServiceLocator.getResolutionCreator().forwardToUrl("/WEB-INF/pages/getBlueprints.jsp");
    }

    public List<GetBlueprintsItem> getItems() {
        return items;
    }

    public void setBlueprintCategory(BlueprintCategory blueprintCategory) {
        this.blueprintCategory = blueprintCategory;
    }

    private List<GetBlueprintsItem> items = new ArrayList<GetBlueprintsItem>();
    private BlueprintCategory blueprintCategory;

}