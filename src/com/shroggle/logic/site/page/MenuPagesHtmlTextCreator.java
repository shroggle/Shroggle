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
import com.shroggle.logic.menu.MenuItemManager;
import com.shroggle.logic.menu.MenuPageCheck;
import com.shroggle.exception.MenuNotFoundException;

import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
//todo. Add tests. Tolik
public class MenuPagesHtmlTextCreator {

    public MenuPagesHtmlTextCreator(Menu menu, MenuPageCheck menuPageCheck, final SiteShowOption siteShowOption) {
        if (menu == null) {
            throw new MenuNotFoundException("Can`t create MenuPagesHtmlTextCreator without Menu!");
        }
        if (menuPageCheck == null) {
            throw new MenuNotFoundException("Can`t create MenuPagesHtmlTextCreator without MenuPageCheck!");
        }
        this.siteShowOption = siteShowOption;
        this.menuPageCheck = menuPageCheck;
        this.html = create(menu.getMenuItems(), 0);
    }

    public String getHtml() {
        return html;
    }

    private String create(final List<MenuItem> items, final int indent) {
        String resultString = "";
        for (final MenuItem item : items) {
            final MenuItemManager manager = new MenuItemManager(item, siteShowOption);
            final String nameWithTitle;
            if (manager.getTitle() != null && manager.getTitle().length() > 0) {
                nameWithTitle = manager.getName() + " (" + manager.getTitle() + ")";
            } else {
                nameWithTitle = manager.getName();
            }
            final String checkbox = "<input type=\"checkbox\" class=\"menuCheckbox\" " + (menuPageCheck.isIncluded(item) ? "checked=\"checked\"" : "") + "/>";

            final String text = "<div id='pageDiv" + item.getPageId() + "'" +
                    " pageId='" + item.getPageId() + "'" +
                    " menuItemId=\"" + item.getId() + "\"" + ">" +
                    "<span><span class='pageName configureMenuPage'>"
                    + checkbox + nameWithTitle +
                    "</span><span style=\"white-space:normal;\"><a href=\"#\" menuItemId=\"" + item.getId() + "\"" +
                    " class=\"eFloatRight\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a><span><span></span>" +
                    "</div>";

            final boolean hasChilds = item.getChildren().size() > 0;
            resultString += "<li "
                    + " id=\"node" + item.getPageId() + "\""
                    + " pageId=\"" + item.getPageId() + "\""
                    + " menuItemId=\"" + item.getId() + "\""
                    + " pageName=\"" + manager.getName() + "\""
                    + " page=\"page\""
                    + ">" + text + (hasChilds ? "<ul class='parent'>" + create(item.getChildren(), indent + 1) + "</ul>" : "") + "</li>";
        }
        return resultString;
    }

    private final MenuPageCheck menuPageCheck;
    private final SiteShowOption siteShowOption;
    private final String html;
}
