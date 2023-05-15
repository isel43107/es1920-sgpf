package io.github.kriolsolutions.sgpf.web.auth;

import io.github.kriolsolutions.sgpf.backend.auth.AuthenticatedUser;
import io.github.kriolsolutions.sgpf.backend.auth.AccessControl;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import java.util.Arrays;
import java.util.List;

/**
 * Default mock implementation of {@link AccessControl}. 
 * This implementation accepts any string as a user if the password is the same string, and
 * considers the user "admin" as the only administrator.
 */
public class BasicAccessControl implements AccessControl {
    
    /**
     * The attribute key used to store the username in the session.
     */
    public static final String CURRENT_USER_SESSION_ATTRIBUTE_KEY = 
            AuthenticatedUser.class.getCanonicalName();

    @Override
    public boolean signIn(String username, String password) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        if (!username.equals(password)) {
            return false;
        }
        
        List<String> roles = Arrays.asList("TECNICO", "GESTOR_FINANCIAMENTO", "COMISSAO_FINANCIAMENTO");
        
        AuthenticatedUser AuthenticatedUser = new AuthenticatedUser(1L, username, roles);

        set(AuthenticatedUser);
        return true;
    }

    @Override
    public boolean isUserSignedIn() {
        return (get() != null);
    }

    @Override
    public boolean isUserInRole(String role) {
        if ("admin".equals(role)) {
            // Only the "admin" user is in the "admin" role
            return getPrincipalName().equals("admin");
        }

        return true;
    }

    @Override
    public String getPrincipalName() {
        return get().getUsername();
    }

    @Override
    public void signOut() {
        VaadinSession.getCurrent().getSession().invalidate();
        UI.getCurrent().navigate("");
    }
    
    /**
     * Returns the name of the current user stored in the current session, or an
     * empty string if no user name is stored.
     * 
     * @return 
     * @throws IllegalStateException
     *             if the current session cannot be accessed.
     */
    public static AuthenticatedUser get() {
        AuthenticatedUser currentUser = (AuthenticatedUser) getCurrentRequest().getWrappedSession()
                .getAttribute(CURRENT_USER_SESSION_ATTRIBUTE_KEY);
        if (currentUser == null) {
            throw new IllegalStateException("Current session has no AuthenticatedUser ");
        } else {
            return currentUser;
        }
    }

    /**
     * Sets the name of the current user and stores 
     * it in the current session.Using a {@code null} 
     * username will remove the username from the session.
     *
     * @param authenticatedUser 
     * @throws IllegalStateException
     *             if the current session cannot be accessed.
     */
    public static void set(AuthenticatedUser authenticatedUser) {
        if (authenticatedUser == null) {
            getCurrentRequest().getWrappedSession().removeAttribute(
                    CURRENT_USER_SESSION_ATTRIBUTE_KEY);
        } else {
            getCurrentRequest().getWrappedSession().setAttribute(
                    CURRENT_USER_SESSION_ATTRIBUTE_KEY, authenticatedUser);
        }
    }

    private static VaadinRequest getCurrentRequest() {
        VaadinRequest request = VaadinService.getCurrentRequest();
        if (request == null) {
            throw new IllegalStateException("No request bound to current thread.");
        }
        return request;
    }

    @Override
    public Long getUserId() {
        return get().getId();
    }

    @Override
    public List<String> getUserRoles() {
       return get().getRoles();
    }
}
