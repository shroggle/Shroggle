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

package com.shroggle.presentation;

import javax.servlet.http.Cookie;

/**
 * @author Stasuk Artem
 */
public class ActionUtil {

    public static Cookie findCookie(Action actionBean, String cookieName) {
        return findCookie(actionBean.getContext().getRequest().getCookies(), cookieName);
    }

    public static Cookie findCookie(Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie != null && cookie.getName() != null && cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static Integer getPageVisitorId(Cookie[] cookies) {
        Cookie cookie = findCookie(cookies, "sh_pvid");
        if (cookie != null) {
            return Integer.parseInt(cookie.getValue());
        } else {
            return null;
        }

    }

}
