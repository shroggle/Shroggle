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
package com.shroggle.logic.menu;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.lang.reflect.Field;

public class MenuItemData implements Cloneable {

    private final long id = System.nanoTime();

    private String name;

    private String description;

    private String href;

    private int level;

    private String number;

    private List<MenuItemData> children = new ArrayList<MenuItemData>();

    private boolean selected;

    private boolean externalUrl;

    private boolean showImage = false;

    private String imageUrl = "";

    private boolean lastBottom = false;

    private boolean lastRight = false;

    public int getIndex() {
        if (number == null || !number.contains("_")) {
            return 0;
        }
        int tempIndex = Integer.valueOf(number.substring(number.indexOf("_") + 1));
        return --tempIndex;
    }

    public boolean isLastBottom() {
        return lastBottom;
    }

    public void setLastBottom(boolean lastBottom) {
        this.lastBottom = lastBottom;
    }

    public boolean isLastRight() {
        return lastRight;
    }

    public void setLastRight(boolean lastRight) {
        this.lastRight = lastRight;
    }

    public boolean isShowImage() {
        return showImage;
    }

    public void setShowImage(boolean showImage) {
        this.showImage = showImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(boolean externalUrl) {
        this.externalUrl = externalUrl;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHref() {
        return isSelected() ? "javascript:void(0);" : href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<MenuItemData> getChildren() {
        return children;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public void setChildren(List<MenuItemData> children) {
        this.children = children;
    }

    public MenuItemData clone() {
        try {
            final MenuItemData clone = (MenuItemData) super.clone();
            final long newId = (System.currentTimeMillis() - new Random().nextInt(1000));
            final Field field = MenuItemData.class.getDeclaredField("id");
            field.setAccessible(true);
            field.setLong(clone, newId);
            field.setAccessible(false);
            return clone;
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Unable to clone object.", e);
        }
        return null;
    }
}
