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
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.process.synchronize.SynchronizeRequest;
import com.shroggle.util.process.synchronize.SynchronizeRequestEntity;
import com.shroggle.util.process.synchronize.SynchronizeMethod;
import com.shroggle.util.process.synchronize.SynchronizeContext;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import com.shroggle.util.persistance.PersistanceTransaction;
import com.shroggle.entity.FilledForm;
import com.shroggle.entity.Site;
import com.shroggle.entity.ChildSiteSettings;
import com.shroggle.entity.Widget;
import com.shroggle.logic.site.SiteManager;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Balakirev Anatoliy
 */
@RemoteProxy
public class OptOutFilledFormFromNetworkService extends AbstractService {

    @RemoteMethod
    public String execute(final int widgetId, final int filledFormId) throws IOException, ServletException {
        final FilledForm filledForm = persistance.getFilledFormById(filledFormId);
        if (filledForm != null) {
            final Integer childSiteSettingsId = filledForm.getChildSiteSettingsId();
            final ChildSiteSettings settings = persistance.getChildSiteSettingsById(childSiteSettingsId);
            if (settings != null) {
                executeInternal(settings);
            }
        }
        final Widget widget = ServiceLocator.getPersistance().getWidget(widgetId);
        return RenderChildSiteRegistration.execute(widget, createRenderContext(false));
    }

    private void executeInternal(final ChildSiteSettings settings) {
        try {
            final SynchronizeRequest synchronizeRequest = new SynchronizeRequestEntity(
                    ChildSiteSettings.class, SynchronizeMethod.WRITE, settings.getChildSiteSettingsId());

            ServiceLocator.getSynchronize().execute(synchronizeRequest, new SynchronizeContext<Void>() {
                public Void execute() {
                    Site childSite = settings.getSite();
                    if (childSite != null) {
                        new SiteManager(childSite).disconnectFromNetwork();
                    } else {
                        persistanceTransaction.execute(new PersistanceTransactionContext<Void>() {
                            @Override
                            public Void execute() {
                                final FilledForm filledForm = persistance.getFilledFormById(settings.getFilledFormId());
                                if (filledForm != null) {
                                    filledForm.setChildSiteSettingsId(null);
                                }
                                ServiceLocator.getPersistance().removeChildSiteSettings(settings);
                                return null;
                            }
                        });
                    }
                    return null;
                }
            });

        } catch (Exception exception) {
            Logger.getLogger(OptOutFilledFormFromNetworkService.class.getName()).log(Level.SEVERE, "Can't execute OptOutFilledFormFromNetworkService ", exception);
        }
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
}
