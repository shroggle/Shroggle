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

package com.shroggle.presentation.site;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
public class VisitorLoginRequest {

    @RemoteProperty
    private String login;

    @RemoteProperty
    private String password;

    @RemoteProperty
    private boolean remember;

    @RemoteProperty
    // If we got formId here, then service will check that this visitor is registered form right form and if he's not
    // then we will show a registration form instead of reloading page.
    private Integer registrationFormId;

    public Integer getRegistrationFormId() {
        return registrationFormId;
    }

    public void setRegistrationFormId(Integer registrationFormId) {
        this.registrationFormId = registrationFormId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean getRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }
}
