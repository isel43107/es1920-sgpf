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
import com.vaadin.flow.theme.lumo.Lumo;

@Viewport("width=device-width, initial-scale=1")
@PageTitle("OBiz (Open Business Platform)")
@BodySize(height = "100vh", width = "100vw")
@Meta(name = "author", content = "KriolOS")
//@Inline(wrapping = Inline.Wrapping.AUTOMATIC,position = Inline.Position.APPEND,target = TargetElement.BODY,value = "custom.html")
@Theme(variant = Lumo.LIGHT)
@PWA(name = "Sistema de Gestão de Projeto de Financiamento", shortName = "SGPF")
@Push(value = PushMode.MANUAL, transport = Transport.WEBSOCKET)
public class ApplicationConfig implements AppShellConfigurator{

}