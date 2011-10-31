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

/**
 * Item order in this enum is very important. Because position is a security level.
 * I`ve replaced getting security level by order with special field, named 'securityLevel'. Tolik
 *
 * @author Stasuk Artem
 */
public enum AccessGroup {

    ALL(0), GUEST(1), VISITORS(2), OWNER(3);

    AccessGroup(int securityLevel){
        this.securityLevel = securityLevel;
    }

    public static AccessGroup max(final AccessGroup accessGroup1, final AccessGroup accessGroup2){
        return AccessGroup.values()[Math.max(accessGroup1.securityLevel, accessGroup2.securityLevel)];
    }

    private final int securityLevel;

}
