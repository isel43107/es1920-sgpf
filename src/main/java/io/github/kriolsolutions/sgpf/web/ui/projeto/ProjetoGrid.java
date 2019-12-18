/*
 * Copyright 2019 kriolSolutions.
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

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.Projeto;
import java.text.DecimalFormat;
import java.util.Comparator;

/**
 *
 * @author pauloborges
 */
public class ProjetoGrid extends Grid<Projeto> {

    public ProjetoGrid() {

        setSizeFull();

        addColumn(Projeto::getProjNumero).setHeader("Numero projeto")
                .setFlexGrow(10).setSortable(true).setKey("projNumero");

        addColumn(Projeto::getProjDesignacao).setHeader("Designação projeto")
                .setFlexGrow(20).setSortable(true).setKey("projDesignacao");

        addColumn(Projeto::getProjDesignacao).setHeader("Estado projeto")
                .setFlexGrow(20).setSortable(true).setKey("projEstado");

        addColumn(Projeto::getProjDesignacao).setHeader("Tipo projeto")
                .setFlexGrow(20).setSortable(true).setKey("projTipo");

        final DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);

        addColumn(product -> decimalFormat.format(product.getProjMontanteSolicitado()))
                .setHeader("Mnt. Solicitado").setTextAlign(ColumnTextAlign.END)
                .setComparator(Comparator.comparing(Projeto::getProjMontanteSolicitado))
                .setFlexGrow(3).setKey("projMontanteSolicitado");

        addColumn(Projeto::getPromotorDesignacao).setHeader("Promotor projeto")
                .setFlexGrow(20).setSortable(true).setKey("promotorDesignacao");

        addColumn(Projeto::getContatoNome).setHeader("Contato projeto")
                .setFlexGrow(20).setSortable(true).setKey("contatoNome");

        addComponentColumn(item -> createOptionsButton(item))
                .setHeader("Ações").setKey("acoesDisponiveis");;

        // If the browser window size changes, check if all columns fit on screen
        // (e.g. switching from portrait to landscape mode)
        UI.getCurrent().getPage().addBrowserWindowResizeListener(
                e -> setColumnVisibility(e.getWidth()));
    }

    private void setColumnVisibility(int width) {
        if (width > 800) {
            getColumnByKey("projNumero").setVisible(true);
            getColumnByKey("projDesignacao").setVisible(true);
            getColumnByKey("projEstado").setVisible(true);
            getColumnByKey("projTipo").setVisible(true);
            getColumnByKey("projMontanteSolicitado").setVisible(true);
            getColumnByKey("promotorDesignacao").setVisible(true);
            getColumnByKey("contatoNome").setVisible(true);
            getColumnByKey("acoesDisponiveis").setVisible(true);
        } else if (width > 550) {
            getColumnByKey("projNumero").setVisible(true);
            getColumnByKey("projDesignacao").setVisible(true);
            getColumnByKey("projEstado").setVisible(true);
            getColumnByKey("projTipo").setVisible(false);
            getColumnByKey("projMontanteSolicitado").setVisible(false);
            getColumnByKey("promotorDesignacao").setVisible(true);
            getColumnByKey("contatoNome").setVisible(false);
            getColumnByKey("acoesDisponiveis").setVisible(true);
        } else {
            getColumnByKey("projNumero").setVisible(true);
            getColumnByKey("projDesignacao").setVisible(true);
            getColumnByKey("projEstado").setVisible(true);
            getColumnByKey("projTipo").setVisible(false);
            getColumnByKey("projMontanteSolicitado").setVisible(false);
            getColumnByKey("promotorDesignacao").setVisible(false);
            getColumnByKey("contatoNome").setVisible(false);
            getColumnByKey("acoesDisponiveis").setVisible(true);
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // fetch browser width
        UI.getCurrent().getInternals().setExtendedClientDetails(null);
        UI.getCurrent().getPage().retrieveExtendedClientDetails(e -> {
            setColumnVisibility(e.getBodyClientWidth());
        });
    }

    public Projeto getSelectedRow() {
        return asSingleSelect().getValue();
    }

    public void refresh(Projeto product) {
        getDataCommunicator().refresh(product);
    }

    private Button createOptionsButton(Projeto projeto) {
        Button button = new Button(new Icon(VaadinIcon.OPTIONS));

        switch (projeto.getProjEstado()) {

            case EM_CANDIDATURA:
                button.addClickListener(clickEvent -> {
                    candidaturaOptions(projeto);
                });
                break;

            case DESPACHO_ABERTURA:
                button.addClickListener(clickEvent -> {
                    despachoAberturaOptions(projeto);
                });
                break;

            case PARECER_TECNICO:
                button.addClickListener(clickEvent -> {
                    despachoAberturaOptions(projeto);
                });
                break;
                
            case DESPACHO_FINANCIAMENTO:
                button.addClickListener(clickEvent -> {
                    despachoFinanciamentoOptions(projeto);
                });
                break;
            case DESPACHO_REFORCO:button.addClickListener(clickEvent -> {
                    Notification.show("DESPACHO_REFORCO sera implementado brevemente");
                });
                break;
            case EM_PAGAMENTO:button.addClickListener(clickEvent -> {
                    Notification.show("EM_PAGAMENTO sera implementado brevemente");
                });
                break;
            case PROJETO_ARQUIVADO:button.addClickListener(clickEvent -> {
                    Notification.show("PROJETO_ARQUIVADO sera implementado brevemente");
                });
                break;
            case PROJETO_REJEITADO:button.addClickListener(clickEvent -> {
                    Notification.show("PROJETO_REJEITADO sera implementado brevemente");
                });
                break;
            case PROJETO_SUSPENSO:button.addClickListener(clickEvent -> {
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
    
    
    private Component candidaturaOptions(Projeto projeto) {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.addItem("Abrir projeto",
                event -> Notification.show("Abrir projeto sera implementado brevemente"));

        contextMenu.addItem("Arquivar projeto",
                event -> Notification.show("Arquivar projeto sera implementado brevemente"));

        contextMenu.setVisible(true);

        return contextMenu;
    }
    
    private Component despachoAberturaOptions(Projeto projeto) {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.addItem("Emitir despacho abertura",
                event -> Notification.show(
                        "Emitir despacho abertura, devera abrir o formulario de abertura. "
                +"Formulario DespachoAberturaForm devera contem as opções: "
                +"APROVADO, REJEITADO(NAO TEM esta opção)"));
        contextMenu.setVisible(true);

        return contextMenu;
    }
    
    private Component despachoFinanciamentoOptions(Projeto projeto) {
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.addItem("Emitir despacho financiamento",
                event -> Notification.show(
                        "Devera abrir o formulario do despacho de acordo com tipo projeto: "
                +"DespachoFinIncentivoForm, DespachoFinBonificacaoForm"));

        contextMenu.setVisible(true);

        return contextMenu;
    }
}
