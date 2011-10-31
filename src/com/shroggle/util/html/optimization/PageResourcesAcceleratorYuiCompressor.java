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
package com.shroggle.util.html.optimization;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.config.ConfigStorage;
import com.shroggle.util.config.ConfigYuiCompressor;
import com.shroggle.util.html.HtmlUtil;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * @author Artem Stasuk
 * @link http://developer.yahoo.com/yui/compressor/#using
 * @link http://jira.web-deva.com/browse/SW-3648
 * @see com.shroggle.util.config.ConfigYuiCompressor
 */
public class PageResourcesAcceleratorYuiCompressor implements PageResourcesAccelerator {

    public PageResourcesAcceleratorYuiCompressor(final PageResourcesAccelerator accelerator) {
        this.accelerator = accelerator;
    }

    @Override
    public PageResourcesValue execute(final PageResourcesContext context, final String name)
            throws ServletException, IOException {

        final ConfigStorage configStorage = ServiceLocator.getConfigStorage();
        final ConfigYuiCompressor config = configStorage.get().getYuiCompresser();

        final PageResourcesValue value = accelerator.execute(context, name);

        String compressed = value.getValue();
        if (config.isUse()) {
            final int startSize = compressed.length();
            if ("text/css".equals(HtmlUtil.getMimeByName(name))) {
                compressed = compressCss(compressed);
            }

            if ("application/x-javascript".equals(HtmlUtil.getMimeByName(name))) {
                compressed = compressJs(compressed, config);
            }

            logger.info("Compressed from " + startSize / 1024 + " to " + compressed.length() / 1024 + " kb");
        }

        return new PageResourcesValue(compressed);
    }

    private String compressJs(final String html, final ConfigYuiCompressor config) throws IOException {
        final Reader reader = new StringReader(html);
        final JavaScriptCompressor compressor = new JavaScriptCompressor(reader, new ErrorReporter() {

            public void warning(final String message, final String sourceName, final int line, final String lineSource,
                                final int lineOffset) {
                if (line < 0) {
                    System.err.println("\n[WARNING] " + message);
                } else {
                    System.err.println("\n[WARNING] line: " + line + ", lineOffset: " + lineOffset + ", message: " + message);
                }
                if (!StringUtil.isNullOrEmpty(lineSource)) {
                    System.err.println("[WARNING] Error in line: \n" + lineSource);
                }
            }

            public void error(final String message, final String sourceName, final int line, final String lineSource,
                              final int lineOffset) {
                if (line < 0) {
                    System.err.println("\n[ERROR] message: " + message);
                } else {
                    System.err.println("\n[ERROR]  line: " + line + ", lineOffset: " + lineOffset + ", message: " + message);
                }
                if (!StringUtil.isNullOrEmpty(lineSource)) {
                    System.err.println("[ERROR] Error in line: \n" + lineSource);
                }
            }

            public EvaluatorException runtimeError(
                    final String message, final String sourceName,
                    final int line, final String lineSource, final int lineOffset) {
                error(message, sourceName, line, lineSource, lineOffset);
                return new EvaluatorException(message);
            }
        });

        final StringWriter writer = new StringWriter(html.length());
        try {
            compressor.compress(writer, -1, config.isObfuscate(), config.isDebug(), false, false);
            return writer.toString();
        } finally {
            reader.close();
            writer.close();
        }
    }

    private String compressCss(final String html) throws IOException {
        final Reader reader = new StringReader(html);
        final StringWriter writer = new StringWriter(html.length());
        try {
            new CssCompressor(reader).compress(writer, -1);
            return writer.toString();
        } finally {
            reader.close();
            writer.close();
        }
    }

    private final PageResourcesAccelerator accelerator;
    private static final Logger logger = Logger.getLogger(PageResourcesAcceleratorYuiCompressor.class.getSimpleName());

}
