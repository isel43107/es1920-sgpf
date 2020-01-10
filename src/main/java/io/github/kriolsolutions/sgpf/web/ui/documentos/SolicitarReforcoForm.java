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
import io.github.kriolsolutions.sgpf.backend.bal.dto.PedidoReforcoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoFinanciamentoReforcoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;
import java.util.Calendar;

/**
 *
 * @author pauloborges
 */
public class SolicitarReforcoForm extends FormLayout {
    private final BeanValidationBinder<PedidoReforcoDto> binder = new BeanValidationBinder<>(PedidoReforcoDto.class);

    private NumberField montanteReforco = new NumberField();
    
    Button solicitarButton = new Button("Solicitar");
    Button cancelarButton = new Button("Cancelar");
    
    private Projeto projeto ; 

    public Button getCancelarButton() {
        return cancelarButton;
    }
    private final DespachoFinanciamentoReforcoAcoes despachoAccoes;
    
    public SolicitarReforcoForm(DespachoFinanciamentoReforcoAcoes despachoAccoes, Projeto projeto){
    
        this.despachoAccoes = despachoAccoes;
        this.projeto = projeto;
        
        init();
    }

    private void init() {
        
        this.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        
        montanteReforco.setLabel("Montante Requerido");
        this.add(montanteReforco);
        buildActionsButtons();
    }
    
    private void buildActionsButtons(){
        /* */
        // Button bar
        solicitarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        solicitarButton.addClickListener((event) -> {
            PedidoReforcoDto pedido = getBinder().getBean();
            pedido.setProjetoId(this.projeto.getId());
            pedido.setDataPedido(Calendar.getInstance().getTime());
            despachoAccoes.solicitar(pedido);
        });
        cancelarButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        
        cancelarButton.addClickListener((event) -> {
            
        });
        
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(solicitarButton, cancelarButton);
        actions.getStyle().set("marginRight", "10px");
    }
    
    
    public Binder<PedidoReforcoDto> getBinder() {
        return binder;
    }
}
