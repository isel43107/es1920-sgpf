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
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import io.github.kriolsolutions.sgpf.backend.bal.dto.DespachoFinBonificacaoDto;
import io.github.kriolsolutions.sgpf.backend.bal.dto.DespachoFinIncentivoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoFinanciamentoBonificacaoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;

/**
 *
 * @author pauloborges
 * Montante máximo de bonificação
 * Taxa de bonificação
 * Período
 */
public class DespachoFinBonificacaoForm extends FormLayout {
    
    private final BeanValidationBinder<DespachoFinBonificacaoDto> binder = new BeanValidationBinder<>(DespachoFinBonificacaoDto.class);

    private NumberField montanteFinanciado = new NumberField();
    private NumberField custoElegivel = new NumberField();
    
    private Button aceitarButton = new Button("Aprovar");
    private Button arquivarButton = new Button("Arquivar");
    private final Projeto projeto;
    private final DespachoFinanciamentoBonificacaoAcoes bonificacaoAccoes;
    
    public DespachoFinBonificacaoForm(DespachoFinanciamentoBonificacaoAcoes accoes, Projeto projeto ){
        this.projeto = projeto;
        this.bonificacaoAccoes = accoes;
        init();
    }

    private void init() {
        
        this.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        
        montanteFinanciado.setLabel("Montante de Financiamento");
        custoElegivel.setLabel("Custo Elegivel");
        
        this.add(montanteFinanciado,custoElegivel);
        binder.bindInstanceFields(this);
        buildActionsButtons();
    }
    
    public Binder<DespachoFinBonificacaoDto> getBinder() {
        return binder;
    }
    
    private void buildActionsButtons(){
        /* */
        // Button bar
        aceitarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aceitarButton.addClickListener((event) -> {
            DespachoFinBonificacaoDto despacho = getBinder().getBean();
            this.bonificacaoAccoes.aprovar(despacho);
        });
        
        arquivarButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        arquivarButton.addClickListener((event) -> {
            DespachoFinBonificacaoDto despacho = getBinder().getBean();
            despacho.setProjetoId(this.projeto.getId());
            this.bonificacaoAccoes.rejeitar(despacho);
        });
        
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(aceitarButton, arquivarButton);
        actions.getStyle().set("marginRight", "10px");
        //this.add(actions);
    }
    
}
