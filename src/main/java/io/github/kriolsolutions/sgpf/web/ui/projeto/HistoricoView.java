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
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.component.crud.CrudGrid;
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
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Historico;
import io.github.kriolsolutions.sgpf.backend.dal.repo.HistoricoRepository;
import io.github.kriolsolutions.sgpf.web.ui.MainLayout;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.inject.Inject;

/**
 * UI
 *
 * @author pauloborges
 */
@Route(value = "historicos", layout = MainLayout.class)
@PageTitle("Historicos")
public class HistoricoView extends VerticalLayout {

    public final static String VIEW_NAME = "Historicos";

    private final static Logger LOGGER = Logger.getLogger(HistoricoView.class.getName());

    private final HistoricoRepository hisRepository;

    private final CrudGrid<Historico> grid = new CrudGrid<>(Historico.class, false);

    @Inject
    public HistoricoView(HistoricoRepository historicoRepository) {

        this.hisRepository = historicoRepository;


        init();
    }

    private void init() {

        setupGrid(grid);

        final Component topLayout = createTopBar();

        final VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(grid);
        barAndGridLayout.setFlexGrow(1, grid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(grid);

        this.add(barAndGridLayout);
    }


    private Component createTopBar() {

        TextField filterText = new TextField();
        filterText.setPlaceholder("Filtrar por n. projeto");
        filterText.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        //filterText.addValueChangeListener(e -> listarHistoricos(historicoGrid, e.getValue()));

        Button refreshButton = new Button("Refresh");
        //refreshButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        refreshButton.setIcon(VaadinIcon.REFRESH.create());
        // CTRL+R will create a new window which is unavoidable
        refreshButton.addClickShortcut(Key.KEY_R, KeyModifier.ALT);
        refreshButton.addClickListener((t) -> {
            loadItems();
        });



        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.getStyle().set("marginRight", "10px");
        topLayout.add(filterText);
        topLayout.add(refreshButton);
        topLayout.setVerticalComponentAlignment(Alignment.START, filterText);
        topLayout.expand(filterText);

        return topLayout;
    }

    private void loadItems() {
        grid.getDataProvider().refreshAll();
    }

    private void listarHistoricos(Grid<Historico> grid, String filterText) {

        List<Historico> historicos = null;
        if (filterText != null && !filterText.isEmpty()) {
            //SingularAttribute<Historico, ?>[] attributes = new SingularAttribute[] {};
            //historicos = this.historicoRepository.findByProjNumero(filterText);
            if (historicos != null) {

                AbstractBackEndDataProvider<Historico, CrudFilter> dp = new AbstractBackEndDataProvider<Historico, CrudFilter>() {
                    @Override
                    protected Stream<Historico> fetchFromBackEnd(
                            Query<Historico, CrudFilter> query) {
                        return historicos.stream();
                    }

                    @Override
                    protected int sizeInBackEnd(Query<Historico, CrudFilter> query) {
                        return historicos.size();
                    }
                };

                grid.setDataProvider(dp);
            }
        } else {
            // List<Historico> historicos = this.historicoRepository.findAll();
            //grid.setItems(historicos);
        }

    }

    private void setupGrid(Grid<Historico> grid) {
        //final ProjectoOptions projectoOptions = new ProjectoOptions();

        grid.removeAllColumns();

        grid.addColumn(Historico::getProjNumero).setHeader("Numero projeto")
                .setFlexGrow(10).setSortable(false).setKey("projNumero");

        grid.addColumn(Historico::getEstadoAnterior).setHeader("Estado anterior")
                .setFlexGrow(20).setSortable(false).setKey("EstadoAnterior");

        grid.addColumn(Historico::getEstadoAtual).setHeader("Estado atual")
                .setFlexGrow(20).setSortable(false).setKey("EstadoAtual");

        grid.addColumn(Historico::getEvento).setHeader("Evento")
                .setFlexGrow(20).setSortable(false).setKey("evento");

        /*
        // O documento podera ser null importante verificar antes de aceder
        grid.addColumn(historico -> historico.getDocumento().getId()).setHeader("Documento")
                .setFlexGrow(20).setSortable(false).setKey("promotorDesignacao");

        
        grid.addComponentColumn(item -> projectoOptions.createOptionsButton(item))
                .setHeader("Ações").setKey("acoesDisponiveis");
        */

        //Dataprovider
        DataProvider<Historico, Void> dataProvider
                = DataProvider.fromCallbacks(// First callback fetches items based on a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            List<Historico> historicos = this.hisRepository.findAll(offset, limit);
                            return historicos.stream();
                        },
                        count -> this.hisRepository.count().intValue()
                );

        this.grid.setDataProvider(dataProvider);
    }

    private void setColumnVisibility(Grid<Historico> grid, int width) {
        /*
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
        */
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        // fetch browser width
        UI.getCurrent().getInternals().setExtendedClientDetails(null);
        UI.getCurrent().getPage().retrieveExtendedClientDetails(e -> {
//            setColumnVisibility(grid, e.getBodyClientWidth());
        });
    }
}