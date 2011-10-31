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
import com.shroggle.entity.User;
import com.shroggle.entity.Widget;
import com.shroggle.entity.WidgetItem;
import com.shroggle.logic.site.page.PageManager;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletException;
import java.io.IOException;


@RunWith(value = TestRunnerWithMockServices.class)
public class ShowSelectWidgetImageServiceTest extends TestBaseWithMockService {

    private ShowSelectWidgetImageService service = new ShowSelectWidgetImageService();

    @Test
    public void execute() throws IOException, ServletException {
        User account = new User();
        persistance.putUser(account);


        PageManager pageVersion = new PageManager(TestUtil.createPageAndSite());

        Widget widget = TestUtil.createWidgetItem();
        persistance.putWidget(widget);
        pageVersion.addWidget(widget);


        service.execute(widget.getWidgetId());
    }

}