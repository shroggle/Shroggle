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
package com.shroggle.presentation.slideShow;

import com.shroggle.entity.*;
import com.shroggle.logic.form.FormItemManager;
import com.shroggle.logic.form.FormManager;
import com.shroggle.logic.site.item.ConfigureItemData;
import com.shroggle.logic.site.item.ItemManager;
import com.shroggle.logic.site.item.ItemsManager;
import com.shroggle.logic.slideShow.SlideShowManager;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.widget.WidgetTitle;
import com.shroggle.logic.widget.WidgetTitleGetter;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.site.WithWidgetTitle;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class ConfigureSlideShowService extends AbstractService implements WithWidgetTitle {

    @RemoteMethod
    public void execute(final Integer widgetId, final Integer slideShowItemId) throws Exception {
        userManager = new UsersManager().getLogined();

        final ConfigureItemData<DraftSlideShow> itemData =
                new ItemsManager().getConfigureItemData(widgetId, slideShowItemId);

        widgetItem = itemData.getWidget();
        slideShow = itemData.getDraftItem();
        widgetTitle = widgetItem != null ? new WidgetTitleGetter(widgetItem) : null;
        site = itemData.getSite();

        formManagers = ItemManager.siteItemsToManagers(
                userManager.getRight().getSiteItemsForView(ItemType.ALL_FORMS));
        formManagers = selectFormsWithImages(formManagers);

        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("slideShowService", this);
        webContext.getHttpServletRequest().setAttribute("slideShowImages",
                new SlideShowManager(slideShow).getSortedImages());
    }

    private List<ItemManager> selectFormsWithImages(final List<ItemManager> formManagers) {
        final List<ItemManager> returnList = new ArrayList<ItemManager>();
        for (ItemManager itemManager : formManagers) {
            final FormItem imageItem = FormManager.getFormItemByFormItemName(FormItemName.IMAGE_FILE_UPLOAD,
                    (DraftForm) itemManager.getDraftItem());

            if (imageItem != null){
                returnList.add(itemManager);
            }
        }

        return returnList;
    }

    public WidgetTitle getWidgetTitle() {
        return widgetTitle;
    }

    public WidgetItem getWidgetItem() {
        return widgetItem;
    }

    public DraftSlideShow getSlideShow() {
        return slideShow;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public List<ItemManager> getFormManagers() {
        return formManagers;
    }

    public Site getSite() {
        return site;
    }

    private List<ItemManager> formManagers;

    private DraftSlideShow slideShow;
    private WidgetItem widgetItem;
    private WidgetTitle widgetTitle;
    private Site site;

    private UserManager userManager;

}
