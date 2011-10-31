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
package com.shroggle.entity;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
@Entity(name = "newMenuItems")
public class DraftMenuItem extends MenuItem {

    @Id
    private int id;

    @ManyToOne
    @ForeignKey(name = "menuItemsMenuId")
    @JoinColumn(name = "menuId", nullable = true)
    private DraftMenu menu;

    private Integer pageId;

    @Column(length = 250)
    private String customUrl;

    @Column(length = 250)
    private String name;

    @Column(length = 250)
    private String title;

    private Integer imageId;

    private boolean includeInMenu;

    private int position;

    @ManyToOne
    @ForeignKey(name = "menuItemsParentId")
    @JoinColumn(name = "parentId")
    private DraftMenuItem parent;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "parent")
    private List<DraftMenuItem> children = new ArrayList<DraftMenuItem>();

    @Enumerated(value = EnumType.STRING)
    @Column(length = 15, nullable = false)
    private MenuUrlType urlType;

    @Column(updatable = false)
    private Integer defaultPageId;


    /*--------------------------------------------------Constructors--------------------------------------------------*/

    public DraftMenuItem() {
        this.pageId = null;
        this.includeInMenu = false;
        this.parent = null;
        this.menu = null;
        this.defaultPageId = this.pageId;
        this.urlType = MenuUrlType.SITE_PAGE;
        this.name = null;
        this.title = null;
        this.customUrl = null;
        this.imageId = null;
    }

    public DraftMenuItem(final Integer pageId, final boolean includeInMenu, final DraftMenu menu) {
        this.pageId = pageId;
        this.includeInMenu = includeInMenu;
        this.parent = null;
        this.defaultPageId = this.pageId;
        this.urlType = MenuUrlType.SITE_PAGE;
        this.menu = menu;
        this.name = null;
        this.title = null;
        this.customUrl = null;
        this.imageId = null;
    }
    /*--------------------------------------------------Constructors--------------------------------------------------*/


    /*-------------------------------------------Simple Getters and Setters-------------------------------------------*/

    //For Hibernate only. Use setParent() menthod.

    @Deprecated
    public void setChildren(List<DraftMenuItem> children) {
        this.children = children;
    }

    public List<MenuItem> getChildren() {
        Collections.sort(children, new Comparator<com.shroggle.entity.MenuItem>() {
            public int compare(com.shroggle.entity.MenuItem o1, com.shroggle.entity.MenuItem o2) {
                return Integer.valueOf(o1.getPosition()).compareTo(o2.getPosition());
            }
        });
        return Collections.unmodifiableList(new ArrayList<MenuItem>(children));
    }

    public DraftMenuItem getParent() {
        return parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public boolean isIncludeInMenu() {
        return includeInMenu;
    }

    public void setIncludeInMenu(boolean includeInMenu) {
        this.includeInMenu = includeInMenu;
    }

    public DraftMenu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        if (menu instanceof DraftMenu) {
            this.menu = (DraftMenu) menu;
        } else {
            logger.warning("Unable to set not DraftMenu as menu for DraftMenuItem.");
        }
    }

    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MenuUrlType getUrlType() {
        return urlType;
    }

    public void setUrlType(MenuUrlType urlType) {
        this.urlType = urlType;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /*-------------------------------------------Simple Getters and Setters-------------------------------------------*/


    void addChildInternal(MenuItem child) {
        if (child instanceof DraftMenuItem) {
            children.add((DraftMenuItem) child);
        } else {
            logger.warning("Unable to add not DraftMenuItem to DraftMenuItem`s children.");
        }
    }

    void removeChildInternal(MenuItem child) {
        if (child instanceof DraftMenuItem) {
            children.remove((DraftMenuItem) child);
        } else {
            logger.warning("Unable to remove not DraftMenuItem from DraftMenuItem`s children.");
        }
    }

    void setParentInternal(MenuItem child) {
        if (child == null) {
            this.parent = null;
            return;
        }
        if (child instanceof DraftMenuItem) {
            this.parent = ((DraftMenuItem) child);
        } else {
            logger.warning("Unable to set as parent not DraftMenuItem.");
        }
    }

    public Integer getDefaultPageId() {
        return defaultPageId;
    }

    public void setDefaultPageId(Integer defaultPageId) {
        this.defaultPageId = defaultPageId;
    }

    /*------------------------------------------------Private methods-------------------------------------------------*/

}
