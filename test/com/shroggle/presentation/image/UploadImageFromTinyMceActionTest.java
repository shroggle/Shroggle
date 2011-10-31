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

package com.shroggle.presentation.image;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.presentation.ResolutionMock;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Artem Stasuk
 */
@RunWith(TestRunnerWithMockServices.class)
public class UploadImageFromTinyMceActionTest {

    @Test
    public void executeWithoutFile() {
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertNull(action.getImage());
        Assert.assertNull(action.getPath());
        Assert.assertEquals("/tinymce/plugins/example/dialogResult.jsp", resolutionMock.getForwardToUrl());
    }

    @Test
    public void executeWithoutLogin() {
        final ResolutionMock resolutionMock = (ResolutionMock) action.execute();

        Assert.assertNull(action.getImage());
        Assert.assertNull(action.getPath());
        Assert.assertEquals("/tinymce/plugins/example/dialogResult.jsp", resolutionMock.getForwardToUrl());
    }

    private final UploadImageFromTinyMceAction action = new UploadImageFromTinyMceAction();

}