package com.shroggle.presentation.site;

import com.shroggle.entity.Site;
import com.shroggle.entity.SiteType;
import com.shroggle.exception.SiteNotFoundException;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.Action;
import com.shroggle.presentation.ResolutionCreator;
import com.shroggle.presentation.account.dashboard.DashboardAction;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.PersistanceTransaction;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * @author Artem Stasuk
 */
@UrlBinding("/copyBlueprint.action")
public class CopyBlueprintAction extends Action {

    @DefaultHandler
    public Resolution execute() {
        final Site blueprint;
        try {
            final UserManager userManager = new UsersManager().getLogined();
            blueprint = userManager.getRight().getSiteRight().getSiteForEdit(blueprintId).getSite();
        } catch (final UserNotLoginedException exception) {
            return resolutionCreator.loginInUser(this);
        } catch (final SiteNotFoundException exception) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        if (blueprint.getType() != SiteType.BLUEPRINT) {
            return resolutionCreator.redirectToAction(DashboardAction.class);
        }

        persistanceTransaction.execute(new Runnable() {

            @Override
            public void run() {
                ServiceLocator.getSiteCopierBlueprint().execute(blueprint);
            }

        });

        return resolutionCreator.redirectToAction(DashboardAction.class);
    }

    public void setBlueprintId(final int blueprintId) {
        this.blueprintId = blueprintId;
    }

    private int blueprintId;
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
    private final ResolutionCreator resolutionCreator = ServiceLocator.getResolutionCreator();

}
