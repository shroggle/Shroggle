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

package com.shroggle.util.persistance;

import com.shroggle.util.ServiceLocator;

import javax.servlet.*;
import java.io.IOException;

public class PersistanceContextFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(
            final ServletRequest req, final ServletResponse resp,
            final FilterChain chain) throws ServletException, IOException {
        ServiceLocator.getPersistance().inContext(new PersistanceContext<Object>() {

            public Object execute() {
                try {
                    chain.doFilter(req, resp);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ServletException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }

        });
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
