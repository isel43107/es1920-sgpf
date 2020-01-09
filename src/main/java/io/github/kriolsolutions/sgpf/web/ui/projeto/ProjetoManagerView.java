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
import io.github.kriolsolutions.sgpf.web.ui.documentos.CandidaturaForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.component.crud.CrudGrid;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.SgpfServiceFacade;
import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.web.ui.MainLayout;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.inject.Inject;

/**
 * UI
 *
 * @author pauloborges
 */
@Route(value = "projetos", layout = MainLayout.class)
@PageTitle("Projetos")
public class ProjetoManagerView extends VerticalLayout {

    public final static String VIEW_NAME = "Projetos";

    private final static Logger LOGGER = Logger.getLogger(ProjetoManagerView.class.getName());

    private final ProjetoRepository projetoRepository;

    private final SgpfServiceFacade sgpfacade;

    private final CrudGrid<Projeto> projetoGrid = new CrudGrid<>(Projeto.class, false);

    @Inject
    public ProjetoManagerView(ProjetoRepository projetoRepository, SgpfServiceFacade sgpfacade) {

        this.projetoRepository = projetoRepository;
        this.sgpfacade = sgpfacade;

        init();
    }

    private void init() {

        setupGrid(projetoGrid);

        final Component topLayout = createTopBar();

        final VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(projetoGrid);
        barAndGridLayout.setFlexGrow(1, projetoGrid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(projetoGrid);

        this.add(barAndGridLayout);

        // If the browser window size changes, check if all columns fit on screen
        // (e.g. switching from portrait to landscape mode)
        UI.getCurrent().getPage().addBrowserWindowResizeListener(
                e -> setColumnVisibility(projetoGrid, e.getWidth()));
    }

    private CrudEditor<Projeto> createProjetoEditor() {

        CandidaturaForm candidaturaForm = new CandidaturaForm(sgpfacade.getAceitacaoCandidaturaAcoes());

        return new BinderCrudEditor<>(candidaturaForm.getBinder(), candidaturaForm);
    }

    private Component createTopBar() {

        TextField filterText = new TextField();
        filterText.setPlaceholder("Filtrar por n. projeto");
        filterText.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> listarProjetos(projetoGrid, e.getValue()));

        Button refreshButton = new Button("Refresh");
        //refreshButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        refreshButton.setIcon(VaadinIcon.REFRESH.create());
        // CTRL+R will create a new window which is unavoidable
        refreshButton.addClickShortcut(Key.KEY_R, KeyModifier.ALT);
        refreshButton.addClickListener((t) -> {
            loadItems();
        });

        Button novaCandidaturaButton = new Button("Nova Candidatura");
        novaCandidaturaButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        novaCandidaturaButton.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        // CTRL+N will create a new window which is unavoidable
        novaCandidaturaButton.addClickShortcut(Key.KEY_N, KeyModifier.ALT);
        novaCandidaturaButton.addClickListener((t) -> {

            CandidaturaForm candForm = new CandidaturaForm(sgpfacade.getAceitacaoCandidaturaAcoes());
            Dialog candDialog = new Dialog(candForm);
            candDialog.open();

            candForm.getArquivarButton().addClickListener((e) -> {
                candDialog.close();
            });
        });

        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.getStyle().set("marginRight", "10px");
        topLayout.add(filterText);
        topLayout.add(refreshButton);
        topLayout.add(novaCandidaturaButton);
        topLayout.setVerticalComponentAlignment(Alignment.START, filterText);
        topLayout.expand(filterText);

        return topLayout;
    }

    private void loadItems() {
        projetoGrid.getDataProvider().refreshAll();
    }

    private void listarProjetos(Grid<Projeto> grid, String filterText) {

        if (filterText != null && !filterText.isEmpty()) {
            //SingularAttribute<Projeto, ?>[] attributes = new SingularAttribute[] {};
            List<Projeto> projetos = this.projetoRepository.findByProjNumero(filterText);
            if (projetos != null) {

                AbstractBackEndDataProvider<Projeto, CrudFilter> dp = new AbstractBackEndDataProvider<Projeto, CrudFilter>() {
                    @Override
                    protected Stream<Projeto> fetchFromBackEnd(
                            Query<Projeto, CrudFilter> query) {
                        return projetos.stream();
                    }

                    @Override
                    protected int sizeInBackEnd(Query<Projeto, CrudFilter> query) {
                        return projetos.size();
                    }
                };

                grid.setDataProvider(dp);
            }
        } else {
            // List<Projeto> projetos = this.projetoRepository.findAll();
            //grid.setItems(projetos);
        }

    }

    private void setupGrid(Grid<Projeto> grid) {

        final ProjectoOptions projectoOptions = new ProjectoOptions();

        grid.removeAllColumns();

        grid.addColumn(Projeto::getProjNumero).setHeader("Numero projeto")
                .setFlexGrow(10).setSortable(false).setKey("projNumero");

        grid.addColumn(Projeto::getProjDesignacao).setHeader("Designação projeto")
                .setFlexGrow(20).setSortable(false).setKey("projDesignacao");

        grid.addColumn(Projeto::getProjEstado).setHeader("Estado projeto")
                .setFlexGrow(20).setSortable(false).setKey("projEstado");

        grid.addColumn(Projeto::getProjTipo).setHeader("Tipo projeto")
                .setFlexGrow(20).setSortable(false).setKey("projTipo");

        final DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);

        grid.addColumn(product -> decimalFormat.format(product.getProjMontanteSolicitado()))
                .setHeader("Mnt. Solicitado").setTextAlign(ColumnTextAlign.END)
                .setComparator(Comparator.comparing(Projeto::getProjMontanteSolicitado))
                .setFlexGrow(3).setSortable(false).setKey("projMontanteSolicitado");

        grid.addColumn(Projeto::getPromotorDesignacao).setHeader("Promotor projeto")
                .setFlexGrow(20).setSortable(false).setKey("promotorDesignacao");

        grid.addColumn(Projeto::getContatoNome).setHeader("Contato projeto")
                .setFlexGrow(20).setSortable(false).setKey("contatoNome");

        grid.addComponentColumn(item -> projectoOptions.createOptionsButton(item))
                .setHeader("Ações").setKey("acoesDisponiveis");

        //Dataprovider
        DataProvider<Projeto, Void> dataProvider
                = DataProvider.fromCallbacks(
                        // First callback fetches items based on a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            List<Projeto> projetos = this.projetoRepository.findAll(offset, limit);
                            return projetos.stream();
                        },
                        count -> this.projetoRepository.count().intValue()
                );

        projetoGrid.setDataProvider(dataProvider);
    }

    private void setColumnVisibility(Grid<Projeto> grid, int width) {
        if (width > 800) {
            grid.getColumnByKey("projNumero").setVisible(true);
            grid.getColumnByKey("projDesignacao").setVisible(true);
            grid.getColumnByKey("projEstado").setVisible(true);
            grid.getColumnByKey("projTipo").setVisible(true);
            grid.getColumnByKey("projMontanteSolicitado").setVisible(false);
            grid.getColumnByKey("promotorDesignacao").setVisible(false);
            grid.getColumnByKey("contatoNome").setVisible(false);
            grid.getColumnByKey("acoesDisponiveis").setVisible(true);
        } else if (width > 550) {
            grid.getColumnByKey("projNumero").setVisible(true);
            grid.getColumnByKey("projDesignacao").setVisible(true);
            grid.getColumnByKey("projEstado").setVisible(true);
            grid.getColumnByKey("projTipo").setVisible(false);
            grid.getColumnByKey("projMontanteSolicitado").setVisible(false);
            grid.getColumnByKey("promotorDesignacao").setVisible(false);
            grid.getColumnByKey("contatoNome").setVisible(false);
            grid.getColumnByKey("acoesDisponiveis").setVisible(true);
        } else {
            grid.getColumnByKey("projNumero").setVisible(true);
            grid.getColumnByKey("projDesignacao").setVisible(true);
            grid.getColumnByKey("projEstado").setVisible(true);
            grid.getColumnByKey("projTipo").setVisible(false);
            grid.getColumnByKey("projMontanteSolicitado").setVisible(false);
            grid.getColumnByKey("promotorDesignacao").setVisible(false);
            grid.getColumnByKey("contatoNome").setVisible(false);
            grid.getColumnByKey("acoesDisponiveis").setVisible(true);
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // fetch browser width
        UI.getCurrent().getInternals().setExtendedClientDetails(null);
        UI.getCurrent().getPage().retrieveExtendedClientDetails(e -> {
            setColumnVisibility(projetoGrid, e.getBodyClientWidth());
        });
    }
}
