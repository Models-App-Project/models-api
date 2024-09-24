package Entity;

import java.io.Serializable;
import java.util.List;
import.Entity.
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class User implements Serializable {

    private Long id;
    private String username;

    private String password;
    @ManyToMany(mappedBy = )
    private List<Role> roles;
}
