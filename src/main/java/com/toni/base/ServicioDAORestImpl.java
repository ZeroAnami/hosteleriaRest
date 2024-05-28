package com.toni.base;

import com.toni.base.exception.InvalidIdObjectException;
import com.toni.base.exception.MasDeUnaEntidadEncontradaException;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.*;

public class ServicioDAORestImpl<T> implements ServicioDAORest<T>, Serializable {
    protected static final Log LOGGER = LogFactory.getLog(ServicioDAORestImpl.class);
    private static final long serialVersionUID = 3L;
    protected EntityManager em = null;
    protected transient EntityManagerFactory emf = this.createEntityManagerFactory();

    public ServicioDAORestImpl() {
    }

    protected EntityManagerFactory getEntityManagerFactory() {
        if (this.emf == null) {
            this.emf = this.createEntityManagerFactory();
        }

        return this.emf;
    }

    protected EntityManagerFactory createEntityManagerFactory() {
        return PersistenceManagerRest.getInstance().getEntityManagerFactory();
    }

    public void beginTransaction() {
        this.em = this.emf.createEntityManager();
        if (!this.em.getTransaction().isActive()) {
            this.em.getTransaction().begin();
        }

    }

    public EntityTransaction getTransaction() {
        return this.em.getTransaction();
    }

    public void closeTransaction() {
        try {
            if (this.em != null && this.em.isOpen()) {
                this.em.close();
            }
        } catch (Exception var2) {
            LOGGER.error("Excepción en ServicioDAOImpl.closeTransaction: ", var2);
        }

    }

    public void commitAndCloseTransaction() {
        this.commit();
        this.closeTransaction();
    }

    public void joinTransaction() {
        LOGGER.debug("joinTransaction: {}");
        this.em = this.emf.createEntityManager();
        this.em.joinTransaction();
    }

    public void commit() {
        if (this.em.getTransaction().isActive()) {
            this.em.getTransaction().commit();
        }

    }

    public void rollback() {
        try {
            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }
        } catch (Throwable var2) {
            LOGGER.error("Excepción en ServicioDAOImpl.rollback: ", var2);
        }

    }

    public void flush() {
        if (this.em.getTransaction().isActive()) {
            this.em.flush();
        }

    }

    public void refresh(T entity) {
        LOGGER.debug("refresh: {entity=" + entity.getClass().getName() + "}");
        this.em.refresh(entity);
    }

    private boolean validarEntidad(T entity) {
        return true;
    }

    public void guardar(T entity) throws DAOException {
        LOGGER.debug("guardar: {entity=" + entity.getClass().getName() + "}");

        try {
            this.beginTransaction();
            this.validarEntidad(entity);
            this.em.persist(entity);
            this.commit();
        } catch (Exception var6) {
            this.rollback();
            throw new DAOException("Error al ejecutar save: ", var6);
        } finally {
            this.closeTransaction();
        }

    }

    public void save(T entity) throws DAOException {
        LOGGER.debug("save: {entity=" + entity.getClass().getName() + "}");

        try {
            this.validarEntidad(entity);
            this.em.persist(entity);
        } catch (Exception var3) {
            LOGGER.error("Error al ejecutar save: ", var3);
            throw new DAOException("Error al ejecutar save: ", var3);
        }
    }

    public void eliminar(T entity) throws DAOException {
        LOGGER.debug("delete: {entity=" + entity.getClass().getName() + "}");

        try {
            this.beginTransaction();
            T entityToBeRemoved = this.em.merge(entity);
            this.em.remove(entityToBeRemoved);
            this.commit();
        } catch (Exception var6) {
            this.rollback();
            LOGGER.error("Error al ejecutar delete: ", var6);
            throw new DAOException("Error al ejecutar delete: ", var6);
        } finally {
            this.closeTransaction();
        }

    }

    public void delete(T entity) throws DAOException {
        LOGGER.debug("delete: {entity=" + entity.getClass().getName() + "}");

        try {
            T entityToBeRemoved = this.em.merge(entity);
            this.em.remove(entityToBeRemoved);
        } catch (Exception var3) {
            LOGGER.error("Error al ejecutar delete: ", var3);
            throw new DAOException("Error al ejecutar delete: ", var3);
        }
    }

    public T modificar(T entity) throws DAOException {
        LOGGER.debug("modificar: {entity=" + entity.getClass().getName() + "}");

        try {
            this.beginTransaction();
            this.validarEntidad(entity);
            entity = this.em.merge(entity);
            this.commit();
        } catch (Exception var6) {
            this.rollback();
            LOGGER.error("Error al ejecutar update: ", var6);
            throw new DAOException("Error al ejecutar update: ", var6);
        } finally {
            this.closeTransaction();
        }

        return entity;
    }

    public T update(T entity) throws DAOException {
        LOGGER.debug("update: {entity=" + entity.getClass().getName() + "}");

        try {
            this.validarEntidad(entity);
            entity = this.em.merge(entity);
            return entity;
        } catch (Exception var3) {
            LOGGER.error("Error al ejecutar update: ", var3);
            throw new DAOException("Error al ejecutar update: ", var3);
        }
    }

    public int update(String textoQuery, Map<String, Object> parameters) throws Exception {
        Class<T> entityClass = this.getEntityClass();
        LOGGER.debug("updateJPQL: {entity=" + entityClass.getName() + ", textoQuery=" + textoQuery + ", parameters=" + parameters + "}");
        int results = 0;

        try {
            this.beginTransaction();
            Query query = this.em.createQuery(textoQuery);
            if (parameters != null && !parameters.isEmpty()) {
                this.populateQueryParameters(query, parameters);
            }

            results = query.executeUpdate();
            this.commit();
        } catch (Exception var9) {
            this.rollback();
            LOGGER.error("Error al ejecutar el update: " + var9.getMessage(), var9);
            throw new Exception("Error al ejecutar el update: " + var9.getMessage(), var9);
        } finally {
            this.closeTransaction();
        }

        return results;
    }

    public T getById(Short entityID) throws DAOException {
        LOGGER.debug("getById: {entityID=" + entityID + "}");

        Object entidad;
        try {
            Class<T> entityClass = this.getEntityClass();
            this.beginTransaction();
            entidad = this.em.find(entityClass, entityID);
            Object var4 = entidad;
            return (T) var4;
        } catch (Exception var8) {
            entidad = null;
        } finally {
            this.closeTransaction();
        }

        return (T) entidad;
    }

    public T getById(Integer entityID) throws DAOException {
        LOGGER.debug("getById: {entityID=" + entityID + "}");

        Object entidad;
        try {
            Class<T> entityClass = this.getEntityClass();
            this.beginTransaction();
            entidad = this.em.find(entityClass, entityID);
            Object var4 = entidad;
            return (T) var4;
        } catch (Exception var8) {
            entidad = null;
        } finally {
            this.closeTransaction();
        }

        return (T) entidad;
    }

    public T getById(Long entityID) throws DAOException {
        LOGGER.debug("getById: {entityID=" + entityID + "}");

        Object entidad;
        try {
            Class<T> entityClass = this.getEntityClass();
            this.beginTransaction();
            entidad = this.em.find(entityClass, entityID);
            Object var4 = entidad;
            return (T) var4;
        } catch (Exception var8) {
            entidad = null;
        } finally {
            this.closeTransaction();
        }

        return (T) entidad;
    }

    public T getById(BigInteger entityID) throws DAOException {
        LOGGER.debug("getById: {entityID=" + entityID + "}");

        Object entidad;
        try {
            Class<T> entityClass = this.getEntityClass();
            this.beginTransaction();
            entidad = this.em.find(entityClass, entityID);
            Object var4 = entidad;
            return (T) var4;
        } catch (Exception var8) {
            entidad = null;
        } finally {
            this.closeTransaction();
        }

        return (T) entidad;
    }

    public T getById(String entityID) throws DAOException {
        LOGGER.debug("getById: {entityID=" + entityID + "}");

        Object entidad;
        try {
            Class<T> entityClass = this.getEntityClass();
            this.beginTransaction();
            entidad = this.em.find(entityClass, entityID);
            Object var4 = entidad;
            return (T) var4;
        } catch (Exception var8) {
            entidad = null;
        } finally {
            this.closeTransaction();
        }

        return (T) entidad;
    }

    public T getById(Object id) throws InvalidIdObjectException, DAOException {
        if (id != null) {
            Class<T> entityClass = this.getEntityClass();
            Field[] fields = entityClass.getDeclaredFields();
            Class pkClass = null;
            Field[] var5 = fields;
            int var6 = fields.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Field field = var5[var7];
                Annotation[] annotations = field.getAnnotations();
                Annotation[] var10 = annotations;
                int var11 = annotations.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    Annotation annotation = var10[var12];
                    if (annotation.annotationType().equals(EmbeddedId.class)) {
                        pkClass = field.getType();
                        break;
                    }
                }
            }

            if (pkClass == null) {
                throw new InvalidIdObjectException(entityClass.getCanonicalName());
            } else if (!id.getClass().equals(pkClass)) {
                throw new InvalidIdObjectException(entityClass.getCanonicalName(), id.getClass().getCanonicalName(), pkClass.getCanonicalName());
            } else {
                Object var20;
                try {
                    this.beginTransaction();
                    T entidad = this.em.find(entityClass, id);
                    if (entidad != null) {
                        var20 = entidad;
                        return (T) var20;
                    }

                    var20 = null;
                } catch (Exception var17) {
                    LOGGER.error("Error al ejecutar la consulta getById(Object id): ", var17);
                    return null;
                } finally {
                    this.closeTransaction();
                }

                return (T) var20;
            }
        } else {
            return null;
        }
    }

    public T findOneResult(Query query) throws DAOException {
        Class<T> entityClass = this.getEntityClass();
        LOGGER.debug("findOneResult: {entity=" + entityClass.getName() + "}");
        T result = null;

        try {
            result = (T) query.getSingleResult();
        } catch (NoResultException var5) {
            this.rollback();
            LOGGER.debug("No hay resultados para la consulta: ", var5);
        } catch (NonUniqueResultException var6) {
            this.rollback();
            LOGGER.debug("Demasiados resultados para la consulta: ", var6);
            throw new MasDeUnaEntidadEncontradaException("Demasiados resultados para la consulta: ", var6);
        } catch (Exception var7) {
            this.rollback();
            LOGGER.error("Error al ejecutar la consulta: " + var7.getMessage(), var7);
            throw new DAOException("Error al ejecutar la consulta: " + var7.getMessage(), var7);
        }

        return result;
    }

    public List findMultipleResult(Query query) throws DAOException {
        Class<T> entityClass = this.getEntityClass();
        LOGGER.debug("findMultipleResult: {entity=" + entityClass.getName() + "}");
        List<T> results = null;

        try {
            results = query.getResultList();
            return results;
        } catch (Exception var5) {
            LOGGER.error("Error al ejecutar la consulta: " + var5.getMessage(), var5);
            throw new DAOException("Error al ejecutar la consulta: " + var5.getMessage(), var5);
        }
    }

    public Object findOneResultJPQL(String textoQuery) throws DAOException {
        Class<T> entityClass = this.getEntityClass();
        LOGGER.debug("findOneResultJPQL: {entity=" + entityClass.getName() + ", textoQuery=" + textoQuery + "}");
        Object result = null;

        try {
            this.beginTransaction();
            Query query = this.em.createQuery(textoQuery);
            result = query.getSingleResult();
            this.commit();
        } catch (NoResultException var10) {
            this.rollback();
            LOGGER.debug("No hay resultados para la consulta: " + textoQuery, var10);
        } catch (NonUniqueResultException var11) {
            this.rollback();
            LOGGER.debug("Demasiados resultados para la consulta: " + textoQuery, var11);
            throw new MasDeUnaEntidadEncontradaException("Demasiados resultados para la consulta: " + textoQuery, var11);
        } catch (Exception var12) {
            this.rollback();
            LOGGER.error("Error al ejecutar la consulta: " + var12.getMessage(), var12);
            throw new DAOException("Error al ejecutar la consulta: " + var12.getMessage(), var12);
        } finally {
            this.closeTransaction();
        }

        return result;
    }

    public Object findOneResultJPQL(String textoQuery, Map<String, Object> parameters) throws DAOException {
        Class<T> entityClass = this.getEntityClass();
        LOGGER.debug("findOneResultJPQL: {entity=" + entityClass.getName() + ", textoQuery=" + textoQuery + ", parameters=" + parameters + "}");
        Object result = null;

        try {
            this.beginTransaction();
            Query query = this.em.createQuery(textoQuery);
            if (parameters != null && !parameters.isEmpty()) {
                this.populateQueryParameters(query, parameters);
            }

            result = query.getSingleResult();
            this.commit();
        } catch (NoResultException var11) {
            this.rollback();
            LOGGER.debug("No hay resultados para la consulta: " + textoQuery, var11);
        } catch (NonUniqueResultException var12) {
            this.rollback();
            LOGGER.debug("Demasiados resultados para la consulta: " + textoQuery, var12);
            throw new MasDeUnaEntidadEncontradaException("Demasiados resultados para la consulta: " + textoQuery, var12);
        } catch (Exception var13) {
            this.rollback();
            LOGGER.error("Error al ejecutar la consulta: " + var13.getMessage(), var13);
            throw new DAOException("Error al ejecutar la consulta: " + var13.getMessage(), var13);
        } finally {
            this.closeTransaction();
        }

        return result;
    }

    public List findMultipleResultJPQL(String textoQuery, Map<String, Object> parameters) throws DAOException {
        Class<T> entityClass = this.getEntityClass();
        LOGGER.debug("findMultipleResultJPQL: {entity=" + entityClass.getName() + ", textoQuery=" + textoQuery + ", parameters=" + parameters + "}");
        List results = null;

        try {
            this.beginTransaction();
            Query query = this.em.createQuery(textoQuery);
            if (parameters != null && !parameters.isEmpty()) {
                this.populateQueryParameters(query, parameters);
            }

            results = query.getResultList();
            this.commit();
        } catch (Exception var9) {
            this.rollback();
            LOGGER.error("Error al ejecutar la consulta: " + var9.getMessage(), var9);
            throw new DAOException("Error al ejecutar la consulta: " + var9.getMessage(), var9);
        } finally {
            this.closeTransaction();
        }

        return results;
    }

    public List<T> findAll() throws DAOException {
        Class<T> entityClass = this.getEntityClass();
        List<T> list = null;
        LOGGER.debug("findAll: {entity=" + entityClass.getName() + "}");

        try {
            this.beginTransaction();
            CriteriaQuery cq = this.em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            list = this.em.createQuery(cq).getResultList();
            this.commit();
        } catch (Exception var7) {
            this.rollback();
            LOGGER.error("Error al ejecutar la consulta findAll: ", var7);
            throw new DAOException("Error al ejecutar la consulta findAll: ", var7);
        } finally {
            this.closeTransaction();
        }

        return list;
    }

    public List<T> findAllActivo() throws DAOException {
        Class<T> entityClass = this.getEntityClass();
        List<T> list = null;
        LOGGER.debug("findAll: {entity=" + entityClass.getName() + "}");

        try {
            this.beginTransaction();
            CriteriaBuilder cb = this.em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root e = cq.from(entityClass);
            if (e.get("activo") != null) {
                cq.where(cb.equal(e.get("activo"), "S"));
            }

            Query query = this.em.createQuery(cq);
            list = query.getResultList();
            this.commit();
        } catch (Exception var10) {
            this.rollback();
            LOGGER.error("Error al ejecutar la consulta findAll: ", var10);
            throw new DAOException("Error al ejecutar la consulta findAll: ", var10);
        } finally {
            this.closeTransaction();
        }

        return list;
    }

    public CriteriaQuery<T> getCriteria() {
        CriteriaBuilder cb = this.emf.getCriteriaBuilder();
        return cb.createQuery(this.getEntityClass());
    }

    public void callProcedure(String namedQuery, Map<String, Object> parameters) throws DAOException {
        Class<T> entityClass = this.getEntityClass();
        LOGGER.debug("callProcedure: {entity=" + entityClass.getName() + ", namedQuery=" + namedQuery + ", parameters=" + parameters + "}");

        try {
            this.beginTransaction();
            Query query = this.em.createNamedQuery(namedQuery);
            if (parameters != null && !parameters.isEmpty()) {
                this.populateQueryParameters(query, parameters);
            }

            query.executeUpdate();
            this.commit();
        } catch (NoResultException var10) {
            this.rollback();
            LOGGER.debug("No hay resultados para la consulta: " + namedQuery, var10);
        } catch (NonUniqueResultException var11) {
            this.rollback();
            LOGGER.debug("Demasiados resultados para la consulta: " + namedQuery, var11);
            throw new MasDeUnaEntidadEncontradaException("Demasiados resultados para la consulta: " + namedQuery, var11);
        } catch (Exception var12) {
            this.rollback();
            LOGGER.error("Error al ejecutar la consulta: " + var12.getMessage(), var12);
            throw new DAOException("Error al ejecutar la consulta: " + var12.getMessage(), var12);
        } finally {
            this.closeTransaction();
        }

    }

    public Object executeProcedure(String namedQuery, Map<String, Object> parameters) throws DAOException {
        Class<T> entityClass = this.getEntityClass();
        LOGGER.debug("callProcedureOneField: {entity=" + entityClass.getName() + ", namedQuery=" + namedQuery + ", parameters=" + parameters + "}");
        Object result = null;

        try {
            this.beginTransaction();
            Query query = this.em.createNamedQuery(namedQuery);
            if (parameters != null && !parameters.isEmpty()) {
                this.populateQueryParameters(query, parameters);
            }

            result = query.getSingleResult();
            this.commit();
        } catch (NoResultException var11) {
            this.rollback();
            LOGGER.debug("No hay resultados para la consulta: " + namedQuery, var11);
        } catch (NonUniqueResultException var12) {
            this.rollback();
            LOGGER.debug("Demasiados resultados para la consulta: " + namedQuery, var12);
            throw new MasDeUnaEntidadEncontradaException("Demasiados resultados para la consulta: " + namedQuery, var12);
        } catch (Exception var13) {
            this.rollback();
            LOGGER.error("Error al ejecutar la consulta: " + var13.getMessage(), var13);
            throw new DAOException("Error al ejecutar la consulta: " + var13.getMessage(), var13);
        } finally {
            this.closeTransaction();
        }

        return result;
    }

    public void limpiarEntidadDeCache() throws DAOException {
        Class<T> entityClass = this.getEntityClass();
        LOGGER.debug("limpiarEntidadDeCache: {entity=" + entityClass.getName() + "}");
        Cache cache = this.emf.getCache();
        cache.evict(entityClass);
    }

    public void limpiarCache() throws DAOException {
        Class<T> entityClass = this.getEntityClass();
        LOGGER.debug("limpiarCache: {entity=" + entityClass.getName() + "}");
        Cache cache = this.emf.getCache();
        cache.evictAll();
    }

    /*protected Class<T> getEntityClass() {
        Class<T> entityClass = (Class)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass;
    }*/

    protected Class<T> getEntityClass() {
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            return (Class<T>) paramType.getActualTypeArguments()[0];
        } else if (type instanceof Class) {
            // Manejar el caso en que el tipo genérico es una clase simple
            Class<?> clazz = (Class<?>) type;
            return (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            throw new IllegalStateException("No se pudo determinar el tipo de entidad genérica.");
        }
    }

    private void populateQueryParameters(Query query, Map<String, Object> parameters) {
        if (parameters != null) {
            Iterator var3 = parameters.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)var3.next();
                query.setParameter((String)entry.getKey(), entry.getValue());
            }
        }

    }

    public List<T> findMultipleResultByDate(Date date) throws DAOException {
        Class<T> entityClass = this.getEntityClass();
        boolean nuevoCreado = false;
        List<String> columnas = new LinkedList();
        Field[] var5 = entityClass.getDeclaredFields();
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Field field = var5[var7];
            Column column = (Column)field.getAnnotation(Column.class);
            if (column != null) {
                columnas.add(column.name());
            }
        }

        Iterator var15 = columnas.iterator();

        while(var15.hasNext()) {
            String columna = (String)var15.next();
            if (columna.toUpperCase().equals("CREADO")) {
                nuevoCreado = true;
            }
        }

        String textoQuery = "Select e From " + entityClass.getSimpleName() + " e Where " + (nuevoCreado ? "(e.creado >= :date or e.modificado >= :date)" : "(e.fechaCreacion >= :date or e.fechaModif >= :date)");
        HashMap<String, Object> parameters = new HashMap();
        parameters.put("date", date);
        LOGGER.debug("findMultipleResultJPQL: {entity=" + entityClass.getName() + ", textoQuery=" + textoQuery + ", parameters=" + parameters + "}");
        List results = null;

        try {
            this.beginTransaction();
            Query query = this.em.createQuery(textoQuery);
            if (!parameters.isEmpty()) {
                this.populateQueryParameters(query, parameters);
            }

            results = query.getResultList();
            this.commit();
        } catch (Exception var13) {
            this.rollback();
            LOGGER.error("Error al ejecutar la consulta: " + var13.getMessage(), var13);
            throw new DAOException("Error al ejecutar la consulta: " + var13.getMessage(), var13);
        } finally {
            this.closeTransaction();
        }

        return results;
    }
}