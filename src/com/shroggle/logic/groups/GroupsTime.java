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
package com.shroggle.logic.groups;

import com.shroggle.util.TimeInterval;
import org.directwebremoting.annotations.DataTransferObject;

/**
 * @author Balakirev Anatoliy
 */
@DataTransferObject
public class GroupsTime {

    public GroupsTime(int groupId, TimeInterval timeInterval) {
        this.groupId = groupId;
        this.timeInterval = timeInterval;
    }

    public GroupsTime() {
    }

    private int groupId;

    private TimeInterval timeInterval;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(TimeInterval timeInterval) {
        this.timeInterval = timeInterval;
    }
}
