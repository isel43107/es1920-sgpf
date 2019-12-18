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
import io.github.kriolsolutions.sgpf.web.ui.projeto.ProjetoGrid;
import com.vaadin.cdi.annotation.VaadinSessionScoped;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudGrid;
import com.vaadin.flow.component.crud.CrudI18n;
import com.vaadin.flow.component.crud.CrudVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import io.github.kriolsolutions.sgpf.backend.dal.ProjetoRepository;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.Projeto;
import java.util.List;
import javax.inject.Inject;

/**
 * UI
 *
 * @author pauloborges
 */
//@VaadinSessionScoped
public class ExampleUI extends VerticalLayout {

    
    private static final String DISCARD_MESSAGE = "There are unsaved modifications to the %s. Discard changes?";
    private static final String DELETE_MESSAGE = "Are you sure you want to delete the selected %s? This action cannot be undone.";

    //@Inject
    private final ProjetoRepository projetoRepository;
    
    public ExampleUI(ProjetoRepository projetoRepository) {

        this.projetoRepository = projetoRepository;
        
        intComponents();
    }
    
    private void intComponents(){
        //add(new CandidaturaForm());
        //add(createForm());
        this.add(createProjetoCrudGrid());
        // add(crud());
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
                        query -> this.projetoRepository.count().intValue()
                );

        CrudGrid<Projeto> crudGrid = new CrudGrid<>(Projeto.class, false);
        crudGrid.setDataProvider(dataProvider);
        return crudGrid;
    }

    private Component crud() {

        DataProvider<Projeto, Void> dataProvider
                = DataProvider.fromCallbacks(
                        // First callback fetches items based on a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            List<Projeto> projetos = projetoRepository.findAll(offset, limit);
                            return projetos.stream();
                        },
                        query -> projetoRepository.count().intValue()
                );

        Crud<Projeto> crud = new Crud<>(Projeto.class, createProjetoEditor());

        CrudI18n crudI18n = CrudI18n.createDefault();
        String entityName = "Projeto";
        crudI18n.setNewItem("New " + entityName);
        crudI18n.setEditItem("Edit " + entityName);
        crudI18n.setEditLabel("Edit " + entityName);
        crudI18n.getConfirm().getCancel().setContent(String.format(DISCARD_MESSAGE, entityName));
        crudI18n.getConfirm().getDelete().setContent(String.format(DELETE_MESSAGE, entityName));
        crudI18n.setDeleteItem("Delete");

        crud.setI18n(crudI18n);

        crud.setDataProvider(dataProvider);
        crud.addSaveListener(e -> projetoRepository.save(e.getItem()));
        crud.addDeleteListener(e -> projetoRepository.remove(e.getItem()));

        crud.getGrid().removeColumnByKey("id");
        crud.addThemeVariants(CrudVariant.NO_BORDER);

        return crud;
    }

    private CrudEditor<Projeto> createProjetoEditor() {

        CandidaturaForm candidaturaForm = new CandidaturaForm();

        return new BinderCrudEditor<>(candidaturaForm.getBinder(), candidaturaForm);
    }

    private Component createProjetoGrid() {
        ProjetoGrid grid = new ProjetoGrid(this.projetoRepository);

        DataProvider<Projeto, Void> dataProvider
                = DataProvider.fromCallbacks(
                        // First callback fetches items based on a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            List<Projeto> projetos = projetoRepository.findAll(offset, limit);
                            return projetos.stream();
                        },
                        query -> projetoRepository.count().intValue()
                );

        grid.setDataProvider(dataProvider);

        return grid;
    }

     /*
    private Component createForm1() {
       
        
    @Inject
    private MessageBean messageBean;
        Button button = new Button("Click me", event -> Notification.show(messageBean.getMessage()));
        Text text = new Text("Ola");

        //add(text);
        //add(button);
        FormLayout form = new FormLayout();
        form.add(text, button);

        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));

        return form;
        
        
    }
    */

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
