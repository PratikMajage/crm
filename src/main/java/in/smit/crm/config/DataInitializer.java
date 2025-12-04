package in.smit.crm.config;

import in.smit.crm.entity.Role;
import in.smit.crm.entity.User;
import in.smit.crm.repository.RoleRepository;
import in.smit.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Data Initializer (Optional)
 * Creates default roles and admin user when application starts
 * REMOVE THIS IN PRODUCTION - Only for testing!
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Create roles if they don't exist
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setRoleName("ADMIN");
            roleRepository.save(adminRole);

            // Role studentRole = new Role();
            // studentRole.setRoleName("STUDENT");
            // roleRepository.save(studentRole);

            // System.out.println("✅ Default roles created: ADMIN, STUDENT");
            System.out.println("✅ Default roles created: ADMIN");
        }

        // Create default admin user if doesn't exist
        if (!userRepository.existsByUsername("admin")) {
            Role adminRole = roleRepository.findByRoleName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Default password
            admin.setRole(adminRole);

            userRepository.save(admin);
            System.out.println("✅ Default admin user created");
            System.out.println("   Username: admin");
            System.out.println("   Password: admin123");
            System.out.println("   ⚠️  CHANGE THIS PASSWORD IN PRODUCTION!");
        }

        // Create default student user for testing
        
        // if (!userRepository.existsByUsername("student")) {
        //     Role studentRole = roleRepository.findByRoleName("STUDENT")
        //             .orElseThrow(() -> new RuntimeException("Student role not found"));

        //     User student = new User();
        //     student.setUsername("student");
        //     student.setPassword(passwordEncoder.encode("student123"));
        //     student.setRole(studentRole);

        //     userRepository.save(student);
        //     System.out.println("✅ Default student user created");
        //     System.out.println("   Username: student");
        //     System.out.println("   Password: student123");
        // }
    }
}