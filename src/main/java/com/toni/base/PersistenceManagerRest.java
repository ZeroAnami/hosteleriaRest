package com.toni.base;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

public class PersistenceManagerRest {
    private static Log logger = LogFactory.getLog(PersistenceManagerRest.class);
    private EntityManagerFactory emf;
    private static final PersistenceManagerRest singleton = new PersistenceManagerRest();

    public PersistenceManagerRest() {
    }

    public static PersistenceManagerRest getInstance() {
        return singleton;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if (this.emf == null) {
            this.createEntityManagerFactory();
        }

        return this.emf;
    }

    public void closeEntityManagerFactory() {
        if (this.emf != null) {
            this.emf.close();
            this.emf = null;
            logger.debug("Finalizando EntityManagerFactory: " + new Date());
        }
    }

    protected void createEntityManagerFactory() {
        this.emf = Persistence.createEntityManagerFactory("BaseTFG");
        logger.debug("Comenzando EntityManagerFactory: " + new Date());
    }
}
