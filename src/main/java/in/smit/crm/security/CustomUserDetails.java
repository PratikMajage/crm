package in.smit.crm.security;

import in.smit.crm.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Custom UserDetails implementation
 * This class wraps our User entity to work with Spring Security
 */
public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Return user's role as authority
    // Spring Security uses this to check permissions
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert role name to Spring Security authority format
        // e.g., "Admin" becomes "ROLE_ADMIN"
        String roleName = "ROLE_" + user.getRole().getRoleName().toUpperCase();
        return Collections.singleton(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // Account status methods - return true to keep account active
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Get the actual user object (useful in controllers)
    public User getUser() {
        return user;
    }
}