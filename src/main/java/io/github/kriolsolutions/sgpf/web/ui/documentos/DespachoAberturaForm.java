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
import com.vaadin.flow.data.binder.ValidationException;
import io.github.kriolsolutions.sgpf.backend.bal.dto.DespachoAberturaDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoAberturaAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pauloborges
 */
public class DespachoAberturaForm extends AbstractDespachoForm {
    
    private final BeanValidationBinder<DespachoAberturaDto> binder = new BeanValidationBinder<>(DespachoAberturaDto.class);

    //Campos - PROMOTOR
    private IntegerField gestorFinanciamentoId = new IntegerField();
    private final DespachoAberturaDto despacho = new DespachoAberturaDto();
    
    
    Button aceitarButton = new Button("Aprovar");
    Button arquivarButton = new Button("Arquivar");
    private final Projeto projeto;

    private final DespachoAberturaAcoes aberturaAccoes;
    
    public DespachoAberturaForm( DespachoAberturaAcoes aberturaAccoes , Projeto projeto){

        this.aberturaAccoes = aberturaAccoes;
        this.projeto = projeto;
        despacho.setProjetoId(projeto.getId());
        setupFields();
        
    }

    @Override
    protected void setupFields() {
        gestorFinanciamentoId.setLabel("Gestor de financiamento");
        
        binder.forMemberField(gestorFinanciamentoId);
        
        this.add(gestorFinanciamentoId);
        binder.bindInstanceFields(this);
        aceitarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aceitarButton.addClickListener((event) -> {
            try {
                binder.writeBean(this.despacho);
                despacho.setProjetoId(this.projeto.getId());
                this.aberturaAccoes.aprovar(despacho);
            } catch (ValidationException ex) {
                Logger.getLogger(DespachoAberturaForm.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        arquivarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        arquivarButton.addClickListener((event) -> {
            try {
                binder.writeBean(this.despacho);
            } catch (ValidationException ex) {
                Logger.getLogger(DespachoAberturaForm.class.getName()).log(Level.SEVERE, null, ex);
                handleException(ex);
            }
            despacho.setProjetoId(this.projeto.getId());
            this.aberturaAccoes.aprovar(despacho);
        });
        
        getActions().add(aceitarButton, arquivarButton);
    }
}
