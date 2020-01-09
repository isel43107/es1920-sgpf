/*
 * Copyright 2020 kriolSolutions.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kriolsolutions.sgpf.web.ui.projeto;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.shared.Registration;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;

/**
 *
 * @author pauloborges
 */
public class ProjectoOptions {

    public Button createOptionsButton(Projeto projeto) {

        Button button = new Button(new Icon(VaadinIcon.OPTIONS));

        switch (projeto.getProjEstado()) {

            case EM_CANDIDATURA:
                button.addClickListener(clickEvent -> {
                    candidaturaOptions(button, projeto);
                });
                break;

            case DESPACHO_ABERTURA:
                button.addClickListener(clickEvent -> {
                    despachoAberturaOptions(button, projeto);
                });
                break;

            case PARECER_TECNICO:
                button.addClickListener(clickEvent -> {
                    despachoAberturaOptions(button, projeto);
                });
                break;

            case DESPACHO_FINANCIAMENTO:
                button.addClickListener(clickEvent -> {
                    despachoFinanciamentoOptions(button, projeto);
                });
                break;
            case DESPACHO_REFORCO:
                button.addClickListener(clickEvent -> {
                    Notification.show("DESPACHO_REFORCO sera implementado brevemente");
                });
                break;
            case EM_PAGAMENTO:
                button.addClickListener(clickEvent -> {
                    Notification.show("EM_PAGAMENTO sera implementado brevemente");
                });
                break;
            case PROJETO_ARQUIVADO:
                button.addClickListener(clickEvent -> {
                    Notification.show("PROJETO_ARQUIVADO sera implementado brevemente");
                });
                break;
            case PROJETO_REJEITADO:
                button.addClickListener(clickEvent -> {
                    Notification.show("PROJETO_REJEITADO sera implementado brevemente");
                });
                break;
            case PROJETO_SUSPENSO:
                button.addClickListener(clickEvent -> {
                    Notification.show("PROJETO_SUSPENSO sera implementado brevemente");
                });
                break;
        }

        //TODO Depende do estado do projeto
        return button;
    }

    //TODO - visto a copia do codio para cria opções podemos de certeza
    //Sera possivel criar uma interface/abstrac que defina o conceito de Options/Opções
    //Action 
    //  - CandidaturaAction {Aceitar, Abrir, Arquivar}
    //  - DespachoAberturaAction {Aprovar, Rejeitar}
    //
    private Component candidaturaOptions(Component component, Projeto projeto) {
        ContextMenu contextMenu = new ContextMenu(component);
        contextMenu.addItem("Abrir projeto",
                event -> Notification.show("Abrir projeto sera implementado brevemente"));

        contextMenu.addItem("Arquivar projeto",
                event -> Notification.show("Arquivar projeto sera implementado brevemente"));

        contextMenu.addItem("Editar projeto",
                event -> Notification.show("Editar projeto sera implementado brevemente"));
        
        contextMenu.setVisible(true);

        //workaround for issue vaadin-context-menu-flow/issues/47
        contextMenu.addAttachListener(a -> contextMenu.setTarget(component));

        return contextMenu;
    }

    private Component despachoAberturaOptions(Component component, Projeto projeto) {
        ContextMenu contextMenu = new ContextMenu(component);
        contextMenu.addItem("Emitir despacho abertura",
                event -> Notification.show(
                        "Emitir despacho abertura, devera abrir o formulario de abertura. "
                        + "Formulario DespachoAberturaForm devera contem as opções: "
                        + "APROVADO, REJEITADO(NAO TEM esta opção)"));
        contextMenu.setVisible(true);
        
        //workaround for issue vaadin-context-menu-flow/issues/47
        contextMenu.addAttachListener(a -> contextMenu.setTarget(component));

        return contextMenu;
    }

    private Component despachoFinanciamentoOptions(Component component, Projeto projeto) {
        ContextMenu contextMenu = new ContextMenu(component);
        contextMenu.addItem("Emitir despacho financiamento",
                event -> Notification.show(
                        "Devera abrir o formulario do despacho de acordo com tipo projeto: "
                        + "DespachoFinIncentivoForm, DespachoFinBonificacaoForm"));
        contextMenu.setVisible(true);
        
        //workaround for issue vaadin-context-menu-flow/issues/47
        contextMenu.addAttachListener(a -> contextMenu.setTarget(component));

        return contextMenu;
    }

    /*
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
    */
}

abstract class STActionEvent extends ComponentEvent<Button> {

    private Projeto projeto;

    protected STActionEvent(Button source, Projeto projeto) {
        super(source, false);
        this.projeto = projeto;
    }

    public Projeto getProjeto() {
        return projeto;
    }
}

class AbrirEvent extends STActionEvent {

    public AbrirEvent(Button source, Projeto projeto) {
        super(source, projeto);
    }
}
