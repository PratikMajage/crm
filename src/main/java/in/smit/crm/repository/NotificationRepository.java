package in.smit.crm.repository;

import in.smit.crm.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Notification entity
 * Handles notification queries
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Find notifications by title (partial match, case-insensitive)
    List<Notification> findByTitleContainingIgnoreCase(String title);

    // Find notifications created on a specific date
    List<Notification> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find recent notifications (last N days)
    @Query("SELECT n FROM Notification n WHERE n.createdDate >= :startDate ORDER BY n.createdDate DESC")
    List<Notification> findRecentNotifications(@Param("startDate") LocalDateTime startDate);

    // Find all notifications ordered by date (newest first)
    List<Notification> findAllByOrderByCreatedDateDesc();

    // Find latest N notifications
    @Query(value = "SELECT n FROM Notification n ORDER BY n.createdDate DESC")
    List<Notification> findLatestNotifications();

    // Count total notifications
    @Query("SELECT COUNT(n) FROM Notification n")
    long countTotalNotifications();

    // Search notifications by title or message content
    @Query("SELECT n FROM Notification n WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(n.message) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Notification> searchNotifications(@Param("keyword") String keyword);
}