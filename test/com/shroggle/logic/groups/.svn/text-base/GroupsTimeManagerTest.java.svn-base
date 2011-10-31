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
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Balakirev Anatoliy
 */
public class GroupsTimeManagerTest {

    @Test
    public void testValueOf() throws Exception {
        final List<GroupsTime> groupsTimes = new ArrayList<GroupsTime>() {{
            add(new GroupsTime(1, TimeInterval.ONE_DAY));
            add(new GroupsTime(2, TimeInterval.TWO_DAYS));
            add(new GroupsTime(3, TimeInterval.ONE_WEEK));
            add(new GroupsTime(4, TimeInterval.ONE_YEAR));
            add(new GroupsTime(5, TimeInterval.ONE_MONTH));
            add(new GroupsTime(6, null));
        }};
        final String groupsTimeStringValue = GroupsTimeManager.valueOf(groupsTimes);
        Assert.assertEquals("1:ONE_DAY;2:TWO_DAYS;3:ONE_WEEK;4:ONE_YEAR;5:ONE_MONTH;6:null;", groupsTimeStringValue);

        final List<GroupsTime> createdGroupsTimeList = GroupsTimeManager.valueOf(groupsTimeStringValue);
        Assert.assertEquals(groupsTimes.size(), createdGroupsTimeList.size());
        for (int i = 0; i < groupsTimes.size(); i++) {
            Assert.assertEquals(groupsTimes.get(i).getGroupId(), createdGroupsTimeList.get(i).getGroupId());
            Assert.assertEquals(groupsTimes.get(i).getTimeInterval(), createdGroupsTimeList.get(i).getTimeInterval());
        }
    }
}
