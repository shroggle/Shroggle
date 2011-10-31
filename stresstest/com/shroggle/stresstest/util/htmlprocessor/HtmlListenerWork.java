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
package com.shroggle.stresstest.util.htmlprocessor;

import com.shroggle.util.html.processor.HtmlFlatMediaBlock;
import com.shroggle.util.html.processor.HtmlListener;
import com.shroggle.util.html.processor.HtmlMediaBlock;

/**
 * @author Artem Stasuk
 */
class HtmlListenerWork implements HtmlListener {

    @Override
    public void onMediaBlock(final HtmlMediaBlock block) {
        block.setHtml("" + Math.random());
    }

    @Override
    public void onFlatMediaBlock(final HtmlFlatMediaBlock block) {
        block.setId("" + Math.random());
        block.setHtml(Math.random() + "" + Math.random());
    }

}