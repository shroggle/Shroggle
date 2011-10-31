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
package com.shroggle.presentation.site.render;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.util.ServiceLocator;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balakirev Anatoliy
 */
@RunWith(TestRunnerWithMockServices.class)
public class RenderGeneratorMetaTagTest {

    @Test
    public void testExecute() {
        ServiceLocator.getConfigStorage().get().setApplicationUrl("testApplicationUrl");
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!-- PAGE_HEADER -->");
        final RenderGeneratorMetaTag renderGeneratorMetaTag = new RenderGeneratorMetaTag();
        renderGeneratorMetaTag.execute(null, stringBuilder);

        Assert.assertEquals("<meta name=\"generator\" content=\"" + ServiceLocator.getConfigStorage().get().getApplicationUrl() + "\" />\n" +
                "<!-- PAGE_HEADER -->", stringBuilder.toString());
    }

}
