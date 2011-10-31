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
import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
@Entity(name = "accessibleSettings")
public class AccessibleSettings {

    @Id
    private int accessibleSettingsId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccessForRender access = AccessForRender.UNLIMITED;

    private boolean administrators = false;

    private boolean visitors = false;

    @CollectionOfElements
    private List<Integer> visitorsGroups = new ArrayList<Integer>();

    public boolean isAdministrators() {
        return administrators;
    }

    public void setAdministrators(boolean administrators) {
        this.administrators = administrators;
    }

    public boolean isVisitors() {
        return visitors;
    }

    public void setVisitors(boolean visitors) {
        this.visitors = visitors;
    }

    public List<Integer> getVisitorsGroups() {
        return visitorsGroups;
    }

    public void setVisitorsGroups(List<Integer> visitorsGroups) {
        this.visitorsGroups = visitorsGroups;
    }

    public AccessForRender getAccess() {
        return access;
    }

    public void setAccess(AccessForRender accessGroup) {
        this.access = accessGroup;
    }

    public int getAccessibleSettingsId() {
        return accessibleSettingsId;
    }

    public void setAccessibleSettingsId(int accessibleSettingsId) {
        this.accessibleSettingsId = accessibleSettingsId;
    }
    
}
