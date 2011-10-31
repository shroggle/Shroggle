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

import java.util.Arrays;
import java.util.List;

/**
 * @author Stasuk Artem
 */
public enum PageType {

    HOME("Home", ItemType.TEXT),
    ABOUT("About", ItemType.TEXT),
    CONTACT("Contact", ItemType.CONTACT_US),
    BLOG("Blog", ItemType.BLOG),
    STORE("Store", ItemType.E_COMMERCE_STORE),
    CATALOG("Catalog", ItemType.GALLERY),
    GALLERY("Gallery", ItemType.GALLERY),
    DIRECTORY("Directory", ItemType.GALLERY),
    REGISTRATION("Registration", ItemType.REGISTRATION),
    SHOPPING_CART("Shopping cart", ItemType.SHOPPING_CART),
    PURCHASE_HISTORY("Purcahse history", ItemType.PURCHASE_HISTORY),
    PUBLIC_FORMS("Public forms", ItemType.CUSTOM_FORM),
    LINK_LIST("Link list"),
    TERMS_AND_CONDITIONS("Terms and Conditions", ItemType.TEXT),
    FORUM("Forum", ItemType.FORUM),
    BLANK("Blank"),
    FAQ("FAQ", ItemType.BLOG, ItemType.BLOG_SUMMARY),
    TESTIMONIALS("Testimonials", ItemType.TEXT),
    LOGIN("Login", ItemType.LOGIN);

    public static List<PageType> getDeniedPageTypes() {
        return Arrays.asList(LOGIN);
    }

    public List<ItemType> getNeededItemTypes() {
        return neededItemTypes;
    }

    public String getNamePattern() {
        return namePattern;
    }

    private PageType(final String namePattern, final ItemType... neededItemTypes) {
        this.namePattern = namePattern;
        this.neededItemTypes = Arrays.asList(neededItemTypes);
    }

    private final List<ItemType> neededItemTypes;
    private final String namePattern;

}