package in.smit.crm.config;

import in.smit.crm.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Configuration
 * Controls authentication and authorization in the application
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Password Encoder Bean
     * BCrypt is a strong hashing algorithm for passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication Provider
     * Connects UserDetailsService with PasswordEncoder
     */
    // @Bean
    // public DaoAuthenticationProvider authenticationProvider() {
    //     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //     authProvider.setUserDetailsService(userDetailsService);
    //     authProvider.setPasswordEncoder(passwordEncoder());
    //     return authProvider;
    // }

    // ✅ Use AuthenticationManager instead of deprecated DaoAuthenticationProvider
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Security Filter Chain
     * Defines URL access rules and login/logout configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(userDetailsService)
                // Authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Public URLs (anyone can access)
                        .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()

                        // Admin-only URLs
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Student-only URLs
                        .requestMatchers("/student/**").hasRole("STUDENT")

                        // All other URLs require authentication
                        .anyRequest().authenticated())

                // Login configuration
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page URL
                        .loginProcessingUrl("/login") // URL to submit username & password
                        .defaultSuccessUrl("/dashboard", true) // Redirect after successful login
                        .failureUrl("/login?error=true") // Redirect after failed login
                        .permitAll())

                // Logout configuration
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL to trigger logout
                        .logoutSuccessUrl("/login?logout=true") // Redirect after logout
                        .invalidateHttpSession(true) // Clear session
                        .deleteCookies("JSESSIONID") // Remove session cookie
                        .permitAll())

                // Exception handling
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied") // Show this page when access is denied
                );
        return http.build();
    }
}