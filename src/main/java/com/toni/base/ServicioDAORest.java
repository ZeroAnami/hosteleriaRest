package com.toni.base;

import com.toni.base.exception.InvalidIdObjectException;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ServicioDAORest<T> {
    void beginTransaction();

    EntityTransaction getTransaction();

    void closeTransaction();

    void commitAndCloseTransaction();

    void joinTransaction();

    void commit();

    void rollback();

    void refresh(T var1);

    void flush();

    void guardar(T var1) throws DAOException;

    void save(T var1) throws DAOException;

    void eliminar(T var1) throws DAOException;

    void delete(T var1) throws DAOException;

    T modificar(T var1) throws DAOException;

    T update(T var1) throws DAOException;

    T getById(Short var1) throws DAOException;

    T getById(Integer var1) throws DAOException;

    T getById(Long var1) throws DAOException;

    T getById(BigInteger var1) throws DAOException;

    T getById(String var1) throws DAOException;

    T getById(Object var1) throws InvalidIdObjectException, DAOException;

    T findOneResult(Query var1) throws DAOException;

    Object findOneResultJPQL(String var1) throws DAOException;

    Object findOneResultJPQL(String var1, Map<String, Object> var2) throws DAOException;

    List findMultipleResult(Query var1) throws DAOException;

    List findMultipleResultJPQL(String var1, Map<String, Object> var2) throws DAOException;

    List<T> findAll() throws DAOException;

    List<T> findAllActivo() throws DAOException;

    CriteriaQuery<T> getCriteria();

    void limpiarEntidadDeCache() throws DAOException;

    void limpiarCache() throws DAOException;

    void callProcedure(String var1, Map<String, Object> var2) throws DAOException;

    Object executeProcedure(String var1, Map<String, Object> var2) throws DAOException;

    List<T> findMultipleResultByDate(Date var1) throws DAOException;

    int update(String var1, Map<String, Object> var2) throws Exception;
}
