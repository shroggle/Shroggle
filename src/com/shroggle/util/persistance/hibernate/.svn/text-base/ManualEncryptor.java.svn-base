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

import com.shroggle.logic.video.SourceVideoSizeCreator;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.persistance.Persistance;
import com.shroggle.util.persistance.PersistanceContext;
import com.shroggle.util.persistance.PersistanceTransaction;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author dmitry.solomadin
 */
public class ManualEncryptor {
    private String fieldName;
    private String tableName;

    public ManualEncryptor(String fieldName, String tableName) {
        this.fieldName = fieldName;
        this.tableName = tableName;
    }

    public void execute() {
        final Logger logger = Logger.getLogger(SourceVideoSizeCreator.class.getName());
        logger.log(Level.INFO, "Started encryption for field: '" + fieldName + "' in table: '" + tableName + "'");
        final PersistanceTransaction persistanceTransaction = ServiceLocator.getPersistanceTransaction();
        final Persistance persistance = ServiceLocator.getPersistance();
        persistance.inContext(new PersistanceContext<Void>() {
            @Override
            public Void execute() {
                persistanceTransaction.execute(new Runnable() {
                    public void run() {
                        final Query query = HibernateManager.get().createNativeQuery("select " + fieldName + " from " + tableName);
                        final List<String> stringsToEncrypt = query.getResultList();
                        final Map<String, String> initialEncryptedStringMap = new HashMap<String, String>();
                        for (String stringToEncrypt : stringsToEncrypt) {
                            // Check if string was already encoded.
                            try {
                                ServiceLocator.getTripleDESCrypter().decryptOrThrowException(stringToEncrypt);
                            } catch (Exception e) {
                                // String should be encrypted.
                                String encryptedString = ServiceLocator.getTripleDESCrypter().crypt(stringToEncrypt);
                                initialEncryptedStringMap.put(stringToEncrypt, encryptedString);
                            }
                        }

                        for (Map.Entry<String, String> entry : initialEncryptedStringMap.entrySet()) {
                            HibernateManager.get().createNativeQuery("update " + tableName + " set " +
                                    "" + fieldName + "= '" + entry.getValue() + "' where " + fieldName + " = :initString")
                                    .setParameter("initString", entry.getKey()).executeUpdate();
                        }
                    }
                });
                return null;
            }
        });
        logger.log(Level.INFO, "Encryption finished!");
    }

}
