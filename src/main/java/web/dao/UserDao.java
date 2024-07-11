package web.dao;


import web.model.User;
import java.util.List;

public interface UserDao {

    List<User> getAll();

    User getUserById(long id);


    void save(User user);


    void update(User user);


    void delete(User user);
}
