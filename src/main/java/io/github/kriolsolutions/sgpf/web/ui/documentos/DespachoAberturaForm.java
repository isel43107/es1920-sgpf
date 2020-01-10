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
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import io.github.kriolsolutions.sgpf.backend.bal.dto.DespachoAberturaDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoAberturaAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;

/**
 *
 * @author pauloborges
 */
public class DespachoAberturaForm extends FormLayout {
    
    private final BeanValidationBinder<DespachoAberturaDto> binder = new BeanValidationBinder<>(DespachoAberturaDto.class);

    //Campos - PROMOTOR
    private IntegerField gestorFinanciamentoId = new IntegerField();
    
    
    Button aceitarButton = new Button("Aprovar");
    Button arquivarButton = new Button("Arquivar");
    private final Projeto projeto;

    public Button getAceitarButton() {
        return aceitarButton;
    }

    public Button getArquivarButton() {
        return arquivarButton;
    }
    private final DespachoAberturaAcoes aberturaAccoes;
    
    public DespachoAberturaForm( DespachoAberturaAcoes aberturaAccoes , Projeto projeto){
        this.aberturaAccoes = aberturaAccoes;
        this.projeto = projeto;
        init();
        buildActionsButtons();
    }

    private void init() {
        
        this.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        
        gestorFinanciamentoId.setLabel("Gestor de financiamento");
        
        binder.forMemberField(gestorFinanciamentoId);
        
        this.add(gestorFinanciamentoId);
        binder.bindInstanceFields(this);
    }
    
    public Binder<DespachoAberturaDto> getBinder() {
        return binder;
    }
    
    private void buildActionsButtons(){
        /* */
        // Button bar
        aceitarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aceitarButton.addClickListener((event) -> {
            DespachoAberturaDto despacho = getBinder().getBean();
            despacho.setProjetoId(this.projeto.getId());
            this.aberturaAccoes.aprovar(despacho);
        });
        arquivarButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(aceitarButton, arquivarButton);
        actions.getStyle().set("marginRight", "10px");
        //this.add(actions);
    }
}
