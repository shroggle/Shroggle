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
package com.shroggle.presentation.site.render.shoppingCart;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.RemoteMethod;
import com.shroggle.presentation.AbstractService;
import com.shroggle.entity.Widget;
import com.shroggle.entity.SiteShowOption;
import com.shroggle.util.ServiceLocator;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author dmitry.solomadin
 */
@RemoteProxy
public class RenderShoppingCartService extends AbstractService {

    @RemoteMethod
    public String execute(final int widgetId, final SiteShowOption siteShowOption,
                          final String shoppingCartCookieValue) throws IOException, ServletException {
        final Widget widget = ServiceLocator.getPersistance().getWidget(widgetId);
        return RenderShoppingCart.executeWithCookies(widget, siteShowOption, shoppingCartCookieValue, createRenderContext(false));
    }

}
