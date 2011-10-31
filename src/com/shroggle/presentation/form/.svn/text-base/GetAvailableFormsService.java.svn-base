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
package com.shroggle.presentation.form;

import com.shroggle.entity.ItemType;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.SiteItemByNameComparator;
import com.shroggle.util.html.HtmlUtil;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class GetAvailableFormsService extends AbstractService {

    @RemoteMethod
    public List<AvailableFormResponse> execute() {
        final UserRightManager userRightManager = new UsersManager().getLogined().getRight();
        final List<ItemManager> formManagers = ItemManager.siteItemsToManagers(
                userRightManager.getSiteItemsForView(ItemType.ALL_FORMS));
        Collections.sort(formManagers, SiteItemByNameComparator.instance);

        final List<AvailableFormResponse> returnList = new ArrayList<AvailableFormResponse>();
        for (ItemManager formManager : formManagers) {
            AvailableFormResponse availableFormResponse = new AvailableFormResponse();
            availableFormResponse.setFormId(formManager.getId());
            availableFormResponse.setFormType(formManager.getFormType());
            availableFormResponse.setFormName(HtmlUtil.limitName(formManager.getOwnerSiteName(), 40) +
                    " - " + HtmlUtil.limitName(formManager.getName(), 40));
            returnList.add(availableFormResponse);
        }

        return returnList;
    }

}
