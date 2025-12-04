package in.smit.crm.service;

import in.smit.crm.entity.Role;
import in.smit.crm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Role entity
 * Handles business logic for role management
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Get all roles (for dropdown in forms)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Get role by ID (for edit form)
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    // Save or update role (for add and edit forms)
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    // Delete role by ID
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    // Find role by name (useful for default roles)
    public Optional<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    // Check if role name already exists (for validation)
    public boolean roleExists(String roleName) {
        return roleRepository.existsByRoleName(roleName);
    }
}