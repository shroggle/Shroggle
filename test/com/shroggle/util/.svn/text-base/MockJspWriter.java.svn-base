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
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Stasuk Artem
 */
public class MockJspWriter extends JspWriter {

    public MockJspWriter(int i, boolean b) {
        super(i, b);
        stringWriter = new StringWriter(1024);
        printWriter = new PrintWriter(stringWriter);
    }

    public MockJspWriter() {
        this(0, false);
    }

    public String toString() {
        return stringWriter.toString();
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
        printWriter.print(s);
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
        printWriter.print(s);
        printWriter.print("\n");
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
        printWriter.write(cbuf, off, len);
    }

    public void flush() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void close() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getRemaining() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private final StringWriter stringWriter;
    private final PrintWriter printWriter;

}
