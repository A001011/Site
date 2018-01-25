package hello;

public interface UserService {
    void create(User user);

        void delete(User user);

        void update(User user);

        User getById(long id);

        User getByEmail(String email);
}
