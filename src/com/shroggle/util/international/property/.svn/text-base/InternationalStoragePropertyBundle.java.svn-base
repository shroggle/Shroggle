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

import com.shroggle.util.international.International;
import com.shroggle.util.international.InternationalException;
import com.shroggle.util.international.InternationalStorage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Stasuk Artem
 */
public class InternationalStoragePropertyBundle implements InternationalStorage {

    public International get(final String part, final Locale locale) {
        if (part == null) {
            throw new NullPointerException("Can't get international. Part is null!");
        }
        if (locale == null) {
            throw new NullPointerException("Can't get international. Locale is null!");
        }

        final InputStream in = getExistFile(part, locale);
        if (in == null) {
            throw new InternationalException("Can't find international file for part " + part + "!");
        }

        final PropertyResourceBundle resourceBundle;
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(in, "UTF8");
            resourceBundle = new PropertyResourceBundle(inputStreamReader);
            inputStreamReader.close();
        } catch (FileNotFoundException exception) {
            throw new InternationalException(exception);
        } catch (IOException exception) {
            throw new InternationalException(exception);
        }
        return new InternationalPropertyBundle(resourceBundle);
    }

    private InputStream getExistFile(final String part, final Locale locale) {
        for (final String suffix : getSuffixes(locale)) {
            final InputStream in = InternationalStorage.class.getResourceAsStream(part + suffix + ".properties");
            if (in != null) {
                return in;
            }
        }
        return null;
    }

    private List<String> getSuffixes(final Locale locale) {
        LinkedList<String> result = new LinkedList<String>();
        result.addFirst("");
        StringBuilder builder = new StringBuilder();
        builder.append('_');
        if (locale.getLanguage().length() > 0) {
            builder.append(locale.getLanguage());
            result.addFirst(builder.toString());
        }
        builder.append('_');
        if (locale.getCountry().length() > 0) {
            builder.append(locale.getCountry());
            result.addFirst(builder.toString());
        }
        builder.append('_');
        if (locale.getVariant().length() > 0) {
            if (locale.getVariant().startsWith("ef_")) {
                builder.append("ef");
                result.addFirst(builder.toString());
                builder.append(locale.getVariant().substring(2));
                result.addFirst(builder.toString());
            } else {
                builder.append(locale.getVariant());
                result.addFirst(builder.toString());
            }
        }
        return new ArrayList<String>(result);
    }

}
