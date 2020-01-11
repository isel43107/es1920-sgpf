package io.github.kriolsolutions.sgpf.web.ui.about;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.github.kriolsolutions.sgpf.web.ui.MainLayout;

@Route(value = "About", layout = MainLayout.class)
@PageTitle("About")
public class AboutView extends HorizontalLayout {

    public static final String VIEW_NAME = "About";

    public AboutView() {
        setSizeFull();
        //setJustifyContentMode(JustifyContentMode.START);
        //setAlignItems(Alignment.START);
        
        add(new H1(VaadinIcon.INFO_CIRCLE.create(), new Span( "Informação sobre SGPF 2019 " )) );
        add(new H3( "Autores" ));
        add(new Paragraph( "Paulo Borges" ));
        add(new Paragraph( "Rafael Delgado" ));
    }
}
