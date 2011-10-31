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
package com.shroggle.logic.site.page;

import com.shroggle.entity.*;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.html.HtmlUtil;
import com.shroggle.logic.menu.MenuItemManager;

import java.util.List;
import java.text.SimpleDateFormat;

/**
 * @author Balakirev Anatoliy
 */
//todo. Add tests. Dmitry Solomadin
public class DefaultMenuPagesHtmlTextCreator {

    public String create(final Site site, final SiteShowOption siteShowOption) {
        return createInternal(site.getMenu().getMenuItems(), 0, siteShowOption);
    }

    private String createInternal(final List<MenuItem> items, final int indent, final SiteShowOption siteShowOption) {
        String resultString = "";
        for (final com.shroggle.entity.MenuItem item : items) {
            final PageManager pageManager = new PageManager(ServiceLocator.getPersistance().getPage(item.getPageId()));

            final DraftPageSettings lastDraftVersion = pageManager.getDraftPageSettings();
            final WorkPageSettings lastWorkVersion = pageManager.getWorkPageSettings();

            final MenuItemManager manager = new MenuItemManager(item, siteShowOption);
            String text = limitPageName != null ?
                    HtmlUtil.limitName(manager.getName(), limitPageName) : manager.getName();
            if (manager.getTitle() != null && manager.getTitle().length() > 0) {
                text += " (" + (limitPageName != null ? HtmlUtil.limitName(manager.getTitle(), limitPageName)
                        : manager.getTitle()) + ")";
            }

            if (lastWorkVersion != null) {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy @ K:mm:ss a");
                text += " <span class='postedText'>Posted: " + format.format(lastWorkVersion.getCreationDate()) + "</span>";
            } else if (forSelect) {
                text += " <span class='postedText'>Posted: n/a</span>";
            }

            text = "<span class='pageName " + (lastDraftVersion.isChanged() ? "draftPage" : "livePage") + "'>" +
                     text + "</span>";

            if (forSelect) {
                //Adding indent for select items.
                for (int i = 0; i < indent; i++) {
                    text = "<img src='/images/tree/s.gif' class='customSelectSpacer'/>" + text;
                }
            }
            text = "<div id='pageDiv" + item.getPageId() + "'" +
                    " pageId='" + item.getPageId() + "'" +
                    " menuItemId=\"" + item.getId() + "\"" +
                    "><span>" + text + "</span></div>";

            final boolean hasChilds = item.getChildren().size() > 0;
            resultString += "<li"
                    + " id=\"node" + item.getPageId() + "\""
                    + " pageId=\"" + item.getPageId() + "\""
                    + " menuItemId=\"" + item.getId() + "\""
                    + " pageName=\"" + manager.getName() + "\""
                    + " newPage=\"" + (lastWorkVersion == null ? "true" : "false") + "\""
                    + " pageLocked=\"" + lastDraftVersion.isBlueprintLocked() + "\""
                    + " pageRequired=\"" + lastDraftVersion.isBlueprintRequired() + "\""
                    + " pageNotEditable=\"" + lastDraftVersion.isBlueprintNotEditable() + "\""
                    + " page=\"page\""
                    + (lastDraftVersion.getAccessibleSettings().getAccess() != AccessForRender.UNLIMITED ? " icon=\"padlock\"" : "")
                    + ">" + text + (hasChilds ? "<ul class='parent'>" + createInternal(item.getChildren(), indent + 1, siteShowOption) + "</ul>" : "") + "</li>";
        }

        return resultString;
    }

    public void setLimitPageName(Integer limitPageName) {
        this.limitPageName = limitPageName;
    }

    public void setForSelect(boolean forSelect) {
        this.forSelect = forSelect;
    }

    private Integer limitPageName;
    private boolean forSelect = false;
}
