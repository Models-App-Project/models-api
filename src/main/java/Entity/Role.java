package Entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, length = 20)
    private String name;

    @ManyToMany(mappedBy = )
    private List<User> usuarios;
}
