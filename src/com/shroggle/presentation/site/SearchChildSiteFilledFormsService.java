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

import com.shroggle.entity.FilledForm;
import com.shroggle.entity.FormItemName;
import com.shroggle.entity.Site;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.logic.form.FilledFormManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class SearchChildSiteFilledFormsService extends AbstractService {

    @RemoteMethod
    public String execute(final String searchKey, final int networkSiteId) throws Exception {
        new UsersManager().getLogined();
        final Site site = persistance.getSite(networkSiteId);
        if (site == null) {
            throw new SiteNotFoundException("Cannot find site by Id = " + networkSiteId);
        }
        this.parentSiteId = networkSiteId;
        filledForms = selectFilledFormsByKey(FilledFormManager.getFilledFormsByNetworkSiteId(networkSiteId), searchKey);
        getContext().getHttpServletRequest().setAttribute("siteName", ServiceLocator.getPersistance().getSite(parentSiteId).getSiteId());
        getContext().getHttpServletRequest().setAttribute("parentSiteId", parentSiteId);
        getContext().getHttpServletRequest().setAttribute("filledForms", filledForms);
        return getContext().forwardToString("/account/networkRegistrantsTable.jsp");
    }


    private List<FilledForm> selectFilledFormsByKey(final List<FilledForm> allFilledForms, final String searchKey) {
        Set<FilledForm> keyFilledForm = new HashSet<FilledForm>();
        if (!searchKey.trim().isEmpty()) {
            String[] keys = searchKey.split(" ");
            for (String key : keys) {
                for (FilledForm filledForm : allFilledForms) {
                    String firstName = FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.FIRST_NAME + "_FN"));
                    String lastName = FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.LAST_NAME + "_FN"));
                    String email = FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.EMAIL + "_FN"));
                    if (firstName.toLowerCase().contains(key.toLowerCase()) ||
                            lastName.toLowerCase().contains(key.toLowerCase()) ||
                            email.toLowerCase().contains(key.toLowerCase())) {
                        keyFilledForm.add(filledForm);
                    }
                }
            }
            if (searchKey.equals("not specified")) {
                for (FilledForm filledForm : allFilledForms) {
                    String firstName = FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.FIRST_NAME + "_FN"));
                    String lastName = FilledFormManager.getFilledFormItemValueByItemName(filledForm, FilledFormManager.getFormInternational().get(FormItemName.LAST_NAME + "_FN"));
                    if ((firstName.trim().isEmpty() || lastName.trim().isEmpty())) {
                        keyFilledForm.add(filledForm);
                    }
                }
            }
            return new ArrayList<FilledForm>(keyFilledForm);
        } else {
            return allFilledForms;
        }
    }

    public int getParentSiteId() {
        return parentSiteId;
    }

    public List<FilledForm> getFilledForms() {
        return filledForms;
    }

    private List<FilledForm> filledForms;
    private int parentSiteId;
    private final Persistance persistance = ServiceLocator.getPersistance();
}