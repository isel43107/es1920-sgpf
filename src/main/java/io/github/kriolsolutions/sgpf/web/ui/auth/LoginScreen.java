package io.github.kriolsolutions.sgpf.web.ui.auth;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.github.kriolsolutions.sgpf.backend.auth.AccessControl;
import io.github.kriolsolutions.sgpf.web.auth.AccessControlFactory;

/**
 * UI content when the user is not logged in yet.
 */
@Route("Login")
@PageTitle("Login")
@CssImport("./styles/shared-styles.css")
public class LoginScreen extends FlexLayout {

    private AccessControl accessControl;

    public LoginScreen() {
        accessControl = AccessControlFactory.getInstance().createAccessControl();
        buildUI();
    }

    private void buildUI() {
        setSizeFull();
        setClassName("login-screen");

        // login form, centered in the available part of the screen
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(this::login);
        loginForm.addForgotPasswordListener(
                event -> Notification.show("Hint: same as username"));

        // layout to center login form when there is sufficient screen space
        FlexLayout centeringLayout = new FlexLayout();
        centeringLayout.setSizeFull();
        centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centeringLayout.setAlignItems(Alignment.CENTER);
        centeringLayout.add(loginForm);

        // information text about logging in
        Component loginInformation = buildLoginInformation();

        add(loginInformation);
        add(centeringLayout);
    }

    private Component buildLoginInformation() {
        VerticalLayout loginInformation = new VerticalLayout();
        loginInformation.setClassName("login-information");

        H1 loginInfoHeader = new H1("SGPF");
        loginInfoHeader.setWidth("100%");
        H2 loginInfoH = new H2("Utilizadores de test");
        Span loginInfoText0 = new Span("* tecnico/tecnico");
        Span loginInfoText1 = new Span("* Gestor Financiamento: gfin/gfin");
        Span loginInfoText2 = new Span("* Comissão Financiamento: cfin/cfin");
        loginInfoText0.setWidth("100%");
        
        
        loginInformation.add(loginInfoHeader);
        loginInformation.add(loginInfoH);
        loginInformation.add(loginInfoText0);
        loginInformation.add(loginInfoText1);
        loginInformation.add(loginInfoText2);

        return loginInformation;
    }

    private void login(LoginForm.LoginEvent event) {
        if (accessControl.signIn(event.getUsername(), event.getPassword())) {
            getUI().get().navigate("");
        } else {
            event.getSource().setError(true);
        }
    }
}
