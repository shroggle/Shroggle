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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class JsonBuilder {

    private String jsonString = "";

    public void putItem(String itemName, String itemValue) {
        jsonString = jsonString + "\"" + itemName + "\"" + ":"
                + "\"" + itemValue + "\", ";
    }

    public void putArray(String arrayName, List<Map<String, String>> array) {
        jsonString = jsonString + "\"" + arrayName + "\":[";
        for (int i = 0; i < array.size(); i++) {
            jsonString = jsonString + "{";
            Map<String, String> items = array.get(i);
            int j = 0;
            for (Map.Entry<String, String> item : items.entrySet()) {
                jsonString = jsonString + "\"" + item.getKey() + "\"" + ":"
                        + "\"" + item.getValue() + "\"";
                if (j != items.size() - 1) {
                    jsonString = jsonString + ", ";
                }
                j++;
            }

            if (i != array.size() - 1) {
                jsonString = jsonString + "},";
            } else {
                jsonString = jsonString + "}";
            }
        }
        jsonString = jsonString + "],";
    }

    //Return's date that accepted by ext js
    public String constructDate(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

        return format.format(calendar.getTime());
    }

    public String getJSON() {
        return "{" + jsonString.substring(0, jsonString.length() - 1) + "}";
    }

}
