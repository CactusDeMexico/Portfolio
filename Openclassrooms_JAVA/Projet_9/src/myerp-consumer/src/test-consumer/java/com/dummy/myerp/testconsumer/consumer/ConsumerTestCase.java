package com.dummy.myerp.testconsumer.consumer;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;


/**
 * Classe mère des classes de test d'intégration de la couche Consumer
 */
public abstract class ConsumerTestCase {

    static {
        SpringRegistry.init();
    }

    /** {@link DaoProxy} */
    private static final DaoProxy DAO_PROXY = SpringRegistry.getDaoProxy();


    // ==================== Constructeurs ====================
    /**
     * Constructeur.
     */
    public ConsumerTestCase() {
    }


    // ==================== Getters/Setters ====================
    public static DaoProxy getDaoProxy() {
        return DAO_PROXY;
    }
}
