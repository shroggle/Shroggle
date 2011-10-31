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

import com.shroggle.logic.menu.MenuItemsManager;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * @author Artem Stasuk
 */
public abstract class MenuItem {

/*---------------------------------Methods for external modifying tree structure----------------------------------*/

    /**
     * @param parent - new parent for this element. If parent is null - add this element to menu (so it becomes root).
     *               Setting element position = children.size (adding it to the end).
     */
    public void setParent(MenuItem parent) {
        this.moveToNewParent(parent);
    }

    /**
     * @param position - desired position of current element in the list of children.
     *                 Set to this element desired position. If position is null or position is more than childrens size -
     *                 move this element to the end of the childrens list.
     *                 Else - set to this element desired position and increment positions of all elements after current.
     */
    public void moveToPosition(final Integer position) {
        final List<MenuItem> children;
        if (this.getParent() != null) {
            children = new ArrayList<MenuItem>(this.getParent().getChildren());
        } else {
            children = new ArrayList<MenuItem>(this.getMenu().getMenuItems());
        }
        final int normalizedPosition = (position == null || position > children.size() || position < 0)
                ? (children.size() - 1) : position;
        MenuItemsManager.normalizePositions(children);
        if (this.getPosition() == normalizedPosition) {
            logger.info("Current element already has desired position. Skip 'moveToPosition' execution.");
            return;
        }
        if (this.getPosition() > normalizedPosition) {
            // Move current element to lower position. We must increment other elements position from desired to the end
            for (int i = normalizedPosition; i < children.size(); i++) {
                children.get(i).setPosition(i + 1);
            }
        } else {
            // Move current element to higher position. We must decrement other elements position from this element old
            // position + 1 to desired
            for (int i = (this.getPosition() + 1); i <= normalizedPosition; i++) {
                children.get(i).setPosition(i - 1);
            }
        }
        this.setPosition(normalizedPosition);
    }

    public void removeChild(MenuItem child) {
        removeChildInternal(child);
        MenuItemsManager.normalizePositions(getChildren());
    }
    /*---------------------------------Methods for external modifying tree structure----------------------------------*/


    /*-------------------------------------------Simple Getters and Setters-------------------------------------------*/

    public abstract List<MenuItem> getChildren();

    public abstract MenuItem getParent();

    public abstract int getId();

    public abstract void setId(int id);

    public abstract Integer getPageId();

    public abstract void setPageId(Integer pageId);

    public abstract boolean isIncludeInMenu();

    public abstract void setIncludeInMenu(boolean includeInMenu);

    public abstract Menu getMenu();

    public abstract void setMenu(Menu menu);

    public abstract String getCustomUrl();

    public abstract void setCustomUrl(String customUrl);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getTitle();

    public abstract void setTitle(String title);

    public abstract MenuUrlType getUrlType();

    public abstract void setUrlType(MenuUrlType urlType);

    public abstract Integer getImageId();

    public abstract void setImageId(Integer imageId);

    public abstract int getPosition();

    public abstract void setPosition(int position);

    public abstract Integer getDefaultPageId();

    public abstract void setDefaultPageId(Integer defaultPageId);

    @Override
    public String toString() {
        return "MenuItem {id: " + getId() + ", name: " + getName() + ", parent: " + getParent() + "}";
    }

    /*-------------------------------------------Simple Getters and Setters-------------------------------------------*/


    void addChild(MenuItem child) {
        MenuItemsManager.normalizePositions(getChildren());
        child.setPosition(getChildren().size());
        addChildInternal(child);
    }

    abstract void addChildInternal(MenuItem child);

    abstract void removeChildInternal(MenuItem child);

    abstract void setParentInternal(MenuItem child);

    /*------------------------------------------------Private methods-------------------------------------------------*/

    /**
     * @param newParent - new parent for current element
     *                  Note:
     *                  newParent can be NewMenuItem - then we just set it as parent for current element and add
     *                  current element to its children (also we remove treeNode element from his old parents children).
     *                  <p/>
     *                  Or newParent can be null. In treeNode case current element is root and we do same things but with
     *                  menu as parent.
     */
    private void moveToNewParent(final MenuItem newParent) {
        if (elementsEquals(this, newParent)) {
            logger.warning("Trying to set element as parent for himself! I am skipping menuItem action, but please, check " +
                    "your code. Current element: MenuItem, id = " + this.getId() + " Parent: " + getInformationAboutParent(newParent));
            return;
        }
        if (hasChild(newParent)) {
            logger.warning("Can`t move element to his child! Current element: MenuItem, id = " + this.getId() +
                    " Parent: " + getInformationAboutParent(newParent));
            return;
        }

        final MenuItem oldParent = this.getParent();
        if (oldParent != null) {
            oldParent.removeChild(this);
        } else {
            if (getMenu() == null) {
                throwItemWithoutMenuException();
            }
            getMenu().removeChild(this);
        }
        if (newParent != null) {
            newParent.addChild(this);
        } else {
            if (getMenu() == null) {
                throwItemWithoutMenuException();
            }
            getMenu().addChild(this);
        }
        setParentInternal(newParent);
    }


    private void throwItemWithoutMenuException() {
        throw new IllegalStateException("MenuItem in tree structure can`t be without 'Menu'." +
                " We must remove all menuItems without menu. MenuItem id = " + this.getId());
    }


    static boolean elementsEquals(final MenuItem firstElement, final MenuItem secondElement) {
        // Elements equal if they are link to one object
        if (firstElement == secondElement) {
            return true;
        }
        // Elements equal if they have same ids more than zero
        if (firstElement != null && secondElement != null) {
            if (firstElement.getId() > 0 && firstElement.getId() == secondElement.getId()) {
                return true;
            }
        }
        return false;
    }

    boolean hasChild(final MenuItem element) {
        return hasChild(this.getChildren(), element);
    }

    private boolean hasChild(final List<MenuItem> children, final MenuItem child) {
        for (MenuItem rootChild : children) {
            if (elementsEquals(rootChild, child)) {
                return true;
            }
            if (hasChild(rootChild.getChildren(), child)) {
                return true;
            }
        }
        return false;
    }

    private String getInformationAboutParent(final MenuItem parent) {
        if (parent != null) {
            return "MenuItem, id = " + parent.getId();
        } else {
            return "Menu, id = " + getMenu().getId();
        }
    }
    /*------------------------------------------------Private methods-------------------------------------------------*/
    @Transient
    protected static final Logger logger = Logger.getLogger(MenuItem.class.getName());
}