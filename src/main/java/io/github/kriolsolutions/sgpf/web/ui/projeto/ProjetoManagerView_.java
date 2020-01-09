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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.SgpfServiceFacade;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import io.github.kriolsolutions.sgpf.backend.dal.repo.SgpfRepositoryFacade;
import io.github.kriolsolutions.sgpf.web.ui.documentos.CandidaturaForm;
import java.util.List;

/**
 * OLD - TEM UMA MINHOCA DESCONHECIDA
 * @author pauloborges
 */
public class ProjetoManagerView_ extends VerticalLayout {

    private final ProjetoGrid projetoGrid = new ProjetoGrid();;
    private final SgpfRepositoryFacade sgpfRepositoryFacade;
    private final SgpfServiceFacade sgpfServiceFacade;

    public ProjetoManagerView_(SgpfRepositoryFacade sgpfRepositoryFacade, 
            SgpfServiceFacade sgpfServiceFacade) {
        
        //SUPID CHECK 
        if (sgpfRepositoryFacade == null) {
            throw new IllegalArgumentException("Parameter sgpfRepositoryFacade must not be null");
        }
        
        //SUPID CHECK 
        if (sgpfServiceFacade == null) {
            throw new IllegalArgumentException("Parameter sgpfServiceFacade must not be null");
        }
        
        this.sgpfRepositoryFacade = sgpfRepositoryFacade;
        this.sgpfServiceFacade = sgpfServiceFacade;
        
        init();
    }
    
    private void init(){
        
        final Component topLayout = createTopBar();
        
        final VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.add(topLayout);
        barAndGridLayout.add(projetoGrid);
        barAndGridLayout.setFlexGrow(1, projetoGrid);
        barAndGridLayout.setFlexGrow(0, topLayout);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.expand(projetoGrid);
        
        this.add(barAndGridLayout);
        
        setGridDataprovider();
    }

    private Component createTopBar() {
        
        TextField filter = new TextField();
        filter.setPlaceholder("Filtrar por n. projeto");
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);

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

            CandidaturaForm candForm = new CandidaturaForm(sgpfServiceFacade.getAceitacaoCandidaturaAcoes());
            Dialog candDialog = new Dialog(candForm);
            candDialog.open();
        });
        
        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.getStyle().set("marginRight", "10px");
        topLayout.add(filter);
        topLayout.add(refreshButton);
        topLayout.add(novaCandidaturaButton);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);

        return topLayout;
    }
    
    private void loadItems(){
        
        //Projetos projectos = sgpfacade.getProjetos();
        
        projetoGrid.getDataProvider().refreshAll();
        
        //projetoGrid.setItems(projectos.findAll(1, Integer.MAX_VALUE));
    }
    
    public Component createTopBar1() {
        
        final TextField filter = new TextField();
        filter.setPlaceholder("Filtrar por n. projeto");
        //Apply the filter to grid's data provider. TextField value is never null
        //filter.addValueChangeListener(event -> projetoGrid.getDataProvider().setFilter(event.getValue()));
        filter.addFocusShortcut(Key.KEY_F, KeyModifier.CONTROL);

        Button newCand = new Button("Nova Candidatura");
        newCand.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        newCand.setIcon(VaadinIcon.PLUS_CIRCLE.create());
        //newProduct.addClickListener(click -> viewLogic.newProduct());
        // CTRL+N will create a new window which is unavoidable
        newCand.addClickShortcut(Key.KEY_N, KeyModifier.ALT);

        final HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidth("100%");
        topLayout.add(filter);
        topLayout.add(newCand);
        topLayout.setVerticalComponentAlignment(Alignment.START, filter);
        topLayout.expand(filter);
        
        return topLayout;
    }

    private void setGridDataprovider() {

        DataProvider<Projeto, Void> projetoDataProvider
                = DataProvider.fromCallbacks(
                        // First callback fetches items based on a query
                        query -> {
                            // The index of the first item to load
                            int offset = query.getOffset();

                            // The number of items to load
                            int limit = query.getLimit();

                            List<Projeto> projetos = sgpfRepositoryFacade.getProjetoRepository().findAll(offset, limit);
                            
                            System.out.println("Projetos count: "+projetos.size());
                            
                            
                            return projetos.stream();
                        },
                        count -> sgpfRepositoryFacade.getProjetoRepository().count().intValue()
                        
                        
                );
        projetoGrid.setDataProvider(projetoDataProvider);
    }
}
