package in.smit.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // AUTO-FILLED by database
    private Long id;

    @Column(nullable = false, unique = true)
    // FRONTEND INPUT REQUIRED - e.g., "Admin", "Student"
    private String roleName;

    // Relationship: One Role can have many Users
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    // AUTO-MANAGED by JPA (don't send from frontend)
    private List<User> users;
}