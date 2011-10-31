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

package com.shroggle.entity;

import java.util.Date;
import java.util.List;

public interface ChildSiteRegistration extends Form {

    double getOneTimeFee();

    boolean isUseOneTimeFee();

    String getEmailText();

    String getWelcomeText();

    Integer getLogoId();

    boolean isRequiredToUseSiteBlueprint();

    Date getStartDate();

    Date getEndDate();

    String getTermsAndConditions();

    SiteAccessLevel getAccessLevel();

    double getPrice250mb();

    double getPrice500mb();

    double getPrice1gb();

    double getPrice3gb();

    void setPrice3gb(double price3gb);

    List<Integer> getBlueprintsId();

    String getFromEmail();

    void setFromEmail(String fromEmail);

    String getFooterText();

    String getFooterUrl();

    Integer getFooterImageId();

    String getBrandedUrl();

    boolean isBrandedAllowShroggleDomain();

    boolean isUseOwnAuthorize();

    boolean isUseOwnPaypal();

    String getAuthorizeLogin();

    String getAuthorizeTransactionKey();

    String getPaypalApiUserName();

    String getPaypalApiPassword();

    String getPaypalSignature();

    int getParentSiteId();

}