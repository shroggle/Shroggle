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
package com.shroggle.stresstest.util.string;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Artem Stasuk
 */
public class StringBuilderRun {

    public static void main(final String[] args) {
        final long start = System.currentTimeMillis();
        final Set<Integer> strings = new HashSet<Integer>();
        for (int i = 0; i < 1000000; i++) {
            final StringBuilder string = new StringBuilder(1024);
            string.append("<request call=\"BillingInfo.save\" response-value-version=\"1\" response-value-depth=\"1\">\n");
            string.append("  <Session><Id>").append(Math.random()).append("</Id></Session>\n");
            string.append("  <BillingInfo>\n");
            string.append("    <Name>" + "" + "</Name>\n");
            string.append("    <Number>").append(Math.random()).append("</Number>\n");
            string.append("    <PaymentType>" + "CREDIT_CARD" + "</PaymentType>\n");
            string.append("    <CVV>").append(Math.random()).append("</CVV>\n");
            string.append("    <ExpirationDate>").append(Math.random()).append("</ExpirationDate>\n");
            string.append("    <Address1>").append(Math.random()).append("</Address1>\n");
            string.append("    <Address2>").append(Math.random()).append("</Address2>\n");
            string.append("    <City>").append(Math.random()).append("</City>\n");
            string.append("    <State>").append(Math.random()).append("</State>\n");
            string.append("    <Zip>").append(Math.random()).append("</Zip>\n");
            string.append("    <Country>").append(Math.random()).append("</Country>\n");
            string.append("  </BillingInfo>\n");
            string.append("</request>");
            strings.add(string.toString().hashCode());
        }
        System.out.println(strings.hashCode());
        System.out.println("Time " + (System.currentTimeMillis() - start) / 1000l + " sec");
    }

}