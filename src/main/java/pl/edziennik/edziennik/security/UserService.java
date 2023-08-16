package pl.edziennik.edziennik.security;

public interface UserService {
    User findByUserName(String name);

    void saveUser(User user);

}
