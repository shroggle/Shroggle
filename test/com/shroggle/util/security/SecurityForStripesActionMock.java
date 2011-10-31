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
package com.shroggle.util.security;

import com.shroggle.presentation.Action;
import com.shroggle.util.ServiceLocator;
import net.sourceforge.stripes.action.Resolution;

/**
 * @author Artem Stasuk
 */
class SecurityForStripesActionMock extends Action {

    @SecurityUser
    public Resolution test() {
        return ServiceLocator.getResolutionCreator().forwardToUrl("F");
    }


    public Resolution testWithoutAnnotation() {
        return ServiceLocator.getResolutionCreator().forwardToUrl("F");
    }

}