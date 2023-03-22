package io.github.kriolsolutions.sgpf.backend.auth;

import java.io.Serializable;
import java.util.List;

/**
 * Provide Authentication and Authorization checks.
 */
public interface AccessControl extends Serializable {

    public boolean signIn(String username, String password);

    public void signOut();

    public boolean isUserSignedIn();

    public boolean isUserInRole(String role);

    public String getPrincipalName();
    
    public Long getUserId();
    
    public List<String> getUserRoles();
}
