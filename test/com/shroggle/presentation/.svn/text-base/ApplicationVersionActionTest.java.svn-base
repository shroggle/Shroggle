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
/**
 *
 * @author Igor Kanshin
 */

import junit.framework.Assert;
import org.junit.Test;

public class ApplicationVersionActionTest extends TestAction<ApplicationVersionAction> {

    public ApplicationVersionActionTest() {
        super(ApplicationVersionAction.class);
    }

    @Test
    public void execute() throws Exception {
        fileSystem.setApplicationVersion("sardfa sdsfd  fasdf v1.12345beta adsf adskjf hasfh ahfkj afdshkj");

        actionOrService.execute();

        Assert.assertEquals("sardfa sdsfd  fasdf v1.12345beta adsf adskjf hasfh ahfkj afdshkj", actionOrService.getApplicationVersion());
    }

}