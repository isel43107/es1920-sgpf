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

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import io.github.kriolsolutions.sgpf.backend.dal.ProjetoRepository;
import io.github.kriolsolutions.sgpf.web.ui.documentos.CandidaturaForm;

/**
 *
 * @author pauloborges
 */
public class ProjetoManager  extends VerticalLayout {

    private final ProjetoGrid projetoGrid;
    
    public ProjetoManager(ProjetoRepository projetoRepository){
    
        this.projetoGrid = new ProjetoGrid(projetoRepository);
        
        addActionsButtons();
        addGrid();
    }
    
    private void addActionsButtons(){
        
        HorizontalLayout actions = new HorizontalLayout();

        TextField pesquisatexto = new TextField();
        
        Button pesquisarButton = new Button("Pesquisar");
        
        Button novaCandidaturaButton = new Button("Nova Candidatura");
        novaCandidaturaButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        novaCandidaturaButton.addClickListener((t) -> {
            new Dialog(new CandidaturaForm()).open();
        });
        
        actions.add(pesquisatexto, pesquisarButton, novaCandidaturaButton);
        
        actions.getStyle().set("marginRight", "10px");
        
        this.add(actions);
    }
    
    private void addGrid(){
        this.add(this.projetoGrid);
    }
}
