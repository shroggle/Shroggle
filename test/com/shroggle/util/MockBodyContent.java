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

package com.shroggle.util;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * @author Stasuk Artem
 */
public class MockBodyContent extends BodyContent {

    public MockBodyContent(JspWriter jspWriter) {
        super(jspWriter);
    }

    public Reader getReader() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getString() {
        return string;
    }

    public void writeOut(Writer writer) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void newLine() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void print(boolean b) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void print(char c) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void print(int i) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void print(long l) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void print(float v) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void print(double v) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void print(char[] chars) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void print(String s) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void print(Object o) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void println() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void println(boolean b) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void println(char c) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void println(int i) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void println(long l) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void println(float v) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void println(double v) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void println(char[] chars) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void println(String s) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void println(Object o) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clear() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clearBuffer() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void write(char cbuf[], int off, int len) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void close() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getRemaining() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setString(final String string) {
        this.string = string;
    }

    private String string;

}
