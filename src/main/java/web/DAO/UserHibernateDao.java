package web.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaUpdate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserHibernateDao implements UserDao{


/*
    private SessionFactory sessionFactory;
    public UserHibernateDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
*/
    private EntityManager currentSession;

    public UserHibernateDao(EntityManager entityManager) {
        this.currentSession = entityManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> read() {
        TypedQuery<User> query= currentSession.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> readRole() {
        TypedQuery<Role> query= currentSession.createQuery("from Role", Role.class);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<Role> getRoles(String[] ids) {
        TypedQuery<Role> query= currentSession.createQuery("from Role where id = :id", Role.class);
        Set<Role> roles = new HashSet<>();
        Arrays.stream(ids).forEach(roleId -> {query.setParameter("id", Long.parseLong(roleId)); roles.add(query.getSingleResult());});
        return roles;
    }

    @Override
    public void insert(User user) {
        currentSession.persist(user);
    }

    @Override
    public void update(User user) {
        currentSession.merge(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public User read(Long id) {
        TypedQuery<User> query= currentSession.createQuery("from User where id = :id", User.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void delete(Long id) {
        currentSession.remove(read(id));
    }

    @Override
    @SuppressWarnings("unchecked")
    public UserDetails findByUsername(String login) {
        TypedQuery<User> query= currentSession.createQuery("from User where login = :login", User.class);
        query.setParameter("login", login);
        User singleResult = query.getSingleResult();
        return singleResult;
    }

/*
    @Override
    public void deleteAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        List<User> users = currentSession.createQuery("from User").getResultList();
        for (User user : users) {
            currentSession.delete(user);
        }
    }
*/

}
