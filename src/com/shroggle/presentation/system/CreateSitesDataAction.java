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
package com.shroggle.presentation.system;

import com.shroggle.logic.system.*;
import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/system/createLotOfSitesData.action")
public class CreateSitesDataAction extends Action {

    @DefaultHandler
    public Resolution show() {
        final CreateSitesStatus status = ServiceLocator.getCreateSitesStatus();
        info = status == null ? "none" : status.get();

        return new ForwardResolution("/system/createSitesData.jsp");
    }

    public Resolution simple() {
        ServiceLocator.setCreateSitesStatus(new CreateSitesStatus());

        ServiceLocator.setCreateSitesMethod(
                new CreateSitesInThreadMethod(
                        new CreateSitesManyMethod(
                                new CreateSitesTransactionMethod(
                                        new CreateSitesSimpleMethod(userId, urlPrefix)), count)));

        ServiceLocator.getCreateSitesMethod().execute(ServiceLocator.getCreateSitesStatus());

        return show();
    }

    public Resolution network() {
        ServiceLocator.setCreateSitesStatus(new CreateSitesStatus());

        ServiceLocator.setCreateSitesMethod(
                new CreateSitesInThreadMethod(
                        new CreateSitesManyMethod(
                                new CreateSitesTransactionMethod(
                                        new CreateSitesNetworkChildMethod(filledFormId, blueprintId, urlPrefix)), count)));

        ServiceLocator.getCreateSitesMethod().execute(ServiceLocator.getCreateSitesStatus());

        return show();
    }

    public Resolution blueprint() {
        ServiceLocator.setCreateSitesStatus(new CreateSitesStatus());

        ServiceLocator.setCreateSitesMethod(
                new CreateSitesInThreadMethod(
                        new CreateSitesManyMethod(
                                new CreateSitesTransactionMethod(
                                        new CreateSitesBlueprintChildMethod(blueprintId, urlPrefix)), count)));

        ServiceLocator.getCreateSitesMethod().execute(ServiceLocator.getCreateSitesStatus());

        return show();
    }

    public Resolution copy() {
        ServiceLocator.setCreateSitesStatus(new CreateSitesStatus());

        ServiceLocator.setCreateSitesMethod(
                new CreateSitesInThreadMethod(
                        new CreateSitesManyMethod(
                                new CreateSitesTransactionMethod(
                                        new CreateSitesCopyMethod(blueprintId, urlPrefix)), count)));

        ServiceLocator.getCreateSitesMethod().execute(ServiceLocator.getCreateSitesStatus());

        return show();
    }

    public void setBlueprintId(int blueprintId) {
        this.blueprintId = blueprintId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public String getInfo() {
        return info;
    }

    public void setFilledFormId(int filledFormId) {
        this.filledFormId = filledFormId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int filledFormId;
    private String urlPrefix;
    private String info;
    private int count;
    private int blueprintId;
    private int userId;

}