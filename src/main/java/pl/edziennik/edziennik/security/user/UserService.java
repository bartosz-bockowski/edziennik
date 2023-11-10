package pl.edziennik.edziennik.security.user;

public interface UserService {
    User findByUserName(String name);
    User findByRestorePasswordCode(String code);

    void saveUser(User user);
    void updateUser(User user);

}
