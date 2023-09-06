package pl.edziennik.edziennik.school.admin;

import lombok.Getter;
import lombok.Setter;
import pl.edziennik.edziennik.security.user.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    private String firstName;
    private String lastName;
    private boolean active;
}
