package pl.edziennik.edziennik.security.user;

public interface UserService {
    User findByUserName(String name);

    void saveUser(User user);

}
