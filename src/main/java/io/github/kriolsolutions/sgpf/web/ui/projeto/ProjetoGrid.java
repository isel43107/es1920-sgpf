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
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.crud.CrudGrid;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import java.text.DecimalFormat;
import java.util.Comparator;

/**
 *
 * @author pauloborges
 */
public class ProjetoGrid extends CrudGrid<Projeto> {

    private final ProjectoOptions projectoOptions = new ProjectoOptions();

    public ProjetoGrid() {
        super(Projeto.class, false);
        setSizeFull();
        init();
        //setupEditor();
    }

    private void init() {
        this.removeAllColumns();
        addColumn(Projeto::getProjNumero).setHeader("Numero projeto")
                .setFlexGrow(10).setSortable(false).setKey("projNumero");

        addColumn(Projeto::getProjDesignacao).setHeader("Designação projeto")
                .setFlexGrow(20).setSortable(false).setKey("projDesignacao");

        addColumn(Projeto::getProjDesignacao).setHeader("Estado projeto")
                .setFlexGrow(20).setSortable(false).setKey("projEstado");

        addColumn(Projeto::getProjDesignacao).setHeader("Tipo projeto")
                .setFlexGrow(20).setSortable(false).setKey("projTipo");

        final DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);

        addColumn(product -> decimalFormat.format(product.getProjMontanteSolicitado()))
                .setHeader("Mnt. Solicitado").setTextAlign(ColumnTextAlign.END)
                .setComparator(Comparator.comparing(Projeto::getProjMontanteSolicitado))
                .setFlexGrow(3).setSortable(false).setKey("projMontanteSolicitado");

        addColumn(Projeto::getPromotorDesignacao).setHeader("Promotor projeto")
                .setFlexGrow(20).setSortable(false).setKey("promotorDesignacao");

        addColumn(Projeto::getContatoNome).setHeader("Contato projeto")
                .setFlexGrow(20).setSortable(false).setKey("contatoNome");

        addComponentColumn(item -> this.projectoOptions.createOptionsButton(item))
                .setHeader("Ações").setKey("acoesDisponiveis");

        // If the browser window size changes, check if all columns fit on screen
        // (e.g. switching from portrait to landscape mode)
        UI.getCurrent().getPage().addBrowserWindowResizeListener(
                e -> setColumnVisibility(e.getWidth()));
    }

    /*
    private void setupEditor() {

        ProjetoEditorForm projetoEditor = new ProjetoEditorForm();
        Dialog projetoDialog = new Dialog(projetoEditor);

        this.getEditor().addOpenListener((editoOpenEvent) -> {
            Projeto projeto = editoOpenEvent.getItem();
            projetoEditor.getBinder().readBean(projeto);
            projetoDialog.open();
        });
        this.getEditor().setBinder(projetoEditor.getBinder());
    }
    */

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
}
