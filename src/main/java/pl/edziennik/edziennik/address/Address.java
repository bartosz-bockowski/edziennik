package pl.edziennik.edziennik.address;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^[0-9]{2}-[0-9]{3}$", message = "validation.error.regex.wrongFormat")
    private String postCode = " - ";
    @NotEmpty(message = "validation.error.cannotBeEmpty")
    private String city = " - ";
    @NotEmpty(message = "validation.error.cannotBeEmpty")
    private String street = " - ";
    @NotEmpty(message = "validation.error.cannotBeEmpty")
    private String homeNumber = "0";
    private String apartment = "";
}
