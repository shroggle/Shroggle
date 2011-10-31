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

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import com.shroggle.util.context.Context;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Stasuk Artem
 */
public class HtmlUtil {

    public static String emptyOrValue(final String value) {
        return value == null ? "" : value;
    }

    public static String escapeForJs(final String value) {
        return value == null ? null : value.replace("'", "\\\'");
    }

    public static String escapeDoubleQuotas(final String value) {
        return value == null ? null : value.replace("\"", "\\\"");
    }

    public static String ignoreWordIf(String string) {
        return string == null ? null : string.replaceAll("<!--\\[if (gte mso [0-9]+|!supportEmptyParas)\\]-*>.*?<!-*\\[endif\\]-->", "");
    }

    public static String insertJsessionId(final String url) {
        int delimiter = url.indexOf("?");
        if (delimiter < 1) {
            delimiter = url.indexOf("#");
        }

        String start = url;
        String end = "";
        if (delimiter > 0) {
            start = url.substring(0, delimiter);
            end = url.substring(delimiter, url.length());
        }

        final Context context = ServiceLocator.getContextStorage().get();
        return start + ";jsessionid=" + context.getSessionId() + end;
    }

    //Render's HTML like plain text

    public static String ignoreHtml(final String input) {
        String output = input;
        output = output.replace("<", "&lt;");
        output = output.replace(">", "&gt;");
        return output;
    }

    //Render's HTML like plain text

    public static String ignoreHtmlWithRemoveParagraphs(final String input) {
        String output = input;
        output = output.replace("<p>", "");
        output = output.replace("</p>", "");
        output = output.replace("<", "&lt;");
        output = output.replace(">", "&gt;");
        return output;
    }

    public static String getCookie(final HttpServletRequest request, final String name, final String def) {
        if (request.getCookies() != null) {
            for (final Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return def;
    }

    public static String removeParagraphs(final String input) {
        String output = input;
        output = output.replace("<p>", "");
        output = output.replace("</p>", "");
        return output;
    }

    public static String limitName(final String name) {
        return (!StringUtil.isNullOrEmpty(name) && name.trim().length() > 60) ? name.trim().substring(0, 57) + "..." : name;
    }

    public static String limitName(final String name, final int length) {
        return (!StringUtil.isNullOrEmpty(name) && name.trim().length() > length) ? name.trim().substring(0, length - 3) + "..." : name;
    }

    public static boolean isNeedLimitation(final String name, final int length) {
        return !StringUtil.isNullOrEmpty(name) && name.length() > length;
    }

    public static void writeHeaderInfo(
            final Writer out, final HttpServletResponse response) throws IOException {
        out.append(HTML_META_UTF);
        out.append(HTML_META_CACHE_CONTROL);
        out.append(HTML_META_PRAGMA_NO_CACHE);
        out.append(HTML_META_EXPIRED);
        response.addHeader("Expires", "Wed, 26 Feb 1997 08:21:57 GMT");
        response.setContentType("text/html; charset=UTF-8");
    }

    public static String encodeToPercent(final String string) {
        if (string == null) {
            return null;//throw new IllegalArgumentException("Can't encode null string!");
        }
        String resultString = string.replace("&", "%26");
        resultString = resultString.replace("?", "%3f");
        return resultString;
    }

    public static String getMimeByExtension(final String extension) {
        if (extension == null) {
            throw new UnsupportedOperationException("Can't get content type by null extension!");
        }
        return contentTypeByExtensions.get(extension.toLowerCase());
    }

    public static String getMimeByName(final String name) {
        for (final String ext : contentTypeByExtensions.keySet()) {
            if (name.endsWith("." + ext)) {
                return contentTypeByExtensions.get(ext);
            }
        }

        throw new UnsupportedOperationException("Can't get content type by unknown name: " + name);
    }

    public static String formatDate(final Date date) {
        return dateFormat.format(date);
    }

    public static String removeEndingSlash(final String str) {
        if (str == null || str.isEmpty())
            return str;
        return str.trim().replaceAll("/$", "");
    }

    public static final String HTML_META_UTF = "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
    public static final String HTML_META_CACHE_CONTROL = "<META HTTP-EQUIV='CACHE-CONTROL' CONTENT='NO-CACHE'>";
    public static final String HTML_META_PRAGMA_NO_CACHE = "<META HTTP-EQUIV='PRAGMA' CONTENT='NO-CACHE'>";
    public static final String HTML_META_EXPIRED = "<META HTTP-EQUIV='EXPIRES' CONTENT='Wed, 26 Feb 1997 08:21:57 GMT'>";
    // Wednesday, April 16, 2008. 5.30pm
    private final static SimpleDateFormat dateFormat;
    private final static Map<String, String> contentTypeByExtensions;

    static {
        Locale.setDefault(new Locale("en"));
        dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy. h:ma");
        final Map<String, String> tempContentTypeByExtension = new HashMap<String, String>();
        tempContentTypeByExtension.put("jpg", "image/jpeg");
        tempContentTypeByExtension.put("jpeg", "image/jpeg");
        tempContentTypeByExtension.put("djvu", "image/x.djvu");
        tempContentTypeByExtension.put("djv", "image/x.djvu");
        tempContentTypeByExtension.put("pdf", "application/pdf");
        tempContentTypeByExtension.put("png", "image/png");
        tempContentTypeByExtension.put("gif", "image/gif");
        tempContentTypeByExtension.put("swf", "application/x-shockwave-flash");
        tempContentTypeByExtension.put("css", "text/css");
        tempContentTypeByExtension.put("js", "application/x-javascript");
        tempContentTypeByExtension.put("mp3", "audio/mpeg");
        tempContentTypeByExtension.put("wav", "audio/wav");
        tempContentTypeByExtension.put("flv", "video/x-flv");
        contentTypeByExtensions = Collections.unmodifiableMap(tempContentTypeByExtension);
    }

    //replace <!--[if !supportLineBreakNewLine]-->"text in tag"<!--[endif]--> by "text in tag"

    public static String removeIeComments(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        Pattern pattern = Pattern.compile("(?:<|&lt;)\\s*?!--\\s*?\\[\\s*?if\\s*?!supportLineBreakNewLine\\s*?\\]\\s*?--(?:>|&gt;)(.*?|\\n*?)(?:<|&lt;)!--\\s*?\\[\\s*?endif\\s*?\\]\\s*?--(?:>|&gt;)");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            text = matcher.replaceFirst(matcher.group(1));
            matcher = pattern.matcher(text);
        }
        return text;
    }

    public static boolean containsIeComments(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("(?:<|&lt;)\\s*?!--\\s*?\\[\\s*?if\\s*?!supportLineBreakNewLine\\s*?\\]\\s*?--(?:>|&gt;)(.*?|\\n*?)(?:<|&lt;)!--\\s*?\\[\\s*?endif\\s*?\\]\\s*?--(?:>|&gt;)");
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public static String replaceNewLineByBr(final String oldText) {
        String newText = StringUtil.getEmptyOrString(oldText).replace("\n", "<br>");
        newText = newText.trim();
        if (newText.startsWith("<p>") && newText.endsWith("</p>")) {
            newText = newText.substring(3, newText.length() - 4);
        }
        return newText;
    }

    public static boolean isIe6(final HttpServletRequest request) {
        final String userAgent = request.getHeader("User-Agent");
        return userAgent != null && userAgent.contains("MSIE6");
    }

    public static String getTag(final String mime, final String src) {
        if ("text/css".equals(mime)) {
            return "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + src + "\">";
        } else if ("application/x-javascript".equals(mime)) {
            return "<script type=\"text/javascript\" src=\"" + src + "\"></script>";
        }
        return src;
    }

    public static String removeAllTags(final String html) {
        return html != null ? (html.replaceAll("<(.|\n)*?>", "")).trim() : null;
    }

    public static String removeAllTagsExceptBr(final String html) {
        return html != null ? (html.replaceAll("<(?!\\s*\\bbr\\b(?:.|\n)*?>)(?:.|\n)*?>", "")).trim() : null;
    }

    public static String replaceBlockTagsByBr(final String html) {
        return html != null ? html.replaceAll(createRegexpForBlockElements(), "<br>") : null;
    }

    /*-------------------------------------------------hidden methods-------------------------------------------------*/
    private static String createRegexpForBlockElements() {
        StringBuilder regexp = new StringBuilder();
        regexp.append("(");
        for (int i = 0; i < blockElements.length; i++) {
            regexp.append(closeBlockElementAndCreateRegexp(blockElements[i]));
            if (i == (blockElements.length - 1)) {
                regexp.append(")");
            } else {
                regexp.append("|");
            }
        }
        return regexp.toString();
    }

    private static String closeBlockElementAndCreateRegexp(final String element) {
        String regexp = element.replaceFirst("(?<=<)(?=\\w)", "\\\\s*?/\\\\s*?");
        regexp = regexp.replaceFirst("(?<=\\w)(?=>)", "\\\\s*?");
        return regexp;
    }

    private static final String[] blockElements = {"<applet>", "<blockquote>", "<body>", "<button>", "<div>", "<dl>",
            "<fieldset>", "<form>", "<frameset>", "<h1>", "<h2>", "<h3>", "<h4>", "<h5>", "<h6>", "<head>", "<html>",
            "<iframe>", "<layer>", "<legend>", "<object>", "<ol>", "<p>", "<select>", "<table>", "<tr>", "<ul>"};
    /*-------------------------------------------------hidden methods-------------------------------------------------*/

}
