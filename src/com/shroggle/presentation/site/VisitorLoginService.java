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

import com.shroggle.entity.PageVisitor;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.Widget;
import com.shroggle.exception.*;
import com.shroggle.logic.user.UserManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.AbstractService;
import com.shroggle.presentation.ActionUtil;
import com.shroggle.util.MD5;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.international.International;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceTransaction;
import org.directwebremoting.WebContext;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.Locale;


@RemoteProxy
public class VisitorLoginService extends AbstractService {

    /**
     * @param request              - t
     * @param widgetId             - t
     * @param returnToRegistration - if ture then will return to registration to complete it. Will not check for rights.
     * @return - t
     * @throws ServletException - t
     * @throws IOException      - t
     */
    @RemoteMethod
    public String execute(
            final VisitorLoginRequest request, final int widgetId,
            final boolean returnToRegistration) throws ServletException, IOException {
        final Widget widget = persistance.getWidget(widgetId);
        if (widget == null) {
            throw new WidgetNotFoundException("Cannot find widget by id=" + widgetId);
        }
        final Site site = widget.getSite();

        final UserManager userManager;
        try {
            userManager = usersManager.login(request.getLogin(), request.getPassword(), site.getSiteId());
        } catch (final UserNotFoundException exception) {
            throw new VisitorNotFoundException(
                    international.get("VisitorNotFoundException"));
        } catch (final UserNotActivatedException exception) {
            throw new UserNotActivatedException(
                    international.get("UserNotActivatedException"));
        } catch (final UserWithWrongPasswordException exception) {
            throw new UserWithWrongPasswordException(
                    international.get("UserWithWrongPasswordException"));
        } catch (final UserWithoutRightException exception) {
            if (!returnToRegistration) {
                throw new VisitorNotFoundException(
                        international.get("VisitorNotFoundException"));
            } else {
                return getContext().forwardToString(
                        "/site/widgetRegistration.action?fillOutFormCompletely=true&widgetId=" + widgetId + "&prefillWithEmail=" + request.getLogin());
            }
        }

        final User user = userManager.getUser();

        if (request.getRegistrationFormId() != null) {
            if (!new UsersManager().isUserLoginedAndRegisteredFromRightForm(request.getRegistrationFormId())) {
                return getContext().forwardToString(
                        "/site/widgetRegistration.action?widgetId=" + widgetId + "&formId=" +
                                request.getRegistrationFormId() + "&showSpecificFormRegisterMessage=true");
            }
        }

        final Integer pageVisitorId = ActionUtil.getPageVisitorId(getContext().getHttpServletRequest().getCookies());

        //Linking PageVisitor and User on login attempt
        if (pageVisitorId != null) {
            final PageVisitor pageVisitor = persistance.getPageVisitorById(pageVisitorId);
            if (pageVisitor != null && pageVisitor.getUserId() == null) {
                persistanceTransaction.execute(new Runnable() {
                    public void run() {
                        pageVisitor.setUserId(user.getUserId());
                    }
                });
            }
        }

        final WebContext context = getContext();
        if (request.getRemember()) {
            final Cookie crypted = new Cookie("sh_cpt", MD5.crypt(user.getEmail() + user.getPassword()));
            final Cookie visitorId = new Cookie("sh_vid", "" + user.getUserId());
            context.getHttpServletResponse().addCookie(crypted);
            context.getHttpServletResponse().addCookie(visitorId);
        }

        return "ok";
    }

    private final UsersManager usersManager = new UsersManager();
    private final Persistance persistance = ServiceLocator.getPersistance();
    private final International international = ServiceLocator.getInternationStorage().get("widgetLogin", Locale.US);
    private final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();

}
