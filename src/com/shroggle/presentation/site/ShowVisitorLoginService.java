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

import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.entity.Widget;
import com.shroggle.exception.WidgetNotFoundException;
import com.shroggle.logic.accessibility.UserRightManager;
import com.shroggle.logic.user.UsersManager;
import com.shroggle.presentation.ActionUtil;
import com.shroggle.presentation.ServiceWithExecutePage;
import com.shroggle.util.MD5;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.process.synchronize.annotation.SynchronizeByMethodParameter;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;

@RemoteProxy
public class ShowVisitorLoginService extends ServiceWithExecutePage {

    /**
     * @param widgetId - selected widget id
     * @param force    - if this parameter true login show always. If false login show only if visitor not logined.
     * @return - html with login controls
     * @throws IOException
     * @throws ServletException
     */

    @RemoteMethod
    @SynchronizeByMethodParameter(entityClass = Widget.class)
    public String execute(final int widgetId, final boolean force) throws IOException, ServletException {
        this.widgetId = widgetId;
        widget = persistance.getWidget(widgetId);
        if (widget == null) {
            throw new WidgetNotFoundException("Cannot find widget by id " + widgetId);
        }
        final Cookie[] cookies = getContext().getHttpServletRequest().getCookies();
        final Site site = widget.getSite();
        if (cookies != null) {
            final Cookie crypted = ActionUtil.findCookie(cookies, "sh_cpt" + site.getSiteId());
            final Cookie visitorId = ActionUtil.findCookie(cookies, "sh_vid" + site.getSiteId());
            if (crypted != null && visitorId != null && !force) {
                //Getting our user from cookie
                user = persistance.getUserById(Integer.parseInt(visitorId.getValue()));

                //Check for rights.
                if (user == null || persistance.getUserOnSiteRightByUserAndSiteId(user.getUserId(), site.getSiteId()) == null) {
                    return executePage("/site/render/widgetLogin.jsp");
                }

                //Checking if user md5(login + password) matches the same form cookie
                if (MD5.crypt(user.getEmail() + user.getPassword()).equals(crypted.getValue())) {
                    //If so - login our user
                    new UsersManager().login(user.getUserId());
                }
            }
        }

        user = new UsersManager().getLoginedUser();
        if (user != null && !force) {
            final boolean isVisitorLogined = user != null && new UserRightManager(user).toSite(site) != null;

            if (isVisitorLogined) {
                return executePage("/site/render/visitorLogined.jsp");
            } else {
                return executePage("/site/render/widgetLogin.jsp");
            }
        }
        return executePage("/site/render/widgetLogin.jsp");
    }

    @RemoteMethod
    @SynchronizeByMethodParameter(entityClass = Widget.class)
    public String executeForRegistration(final int widgetId) throws IOException, ServletException {
        returnToRegistration = true;
        return execute(widgetId, false);
    }

    @RemoteMethod
    @SynchronizeByMethodParameter(entityClass = Widget.class)
    public String executeForChildSiteRegistration(final int widgetId) throws IOException, ServletException {
        returnToChildSiteRegistration = true;
        return execute(widgetId, true);
    }

    @RemoteMethod
    @SynchronizeByMethodParameter(entityClass = Widget.class)
    public String executeForManageVotes(final int widgetId, final int formId) throws IOException, ServletException {
        returnToManageVotes = true;
        this.formId = formId;
        return execute(widgetId, true);
    }

    @RemoteMethod
    @SynchronizeByMethodParameter(entityClass = Widget.class)
    public String executeForGalleryWithForm(final int widgetId, final int formId,
                                            final Integer shouldBeRegisteredFromRightFormId) throws IOException, ServletException {
        returnToGallery = true;
        this.formId = formId;
        this.shouldBeRegisteredFromRightFormId = shouldBeRegisteredFromRightFormId;
        return execute(widgetId, true);
    }

    @RemoteMethod
    @SynchronizeByMethodParameter(entityClass = Widget.class)
    public String executeForShoppingCart(final int widgetId, final int formId) throws IOException, ServletException {
        returnToShoppingCart = true;
        this.formId = formId;
        return execute(widgetId, true);
    }

    @RemoteMethod
    @SynchronizeByMethodParameter(entityClass = Widget.class)
    public String executeForGallery(final int widgetId) throws IOException, ServletException {
        returnToGallery = true;
        return execute(widgetId, true);
    }

    @RemoteMethod
    @SynchronizeByMethodParameter(entityClass = Widget.class)
    public String executeForForum(final int widgetId) throws IOException, ServletException {
        returnToForum = true;
        return execute(widgetId, true);
    }

    @RemoteMethod
    @SynchronizeByMethodParameter(entityClass = Widget.class)
    public String executeForBlog(final int widgetId) throws IOException, ServletException {
        returnToBlog = true;
        return execute(widgetId, true);
    }

    public User getVisitor() {
        return user;
    }

    public int getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    public void setVisitor(User user) {
        this.user = user;
    }

    public Widget getWidget() {
        return widget;
    }

    public boolean isReturnToRegistration() {
        return returnToRegistration;
    }

    public boolean isReturnToChildSiteRegistration() {
        return returnToChildSiteRegistration;
    }

    public boolean isReturnToManageVotes() {
        return returnToManageVotes;
    }

    public boolean isReturnToGallery() {
        return returnToGallery;
    }

    public Integer getFormId() {
        return formId;
    }

    public boolean isReturnToForum() {
        return returnToForum;
    }

    public boolean isReturnToBlog() {
        return returnToBlog;
    }

    public Integer getShouldBeRegisteredFromRightFormId() {
        return shouldBeRegisteredFromRightFormId;
    }

    public boolean isReturnToShoppingCart() {
        return returnToShoppingCart;
    }

    private final Persistance persistance = ServiceLocator.getPersistance();
    private final SessionStorage sessionStorage = ServiceLocator.getSessionStorage();
    private int widgetId;
    private Integer formId;
    private Integer shouldBeRegisteredFromRightFormId;
    private User user;
    private Widget widget;
    private boolean returnToRegistration;
    private boolean returnToChildSiteRegistration;
    private boolean returnToManageVotes;
    private boolean returnToGallery;
    private boolean returnToForum;
    private boolean returnToBlog;
    private boolean returnToShoppingCart;

}