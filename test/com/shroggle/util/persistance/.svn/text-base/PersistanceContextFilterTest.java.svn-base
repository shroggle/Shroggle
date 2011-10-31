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

import com.shroggle.PersistanceMock;
import com.shroggle.util.ServiceLocator;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author Artem Stasuk (artem)
 *         </p>
 *         Date: 13 вер 2008
 */
public class PersistanceContextFilterTest {

    @Test
    public void doFilter() throws IOException, ServletException {
        final PersistanceMock persistanceMock = new PersistanceMock();
        ServiceLocator.setPersistance(persistanceMock);
        final PersistanceContextFilter persistanceContextFilter = new PersistanceContextFilter();
        persistanceContextFilter.init(null);
        persistanceContextFilter.doFilter(null, null, new FilterChain() {

            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse)
                    throws IOException, ServletException {
                persistanceMock.checkInContext();
            }

        });
        persistanceContextFilter.destroy();
    }

}
