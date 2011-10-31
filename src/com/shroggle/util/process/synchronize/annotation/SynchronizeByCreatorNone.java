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
package com.shroggle.util.process.synchronize.annotation;

import com.shroggle.util.process.synchronize.SynchronizeRequest;

/**
 * Author: Artem Stasuk (artem).
 * </p>
 * Date: 17.09.2008
 */
class SynchronizeByCreatorNone implements SynchronizeByCreator {

    public SynchronizeRequest create(
            final Object object, final Object... parameters) {
        return null;
    }

}
