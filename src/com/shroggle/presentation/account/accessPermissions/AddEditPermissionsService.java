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

package com.shroggle.presentation.account.accessPermissions;

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteAccessLevel;
import com.shroggle.entity.User;
import com.shroggle.entity.UserOnSiteRight;
import com.shroggle.exception.LoginedUserWithoutRightsException;
import com.shroggle.exception.OneSiteAdminException;
import com.shroggle.logic.user.EmailChecker;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;


@RemoteProxy
public class AddEditPermissionsService extends AbstractService {


    @RemoteMethod
    public String showShareYourSitesPage(final int userId) throws IOException, ServletException {
        final Integer loginedUserId = new UsersManager().getLogined().getUserId();

        selectedUser = persistance.getUserById(userId);
        disableWindow = userId == loginedUserId;
        loginedUser = persistance.getUserById(loginedUserId);
        selectedUserSites = persistance.getSites(loginedUserId, new SiteAccessLevel[]{SiteAccessLevel.ADMINISTRATOR});
        invitationText = InviteUserHelper.getInvitationTextByUser(selectedUser, loginedUserId);
        final WebContext webContext = getContext();
        webContext.getHttpServletRequest().setAttribute("service", this);
        return webContext.forwardToString("/account/shareYourSites.jsp");
    }


    @RemoteMethod
    public String changeUserInfo(final AddEditPermissionsRequest request) throws Exception {
        final Integer loginedUserId = new UsersManager().getLogined().getUserId();

        if (!request.getUserId().equals(loginedUserId)) {
            final boolean setActive = InviteUserHelper.isSetNewRightActive(request.getUserId(), loginedUserId);
            removeUserRightsUtil.removeRight(request.getUserId(), false, false);
            ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
                public void run() {
                    InviteUserHelper.createRightsForExistingUser(request.getSelectedSites(), persistance.getUserById(request.getUserId()),
                            setActive, request.getInvitationText(), loginedUserId);
                }
            });
        }
        return new AccessPermissionsService().getUsersTable();
    }


    @RemoteMethod
    public String deleteUserRights(final List<Integer> selectedUsersId) throws Exception {
        final Integer loginedUserId = new UsersManager().getLogined().getUserId();

        if (removeUserRightsUtil.isOneSiteAdmin(selectedUsersId, loginedUserId)) {
            throw new OneSiteAdminException();
        }
        final RemoveUserRightsUtil.RemovedRightsResponse removedRightsResponse = new RemoveUserRightsUtil.RemovedRightsResponse();
        for (final Integer userId : selectedUsersId) {
            removedRightsResponse.add(removeUserRightsUtil.removeRight(userId, !loginedUserId.equals(userId), true));
        }
        if (selectedUsersId.contains(loginedUserId)) {
            throw new LoginedUserWithoutRightsException();
        }
        return new AccessPermissionsService().getUsersTable(removedRightsResponse);
    }


    @RemoteMethod
    public String inviteUser(final AddEditPermissionsRequest request) throws Exception {
        final User user = new UsersManager().getLogined().getUser();

        try {
            new EmailChecker().execute(request.getEmail().toLowerCase());
        } catch (Exception exception) {
            return "wrongEmail";
        }

        if (InviteUserHelper.hasAccessToUserSites(request.getEmail().toLowerCase(), request.getSelectedSites())) {
            return "userExist";
        }

        final User existingUser = persistance.getUserByEmail(request.getEmail().toLowerCase());
        final String invitationText = InviteUserHelper.createInvitationText(request);
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                if (existingUser != null) {
                    InviteUserHelper.createRightsForExistingUser(request.getSelectedSites(), existingUser, false, invitationText, user.getUserId());
                } else {
                    InviteUserHelper.createNewUser(request, invitationText, false, user.getUserId());
                }
            }
        });
        InviteUserHelper.sendMessageForInvitedUser(request.getEmail().toLowerCase(), invitationText, user.getUserId());
        return new AccessPermissionsService().getUsersTable();
    }

    @RemoteMethod
    public void resendInvitation(final String email) {
        User user = ServiceLocator.getPersistance().getUserByEmail(email);
        final List<UserOnSiteRight> rights = InviteUserHelper.getInactiveRightsWithParentUserIdByUser(user);
        for (UserOnSiteRight right : rights) {
            InviteUserHelper.sendMessageForInvitedUser(email.toLowerCase(), right.getInvitationText(),
                    right.getRequesterUserId());
        }
    }

    public List<Site> getSelectedUserSites() {
        return selectedUserSites;
    }

    public User getLoginedUser() {
        return loginedUser;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public boolean isDisableWindow() {
        return disableWindow;
    }

    public String getInvitationText() {
        return invitationText;
    }

    private User loginedUser;
    private boolean disableWindow;
    private User selectedUser;
    private List<Site> selectedUserSites;
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final RemoveUserRightsUtil removeUserRightsUtil = new RemoveUserRightsUtil();
    private String invitationText;
}