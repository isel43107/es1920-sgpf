package io.github.kriolsolutions.sgpf.web.ui;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Meta;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.shared.communication.PushMode;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.Theme;


@PageTitle("OBiz")
@Meta(name = "author", content = "KriolOS")
@Meta(name = "description", content = "Open Business Platform")
@PWA(name = "Sistema de Gestão de Projeto de Financiamento", shortName = "SGPF")
@Viewport("width=device-width, initial-scale=1")
@BodySize(height = "100vh", width = "100vw")
@Theme(value = "sgpf")
@Push(value = PushMode.MANUAL, transport = Transport.WEBSOCKET)
public class ApplicationConfig implements AppShellConfigurator{

}