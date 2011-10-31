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

import com.shroggle.TestBaseWithMockService;
import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.TestUtil;
import com.shroggle.entity.Site;
import com.shroggle.entity.User;
import com.shroggle.exception.UserNotLoginedException;
import com.shroggle.presentation.MockWebContext;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.context.SessionStorage;
import net.sourceforge.stripes.mock.MockHttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;


@RunWith(value = TestRunnerWithMockServices.class)
public class GetWidgetByPageWithPositionServiceTest extends TestBaseWithMockService {

    private GetWidgetByPageWithPositionService service = new GetWidgetByPageWithPositionService();
    private SessionStorage sessionStorage = ServiceLocator.getSessionStorage();

    //todo add tests. Dmitry Solomadin.    

    @Test(expected = UserNotLoginedException.class)
    public void executeWithNotMyLogin() throws IOException, ServletException {
        User account2 = new User();
        persistance.putUser(account2);

        Site site = new Site();
        persistance.putSite(site);

        TestUtil.createPageAndSite();

        MockWebContext mockWebContext = (MockWebContext) service.getContext();
        mockWebContext.setHttpServletRequest(new MockHttpServletRequest("", ""));

        service.execute(0);
    }

    @Test(expected = UserNotLoginedException.class)
    public void executeWithoutLogin() throws IOException, ServletException {
        service.execute(0);
    }

}