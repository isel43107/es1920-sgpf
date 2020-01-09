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
package io.github.kriolsolutions.sgpf.web.ui;

import io.github.kriolsolutions.sgpf.web.ui.documentos.CandidaturaForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudGrid;
import com.vaadin.flow.component.crud.CrudI18n;
import com.vaadin.flow.component.crud.CrudVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.SgpfServiceFacade;
import io.github.kriolsolutions.sgpf.backend.dal.repo.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.web.ui.projeto.ProjectoOptions;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 * UI
 *
 * @author pauloborges
 */
@Route(value = "examples", layout = MainLayout.class)
@PageTitle("Examples")
public class ExampleView extends VerticalLayout {

    public static final String VIEW_NAME = "Examples";
    
    private static final String DISCARD_MESSAGE = "There are unsaved modifications to the %s. Discard changes?";
    private static final String DELETE_MESSAGE = "Are you sure you want to delete the selected %s? This action cannot be undone.";

    private static Logger LOGGER = Logger.getLogger(ExampleView.class.getName());

    private final ProjetoRepository projetoRepository;

    private final SgpfServiceFacade sgpfacade;

    @Inject
    public ExampleView(ProjetoRepository projetoRepository, SgpfServiceFacade sgpfacade) {

        this.projetoRepository = projetoRepository;
        this.sgpfacade = sgpfacade;

        intComponents();
    }

    private void intComponents() {
        //add(new CandidaturaForm());
        add(createForm());
        add(createProjetoCrudGrid());
        //this.add(new Div());
        //this.add(createProjetoGrid());
    }

    private Component createProjetoCrudGrid() {

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

        CrudGrid<Projeto> crudGrid = new CrudGrid<>(Projeto.class, false);
        settupColumn(crudGrid);
        crudGrid.setDataProvider(dataProvider);
        return crudGrid;
    }

    private Component createProjetoCrudGrid1() {

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

        Crud<Projeto> crud = new Crud<>(Projeto.class, createProjetoEditor());

        CrudI18n crudI18n = CrudI18n.createDefault();
        String entityName = "Projeto";
        crudI18n.setNewItem("Novo " + entityName);
        crudI18n.setEditItem("Editar " + entityName);
        crudI18n.setEditLabel("Editar " + entityName);
        crudI18n.getConfirm().getCancel().setContent(String.format(DISCARD_MESSAGE, entityName));
        crudI18n.getConfirm().getDelete().setContent(String.format(DELETE_MESSAGE, entityName));
        crudI18n.setDeleteItem("Remover");

        crud.setI18n(crudI18n);

        crud.setDataProvider(dataProvider);
        //crud.addSaveListener(e -> sgpfacade.getProjetos().save(e.getItem()));
        //crud.addDeleteListener(e -> sgpfacade.getProjetos().remove(e.getItem()));

        crud.getGrid().removeColumnByKey("id");
        crud.addThemeVariants(CrudVariant.NO_BORDER);

        return crud;
    }

    private CrudEditor<Projeto> createProjetoEditor() {

        CandidaturaForm candidaturaForm = new CandidaturaForm(sgpfacade.getAceitacaoCandidaturaAcoes());

        return new BinderCrudEditor<>(candidaturaForm.getBinder(), candidaturaForm);
    }

    private Component createProjetoGrid() {
        
        //ProjetoGrid ProjetoGrid = new ProjetoGrid(this.sgpfacade);
        //ProjetoGrid.setVisible(true);
        

        Grid<Projeto> grid = new Grid<>();
        grid.setSizeFull();

        grid.addSelectionListener(x -> {
            if (x.getAllSelectedItems().size() > 0) {
                LOGGER.info("Selection changed to: " + x.getFirstSelectedItem().get().getProjDesignacao());
            } else {
                LOGGER.info("No element selected");
            }
        });

        DataProvider<Projeto, Void> dataProvider
                = DataProvider.fromCallbacks(
                        // First callback fetches items based on a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            List<Projeto> projetos = this.projetoRepository.findAll(offset, limit);

                            System.out.println("Projetos count: " + projetos.size());

                            return projetos.stream();
                        },
                        query -> projetoRepository.count().intValue()
                );

        grid.setDataProvider(dataProvider);

        VerticalLayout layout = new VerticalLayout();
        
        TextField filterText = new TextField();
        filterText.setPlaceholder("Filtrar por n. projeto");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> listarProjetos(grid, e.getValue()));

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setSizeFull();
        toolbar.add(filterText);

        HorizontalLayout mainContent = new HorizontalLayout(grid);
        mainContent.setSizeFull();
        mainContent.add(grid);

        layout.add(toolbar);
        layout.add(mainContent);
        
        listarProjetos(grid, null);

        return layout;
    }

    void listarProjetos(Grid<Projeto> grid, String filterText) {

        if (filterText != null && !filterText.isEmpty()) {
            //SingularAttribute<Projeto, ?>[] attributes = new SingularAttribute[] {};
            List<Projeto> projetos = this.projetoRepository.findByProjNumero(filterText);
            grid.setItems(projetos);
        } else {
            
            List<Projeto> projetos = this.projetoRepository.findAll();
            grid.setItems(projetos);
        }
    }

    
    private void settupColumn(Grid<Projeto> grid) {
        
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

        // If the browser window size changes, check if all columns fit on screen
        // (e.g. switching from portrait to landscape mode)
        //UI.getCurrent().getPage().addBrowserWindowResizeListener(e -> setColumnVisibility(e.getWidth()));
    }
    
    private Component createForm() {
        FormLayout nameLayout = new FormLayout();

        TextField titleField = new TextField();
        titleField.setLabel("Title");
        titleField.setPlaceholder("Sir");
        TextField firstNameField = new TextField();
        firstNameField.setLabel("First name");
        firstNameField.setPlaceholder("John");
        TextField lastNameField = new TextField();
        lastNameField.setLabel("Last name");
        lastNameField.setPlaceholder("Doe");

        nameLayout.add(titleField, firstNameField, lastNameField);

        nameLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));

        return nameLayout;

    }
    /**/
}
