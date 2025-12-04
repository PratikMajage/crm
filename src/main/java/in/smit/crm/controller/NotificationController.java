package in.smit.crm.controller;

import in.smit.crm.entity.Notification;
import in.smit.crm.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Notification Controller
 * Handles notification management
 * Admin creates notifications, students view them
 */
@Controller
@RequestMapping("/admin/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * Show all notifications
     * URL: GET /admin/notifications
     * Thymeleaf: templates/admin/notifications.html (list.html)
     */
    @GetMapping
    public String listNotifications(Model model) {
        model.addAttribute("notifications", notificationService.getAllNotificationsOrderByDate());
        return "admin/notifications";
    }

    /**
     * Show create notification form
     * URL: GET /admin/notifications/add
     * Thymeleaf: templates/admin/notification-add.html (add.html)
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("notification", new Notification());
        return "admin/notification-add";
    }

    /**
     * Save new notification
     * URL: POST /admin/notifications/save
     * Redirects to: /admin/notifications
     */
    @PostMapping("/save")
    public String saveNotification(@ModelAttribute("notification") Notification notification,
            RedirectAttributes redirectAttributes) {
        try {
            notificationService.saveNotification(notification);
            redirectAttributes.addFlashAttribute("success", "Notification created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/notifications";
    }

    /**
     * Show edit notification form
     * URL: GET /admin/notifications/edit/{id}
     * Thymeleaf: templates/admin/notification-edit.html (edit.html)
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        try {
            Notification notification = notificationService.getNotificationById(id);
            model.addAttribute("notification", notification);
            return "admin/notification-edit";
        } catch (Exception e) {
            model.addAttribute("error", "Notification not found!");
            return "redirect:/admin/notifications";
        }
    }

    /**
     * Update notification
     * URL: POST /admin/notifications/update
     * Redirects to: /admin/notifications
     */
    @PostMapping("/update")
    public String updateNotification(@ModelAttribute("notification") Notification notification,
            RedirectAttributes redirectAttributes) {
        try {
            notificationService.saveNotification(notification);
            redirectAttributes.addFlashAttribute("success", "Notification updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/notifications";
    }

    /**
     * Delete notification
     * URL: GET /admin/notifications/delete/{id}
     * Redirects to: /admin/notifications
     */
    @GetMapping("/delete/{id}")
    public String deleteNotification(@PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {
        try {
            notificationService.deleteNotification(id);
            redirectAttributes.addFlashAttribute("success", "Notification deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/notifications";
    }

    /**
     * View notification details
     * URL: GET /admin/notifications/view/{id}
     * Thymeleaf: templates/admin/notification-view.html
     */
    @GetMapping("/view/{id}")
    public String viewNotification(@PathVariable("id") Long id, Model model) {
        try {
            Notification notification = notificationService.getNotificationById(id);
            model.addAttribute("notification", notification);
            return "admin/notification-view";
        } catch (Exception e) {
            model.addAttribute("error", "Notification not found!");
            return "redirect:/admin/notifications";
        }
    }
}