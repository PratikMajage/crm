package in.smit.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // AUTO-FILLED by database
    private Long id;

    @Column(nullable = false, unique = true)
    // FRONTEND INPUT REQUIRED
    private String username;

    @Column(nullable = false)
    // FRONTEND INPUT REQUIRED (should be hashed before saving)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    // AUTO-FILLED in backend using @PrePersist
    private LocalDateTime createdAt;

    // Relationship: Many Users belong to One Role
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    // FRONTEND INPUT REQUIRED - send role_id
    private Role role;

    // Relationship: One User has One Student profile
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    // AUTO-MANAGED by JPA (don't send from frontend)
    private Student student;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}