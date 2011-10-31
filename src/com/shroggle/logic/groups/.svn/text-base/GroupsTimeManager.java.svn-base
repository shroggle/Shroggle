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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class GroupsTimeManager {

    public GroupsTimeManager(List<GroupsTime> groupsTimes) {
        this.groupsTimes = groupsTimes;
    }

    public boolean containsGroup(final int groupId) {
        if (groupsTimes == null || groupsTimes.isEmpty()) {
            return false;
        }
        for (GroupsTime groupsTime : this.groupsTimes) {
            if (groupsTime.getGroupId() == groupId) {
                return true;
            }
        }
        return false;
    }

    public TimeInterval getSavedTimeInterval(final int groupId) {
        if (groupsTimes == null || groupsTimes.isEmpty()) {
            return null;
        }
        for (GroupsTime groupsTime : this.groupsTimes) {
            if (groupsTime.getGroupId() == groupId) {
                return groupsTime.getTimeInterval();
            }
        }
        return null;
    }

    public static List<GroupsTime> valueOf(final String value) {
        final List<GroupsTime> groupsTimes = new ArrayList<GroupsTime>();
        if (value == null || value.isEmpty()) {
            return groupsTimes;
        }
        for (String groupsWithTimeString : value.split(VALUES_PAIR_DELIMETER)) {
            final GroupsTime groupsTime = new GroupsTime();
            final String groupId = groupsWithTimeString.split(VALUE_DELIMETER)[0];
            groupsTime.setGroupId(Integer.valueOf(groupId));
            final String timeInterval = groupsWithTimeString.split(VALUE_DELIMETER)[1];
            groupsTime.setTimeInterval(timeInterval.equals("null") ? null : TimeInterval.valueOf(timeInterval));
            groupsTimes.add(groupsTime);
        }
        return groupsTimes;
    }


    public static String valueOf(List<GroupsTime> groupsTimes) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (GroupsTime groupsTime : groupsTimes) {
            stringBuilder.append(groupsTime.getGroupId());
            stringBuilder.append(VALUE_DELIMETER);
            stringBuilder.append(groupsTime.getTimeInterval());
            stringBuilder.append(VALUES_PAIR_DELIMETER);
        }
        return stringBuilder.toString();
    }

    private final List<GroupsTime> groupsTimes;

    private static final String VALUES_PAIR_DELIMETER = ";";
    private static final String VALUE_DELIMETER = ":";
}
