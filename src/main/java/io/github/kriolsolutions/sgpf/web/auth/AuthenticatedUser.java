package io.github.kriolsolutions.sgpf.web.auth;

import java.util.List;

/**
 * Java bean to Authenticate user 
 */
public final class AuthenticatedUser {

    private final String username;
    private final List<String> roles;
    
    public AuthenticatedUser(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}
