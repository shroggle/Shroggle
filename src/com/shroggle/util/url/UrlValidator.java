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
package com.shroggle.util.url;

import com.shroggle.util.ServiceLocator;

import java.util.regex.Pattern;

/**
 * @author Balakirev Anatoliy
 */
public class UrlValidator {

    public static boolean isIpAddress(final String iP) {
        return IP_PATTERN.matcher(iP).matches();
    }

    public static boolean isDomainValid(final String url) {
        return url != null && URL_VALIDATOR.matcher(url.toLowerCase()).matches();
    }

    public static boolean isLocalDomainValid(final String url) {
        return url != null && URL_VALIDATOR_LOCALHOST.matcher(url.toLowerCase()).matches();
    }

    public static boolean isSystemSubDomainValid(final String subDomain) {
        String userSitesUrl = ServiceLocator.getConfigStorage().get().getUserSitesUrl();
        if (userSitesUrl.startsWith("localhost"))
            return subDomain != null && isLocalDomainValid("http://www." + subDomain + "." + userSitesUrl);
        else
            return subDomain != null && isDomainValid("http://www." + subDomain + "." + userSitesUrl);
    }

    private static final Pattern URL_VALIDATOR_LOCALHOST = Pattern.compile("^(https?://)?www\\.([0-9a-z]+)?\\.localhost(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)");
    private static final Pattern URL_VALIDATOR = Pattern.compile("(^(https?://)?(([0-9a-z!~*'().&=+$%-]+:)?[0-9a-z!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([0-9a-z~*'()-]+\\.)*([0-9a-z][0-9a-z-]{0,61})+[0-9a-z]\\.[a-z]{2,6})(:[0-9]{1,4})?((/?)|(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$)");
    private final static String S255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    private final static Pattern IP_PATTERN = Pattern.compile("^(?:" + S255 + "\\.){3}" + S255 + "$");

}
