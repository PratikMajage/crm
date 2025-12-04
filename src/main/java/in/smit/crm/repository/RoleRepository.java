package in.smit.crm.repository;

import in.smit.crm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Role entity
 * JpaRepository provides basic CRUD operations automatically
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Custom query method to find role by name
    // Spring Data JPA automatically implements this based on method name
    Optional<Role> findByRoleName(String roleName);

    // Check if role exists by name
    boolean existsByRoleName(String roleName);
}