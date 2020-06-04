package web.DAO;

import org.springframework.security.core.userdetails.UserDetails;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    List<User> read();
    List<Role> readRole();
    Set<Role> getRoles(String[] ids);
    void insert(User user);
    void update(User user);
    User read(Long id);
    void delete(Long id);
    public UserDetails findByUsername(String username);
/*
    void deleteAll();
*/

}
