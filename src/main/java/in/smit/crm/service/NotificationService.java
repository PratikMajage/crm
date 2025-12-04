package in.smit.crm.service;

import in.smit.crm.entity.Notification;
import in.smit.crm.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for Notification entity
 * Handles notification management logic
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Get all notifications (for list.html)
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Get notification by ID (for edit.html and view details)
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    // Save or update notification (for add.html and edit.html)
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    // Delete notification by ID
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    // Get all notifications ordered by newest first
    public List<Notification> getAllNotificationsOrderByDate() {
        return notificationRepository.findAllByOrderByCreatedDateDesc();
    }

    // Get recent notifications (last 7 days)
    public List<Notification> getRecentNotifications() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        return notificationRepository.findRecentNotifications(sevenDaysAgo);
    }

    // Search notifications by keyword
    public List<Notification> searchNotifications(String keyword) {
        return notificationRepository.searchNotifications(keyword);
    }

    // Count total notifications (for dashboard)
    public long getTotalNotifications() {
        return notificationRepository.count();
    }

    // Create and broadcast notification
    public Notification createNotification(String title, String message) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        return notificationRepository.save(notification);
    }
}