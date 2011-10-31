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
package com.shroggle.logic.accessibility;

import com.shroggle.entity.*;
import com.shroggle.exception.SiteOnItemRightNotFoundException;
import com.shroggle.exception.SiteOnItemRightOwnerException;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;

import java.util.*;

/**
 * Before use this class read comments above need methods.
 * Now, your security system define 3 access way:
 * <ul>
 * <li>If you want edit or view site, pages, widgets</li>
 * <li>If you want disconnect site item from site</li>
 * <li>If you want edit or view site item (settings or you can use data)</li>
 * </ul>
 *
 * @author Artem Stasuk
 */
public class UserRightManager {

    public UserRightManager(final User user) {
        this.user = user;
    }

    public UserSiteRightManager getSiteRight() {
        return new UserSiteRightManager(this);
    }

    /**
     * Find and return siteOnItemRight between user and selected site for item by type,
     * never return null, return real object or throw exception.
     *
     * @param siteItemId - item id
     * @param siteId     - site id
     * @param type       - site item type
     * @return - site on item right
     */
    public SiteOnItem getSiteOnItemRightForEdit(
            final Integer siteItemId, final Integer siteId, final ItemType type) {
        if (siteId != null && siteItemId != null && type != null) {
            final DraftItem siteItem = persistance.getDraftItem(siteItemId);
            if (siteItem != null) {
                if (siteId.equals(siteItem.getSiteId())) {
                    throw new SiteOnItemRightOwnerException();
                }

                final SiteOnItem siteOnItemRight =
                        persistance.getSiteOnItemRightBySiteIdItemIdAndType(siteId, siteItemId, type);
                if (siteOnItemRight != null) {
                    if (isUserOnSiteRightForEdit(siteItem.getSiteId())) {
                        return siteOnItemRight;
                    }

                    if (isUserOnSiteRightForEdit(siteId) && siteOnItemRight.getAcceptDate() != null) {
                        return siteOnItemRight;
                    }
                }
            }
        }

        throw new SiteOnItemRightNotFoundException(
                "Can't find for " + type + " with id " + siteItemId + " on site " + siteId);
    }

    /**
     * @param siteId - site id
     * @return - return right only if it's valid active and etc, in other cases return null
     *         //todo test for visitor with pending or expired rights.
     */
    public UserOnSiteRight getValidUserOnSiteRight(final int siteId) {
        if (user == null) {
            return null;
        }
        final UserOnSiteRight validRight = getCachedValidRight(siteId); // For better performance. Tolik
        if (validRight != null) {
            return validRight;
        }

        final UserOnSiteRight userOnSiteRight = persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), siteId);
        if (isUserOnSiteRightValid(userOnSiteRight)) {
            validUserOnSiteRights.add(userOnSiteRight);
            return userOnSiteRight;
        }
        return null;
    }

    public static boolean isUserOnSiteRightValid(final UserOnSiteRight userOnSiteRight) {
        return userOnSiteRight != null && userOnSiteRight.isActive()
                && userOnSiteRight.getVisitorStatus() != VisitorStatus.PENDING
                && userOnSiteRight.getVisitorStatus() != VisitorStatus.EXPIRED;
    }

    public void createUserOnSiteRight(final Site site, final SiteAccessLevel accessLevel, final boolean fromNetwork) {
        final UserOnSiteRight userOnSiteRight = new UserOnSiteRight();
        userOnSiteRight.setSiteAccessType(accessLevel);
        userOnSiteRight.setActive(true);
        userOnSiteRight.setFromNetwork(fromNetwork);
        user.addUserOnSiteRight(userOnSiteRight);
        site.addUserOnSiteRight(userOnSiteRight);
        ServiceLocator.getPersistance().putUserOnSiteRight(userOnSiteRight);
    }

    /**
     * @param itemType - what site item group you want receive
     * @return - site items what accessible for this user with edit and view rights, in sort by name.
     */
    public List<DraftItem> getSiteItemsForView(final ItemType itemType) {
        return persistance.getDraftItemsByUserId(user.getUserId(), itemType);
    }

    public UserOnSiteRight toSite(final Site site) {
        if (checkValidUser() && site != null) {
            final UserOnSiteRight userOnSiteRight = getValidUserOnSiteRight(site.getSiteId());

            //Checking if user has normal rights on site
            if (userOnSiteRight != null) {
                return userOnSiteRight;
            }

            final UserOnSiteRight networkRight = getNetworkRight(site);

            //Checking if user has network rights on site
            if (networkRight != null) {
                return networkRight;
            }
        }
        return null;
    }

    //Network rights for child site exists if visitor has rights on parent site.

    public UserOnSiteRight getNetworkRight(final Site childSite) {
        final boolean isChildSite = childSite.getChildSiteSettings() != null;
        if (isChildSite) {
            final UserOnSiteRight visitorOnNetworkSiteRight =
                    getValidUserOnSiteRight(childSite.getChildSiteSettings().getParentSite().getSiteId());
            if (isUserOnSiteRightValid(visitorOnNetworkSiteRight)
                    && Arrays.asList(SiteAccessLevel.getVisitorAccessLevels()).contains(visitorOnNetworkSiteRight.getSiteAccessType())) {
                return visitorOnNetworkSiteRight;
            }
            // todo: do it right. dima
            /*final FilledForm filledNetworkRegistrationForm =
                    visitorOnNetworkSiteRight != null ? visitorOnNetworkSiteRight.getFilledRegistrationForm() : null;
            if (filledNetworkRegistrationForm != null) {
                final RegistrationForm networkRegistrationForm =
                        persistance.getRegistrationFormById(filledNetworkRegistrationForm.getFormId());
                if (networkRegistrationForm != null && filledNetworkRegistrationForm.isNetworkRegistration()) {
                    return visitorOnNetworkSiteRight;
                }
            }*/
        }
        return null;
    }

    //Check's for entire user. Use this for all common cases

    public SiteOnItemRightType toSiteItem(final Item item) {
        if (checkValidUser() && item != null) {
            //Checking item owned by site.
            final SiteOnItemRightType siteOnItemRightType = getValidOwner(item);
            if (siteOnItemRightType != null) {
                return siteOnItemRightType;
            }

            //Checking if user has rights on item itself which was received through sharing.
            return getValidOver(item);
        }
        return null;
    }

    //Check's for site. Use this when you need to check rights for paticular site.

    public SiteOnItemRightType toSiteItem(final Item siteItem, final Site forSite) {
        if (checkValidUser() && siteItem != null) {
            //Checking item owned by site.
            final SiteOnItemRightType siteOnItemRightType = isOwnedBySite(siteItem, forSite);

            final UserOnSiteRight userOnSiteRight = toSite(forSite);
            if (siteOnItemRightType != null &&
                    (userOnSiteRight != null && (userOnSiteRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR
                            || userOnSiteRight.getSiteAccessType() == SiteAccessLevel.EDITOR))) {
                return siteOnItemRightType;
            }

            //Checking if user has rights on item itself which was recieved thru sharing.
            return getValidOver(siteItem);
        }
        return null;
    }

    public List<Integer> getAvailableSiteIds() {
        final List<Integer> availableSiteIds = new ArrayList<Integer>();
        final List<Site> sites = persistance.getSites(user.getUserId(),
                SiteAccessLevel.getUserAccessLevels());

        for (Site site : sites) {
            availableSiteIds.add(site.getSiteId());
        }

        return availableSiteIds;
    }

    /*------------------------------------------------Private methods-------------------------------------------------*/

    private boolean isUserOnSiteRightForEdit(final Integer siteId) {
        if (siteId != null) {
            final UserOnSiteRight userOnSiteRight = getValidUserOnSiteRight(siteId);
            return userOnSiteRight != null && userOnSiteRight.getSiteAccessType() != SiteAccessLevel.VISITOR;
        }
        return false;
    }


    private <T extends DraftItem> void removeUnaccessibleForView(final Collection<T> siteItems) {
        final Iterator<T> siteItemsIterator = siteItems.iterator();
        while (siteItemsIterator.hasNext()) {
            if (toSiteItem(siteItemsIterator.next()) == null) {
                siteItemsIterator.remove();
            }
        }
    }

    private SiteOnItemRightType getValidOwner(final Item item) {
        if (item != null && item.getSiteId() > 0) {
            return getValidSiteOnItemRight(item.getSiteId());
        }
        return null;
    }

    private SiteOnItemRightType isOwnedBySite(final Item siteItem, final Site forSite) {
        if (siteItem != null && siteItem.getSiteId() > 0 && forSite != null && siteItem.getSiteId() == forSite.getId()) {
            return SiteOnItemRightType.EDIT;
        }
        return null;
    }

    private SiteOnItemRightType getValidOver(final Item item) {
        if (item != null) {
            for (final SiteOnItem siteOnItemRight : persistance.getSiteOnItemsByItem(item.getId())) {
                if (siteOnItemRight.getAcceptDate() != null) {
                    final int siteId = siteOnItemRight.getSite().getSiteId();
                    if (getValidUserOnSiteRight(siteId) != null) {
                        return siteOnItemRight.getType();
                    }
                }
            }
        }
        return null;
    }

    private SiteOnItemRightType getValidSiteOnItemRight(final int siteId) {
        final UserOnSiteRight userOnSiteRight = getValidUserOnSiteRight(siteId);
        if (userOnSiteRight != null) {
            if (userOnSiteRight.getSiteAccessType() == SiteAccessLevel.ADMINISTRATOR) {
                return SiteOnItemRightType.EDIT;
            } else if (userOnSiteRight.getSiteAccessType() == SiteAccessLevel.EDITOR) {
                return SiteOnItemRightType.READ;
            }
        }
        return null;
    }

    /**
     * @return - true if user correct and active and not expired in other cases false
     */
    private boolean checkValidUser() {
        if (userValid != null) {
            return userValid;
        } else {
            userValid = (user != null && user.getActiveted() != null && user.getRegistrationDate() != null);
            return userValid;
        }
    }

    private final User user;
    private final Persistance persistance = ServiceLocator.getPersistance();

    
    // Following fields and methods are for better performance. Tolik
    private Boolean userValid = null;
    private List<UserOnSiteRight> validUserOnSiteRights = new ArrayList<UserOnSiteRight>();

    private UserOnSiteRight getCachedValidRight(final int siteId) {
        for (UserOnSiteRight right : validUserOnSiteRights) {
            if (right.getUserId() == user.getUserId() && right.getSiteId() == siteId) {
                return right;
            }
        }
        return null;
    }
}
