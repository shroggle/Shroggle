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

import com.shroggle.util.html.processor.HtmlProcessor;
import com.shroggle.util.html.processor.simple.HtmlProcessorRegex;
import com.shroggle.util.html.processor.simple.HtmlProcessorSimple;

/**
 * @author Artem Stasuk
 */
public class HtmlProcessorBench {

    public static void main(String[] args) {
        visit(new HtmlProcessorSimple());
        visit(new HtmlProcessorRegex());
        visitWithReplace(new HtmlProcessorSimple());
        visitWithReplace(new HtmlProcessorRegex());
    }

    public static void visit(final HtmlProcessor processor) {
        final long start = System.currentTimeMillis();
        System.out.println("Start visit test " + processor.getClass());
        for (int i = 0; i < 100000; i++) {
            processor.execute(html, new HtmlListenerEmpty());
        }
        System.out.println("Finish " + (System.currentTimeMillis() - start) + " msec");
    }

    public static void visitWithReplace(final HtmlProcessor processor) {
        final long start = System.currentTimeMillis();
        System.out.println("Start replace test " + processor.getClass());
        for (int i = 0; i < 1000; i++) {
            processor.execute(html, new HtmlListenerWork());
        }
        System.out.println("Finish " + (System.currentTimeMillis() - start) + " msec");
    }

    private static final StringBuilder html = new StringBuilder(
            "It's <!-- MEDIA_BLOCK --> test text for defined speed <b class=\"mediaBlock\">33</b>" +
                    "for differend html processors. <!-- MEDIA_BLOCK --> fwerw re tr ewtre gter rh" +
                    " w3 234 234 23 rew eg we gr bre <!-- MEDIA_BLOCK --> fsfe wr ew we " +
                    "<b class=\"mediaBlock\">d</b>");

}
