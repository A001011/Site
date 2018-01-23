package hello;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@Repository
public class UserServiceImpl implements UserService{
@PersistenceContext
private EntityManager entityManager;
    @Override
    public void create(User user) {
        entityManager.persist(user);
    }
}
