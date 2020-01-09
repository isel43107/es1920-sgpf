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
package io.github.kriolsolutions.sgpf.web.ui.documentos;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;

/**
 *
 * @author pauloborges
 * Montante máximo de bonificação
 * Taxa de bonificação
 * Período
 */
public class DespachoFinBonificacaoForm extends FormLayout {
    
    private final BeanValidationBinder<Projeto> binder = new BeanValidationBinder<>(Projeto.class);

    private NumberField utilizadorGestorFin = new NumberField();
    
    private Button aceitarButton = new Button("Aprovar");
    private Button arquivarButton = new Button("Arquivar");
    private final Projeto projeto;
    
    public DespachoFinBonificacaoForm( Projeto projeto ){
        this.projeto = projeto;
        init();
    }

    private void init() {
        
        this.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        
        utilizadorGestorFin.setLabel("Gestor de financiamento");
        binder.bindInstanceFields(this);
        binder.readBean(this.projeto);
    }
    
    public Binder<Projeto> getBinder() {
        return binder;
    }
    
    private void buildActionsButtons(){
        /* */
        // Button bar
        aceitarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aceitarButton.addClickListener((event) -> {
            Projeto proj = getBinder().getBean();
            //this.aberturaAccoes.aprovar(proj);
        });
        arquivarButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(aceitarButton, arquivarButton);
        actions.getStyle().set("marginRight", "10px");
        //this.add(actions);
    }
    
}
