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
package com.shroggle.presentation.site.render.childSiteRegistration;

import com.shroggle.presentation.AbstractService;
import com.shroggle.entity.Widget;
import com.shroggle.entity.FilledForm;
import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.Site;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Balakirev Anatoliy
 *         Date: 13.08.2009
 */
@RemoteProxy
public class DeleteChildSiteAndFilledFormService extends AbstractService {

    @RemoteMethod
    public String execute(final int widgetId, final int filledFormId) throws IOException, ServletException {

        final FilledForm filledForm = persistance.getFilledFormById(filledFormId);
        if (filledForm != null) {
            persistanceTransaction.execute(new Runnable() {
                public void run() {
                    final Integer childSiteSettingsId = filledForm.getChildSiteSettingsId();
                    final ChildSiteSettings settings = persistance.getChildSiteSettingsById(childSiteSettingsId);
                    if (settings != null) {
                        final Site childSite = settings.getSite();
                        persistance.removeChildSiteSettings(settings);
                        persistance.removeSite(childSite);
                    }
                    persistance.removeFilledForm(filledForm);
                }
            });
        }
        final Widget widget = ServiceLocator.getPersistance().getWidget(widgetId);
        return RenderChildSiteRegistration.execute(widget, createRenderContext(false));
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}
