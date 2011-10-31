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
package com.shroggle.logic.user;

import com.shroggle.entity.Group;
import com.shroggle.entity.User;
import com.shroggle.entity.UsersGroup;
import com.shroggle.entity.UsersGroupId;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.TimeInterval;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
public class UsersGroupManager {

    public UsersGroupManager(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Unable to create UsersGroupManager without user!");
        }
        this.user = user;
    }

    public void removeExpiredAccessToGroups() {
        for (final UsersGroup usersGroup : new ArrayList<UsersGroup>(user.getUsersGroups())) {
            if (isAccessExpired(usersGroup)) {
                logger.info("Users (userId = " + user.getUserId() + ") access to group (groupId = " +
                        usersGroup.getGroupId() + ") has been expired:\n current date = " + new Date() +
                        ", expirationDate = " + usersGroup.getExpirationDate() + ". Removing it...");
                removeAccessToGroup(usersGroup.getGroupId());
                logger.info("Users (userId = " + user.getUserId() + ") access to group (groupId = " +
                        usersGroup.getGroupId() + ") successfully removed.");
            }
        }
    }

    public void removeAccessToGroup(final Integer groupId) {
        persistanceTransaction.execute(new Runnable() {
            @Override
            public void run() {
                for (Iterator<UsersGroup> iterator = user.getUsersGroups().iterator(); iterator.hasNext();) {
                    final UsersGroup usersGroup = iterator.next();
                    if (usersGroup.getGroupId() == groupId) {
                        iterator.remove();
                        persistance.removeUsersGroup(usersGroup);
                    }
                }
            }
        });
    }

    public boolean hasAccessToOneOfGroups(final List<Integer> groupsId) {
        for (Integer groupId : groupsId) {
            if (groupId != null && hasAccessToGroup(groupId)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAccessToGroup(final int groupId) {
        UsersGroup accessToGroup = null;
        for (UsersGroup usersGroup : user.getUsersGroups()) {
            if (usersGroup.getGroupId() == groupId) {
                accessToGroup = usersGroup;
                break;
            }
        }
        if (accessToGroup == null) {
            return false;
        } else {
            if (isAccessExpired(accessToGroup)) {
                logger.severe("User (userId = " + user.getUserId() + ") " +
                        "has expired access to group with id = " + accessToGroup.getGroupId() + "!\n " +
                        "This access will be removed now, but please, check mechanism " +
                        "which removes expired users access to groups.\n " +
                        "Expiration date for current group = " + accessToGroup.getExpirationDate() +
                        ", current date = " + new Date() + ". Tolik");
                removeAccessToGroup(accessToGroup.getGroupId());
                return false;
            }
            return true;
        }
    }

    public Date getExpirationDateForGroup(final int groupId) {
        if (hasAccessToGroup(groupId)) {
            for (UsersGroup usersGroup : user.getUsersGroups()) {
                if (usersGroup.getGroupId() == groupId) {
                    return usersGroup.getExpirationDate();
                }
            }
        }
        return null;
    }

    public boolean isAccessExpired(final UsersGroup accessToGroup) {
        return accessToGroup.getExpirationDate() != null && accessToGroup.getExpirationDate().before(new Date());
    }

    public List<Integer> getAccessibleGroupsId() {
        List<Integer> accessibleGroupsId = new ArrayList<Integer>();
        for (UsersGroup usersGroup : new ArrayList<UsersGroup>(user.getUsersGroups())) {
            if (hasAccessToGroup(usersGroup.getGroupId())) {
                accessibleGroupsId.add(usersGroup.getGroupId());
            }
        }
        return accessibleGroupsId;
    }

    public List<Integer> getAccessibleGroupsIdForSite(final int siteId) {
        List<Integer> accessibleGroupsId = new ArrayList<Integer>();
        for (UsersGroup usersGroup : new ArrayList<UsersGroup>(user.getUsersGroups())) {
            if (hasAccessToGroup(usersGroup.getGroupId())) {
                final Group group = persistance.getGroup(usersGroup.getGroupId());
                if (group.getOwner().getId() == siteId) {
                    accessibleGroupsId.add(usersGroup.getGroupId());
                }
            }
        }
        return accessibleGroupsId;
    }

    public void addAccessToGroup(final Group group, final TimeInterval timeInterval) {
        persistanceTransaction.execute(new Runnable() {
            @Override
            public void run() {
                boolean createNewUsersGroup = persistance.getUsersGroup(new UsersGroupId(user, group)) == null;
                final UsersGroup usersGroup = createNewUsersGroup ? new UsersGroup() : persistance.getUsersGroup(new UsersGroupId(user, group));
                usersGroup.setId(user, group);
                if (timeInterval == null) {
                    usersGroup.setExpirationDate(null);
                } else {
                    if (usersGroup.getExpirationDate() == null) {
                        final Date newExpirationDate = new Date(System.currentTimeMillis() + timeInterval.getMillis());
                        logger.info("Access to group with id = " + group.getGroupId() + " for user with id = " +
                                user.getUserId() + " was unlimited so we just creating new expiration date.\n" +
                                " Current date = " + new Date() + ",\n time interval = " + timeInterval.toString() +
                                ",\n new expiration date = " + newExpirationDate);
                        usersGroup.setExpirationDate(newExpirationDate);
                    } else {
                        final Date newExpirationDate = new Date(usersGroup.getExpirationDate().getTime() + timeInterval.getMillis());
                        logger.info("Access to group with id = " + group.getGroupId() + " for user with id = " +
                                user.getUserId() + " was limited.\n New expiration date will be created by formula: currentExpirationDate + timeInterval." +
                                "\n Current expiration date = " + usersGroup.getExpirationDate() + "," +
                                "\n time interval = " + timeInterval.toString() +
                                ",\n new expiration date = " + newExpirationDate);
                        usersGroup.setExpirationDate(newExpirationDate);
                    }
                }
                if (createNewUsersGroup) {
                    persistance.putUsersGroup(usersGroup);
                    user.addAccessToGroup(usersGroup);
                }
            }
        });
    }

    public void addAccessToGroup(final Group group) {
        addAccessToGroup(group, null);
    }

    private final User user;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final Logger logger = Logger.getLogger(this.getClass().getName());
}
