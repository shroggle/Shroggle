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
package com.shroggle.util.html;

import com.shroggle.util.MockJspWriter;
import com.shroggle.util.PageContextMock;
import junit.framework.Assert;
import net.sourceforge.stripes.mock.MockHttpServletResponse;
import org.junit.Test;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author Artem Stasuk (artem)
 *         </p>
 *         Date: 13 вер 2008
 */
public class NoCacheTagTest {

    @Test
    public void doStartTag() throws JspException {
        NoCacheTag noCacheTag = new NoCacheTag();
        final MockJspWriter mockJspWriter = new MockJspWriter(0, false);
        PageContextMock pageContextMock = new PageContextMock(mockJspWriter);
        pageContextMock.setResponse(new MockHttpServletResponse());
        noCacheTag.setPageContext(pageContextMock);
        Assert.assertEquals(Tag.SKIP_BODY, noCacheTag.doStartTag());
    }

}
