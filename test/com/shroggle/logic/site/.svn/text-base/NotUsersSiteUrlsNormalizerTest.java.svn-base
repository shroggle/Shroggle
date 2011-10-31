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
package com.shroggle.logic.site;

import org.junit.Test;

import net.sourceforge.stripes.mock.MockHttpServletRequest;


import junit.framework.Assert;

/**
 * @author Balakirev Anatoliy
 */
public class NotUsersSiteUrlsNormalizerTest {


    @Test
    public void testGetNormalizedUrl() {
        final MockHttpServletRequest request = new MockHttpServletRequest("/start.action", "");
        request.setServerName("web-deva.com");
        request.setProtocol("http");
        request.setServerPort(8080);
        request.setQueryString("a=1&b=2");

        final NotUsersSiteUrlsNormalizer normalizer = new NotUsersSiteUrlsNormalizer(request);
        final String newUrl = normalizer.getNormalizedUrl();
        Assert.assertEquals("http://www.web-deva.com:8080/start.action?a=1&b=2", newUrl);
    }
    
    @Test
    public void testGetNormalizedUrl_withport80() {
        final MockHttpServletRequest request = new MockHttpServletRequest("/start.action", "");
        request.setServerName("web-deva.com");
        request.setProtocol("http");
        request.setServerPort(80);
        request.setQueryString("a=1&b=2");

        final NotUsersSiteUrlsNormalizer normalizer = new NotUsersSiteUrlsNormalizer(request);
        final String newUrl = normalizer.getNormalizedUrl();
        Assert.assertEquals("http://www.web-deva.com/start.action?a=1&b=2", newUrl);
    }

    @Test
    public void testGetNormalizedUrl_withWww() {
        final MockHttpServletRequest request = new MockHttpServletRequest("/start.action", "");
        request.setServerName("www.web-deva.com");
        request.setProtocol("http");
        request.setServerPort(8080);
        request.setQueryString("a=1&b=2");

        final NotUsersSiteUrlsNormalizer normalizer = new NotUsersSiteUrlsNormalizer(request);
        final String newUrl = normalizer.getNormalizedUrl();
        Assert.assertEquals("http://www.web-deva.com:8080/start.action?a=1&b=2", newUrl);
    }

    @Test
    public void testGetNormalizedUrl_withCapilalWww() {
        final MockHttpServletRequest request = new MockHttpServletRequest("/start.action", "");
        request.setServerName("WWW.web-deva.com");
        request.setProtocol("http");
        request.setServerPort(8080);
        request.setQueryString("a=1&b=2");

        final NotUsersSiteUrlsNormalizer normalizer = new NotUsersSiteUrlsNormalizer(request);
        final String newUrl = normalizer.getNormalizedUrl();
        Assert.assertEquals("http://WWW.web-deva.com:8080/start.action?a=1&b=2", newUrl);
    }

    @Test
    public void testGetNormalizedUrl_withIpAddress() {
        final MockHttpServletRequest request = new MockHttpServletRequest("/start.action", "");
        request.setServerName("192.168.0.1");
        request.setProtocol("https");
        request.setServerPort(8080);
        request.setQueryString("a=1&b=2");

        final NotUsersSiteUrlsNormalizer normalizer = new NotUsersSiteUrlsNormalizer(request);
        final String newUrl = normalizer.getNormalizedUrl();
        Assert.assertEquals("https://192.168.0.1:8080/start.action?a=1&b=2", newUrl);
    }

    @Test
    public void testGetNormalizedUrl_withWithoutParameters() {
        final MockHttpServletRequest request = new MockHttpServletRequest("/start.action", "");
        request.setServerName("www.web-deva.com");
        request.setProtocol("http");
        request.setServerPort(8080);

        final NotUsersSiteUrlsNormalizer normalizer = new NotUsersSiteUrlsNormalizer(request);
        final String newUrl = normalizer.getNormalizedUrl();
        Assert.assertEquals("http://www.web-deva.com:8080/start.action", newUrl);
    }

    @Test
    public void testGetNormalizedUrl_withoutUri() {
        final MockHttpServletRequest request = new MockHttpServletRequest("", "");
        request.setServerName("www.web-deva.com");
        request.setProtocol("http");
        request.setServerPort(8080);
        request.setQueryString("a=1&b=2");

        final NotUsersSiteUrlsNormalizer normalizer = new NotUsersSiteUrlsNormalizer(request);
        final String newUrl = normalizer.getNormalizedUrl();
        Assert.assertEquals("http://www.web-deva.com:8080?a=1&b=2", newUrl);
    }
}
