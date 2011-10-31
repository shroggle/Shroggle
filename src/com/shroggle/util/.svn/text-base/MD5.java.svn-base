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

package com.shroggle.util;

import com.shroggle.exception.MD5CryptingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5 {

    /**
     * Encodes a string
     *
     * @param string String to encode
     * @return Encoded String
     */
    public static String crypt(final String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        final StringBuffer hexString = new StringBuffer();
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(string.getBytes());
            for (final byte aHash : messageDigest.digest()) {
                if ((0xff & aHash) < 0x10) {
                    hexString.append("0");
                }
                hexString.append(Integer.toHexString(0xFF & aHash));
            }
        } catch (final NoSuchAlgorithmException exception) {
            throw new MD5CryptingException();
        }
        return hexString.toString();
    }

    /**
     * Package visible constructor for use it in test.
     * It is need for correct code cover. Thanks.
     */
    MD5() {

    }

} 
