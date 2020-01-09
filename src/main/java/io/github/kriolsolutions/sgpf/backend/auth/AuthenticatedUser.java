package io.github.kriolsolutions.sgpf.backend.auth;

import java.util.List;

/**
 * Java bean to Authenticate user 
 */
public final class AuthenticatedUser {

    private final Long id;
    private final String username;
    private final List<String> roles;
    
    public AuthenticatedUser(Long id, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
    
    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }
}
