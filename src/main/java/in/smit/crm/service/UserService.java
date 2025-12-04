package in.smit.crm.service;

import in.smit.crm.entity.User;
import in.smit.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for User entity
 * NOW WITH PASSWORD ENCODING!
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // BCrypt encoder

    // Get all users (for list.html)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID (for edit.html)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Save or update user (for add.html and edit.html)
    public User saveUser(User user) {
        // IMPORTANT: Hash password before saving to database
        // Only encode if password is not already encoded (for updates)
        if (user.getId() == null || !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    // Register new user with password encoding
    public User registerUser(User user) {
        // Check if username already exists
        if (usernameExists(user.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Delete user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Find user by username (for login)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Check if username exists (for registration validation)
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    // Get users by role (filter students or admins)
    public List<User> getUsersByRoleId(Long roleId) {
        return userRepository.findByRoleId(roleId);
    }

    // Count total users
    public long getTotalUsers() {
        return userRepository.count();
    }
}