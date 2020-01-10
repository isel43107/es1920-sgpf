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
import com.vaadin.flow.data.binder.ValidationException;
import io.github.kriolsolutions.sgpf.backend.bal.dto.DespachoFinBonificacaoDto;
import io.github.kriolsolutions.sgpf.backend.bal.dto.DespachoFinIncentivoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoAberturaAcoes;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoFinanciamentoIncentivoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pauloborges
 */
public class DespachoFinIncentivoForm extends FormLayout {

    private final BeanValidationBinder<DespachoFinIncentivoDto> binder =
            new BeanValidationBinder<>(DespachoFinIncentivoDto.class);

    
    private final DespachoFinIncentivoDto despacho = new DespachoFinIncentivoDto();
    
    private NumberField montanteFinanciado = new NumberField();
    private DatePicker  prazoExecucao =  new DatePicker();
    private NumberField custoElegivel = new NumberField();

    private final Projeto projeto;
    private final DespachoFinanciamentoIncentivoAcoes despachoAcoes;
    
    Button aprovarButton = new Button("Aprovar");
    Button rejeitarButton = new Button("Rejeitar");
    Button transformarButton = new Button("Transformar");

    public DespachoFinIncentivoForm(DespachoFinanciamentoIncentivoAcoes despachoAcoes, Projeto projeto) {

        this.projeto = projeto;
        this.despachoAcoes = despachoAcoes;
        this.despacho.setProjetoId(projeto.getId());
        init();
    }

    private void init() {

        this.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));

        montanteFinanciado.setLabel("Montante de Financiamento");
        prazoExecucao.setLabel("Prazo de execução");
        custoElegivel.setLabel("Custo Elegivel");
        
        binder.forMemberField(montanteFinanciado);        
        binder.forMemberField(custoElegivel);
        binder.forMemberField(prazoExecucao);

        
        this.add(montanteFinanciado,custoElegivel, prazoExecucao);

        binder.bindInstanceFields(this);
        buildActionsButtons();
    }
    
    private void buildActionsButtons(){
        /* */
        // Button bar
        aprovarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aprovarButton.addClickListener((event) -> {
            try {
                getBinder().writeBean(despacho);
                this.despachoAcoes.aprovar(despacho);
                
            } catch (ValidationException ex) {
                Logger.getLogger(DespachoFinIncentivoForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        rejeitarButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        rejeitarButton.addClickListener((event) -> {
            
            try {
                getBinder().writeBean(despacho);
                this.despachoAcoes.rejeitar(despacho);
                
            } catch (ValidationException ex) {
                Logger.getLogger(DespachoFinIncentivoForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        transformarButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        transformarButton.addClickListener((event) -> {
            try {
                getBinder().writeBean(despacho);
                this.despachoAcoes.transformarBonificacao(despacho);
                
            } catch (ValidationException ex) {
                Logger.getLogger(DespachoFinIncentivoForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(aprovarButton, rejeitarButton,transformarButton );
        
        actions.getStyle().set("marginRight", "10px");
        this.add(actions);
    }

    public Binder<DespachoFinIncentivoDto> getBinder() {
        return binder;
    }
}
