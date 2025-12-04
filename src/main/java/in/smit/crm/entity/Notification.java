package in.smit.crm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // AUTO-FILLED by database
    private Long id;

    @Column(nullable = false)
    // FRONTEND INPUT REQUIRED
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    // FRONTEND INPUT REQUIRED
    private String message;

    @Column(name = "created_date", nullable = false, updatable = false)
    // AUTO-FILLED in backend using @PrePersist
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}