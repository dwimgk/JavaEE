package wat.jeet.webregistration_lab2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class UsersService {
    private static final String PU_NAME = "my_persistence_unit";
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME);

    private EntityManager em() { return emf.createEntityManager(); }
    public void close() { emf.close(); }

    public List<UsersLab> findAll() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT u FROM UsersLab u ORDER BY u.id", UsersLab.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public int nextId() {
        EntityManager em = em();
        try {
            Integer maxId = em.createQuery("SELECT MAX(u.id) FROM UsersLab u", Integer.class)
                              .getSingleResult();
            return (maxId == null ? 1 : maxId + 1);
        } finally {
            em.close();
        }
    }

    public void create(String name, String email, int age) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();

            UsersLab u = new UsersLab();
            u.setId(nextId()); // because your table ID is not auto-increment
            u.setName(name);
            u.setEmail(email);
            u.setAge(age);
            u.setRegistrationDate(Date.valueOf(LocalDate.now()));

            em.persist(u);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public boolean deleteById(int id) {
        EntityManager em = em();
        try {
            em.getTransaction().begin();
            UsersLab u = em.find(UsersLab.class, id);
            if (u == null) {
                em.getTransaction().commit();
                return false;
            }
            em.remove(u);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
