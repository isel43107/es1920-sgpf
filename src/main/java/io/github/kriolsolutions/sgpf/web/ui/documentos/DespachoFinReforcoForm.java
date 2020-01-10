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
import io.github.kriolsolutions.sgpf.backend.bal.dto.DespachoFinIncentivoDto;
import io.github.kriolsolutions.sgpf.backend.bal.dto.PedidoReforcoDto;
import io.github.kriolsolutions.sgpf.backend.bal.services.api.DespachoFinanciamentoReforcoAcoes;
import io.github.kriolsolutions.sgpf.backend.dal.entidades.projeto.Projeto;

/**
 *
 * @author pauloborges
 */
public class DespachoFinReforcoForm extends FormLayout{
    private final BeanValidationBinder<PedidoReforcoDto> binder = new BeanValidationBinder<>(PedidoReforcoDto.class);

    private NumberField montanteReforco = new NumberField();
    private DatePicker dataPedido = new DatePicker();
    
    Button aprovarButton = new Button("Aprovar");
    Button rejeitarButton = new Button("Rejeitar");
    private final DespachoFinanciamentoReforcoAcoes despachoAccoes;
    
    public DespachoFinReforcoForm(DespachoFinanciamentoReforcoAcoes despachoAccoes , PedidoReforcoDto pedido ){
    
        this.despachoAccoes = despachoAccoes;
        binder.readBean(pedido);
        init();
    }

    private void init() {
        
        this.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        
        montanteReforco.setLabel("Montante Requerido");
        dataPedido.setLabel("Data de pedido");
        
        this.add(dataPedido, montanteReforco);
        
        binder.bindInstanceFields(this);
        buildActionsButtons();
    }
    
    
    private void buildActionsButtons(){
        /* */
        // Button bar
        aprovarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aprovarButton.addClickListener((event) -> {
            PedidoReforcoDto despacho  = getBinder().getBean();
            this.despachoAccoes.aprovar(despacho);
        });
        
        rejeitarButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        rejeitarButton.addClickListener((event) -> {
            PedidoReforcoDto despacho  = getBinder().getBean();
            this.despachoAccoes.rejeitar(despacho);
        });
        
        
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(aprovarButton, rejeitarButton );
        
        actions.getStyle().set("marginRight", "10px");
        //this.add(actions);
    }

    
    public Binder<PedidoReforcoDto> getBinder() {
        return binder;
    }
}
