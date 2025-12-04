package in.smit.crm.repository;

import in.smit.crm.entity.User;
import in.smit.crm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity
 * Handles user authentication and management queries
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username (for login)
    Optional<User> findByUsername(String username);

    // Check if username already exists (for registration validation)
    boolean existsByUsername(String username);

    // Find all users by role (e.g., all students or all admins)
    List<User> findByRole(Role role);

    // Find users by role ID
    List<User> findByRoleId(Long roleId);
}