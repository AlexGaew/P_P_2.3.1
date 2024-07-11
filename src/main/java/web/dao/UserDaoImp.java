package web.dao;


import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private final EntityManager entityManager;


    public UserDaoImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<User> getAll() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public User getUserById(long id) {
        Optional<User> user = Optional.ofNullable(entityManager.find(User.class, id));
        return user.orElseThrow();
    }


    @Override
    public void save(User user) {
        entityManager.persist(user);

    }


    @Override
    public void update(User user) {
        if (entityManager.find(User.class, user.getId()) == null) {
            throw new EntityNotFoundException();
        } else entityManager.merge(user);

    }


    @Override
    public void delete(User user) {
        if (entityManager.contains(getUserById(user.getId()))) {
            entityManager.remove(getUserById(user.getId()));
        } else {
            throw new EntityNotFoundException();
        }


    }
}