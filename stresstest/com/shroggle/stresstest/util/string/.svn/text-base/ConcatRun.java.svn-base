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
public class ConcatRun {

    public static void main(final String[] args) {
        final long start = System.currentTimeMillis();
        final Set<Integer> strings = new HashSet<Integer>();
        for (int i = 0; i < 1000000; i++) {
            final String string = "<request call=\"BillingInfo.save\" response-value-version=\"1\" response-value-depth=\"1\">\n" +
                    "  <Session><Id>" + Math.random() + "</Id></Session>\n" +
                    "  <BillingInfo>\n" +
                    "    <Name>" + "" + "</Name>\n" +
                    "    <Number>" + Math.random() + "</Number>\n" +
                    "    <PaymentType>" + "CREDIT_CARD" + "</PaymentType>\n" +
                    "    <CVV>" + Math.random() + "</CVV>\n" +
                    "    <ExpirationDate>" + Math.random() + "</ExpirationDate>\n" +
                    "    <Address1>" + Math.random() + "</Address1>\n" +
                    "    <Address2>" + Math.random() + "</Address2>\n" +
                    "    <City>" + Math.random() + "</City>\n" +
                    "    <State>" + Math.random() + "</State>\n" +
                    "    <Zip>" + Math.random() + "</Zip>\n" +
                    "    <Country>" + Math.random() + "</Country>\n" +
                    "  </BillingInfo>\n" +
                    "</request>";
            strings.add(string.hashCode());
        }
        System.out.println(strings.hashCode());
        System.out.println("Time " + (System.currentTimeMillis() - start) / 1000l + " sec");
    }

}
