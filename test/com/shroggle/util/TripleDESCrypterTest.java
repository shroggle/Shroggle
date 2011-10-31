/*********************************************************************
 *                                                                   *
 * Copyright (c) 2007-2008 by Web-Deva.com Ltd.                      *
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

import com.shroggle.TestRunnerWithMockServices;
import com.shroggle.exception.TripleDESCryptingException;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.solomadin
 */
@RunWith(value = TestRunnerWithMockServices.class)
public class TripleDESCrypterTest extends TestCase {

    private TripleDESCrypter encrypter = new TripleDESCrypter("b1dbb7bf422586ef40a29876bf59e98b8a621f7c314016dc");
    
    @Test
    public void testCrypter() throws Exception {
        String initialString = "$0m€ nIC€ prT€Ct€d þa$$w0rd123 Ãæ«®¹—²»";
        String encryptedString = encrypter.crypt(initialString);

        Assert.assertNotSame(initialString, encryptedString);
        Assert.assertEquals(initialString, encrypter.decrypt(encryptedString));

        initialString = "easy_pass";
        encryptedString = encrypter.crypt(initialString);
                   List fff = new ArrayList();

        Assert.assertNotSame(initialString, encryptedString);
        Assert.assertEquals(initialString, encrypter.decrypt(encryptedString));
        //System.out.println(encrypter.decrypt("225E852206858CCA9C2E2EF03586974A"));
    }
    
    @Test(expected = TripleDESCryptingException.class)
    public void testCrypter_InitWithBadKey() throws Exception {
        encrypter = new TripleDESCrypter("some_shitty_key");
    }

    @Test(expected = TripleDESCryptingException.class)
    public void testCrypter_withoutKey() throws Exception {
        encrypter = new TripleDESCrypter("");
    }

  @Test
  public void testManualdesript() {
    TripleDESCrypter localDecriptor =
        new TripleDESCrypter("b1dbb7bf422586ef40a29876bf59e98b8a621f7c314016dc");

    String res = localDecriptor.decrypt("7222AA9512CECC05859CE611BC28FCFD");
    System.out.println("res = " + res);

  }

}