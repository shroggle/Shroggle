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
package com.shroggle.util.process;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * @author Balakirev Anatoliy
 *         Date: 21.09.2009
 */
public class ProcessResponse {

    private String responseText;

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public void createResponseText(InputStream errorStream, InputStream outputStream) {
        StringBuilder responseString = new StringBuilder();
        responseString.append(createResponse(errorStream));
        responseString.append("\n");
        responseString.append(createResponse(outputStream));
        setResponseText(responseString.toString());
    }

    private String createResponse(InputStream stream) {
        StringBuilder responseString = new StringBuilder();
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(stream);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseString.append(line);
                responseString.append("\n");
            }
        } catch (Exception e) {
            Logger.getLogger(ProcessResponse.class.getName()).log(Level.SEVERE, "Can`t create response text by input stream.");
        }
        return responseString.toString();
    }
}
