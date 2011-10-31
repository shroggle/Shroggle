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

import com.shroggle.entity.User;
import com.shroggle.util.ServiceLocator;
import com.shroggle.util.config.Config;
import com.shroggle.util.config.ConfigDatabase;
import com.shroggle.util.persistance.PersistanceContext;
import com.shroggle.util.persistance.PersistanceEntities;
import com.shroggle.util.persistance.PersistanceId;
import com.shroggle.util.persistance.PersistanceTransactionContext;
import org.hibernate.ejb.Ejb3Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class HibernateManager {

    @SuppressWarnings({"deprecation"})
    public static void configuration(final HibernateInterceptor interceptor) {
        if (factory != null) {
            throw new UnsupportedOperationException(
                    "Can't configure manager, manager alredy configure!");
        }

        final Config config = ServiceLocator.getConfigStorage().get();
        final ConfigDatabase configDatabase = config.getDatabase();
        final Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", configDatabase.getDialect());
        properties.setProperty("hibernate.connection.url", configDatabase.getUrl());
        properties.setProperty("hibernate.connection.driver_class", configDatabase.getDriver());
        properties.setProperty("hibernate.connection.username", configDatabase.getLogin());
        properties.setProperty("hibernate.connection.password", configDatabase.getPassword());
        properties.setProperty("hibernate.connection.autocommit", "false");
        properties.setProperty("hibernate.max_fetch_depth", "0");

        if (config.isUseCacheHibernate()) {
            properties.setProperty(
                    "hibernate.cache.provider_class",
                    "com.shroggle.util.persistance.hibernate.cache.HibernateCacheProvider");
            properties.setProperty("hibernate.cache.use_second_level_cache", "true");
            properties.setProperty("hibernate.cache.use_query_cache", "true");
        } else {
            properties.setProperty("hibernate.cache.use_second_level_cache", "false");
            properties.setProperty("hibernate.cache.use_query_cache", "false");
        }
//        properties.setProperty("connection.pool_size", "" + configDatabase.getMaxConnections());

        properties.setProperty("hibernate.connection.pool_size", "" + configDatabase.getMaxConnections());
        properties.setProperty("hibernate.dbcp.maxActive", "-1");
        properties.setProperty("hibernate.dbcp.maxIdle", "50");
//        properties.setProperty("hibernate.dbcp.numTestsPerEvictionRun", "10");
        properties.setProperty("hibernate.dbcp.minEvictableIdleTimeMillis", "60000");

        properties.setProperty("hibernate.generate_statistics", "false");

//        properties.setProperty("hibernate.c3p0.minPoolSize", "5");
//        properties.setProperty("hibernate.c3p0.timeout", "300");
//        properties.setProperty("hibernate.c3p0.max_statements", "50");

        properties.setProperty("hibernate.connection.provider_class", "com.shroggle.util.persistance.hibernate.HibernateDBCPConnection");
        properties.setProperty("hibernate.show_sql", "false");
        properties.setProperty("hibernate.format_sql", "false");
        properties.setProperty("hibernate.hbm2ddl.auto", configDatabase.getCreated());

        final List<Class> entityClasses = PersistanceEntities.getClasses();
        final Ejb3Configuration configuration = new Ejb3Configuration().addProperties(properties);
        for (final Class entityClass : entityClasses) {
            configuration.addAnnotatedClass(entityClass);
        }
        configuration.setInterceptor(interceptor);
        configuration.buildMappings();
        try {
            factory = configuration.createEntityManagerFactory();
        } catch (final Throwable exception) {
            System.err.println("Initial SessionFactory creation failed." + exception);
            throw new ExceptionInInitializerError(exception);
        }

//        MBeanServerFactory.createMBeanServer("Hibernate");

        persistanceId = HibernatePersistanceIdCreator.execute(factory);
        inSession(new PersistanceContext<Void>() {

            public Void execute() {
                // if need create admin user
                if (configDatabase.getCreated() != null && configDatabase.getCreated().equals("create-drop")) {
                    inTransaction(new Runnable() {

                        public void run() {
                            final User user = new User();
                            user.setActiveted(new Date());
                            user.setRegistrationDate(new Date());
                            user.setEmail(config.getAdminLogin());
                            user.setPassword(config.getAdminPassword());
                            get().persist(user);
                        }

                    });
                }
                return null;
            }

        });
    }

    public static <R> R inSession(final PersistanceContext<R> persistanceContext) {
        if (factory == null) {
            throw new UnsupportedOperationException(
                    "Can't start in session with not configure manager!");
        }
        managers.set(new HibernateEntityManager(factory.createEntityManager(), persistanceId));
        try {
            return persistanceContext.execute();
        } finally {
            managers.get().close();
            managers.remove();
        }
    }

    public static void inTransaction(final Runnable transactionContext) {
        inTransaction(new PersistanceTransactionContext<Void>() {

            @Override
            public Void execute() {
                transactionContext.run();
                return null;
            }

        });
    }

    public static <R> R inTransaction(final PersistanceTransactionContext<R> context) {
        if (getTransactionCounter() == 0) {
            // Starts a transaction
            managers.get().getTransaction().begin();
        }
        incrementTransactionCounter();
        try {
            // Executes the user's code inside the transaction
            final R result = context.execute();
            decrementTransactionCounter();
            if (getTransactionCounter() == 0) {
                // Everything is fine, so commit the transactio
                managers.get().getTransaction().commit();
            }
            return result;
        } catch (final RuntimeException exception) {
            resetTransactionCounter();
            managers.get().getTransaction().rollback();
            throw exception;
        }
    }

    // todo Move this methods to separeted class.
    private static int getTransactionCounter() {
        final Integer counter = transactionCounters.get();
        if (counter == null) {
            return 0;
        }
        return counter;
    }

    private static void incrementTransactionCounter() {
        Integer counter = transactionCounters.get();
        if (counter == null) {
            counter = 0;
        }
        transactionCounters.set(counter + 1);
    }

    private static void decrementTransactionCounter() {
        Integer counter = transactionCounters.get();
        if (counter == null) {
            counter = 1;
        }
        transactionCounters.set(counter - 1);
    }

    private static void resetTransactionCounter() {
        transactionCounters.remove();
    }

    public static void close() {
        if (factory != null) {
            factory.close();
        }
        persistanceId = null;
        factory = null;
    }

    public static EntityManager get() {
        return managers.get();
    }

    static PersistanceId getPersistanceId() {
        return persistanceId;
    }

    private static EntityManagerFactory factory = null;
    private static PersistanceId persistanceId = null;
    private static final ThreadLocal<EntityManager> managers = new ThreadLocal<EntityManager>();
    private static final ThreadLocal<Integer> transactionCounters = new ThreadLocal<Integer>();

}
