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

package com.shroggle.presentation.user.create;

import com.shroggle.entity.User;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.site.LoginedUserInfo;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByAllEntity;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperties;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByClassProperty;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByLoginUser;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Tolik, Dima, Artem
 * @see com.shroggle.presentation.account.accessPermissions.AddEditPermissionsService
 */
@UrlBinding("/account/createUser.action")
public class CreateUserAction extends Action implements LoginedUserInfo {

    /**
     * @return - show page for create account
     */
    @SynchronizeByLoginUser
    @DefaultHandler
    public Resolution show() {
        state = new CreateUserState(request);
        request.setMode(CreateUserMode.NEW);
        return showInternal();
    }

    /**
     * It's general method.
     *
     * @return - show create account page.
     */
    private Resolution showInternal() {
        loginUser = new UsersManager().getLoginedUser();
        return resolutionCreator.forwardToUrl("/account/createUser.jsp");
    }

    /**
     * Need link
     * /account/createUser.action
     * ?showForInvited=true
     * &request.confirmCode=X
     * &request.userId=X
     * &request.invitedUserId=X
     *
     * @return - show page for accept user settings
     */
    @SynchronizeByLoginUser
    @SynchronizeByClassProperties({
        @SynchronizeByClassProperty(
                entityClass = User.class,
                entityIdFieldPath = "request.invitedUserId"),
        @SynchronizeByClassProperty(
                entityClass = User.class,
                entityIdFieldPath = "request.userId")})
    public Resolution showForInvited() {
        state = new CreateUserState(request);
        request.setNotWantNewUser(true);
        request.setAgree(true);
        request.setMode(CreateUserModeCreator.get(request, state));
        request.getMode().getShow().execute(this);
        return showInternal();
    }

    /**
     * Step by step:
     * <p/>
     * 0. validate user
     * 1. get or create user
     * 2. get or create or move right and activate it
     * 3. login user
     * 4. redirect to need page
     *
     * @return - resolution to next page
     */
    @SynchronizeByAllEntity(
            entityClass = User.class)
    public Resolution execute() {
        state = new CreateUserState(request);
        final CreateUserMode mode = CreateUserModeCreator.next(request, state);
        request.setMode(mode);

        mode.getValidate().execute(this);
        if (getContext().getValidationErrors().size() > 0) {
            if (mode == CreateUserMode.INVITED_NEW){
                return showForInvited();
            } else {
                return showInternal();
            }
        }

        persistanceTransaction.execute(new PersistanceTransactionContext<Void>() {

            @Override
            public Void execute() {
                mode.getCreateUser().execute(CreateUserAction.this);
                mode.getRight().execute(state);
                return null;
            }

        });
        mode.getLogin().execute(this);
        return mode.getRedirect().execute(state);
    }

    public void setRequest(final CreateUserRequest request) {
        this.request = request;
    }

    public CreateUserRequest getRequest() {
        return request;
    }

    public User getLoginUser() {
        return loginUser;
    }

    /**
     * This method used only in logic code for action.
     * It don't have public modificator.
     *
     * @return - state
     */
    CreateUserState getState() {
        return state;
    }

    void setState(final CreateUserState state) {// for tests. Tolik
        this.state = state;
    }

    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private CreateUserRequest request = new CreateUserRequest();
    private CreateUserState state;
    private User loginUser;

}
