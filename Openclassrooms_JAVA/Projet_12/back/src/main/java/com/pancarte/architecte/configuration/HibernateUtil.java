package com.pancarte.architecte.configuration;

import com.pancarte.architecte.model.User;
import org.apache.logging.log4j.LogManager;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {

        private static final Logger logger = (Logger) LogManager.getLogger(HibernateUtil.class);

        private static StandardServiceRegistry registry;
        private static SessionFactory sessionFactory;

        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                try {
                    StandardServiceRegistryBuilder registryBuilder =
                            new StandardServiceRegistryBuilder();

                    Map<String, String> settings = new HashMap<>();
                    settings.put(Environment.DRIVER, "com.postgresql.cj.jdbc.Driver");
                    settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/db_architect");
                    settings.put(Environment.USER, "postgres");
                    settings.put(Environment.PASS, "root");
                    settings.put(Environment.HBM2DDL_AUTO, "update");

                    registryBuilder.applySettings(settings);

                    registry = registryBuilder.build();

                    logger.info("Hibernate Registry builder created.");

                    MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(User.class);

                    Metadata metadata = sources.getMetadataBuilder().build();

                    sessionFactory = metadata.getSessionFactoryBuilder().build();

                    logger.info("SessionFactory created.");

                } catch (Exception e) {
                    logger.info("SessionFactory creation failed");
                    if (registry != null) {
                        StandardServiceRegistryBuilder.destroy(registry);
                    }
                }
            }
            return sessionFactory;
        }

        public static void shutdown() {
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
    }

