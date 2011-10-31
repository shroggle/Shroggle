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

package com.shroggle.util.international.property;

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalException;
import com.shroggle.util.international.InternationalStorage;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

/**
 * @author Stasuk Artem
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class PropertyBundleInternationStorageTest {

    @Test
    public void create() {
        new InternationalStoragePropertyBundle();
    }

    @Test
    public void get() {
        InternationalStorage internationalStorage = new InternationalStoragePropertyBundle();
        International international = internationalStorage.get("test", Locale.ENGLISH);

        Assert.assertNotNull(international);
    }

    @Test(expected = NullPointerException.class)
    public void getWithNullPart() {
        InternationalStorage internationalStorage = new InternationalStoragePropertyBundle();
        International international = internationalStorage.get(null, Locale.ENGLISH);

        Assert.assertNotNull(international);
    }

    @Test(expected = NullPointerException.class)
    public void getWithNullLocale() {
        InternationalStorage internationalStorage = new InternationalStoragePropertyBundle();
        International international = internationalStorage.get("test", null);

        Assert.assertNotNull(international);
    }

    @Test(expected = InternationalException.class)
    public void getNotFoundPart() {
        InternationalStorage internationalStorage = new InternationalStoragePropertyBundle();
        internationalStorage.get("", Locale.CANADA);
    }

}
