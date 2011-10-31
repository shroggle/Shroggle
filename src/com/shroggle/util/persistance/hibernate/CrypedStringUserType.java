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

package com.shroggle.util.persistance.hibernate;

import com.shroggle.util.ServiceLocator;
import com.shroggle.util.StringUtil;
import org.hibernate.HibernateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

/**
 * @author dmitry.solomadin
 */
public class CrypedStringUserType extends MutableUserType {

    private static final int[] SQL_TYPES = {Types.VARCHAR};

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class returnedClass() {
        return String.class;
    }

    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
            throws HibernateException, SQLException {
        String result = resultSet.getString(names[0]);
        if (!resultSet.wasNull()) {
            result = StringUtil.isNullOrEmpty(result) ? null : ServiceLocator.getTripleDESCrypter().decrypt(result);
        }
        return result;
    }

    public void nullSafeSet(PreparedStatement statement, Object value, int index)
            throws HibernateException, SQLException {
        if (StringUtil.isNullOrEmpty((String) value)) {
            statement.setString(index, "");
        } else {
            String cryptedString = ServiceLocator.getTripleDESCrypter().crypt((String) value);
            statement.setString(index, cryptedString);
        }
    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    

}
