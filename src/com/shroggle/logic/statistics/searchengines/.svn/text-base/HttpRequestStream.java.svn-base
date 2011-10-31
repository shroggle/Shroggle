/*********************************************************************
 *                                                                   *
 * Copyright (c) 2002-2006 by Survey Software Services, Inc.         *
 * All rights reserved.                                              *
 *                                                                   *
 * This computer program is protected by copyright law and           *
 * international treaties. Unauthorized reproduction or distribution *
 * of this program, or any portion of it, may result in severe civil *
 * and criminal penalties, and will be prosecuted to the maximum     *
 * extent possible under the law.                                    *
 *                                                                   *
 *********************************************************************/
package com.shroggle.logic.statistics.searchengines;


import java.io.*;
import java.net.URLDecoder;
import java.util.*;


public class HttpRequestStream {
    HttpRequestStream(InputStream stream) throws IOException {
        _stream = new BufferedInputStream(stream);

        StringTokenizer st = new StringTokenizer(readHeaderLine());
        _command = st.nextToken();
        _uri = st.nextToken();

        if (!_command.equals("GET") && !_command.equals("POST") && !_command.equals("PUT")) {
            throw new IOException("FORM Method does not support:" + _command);
        }
        readHeaders();
        readContent();
    }


    Reader getReader() {
        return _reader;
    }

    String getCommand() {
        return _command;
    }

    String getURI() {
        return _uri;
    }


    String getHeader(String name) {
        return _headers.get(name.toUpperCase());
    }


    byte[] getBody() {
        return _requestBody;
    }


    /**
     * Returns the parameter with the specified name. If no such parameter exists, will
     * return null.
     */
    String[] getParameter(String name) {
        if (_parameters == null) {
            _parameters = readParameters(new String(_requestBody));
        }
        return (String[]) _parameters.get(name);
    }


    private static final int CR = 13;
    private static final int LF = 10;

    private InputStream _stream;
    private Reader _reader;
    private String _command;
    private String _uri;
    private Hashtable<String, String> _headers = new Hashtable<String, String>();
    private Hashtable _parameters;
    private byte[] _requestBody;


    private void readContent() throws IOException {
        _requestBody = new byte[getContentLength()];
        try {
            _stream.read(_requestBody);
            _reader = new InputStreamReader(new ByteArrayInputStream(_requestBody));
        } catch (NumberFormatException e) {/**/
        }
    }


    private int getContentLength() {
        try {
            return Integer.parseInt(getHeader("Content-Length"));
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    private void readHeaders() throws IOException {
        String lastHeader = null;

        String header = readHeaderLine();
        while (header.length() > 0) {
            if (header.charAt(0) <= ' ') {
                if (lastHeader == null) continue;
                _headers.put(lastHeader, _headers.get(lastHeader) + header.trim());
            } else {
                lastHeader = header.substring(0, header.indexOf(':')).toUpperCase();
                _headers.put(lastHeader, header.substring(header.indexOf(':') + 1).trim());
            }
            header = readHeaderLine();
        }
    }


    public String readHeaderLine() throws IOException {
        StringBuilder sb = new StringBuilder();
        int b = _stream.read();
        while (b != CR) {
            sb.append((char) b);
            b = _stream.read();
        }

        b = _stream.read();
        if (b != LF) throw new IOException("Bad header line termination: " + b);

        return sb.toString();
    }


    public static Hashtable readParameters(String content) {
        Hashtable parameters = new Hashtable();
        if (content == null || content.trim().length() == 0) return parameters;

        StringTokenizer st = new StringTokenizer(content, "&=");
        while (st.hasMoreTokens()) {
            String name = st.nextToken();
            if (st.hasMoreTokens()) {
                try {
                    addParameter(parameters, URLDecoder.decode(name, "ISO-8859-1"), URLDecoder.decode(st.nextToken(), "ISO-8859-1"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return parameters;
    }

    public static Map<String, String> readParametersWithSense(String content) {
        Map<String, String> parameters = new Hashtable<String, String>();
        if (content == null || content.trim().length() == 0)
            return parameters;
        List<String> parNames = new ArrayList<String>();//new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(content, "&");
        while (st.hasMoreTokens()) {
            parNames.add(st.nextToken());
        }
        int size = parNames.size();
        int i = 0;
        while (i < size) {
            String parname = parNames.get(i);
            i++;
            if (parname == null) continue;
            int ind = parname.indexOf("=");
            int length = parname.length();
            if (ind > 0) {
                String name = parname.substring(0, ind);
                String value = (ind == length) ? "" : parname.substring(ind + 1, length);
                try {
                    value = URLDecoder.decode(value, "UTF-8");
                    addParameter(parameters, URLDecoder.decode(name, "ISO-8859-1"), value);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
        return parameters;
    }

    public static void addParameter(Map parameters, String name, String value) {
        String[] oldValues = (String[]) parameters.get(name);
        if (oldValues == null) {
            parameters.put(name, new String[]{value});
        } else {
            String[] values = new String[oldValues.length + 1];
            System.arraycopy(oldValues, 0, values, 0, oldValues.length);
            values[oldValues.length] = value;
            parameters.put(name, values);
        }
    }

    public static void main(String[] par) {
        readParametersWithSense("q=&q=free+employee+online+survey&FORM=SMCRT");
    }

}

