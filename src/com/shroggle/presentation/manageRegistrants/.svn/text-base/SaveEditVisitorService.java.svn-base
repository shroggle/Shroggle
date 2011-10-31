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
package com.shroggle.presentation.manageRegistrants;

import com.shroggle.logic.form.FilledFormManager;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import com.shroggle.presentation.site.RegisteredVisitorInfo;
import com.shroggle.presentation.AbstractService;
import com.shroggle.entity.FilledFormItem;
import com.shroggle.entity.User;
import com.shroggle.entity.Site;
import com.shroggle.entity.FilledForm;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.logic.visitor.VisitorInfoGetter;
import com.shroggle.exception.UserNotFoundException;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.ServiceLocator;

import javax.servlet.ServletException;
import java.util.List;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class SaveEditVisitorService extends AbstractService{

    @RemoteMethod
    public RegisteredVisitorInfo execute(final int visitorId, final int siteId, final List<FilledFormItem> filledFormItems) throws IOException, ServletException {
        new UsersManager().getLogined();

        final User visitorToEdit = persistance.getUserById(visitorId);
        if (visitorToEdit == null) {
            throw new UserNotFoundException("Cannot find user by Id=" + visitorId);
        }

        final Site site = persistance.getSite(siteId);

        if (site == null) {
            throw new SiteNotFoundException("Cannot find site by Id=" + siteId);
        }

        final FilledForm filledRegistrationForm = FilledFormManager.getFirstRegistrationFilledFormForSite(visitorToEdit, site);
        ServiceLocator.getPersistanceTransaction().execute(new Runnable() {
            public void run() {
                FilledFormManager.updateFilledFormItems(filledFormItems, filledRegistrationForm);
            }
        });
        return new VisitorInfoGetter().execute(visitorToEdit, siteId);
    }

    private final Persistance persistance = ServiceLocator.getPersistance();

}
