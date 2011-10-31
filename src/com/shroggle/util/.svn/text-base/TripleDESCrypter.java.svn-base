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

import com.shroggle.exception.TripleDESCryptingException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * @author dmitry.solomadin
 */
public class TripleDESCrypter {
    private String rawKey;
    private SecretKey key;

    public TripleDESCrypter(final String key) {
        if (StringUtil.isNullOrEmpty(key)) {
            throw new TripleDESCryptingException("Encryption key wasn't provided.");
        }

        this.rawKey = key;
        try {
            buildSecretKey();
        } catch (NoSuchAlgorithmException e) {
            throw new TripleDESCryptingException(e);
        } catch (InvalidKeyException e) {
            throw new TripleDESCryptingException(e);
        } catch (InvalidKeySpecException e) {
            throw new TripleDESCryptingException(e);
        } catch (UnsupportedEncodingException e) {
            throw new TripleDESCryptingException(e);
        } catch (DecoderException e) {
            throw new TripleDESCryptingException(e);
        }
    }

    private TripleDESCrypter() {
    }

    public String crypt(final String sToCrypt) {
        if (StringUtil.isNullOrEmpty(sToCrypt)) {
            return "";
        }

        try {
            return cryptInternal(sToCrypt);
        } catch (Exception e) {
            throw new TripleDESCryptingException(e);
        }
    }

    public String decrypt(final String sToDecrypt) {
        if (StringUtil.isNullOrEmpty(sToDecrypt)) {
            return "";
        }

        try {
            return decryptInternal(sToDecrypt);
        } catch (Exception e) {
            e.printStackTrace();
            return sToDecrypt;
        }
    }

    public String decryptOrThrowException(final String sToDecrypt) {
        if (StringUtil.isNullOrEmpty(sToDecrypt)) {
            return "";
        }

        try {
            return decryptInternal(sToDecrypt);
        } catch (Exception e) {
            throw new TripleDESCryptingException(e);
        }
    }

    private String cryptInternal(final String sToCrypt) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        /* Initialization Vector of 8 bytes set to zero. */
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);

        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        final byte[] cipheredText = cipher.doFinal(sToCrypt.getBytes("UTF-8"));

        return new String(Hex.encodeHex(cipheredText)).toUpperCase();
    }

    private String decryptInternal(final String sToDecrypt) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException,
            DecoderException {
        /* Initialization Vector of 8 bytes set to zero. */
        final IvParameterSpec iv = new IvParameterSpec(new byte[8]);

        final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        final byte[] decryptedText = cipher.doFinal(Hex.decodeHex(sToDecrypt.toCharArray()));

        return new String(decryptedText, "UTF-8");
    }

    private void buildSecretKey() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, UnsupportedEncodingException, DecoderException {
        // Check to see whether there is a provider that can do TripleDES
        // encryption. If not, explicitly install the SunJCE provider.
        try {
            Cipher.getInstance("DESede");
        } catch (Exception e) {
            // An exception here probably means the JCE provider hasn't
            // been permanently installed on this system by listing it
            // in the $JAVA_HOME/jre/lib/security/java.security file.
            // Therefore, we have to install the JCE provider explicitly.
            System.err.println("Installing SunJCE provider.");
            Provider sunJCE = new com.sun.crypto.provider.SunJCE();
            Security.addProvider(sunJCE);
        }

        final DESedeKeySpec keySpec = new DESedeKeySpec(Hex.decodeHex(rawKey.toCharArray()));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        key = keyFactory.generateSecret(keySpec);
    }

}
