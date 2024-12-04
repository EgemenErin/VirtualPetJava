package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

/**
 * A generic DAO class providing basic CRUD operations.
 *
 * @param <T> the type of the entity.
 */
public class GenericDAO<T> {

    private Class<T> type;

    /**
     * Constructor that sets the type of the entity.
     *
     * @param type the class type of the entity.
     */
    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    /**
     * Saves a new entity to the database.
     *
     * @param entity the entity to save.
     */
    public void save(T entity) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(entity); // Use persist() in Hibernate 6
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing entity in the database.
     *
     * @param entity the entity to update.
     * @return the merged entity.
     */
    public T update(T entity) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction tx = null;
        T mergedEntity = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            mergedEntity = (T) session.merge(entity); // Use merge() in Hibernate 6
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        return mergedEntity;
    }

    /**
     * Deletes an entity from the database.
     *
     * @param entity the entity to delete.
     */
    public void delete(T entity) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.remove(entity); // Use remove() in Hibernate 6
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    /**
     * Finds an entity by its ID.
     *
     * @param id the ID of the entity.
     * @return the found entity or null if not found.
     */
    public T findById(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        T entity = null;

        try (Session session = sessionFactory.openSession()) {
            entity = session.get(type, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}
